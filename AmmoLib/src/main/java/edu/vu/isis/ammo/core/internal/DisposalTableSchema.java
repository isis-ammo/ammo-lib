
package edu.vu.isis.ammo.core.internal;

import android.provider.BaseColumns;

public enum DisposalTableSchema {
    _ID(BaseColumns._ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),

    /** Meaning the parent type: subscribe, retrieval, postal */
    TYPE("type", "INTEGER"),

    /** The _id of the parent */
    PARENT("parent", "INTEGER"),

    /** The name of the channel over which the message could be sent */
    CHANNEL("channel", "TEXT"),

    /** Where the request is on the channel */
    STATE("state", "INTEGER");

    /** the well known name */
    final public String n;
    /** the data type */
    final public String t;

    private DisposalTableSchema(String name, String type) {
        this.n = name;
        this.t = type;
    }

    /**
     * Produce string of the form... "<field-name>" <field-type> e.g. "dog" TEXT
     */
    public String addfield() {
        return new StringBuilder()
                .append('"').append(this.n).append('"').append(' ').append(this.t)
                .toString();
    }

    /**
     * Produce string of the form... "<field-name>"
     */
    public String q() {
        return new StringBuilder()
                .append('"').append(this.n).append('"')
                .toString();
    }

    public String cv() {
        return String.valueOf(this.n);
    }
}
