package edu.vu.isis.ammo.api.type;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;


public class Payload implements Parcelable { 
    
    public static final Parcelable.Creator<Payload> CREATOR = 
        new Parcelable.Creator<Payload>() {

        @Override
        public Payload createFromParcel(Parcel source) {
            return new Payload(source);
        }

        @Override
        public Payload[] newArray(int size) {
            return new Payload[size];
        }

    };
    
     public enum Type { NONE, STR, BYTE, CV; }
     
     final private Type type;
     final private String str;
     final private byte[] bytes;
     final private ContentValues cv;
     
     public Payload(String val) {
        this.type = Type.STR;
        this.str = val;
        this.bytes = null;
        this.cv = null;
      }
      public Payload(byte[] val) {
          this.type = Type.BYTE;
          this.str = null;
          this.bytes = val;
          this.cv = null;
      }
      public Payload(ContentValues val) {
          this.type = Type.CV;
          this.str = null;
          this.bytes = null;
          this.cv = val;
      }
    
     @Override
     public int describeContents() { return 0; }
     
     @Override
     public void writeToParcel(Parcel dest, int flags) {
         dest.writeInt(this.type.ordinal());
         
        switch (this.type) {
        case CV:
            this.cv.writeToParcel(dest, flags); 
            break;
        case BYTE:
            dest.writeByteArray(this.bytes);
            break;
        case STR:
            dest.writeString(this.str);
            break;
        }
     }
     
     public Payload(Parcel in) {
        this.type = Type.values()[ in.readInt() ];
        if (this.type == null) {
            this.str = null;
            this.bytes = null;
            this.cv = null;
        } else
           switch (this.type) {
           case CV:
               this.str = null;
               this.bytes = null;
               this.cv = ContentValues.CREATOR.createFromParcel(in);
               break;
           case BYTE:
               this.str = null;
               this.bytes = in.createByteArray();
               this.cv = null;
               break;
           case STR:
               this.str = in.readString();
               this.bytes = null;
               this.cv = null;
               break;
           default:
               this.str = null;
               this.bytes = null;
               this.cv = null;
           }
     }
     
     public byte[] asBytes() {
         switch (this.type){
         case BYTE: return this.bytes;
         case STR: return this.str.getBytes();
         }
         return null;
     }
     
     public String asString() {
         switch (this.type){
         case BYTE: return new String(this.bytes);
         case STR: return this.str;
         }
         return null;
     }
 }
