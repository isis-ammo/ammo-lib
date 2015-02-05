/* Copyright (c) 2010-2015 Vanderbilt University
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
	
	//something trivial
	public static enum AMMO_PREF_TYPE {
		STRING, BOOLEAN, FLOAT, INT, LONG
	};
	
}

