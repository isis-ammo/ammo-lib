package edu.vu.isis.ammo.api.type;

import android.os.Parcel;
import android.os.Parcelable;
import edu.vu.isis.ammo.api.IAmmoRequest;

/**
 * The anon is used to convey the recipient 
 * to whom the request is directed
 * or from whom it originates.
 */

public class Anon extends AmmoType implements IAmmoRequest.IAnon {

	final private String str;

	// *********************************
	// Parcelable Support
	// *********************************

	public static final Parcelable.Creator<Anon> CREATOR = 
			new Parcelable.Creator<Anon>() {

		@Override
		public Anon createFromParcel(Parcel source) {
			return new Anon(source);
		}
		@Override
		public Anon[] newArray(int size) {
			return new Anon[size];
		}
	};
	public static Anon readFromParcel(Parcel source) {
		if (AmmoType.isNull(source)) return null;
		return new Anon(source);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		plogger.trace("marshall anon {}", this);
		dest.writeString(this.str);
	}

	private Anon(Parcel in) {
		this.str = in.readString();
		plogger.trace("unmarshall anon {}", this);
	}

	// *********************************
	// IAmmoRequest Support
	// *********************************

	public Anon(String val) {
		this.str = val;
	}

	@Override
	public String name() {
		return this.str;
	}
	
	@Override
    public String toString() {
    	return this.str;
    }


}
