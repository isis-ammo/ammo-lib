package edu.vu.isis.ammo.util;

public interface BaseDateColumns {
    /**
     * The timestamp when the row was registered with the server.
     * <P>Type: INTEGER (long)</P>
     * from System.currentTimeMillis()
     */
    public static final String CREATED_DATE = "created_date";

    /**
     * Timestamp when a the row was last modified
     * <P>Type: INTEGER (long)</P>
     * from System.currentTimeMillis()
     */
    public static final String MODIFIED_DATE = "modified_date";

}
