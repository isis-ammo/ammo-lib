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



