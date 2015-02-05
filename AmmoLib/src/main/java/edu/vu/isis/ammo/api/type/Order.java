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
public class Order extends AmmoType {
    static final Logger logger = LoggerFactory.getLogger("type.order");

    public enum Type {
        /**
         * Present the oldest items from the set first
         */
        OLDEST_FIRST(1, "Oldest First"),
        /**
         * 
         */
        NEWEST_ONLY(2, "Newest Only"),
        /**
         * 
         */
        NEWEST_FIRST(3, "Newest First");

        private final int o;
        private final String d;

        private Type(int ordinal, String description) {
            this.o = ordinal;
            this.d = description;
        }
    }

    final private Type type;

    public int cv() {
        return this.type.o;
    }

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Order> CREATOR =
            new Parcelable.Creator<Order>() {

                @Override
                public Order createFromParcel(Parcel source) {
                    return new Order(source);
                }

                @Override
                public Order[] newArray(int size) {
                    return new Order[size];
                }
            };

    public static Order readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        return new Order(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall order {}", this);
        dest.writeInt(this.type.ordinal());
    }

    private Order(Parcel in) {
        this.type = Type.values()[in.readInt()];
        plogger.trace("unmarshall order []", this);
    }

    // *********************************
    // IAmmoRequest Support
    // *********************************

    public Order(String val) {
        if (val.contains("L")) {
            this.type = Type.NEWEST_FIRST;
            return;
        }
        if (val.contains("1")) {
            this.type = Type.NEWEST_ONLY;
            return;
        }
        if (val.contains("F")) {
            this.type = Type.OLDEST_FIRST;
            return;
        }
        this.type = Type.OLDEST_FIRST;
    }

    public Order(Type val) {
        this.type = val;
    }

    public Type type() {
        return this.type;
    }

    final public static Order NEWEST_FIRST = new Order(Type.NEWEST_FIRST);
    final public static Order NEWEST_ONLY = new Order(Type.NEWEST_ONLY);
    final public static Order OLDEST_FIRST = new Order(Type.OLDEST_FIRST);

    @Override
    public String toString() {
        return new StringBuilder().append(this.type.d).toString();
    }

    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Order))
            return false;
        final Order that = (Order) obj;
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

    @Override
    public String asString() {
        logger.error("asString() not implemented");
        switch(this.type) {
            case NEWEST_FIRST: return "L";
            case NEWEST_ONLY: return "1";
            case OLDEST_FIRST:
                default: return "F";
        }
    }

}
