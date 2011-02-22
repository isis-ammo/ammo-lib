// THIS IS GENERATED CODE, MAKE SURE ANY CHANGES MADE HERE ARE PROPAGATED INTO THE GENERATOR TEMPLATES
package edu.vu.isis.ammo.core.provider;

import android.net.Uri;
import android.provider.BaseColumns;
import android.content.ContentResolver;
import android.database.Cursor;

public abstract class DistributorSchemaBase {
   public static final String AUTHORITY = "edu.vu.isis.ammo.core.provider.distributorprovider";

   public static final String DATABASE_NAME = "distributor.db";

   public static final int _DISPOSITION_START = 0;
   public static final int _DISPOSITION_SEND = 1;
   public static final int _DISPOSITION_RECV = 2;
   public static final int _DISPOSITION_COMPLETE = 3;

   protected DistributorSchemaBase() {}

// BEGIN CUSTOM Distributor CONSTANTS
// END   CUSTOM Distributor CONSTANTS

public static final String[] DELIVERY_MECHANISM_CURSOR_COLUMNS = new String[] {
  DeliveryMechanismTableSchemaBase.CONN_TYPE ,
     DeliveryMechanismTableSchemaBase.STATUS ,
     DeliveryMechanismTableSchemaBase.UNIT ,
     DeliveryMechanismTableSchemaBase.COST_UP ,
     DeliveryMechanismTableSchemaBase.COST_DOWN 
};

public static class DeliveryMechanismTableSchemaBase implements BaseColumns {
   protected DeliveryMechanismTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/delivery_mechanism");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(DeliveryMechanismTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.edu.vu.isis.ammo.core.delivery_mechanism";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.edu.vu.isis.ammo.core.delivery_mechanism";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single delivery_mechanism entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.edu.vu.isis.ammo.core.delivery_mechanism";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: 
      * <P>Type: EXCLUSIVE</P> 
      */
              public static final int CONN_TYPE_UNKNOWN = 0;
                 public static final int CONN_TYPE_WIFI = 1;
                 public static final int CONN_TYPE_CELLULAR = 2;
                 public static final int CONN_TYPE_USB = 3;
            
         public static final String CONN_TYPE = "conn_type";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String STATUS = "status";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String UNIT = "unit";
      
      /** 
      * Description: 
      * <P>Type: INTEGER</P> 
      */
          public static final String COST_UP = "cost_up";
      
      /** 
      * Description: 
      * <P>Type: INTEGER</P> 
      */
          public static final String COST_DOWN = "cost_down";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM DELIVERY_MECHANISM_SCHEMA PROPERTIES
// END   CUSTOM DELIVERY_MECHANISM_SCHEMA PROPERTIES
} 
public static final String[] POSTAL_CURSOR_COLUMNS = new String[] {
  PostalTableSchemaBase.CP_TYPE ,
     PostalTableSchemaBase.URI ,
     PostalTableSchemaBase.NOTICE ,
     PostalTableSchemaBase.SERIALIZE_TYPE ,
     PostalTableSchemaBase.DISPOSITION ,
     PostalTableSchemaBase.EXPIRATION ,
     PostalTableSchemaBase.UNIT ,
     PostalTableSchemaBase.VALUE ,
     PostalTableSchemaBase.DATA ,
     PostalTableSchemaBase.CREATED_DATE ,
     PostalTableSchemaBase.MODIFIED_DATE 
};

public static class PostalTableSchemaBase implements BaseColumns {
   protected PostalTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/postal");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(PostalTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.edu.vu.isis.ammo.core.postal";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.edu.vu.isis.ammo.core.postal";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single postal entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.edu.vu.isis.ammo.core.postal";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: This is used for post requests.
          This along with the cost is used to decide how to deliver the specific object.
      * <P>Type: TEXT</P> 
      */
          public static final String CP_TYPE = "cp_type";
      
      /** 
      * Description: URI of the data to be distributed.
      * <P>Type: TEXT</P> 
      */
          public static final String URI = "uri";
      
      /** 
      * Description: A serialized pending intent.
           This is a platform specific serialization.
           In most cases this would be inappropriate but in this case the entry
           is fairly transient so performance is more important.
      * <P>Type: BLOB</P> 
      */
          public static final String NOTICE = "notice";
      
      /** 
      * Description: Indicates if the uri indicates a table or whether the data has been preserialized.
             DIRECT : the serialized data is found in the data field (or a suitable file).
             INDIRECT : the serialized data is obtained from the named uri.
             DEFERRED : the same as INDIRECT but the serialization doesn't happen until the data is sent.
      * <P>Type: EXCLUSIVE</P> 
      */
              public static final int SERIALIZE_TYPE_DIRECT = 1;
                 public static final int SERIALIZE_TYPE_INDIRECT = 2;
                 public static final int SERIALIZE_TYPE_DEFERRED = 3;
            
         public static final String SERIALIZE_TYPE = "serialize_type";
      
      /** 
      * Description: Status of the entry (sent or not sent).
      * <P>Type: EXCLUSIVE</P> 
      */
              public static final int DISPOSITION_PENDING = 1;
                 public static final int DISPOSITION_SENT = 2;
                 public static final int DISPOSITION_JOURNAL = 3;
                 public static final int DISPOSITION_FAIL = 4;
                 public static final int DISPOSITION_SATISFIED = 5;
                 public static final int DISPOSITION_EXPIRED = 6;
            
         public static final String DISPOSITION = "disposition";
      
      /** 
      * Description: Timestamp at which point entry becomes stale.
      * <P>Type: INTEGER</P> 
      */
          public static final String EXPIRATION = "expiration";
      
      /** 
      * Description: Units associated with {@link #VALUE}. Used to determine whether should occur.
      * <P>Type: TEXT</P> 
      */
          public static final String UNIT = "unit";
      
      /** 
      * Description: Arbitrary value linked to importance that entry is 
          transmitted and battery drain.
      * <P>Type: INTEGER</P> 
      */
          public static final String VALUE = "value";
      
      /** 
      * Description: If the If null then the data file corresponding to the column name and record id should be used.
           This is done when the data size is larger than that allowed for a field contents.
      * <P>Type: TEXT</P> 
      */
          public static final String DATA = "data";
      
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
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM POSTAL_SCHEMA PROPERTIES
// END   CUSTOM POSTAL_SCHEMA PROPERTIES
} 
public static final String[] RETRIVAL_CURSOR_COLUMNS = new String[] {
  RetrivalTableSchemaBase.DISPOSITION ,
     RetrivalTableSchemaBase.NOTICE ,
     RetrivalTableSchemaBase.URI ,
     RetrivalTableSchemaBase.MIME ,
     RetrivalTableSchemaBase.PROJECTION ,
     RetrivalTableSchemaBase.SELECTION ,
     RetrivalTableSchemaBase.ARGS ,
     RetrivalTableSchemaBase.ORDERING ,
     RetrivalTableSchemaBase.CONTINUITY ,
     RetrivalTableSchemaBase.CONTINUITY_VALUE ,
     RetrivalTableSchemaBase.EXPIRATION ,
     RetrivalTableSchemaBase.CREATED_DATE ,
     RetrivalTableSchemaBase.MODIFIED_DATE 
};

public static class RetrivalTableSchemaBase implements BaseColumns {
   protected RetrivalTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/retrival");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(RetrivalTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.edu.vu.isis.ammo.core.retrival";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.edu.vu.isis.ammo.core.retrival";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single retrival entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.edu.vu.isis.ammo.core.retrival";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: Status of the entry (sent or not sent).
      * <P>Type: EXCLUSIVE</P> 
      */
              public static final int DISPOSITION_PENDING = 1;
                 public static final int DISPOSITION_SENT = 2;
                 public static final int DISPOSITION_JOURNAL = 3;
                 public static final int DISPOSITION_FAIL = 4;
                 public static final int DISPOSITION_SATISFIED = 5;
                 public static final int DISPOSITION_EXPIRED = 6;
            
         public static final String DISPOSITION = "disposition";
      
      /** 
      * Description: A serialized pending intent.
           This is a platform specific serialization.
           In most cases this would be inappropriate but in this case the entry
           is fairly transient so performance is more important.
      * <P>Type: BLOB</P> 
      */
          public static final String NOTICE = "notice";
      
      /** 
      * Description: URI target for the data to be pulled.
      * <P>Type: TEXT</P> 
      */
          public static final String URI = "uri";
      
      /** 
      * Description: mime type of the data to be pulled.
      * <P>Type: TEXT</P> 
      */
          public static final String MIME = "mime";
      
      /** 
      * Description: The fields/columns wanted.
      * <P>Type: TEXT</P> 
      */
          public static final String PROJECTION = "projection";
      
      /** 
      * Description: The rows/tuples wanted.
      * <P>Type: TEXT</P> 
      */
          public static final String SELECTION = "selection";
      
      /** 
      * Description: The values using in the selection.
      * <P>Type: TEXT</P> 
      */
          public static final String ARGS = "args";
      
      /** 
      * Description: The order the values are to be returned in.
      * <P>Type: TEXT</P> 
      */
          public static final String ORDERING = "ordering";
      
      /** 
      * Description: Continuity indicates whether this is a...
             - one time rerieval or
             - should continue for some period of time
             - should be limited to a specific quantity of elements
      * <P>Type: EXCLUSIVE</P> 
      */
              public static final int CONTINUITY_ONCE = 1;
                 public static final int CONTINUITY_TEMPORAL = 2;
                 public static final int CONTINUITY_QUANTITY = 3;
            
         public static final String CONTINUITY = "continuity";
      
      /** 
      * Description: The meaning changes based on the continuity type.
             - ONCE : undefined
             - TEMPORAL : chronic  
             - QUANTITY : the maximum number of objects to return
      * <P>Type: INTEGER</P> 
      */
          public static final String CONTINUITY_VALUE = "continuity_value";
      
      /** 
      * Description: Timestamp at which point entry becomes stale.
           This is different than a temporal continuity. (I believe?)
      * <P>Type: INTEGER</P> 
      */
          public static final String EXPIRATION = "expiration";
      
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
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM RETRIVAL_SCHEMA PROPERTIES
// END   CUSTOM RETRIVAL_SCHEMA PROPERTIES
} 
public static final String[] PUBLICATION_CURSOR_COLUMNS = new String[] {
  PublicationTableSchemaBase.DISPOSITION ,
     PublicationTableSchemaBase.URI ,
     PublicationTableSchemaBase.MIME ,
     PublicationTableSchemaBase.EXPIRATION ,
     PublicationTableSchemaBase.CREATED_DATE ,
     PublicationTableSchemaBase.MODIFIED_DATE 
};

public static class PublicationTableSchemaBase implements BaseColumns {
   protected PublicationTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/publication");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(PublicationTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.edu.vu.isis.ammo.core.publication";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.edu.vu.isis.ammo.core.publication";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single publication entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.edu.vu.isis.ammo.core.publication";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: Status of the entry (sent or not sent).
      * <P>Type: EXCLUSIVE</P> 
      */
              public static final int DISPOSITION_PENDING = 1;
                 public static final int DISPOSITION_SENT = 2;
                 public static final int DISPOSITION_JOURNAL = 3;
                 public static final int DISPOSITION_FAIL = 4;
                 public static final int DISPOSITION_EXPIRED = 5;
            
         public static final String DISPOSITION = "disposition";
      
      /** 
      * Description: URI target for the data to be pulled.
      * <P>Type: TEXT</P> 
      */
          public static final String URI = "uri";
      
      /** 
      * Description: mime type of the data to be pulled.
      * <P>Type: TEXT</P> 
      */
          public static final String MIME = "mime";
      
      /** 
      * Description: Timestamp at which point entry becomes stale.
      * <P>Type: INTEGER</P> 
      */
          public static final String EXPIRATION = "expiration";
      
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
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM PUBLICATION_SCHEMA PROPERTIES
// END   CUSTOM PUBLICATION_SCHEMA PROPERTIES
} 
public static final String[] SUBSCRIPTION_CURSOR_COLUMNS = new String[] {
  SubscriptionTableSchemaBase.DISPOSITION ,
     SubscriptionTableSchemaBase.URI ,
     SubscriptionTableSchemaBase.MIME ,
     SubscriptionTableSchemaBase.SELECTION ,
     SubscriptionTableSchemaBase.EXPIRATION ,
     SubscriptionTableSchemaBase.NOTICE ,
     SubscriptionTableSchemaBase.CREATED_DATE ,
     SubscriptionTableSchemaBase.MODIFIED_DATE 
};

public static class SubscriptionTableSchemaBase implements BaseColumns {
   protected SubscriptionTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/subscription");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(SubscriptionTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.edu.vu.isis.ammo.core.subscription";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.edu.vu.isis.ammo.core.subscription";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single subscription entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.edu.vu.isis.ammo.core.subscription";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: Status of the entry (sent or not sent).
      * <P>Type: EXCLUSIVE</P> 
      */
              public static final int DISPOSITION_PENDING = 1;
                 public static final int DISPOSITION_SENT = 2;
                 public static final int DISPOSITION_JOURNAL = 3;
                 public static final int DISPOSITION_FAIL = 4;
                 public static final int DISPOSITION_EXPIRED = 5;
            
         public static final String DISPOSITION = "disposition";
      
      /** 
      * Description: URI target for the data to be pulled.
      * <P>Type: TEXT</P> 
      */
          public static final String URI = "uri";
      
      /** 
      * Description: mime type of the data to be pulled.
      * <P>Type: TEXT</P> 
      */
          public static final String MIME = "mime";
      
      /** 
      * Description: The rows/tuples wanted.
      * <P>Type: TEXT</P> 
      */
          public static final String SELECTION = "selection";
      
      /** 
      * Description: Timestamp at which point entry becomes stale.
      * <P>Type: INTEGER</P> 
      */
          public static final String EXPIRATION = "expiration";
      
      /** 
      * Description: A serialized pending intent.
           This is a platform specific serialization.
           In most cases this would be inappropriate but in this case the entry
           is fairly transient so performance is more important.
      * <P>Type: BLOB</P> 
      */
          public static final String NOTICE = "notice";
      
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
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM SUBSCRIPTION_SCHEMA PROPERTIES
// END   CUSTOM SUBSCRIPTION_SCHEMA PROPERTIES
} 


} 
