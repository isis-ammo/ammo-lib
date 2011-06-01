// THIS IS GENERATED CODE, YOU SHOULD COPY THIS FOR YOUR HAND EDITS
package edu.vu.isis.ammo.launch.provider;

import android.net.Uri;

public class LaunchSchema extends LaunchSchemaBase {

   public static final int DATABASE_VERSION = 1;
   
   public static final class StructuredName {
	   public static final String CONTENT_ITEM_TYPE = "vnd.android.item/vnd.edu.isis.ammo.STRUCTURED_NAME";
	   public static final String DISPLAY_NAME = DataTableSchemaBase.DATA_KEY;
       public static final String FIRST_NAME = DataTableSchemaBase.DATA_2;
       public static final String MIDDLE_NAME = DataTableSchemaBase.DATA_3;
       public static final String LAST_NAME = DataTableSchemaBase.DATA_4;
   }
   
   public static final class Telephone {
	   public static final String CONTENT_ITEM_TYPE = "vnd.android.item/vnd.edu.isis.ammo.TELEPHONE";
	   public static final String NUMBER = DataTableSchemaBase.DATA_KEY;
   }
   
   public static final class Email {
	   public static final String CONTENT_ITEM_TYPE = "vnd.android.item/vnd.edu.isis.ammo.E_MAIL";
	   public static final String ADDRESS = DataTableSchemaBase.DATA_KEY;
   }
   
   public static final class Callsign {
	   public static final String CONTENT_ITEM_TYPE = "vnd.android.item/vnd.edu.isis.ammo.CALL_SIGN";
	   public static final String NAME = DataTableSchemaBase.DATA_KEY;
   }
   
   public static final Uri CONTENT_LDAP_URI = Uri.parse("content://"+AUTHORITY+"/ldap");

      public static class ContactTableSchema extends ContactTableSchemaBase {

         protected ContactTableSchema() { super(); }
      }

      public static class RawContactTableSchema extends RawContactTableSchemaBase {

         protected RawContactTableSchema() { super(); }
      }

      public static class DataTableSchema extends DataTableSchemaBase {

         protected DataTableSchema() { super(); }
      }

      public static class DataExtraTableSchema extends DataExtraTableSchemaBase {

         protected DataExtraTableSchema() { super(); }
      }

      public static class GroupMembershipTableSchema extends GroupMembershipTableSchemaBase {

         protected GroupMembershipTableSchema() { super(); }
      }

      public static class MimeTypeTableSchema extends MimeTypeTableSchemaBase {

         protected MimeTypeTableSchema() { super(); }
      }

      public static class MimeFieldTableSchema extends MimeFieldTableSchemaBase {

         protected MimeFieldTableSchema() { super(); }
      }

      public static class ActionTableSchema extends ActionTableSchemaBase {

         protected ActionTableSchema() { super(); }
      }

      public static class ActionExtraTableSchema extends ActionExtraTableSchemaBase {

         protected ActionExtraTableSchema() { super(); }
      }
      
      public static final String View_Ldap = "lookup_key";

      
}
