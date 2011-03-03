package edu.vu.isis.ammo.core.provider;

import android.net.Uri;

public class PreferenceSchema {
	public static final String AUTHORITY = "edu.vu.isis.ammo.core.provider.preferenceprovider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/preference");

	// The following constants are used for reflective purposes when 
	// querying the Preference Content Provider for values.
	public static final String AMMO_PREF_TYPE_STRING = "getString";
	public static final String AMMO_PREF_TYPE_BOOLEAN = "getBoolean";
	public static final String AMMO_PREF_TYPE_FLOAT = "getFloat";
	public static final String AMMO_PREF_TYPE_INT = "getInt";
	public static final String AMMO_PREF_TYPE_LONG = "getLong";
	
	public static enum AMMO_PREF_TYPE {
		STRING, BOOLEAN, FLOAT, INT, LONG
	};
}

