package edu.vu.isis.ammo.api.type;

import android.os.Parcel;
import android.os.Parcelable;



public class Topic implements Parcelable { 
	
    /**
     * The notify is used to specify actions to perform
     * as certain places are traversed.
     */
    public static final Parcelable.Creator<Topic> CREATOR = 
        new Parcelable.Creator<Topic>() {

        @Override
        public Topic createFromParcel(Parcel source) {
            return new Topic(source);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }

    };
    
    public enum Type { OID, STR; }
    
    final private Type type;
    final private String str;
    final private Oid oid;
    
    public Topic(String val) {
       this.type = Type.STR;
       this.str = val;
       this.oid = null;
     }
     public Topic(Oid val) {
         this.type = Type.OID;
         this.str = null;
         this.oid = val;
     }
   
    @Override
    public int describeContents() { return 0; }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type.ordinal());
        
       switch (this.type) {
       case OID:
           dest.writeValue(this.oid);
           break;
       case STR:
           dest.writeString(this.str);
           break;
       }
    }
    
    public Topic(Parcel in) {
       this.type = Type.values()[ in.readInt() ];
       if (this.type == null) {
           this.str = null;
           this.oid = null;
       } else
          switch (this.type) {
          case OID:
              this.str = null;
              this.oid = null; // in.readValue();
              break;
          case STR:
              this.str = in.readString();
              this.oid = null;
              break;
          default:
              this.str = null;
              this.oid = null;
          }
    }
    
    public String asString() { 
    	return this.toString();
    }
    
    public String toString() {
   	 switch(this.type) {
        case STR: return this.str;
        case OID: return this.oid.toString();
        }
   	 return "<none>";
    }
}


