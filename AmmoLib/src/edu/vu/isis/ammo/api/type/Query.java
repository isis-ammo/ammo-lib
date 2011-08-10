package edu.vu.isis.ammo.api.type;

import android.os.Parcel;
import android.os.Parcelable;
import edu.vu.isis.ammo.api.IAmmoRequest;

// *********************************
// Support Classes
// *********************************

/**
 * The query is used to convey some selection of objects.
 */

public class Query implements IAmmoRequest.Query, Parcelable {
	
	public static final Parcelable.Creator<Query> CREATOR = 
	    new Parcelable.Creator<Query>() {

	    @Override
	    public Query createFromParcel(Parcel source) {
	        return new Query(source);
	    }

	    @Override
	    public Query[] newArray(int size) {
	        return new Query[size];
	    }

	};
	
    final private String select;
    final private String[] args;

    // *********************************
    // Parcelable Support
    // *********************************
   
    private Query(Parcel in) {
        this.select = in.readString();
        this.args = in.createStringArray();
    }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.select);
        dest.writeStringArray(this.args);
    }
    
    // *********************************
    // IAmmoReques Support
    // *********************************

    public Query(String select, String[] args) {
        this.select = select;
        this.args = args;
    }

    public Query(String select) {
        this.select = select;
        this.args = null;
    }

    public String select() {
        return this.select;
    }

    public String[] args() {
        return this.args();
    }

    public Query args(String[] args) {
        return new Query(this.select, args);
    }
}

