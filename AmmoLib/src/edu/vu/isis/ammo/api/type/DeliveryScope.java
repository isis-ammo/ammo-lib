package edu.vu.isis.ammo.api.type;

import android.os.Parcel;
import android.os.Parcelable;

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
