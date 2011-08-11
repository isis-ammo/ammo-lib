package edu.vu.isis.ammo.api.type;

import android.os.Parcel;
import android.os.Parcelable;

public class DeliveryScope implements Parcelable {
    public enum Type {
        GLOBAL, LOCAL;
    }
  
    final private Type type;
    
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
    
   
    private DeliveryScope(Parcel in) {
        this.type = Type.values()[in.readInt()];
    }
    
    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type.ordinal());
    }

}
