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
 * Specifies the Moment in which queued items are to be processed.
 */
public class SerialMoment extends AmmoType {
    static final Logger logger = LoggerFactory.getLogger("type.serial.moment");

    static final public SerialMoment RESET = null;

    public static final int APRIORI_ID = 1;
    public static final int EAGER_ID = 2;
    public static final int LAZY_ID = 3;

    public enum Type {
        /**
         * a.k.a. DIRECT
         * <p>
         * the serialized object is placed in the payload directly.
         */
        APRIORI(APRIORI_ID, "pre-serialized"),
        /**
         * a.k.a. INDIRECT
         * <p>
         * the serialized data is obtained from the named provider by uri as
         * soon as the request is received.
         */
        EAGER(EAGER_ID, "as soon as possible"),
        /**
         * a.k.a. DEFFERED
         * <p>
         * the serialized data is obtained from the named provider by uri, but
         * the serialization doesn't happen until the data is sent, i.e. the
         * channel is available.
         */
        LAZY(LAZY_ID, "the last moment");

        private final int o;
        private final String d;

        private Type(int ordinal, String description) {
            this.o = ordinal;
            this.d = description;
        }
    }

    final private Type type;

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<SerialMoment> CREATOR =
            new Parcelable.Creator<SerialMoment>() {

                @Override
                public SerialMoment createFromParcel(Parcel source) {
                    return new SerialMoment(source);
                }

                @Override
                public SerialMoment[] newArray(int size) {
                    return new SerialMoment[size];
                }
            };

    public static SerialMoment readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        return new SerialMoment(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall Moment {}", this);
        dest.writeInt(this.type.o);
    }

    private SerialMoment(Parcel in) {
        final int ordinal = in.readInt();
        this.type = getInstance(ordinal).type;
    }

    public SerialMoment(String val) {
        if (val.startsWith("A")) {
            this.type = Type.APRIORI;
            return;
        }
        if (val.startsWith("E")) {
            this.type = Type.EAGER;
            return;
        }
        if (val.startsWith("L")) {
            this.type = Type.LAZY;
            return;
        }
        this.type = Type.LAZY;
    }

    public SerialMoment(int cv) {
        this.type = getInstance(cv).type;
    }

    public static SerialMoment getInstance(int ordinal) {
        switch (ordinal) {
            case APRIORI_ID:
                return APRIORI;
            case EAGER_ID:
                return EAGER;
            case LAZY_ID:
                return LAZY;
            default:
                return DEFAULT;
        }
    }

    static public SerialMoment getInstance(String ordinal) {
        return getInstance(Integer.parseInt(ordinal));
    }

    public SerialMoment(Type val) {
        this.type = val;
    }

    public Type type() {
        return this.type;
    }

    final public static SerialMoment APRIORI = new SerialMoment(Type.APRIORI);
    final public static SerialMoment EAGER = new SerialMoment(Type.EAGER);
    final public static SerialMoment LAZY = new SerialMoment(Type.LAZY);

    public static final SerialMoment DEFAULT = LAZY;

    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.type.name())
                .append(" : ")
                .append(this.type.d)
                .toString();
    }

    /**
     * Produce string of the form... '<field-ordinal-value>';
     */
    public String quote() {
        return new StringBuilder().append("'").append(this.type.o).append("'").toString();
    }

    public int cv() {
        return this.type.o;
    }

    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof SerialMoment))
            return false;
        final SerialMoment that = (SerialMoment) obj;
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
        return null;
    }

}
