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
 * Specifies the order in which queued items are to be processed.
 */
public class Order extends AmmoType {
	
	static final public Order RESET = null;

	static final private int OLDEST_FIRST_ID = 1;
	static final private int NEWEST_ONLY_ID = 2;
	static final private int NEWEST_FIRST_ID = 3;
	
    public enum Type {
    	OLDEST_FIRST(OLDEST_FIRST_ID, "Oldest First"),
    	NEWEST_ONLY(NEWEST_ONLY_ID, "Newest Only"),
    	NEWEST_FIRST(NEWEST_FIRST_ID, "Newest First");

    	private final int id;
    	private final String d;
    	
    	private Type(int id, String description) {
    		this.id = id;
    		this.d = description;
    	}
    	
    	static public Type getInstance(int id) {
    		switch (id) {
    		case OLDEST_FIRST_ID: return OLDEST_FIRST;
    		case NEWEST_ONLY_ID: return NEWEST_ONLY;
    		case NEWEST_FIRST_ID: return NEWEST_FIRST;
    		}
    		return null;
    	}
    }
    

    final private Type type;
    
   	public int cv() {
		return this.type.id;
	}
  
    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Order> CREATOR = 
        new Parcelable.Creator<Order>() {

        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
    public static Order readFromParcel(Parcel source) {
    	if (AmmoType.isNull(source)) return null;
        return new Order(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	plogger.trace("marshall order {}", this);
        dest.writeInt(this.type.id);
    }

    private Order(Parcel in) {
        this.type = Type.getInstance(in.readInt());
    	plogger.trace("unmarshall order []", this);
    }

	// *********************************
    // IAmmoRequest Support
    // *********************************
	
    public Order(String val) {
        if (val.contains("L")) { this.type = Type.NEWEST_FIRST; return; }
        if (val.contains("1")) { this.type = Type.NEWEST_ONLY; return; }
        if (val.contains("F")) { this.type = Type.OLDEST_FIRST; return; }
        this.type = Type.OLDEST_FIRST;
    }
    
    public Order(Type val) {
        this.type = val;
    }
    
    public Type type() { return this.type; }
    
    final public static Order NEWEST_FIRST = new Order(Type.NEWEST_FIRST);
    final public static Order NEWEST_ONLY = new Order(Type.NEWEST_ONLY);
    final public static Order OLDEST_FIRST = new Order(Type.OLDEST_FIRST);
    
    @Override
    public String toString() {
    	return new StringBuilder().append(this.type.d).toString();
    }
    
}
