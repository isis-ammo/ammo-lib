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

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeStamp extends AmmoType {
    static final Logger logger = LoggerFactory.getLogger("type.time.stamp");

    final private long millis;
    final private TimeInterval interval;

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<TimeStamp> CREATOR =
            new Parcelable.Creator<TimeStamp>() {

                @Override
                public TimeStamp createFromParcel(Parcel source) {
                    return new TimeStamp(source);
                }

                @Override
                public TimeStamp[] newArray(int size) {
                    return new TimeStamp[size];
                }
            };

    public static TimeStamp readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        return new TimeStamp(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall time stamp {}", this);
        dest.writeLong(this.millis);
        TimeInterval.writeToParcel(this.interval, dest, flags);
    }

    private TimeStamp(Parcel in) {
        this.millis = in.readLong();
        this.interval = TimeInterval.readFromParcel(in);
        plogger.trace("unmarshall time stamp {}", this);
    }

    public long cv() {
        return this.millis + this.interval.cv();
    }

    // *********************************
    // Standard Methods
    // *********************************
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.millis).append(" + ").append(this.interval);
        return sb.toString();
    }

    // *********************************
    // IAmmoReques Support
    // *********************************

    public TimeStamp() {
        this.millis = System.currentTimeMillis();
        this.interval = new TimeInterval(0);
    }

    public TimeStamp(String val) {
        this.millis = System.currentTimeMillis();
        this.interval = new TimeInterval(val);
    }

    public TimeStamp(Calendar cal) {
        this.millis = (cal == null) ? System.currentTimeMillis() : cal.getTimeInMillis();
        this.interval = new TimeInterval(0);
    }

    public TimeStamp(Calendar cal, int interval) {
        this.millis = (cal == null) ? System.currentTimeMillis() : cal.getTimeInMillis();
        this.interval = new TimeInterval(interval);
    }

    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof TimeStamp))
            return false;
        final TimeStamp that = (TimeStamp) obj;
        if (this.millis != that.millis)
            return false;
        if (AmmoType.differ(this.interval, that.interval))
            return false;
        return true;
    }

    @Override
    public synchronized int hashCode() {
        if (!this.dirtyHashcode.getAndSet(false))
            return this.hashcode;
        this.hashcode = AmmoType.HashBuilder.newBuilder()
                .increment(this.millis)
                .increment(this.interval)
                .hashCode();
        return this.hashcode;
    }

    @Override
    public String asString() {
        logger.error("asString() not implemented");
        return null;
    }

}
