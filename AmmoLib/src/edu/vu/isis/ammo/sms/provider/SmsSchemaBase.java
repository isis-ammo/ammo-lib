// THIS IS GENERATED CODE, MAKE SURE ANY CHANGES MADE HERE ARE PROPAGATED INTO THE GENERATOR TEMPLATES
package edu.vu.isis.ammo.sms.provider;

import android.net.Uri;
import android.provider.BaseColumns;
import android.content.ContentResolver;
import android.database.Cursor;

public abstract class SmsSchemaBase {
   public static final String AUTHORITY = "edu.vu.isis.ammo.sms.provider.smsprovider";

   public static final String DATABASE_NAME = "sms.db";

   public static final int _DISPOSITION_START = 0;
   public static final int _DISPOSITION_SEND = 1;
   public static final int _DISPOSITION_RECV = 2;
   public static final int _DISPOSITION_COMPLETE = 3;

   protected SmsSchemaBase() {}

// BEGIN CUSTOM Sms CONSTANTS
// END   CUSTOM Sms CONSTANTS

public static final String[] MESSAGE_CURSOR_COLUMNS = new String[] {
  MessageTableSchemaBase.FROM ,
     MessageTableSchemaBase.TO ,
     MessageTableSchemaBase.THREAD ,
     MessageTableSchemaBase.PAYLOAD 
};

public static class MessageTableSchemaBase implements BaseColumns {
   protected MessageTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/message");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(MessageTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.edu.vu.isis.ammo.sms.message";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.edu.vu.isis.ammo.sms.message";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single message entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.edu.vu.isis.ammo.sms.message";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: Who the message is from.
      * <P>Type: TEXT</P> 
      */
          public static final String FROM = "from";
      
      /** 
      * Description: Who the message is to.
      * <P>Type: TEXT</P> 
      */
          public static final String TO = "to";
      
      /** 
      * Description: The message thread id of a conversation
      * <P>Type: LONG</P> 
      */
          public static final String THREAD = "thread";
      
      /** 
      * Description: The content of the message.
      * <P>Type: TEXT</P> 
      */
          public static final String PAYLOAD = "payload";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM MESSAGE_SCHEMA PROPERTIES
// END   CUSTOM MESSAGE_SCHEMA PROPERTIES
} 


} 
