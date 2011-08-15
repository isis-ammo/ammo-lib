package edu.vu.isis.ammo.api.type;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


public class Provider implements Parcelable { 
    
    public static final Parcelable.Creator<Provider> CREATOR = 
        new Parcelable.Creator<Provider>() {

        @Override
        public Provider createFromParcel(Parcel source) {
            return new Provider(source);
        }
        @Override
        public Provider[] newArray(int size) {
            return new Provider[size];
        }
    };
    
     public enum Type { URI; }
     
     final private Type type;
     final private Uri uri;
     
     public Provider(Uri val) {
        this.type = Type.URI;
        this.uri = val;
      }
     
     @Override
     public int describeContents() { return 0; }
     @Override
     public void writeToParcel(Parcel dest, int flags) {
         dest.writeInt(this.type.ordinal());
         
        switch (this.type) {
        case URI:
            dest.writeValue(this.uri);
            break;
        }
     }
     
     public Provider(Parcel in) {
        this.type = Type.values()[ in.readInt() ];
        if (this.type == null) {
            this.uri = null;
        } else
           switch (this.type) {
           case URI:
               this.uri = in.readParcelable(Uri.class.getClassLoader());
               break;
           default:
               this.uri = null;
           }
     }
     
     public byte[] asBytes() {
         switch (this.type){
         case URI: return this.uri.toString().getBytes();
         }
         return null;
     }
     
     public String asString() {
         switch (this.type){
         case URI: return this.uri.toString();
         }
         return null;
     }
     
     public Uri asUri() {
         switch (this.type){
         case URI: return this.uri;
         }
         return null;
     }
 }
