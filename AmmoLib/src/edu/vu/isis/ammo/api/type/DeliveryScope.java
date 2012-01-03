/*
Copyright(c) 2010-2012

This software was developed by the Institute for Software Integrated
Systems (ISIS) at Vanderbilt University, Tennessee, USA for the 
Transformative Apps program under contract [contract citation, subcontract and prime contract]. 
The United States Government has unlimited rights to this software. 
The US government has the right to use, modify, reproduce, release, 
perform, display, or disclose computer software or computer software 
documentation in whole or in part, in any manner and for any 
purpose whatsoever, and to have or authorize others to do so.

 */

package edu.vu.isis.ammo.api.type;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This identifies how far the message is to diffuse.
 */
public class DeliveryScope extends AmmoType {

    public enum Type {
        GLOBAL, LOCAL;
    }
  
    final private Type type;
    
    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<DeliveryScope> CREATOR = 
        new Parcelable.Creator<DeliveryScope>() {

        @Override
        public DeliveryScope createFromParcel(Parcel source) {
            return new DeliveryScope(source);
        }

        @Override
        public DeliveryScope[] newArray(int size) {
            return new DeliveryScope[size];
        }
    };
    public static DeliveryScope readFromParcel(Parcel source) {
    	if (AmmoType.isNull(source)) return null;
        return new DeliveryScope(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	plogger.trace("marshall dscope {}", this);
        dest.writeInt(this.type.ordinal());
    }

    private DeliveryScope(Parcel in) {
        this.type = Type.values()[in.readInt()];
    	plogger.trace("unmarshall dscope []", this);
    }

	// *********************************
    // IAmmoRequest Support
    // *********************************
	
    public DeliveryScope(String val) {
        this.type = (val.contains("G") || val.contains("g")) 
            ? Type.GLOBAL
            : Type.LOCAL;
    }
    
    public DeliveryScope(Type val) {
        this.type = val;
    }
    
    public Type type() { return this.type; }
    
    final public static DeliveryScope GLOBAL = new DeliveryScope(Type.GLOBAL);
    final public static DeliveryScope LOCAL = new DeliveryScope(Type.LOCAL);
    
    @Override
    public String toString() {
    	final StringBuilder sb = new StringBuilder();
    	switch (this.type) {
    	case GLOBAL: sb.append("Global"); break;
    	case LOCAL: sb.append("Local"); break;
    	default:
    	sb.append("<unknown>");
    	}
    	return sb.toString();
    }
    
}
