package edu.vu.isis.ammo.api.type;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import edu.vu.isis.ammo.api.IAmmoRequest;
import edu.vu.isis.ammo.api.IAmmoRequest.Event;

/**
 * The notify is used to specify actions to perform
 * as certain places are traversed.
 */

public class Notice extends AmmoType implements IAmmoRequest.Notice {

	private final PendingIntent intent;

	// *********************************
	// Parcelable Support
	// *********************************

	public static final Parcelable.Creator<Notice> CREATOR = 
			new Parcelable.Creator<Notice>() {

		@Override
		public Notice createFromParcel(Parcel source) {
			return new Notice(source);
		}
		@Override
		public Notice[] newArray(int size) {
			return new Notice[size];
		}
	};
	
	public Notice readFromParcel(Parcel source) {
		if (AmmoType.isNull(source)) return null;
		return new Notice(source);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		plogger.trace("marshall notice {}", this);
		this.intent.writeToParcel(dest, flags);
	}

	private Notice(Parcel in) {
		this.intent = PendingIntent.CREATOR.createFromParcel(in);
		plogger.trace("unmarshall notice {}", this);
	}

	// *********************************
	// Standard Methods
	// *********************************
	@Override
	public String toString() {
		return this.intent.toString();
	}

	// *********************************
	// IAmmoReques Support
	// *********************************


	private Notice(PendingIntent intent) {
		this.intent = intent;
	}

	@Override
	public boolean act() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object action() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Notice action(Object val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event source() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Notice source(Event val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event target() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Notice target(Event val) {
		// TODO Auto-generated method stub
		return null;
	}
}
