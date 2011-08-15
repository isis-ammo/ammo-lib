package edu.vu.isis.ammo.api.type;

import java.util.HashMap;

import android.os.Parcel;
import android.os.Parcelable;
import edu.vu.isis.ammo.api.IAmmoRequest;

/**
 * The form is used to convey some selection of objects.
 */

public class Form extends HashMap<String, String> implements IAmmoRequest.Form, Parcelable {
	
    private static final long serialVersionUID = 4787609325728657052L;
    
    public static final Parcelable.Creator<Form> CREATOR = 
        new Parcelable.Creator<Form>() {

        @Override
        public Form createFromParcel(Parcel source) {
            return new Form(source);
        }

        @Override
        public Form[] newArray(int size) {
            return new Form[size];
        }

    };

    // *********************************
    // Parcelable Support
    // *********************************
   
    private Form(Parcel in) {
        // in.readMap(this, loader)
    }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(this);
    }
    
    // *********************************
    // IAmmoReques Support
    // *********************************

    public Form() {
        super();
    }
    
}
