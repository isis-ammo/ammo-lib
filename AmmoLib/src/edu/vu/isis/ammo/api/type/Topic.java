/*
Copyright(c) 2010-2012

This software was developed by the Institute for Software Integrated
Systems (ISIS) at Vanderbilt University, Tennessee, USA for the 
Transformative Apps program under contract [contract citation, subcontract and prime contract]. 
The United States Government has unlimited rights to this software. 
The US government has the right to use, modify, reproduce, release, 
perform, display, or disclose computer software or computer software 
documentation in whole or in part, in any manner and for any 
purpose whatsoever, and to have or authorize others to do so.

 */

package edu.vu.isis.ammo.api.type;

import android.os.Parcel;
import android.os.Parcelable;

public class Topic extends AmmoType { 

	public enum Type { OID, STR; }

	final private Type type;
	final private String str;
	final private Oid oid;

	// *********************************
	// Parcelable Support
	// *********************************

	public static final Parcelable.Creator<Topic> CREATOR = 
			new Parcelable.Creator<Topic>() {

		@Override
		public Topic createFromParcel(Parcel source) {
			return new Topic(source);
		}

		@Override
		public Topic[] newArray(int size) {
			return new Topic[size];
		}
	};
	
	public static Topic readFromParcel(Parcel source) {
		if (AmmoType.isNull(source)) return null;
		return new Topic(source);
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

	public Topic(Parcel in) {
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

	public Topic(String val) {
		this.type = Type.STR;
		this.str = val;
		this.oid = null;
	}
	public Topic(Oid val) {
		this.type = Type.OID;
		this.str = null;
		this.oid = val;
	}

	public String asString() { 
		return this.toString();
	}

}


