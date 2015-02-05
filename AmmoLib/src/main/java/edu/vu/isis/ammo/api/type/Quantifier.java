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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The quantifier describes the extent to which the message is expected to
 * spread.
 */
public class Quantifier extends AmmoType {

    static final public Quantifier RESET = null;

    public enum Type {
        /**
         * only one recipient/subscriber is expected
         */
        SINGLE(0x01),
        /**
         * only multiple subscribers restricted on the sub-topic
         */
        ROOM(0x02),
        /**
         * expected to go to pretty much everybody, subtopic irrelevant
         */
        ALL(0x03),
        /*
         * expected to be retrieved so distribution is not knowable
         */
        BULLETIN(0x04);

        final public int o;

        private Type(int o) {
            this.o = o;
        }
    }

    final private Type type;

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Quantifier> CREATOR =
            new Parcelable.Creator<Quantifier>() {

                @Override
                public Quantifier createFromParcel(Parcel source) {
                    return new Quantifier(source);
                }

                @Override
                public Quantifier[] newArray(int size) {
                    return new Quantifier[size];
                }
            };

    public static final Type DEFAULT = Type.BULLETIN;

    public static Quantifier readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        return new Quantifier(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall topic {}", this);
        dest.writeInt(this.type.o);

    }

    public Quantifier(Parcel in) {
        final int ordinal = in.readInt();
        if (ordinal == Type.SINGLE.o) {
            this.type = Type.SINGLE;
        } else if (ordinal == Type.BULLETIN.o) {
            this.type = Type.BULLETIN;
        } else if (ordinal == Type.ROOM.o) {
            this.type = Type.ROOM;
        } else if (ordinal == Type.ALL.o) {
            this.type = Type.ALL;
        } else {
            this.type = DEFAULT;
            plogger.trace("unknown qualifier {} using {}",
                    ordinal, this.type.name());
        }
        plogger.trace("unmarshall topic {}", this);
    }

    // *********************************
    // Standard Methods
    // *********************************
    @Override
    public String toString() {
        if (this.type == null) {
            return "<no type>";
        }
        return this.type.name();
    }

    // *********************************
    // IAmmoReques Support
    // *********************************

    public Quantifier(String val) {
        if (val.equalsIgnoreCase(Type.SINGLE.name())) {
            this.type = Type.SINGLE;
        } else if (val.equalsIgnoreCase(Type.ROOM.name())) {
            this.type = Type.ROOM;
        } else if (val.equalsIgnoreCase(Type.BULLETIN.name())) {
            this.type = Type.BULLETIN;
        } else if (val.equalsIgnoreCase(Type.ALL.name())) {
            this.type = Type.ALL;
        } else {
            this.type = DEFAULT;
            plogger.trace("unknown qualifier {} using {}",
                    val, this.type.name());
        }
    }

    public Quantifier(Type type) {
        this.type = type;
    }

    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Quantifier))
            return false;
        final Quantifier that = (Quantifier) obj;
        if (AmmoType.differ(this.type, that.type))
            return false;
        return true;
    }

    @Override
    public synchronized int hashCode() {
        if (! this.dirtyHashcode.getAndSet(false))
            return this.hashcode;
        this.hashcode = AmmoType.HashBuilder.newBuilder()
                .increment(this.type)
                .hashCode();
        return this.hashcode;
    }

    public String asString() {
        return this.toString();
    }

}
