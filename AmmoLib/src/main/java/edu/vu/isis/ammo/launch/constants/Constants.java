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
package edu.vu.isis.ammo.launch.constants;

/**
 * Static constants used throughout the application.
 * @author Demetri Miller
 *
 */

/*
 * IMPORTANT NOTE: The mimetype constants must match those in AmmoSource.java
 * in the Contacts app
 */

public class Constants {
	/**
	 * Key to use when storing uris in intents.
	 */
	public static final String ROW_URI = "row_uri";

	/**
	 * Intent to load indiviual contact.
	 */
	public static final String INDIVIDUAL_CONTACT_ACTIVITY_LAUNCH = "edu.vu.isis.ammo.launch.individualcontactactivity.LAUNCH";
	
	public static final String AMMO_ACCOUNT_TYPE = "edu.vu.isis.ammo";
	public static final String AMMO_DEFAULT_ACCOUNT_NAME = "ammo";
    public static final String AMMO_AUTHTOKEN_TYPE = "edu.vu.isis.ammo";
    
    public static final String LDAP_MIME = "ammo/edu.vu.isis.ammo.launcher.contact_pull";
	
	public static final String MIME_INSIGNIA = "vnd.android.cursor.item/insignia";
	public static final String MIME_CALLSIGN = "vnd.android.cursor.item/callsign";
	public static final String MIME_USERID = "vnd.android.cursor.item/userid";
	public static final String MIME_USERID_NUM = "vnd.android.cursor.item/usernumber";
	public static final String MIME_RANK = "vnd.android.cursor.item/rank";
	public static final String MIME_UNIT_NAME = "vnd.android.cursor.item/unitname";
	public static final String MIME_DESIGNATOR = "vnd.android.cursor.item/designator";
	public static final String MIME_BRANCH = "vnd.android.cursor.item/branch";
}
