package edu.vu.isis.ammo.api.type;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


public class Provider extends AmmoType {

	public enum Type { URI; }

	final private Type type;
	final private Uri uri;

	public Provider(Uri val) {
		this.type = Type.URI;
		this.uri = val;
	}
	
	public String cv() {
		return this.uri.toString();
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
		dest.writeInt(this.type.ordinal());

		switch (this.type) {
		case URI:
			Uri.writeToParcel(dest, this.uri);
			return;
		}
	}

	public Provider(Parcel in) {
		this.type = Type.values()[ in.readInt() ];
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
