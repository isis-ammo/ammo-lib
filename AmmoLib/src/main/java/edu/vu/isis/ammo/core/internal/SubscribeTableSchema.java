
package edu.vu.isis.ammo.core.internal;

import android.provider.BaseColumns;

public enum SubscribeTableSchema {
    _ID(BaseColumns._ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),

    /** This is a unique identifier for the request */
    UUID("uuid", "TEXT"),

    /** When the request was made */
    CREATED("created", "INTEGER"),

    /** When the request was last modified */
    MODIFIED("modified", "INTEGER"),

    /** The data type of the objects being subscribed to */
    TOPIC("topic", "TEXT"),

    /** The application UUID for the request */
    AUID("auid", "TEXT"),

    /** The uri of the content provider */
    PROVIDER("provider", "TEXT"),

    /** The current best guess of the status of the request. */
    DISPOSITION("disposition", "INTEGER"),

    /** Time-stamp at which request entry becomes stale. */
    EXPIRATION("expiration", "INTEGER"),

    /** The rows/tuples wanted. */
    SELECTION("selection", "TEXT"),

    /**
     * A description of what is to be done when various state-transition occur.
     */
    NOTICE("notice", "BLOB"),

    /**
     * What order should this message be sent. Negative priorities indicated
     * less than normal.
     */
    PRIORITY("priority", "INTEGER");

    final public String n; // name
    final public String t; // type

    private SubscribeTableSchema(String name, String type) {
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
