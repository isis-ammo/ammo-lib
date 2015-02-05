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

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Provider extends AmmoType {

    static final public Provider RESET = null;

    static final private int URI_ID = 0;

    public enum Type {
        URI(URI_ID);

        final public int id;

        private Type(int id) {
            this.id = id;
        }

        static public Type getInstance(int id) {
            switch (id) {
                case URI_ID:
                    return URI;
            }
            return null;
        }
    }

    final private Type type;
    final private Uri uri;

    public Provider(Uri val) {
        this.type = Type.URI;
        this.uri = val;
    }

    public String cv() {
        if (this.type == null) {
            return null;
        }

        switch (this.type) {
            case URI:
                if (this.uri == null) {
                    return null;
                }
                return this.uri.toString();
        }
        return null;
    }

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Provider> CREATOR =
            new Parcelable.Creator<Provider>() {

                @Override
                public Provider createFromParcel(Parcel source) {
                    return new Provider(source);
                }

                @Override
                public Provider[] newArray(int size) {
                    return new Provider[size];
                }
            };

    public static Provider readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        return new Provider(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall provider {}", this);
        dest.writeInt(this.type.id);

        switch (this.type) {
            case URI:
                Uri.writeToParcel(dest, this.uri);
                return;
        }
    }

    public Provider(Parcel in) {
        this.type = Type.getInstance(in.readInt());
        if (this.type == null) {
            this.uri = null;
        } else
            switch (this.type) {
                case URI:
                    this.uri = Uri.CREATOR.createFromParcel(in);
                    break;
                default:
                    this.uri = null;
            }
        plogger.trace("unmarshall provider {}", this);
    }

    // *********************************
    // Standard Methods
    // *********************************

    public Provider(String val) {
        this.type = Type.URI;
        this.uri = Uri.parse(val);
    }

    @Override
    public String toString() {
        if (this.type == null) {
            return "<no type>";
        }
        switch (this.type) {
            case URI:
                return this.uri.toString();
            default:
                return "<unknown type>" + this.type;
        }
    }

    // *********************************
    // IAmmoRequest Support
    // *********************************

    public final static Uri DEFAULT = Uri.parse("");

    public byte[] asBytes() {
        switch (this.type) {
            case URI:
                return this.uri.toString().getBytes();
        }
        return null;
    }

    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Provider))
            return false;
        final Provider that = (Provider) obj;
        if (AmmoType.differ(this.type, that.type))
            return false;

        switch (this.type) {
            case URI:
                if (AmmoType.differ(this.uri, that.uri))
                    return false;
                return true;
        }
        return true;
    }

    @Override
    public synchronized int hashCode() {
        if (!this.dirtyHashcode.getAndSet(false))
            return this.hashcode;
        this.hashcode = AmmoType.HashBuilder.newBuilder()
                .increment(this.type)
                .increment(this.uri)
                .hashCode();
        return this.hashcode;
    }

    public String asString() {
        switch (this.type) {
            case URI:
                return this.uri.toString();
        }
        return null;
    }

    public Uri asUri() {
        switch (this.type) {
            case URI:
                return this.uri;
        }
        return null;
    }
}
