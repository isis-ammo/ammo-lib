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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.vu.isis.ammo.api.IncompleteRequest;
import android.os.Parcel;
import android.os.Parcelable;

public class Topic extends AmmoType { 

    static final Logger logger = LoggerFactory.getLogger("type.topic");
    
    static final public Topic RESET = null;

    public static final String DEFAULT = "";

    static final private int OID_ID = 0;
    static final private int STR_ID = 1;

    static final Topic NONE = new Topic(Oid.EMPTY);
    
    public enum Type { 
        /**
         * An object identifier (list of integers)
         */
        OID(OID_ID), 
        /**
         * A topic as a string.
         */
        STR(STR_ID);

        final public int id;
        private Type(final int id) { this.id = id; }
        static public Type getInstance(final int id) {
            switch (id) {
                case OID_ID: return OID;
                case STR_ID: return STR;
            }
            return null;
        }
    };

    final private Type type;
    final private String str;
    final private Oid oid;

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Topic> CREATOR = 
            new Parcelable.Creator<Topic>() {

        @Override
        public Topic createFromParcel(Parcel source) {
            try {
                return new Topic(source);
            } catch (IncompleteRequest ex) {
                return null;
            }
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    public static Topic readFromParcel(Parcel source) {
        if (AmmoType.isNull(source)) return null;
        try {
            return new Topic(source);
        } catch (IncompleteRequest ex) {
            return null;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {		
        plogger.trace("marshall topic {}", this);
        dest.writeInt(this.type.id);

        switch (this.type) {
            case OID:
                this.oid.writeToParcel(dest, flags);
                return;
            case STR:
                dest.writeString(this.str);
                return;
        }
    }

    public Topic(Parcel in) throws IncompleteRequest {
        int ordinal = -1;
        try {
            ordinal = in.readInt();

            this.type = Type.getInstance(ordinal);
            if (this.type == null) {
                this.str = null;
                this.oid = null;
            } else
                switch (this.type) {
                    case OID:
                        this.str = null;
                        this.oid = Oid.CREATOR.createFromParcel(in);
                        break;
                    case STR:
                        this.str = in.readString();
                        this.oid = null;
                        break;
                    default:
                        this.str = null;
                        this.oid = null;
                }

        } catch (ArrayIndexOutOfBoundsException ex) {
            plogger.error("bad topic {} {}", ordinal, ex);
            throw new IncompleteRequest(ex);
        }
        plogger.trace("unmarshall topic {}", this);
    }
    // *********************************
    // Standard Methods
    // *********************************
    /**
     * Do not used toString() for serializing the topic.
     * Use asString() instead.
     * toString() is intended for reading by humans.
     */
    @Override
    public String toString() {
        if (this.type == null) {
            return "<no type>";
        }
        switch (this.type) {
            case OID:
                return "oid:"+this.oid.toString();
            case STR:
                return "str:"+this.str;
            default:
                return "<unknown type>"+ this.type;
        }
    }

    // *********************************
    // IAmmoReques Support
    // *********************************

    /**
     * The constructor taking a string is symmetric with 
     * the asString() method.
     * @param val
     */
    public Topic(String val) {
        this.type = Type.STR;
        this.str = val;
        this.oid = null;
    }
    public Topic(Oid val) {
        this.type = Type.OID;
        this.str = null;
        this.oid = val;
    }

    /**
     * When the topic is to be transmitted as a string this 
     * is the method which should be used <b>NOT</b> toString().
     * 
     * @return
     */
    @Override
    public String asString() { 
        return this.toString();
    }
    
    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Topic)) return false;
        final Topic that = (Topic) obj;
        if (this.type != that.type) return false;
        if (this.type == null) return true;
        switch (this.type){
            case STR: 
                return (this.str.equals(that.str));
            case OID: 
                return (this.oid.equals(that.oid));
            default:
                plogger.warn("invalid type {}", this.type);
                return false;
        }
    }

    private int hashcode;
    private boolean dirtyHashcode;

    @Override
    public synchronized int hashCode() {
        if (!this.dirtyHashcode)
            return this.hashcode;
        this.dirtyHashcode = false;
        final HashBuilder hb =  AmmoType.HashBuilder.newBuilder()
                .increment(this.type);
        switch (this.type){
            case STR: 
                hb.increment(this.str);
                break;
            case OID: 
                hb.increment(this.str);
                break;
            default:
                hb.increment(this.str);
                break;
        }
        this.hashcode = hb.toCode();
        return this.hashcode;
    }

}


