// THIS IS GENERATED CODE, MAKE SURE ANY CHANGES MADE HERE ARE PROPAGATED INTO THE GENERATOR TEMPLATES
package com.aterrasys.nevada.provider;

import android.net.Uri;
import android.provider.BaseColumns;
import android.content.ContentResolver;
import android.database.Cursor;

public abstract class NevadaSchemaBase {
   public static final String AUTHORITY = "com.aterrasys.nevada.provider.nevadaprovider";

   public static final String DATABASE_NAME = "nevada.db";

   public static final int _DISPOSITION_START = 0;
   public static final int _DISPOSITION_SEND = 1;
   public static final int _DISPOSITION_RECV = 2;
   public static final int _DISPOSITION_COMPLETE = 3;

   protected NevadaSchemaBase() {}

// BEGIN CUSTOM Nevada CONSTANTS
// END   CUSTOM Nevada CONSTANTS

public static final String[] PEOPLE_CURSOR_COLUMNS = new String[] {
  PeopleTableSchemaBase.ID ,
     PeopleTableSchemaBase.NAME ,
     PeopleTableSchemaBase.SMS_EMAIL_GATEWAY 
};

public static class PeopleTableSchemaBase implements BaseColumns {
   protected PeopleTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/people");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(PeopleTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.com.aterrasys.nevada.people";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.com.aterrasys.nevada.people";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single people entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.com.aterrasys.nevada.people";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: 
      * <P>Type: LONG</P> 
      */
          public static final String ID = "id";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String NAME = "name";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String SMS_EMAIL_GATEWAY = "sms_email_gateway";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM PEOPLE_SCHEMA PROPERTIES
// END   CUSTOM PEOPLE_SCHEMA PROPERTIES
} 
public static final String[] CHANNELS_CURSOR_COLUMNS = new String[] {
  ChannelsTableSchemaBase.ID ,
     ChannelsTableSchemaBase.ACTIVE ,
     ChannelsTableSchemaBase.NAME ,
     ChannelsTableSchemaBase.DESCRIPTON ,
     ChannelsTableSchemaBase.TYPE ,
     ChannelsTableSchemaBase.CREATORID ,
     ChannelsTableSchemaBase.CANVASID 
};

public static class ChannelsTableSchemaBase implements BaseColumns {
   protected ChannelsTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/channels");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(ChannelsTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.com.aterrasys.nevada.channels";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.com.aterrasys.nevada.channels";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single channels entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.com.aterrasys.nevada.channels";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: The channel id.
      * <P>Type: LONG</P> 
      */
          public static final String ID = "id";
      
      /** 
      * Description: Active or not.
      * <P>Type: INTEGER</P> 
      */
          public static final String ACTIVE = "active";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String NAME = "name";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String DESCRIPTON = "descripton";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String TYPE = "type";
      
      /** 
      * Description: 
      * <P>Type: LONG</P> 
      */
          public static final String CREATORID = "creatorid";
      
      /** 
      * Description: 
      * <P>Type: LONG</P> 
      */
          public static final String CANVASID = "canvasid";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM CHANNELS_SCHEMA PROPERTIES
// END   CUSTOM CHANNELS_SCHEMA PROPERTIES
} 
public static final String[] USER_CURSOR_COLUMNS = new String[] {
  UserTableSchemaBase.ID ,
     UserTableSchemaBase.USERNAME ,
     UserTableSchemaBase.EMAIL 
};

public static class UserTableSchemaBase implements BaseColumns {
   protected UserTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/user");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(UserTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.com.aterrasys.nevada.user";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.com.aterrasys.nevada.user";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single user entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.com.aterrasys.nevada.user";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: The user id.
      * <P>Type: LONG</P> 
      */
          public static final String ID = "id";
      
      /** 
      * Description: A string representing the username .
      * <P>Type: TEXT</P> 
      */
          public static final String USERNAME = "username";
      
      /** 
      * Description: The users email
      * <P>Type: TEXT</P> 
      */
          public static final String EMAIL = "email";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM USER_SCHEMA PROPERTIES
// END   CUSTOM USER_SCHEMA PROPERTIES
} 
public static final String[] UNIT_CURSOR_COLUMNS = new String[] {
  UnitTableSchemaBase.ID ,
     UnitTableSchemaBase.UNITNAME 
};

public static class UnitTableSchemaBase implements BaseColumns {
   protected UnitTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/unit");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(UnitTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.com.aterrasys.nevada.unit";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.com.aterrasys.nevada.unit";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single unit entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.com.aterrasys.nevada.unit";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: The unit id.
      * <P>Type: LONG</P> 
      */
          public static final String ID = "id";
      
      /** 
      * Description: The unit name.
      * <P>Type: TEXT</P> 
      */
          public static final String UNITNAME = "unitname";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM UNIT_SCHEMA PROPERTIES
// END   CUSTOM UNIT_SCHEMA PROPERTIES
} 
public static final String[] UNITPERSON_CURSOR_COLUMNS = new String[] {
  UnitpersonTableSchemaBase.UNITID ,
     UnitpersonTableSchemaBase.USERID 
};

public static class UnitpersonTableSchemaBase implements BaseColumns {
   protected UnitpersonTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/unitperson");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(UnitpersonTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.com.aterrasys.nevada.unitperson";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.com.aterrasys.nevada.unitperson";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single unitperson entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.com.aterrasys.nevada.unitperson";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: The unit id.
      * <P>Type: LONG</P> 
      */
          public static final String UNITID = "unitid";
      
      /** 
      * Description: The user id.
      * <P>Type: LONG</P> 
      */
          public static final String USERID = "userid";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM UNITPERSON_SCHEMA PROPERTIES
// END   CUSTOM UNITPERSON_SCHEMA PROPERTIES
} 
public static final String[] UNITTRACKING_CURSOR_COLUMNS = new String[] {
  UnittrackingTableSchemaBase.ID ,
     UnittrackingTableSchemaBase.LAT ,
     UnittrackingTableSchemaBase.LON ,
     UnittrackingTableSchemaBase.CURRENTTIME 
};

public static class UnittrackingTableSchemaBase implements BaseColumns {
   protected UnittrackingTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/unittracking");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(UnittrackingTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.com.aterrasys.nevada.unittracking";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.com.aterrasys.nevada.unittracking";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single unittracking entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.com.aterrasys.nevada.unittracking";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: The unit id.
      * <P>Type: LONG</P> 
      */
          public static final String ID = "id";
      
      /** 
      * Description: 
      * <P>Type: REAL</P> 
      */
          public static final String LAT = "lat";
      
      /** 
      * Description: 
      * <P>Type: REAL</P> 
      */
          public static final String LON = "lon";
      
      /** 
      * Description: 
      * <P>Type: LONG</P> 
      */
          public static final String CURRENTTIME = "currenttime";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM UNITTRACKING_SCHEMA PROPERTIES
// END   CUSTOM UNITTRACKING_SCHEMA PROPERTIES
} 
public static final String[] LOCATION_CURSOR_COLUMNS = new String[] {
  LocationTableSchemaBase.ID ,
     LocationTableSchemaBase.LAT ,
     LocationTableSchemaBase.LON ,
     LocationTableSchemaBase.TIMESTAMP 
};

public static class LocationTableSchemaBase implements BaseColumns {
   protected LocationTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/location");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(LocationTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.com.aterrasys.nevada.location";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.com.aterrasys.nevada.location";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single location entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.com.aterrasys.nevada.location";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: The unique id for this row
      * <P>Type: LONG</P> 
      */
          public static final String ID = "id";
      
      /** 
      * Description: 
      * <P>Type: REAL</P> 
      */
          public static final String LAT = "lat";
      
      /** 
      * Description: 
      * <P>Type: REAL</P> 
      */
          public static final String LON = "lon";
      
      /** 
      * Description: 
      * <P>Type: LONG</P> 
      */
          public static final String TIMESTAMP = "timestamp";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM LOCATION_SCHEMA PROPERTIES
// END   CUSTOM LOCATION_SCHEMA PROPERTIES
} 
public static final String[] MAPANNOTATION_CURSOR_COLUMNS = new String[] {
  MapannotationTableSchemaBase.ID ,
     MapannotationTableSchemaBase.TYPE ,
     MapannotationTableSchemaBase.TEXT ,
     MapannotationTableSchemaBase.ZOOM ,
     MapannotationTableSchemaBase.LAT ,
     MapannotationTableSchemaBase.LON ,
     MapannotationTableSchemaBase.MGRS ,
     MapannotationTableSchemaBase.IMAGEURI 
};

public static class MapannotationTableSchemaBase implements BaseColumns {
   protected MapannotationTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/mapannotation");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(MapannotationTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.com.aterrasys.nevada.mapannotation";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.com.aterrasys.nevada.mapannotation";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single mapannotation entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.com.aterrasys.nevada.mapannotation";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: 
      * <P>Type: LONG</P> 
      */
          public static final String ID = "id";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String TYPE = "type";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String TEXT = "text";
      
      /** 
      * Description: 
      * <P>Type: INTEGER</P> 
      */
          public static final String ZOOM = "zoom";
      
      /** 
      * Description: 
      * <P>Type: REAL</P> 
      */
          public static final String LAT = "lat";
      
      /** 
      * Description: 
      * <P>Type: REAL</P> 
      */
          public static final String LON = "lon";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String MGRS = "mgrs";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String IMAGEURI = "imageuri";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM MAPANNOTATION_SCHEMA PROPERTIES
// END   CUSTOM MAPANNOTATION_SCHEMA PROPERTIES
} 


} 
