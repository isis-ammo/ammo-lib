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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The query is used to convey some selection of objects.
 */

public class Query extends AmmoType {
    static final Logger logger = LoggerFactory.getLogger("type.query");

    static final public Query RESET = null;

    final private String select;
    final private String[] args;

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Query> CREATOR =
            new Parcelable.Creator<Query>() {

                @Override
                public Query createFromParcel(Parcel source) {
                    return new Query(source);
                }

                @Override
                public Query[] newArray(int size) {
                    return new Query[size];
                }
            };

    public static Query readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        return new Query(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall query {}", this);
        dest.writeString(this.select);
        dest.writeStringArray(this.args);
    }

    private Query(Parcel in) {
        this.select = in.readString();
        this.args = in.createStringArray();
        plogger.trace("unmarshall query {}", this);
    }

    // *********************************
    // Standard Methods
    // *********************************
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.select);
        if (this.args != null)
            sb.append(" args ").append(this.args);
        return sb.toString();
    }

    // *********************************
    // IAmmoReques Support
    // *********************************

    public Query(String select, String[] args) {
        this.select = select;
        this.args = args;
    }

    public Query(String select) {
        this.select = select;
        this.args = null;
    }

    public String select() {
        return this.select;
    }

    public String[] args() {
        return this.args();
    }

    public Query args(String[] args) {
        return new Query(this.select, args);
    }

    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Query))
            return false;
        final Query that = (Query) obj;
        if (AmmoType.differ(this.select, that.select))
            return false;
        if (AmmoType.differ(this.args, that.args))
            return false;
        return true;
    }

    @Override
    public synchronized int hashCode() {
        if (!this.dirtyHashcode.getAndSet(false))
            return this.hashcode;
        this.hashcode = AmmoType.HashBuilder.newBuilder()
                .increment(this.select)
                .increment(this.args)
                .hashCode();
        return this.hashcode;
    }

    @Override
    public String asString() {
        logger.error("asString() not implemented");
        return null;
    }
}
