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

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


public class Provider extends AmmoType {

	static final public Provider RESET = null;

	static final private int URI_ID = 0;

	public enum Type { 
		URI(URI_ID); 

		final public int id;
		private Type(int id) {
			this.id = id;
		}
		static public Type getInstance(int id) {
			switch(id) {
			case URI_ID: return URI;
			}
			return null;
		}
	}

	final private Type type;
	final private Uri uri;

	public Provider(Uri val) {
		this.type = Type.URI;
		this.uri = val;
	}

	public String cv() {
		if (this.type == null) {
			return null;
		}
			
		switch(this.type) {
		case URI:
			if (this.uri == null) {
				return null;
			}
			return this.uri.toString();
		}
		return null;
	}

	// *********************************
	// Parcelable Support
	// *********************************

	public static final Parcelable.Creator<Provider> CREATOR = 
			new Parcelable.Creator<Provider>() {

		@Override
		public Provider createFromParcel(Parcel source) {
			return new Provider(source);
		}
		@Override
		public Provider[] newArray(int size) {
			return new Provider[size];
		}
	};
	public static Provider readFromParcel(Parcel source) {
		if (AmmoType.isNull(source)) return null;
		return new Provider(source);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		plogger.trace("marshall provider {}", this);
		dest.writeInt(this.type.id);

		switch (this.type) {
		case URI:
			Uri.writeToParcel(dest, this.uri);
			return;
		}
	}

	public Provider(Parcel in) {
		this.type = Type.getInstance(in.readInt());
		if (this.type == null) {
			this.uri = null;
		} else
			switch (this.type) {
			case URI:
				this.uri = Uri.CREATOR.createFromParcel(in);
				break;
			default:
				this.uri = null;
			}
		plogger.trace("unmarshall provider {}", this);
	}
	// *********************************
	// Standard Methods
	// *********************************

	public Provider(String val) {
		this.type = Type.URI;
		this.uri = Uri.parse(val);
	}

	@Override
	public String toString() {
		if (this.type == null) {
			return "<no type>";
		} 
		switch (this.type) {
		case URI:
			return this.uri.toString();
		default:
			return "<unknown type>"+ this.type;
		}
	}

	// *********************************
	// IAmmoRequest Support
	// *********************************

	public final static Uri DEFAULT = Uri.parse("");

	public byte[] asBytes() {
		switch (this.type){
		case URI: return this.uri.toString().getBytes();
		}
		return null;
	}

	public String asString() {
		switch (this.type){
		case URI: return this.uri.toString();
		}
		return null;
	}

	public Uri asUri() {
		switch (this.type){
		case URI: return this.uri;
		}
		return null;
	}
}
