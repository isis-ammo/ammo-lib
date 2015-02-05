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
 * This identifies how far the message is to diffuse.
 */
public class DeliveryScope extends AmmoType {
    static final Logger logger = LoggerFactory.getLogger("type.delivery-scope");

    static final public DeliveryScope RESET = null;

    static final private int GLOBAL_ID = 0;
    static final private int LOCAL_ID = 1;

    public enum Type {
        /**
         * Indicates that the last update originated outside of the current
         * device/application scope.
         */
        GLOBAL(GLOBAL_ID),
        /**
         * Indicates that the last update originated in the current
         * device/application scope.
         */
        LOCAL(LOCAL_ID);
        final public int id;

        private Type(final int id) {
            this.id = id;
        }

        static public Type getInstance(final int id) {
            switch (id) {
                case GLOBAL_ID:
                    return GLOBAL;
                case LOCAL_ID:
                    return LOCAL;
            }
            return null;
        }
    }

    final private Type type;

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<DeliveryScope> CREATOR =
            new Parcelable.Creator<DeliveryScope>() {

                @Override
                public DeliveryScope createFromParcel(Parcel source) {
                    return new DeliveryScope(source);
                }

                @Override
                public DeliveryScope[] newArray(int size) {
                    return new DeliveryScope[size];
                }
            };

    public static DeliveryScope readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        return new DeliveryScope(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall dscope {}", this);
        dest.writeInt(this.type.id);
    }

    private DeliveryScope(Parcel in) {
        this.type = Type.getInstance(in.readInt());
        plogger.trace("unmarshall dscope []", this);
    }

    // *********************************
    // IAmmoRequest Support
    // *********************************

    public DeliveryScope(String val) {
        this.type = (val.contains("G") || val.contains("g"))
                ? Type.GLOBAL
                : Type.LOCAL;
    }

    public DeliveryScope(Type val) {
        this.type = val;
    }

    public Type type() {
        return this.type;
    }

    final public static DeliveryScope GLOBAL = new DeliveryScope(Type.GLOBAL);
    final public static DeliveryScope LOCAL = new DeliveryScope(Type.LOCAL);
    public final static DeliveryScope DEFAULT = GLOBAL;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        switch (this.type) {
            case GLOBAL:
                sb.append("Global");
                break;
            case LOCAL:
                sb.append("Local");
                break;
            default:
                sb.append("<unknown>");
        }
        return sb.toString();
    }

    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof DeliveryScope))
            return false;
        final DeliveryScope that = (DeliveryScope) obj;
        if (AmmoType.differ(this.type, that.type))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        if (this.hashcode != 0)
            return this.hashcode;
        this.hashcode = AmmoType.HashBuilder.newBuilder()
                .increment(this.type)
                .hashCode();
        return this.hashcode;
    }

    @Override
    public String asString() {
        logger.error("asString() not implemented");
        return null;
    }

}
