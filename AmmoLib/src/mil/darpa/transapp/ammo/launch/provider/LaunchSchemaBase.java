// THIS IS GENERATED CODE, MAKE SURE ANY CHANGES MADE HERE ARE PROPAGATED INTO THE GENERATOR TEMPLATES
package mil.darpa.transapp.ammo.launch.provider;

import android.net.Uri;
import android.provider.BaseColumns;
import android.content.ContentResolver;
import android.database.Cursor;

public abstract class LaunchSchemaBase {
   public static final String AUTHORITY = "mil.darpa.transapp.ammo.launch.provider.launchprovider";

   public static final String DATABASE_NAME = "launch.db";

   public static final int _DISPOSITION_START = 0;
   public static final int _DISPOSITION_SEND = 1;
   public static final int _DISPOSITION_RECV = 2;
   public static final int _DISPOSITION_COMPLETE = 3;

   protected LaunchSchemaBase() {}

// BEGIN CUSTOM Launch CONSTANTS
// END   CUSTOM Launch CONSTANTS

public static final String[] CONTACT_CURSOR_COLUMNS = new String[] {
  ContactTableSchemaBase.LOOKUP_KEY ,
     ContactTableSchemaBase.DISPLAY_NAME ,
     ContactTableSchemaBase.CALL_SIGN ,
     ContactTableSchemaBase.USER_ID ,
     ContactTableSchemaBase.VISUAL_ID ,
     ContactTableSchemaBase.AUDIO_ID ,
     ContactTableSchemaBase.TACTILE_ID ,
     ContactTableSchemaBase.TIMES_CONTACTED ,
     ContactTableSchemaBase.LAST_CONTACT ,
     ContactTableSchemaBase.INITIAL_CONTACT ,
     ContactTableSchemaBase.PRESENCE ,
     ContactTableSchemaBase.SUPPRESS ,
     ContactTableSchemaBase.STARRED 
};

public static class ContactTableSchemaBase implements BaseColumns {
   protected ContactTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/contact");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(ContactTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.contact";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.mil.darpa.transapp.ammo.launch.contact";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single contact entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.contact";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: A globally unique identifier for the contact.
      * <P>Type: GUID</P> 
      */
          public static final String LOOKUP_KEY = "lookup_key";
      
      /** 
      * Description: The default textual name of the contact.
      * <P>Type: TEXT</P> 
      */
          public static final String DISPLAY_NAME = "display_name";
      
      /** 
      * Description: An alias for the war fighter.
           e.g. Hawk4
      * <P>Type: TEXT</P> 
      */
          public static final String CALL_SIGN = "call_sign";
      
      /** 
      * Description: The user ID, e.g. for Tigr
      * <P>Type: TEXT</P> 
      */
          public static final String USER_ID = "user_id";
      


      /** 
      * Description: Into the data table to the contacts photograph. Null allowed.
      * <P>Type: FK</P> 
      */
          public static final String VISUAL_ID = "visual_id";
      
      /** 
      * Description: Into the data table to the contacts audio identifier. Null allowed.
      * <P>Type: FK</P> 
      */
          public static final String AUDIO_ID = "audio_id";
      
      /** 
      * Description: Into the data table to the contacts tactile/vibration identifier. Null allowed.
      * <P>Type: FK</P> 
      */
          public static final String TACTILE_ID = "tactile_id";
      
      /** 
      * Description: How many times the contact has been accessed.
      * <P>Type: INTEGER</P> 
      */
          public static final String TIMES_CONTACTED = "times_contacted";
      
      /** 
      * Description: The last time contacted.
      * <P>Type: TIMESTAMP</P> 
      */
          public static final String LAST_CONTACT = "last_contact";
      
      /** 
      * Description: The first contact included in the times contacted count.
      * <P>Type: TIMESTAMP</P> 
      */
          public static final String INITIAL_CONTACT = "initial_contact";
      
      /** 
      * Description: Indicates whether the contact is presently reachable (best guess).
           This is related to suppression.
      * <P>Type: INCLUSIVE</P> 
      */
              public static final int PRESENCE_ALL = 0;
                 public static final int PRESENCE_AUDIO = 1;
                 public static final int PRESENCE_VIDEO = 2;
                 public static final int PRESENCE_TACTILE = 3;
            
         public static final String PRESENCE = "presence";
      
      /** 
      * Description: Indicates whether the subject is reachable by the contact.
           This is related to suppression.
      * <P>Type: INCLUSIVE</P> 
      */
              public static final int SUPPRESS_ALL = 0;
                 public static final int SUPPRESS_AUDIO = 1;
            
         public static final String SUPPRESS = "suppress";
      
      /** 
      * Description: What is the primary relationship with this contact?
      * <P>Type: EXCLUSIVE</P> 
      */
              public static final int STARRED_NONE = 0;
                 public static final int STARRED_FAVORITE = 1;
                 public static final int STARRED_FRIEND = 2;
                 public static final int STARRED_TEAM = 3;
                 public static final int STARRED_SUPERIOR = 4;
                 public static final int STARRED_SUBORDINATE = 5;
            
         public static final String STARRED = "starred";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM CONTACT_SCHEMA PROPERTIES
// END   CUSTOM CONTACT_SCHEMA PROPERTIES
} 
public static final String[] RAW_CONTACT_CURSOR_COLUMNS = new String[] {
  RawContactTableSchemaBase.CONTACT_ID ,
     RawContactTableSchemaBase.AGGREGATION_MODE ,
     RawContactTableSchemaBase.NODE_TYPE ,
     RawContactTableSchemaBase.ACCOUNT_NAME ,
     RawContactTableSchemaBase.ACCOUNT_TYPE ,
     RawContactTableSchemaBase.ACCOUNT_SOURCE_ID ,
     RawContactTableSchemaBase.DISPLAY_NAME ,
     RawContactTableSchemaBase.CALL_SIGN ,
     RawContactTableSchemaBase.USER_ID ,
     RawContactTableSchemaBase.RANK_ID ,
     RawContactTableSchemaBase.VISUAL_ID ,
     RawContactTableSchemaBase.AUDIO_ID ,
     RawContactTableSchemaBase.TACTILE_ID ,
     RawContactTableSchemaBase.TIMES_CONTACTED ,
     RawContactTableSchemaBase.LAST_CONTACT ,
     RawContactTableSchemaBase.INITIAL_CONTACT ,
     RawContactTableSchemaBase.NOTES 
};

public static class RawContactTableSchemaBase implements BaseColumns {
   protected RawContactTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/raw_contact");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(RawContactTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.raw_contact";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.mil.darpa.transapp.ammo.launch.raw_contact";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single raw_contact entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.raw_contact";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: Each raw contact must have a corresponding contact.
   The ID of the row in the contacts table to which this raw contact belongs.
   Raw contacts are linked to contacts by the aggregation process, which can be 
   controlled by the AGGREGATION_MODE field and AggregationExceptions.
      * <P>Type: FK</P> 
      */
          public static final String CONTACT_ID = "contact_id";
      
      /** 
      * Description: A mechanism that allows programmatic control of the aggregation process.
   See also AggregationExceptions.
      * <P>Type: EXCLUSIVE</P> 
      */
              public static final int AGGREGATION_MODE_DEFAULT = 0;
                 public static final int AGGREGATION_MODE_DISABLED = 1;
                 public static final int AGGREGATION_MODE_SUSPENDED = 2;
            
         public static final String AGGREGATION_MODE = "aggregation_mode";
      
      /** 
      * Description: A mechanism that allows programmatic control of the aggregation process.
   See also AggregationExceptions.
      * <P>Type: EXCLUSIVE</P> 
      */
              public static final int NODE_TYPE_PERSON = 0;
                 public static final int NODE_TYPE_GROUP = 1;
                 public static final int NODE_TYPE_ORG = 2;
                 public static final int NODE_TYPE_ROLE = 3;
            
         public static final String NODE_TYPE = "node_type";
      
      /** 
      * Description: The name of the account instance to which this row belongs, 
   which when paired with ACCOUNT_TYPE identifies a specific account. 
   For example, this will be the Gmail address if it is a Google account. 
   It should be set at the time the raw contact is inserted and never changed afterwards.
      * <P>Type: TEXT</P> 
      */
          public static final String ACCOUNT_NAME = "account_name";
      
      /** 
      * Description: The type of account to which this row belongs, which when paired with 
   ACCOUNT_NAME identifies a specific account. 
   It is set at the time the raw contact is inserted and never changed afterwards.

   To ensure uniqueness, new account types are chosen according to the Java package naming convention. 
   Thus a Google account is of type "com.google".
      * <P>Type: TEXT</P> 
      */
          public static final String ACCOUNT_TYPE = "account_type";
      
      /** 
      * Description: String that uniquely identifies this row to its source account.
   Typically it is set at the time the raw contact is inserted and never changed afterwards.
   The one notable exception is a new raw contact:
   it will have an account name and type, but no source id.
   This indicates to the sync adapter that a new contact needs to be 
   created server-side and its ID stored in the corresponding SOURCE_ID field on the phone.
      * <P>Type: TEXT</P> 
      */
          public static final String ACCOUNT_SOURCE_ID = "account_source_id";
      
      /** 
      * Description: The default textual name of the contact.
      * <P>Type: TEXT</P> 
      */
          public static final String DISPLAY_NAME = "display_name";
      
      /** 
      * Description: An alias for the war fighter.
           e.g. Hawk4
      * <P>Type: TEXT</P> 
      */
          public static final String CALL_SIGN = "call_sign";
      

      /** 
      * Description: A user ID, e.g. for Tigr
      * <P>Type: TEXT</P> 
      */
          public static final String USER_ID = "user_id";
      


      /** 
      * Description: Into the data table to the contact's rank icon. Null allowed.
           The contact may have additional items in the data table but this is their primary rank.
      * <P>Type: FK</P> 
      */
          public static final String RANK_ID = "rank_id";
      
      /** 
      * Description: Into the data table to the contact's photograph. Null allowed.
           The contact may have additional items in the data table but this is their primary image identifier.
      * <P>Type: FK</P> 
      */
          public static final String VISUAL_ID = "visual_id";
      
      /** 
      * Description: Into the data table to the contact's audio identifier. Null allowed.
           The contact may have additional items in the data table but this is their primary audible identifier.
      * <P>Type: FK</P> 
      */
          public static final String AUDIO_ID = "audio_id";
      
      /** 
      * Description: Into the data table to the contact's tactile/vibration identifier. Null allowed.
           The contact may have additional items in the data table but this is their primary tactile identifier.
      * <P>Type: FK</P> 
      */
          public static final String TACTILE_ID = "tactile_id";
      
      /** 
      * Description: How many times the contact has been accessed.
      * <P>Type: INTEGER</P> 
      */
          public static final String TIMES_CONTACTED = "times_contacted";
      
      /** 
      * Description: The last time contacted.
      * <P>Type: TIMESTAMP</P> 
      */
          public static final String LAST_CONTACT = "last_contact";
      
      /** 
      * Description: The first contact included in the times contacted count.
      * <P>Type: TIMESTAMP</P> 
      */
          public static final String INITIAL_CONTACT = "initial_contact";
      
      /** 
      * Description: Miscellaneous notes about the contact.
      * <P>Type: TEXT</P> 
      */
          public static final String NOTES = "notes";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM RAW_CONTACT_SCHEMA PROPERTIES
// END   CUSTOM RAW_CONTACT_SCHEMA PROPERTIES
} 
public static final String[] DATA_CURSOR_COLUMNS = new String[] {
  DataTableSchemaBase.RAW_CONTACT_ID ,
     DataTableSchemaBase.PRIMACY ,
     DataTableSchemaBase.MIME ,
     DataTableSchemaBase.VISIBILITY ,
     DataTableSchemaBase.DATA_KEY ,
     DataTableSchemaBase.DATA_2 ,
     DataTableSchemaBase.DATA_3 ,
     DataTableSchemaBase.DATA_4 ,
     DataTableSchemaBase.BLOB 
};

public static class DataTableSchemaBase implements BaseColumns {
   protected DataTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/data");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(DataTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.data";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.mil.darpa.transapp.ammo.launch.data";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single data entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.data";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: The id of the row in the RawContacts table to which this data belongs.
      * <P>Type: FK</P> 
      */
          public static final String RAW_CONTACT_ID = "raw_contact_id";
      
      /** 
      * Description: PRIMARY : Whether this is the primary entry of its kind for the raw contact to which it belongs.
   SUPER : Whether this is the primary entry of its kind for the aggregate contact to which it belongs.
   Any data record that is "SUPER primary" must also be "PRIMARY".
   For example, the super-primary entry may be interpreted as the default contact
   value of its kind (for example, the default phone number to use for the contact).
      * <P>Type: EXCLUSIVE</P> 
      */
              public static final int PRIMACY_NONE = 0;
                 public static final int PRIMACY_PRIMARY = 1;
                 public static final int PRIMACY_SUPER = 2;
                 public static final int PRIMACY_EXTRA = 2;
            
         public static final String PRIMACY = "primacy";
      
      /** 
      * Description: The MIME type of the item represented by this row.
   The mime type is used to detemine the actions which can be performed with or on the contact.
      * <P>Type: FK</P> 
      */
          public static final String MIME = "mime";
      
      /** 
      * Description: Indicates whether this contact is visible in a user interface.
      * <P>Type: BOOL</P> 
      */
          public static final String VISIBILITY = "visibility";
      
      /** 
      * Description: Generic data columns.
   The meaning of each column is determined by the MIME TYPE. 
   By convention...
    DATA KEY is used for the candidate key as it is indexed
    BLOB is used for storing BLOBs (binary data).
   Data columns whose meaning is not explicitly defined for a given MIMETYPE should not be used. 

   If additional data is required the primacy field indicates extra data with the same primacy. 
   In that case the mime type and data key provide the keys to the group.
      * <P>Type: TEXT</P> 
      */
          public static final String DATA_KEY = "data_key";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String DATA_2 = "data_2";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String DATA_3 = "data_3";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String DATA_4 = "data_4";
      
      /** 
      * Description: 
      * <P>Type: BLOB</P> 
      */
          public static final String BLOB = "blob";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM DATA_SCHEMA PROPERTIES
// END   CUSTOM DATA_SCHEMA PROPERTIES
} 
public static final String[] DATA_EXTRA_CURSOR_COLUMNS = new String[] {
  DataExtraTableSchemaBase.PARENT ,
     DataExtraTableSchemaBase.ORDINAL ,
     DataExtraTableSchemaBase.EXTRA_1 ,
     DataExtraTableSchemaBase.EXTRA_2 ,
     DataExtraTableSchemaBase.EXTRA_3 ,
     DataExtraTableSchemaBase.BLOB 
};

public static class DataExtraTableSchemaBase implements BaseColumns {
   protected DataExtraTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/data_extra");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(DataExtraTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.data_extra";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.mil.darpa.transapp.ammo.launch.data_extra";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single data_extra entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.data_extra";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: A reference back to the head record which this record extends
      * <P>Type: FK</P> 
      */
          public static final String PARENT = "parent";
      
      /** 
      * Description: If more than one extension is required this provides an ordinal.
      * <P>Type: INTEGER</P> 
      */
          public static final String ORDINAL = "ordinal";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String EXTRA_1 = "extra_1";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String EXTRA_2 = "extra_2";
      
      /** 
      * Description: 
      * <P>Type: TEXT</P> 
      */
          public static final String EXTRA_3 = "extra_3";
      
      /** 
      * Description: 
      * <P>Type: BLOB</P> 
      */
          public static final String BLOB = "blob";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM DATA_EXTRA_SCHEMA PROPERTIES
// END   CUSTOM DATA_EXTRA_SCHEMA PROPERTIES
} 
public static final String[] GROUP_MEMBERSHIP_CURSOR_COLUMNS = new String[] {
  GroupMembershipTableSchemaBase.GROUP_ID ,
     GroupMembershipTableSchemaBase.MEMBER_ID ,
     GroupMembershipTableSchemaBase.ROLE 
};

public static class GroupMembershipTableSchemaBase implements BaseColumns {
   protected GroupMembershipTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/group_membership");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(GroupMembershipTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.group_membership";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.mil.darpa.transapp.ammo.launch.group_membership";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single group_membership entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.group_membership";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: The id of the row in the Group table.
      * <P>Type: FK</P> 
      */
          public static final String GROUP_ID = "group_id";
      
      /** 
      * Description: The id of the row in the RawContacts table indicating its membership in the group.
      * <P>Type: FK</P> 
      */
          public static final String MEMBER_ID = "member_id";
      
      /** 
      * Description: Indicates the role played by the member of the group.
      * <P>Type: EXCLUSIVE</P> 
      */
              public static final int ROLE_MEMBER = 0;
                 public static final int ROLE_LEADER = 1;
                 public static final int ROLE_OWNER = 2;
            
         public static final String ROLE = "role";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM GROUP_MEMBERSHIP_SCHEMA PROPERTIES
// END   CUSTOM GROUP_MEMBERSHIP_SCHEMA PROPERTIES
} 
public static final String[] MIME_TYPE_CURSOR_COLUMNS = new String[] {
  MimeTypeTableSchemaBase.NAME 
};

public static class MimeTypeTableSchemaBase implements BaseColumns {
   protected MimeTypeTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/mime_type");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(MimeTypeTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.mime_type";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.mil.darpa.transapp.ammo.launch.mime_type";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single mime_type entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.mime_type";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: Some typical mime types are...
   * StructuredName.CONTENT_ITEM_TYPE
   * Phone.CONTENT_ITEM_TYPE
   * Email.CONTENT_ITEM_TYPE
   * Photo.CONTENT_ITEM_TYPE
   * Organization.CONTENT_ITEM_TYPE
   * Im.CONTENT_ITEM_TYPE
   * Nickname.CONTENT_ITEM_TYPE
   * Note.CONTENT_ITEM_TYPE
   * StructuredPostal.CONTENT_ITEM_TYPE
   * Website.CONTENT_ITEM_TYPE
   * Event.CONTENT_ITEM_TYPE
   * Relation.CONTENT_ITEM_TYPE
   * SipAddress.CONTENT_ITEM_TYPE
      * <P>Type: TEXT</P> 
      */
          public static final String NAME = "name";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM MIME_TYPE_SCHEMA PROPERTIES
// END   CUSTOM MIME_TYPE_SCHEMA PROPERTIES
} 
public static final String[] MIME_FIELD_CURSOR_COLUMNS = new String[] {
  MimeFieldTableSchemaBase.VAR ,
     MimeFieldTableSchemaBase.DIM ,
     MimeFieldTableSchemaBase.TABLE ,
     MimeFieldTableSchemaBase.ORDINAL ,
     MimeFieldTableSchemaBase.COL 
};

public static class MimeFieldTableSchemaBase implements BaseColumns {
   protected MimeFieldTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/mime_field");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(MimeFieldTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.mime_field";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.mil.darpa.transapp.ammo.launch.mime_field";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single mime_field entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.mime_field";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: The name of the mime field. Presented to the operator to be filled out.
      * <P>Type: TEXT</P> 
      */
          public static final String VAR = "var";
      
      /** 
      * Description: The dimension (and unit) of the mime field.
      * <P>Type: TEXT</P> 
      */
          public static final String DIM = "dim";
      
      /** 
      * Description: The name of the table containing the mime field.
      * <P>Type: TEXT</P> 
      */
          public static final String TABLE = "table";
      
      /** 
      * Description: The ordinal of the row in the table containing the mime field.
      * <P>Type: TEXT</P> 
      */
          public static final String ORDINAL = "ordinal";
      
      /** 
      * Description: The name of the column table containing the mime field.
      * <P>Type: TEXT</P> 
      */
          public static final String COL = "col";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM MIME_FIELD_SCHEMA PROPERTIES
// END   CUSTOM MIME_FIELD_SCHEMA PROPERTIES
} 
public static final String[] ACTION_CURSOR_COLUMNS = new String[] {
  ActionTableSchemaBase.NAME ,
     ActionTableSchemaBase.INTENT ,
     ActionTableSchemaBase.ICON ,
     ActionTableSchemaBase.MIME 
};

public static class ActionTableSchemaBase implements BaseColumns {
   protected ActionTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/action");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(ActionTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.action";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.mil.darpa.transapp.ammo.launch.action";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single action entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.action";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: What the action
      * <P>Type: TEXT</P> 
      */
          public static final String NAME = "name";
      
      /** 
      * Description: The name of the intent used to start an activity for this contact.
      * <P>Type: TEXT</P> 
      */
          public static final String INTENT = "intent";
      
      /** 
      * Description: An icon representing the action to be taken.
      * <P>Type: BLOB</P> 
      */
          public static final String ICON = "icon";
      
      /** 
      * Description: A mime type required to fulfill this action.
      * <P>Type: FK</P> 
      */
          public static final String MIME = "mime";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM ACTION_SCHEMA PROPERTIES
// END   CUSTOM ACTION_SCHEMA PROPERTIES
} 
public static final String[] ACTION_EXTRA_CURSOR_COLUMNS = new String[] {
  ActionExtraTableSchemaBase.PARENT ,
     ActionExtraTableSchemaBase.ORDINAL ,
     ActionExtraTableSchemaBase.MIME 
};

public static class ActionExtraTableSchemaBase implements BaseColumns {
   protected ActionExtraTableSchemaBase() {} // No instantiation.
   
   /**
    * The content:// style URL for this table
    */
   public static final Uri CONTENT_URI =
      Uri.parse("content://"+AUTHORITY+"/action_extra");

   public static Uri getUri(Cursor cursor) {
     Integer id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
     return  Uri.withAppendedPath(ActionExtraTableSchemaBase.CONTENT_URI, id.toString());
   }
   
   /**
    * The MIME type of {@link #CONTENT_URI} providing a directory
    */
   public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.action_extra";
   
   /**
    * A mime type used for publisher subscriber.
    */
   public static final String CONTENT_TOPIC =
      "application/vnd.mil.darpa.transapp.ammo.launch.action_extra";
   
   /**
    * The MIME type of a {@link #CONTENT_URI} sub-directory of a single action_extra entry.
    */
   public static final String CONTENT_ITEM_TYPE = 
      ContentResolver.CURSOR_ITEM_BASE_TYPE+"/vnd.mil.darpa.transapp.ammo.launch.action_extra";
   
   
   public static final String DEFAULT_SORT_ORDER = ""; //"modified_date DESC";
   

      /** 
      * Description: A reference back to the head record which this record extends
      * <P>Type: FK</P> 
      */
          public static final String PARENT = "parent";
      
      /** 
      * Description: If more than one set is required this provides an ordinal.
      * <P>Type: INTEGER</P> 
      */
          public static final String ORDINAL = "ordinal";
      
      /** 
      * Description: A extra mime type required to fulfill this action.
      * <P>Type: FK</P> 
      */
          public static final String MIME = "mime";
      

   public static final String _DISPOSITION = "_disp"; 


// BEGIN CUSTOM ACTION_EXTRA_SCHEMA PROPERTIES
// END   CUSTOM ACTION_EXTRA_SCHEMA PROPERTIES
} 


} 
