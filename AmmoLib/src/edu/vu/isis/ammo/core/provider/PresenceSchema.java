/*
Copyright (C) 2010-2012 Institute for Software Integrated Systems (ISIS)
This software was developed by the Institute for Software Integrated
Systems (ISIS) at Vanderbilt University, Tennessee, USA for the 
Transformative Apps program under DARPA, Contract # HR011-10-C-0175.
The United States Government has unlimited rights to this software. 
The US government has the right to use, modify, reproduce, release, 
perform, display, or disclose computer software or computer software 
documentation in whole or in part, in any manner and for any 
purpose whatsoever, and to have or authorize others to do so.
 */
package edu.vu.isis.ammo.core.provider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This provider exposes presence information about other operators.
 * Presence indicates the last visible activity from the operators.
 * 
 * Usage:
 * 
 * // acquire content resolver
 * final ContentResolver cr = getContentResolver(); 
 * static final String select = new StringBuilder()
 *          .append(Identity.STATUS_q).append(" =?")
 *          .toString();
 * 
 * final Cursor cursor = cr.query(PresenceSchema.Capability.CONTENT_URI, 
 *          select, new String[]{String.valueOf(Identity.STATUS_AVAILABLE)},
 *          null, null);
 *      
 *
 */
public class PresenceSchema {
	public static final String AUTHORITY = "edu.vu.isis.ammo.core.provider.presence";
	
	/**
	 * Quote the field name suitably for inclusion in SQL
	 */
	private static String q(final String v) {
		return new StringBuilder().append('"').append(v).append('"').toString();
	}
	
    abstract public static class Identity implements BaseColumns {
    	/** 
		 * The unique identifier for the interest
		 * <P>Type: TEXT</P> 
		 */
		public static final String UID = "uid";
		public static final String UID_q = q(UID);
		
		/** 
		 * The operator identifier
		 * <P>Type: TEXT</P> 
		 */
		public static final String OPERATOR = "operator";
		public static final String OPERATOR_q = q(OPERATOR);
		/** 
		 * The universally unique device identifier
		 * <P>Type: TEXT</P> 
		 */
		public static final String DEVICE = "device";
		public static final String DEVICE_q = q(DEVICE);
		
		/** 
		 * The timestamp for the latest/creation subscription activity.
		 * <P>Type: TIMESTAMP</P> 
		 */
		public static final String LATEST = "latest";
		public static final String CREATION = "creation";
		public static final String LATEST_q = q(LATEST);
		public static final String CREATION_q = q(CREATION);
		

    }
	/**
	 * Capability indicates the interests of the operators.
	 *
	 */
	public static class Capability extends Identity {
		protected Capability() {} 

		/**
		 * The content:// style URL for this table
		 */
		public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/capablity");

		public static Uri getUri(Cursor cursor) {
			final Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
			return  Uri.withAppendedPath(Capability.CONTENT_URI, id.toString());
		}

		/**
		 * The MIME type of {@link #CONTENT_URI} providing a directory
		 */
		public static final String CONTENT_TYPE =
				ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.edu.vu.isis.ammo.presence.capability";

		/**
		 * The MIME type of a {@link #CONTENT_URI} sub-directory of a single media entry.
		 */
		public static final String CONTENT_ITEM_TYPE =
				ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.edu.vu.isis.ammo.presence.capability";

		public static final String DEFAULT_SORT_ORDER = ""; 

		// ========= Field Name and Type Constants ================
		/**
		 * The unique keys for this relation are:
		 * PK : UID
		 * 
		 * CK : DEVICE + TOPIC + SUBTOPIC + CHANNEL
		 * 
		 */
		
		
		/** 
		 * The topic and subtopic indicating the items of interest.
		 * <P>Type: TEXT</P> 
		 */
		public static final String TOPIC = "topic";
		public static final String SUBTOPIC = "subtopic";
		public static final String TOPIC_q = q(TOPIC);
		public static final String SUBTOPIC_q = q(SUBTOPIC);
		
		
		/**
		 * A derived field indicating whether the capability
		 * is still relevant.
		 * 
		 * <P>Type: ENUM</P> 
		 * Allowed values:
		 *  AVAILABLE : highly probable that the operator is still interested
		 *  ACTIVE    : the operator may not be interested or available
		 *  INACTIVE  : the operator has intentionally lost interest
		 */
		public static final String STATUS = "status";
		public static final String STATUS_q = q(STATUS);
		
		public static final int STATUS_AVAILABLE = 1;
		public static final int STATUS_ACTIVE = 2;
		public static final int STATUS_INACTIVE = 3;
	}
	
	/**
	 * Capability indicates the devices visible on the network. 
	 *
	 */
	public static class Device extends Identity {
		protected Device() {} 

		/**
		 * The content:// style URL for this table
		 */
		public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/device");

		public static Uri getUri(Cursor cursor) {
			final Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
			return  Uri.withAppendedPath(Capability.CONTENT_URI, id.toString());
		}

		/**
		 * The MIME type of {@link #CONTENT_URI} providing a directory
		 */
		public static final String CONTENT_TYPE =
				ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.edu.vu.isis.ammo.presence.device";

		/**
		 * The MIME type of a {@link #CONTENT_URI} sub-directory of a single media entry.
		 */
		public static final String CONTENT_ITEM_TYPE =
				ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.edu.vu.isis.ammo.presence.device";

		public static final String DEFAULT_SORT_ORDER = ""; 

		// ========= Field Name and Type Constants ================
		/**
		 * The unique keys for this relation are:
		 * PK : UID
		 * 
		 * CK : DEVICE + CHANNEL
		 * 
		 */

		/** 
		 * The channel showing this activity
		 * <P>Type: TEXT</P> 
		 */
		public static final String CHANNEL = "channel";
		public static final String CHANNEL_q = q(CHANNEL);

		/**
		 * A derived field indicating whether the capability
		 * is still relevant.
		 * 
		 * <P>Type: ENUM</P> 
		 * Allowed values:
		 *  ?
		 */
		public static final String STATUS = "status";
		public static final String STATUS_q = q(STATUS);
	}


}

