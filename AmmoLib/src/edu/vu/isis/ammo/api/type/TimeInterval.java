/*
Copyright (C) 2010-2012 Institute for Software Integrated Systems (ISIS)
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
 * Time intervals, the an interval of time expressed in a single unit.
 */
public class TimeInterval extends AmmoType {
	
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
    	if (AmmoType.isNull(source)) return null;
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
    	case YEAR: millis *= 365.25;
    	case DAY: millis *= 24;
    	case HOUR: millis *= 60;
    	case MINUTE: millis *= 60;
    	case SECOND: millis *= 1000;
    	case MILLISEC:
    		return millis;
    	default:
    			return 0;
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
   
}
