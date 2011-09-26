package edu.vu.isis.ammo.api.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * It is expected that there will be a CREATOR static variable 
 * for each child of this class and that it will be of the form...
 * 
 * public static final Parcelable.Creator<Anon> CREATOR = 
			new Parcelable.Creator<Anon>() {

		@Override
		public Anon createFromParcel(Parcel source) {
			if (AmmoType.isNull(source)) return null;
			return new X(source);
		}
		@Override
		public Anon[] newArray(int size) {
			return new X[size];
		}
	};
 * Where 'X' is the name of the child class.
 * Note the null check which matches up with the null check in 'writeToParcel' below.
 * 
 * Typically when a null value is allowed the following pair would be used.
 * 
 * parcel.writeValue(parcelable, flags);
 * and
 * parcelable = parcel.readParcelable(ParcelableType.class.getClassLoader());
 * but these introduce a bunch of extra stuff that I thought we could do without.
 *
 */
public abstract class AmmoType implements Parcelable {
	protected static final Logger plogger = LoggerFactory.getLogger( "ammo-parcel" );

	// *********************************
	// Parcelable Support
	// *********************************

	static public boolean isNull(Parcel source) {
		return (source.readInt() == 0) ? true : false;
	}
	
    /**
	* Note the null check which matches up with the null check in 'createFromParcel' 
    * mentioned above and implemented in each child class.
	*/
	static public void writeToParcel(AmmoType that, Parcel dest, int flags) {
		if (that == null) {
			dest.writeInt( 0 );
			return;
		}
		dest.writeInt( 1 );
		that.writeToParcel(dest, flags);
	}

	@Override
	public int describeContents() { return 0; }
}
