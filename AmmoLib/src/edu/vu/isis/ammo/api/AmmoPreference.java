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
package edu.vu.isis.ammo.api;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.vu.isis.ammo.AmmoPreferenceReadOnlyAccess;
import edu.vu.isis.ammo.core.provider.PreferenceSchema;

public class AmmoPreference {
	
	private static AmmoPreference instance = null;
	@SuppressWarnings("unused")
	final private Context mContext;
	final private ContentResolver mContentResolver;
	final private boolean hasPermissionReadWrite;
	
	private AmmoPreference(Context context, ContentResolver contentResolver) {
		mContext = context;
		mContentResolver = contentResolver;
		
		if (context.getApplicationInfo().packageName.startsWith("edu.vu.isis.ammo.core")) {
			hasPermissionReadWrite = true;
		} else {
			hasPermissionReadWrite = false;
		}
	}
	
	public static AmmoPreference getInstance(Context context) { return newInstance(context); }
	public static AmmoPreference newInstance(Context context) {
		if (instance == null) {
			instance = new AmmoPreference(context, context.getContentResolver());
		}
		return instance;
	}
	
	// Returns a string array where values are concatenated from each string element.
	@SuppressWarnings("unused")
	private String[] constructFredProtocolStringArray(String[] defVals, String[] methodTypes) {
		if (defVals.length != methodTypes.length) {
			return null;
		}
		
		String[] result = new String[defVals.length];
		for (int i=0; i<defVals.length; i++) {
			result[i] = methodTypes[i] + ":" + defVals[i];
		}
		
		return result;
	}
	
	// =================================
	// Preference Setters/Getters
	// =================================
	/**
	 * Preference key is stored in projection. 
	 * Preference def value is stored in selectionArgs
	 * Preference type is stored in selection.
	 */
	public String getString(String key, String defaultValue) {
		final String[] projection = {key};
		final String selection = PreferenceSchema.AMMO_PREF_TYPE_STRING;
		final String[] selectionArgs = {defaultValue};
		final String sortOrder = null;

		final Cursor cur = mContentResolver.query(PreferenceSchema.CONTENT_URI, projection, 
				selection, selectionArgs, sortOrder);
		cur.moveToFirst();
		if (cur.getCount() < 1) {
			cur.close();
			return defaultValue;
		}
		if (cur.getColumnIndex(key) < 0) {
			cur.close();
			return defaultValue;
		}
		final String value = cur.getString(cur.getColumnIndex(key));
		cur.close();
		return value;
	}
	
	// Cursors don't support boolean type. Cast as string until value retrieved
	// and then cast back.
	public boolean getBoolean(String key, boolean defaultValue) {
		final String[] projection = {key};
		final String selection = PreferenceSchema.AMMO_PREF_TYPE_BOOLEAN;
		final String[] selectionArgs = {String.valueOf(defaultValue)};
		final String sortOrder = null;
		final Cursor cur = mContentResolver.query(PreferenceSchema.CONTENT_URI, projection, 
				selection, selectionArgs, sortOrder);
		cur.moveToFirst();
		if (cur.getCount() < 1) {
			cur.close();
			return defaultValue;
		}
		if (cur.getColumnIndex(key) < 0) {
			cur.close();
			return defaultValue;
		}
		boolean value = Boolean.valueOf(cur.getString(cur.getColumnIndex(key)));
		cur.close();
		return value;
	}
	
	public long getLong(String key, long defaultValue) {
		String[] projection = {key};
		String selection = PreferenceSchema.AMMO_PREF_TYPE_LONG;
		String[] selectionArgs = {String.valueOf(defaultValue)};
		Cursor cur = mContentResolver.query(PreferenceSchema.CONTENT_URI, projection, 
				selection, selectionArgs, null);
		cur.moveToFirst();
		if (cur.getCount() < 1) {
			cur.close();
			return defaultValue;
		}
		if (cur.getColumnIndex(key) < 0) {
			cur.close();
			return defaultValue;
		}
		long value = cur.getLong(cur.getColumnIndex(key));
		cur.close();
		return value;
	}
	
	public float getFloat(String key, float defaultValue) {
		String[] projection = {key};
		String selection = PreferenceSchema.AMMO_PREF_TYPE_FLOAT;
		String[] selectionArgs = {String.valueOf(defaultValue)};
		Cursor cur = mContentResolver.query(PreferenceSchema.CONTENT_URI, projection, 
				selection, selectionArgs, null);
		cur.moveToFirst();
		if (cur.getCount() < 1) {
			cur.close();
			return defaultValue;
		}
		if (cur.getColumnIndex(key) < 0) {
			cur.close();
			return defaultValue;
		}
		float value = cur.getFloat(cur.getColumnIndex(key));
		cur.close();
		return value;
	}
	
	public int getInt(String key, int defaultValue) {
		String[] projection = {key};
		String selection = PreferenceSchema.AMMO_PREF_TYPE_INT;
		String[] selectionArgs = {String.valueOf(defaultValue)};
		Cursor cur = mContentResolver.query(PreferenceSchema.CONTENT_URI, projection, 
				selection, selectionArgs, null);
		cur.moveToFirst();
		if (cur.getCount() < 1) {
			cur.close();
			return defaultValue;
		}
		if (cur.getColumnIndex(key) < 0) {
			cur.close();
			return defaultValue;
		}
		int value = cur.getInt(cur.getColumnIndex(key));
		cur.close();
		return value;
	}
	
	public void putString(String key, String value) throws AmmoPreferenceReadOnlyAccess {
		if (!hasPermissionReadWrite) {
			throw new AmmoPreferenceReadOnlyAccess();
		}
		ContentValues vals = new ContentValues();
		vals.put(key, value);
		String[] selectionArgs = {key};
		mContentResolver.update(PreferenceSchema.CONTENT_URI, vals, PreferenceSchema.AMMO_PREF_TYPE_STRING, selectionArgs);
	}
	
	public void putBoolean(String key, boolean value) throws AmmoPreferenceReadOnlyAccess {
		if (!hasPermissionReadWrite) {
			throw new AmmoPreferenceReadOnlyAccess();
		}
		ContentValues vals = new ContentValues();
		vals.put(key, value);
		String[] selectionArgs = {key};
		mContentResolver.update(PreferenceSchema.CONTENT_URI, vals, PreferenceSchema.AMMO_PREF_TYPE_BOOLEAN, selectionArgs);
	}
	
	public void putInt(String key, int value) throws AmmoPreferenceReadOnlyAccess {
		if (!hasPermissionReadWrite) {
			throw new AmmoPreferenceReadOnlyAccess();
		}
		ContentValues vals = new ContentValues();
		vals.put(key, value);
		String[] selectionArgs = {key};
		mContentResolver.update(PreferenceSchema.CONTENT_URI, vals, PreferenceSchema.AMMO_PREF_TYPE_INT, selectionArgs);
	}
	
	public void putLong(String key, long value) throws AmmoPreferenceReadOnlyAccess {
		if (!hasPermissionReadWrite) {
			throw new AmmoPreferenceReadOnlyAccess();
		}
		ContentValues vals = new ContentValues();
		vals.put(key, value);
		String[] selectionArgs = {key};
		mContentResolver.update(PreferenceSchema.CONTENT_URI, vals, PreferenceSchema.AMMO_PREF_TYPE_LONG, selectionArgs);
	}
	
	public void putFloat(String key, float value) throws AmmoPreferenceReadOnlyAccess {
		if (!hasPermissionReadWrite) {
			throw new AmmoPreferenceReadOnlyAccess();
		}
		ContentValues vals = new ContentValues();
		vals.put(key, value);
		String[] selectionArgs = {key};
		mContentResolver.update(PreferenceSchema.CONTENT_URI, vals, PreferenceSchema.AMMO_PREF_TYPE_FLOAT, selectionArgs);
	}
}
