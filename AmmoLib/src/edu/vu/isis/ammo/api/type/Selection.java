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

public class Selection extends AmmoType {
	
	static final public Selection RESET = null;


	static final private int STRING_ID = 0;
	static final private int QUERY_ID = 1;
	static final private int FORM_ID = 2;
	
	public enum Type { 
		STRING(STRING_ID), QUERY(QUERY_ID), FORM(FORM_ID); 
		final public int id;
		private Type(final int id) { this.id = id; }
		static public Type getInstance(final int id) {
			switch (id) {
			case STRING_ID: return STRING;
			case QUERY_ID: return QUERY;
			case FORM_ID: return FORM;
			}
			return null;
		}
	};

	final private Type type;

	final public String string;
	final public Query query;
	final public Form form;

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
		dest.writeInt(this.type.id);

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
		this.type = Type.getInstance(in.readInt());
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

	public Selection(Query val) {
		this.type = Type.QUERY;
		this.string = null;
		this.query = val;
		this.form = null;
	}
	public Selection(Form val) {
		this.type = Type.FORM;
		this.string = null;
		this.query = null;
		this.form = val;
	}

}