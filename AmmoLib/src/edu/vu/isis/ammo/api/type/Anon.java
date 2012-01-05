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
