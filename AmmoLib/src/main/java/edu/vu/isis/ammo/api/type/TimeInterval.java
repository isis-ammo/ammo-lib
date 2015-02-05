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
 * Time intervals, the an interval of time expressed in a single unit.
 */
public class TimeInterval extends AmmoType {
    static final Logger logger = LoggerFactory.getLogger("type.time.interval");

    public enum Unit {
        MILLISEC, SECOND, MINUTE, HOUR, DAY, YEAR
    };

    static public final int UNLIMITED = Integer.MAX_VALUE;

    final private Unit units;
    final private long quantity;

    // ****************************
    // Parcelable Support
    // ****************************

    public static final Parcelable.Creator<TimeInterval> CREATOR =
            new Parcelable.Creator<TimeInterval>() {

                @Override
                public TimeInterval createFromParcel(Parcel source) {
                    return new TimeInterval(source);
                }

                @Override
                public TimeInterval[] newArray(int size) {
                    return new TimeInterval[size];
                }
            };

    public static TimeInterval readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        return new TimeInterval(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall time interval {}", this);
        dest.writeInt(this.units.ordinal());
        dest.writeLong(this.quantity);
    }

    private TimeInterval(Parcel in) {
        this.units = Unit.values()[in.readInt()];
        this.quantity = in.readLong();
        plogger.trace("unmarshall time interval {}", this);
    }

    public long cv() {
        long millis = this.quantity;
        switch (this.units) {
            case YEAR:
                millis *= 365.25;
            case DAY:
                millis *= 24;
            case HOUR:
                millis *= 60;
            case MINUTE:
                millis *= 60;
            case SECOND:
                millis *= 1000;
            case MILLISEC:
                return millis;
            default:
                return Long.MAX_VALUE;
        }
    }

    // *********************************
    // Standard Methods
    // *********************************
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.quantity).append(' ').append(this.units);
        return sb.toString();
    }

    // *********************************
    // IAmmoReques Support
    // *********************************

    public Unit unit() {
        return this.units;
    }

    public long quantity() {
        return this.quantity;
    }

    public TimeInterval(Unit unit, long amount) {
        this.units = unit;
        this.quantity = amount;
    }

    public TimeInterval(Unit unit) {
        this.units = unit;
        this.quantity = 1;
    }

    public TimeInterval(String seconds) {
        this.units = Unit.SECOND;
        this.quantity = Long.parseLong(seconds);
    }

    public TimeInterval(long seconds) {
        this.units = Unit.SECOND;
        this.quantity = seconds;
    }

    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof TimeInterval))
            return false;
        final TimeInterval that = (TimeInterval) obj;
        if (AmmoType.differ(this.units, that.units))
            return false;
        if (this.quantity != that.quantity)
            return false;
        return true;
    }

    @Override
    public synchronized int hashCode() {
        if (! this.dirtyHashcode.getAndSet(false))
            return this.hashcode;
        this.hashcode = AmmoType.HashBuilder.newBuilder()
                .increment(this.units)
                .increment(this.quantity)
                .hashCode();
        return this.hashcode;
    }

    @Override
    public String asString() {
        logger.error("asString() not implemented");
        return null;
    }

}
