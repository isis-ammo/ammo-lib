/* Copyright (c) 2010-2015 Vanderbilt University
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package edu.vu.isis.ammo.api.type;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

public class Template extends AmmoType {

    static final public Template RESET = null;

    static final private int NONE_ID = 0;
    static final private int STR_ID = 1;
    static final private int BYTE_ID = 2;
    static final private int CV_ID = 3;

    public enum Type {
        /**
         * Essentially a null payload.
         */
        NONE(NONE_ID),
        /**
         * A string.
         */
        STR(STR_ID),
        /**
         * A byte array
         */
        BYTE(BYTE_ID),
        /**
         * A content provider style set of content values.
         */
        CV(CV_ID);

        final public int id;

        private Type(final int id) {
            this.id = id;
        }

        static public Type getInstance(final int id) {
            switch (id) {
                case NONE_ID:
                    return NONE;
                case STR_ID:
                    return STR;
                case BYTE_ID:
                    return BYTE;
                case CV_ID:
                    return CV;
            }
            return null;
        }
    };

    final private Type type;
    final private String str;
    final private byte[] bytes;
    final private ContentValues cv;

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Template> CREATOR =
            new Parcelable.Creator<Template>() {

                @Override
                public Template createFromParcel(Parcel source) {
                    return new Template(source);
                }

                @Override
                public Template[] newArray(int size) {
                    return new Template[size];
                }
            };

    public static final String DEFAULT = "";

    public static final Template NONE = new Template();

    public static Template readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        return new Template(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall payload {}", this);
        dest.writeInt(this.type.id);

        switch (this.type) {
            case CV:
                this.cv.writeToParcel(dest, flags);
                return;
            case BYTE:
                dest.writeByteArray(this.bytes);
                return;
            case STR:
                dest.writeString(this.str);
                return;
            case NONE:
            default:
                plogger.error("invalid type {}", this.type);
                break;
        }
    }

    public Template(Parcel in) {
        this.type = Type.getInstance(in.readInt());
        if (this.type == null) {
            this.str = null;
            this.bytes = null;
            this.cv = null;
        } else
            switch (this.type) {
                case CV:
                    this.str = null;
                    this.bytes = null;
                    this.cv = ContentValues.CREATOR.createFromParcel(in);
                    break;
                case BYTE:
                    this.str = null;
                    this.bytes = in.createByteArray();
                    this.cv = null;
                    break;
                case STR:
                    this.str = in.readString();
                    this.bytes = null;
                    this.cv = null;
                    break;
                case NONE:
                default:
                    this.str = null;
                    this.bytes = null;
                    this.cv = null;
            }
        plogger.trace("unmarshall payload");
    }

    // *********************************
    // Standard Methods
    // *********************************
    @Override
    public String toString() {
        if (this.type == null) {
            return "<no type>";
        }
        switch (this.type) {
            case CV:
                if (this.cv == null)
                    return "";
                return this.cv.toString();
            case BYTE:
                if (this.bytes == null)
                    return "";
                return this.bytes.toString();
            case STR:
                if (this.str == null)
                    return "";
                return this.str.toString();
            case NONE:
            default:
                return "<unknown type>" + this.type;
        }
    }

    // *********************************
    // IAmmoRequest Support
    // *********************************

    public Template() {
        this.type = Type.NONE;
        this.str = null;
        this.bytes = null;
        this.cv = null;
    }

    public Template(String val) {
        this.type = Type.STR;
        this.str = val;
        this.bytes = null;
        this.cv = null;
    }

    public Template(byte[] val) {
        this.type = Type.BYTE;
        this.str = null;
        this.bytes = val;
        this.cv = null;
    }

    public Template(ContentValues val) {
        this.type = Type.CV;
        this.str = null;
        this.bytes = null;
        this.cv = val;
    }

    public byte[] asBytes() {

        switch (this.type) {
            case BYTE:
                return this.bytes;
            case STR:
                return this.str.getBytes();
            case CV:
                return this.encodeJson();
            case NONE:
            default:
                plogger.error("invalid type {}", this.type);
        }
        return null;
    }

    private byte[] encodeJson() {
        // encoding in json for now ...
        Set<java.util.Map.Entry<String, Object>> data = this.cv.valueSet();
        Iterator<java.util.Map.Entry<String, Object>> iter = data.iterator();
        final JSONObject json = new JSONObject();

        while (iter.hasNext())
        {
            Map.Entry<String, Object> entry =
                    (Map.Entry<String, Object>) iter.next();
            try {
                if (entry.getValue() instanceof String)
                    json.put(entry.getKey(), cv.getAsString(entry.getKey()));
                else if (entry.getValue() instanceof Integer)
                    json.put(entry.getKey(), cv.getAsInteger(entry.getKey()));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return json.toString().getBytes();
    }

    /**
     * What type of content if any is present. Just because the type is
     * specified it may not be valid. This method can be used to determine if
     * the payload has content.
     * 
     * @return
     */
    public Type whatContent() {
        switch (this.type) {
            case STR:
                if (this.str == null)
                    return Type.NONE;
                if (this.str.length() < 1)
                    return Type.NONE;
                return Type.STR;
            case BYTE:
                if (this.bytes == null)
                    return Type.NONE;
                return Type.BYTE;
            case CV:
                if (this.cv == null)
                    return Type.NONE;
                if (this.cv.valueSet().isEmpty())
                    return Type.NONE;
                return Type.CV;
            case NONE:
            default:
                plogger.warn("invalid type {}", this.type);
        }
        return Type.NONE;
    }

    public ContentValues getCV() {
        return cv;
    }

    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Template))
            return false;
        final Template that = (Template) obj;
        if (AmmoType.differ(this.type, that.type))
            return false;
        switch (this.type) {
            case STR:
                if (AmmoType.differ(this.str, that.str))
                    return false;
                return true;
            case BYTE:
                if (AmmoType.differ(this.bytes, that.bytes))
                    return false;
                return true;
            case CV:
                if (AmmoType.differ(this.cv, that.cv))
                    return false;
                return true;
            case NONE:
                return true;
            default:
                plogger.warn("invalid type {}", this.type);
                return false;
        }
    }

    @Override
    public synchronized int hashCode() {
        if (!this.dirtyHashcode.getAndSet(false))
            return this.hashcode;
        this.hashcode = AmmoType.HashBuilder.newBuilder()
                .increment(this.type)
                .increment(this.str)
                .increment(this.bytes)
                .increment(this.cv)
                .hashCode();
        return this.hashcode;
    }

    public String asString() {
        switch (this.type) {
            case BYTE:
                return new String(this.bytes);
            case STR:
                return this.str;
            case NONE:
            default:
                plogger.error("invalid type {}", this.type);
        }
        return null;
    }

}
