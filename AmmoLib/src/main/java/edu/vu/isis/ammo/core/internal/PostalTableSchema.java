
package edu.vu.isis.ammo.core.internal;

import android.provider.BaseColumns;

public enum PostalTableSchema {
    _ID(BaseColumns._ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),

    /** This is a unique identifier for the request */
    UUID("uuid", "TEXT"),

    /** When the request was made */
    CREATED("created", "INTEGER"),

    /** When the request was last modified */
    MODIFIED("modified", "INTEGER"),

    /**
     * This along with the cost is used to decide how to deliver the specific
     * object.
     */
    TOPIC("topic", "TEXT"),

    /**
     * This specifies a forced channel be used and no other. NULL indicates that
     * the normal processing to select a channel be used.
     */
    CHANNEL("channel", "TEXT"),

    /**
     * (optional) The appplication specific unique identifier This is used in
     * notice intents so the application can relate.
     */
    AUID("auid", "TEXT"),

    /** The uri of the content provider */
    PROVIDER("provider", "TEXT"),

    /**
     * The payload instead of content provider Very similar to DATA maybe these
     * should be combined.
     */
    PAYLOAD("payload", "BLOB"),

    /**
     * If null then the data file corresponding to the column name and record id
     * should be used. This is done when the data size is larger than that
     * allowed for a field contents.
     */
    DATA("data", "BLOB"),

    /** The current best guess of the status of the request. */
    DISPOSITION("disposition", "INTEGER"),

    /**
     * A description of what is to be done when various state-transition occur.
     */
    NOTICE("notice", "BLOB"),

    /**
     * Controls the order this message be sent. Negative priorities indicated
     * less than normal.
     */
    PRIORITY("priority", "INTEGER"),

    /** ? */
    ORDER("serialize_type", "INTEGER"),

    /** Time-stamp at which point entry becomes stale. */
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
    WORTH("value", "INTEGER");

    /** the well known name */
    final public String n;
    /** the data type */
    final public String t;

    private PostalTableSchema(String n, String t) {
        this.n = n;
        this.t = t;
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
