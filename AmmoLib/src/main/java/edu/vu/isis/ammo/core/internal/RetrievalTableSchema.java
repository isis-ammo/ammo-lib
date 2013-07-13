
package edu.vu.isis.ammo.core.internal;

import android.provider.BaseColumns;

public enum RetrievalTableSchema {
    _ID(BaseColumns._ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),

    /** This is a unique identifier for the request */
    UUID("uuid", "TEXT"),

    /** When the request was made */
    CREATED("created", "INTEGER"),

    /** When the request was last modified */
    MODIFIED("modified", "INTEGER"),

    /** This is the data type */
    TOPIC("topic", "TEXT"),

    /**
     * This is a unique identifier for the request as specified by the
     * application
     */
    AUID("auid", "TEXT"),

    /** The uri of the content provider */
    PROVIDER("provider", "TEXT"),

    /**
     * A description of what is to be done when various state-transition occur.
     */
    NOTICE("notice", "BLOB"),

    /**
     * What order should this message be sent. Negative priorities indicated
     * less than normal.
     */
    PRIORITY("priority", "INTEGER"),

    /** The fields/columns wanted. */
    PROJECTION("projection", "TEXT"),

    /** The rows/tuples wanted. */
    SELECTION("selection", "TEXT"),

    /** The values using in the selection. */
    ARGS("args", "TEXT"),

    /** The order the values are to be returned in. */
    ORDERING("ordering", "TEXT"),

    /**
     * The maximum number of items to retrieve as items are obtained the count
     * should be decremented
     */
    LIMIT("maxrows", "INTEGER"),

    /** The current best guess of the status of the request. */
    DISPOSITION("disposition", "INTEGER"),

    /** Time-stamp at which request entry becomes stale. */
    EXPIRATION("expiration", "INTEGER"),

    /**
     * Units associated with {@link #VALUE}. Used to determine whether should
     * occur.
     */
    UNIT("unit", "TEXT"),

    /**
     * Arbitrary value linked to importance that entry is transmitted and
     * battery drain.
     */
    VALUE("value", "INTEGER"),

    /**
     * If the If null then the data file corresponding to the column name and
     * record id should be used. This is done when the data size is larger than
     * that allowed for a field contents.
     */
    DATA("data", "TEXT"),

    /**
     * The meaning changes based on the continuity type.
     * <ul>
     * <li>ONCE : undefined
     * <li>TEMPORAL : chronic, this differs slightly from the expiration which
     * deals with the request this deals with the time stamps of the requested
     * objects.
     * <li>QUANTITY : the maximum number of objects to return
     * </ul>
     */
    CONTINUITY_TYPE("continuity_type", "INTEGER"),
    CONTINUITY_VALUE("continuity_value", "INTEGER");

    final public String n; // name
    final public String t; // type

    private RetrievalTableSchema(String name, String type) {
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
};
