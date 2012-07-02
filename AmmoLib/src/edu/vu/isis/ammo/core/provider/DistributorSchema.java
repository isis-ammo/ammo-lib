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

import java.util.EnumMap;
import java.util.Map;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import edu.vu.isis.ammo.util.BaseDateColumns;

/**
@code{
   // SAMPLE QUERY
   Cursor presenceCursor = null;
   try {
      Uri presenceUri = DistributorSchema.CONTENT_URI.get(Relations.PRESENCE);
      String selection = new StringBuilder().append(PresenceSchema.OPERATOR).append("=?").toString() ;
      String[] selectionArgs = {cache.callsignBuffer.toString()};
      presenceCursor = mContext.getContentResolver().query(presenceUri, null, 
                             selection, selectionArgs, null);
      presenceCursor.moveToFirst();
      String originDevice = presenceCursor.getString(presenceCursor.getColumnIndex(PresenceSchema.ORIGIN.field));
   } finally {
      if (presenceCursor != null) {
         presenceCursor.close();
      }
   }
}
 */

public class DistributorSchema implements BaseColumns, BaseDateColumns {

	public static final String AUTHORITY = "edu.vu.isis.ammo.core.provider.distributor";

	/**
	 * The content:// style URL for these tables
	 * This Uri is a Map containing all table Uris contained in the distributor database.
	 * The map is indexed by Relations.<TABLE_NAME>.n where n is the 
	 * name of the enum declared in Tables.
	 * 
	 * e.g. 
	 * final Uri presenceUri = DistributorSchema.CONTENT_URI.get(Relations.PRESENCE);
	 */
	public static final Map<Relations, Uri> CONTENT_URI;
	static {
		CONTENT_URI = new EnumMap<Relations, Uri>(Relations.class);
		for (Relations table : Relations.values()) {
			CONTENT_URI.put(table, Uri.parse("content://"+AUTHORITY+"/"+table.n));
		}
	}

	/**
	 * Special URI that when queried, returns a cursor to the number of 
	 * queued messages per channel (where each channel is in a separate row)
	 */  

	/**
	 * The MIME type of {@link #CONTENT_URI} providing a directory
	 */
	public static final String CONTENT_TYPE =
			ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.edu.vu.isis.ammo.core.distributor";

	/**
	 * A mime type used for publisher subscriber.
	 */
	public static final String CONTENT_TOPIC =
			"ammo/edu.vu.isis.ammo.core.distributor";

	/**
	 * The MIME type of a {@link #CONTENT_URI} sub-directory of a single media entry.
	 */
	public static final String CONTENT_ITEM_TYPE = 
			ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.edu.vu.isis.ammo.core.distributor";

	public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";


}



