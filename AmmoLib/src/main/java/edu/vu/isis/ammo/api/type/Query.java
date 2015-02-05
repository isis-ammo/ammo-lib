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
