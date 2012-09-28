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

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
/**
 * When the application wishes to get a broadcast intent
 * this parameter is used.
 *
 */
public class BroadIntent extends AmmoType {

    final private Intent intent;

    public BroadIntent(Intent val) {
        this.intent = val;
    }

    public String cv() {
        throw new UnsupportedOperationException();
        // return ""; // intent.cv();
    }

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<BroadIntent> CREATOR =
            new Parcelable.Creator<BroadIntent>() {

                @Override
                public BroadIntent createFromParcel(Parcel source) {
                    return new BroadIntent(source);
                }

                @Override
                public BroadIntent[] newArray(int size) {
                    return new BroadIntent[size];
                }
            };

    public static BroadIntent readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        return new BroadIntent(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall provider {}", this);
                dest.writeParcelable(this, flags);
    }

    public BroadIntent(Parcel in) {
        this.intent = in.readParcelable(null);
        plogger.trace("unmarshall provider {}", this);
    }

    // *********************************
    // Standard Methods
    // *********************************

    @Override
    public String toString() {
       return "";
           //     return this.uri.toString();
    }

    // *********************************
    // IAmmoRequest Support
    // *********************************


    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof BroadIntent))
            return false;
        final BroadIntent that = (BroadIntent) obj;
        return this.intent.equals(that.intent);
    }

    @Override
    public synchronized int hashCode() {
        if (!this.dirtyHashcode.getAndSet(false))
            return this.hashcode;
        this.hashcode = this.intent.hashCode();
        return this.hashcode;
    }

    public String asString() {
       
        return null;
    }

}
