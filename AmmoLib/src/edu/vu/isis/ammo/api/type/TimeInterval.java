package edu.vu.isis.ammo.api.type;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Time intervals, the an interval of time expressed in a single unit.
 */
public class TimeInterval implements Parcelable {
    public enum Unit {
        MILLISEC, SECOND, MINUTE, HOUR, DAY, MONTH, YEAR
    };
    static public final int UNLIMITED = Integer.MAX_VALUE;

    final private Unit units;
    public Unit unit() {
        return this.units;
    }

    final private long quantity;
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

    public TimeInterval(long seconds) {
        this.units = Unit.SECOND;
        this.quantity = seconds;
    }
    
    
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
    
    private TimeInterval(Parcel in) {
        this.units = Unit.values()[in.readInt()];
        this.quantity = in.readLong();
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.units.ordinal());
		dest.writeLong(this.quantity);
	}

}
