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

import edu.vu.isis.ammo.api.IncompleteRequest;
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
			try {
				return new Topic(source);
			} catch (IncompleteRequest ex) {
				return null;
			}
		}

		@Override
		public Topic[] newArray(int size) {
			return new Topic[size];
		}
	};

	public static Topic readFromParcel(Parcel source) {
		if (AmmoType.isNull(source)) return null;
		try {
			return new Topic(source);
		} catch (IncompleteRequest ex) {
			return null;
		}
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

	public Topic(Parcel in) throws IncompleteRequest {
		int ordinal = -1;
		try {
			ordinal = in.readInt();

			this.type = Type.values()[ ordinal ];
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

		} catch (ArrayIndexOutOfBoundsException ex) {
			plogger.error("bad topic {} {}", ordinal, ex);
			throw new IncompleteRequest(ex);
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


