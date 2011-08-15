package edu.vu.isis.ammo.api.type;

import android.os.Parcel;
import android.os.Parcelable;

public class StartTime implements Parcelable {
	
    public static final Parcelable.Creator<StartTime> CREATOR = 
        new Parcelable.Creator<StartTime>() {

        @Override
        public StartTime createFromParcel(Parcel source) {
            return new StartTime(source);
        }

        @Override
        public StartTime[] newArray(int size) {
            return new StartTime[size];
        }

    };
    
     public enum Type { ABS, REL; }
     
    private Type type;
    public Type type() { return type; }

    final public TimeStamp abs;
    final public TimeInterval rel;
    
    public TimeStamp abs() { return abs; }
    public TimeInterval rel() { return rel; }
    
    public StartTime(TimeStamp val) {
       this.type = Type.ABS;
      this.abs = val;
      this.rel = null;
    }
    public StartTime(TimeInterval val) {
        this.type = Type.REL;
       this.abs = null;
       this.rel = val;
     }
  
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type.ordinal());
        switch (this.type) {
        case ABS:
            this.abs.writeToParcel(dest, flags);
            break;
        case REL:
            this.rel.writeToParcel(dest, flags);
            break;
        }
    }
    
    public StartTime(Parcel in) {
        this.type = Type.values()[ in.readInt() ];
        if (this.type == null) {
            this.abs = null;
            this.rel = null;
        } else 
            switch (this.type) {
            case ABS:
                this.abs = TimeStamp.CREATOR.createFromParcel(in);
                this.rel = null;
                break;
            case REL:
                this.abs = null;
                this.rel = TimeInterval.CREATOR.createFromParcel(in);
                break;
            default:
                this.abs = null;
                this.rel = null;
            }
    }
}
