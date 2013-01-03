/*Copyright (C) 2010-2012 Institute for Software Integrated Systems (ISIS)
This software was developed by the Institute for Software Integrated
Systems (ISIS) at Vanderbilt University, Tennessee, USA for the 
Transformative Apps program under DARPA, Contract # HR011-10-C-0175.
The United States Government has unlimited rights to this software. 
The US government has the right to use, modify, reproduce, release, 
perform, display, or disclose computer software or computer software 
documentation in whole or in part, in any manner and for any 
purpose whatsoever, and to have or authorize others to do so.
 */

package edu.vu.isis.ammo.api.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Parcel;
import android.os.Parcelable;

public class Selection extends AmmoType {
    static final Logger logger = LoggerFactory.getLogger("type.selection");

    static final public Selection RESET = null;

    static final private int STRING_ID = 0;
    static final private int QUERY_ID = 1;
    static final private int FORM_ID = 2;

    public enum Type {
        STRING(STRING_ID), QUERY(QUERY_ID), FORM(FORM_ID);
        final public int id;

        private Type(final int id) {
            this.id = id;
        }

        static public Type getInstance(final int id) {
            switch (id) {
                case STRING_ID:
                    return STRING;
                case QUERY_ID:
                    return QUERY;
                case FORM_ID:
                    return FORM;
            }
            return null;
        }
    };

    final private Type type;

    final public String string;
    final public Query query;
    final public Form form;

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Selection> CREATOR =
            new Parcelable.Creator<Selection>() {

                @Override
                public Selection createFromParcel(Parcel source) {
                    if (AmmoType.isNull(source))
                        return null;
                    return new Selection(source);
                }

                @Override
                public Selection[] newArray(int size) {
                    return new Selection[size];
                }
            };

    public static Selection readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        return new Selection(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall select {}", this);
        dest.writeInt(this.type.id);

        switch (this.type) {
            case STRING:
                dest.writeValue(this.string);
                return;
            case FORM:
                Form.writeToParcel((Form) this.form, dest, flags);
                return;
            case QUERY:
                Selection.writeToParcel((Query) this.query, dest, flags);
                return;
        }
    }

    public Selection(Parcel in) {
        this.type = Type.getInstance(in.readInt());
        if (this.type == null) {
            this.string = null;
            this.query = null;
            this.form = null;
        } else
            switch (this.type) {
                case STRING:
                    this.string = in.readString();
                    this.query = null;
                    this.form = null;
                    break;
                case QUERY:
                    this.string = null;
                    this.query = Query.readFromParcel(in);
                    this.form = null;
                    break;
                case FORM:
                    this.string = null;
                    this.query = null;
                    this.form = Form.readFromParcel(in);
                    break;
                default:
                    this.string = null;
                    this.query = null;
                    this.form = null;
            }
        plogger.trace("unmarshall select {}", this);
    }

    // *********************************
    // Standard Methods
    // *********************************
    @Override
    public String toString() {

        if (this.type == null) {
            return "<no type>";
        }
        // final StringBuilder sb = new StringBuilder();
        switch (this.type) {
            case STRING:
                if (this.string == null)
                    return "<null string>";
                return this.string;
            case QUERY:
                if (this.query == null)
                    return "<null query>";
                return this.query.toString();
            case FORM:
                if (this.form == null)
                    return "<null form>";
                return this.form.toString();
            default:
                return "<unknown type>" + this.type;
        }
    }

    // *********************************
    // IAmmoReques Support
    // *********************************

    public Selection(String val) {
        this.type = Type.STRING;
        this.string = val;
        this.query = null;
        this.form = null;
    }

    public String cv() {
        switch (this.type) {
            case STRING:
                return this.string;
            case FORM:
            case QUERY:
            default:
                break;
        }
        return "";
    }

    public Selection(Query val) {
        this.type = Type.QUERY;
        this.string = null;
        this.query = val;
        this.form = null;
    }

    public Selection(Form val) {
        this.type = Type.FORM;
        this.string = null;
        this.query = null;
        this.form = val;
    }

    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Selection))
            return false;
        final Selection that = (Selection) obj;
        if (AmmoType.differ(this.type, that.type))
            return false;

        switch (this.type) {
            case STRING:
                if (AmmoType.differ(this.string, that.string))
                    return false;
                return true;
            case FORM:
                if (AmmoType.differ(this.form, that.form))
                    return false;
                return true;
            case QUERY:
                if (AmmoType.differ(this.query, that.query))
                    return false;
                return true;
            default:
                break;
        }
        return true;
    }

    @Override
    public synchronized int hashCode() {
        if (!this.dirtyHashcode.getAndSet(false))
            return this.hashcode;
        this.hashcode = AmmoType.HashBuilder.newBuilder()
                .increment(this.type)
                .increment(this.string)
                .increment(this.form)
                .increment(this.query)
                .hashCode();
        return this.hashcode;
    }

    @Override
    public String asString() {
        logger.error("asString() not implemented");
        return null;
    }

}
