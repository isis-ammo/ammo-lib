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

import android.os.Parcel;
import android.os.Parcelable;

public class TimeTrigger extends AmmoType {

	public enum Type { ABS, REL; }

	private Type type;
	public Type type() { return type; }

	final public TimeStamp abs;
	final public TimeInterval rel;

	// *********************************
	// Parcelable Support
	// *********************************

	public static final Parcelable.Creator<TimeTrigger> CREATOR = 
			new Parcelable.Creator<TimeTrigger>() {

		@Override
		public TimeTrigger createFromParcel(Parcel source) {
			return new TimeTrigger(source);
		}

		@Override
		public TimeTrigger[] newArray(int size) {
			return new TimeTrigger[size];
		}
	};

	public static TimeTrigger readFromParcel(Parcel source) {
		if (AmmoType.isNull(source)) return null;
		return new TimeTrigger(source);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		plogger.trace("marshall time trigger {}", this);
		dest.writeInt(this.type.ordinal());
		switch (this.type) {
		case ABS:
			TimeStamp.writeToParcel(this.abs, dest, flags);
			return;
		case REL:
			TimeInterval.writeToParcel(this.rel, dest, flags);
			return;
		}
	}

	private TimeTrigger(Parcel in) {
		this.type = Type.values()[ in.readInt() ];
		if (this.type == null) {
			this.abs = null;
			this.rel = null;
		} else
			switch (this.type) {
			case ABS:
				this.abs = TimeStamp.readFromParcel(in);
				this.rel = null;
				break;
			case REL:
				this.abs = null;
				this.rel = TimeInterval.readFromParcel(in);
				break;
			default:
				this.abs = null;
				this.rel = null;
			}
		plogger.trace("unmarshall time trigger {}", this);
	}

	/**
	 * The cv returns an absolute time value in milliseconds.
	 * This time is only a recommendation; it may be that
	 * the distribution policy has set a maximum.
	 * 
	 * @return the absolute expiration time in milliseconds.
	 */
	public long cv() {
		if (this.type == null) {
			return Long.MAX_VALUE;
		} else
			switch (this.type) {
			case ABS:
				return this.abs.cv();
			case REL:
				if (this.rel.cv() < Long.MAX_VALUE) {
					return System.currentTimeMillis() + this.rel.cv();
				} else {
					return Long.MAX_VALUE;
				}
			default:
				return Long.MAX_VALUE;
			}
	}
	// *********************************
	// Standard Methods
	// *********************************
	@Override
	public String toString() {
		if (this.type == null) {
			return "<no type>";
		}
		switch (this.type) {
		case ABS:
			return this.abs.toString();
		case REL:
			return this.rel.toString();
		default:
			return "<unknown type>"+ this.type;
		}
	}

	// *********************************
	// IAmmoReques Support
	// *********************************

	public TimeStamp abs() { return abs; }
	public TimeInterval rel() { return rel; }

	public TimeTrigger(TimeStamp val) {
		this.type = Type.ABS;
		this.abs = val;
		this.rel = null;
	}

	public TimeTrigger(TimeInterval val) {
		this.type = Type.REL;
		this.abs = null;
		this.rel = val;
	}

}
