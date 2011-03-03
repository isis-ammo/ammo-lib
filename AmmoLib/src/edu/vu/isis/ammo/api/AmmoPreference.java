package edu.vu.isis.ammo.api;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.vu.isis.ammo.core.provider.PreferenceSchema;

public class AmmoPreference {
	
	private static AmmoPreference instance = null;
	final private Context mContext;
	final private ContentResolver mContentResolver;
	
	private AmmoPreference(Context context, ContentResolver contentResolver) {
		mContext = context;
		mContentResolver = contentResolver;
	}
	
	public static AmmoPreference getInstance(Context context) {
		if (instance == null) {
			instance = new AmmoPreference(context, context.getContentResolver());
		}
		return instance;
	}
	
	// Returns a string array where values are concatenated from each string element.
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
		String[] projection = {key};
		String selection = PreferenceSchema.AMMO_PREF_TYPE_STRING;
		String[] selectionArgs = {defaultValue};
		String sortOrder = null;

		Cursor cur = mContentResolver.query(PreferenceSchema.CONTENT_URI, projection, 
				selection, selectionArgs, sortOrder);
		cur.moveToFirst();
		String value = cur.getString(cur.getColumnIndex(key));
		cur.close();
		return value;
	}
	
	// Cursors don't support boolean type. Cast as string until value retrieved
	// and then cast back.
	public boolean getBoolean(String key, boolean defaultValue) {
		String[] projection = {key};
		String selection = PreferenceSchema.AMMO_PREF_TYPE_BOOLEAN;
		String[] selectionArgs = {String.valueOf(defaultValue)};
		String sortOrder = null;
		Cursor cur = mContentResolver.query(PreferenceSchema.CONTENT_URI, projection, 
				selection, selectionArgs, sortOrder);
		cur.moveToFirst();
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
		int value = cur.getInt(cur.getColumnIndex(key));
		cur.close();
		return value;
	}
	
	public void putString(String key, String value) {
		ContentValues vals = new ContentValues();
		vals.put(key, value);
		String[] selectionArgs = {key};
		mContentResolver.update(PreferenceSchema.CONTENT_URI, vals, PreferenceSchema.AMMO_PREF_TYPE_STRING, selectionArgs);
	}
	
	public void putBoolean(String key, boolean value) {
		ContentValues vals = new ContentValues();
		vals.put(key, value);
		String[] selectionArgs = {key};
		mContentResolver.update(PreferenceSchema.CONTENT_URI, vals, PreferenceSchema.AMMO_PREF_TYPE_BOOLEAN, selectionArgs);
	}
	
	public void putInt(String key, int value) {
		ContentValues vals = new ContentValues();
		vals.put(key, value);
		String[] selectionArgs = {key};
		mContentResolver.update(PreferenceSchema.CONTENT_URI, vals, PreferenceSchema.AMMO_PREF_TYPE_INT, selectionArgs);
	}
	
	public void putLong(String key, long value) {
		ContentValues vals = new ContentValues();
		vals.put(key, value);
		String[] selectionArgs = {key};
		mContentResolver.update(PreferenceSchema.CONTENT_URI, vals, PreferenceSchema.AMMO_PREF_TYPE_LONG, selectionArgs);
	}
	
	public void putFloat(String key, float value) {
		ContentValues vals = new ContentValues();
		vals.put(key, value);
		String[] selectionArgs = {key};
		mContentResolver.update(PreferenceSchema.CONTENT_URI, vals, PreferenceSchema.AMMO_PREF_TYPE_FLOAT, selectionArgs);
	}
	
	
}
