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

public class Quantifier extends AmmoType { 

	public enum Type { 
		SINGLE, 
		ROOM, 
		ALL,
		BULLETIN,
		; }

	final private Type type;

	// *********************************
	// Parcelable Support
	// *********************************

	public static final Parcelable.Creator<Quantifier> CREATOR = 
			new Parcelable.Creator<Quantifier>() {

		@Override
		public Quantifier createFromParcel(Parcel source) {
			return new Quantifier(source);
		}

		@Override
		public Quantifier[] newArray(int size) {
			return new Quantifier[size];
		}
	};
	
	public static Quantifier readFromParcel(Parcel source) {
		if (AmmoType.isNull(source)) return null;
		return new Quantifier(source);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		plogger.trace("marshall topic {}", this);
		dest.writeInt(this.type.ordinal());

		
	}

	public Quantifier(Parcel in) {
		this.type = Type.values()[ in.readInt() ];
		
		plogger.trace("unmarshall topic {}", this);
	}
	// *********************************
	// Standard Methods
	// *********************************
	@Override
	public String toString() {
		if (this.type == null) {
			return "<no type>";
		}
		return this.type.name();
	}

	// *********************************
	// IAmmoReques Support
	// *********************************

	public Quantifier(String val) {
		if (val.equalsIgnoreCase(Type.SINGLE.name())) {
			this.type = Type.SINGLE;
		} else
		if (val.equalsIgnoreCase(Type.ROOM.name())) {
			this.type = Type.ROOM;
		} else
		if (val.equalsIgnoreCase(Type.BULLETIN.name())) {
			this.type = Type.BULLETIN;
		} else
		if (val.equalsIgnoreCase(Type.ALL.name())) {
			this.type = Type.ALL;
		} else {
			this.type = Type.ALL;
		}
	}
	

	public String asString() { 
		return this.toString();
	}

}


