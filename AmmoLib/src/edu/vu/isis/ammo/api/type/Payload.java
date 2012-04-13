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

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;


public class Payload extends AmmoType { 

	public enum Type { NONE, STR, BYTE, CV; }

	final private Type type;
	final private String str;
	final private byte[] bytes;
	final private ContentValues cv;

	// *********************************
	// Parcelable Support
	// *********************************

	public static final Parcelable.Creator<Payload> CREATOR = 
			new Parcelable.Creator<Payload>() {

		@Override
		public Payload createFromParcel(Parcel source) {
			return new Payload(source);
		}

		@Override
		public Payload[] newArray(int size) {
			return new Payload[size];
		}
	};
	
	public static final String DEFAULT = "";
	
	public static Payload readFromParcel(Parcel source) {
		if (AmmoType.isNull(source)) return null;
		return new Payload(source);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		plogger.trace("marshall payload {}", this);
		dest.writeInt(this.type.ordinal());

		switch (this.type) {
		case CV:
			this.cv.writeToParcel(dest, flags); 
			return;
		case BYTE:
			dest.writeByteArray(this.bytes);
			return;
		case STR:
			dest.writeString(this.str);
			return;
		}
	}

	public Payload(Parcel in) {
		this.type = Type.values()[ in.readInt() ];
		if (this.type == null) {
			this.str = null;
			this.bytes = null;
			this.cv = null;
		} else
			switch (this.type) {
			case CV:
				this.str = null;
				this.bytes = null;
				this.cv = ContentValues.CREATOR.createFromParcel(in);
				break;
			case BYTE:
				this.str = null;
				this.bytes = in.createByteArray();
				this.cv = null;
				break;
			case STR:
				this.str = in.readString();
				this.bytes = null;
				this.cv = null;
				break;
			default:
				this.str = null;
				this.bytes = null;
				this.cv = null;
			}
		plogger.trace("unmarshall payload");
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
		case CV:
			return this.cv.toString();
		case BYTE: 
			return this.bytes.toString();
		case STR:
			return this.str.toString();

		default:
			return "<unknown type>"+ this.type;
		}
	}

	// *********************************
	// IAmmoRequest Support
	// *********************************

	public Payload(String val) {
		this.type = Type.STR;
		this.str = val;
		this.bytes = null;
		this.cv = null;
	}
	public Payload(byte[] val) {
		this.type = Type.BYTE;
		this.str = null;
		this.bytes = val;
		this.cv = null;
	}
	public Payload(ContentValues val) {
		this.type = Type.CV;
		this.str = null;
		this.bytes = null;
		this.cv = val;
	}

	public byte[] asBytes() {
		switch (this.type){
		case BYTE: return this.bytes;
		case STR: return this.str.getBytes();
		}
		return null;
	}

	public String asString() {
		switch (this.type){
		case BYTE: return new String(this.bytes);
		case STR: return this.str;
		}
		return null;
	}

	public boolean hasContent() {
		switch (this.type){
		case STR: return (this.str != null && this.str.length() > 0);
		case BYTE: return (this.bytes != null);
		}
		return false;
	}
}
