package edu.vu.isis.ammo.api.type;

import edu.vu.isis.ammo.api.IAmmoRequest;
import android.os.Parcel;
import android.os.Parcelable;

public class Selection implements Parcelable {

    public static final Parcelable.Creator<Selection> CREATOR = 
        new Parcelable.Creator<Selection>() {

        @Override
        public Selection createFromParcel(Parcel source) {
            return new Selection(source);
        }

        @Override
        public Selection[] newArray(int size) {
            return new Selection[size];
        }
    };

    public enum Type { QUERY, FORM; }

    final private Type type;

    final public IAmmoRequest.Query query;
    final public IAmmoRequest.Form form;

    public Selection(String val) {
        this.type = Type.QUERY;
        this.query = new Query(val);
        this.form = null;
    }
    
    public Selection(IAmmoRequest.Query val) {
        this.type = Type.QUERY;
        this.query = val;
        this.form = null;
    }
    public Selection(IAmmoRequest.Form val) {
        this.type = Type.FORM;
        this.query = null;
        this.form = val;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type.ordinal());

        switch (this.type) {
        case FORM:
            dest.writeValue(this.form);
            break;
        case QUERY:
            dest.writeValue(this.query);
            break;
        }
    }

    public Selection(Parcel in) {
        this.type = Type.values()[ in.readInt() ];
        if (this.type == null) {
            this.query = null;
            this.form = null;
            return;
        } 
        switch (this.type) {
        case QUERY:
            this.query = Query.CREATOR.createFromParcel(in);
            this.form = null;
            break;
        case FORM:
            this.query = null;
            this.form = Form.CREATOR.createFromParcel(in);
            break;
        default:
            this.query = null;
            this.form = null;
        }
    }

    public String toString() {
        switch(this.type) {
        case FORM: return this.form.toString();
        case QUERY: return this.query.toString();
        }
        return "<none>";
    }
}