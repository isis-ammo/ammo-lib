package edu.vu.isis.ammo.core.provider;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

public class PreferenceSchema {
	public static final String AUTHORITY = "edu.vu.isis.ammo.core.provider.preferenceprovider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/preference");

	public static final String AMMO_PREF_CHANGED_ACTION = "edu.vu.isis.ammo.preferencechanged";
	public static final Intent AMMO_PREF_CHANGED_INTENT = new Intent(AMMO_PREF_CHANGED_ACTION);
	public static final IntentFilter AMMO_PREF_CHANGED_INTENT_FILTER = new IntentFilter(AMMO_PREF_CHANGED_ACTION);
	public static final String AMMO_INTENT_KEY_PREF_CHANGED_KEY = "ammo_intent_key_pref_changed_key";
	public static final String AMMO_INTENT_KEY_PREF_CHANGED_VALUE = "ammo_intent_key_pref_changed_value";
	
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

