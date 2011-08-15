package edu.vu.isis.ammo.api.type;

import android.os.Parcel;
import android.os.Parcelable;
import edu.vu.isis.ammo.api.IAmmoRequest;


/**
 * The anon is used to convey the recipient 
 * to whom the request is directed
 * or from whom it originates.
 */

public class Anon implements IAmmoRequest.IAnon, Parcelable {
	
	public static final Parcelable.Creator<Anon> CREATOR = 
	    new Parcelable.Creator<Anon>() {

	    @Override
	    public Anon createFromParcel(Parcel source) {
	        return new Anon(source);
	    }
	    @Override
	    public Anon[] newArray(int size) {
	        return new Anon[size];
	    }
	};

    // *********************************
    // Parcelable Support
    // *********************************

    private Anon(Parcel in) {
    	this.str = in.readString();
    }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	dest.writeString(this.str);
    }
    
    // *********************************
    // IAmmoRequest Support
    // *********************************

    public Anon(String val) {
    	this.str = val;
    }
    
    
    final private String str;
    
    @Override
    public String name() {
        return this.str;
    }
    

}
