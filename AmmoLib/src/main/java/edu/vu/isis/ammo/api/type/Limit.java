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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Specifies the order in which queued items are to be processed.
 */
public class Limit extends AmmoType {
    static final Logger logger = LoggerFactory.getLogger("type.limit");

    public int count;

    public enum Type {
        /**
         * indicates that the oldest items should be preferred
         */
        OLDEST(1, "Oldest"),
        /**
         * indicates that the newest items should be preferred
         */
        NEWEST(2, "Newest");

        public final int o;
        public final String d;

        private Type(int ordinal, String description) {
            this.o = ordinal;
            this.d = description;
        }
    }

    final private Type type;

    public int cv() {
        return this.count;
    }

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Limit> CREATOR =
            new Parcelable.Creator<Limit>() {

                @Override
                public Limit createFromParcel(Parcel source) {
                    return new Limit(source);
                }

                @Override
                public Limit[] newArray(int size) {
                    return new Limit[size];
                }
            };

    public static Limit readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        return new Limit(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall order {}", this);
        dest.writeInt(this.type.ordinal());
        dest.writeInt(this.count);
    }

    private Limit(Parcel in) {
        this.dirtyHashcode.set(true);
        this.type = Type.values()[in.readInt()];
        this.count = in.readInt();
        plogger.trace("unmarshall limit []", this);
    }

    // *********************************
    // IAmmoRequest Support
    // *********************************

    public Limit(String val) {
        this.dirtyHashcode.set(true);
        this.count = 1;
        if (val.startsWith("O")) {
            this.type = Type.OLDEST;
            return;
        }
        this.type = Type.NEWEST;
    }

    public Limit(Type type, int count) {
        this.type = type;
        this.count = count;
    }

    public Limit(int count) {
        this.type = Type.NEWEST;
        this.count = count;
    }

    public Limit() {
        this.type = Type.NEWEST;
        this.count = -1;
    }

    public Type type() {
        return this.type;
    }

    final public static Limit NEWEST = new Limit(Type.NEWEST, 1);
    final public static Limit OLDEST = new Limit(Type.OLDEST, 1);

    @Override
    public String toString() {
        return new StringBuilder().append(this.type.d).toString();
    }

    public Integer asInteger() {
        switch (this.type) {
            case OLDEST:
                return -(this.count);
            case NEWEST:
            default:
                return (this.count);
        }
    }

    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Limit))
            return false;
        final Limit that = (Limit) obj;
        if (AmmoType.differ(this.type, that.type))
            return false;
        if (this.count != that.count)
            return false;
        return true;
    }

    @Override
    public synchronized int hashCode() {
        if (!this.dirtyHashcode.getAndSet(false))
            return this.hashcode;
        this.hashcode = AmmoType.HashBuilder.newBuilder()
                .increment(this.type)
                .increment(this.count)
                .hashCode();
        return this.hashcode;
    }

    @Override
    public String asString() {
        logger.error("asString() not implemented");
        return null;
    }

}
