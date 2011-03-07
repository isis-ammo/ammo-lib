// THIS IS GENERATED CODE, MAKE SURE ANY CHANGES MADE HERE ARE PROPAGATED INTO THE GENERATOR TEMPLATES
package edu.vu.isis.ammo.tigr.provider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public abstract class TigrIncidentSchemaBase {
   public static final String AUTHORITY = "com.ascendintelligence.TigrMobile.provider.tigr_incidentprovider";

   public static final String DATABASE_NAME = "tigr_incident.db";

   public static final int _DISPOSITION_START = 0;
   public static final int _DISPOSITION_SEND = 1;
   public static final int _DISPOSITION_RECV = 2;
   public static final int _DISPOSITION_COMPLETE = 3;

   protected TigrIncidentSchemaBase() {}

// BEGIN CUSTOM TigrIncident CONSTANTS
// END   CUSTOM TigrIncident CONSTANTS

public static final String[] CONTENT_BASE_CURSOR_COLUMNS = new String[] {
  ContentBaseTableSchemaBase.CONTENT_GUID ,ContentBaseTableSchemaBase.VERSION_ID ,ContentBaseTableSchemaBase.TYPE ,ContentBaseTableSchemaBase.TITLE ,ContentBaseTableSchemaBase.STATUS ,ContentBaseTableSchemaBase.CATEGORY_ID ,ContentBaseTableSchemaBase.SUBCATEGORY_ID ,ContentBaseTableSchemaBase.DESCRIPTION ,ContentBaseTableSchemaBase.UNIT_ID ,ContentBaseTableSchemaBase.USER_ID ,ContentBaseTableSchemaBase.TIME_ZONE ,ContentBaseTableSchemaBase.REVISION_DATE ,ContentBaseTableSchemaBase.DATE_CREATED ,ContentBaseTableSchemaBase.DATE_MODIFIED ,ContentBaseTableSchemaBase.SECURITY_INFO ,ContentBaseTableSchemaBase.DELETED 
};

public static class ContentBaseTableSchemaBase implements BaseColumns {
   protected ContentBaseTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/content_base");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(ContentBaseTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.com.ascendintelligence.TigrMobile.content_base";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.com.ascendintelligence.TigrMobile.content_base";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single content_base entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.com.ascendintelligence.TigrMobile.content_base";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String CONTENT_GUID = "content_guid";
      
      /** 
      * Description: 
      * <P>Type: INTEGER</P> 
      */
          public static final String VERSION_ID = "version_id";
      
      /** 
      * Description: 
      * <P>Type: INTEGER</P> 
      */
          public static final String TYPE = "type";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String TITLE = "title";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String STATUS = "status";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String CATEGORY_ID = "category_id";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String SUBCATEGORY_ID = "subcategory_id";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String DESCRIPTION = "description";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String UNIT_ID = "unit_id";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String USER_ID = "user_id";
      
      /** 
      * Description: 
      * <P>Type: INTEGER</P> 
      */
          public static final String TIME_ZONE = "time_zone";
      
      /** 
      * Description: 
      * <P>Type: LONG</P> 
      */
          public static final String REVISION_DATE = "revision_date";
      
      /** 
      * Description: 
      * <P>Type: LONG</P> 
      */
          public static final String DATE_CREATED = "date_created";
      
      /** 
      * Description: 
      * <P>Type: LONG</P> 
      */
          public static final String DATE_MODIFIED = "date_modified";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String SECURITY_INFO = "security_info";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String DELETED = "deleted";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM CONTENT_BASE_SCHEMA PROPERTIES
// END   CUSTOM CONTENT_BASE_SCHEMA PROPERTIES
} 
public static final String[] MEDIA_CONTENT_CURSOR_COLUMNS = new String[] {
  MediaContentTableSchemaBase.EVENT_ID_FK ,MediaContentTableSchemaBase.EVENT_GUID ,MediaContentTableSchemaBase.CONTENT_GUID ,MediaContentTableSchemaBase.DATA ,MediaContentTableSchemaBase.MEDIA_FILE_URL ,MediaContentTableSchemaBase.STATUS ,MediaContentTableSchemaBase.TITLE ,MediaContentTableSchemaBase.NAME 
};

public static class MediaContentTableSchemaBase implements BaseColumns {
   protected MediaContentTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/media_content");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(MediaContentTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.com.ascendintelligence.TigrMobile.media_content";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.com.ascendintelligence.TigrMobile.media_content";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single media_content entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.com.ascendintelligence.TigrMobile.media_content";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: FK to Content Id
      * <P>Type: FK</P> 
      */
          public static final String EVENT_ID_FK = "event_id_fk";
      
      /** 
      * Description: Event Content Id
      * <P>Type: TEXT</P> 
      */
          public static final String EVENT_GUID = "event_guid";
      
      /** 
      * Description: COntent Unique Identifier
      * <P>Type: TEXT</P> 
      */
          public static final String CONTENT_GUID = "content_guid";
      
      /** 
      * Description: The path to the file on the mobile device
      * <P>Type: TEXT</P> 
      */
          public static final String DATA = "data";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String MEDIA_FILE_URL = "media_file_url";
      
      /** 
      * Description: Download status
      * <P>Type: TEXT</P> 
      */
          public static final String STATUS = "status";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String TITLE = "title";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String NAME = "name";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM MEDIA_CONTENT_SCHEMA PROPERTIES
// END   CUSTOM MEDIA_CONTENT_SCHEMA PROPERTIES
} 
public static final String[] LOCATIONS_CURSOR_COLUMNS = new String[] {
  LocationsTableSchemaBase.CONTENT_GUID ,LocationsTableSchemaBase.CONTENT_ID_FK ,LocationsTableSchemaBase.LOCATION_NUMBER ,LocationsTableSchemaBase.START_LATITUDE ,LocationsTableSchemaBase.START_LONGITUDE 
};

public static class LocationsTableSchemaBase implements BaseColumns {
   protected LocationsTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/locations");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(LocationsTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.com.ascendintelligence.TigrMobile.locations";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.com.ascendintelligence.TigrMobile.locations";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single locations entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.com.ascendintelligence.TigrMobile.locations";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String CONTENT_GUID = "content_guid";
      
      /** 
      * Description: 
      * <P>Type: FK</P> 
      */
          public static final String CONTENT_ID_FK = "content_id_fk";
      
      /** 
      * Description: 
      * <P>Type: INTEGER</P> 
      */
          public static final String LOCATION_NUMBER = "location_number";
      
      /** 
      * Description: 
      * <P>Type: LONG</P> 
      */
          public static final String START_LATITUDE = "start_latitude";
      
      /** 
      * Description: 
      * <P>Type: LONG</P> 
      */
          public static final String START_LONGITUDE = "start_longitude";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM LOCATIONS_SCHEMA PROPERTIES
// END   CUSTOM LOCATIONS_SCHEMA PROPERTIES
} 


} 
