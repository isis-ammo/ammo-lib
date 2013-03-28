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

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * It is expected that there will be a CREATOR static variable for each child of
 * this class and that it will be of the form... public static final
 * Parcelable.Creator&lt;Anon&gt; CREATOR = new Parcelable.Creator&lt;Anon&gt;()
 * { &at;Override public Anon createFromParcel(Parcel source) { if
 * (AmmoType.isNull(source)) return null; return new X(source); } &at;Override
 * public Anon[] newArray(int size) { return new X[size]; } }; Where 'X' is the
 * name of the child class. Note the null check which matches up with the null
 * check in 'writeToParcel' below. Typically when a null value is allowed the
 * following pair would be used. parcel.writeValue(parcelable, flags); and
 * parcelable = parcel.readParcelable(ParcelableType.class.getClassLoader());
 * but these introduce a bunch of extra stuff that I thought we could do
 * without.
 */
public abstract class AmmoType implements Parcelable {
    protected static final Logger plogger = LoggerFactory.getLogger("api.type");

    static final int BEFORE = -1;
    static final int EQUAL = 0;
    static final int AFTER = 1;

    // *********************************
    // Parcelable Support
    // *********************************

    /**
     * Note the null check which matches up with the null check in
     * 'createFromParcel' mentioned above and implemented in each child class.
     * There is an expectation that the implementation classes will have a
     * method like... public static T readFromParcel(Parcel source) { if
     * (AmmoType.isNull(source)) return null; return new T(source); }
     */
    static public void writeToParcel(final AmmoType that, final Parcel dest, final int flags) {
        if (that == null) {
            dest.writeInt(0);
            return;
        }
        dest.writeInt(1);
        that.writeToParcel(dest, flags);
    }

    static public boolean isNull(Parcel source) {
        return (source.readInt() == 0) ? true : false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * force the individual types to implement this method.
     */
    @Override
    public abstract boolean equals(Object that);

    /**
     * force the individual types to implement this method.
     */
    @Override
    public abstract int hashCode();

    /**
     * @return
     */
    public abstract String asString();

    protected volatile int hashcode = 0;
    protected AtomicBoolean dirtyHashcode = new AtomicBoolean(true);

    /**
     * This is a helper method for constructing hash codes. On its initial
     * invocation the work should be set to 0.
     * <p>
     * For example <code>
     *   work = incrementHash(0, part1.hashCode());
     *   work = incrementHash(work, part2.hashCode();
     *   work = incrementHash(work, part3.hashCode();
     *   ...
     *   work = incrementHash(work, part_N.hashCode();
     * </code>
     * <p>
     * This class is based on "Effective Java" Joshua Bloch Item 9 : Always
     * override hashcode when you override equals.
     * 
     * @param work the hash code in progress.
     * @param increment the hash code of the next element.
     * @return the candidate hash code
     */
    protected static class HashBuilder {
        private int hashcode;

        private HashBuilder(int basecode) {
            this.hashcode = basecode;
        }

        public static HashBuilder newBuilder() {
            return new HashBuilder(17);
        }

        /**
         * Arrays are special.
         * 
         * @param nextcode
         * @return
         */
        public HashBuilder increment(Object nextcode) {
            this.hashcode *= 31;
            if (nextcode != null) {
                this.hashcode += nextcode.hashCode();
            }
            return this;
        }

        public HashBuilder increment(Object[] array) {
            return this.increment(Arrays.hashCode(array));
        }

        public HashBuilder increment(int nextcode) {
            this.hashcode *= 31;
            this.hashcode += nextcode;
            return this;
        }

        public HashBuilder increment(int[] array) {
            return this.increment(Arrays.hashCode(array));
        }

        public HashBuilder increment(boolean nextcode) {
            return this.increment((int) (nextcode ? 1 : 0));
        }

        public HashBuilder increment(boolean[] array) {
            return this.increment(Arrays.hashCode(array));
        }

        public HashBuilder increment(byte nextcode) {
            return this.increment((int) nextcode);
        }

        public HashBuilder increment(byte[] array) {
            return this.increment(Arrays.hashCode(array));
        }

        public HashBuilder increment(char nextcode) {
            return this.increment((int) nextcode);
        }

        public HashBuilder increment(char[] array) {
            return this.increment(Arrays.hashCode(array));
        }

        public HashBuilder increment(short nextcode) {
            return this.increment((int) nextcode);
        }

        public HashBuilder increment(short[] array) {
            return this.increment(Arrays.hashCode(array));
        }

        public HashBuilder increment(long nextcode) {
            return this.increment((int) (nextcode ^ (nextcode >>> 32)));
        }

        public HashBuilder increment(long[] array) {
            return this.increment(Arrays.hashCode(array));
        }

        public HashBuilder increment(float nextcode) {
            return this.increment((int) (Float.floatToIntBits(nextcode)));
        }

        public HashBuilder increment(float[] array) {
            return this.increment(Arrays.hashCode(array));
        }

        public HashBuilder increment(double nextcode) {
            return this.increment((long) (Double.doubleToLongBits(nextcode)));
        }

        public HashBuilder increment(double[] array) {
            return this.increment(Arrays.hashCode(array));
        }

        @Override
        public int hashCode() {
            return this.hashcode;
        }
    }

    protected static boolean differ(Object left, Object right) {
        if (left != right) {
            if (left == null)
                return true;
            if (!left.equals(right))
                return true;
        }
        return false;
    }

    /**
     * Write the object to a stream of bytes using the Parcel encoding. Make
     * sure to write the not null indicator (a 1) before writing the parcel.
     * 
     * @return
     */
    public byte[] pickle() {
        Parcel np = null;
        try {
            np = Parcel.obtain();

            np.setDataPosition(0);
            np.writeInt(1);
            this.writeToParcel(np, Parcelable.CONTENTS_FILE_DESCRIPTOR);
            np.setDataPosition(0);
            final byte[] bytes = np.marshall();
            plogger.info("pickle: [{}]", bytes);
            return bytes;
        } finally {
            if (np != null)
                np.recycle();
        }
    }

}
