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

import edu.vu.isis.ammo.api.IAmmoRequest.Event;
import edu.vu.isis.ammo.api.IAmmoRequest.INotice;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Specifies the Notice in which queued items are to be processed.
 */
public class Notice extends AmmoType implements INotice {

    public enum Type {
      	NONE(0, "no action"),
      	SENT(1, "Pre serialized"),
    	ARCHIVED(2, "stored long term"),
    	RECEIVED(3, "received by a subscriber");
    	
    	private final int o;
    	private final String d;
    	
    	private Type(int ordinal, String description) {
    		this.o = ordinal;
    		this.d = description;
    	}
    }
    

    final private Type type;
    
   	public int cv() {
		return this.type.o;
	}
  
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
    public static Notice readFromParcel(Parcel source) {
    	if (AmmoType.isNull(source)) return null;
        return new Notice(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	plogger.trace("marshall Notice {}", this);
        dest.writeInt(this.type.ordinal());
    }

    private Notice(Parcel in) {
        this.type = Type.values()[in.readInt()];
    	plogger.trace("unmarshall Notice []", this);
    }

	// *********************************
    // IAmmoRequest Support
    // *********************************
	
    public Notice(String val) {
    	if (val.contains("N")) { this.type = Type.NONE; return; }
        if (val.contains("S")) { this.type = Type.SENT; return; }
        if (val.contains("R")) { this.type = Type.RECEIVED; return; }
        if (val.contains("A")) { this.type = Type.ARCHIVED; return; }
        this.type = Type.NONE;
    }
    
    public Notice(Type val) {
        this.type = val;
    }
    
    public Type type() { return this.type; }
    
    final public static Notice NONE = new Notice(Type.NONE);
    final public static Notice SENT = new Notice(Type.SENT);
    final public static Notice RECEIVED = new Notice(Type.RECEIVED);
    final public static Notice ARCHIVED = new Notice(Type.ARCHIVED);
    
    @Override
    public String toString() {
    	return new StringBuilder().append(this.type.d).toString();
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

}

