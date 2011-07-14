package edu.vu.isis.ammo.api;

import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeStamp implements Parcelable {

    final private long millis;

    final private TimeInterval interval;

    public TimeStamp() {
        this.millis = System.currentTimeMillis();
        this.interval = new TimeInterval(0);
    }
    
    public TimeStamp(Calendar cal) {
    	this.millis = cal.getTimeInMillis();
    	this.interval = new TimeInterval(0);
    }
    
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
    
    private TimeStamp(Parcel in) {
    	this.millis = in.readLong();
    	this.interval = TimeInterval.CREATOR.createFromParcel(in);
    }
    
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.millis);
		this.interval.writeToParcel(dest, flags);
	}
}
