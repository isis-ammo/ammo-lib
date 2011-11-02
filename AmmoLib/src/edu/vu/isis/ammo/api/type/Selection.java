package edu.vu.isis.ammo.api.type;

import edu.vu.isis.ammo.api.IAmmoRequest;
import android.os.Parcel;
import android.os.Parcelable;

public class Selection extends AmmoType {

	public enum Type { STRING, QUERY, FORM; }

	final private Type type;

	final public String string;
	final public IAmmoRequest.Query query;
	final public IAmmoRequest.Form form;

	// *********************************
	// Parcelable Support
	// *********************************

	public static final Parcelable.Creator<Selection> CREATOR = 
			new Parcelable.Creator<Selection>() {

		@Override
		public Selection createFromParcel(Parcel source) {
			if (AmmoType.isNull(source)) return null;
			return new Selection(source);
		}

		@Override
		public Selection[] newArray(int size) {
			return new Selection[size];
		}
	};
	
	public static Selection readFromParcel(Parcel source) {
		if (AmmoType.isNull(source)) return null;
		return new Selection(source);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		plogger.trace("marshall select {}", this);
		dest.writeInt(this.type.ordinal());

		switch (this.type) {
		case STRING:
			dest.writeValue(this.string);
			return;
		case FORM:
			Form.writeToParcel((Form)this.form, dest, flags);
			return;
		case QUERY:
			Query.writeToParcel((Query)this.query, dest, flags);
			return;
		}
	}

	public Selection(Parcel in) {
		this.type = Type.values()[ in.readInt() ];
		if (this.type == null) {
			this.string = null;
			this.query = null;
			this.form = null;
		} else
			switch (this.type) {
			case STRING:
				this.string = in.readString();
				this.query = null;
				this.form = null;
				break;
			case QUERY:
				this.string = null;
				this.query = Query.readFromParcel(in);
				this.form = null;
				break;
			case FORM:
				this.string = null;
				this.query = null;
				this.form = Form.readFromParcel(in);
				break;
			default:
				this.string = null;
				this.query = null;
				this.form = null;
			}
		plogger.trace("unmarshall select {}", this);
	}
	// *********************************
	// Standard Methods
	// *********************************
	@Override
	public String toString() {
		
		if (this.type == null) {
			return "<no type>";
		} 
		// final StringBuilder sb = new StringBuilder();
		switch (this.type) {
		case STRING:
			if (this.string == null) return "<null string>";
			return this.string;
		case QUERY:
			if (this.query == null) return "<null query>";
			return this.query.toString();
		case FORM:
			if (this.form == null) return "<null form>";
			return this.form.toString();
		default:
			return "<unknown type>"+ this.type;
		}
	}

	// *********************************
	// IAmmoReques Support
	// *********************************

	public Selection(String val) {
		this.type = Type.STRING;
		this.string = val;
		this.query = null;
		this.form = null;
	}

	public Selection(IAmmoRequest.Query val) {
		this.type = Type.QUERY;
		this.string = null;
		this.query = val;
		this.form = null;
	}
	public Selection(IAmmoRequest.Form val) {
		this.type = Type.FORM;
		this.string = null;
		this.query = null;
		this.form = val;
	}

}