package edu.vu.isis.ammo.api.type;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Specifies the order in which queued items are to be processed.
 */
public class Order extends AmmoType {

    public enum Type {
    	OLDEST_FIRST(1, "Oldest First"),
    	NEWEST_ONLY(2, "Newest Only"),
    	NEWEST_FIRST(3, "Newest First");

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
        dest.writeInt(this.type.ordinal());
    }

    private Order(Parcel in) {
        this.type = Type.values()[in.readInt()];
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
