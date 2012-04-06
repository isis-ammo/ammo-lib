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

/**
 * Specifies the Moment in which queued items are to be processed.
 */
public class Moment extends AmmoType {

    public enum Type {
    	LAZY(1, "Wait until the last moment"),
    	EAGER(2, "Serialize as soon as possible"),
    	APRIORI(3, "Pre serialized");

    	private final int o;
    	private final String d;
    	
    	private Type(int ordinal, String description) {
    		this.o = ordinal;
    		this.d = description;
    	}
    }
    

    final private Type type;
    
    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Moment> CREATOR = 
        new Parcelable.Creator<Moment>() {

        @Override
        public Moment createFromParcel(Parcel source) {
            return new Moment(source);
        }

        @Override
        public Moment[] newArray(int size) {
            return new Moment[size];
        }
    };
    public static Moment readFromParcel(Parcel source) {
    	if (AmmoType.isNull(source)) return null;
        return new Moment(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	plogger.trace("marshall Moment {}", this);
        dest.writeInt(this.type.ordinal());
    }

    private Moment(Parcel in) {
        this.type = Type.values()[in.readInt()];
    	plogger.trace("unmarshall Moment []", this);
    }

	// *********************************
    // IAmmoRequest Support
    // *********************************
	
    public Moment(String val) {
        if (val.startsWith("A")) { this.type = Type.APRIORI; return; }
        if (val.startsWith("E")) { this.type = Type.EAGER; return; }
        if (val.startsWith("L")) { this.type = Type.LAZY; return; }
        this.type = Type.LAZY;
    }
    
	public int cv() {
		return this.type.o;
	}
  
    public Moment(int cv) {
    	if (cv == Type.APRIORI.o) { this.type = Type.APRIORI; return; }
    	if (cv == Type.EAGER.o) { this.type = Type.EAGER; return; }
    	if (cv == Type.LAZY.o) { this.type = Type.LAZY; return; }
        this.type = Type.LAZY;
    }
    
    public Moment(Type val) {
        this.type = val;
    }
    
    public Type type() { return this.type; }
    
    final public static Moment APRIORI = new Moment(Type.APRIORI);
    final public static Moment EAGER = new Moment(Type.EAGER);
    final public static Moment LAZY = new Moment(Type.LAZY);
    
    @Override
    public String toString() {
    	return new StringBuilder().append(this.type.d).toString();
    }
    
}

