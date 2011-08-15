package edu.vu.isis.ammo.api.type;

import edu.vu.isis.ammo.api.IAmmoRequest;
import edu.vu.isis.ammo.api.IAmmoRequest.Event;
import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;

public class Notice implements IAmmoRequest.Notice, Parcelable {

    /**
     * The notify is used to specify actions to perform
     * as certain places are traversed.
     */
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

    @SuppressWarnings("unused")
	private final PendingIntent intent;

    // *********************************
    // Parcelable Support
    // *********************************

    private Notice(Parcel in) {
        this.intent = null;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

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
