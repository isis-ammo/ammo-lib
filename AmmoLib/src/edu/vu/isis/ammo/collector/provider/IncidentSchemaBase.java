// THIS IS GENERATED CODE, MAKE SURE ANY CHANGES MADE HERE ARE PROPAGATED INTO THE GENERATOR TEMPLATES
package edu.vu.isis.ammo.collector.provider;

import android.net.Uri;
import android.provider.BaseColumns;
import android.content.ContentResolver;
import android.database.Cursor;

public abstract class IncidentSchemaBase {
   public static final String AUTHORITY = "edu.vu.isis.ammo.collector.provider.incidentprovider";

   public static final String DATABASE_NAME = "incident.db";

   public static final int _DISPOSITION_START = 0;
   public static final int _DISPOSITION_SEND = 1;
   public static final int _DISPOSITION_RECV = 2;
   public static final int _DISPOSITION_COMPLETE = 3;

   protected IncidentSchemaBase() {}

// BEGIN CUSTOM Incident CONSTANTS
// END   CUSTOM Incident CONSTANTS

public static final String[] MEDIA_CURSOR_COLUMNS = new String[] {
  MediaTableSchemaBase.EVENT_ID ,
     MediaTableSchemaBase.DATA_TYPE, MediaTableSchemaBase.DATA 
};

public static class MediaTableSchemaBase implements BaseColumns {
   protected MediaTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/media");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(MediaTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.edu.vu.isis.ammo.collector.media";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.edu.vu.isis.ammo.collector.media";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single media entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.edu.vu.isis.ammo.collector.media";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: Points to the event to which this belongs.
      * <P>Type: FK</P> 
      */
          public static final String EVENT_ID = "event_id";
      
      /** 
      * Description: The file where the media is stored (and its mime type)
      * <P>Type: FILE</P> 
      */
          public static final String DATA_TYPE = "data_type";
          public static final String DATA = "data";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM MEDIA_SCHEMA PROPERTIES
// END   CUSTOM MEDIA_SCHEMA PROPERTIES
} 
public static final String[] EVENT_CURSOR_COLUMNS = new String[] {
  EventTableSchemaBase.MEDIA_COUNT ,
     EventTableSchemaBase.DISPLAY_NAME ,
     EventTableSchemaBase.CATEGORY_ID ,
     EventTableSchemaBase.TITLE ,
     EventTableSchemaBase.DESCRIPTION ,
     EventTableSchemaBase.LONGITUDE ,
     EventTableSchemaBase.LATITUDE ,
     EventTableSchemaBase.CREATED_DATE ,
     EventTableSchemaBase.MODIFIED_DATE ,
     EventTableSchemaBase.CID ,
     EventTableSchemaBase.CATEGORY ,
     EventTableSchemaBase.UNIT ,
     EventTableSchemaBase.SIZE 
};

public static class EventTableSchemaBase implements BaseColumns {
   protected EventTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/event");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(EventTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.edu.vu.isis.ammo.collector.event";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.edu.vu.isis.ammo.collector.event";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single event entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.edu.vu.isis.ammo.collector.event";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: A count of the number of media children (i.e. from the media table.
      * <P>Type: INTEGER</P> 
      */
          public static final String MEDIA_COUNT = "media_count";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String DISPLAY_NAME = "display_name";
      
      /** 
      * Description: Previously this was directed toward the primary key of the
            category table now it points to the tigr id.
            This is also a perfectly good key field.
      * <P>Type: TEXT</P> 
      */
          public static final String CATEGORY_ID = "category_id";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String TITLE = "title";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String DESCRIPTION = "description";
      
      /** 
      * Description: 
      * <P>Type: REAL</P> 
      */
          public static final String LONGITUDE = "longitude";
      
      /** 
      * Description: 
      * <P>Type: REAL</P> 
      */
          public static final String LATITUDE = "latitude";
      
      /** 
      * Description: 
      * <P>Type: LONG</P> 
      */
          public static final String CREATED_DATE = "created_date";
      
      /** 
      * Description: 
      * <P>Type: LONG</P> 
      */
          public static final String MODIFIED_DATE = "modified_date";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String CID = "cid";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String CATEGORY = "category";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String UNIT = "unit";
      
      /** 
      * Description: The total size of the incident in kibibytes.
            Although this is the pre-serialized size it should
            be sufficient for determining message size. 
            In particular the meta-data is presumed constant.
            Note that the image size is not the display resolution size.
            (jpeg files are compressed)
      * <P>Type: LONG</P> 
      */
          public static final String SIZE = "size";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM EVENT_SCHEMA PROPERTIES
// END   CUSTOM EVENT_SCHEMA PROPERTIES
} 
public static final String[] CATEGORY_CURSOR_COLUMNS = new String[] {
  CategoryTableSchemaBase.MAIN_CATEGORY ,
     CategoryTableSchemaBase.SUB_CATEGORY ,
     CategoryTableSchemaBase.TIGR_ID ,
     CategoryTableSchemaBase.ICON_TYPE, CategoryTableSchemaBase.ICON 
};

public static class CategoryTableSchemaBase implements BaseColumns {
   protected CategoryTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/category");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(CategoryTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.edu.vu.isis.ammo.collector.category";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.edu.vu.isis.ammo.collector.category";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single category entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.edu.vu.isis.ammo.collector.category";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String MAIN_CATEGORY = "main_category";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String SUB_CATEGORY = "sub_category";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String TIGR_ID = "tigr_id";
      
      /** 
      * Description: 
      * <P>Type: FILE</P> 
      */
          public static final String ICON_TYPE = "icon_type";
          public static final String ICON = "icon";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM CATEGORY_SCHEMA PROPERTIES
// END   CUSTOM CATEGORY_SCHEMA PROPERTIES
} 


} 
