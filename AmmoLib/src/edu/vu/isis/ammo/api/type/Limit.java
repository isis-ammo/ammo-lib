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
 * Specifies the order in which queued items are to be processed.
 */
public class Limit extends AmmoType {

	public int count;
	
    public enum Type {
    	OLDEST(1, "Oldest"),
    	NEWEST(2, "Newest");

    	public final int o;
    	public final String d;
    	
    	private Type(int ordinal, String description) {
    		this.o = ordinal;
    		this.d = description;
    	}
    }
    
    final private Type type;
    
   	public int cv() {
		return this.count;
	}
  
    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Limit> CREATOR = 
        new Parcelable.Creator<Limit>() {

        @Override
        public Limit createFromParcel(Parcel source) {
            return new Limit(source);
        }

        @Override
        public Limit[] newArray(int size) {
            return new Limit[size];
        }
    };
    public static Limit readFromParcel(Parcel source) {
    	if (AmmoType.isNull(source)) return null;
        return new Limit(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	plogger.trace("marshall order {}", this);
        dest.writeInt(this.type.ordinal());
        dest.writeInt(this.count);
    }

    private Limit(Parcel in) {
        this.type = Type.values()[in.readInt()];
        this.count = in.readInt();
    	plogger.trace("unmarshall limit []", this);
    }

	// *********************************
    // IAmmoRequest Support
    // *********************************
	
    public Limit(String val) {
    	this.count = 1;
        if (val.startsWith("O")) { this.type = Type.OLDEST; return; }
        this.type = Type.NEWEST;
    }
    
    public Limit(Type type, int count) {
        this.type = type;
        this.count = count;
    }
    
    public Limit(int count) {
        this.type = Type.NEWEST;
        this.count = count;
    }
    
    public Limit() {
        this.type = Type.NEWEST;
        this.count = -1;
    }
    
    public Type type() { return this.type; }
    
    final public static Limit NEWEST = new Limit(Type.NEWEST, 1);
    final public static Limit OLDEST = new Limit(Type.OLDEST, 1);
    
    @Override
    public String toString() {
    	return new StringBuilder().append(this.type.d).toString();
    }

	public Integer asInteger() {
		switch (this.type) {
		case OLDEST: return -(this.count);
		case NEWEST: 
		default: return (this.count);
		}
	}
    
}
