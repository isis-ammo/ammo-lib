/*Copyright (C) 2010-2012 Institute for Software Integrated Systems (ISIS)
This software was developed by the Institute for Software Integrated
Systems (ISIS) at Vanderbilt University, Tennessee, USA for the 
Transformative Apps program under DARPA, Contract # HR011-10-C-0175.
The United States Government has unlimited rights to this software. 
The US government has the right to use, modify, reproduce, release, 
perform, display, or disclose computer software or computer software 
documentation in whole or in part, in any manner and for any 
purpose whatsoever, and to have or authorize others to do so.
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
