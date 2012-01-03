package edu.vu.isis.ammo.api.type;

import android.os.Parcel;
import android.os.Parcelable;
import edu.vu.isis.ammo.api.IAmmoRequest;

/**
 * The query is used to convey some selection of objects.
 */

public class Query extends AmmoType implements IAmmoRequest.Query {

	final private String select;
	final private String[] args;

	// *********************************
	// Parcelable Support
	// *********************************

	public static final Parcelable.Creator<Query> CREATOR = 
			new Parcelable.Creator<Query>() {

		@Override
		public Query createFromParcel(Parcel source) {
			return new Query(source);
		}

		@Override
		public Query[] newArray(int size) {
			return new Query[size];
		}
	};
	public static Query readFromParcel(Parcel source) {
		if (AmmoType.isNull(source)) return null;
		return new Query(source);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		plogger.trace("marshall query {}", this);
		dest.writeString(this.select);
		dest.writeStringArray(this.args);
	}

	private Query(Parcel in) {
		this.select = in.readString();
		this.args = in.createStringArray();
		plogger.trace("unmarshall query {}", this);
	}
	// *********************************
	// Standard Methods
	// *********************************
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.select);
		if (this.args != null) 
			sb.append(" args ").append(this.args);
		return sb.toString();
	}

	// *********************************
	// IAmmoReques Support
	// *********************************

	public Query(String select, String[] args) {
		this.select = select;
		this.args = args;
	}

	public Query(String select) {
		this.select = select;
		this.args = null;
	}

	public String select() {
		return this.select;
	}

	public String[] args() {
		return this.args();
	}

	public Query args(String[] args) {
		return new Query(this.select, args);
	}
}

