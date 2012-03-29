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
	final private String str;
	final private Oid oid;

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

		switch (this.type) {
		case OID:
			this.oid.writeToParcel(dest, flags);
			return;
		case STR:
			dest.writeString(this.str);
			return;
		}
	}

	public Quantifier(Parcel in) {
		this.type = Type.values()[ in.readInt() ];
		if (this.type == null) {
			this.str = null;
			this.oid = null;
		} else
			switch (this.type) {
			case OID:
				this.str = null;
				this.oid = Oid.CREATOR.createFromParcel(in);
				break;
			case STR:
				this.str = in.readString();
				this.oid = null;
				break;
			default:
				this.str = null;
				this.oid = null;
			}
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
		switch (this.type) {
		case OID:
			return this.oid.toString();
		case STR:
			return this.str;
		default:
			return "<unknown type>"+ this.type;
		}
	}

	// *********************************
	// IAmmoReques Support
	// *********************************

	public Quantifier(String val) {
		this.type = Type.STR;
		this.str = val;
		this.oid = null;
	}
	public Quantifier(Oid val) {
		this.type = Type.OID;
		this.str = null;
		this.oid = val;
	}

	public String asString() { 
		return this.toString();
	}

}


