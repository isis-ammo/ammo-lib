package edu.vu.isis.ammo.api.type;

import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeStamp extends AmmoType {

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
		if (AmmoType.isNull(source)) return null;
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

}
