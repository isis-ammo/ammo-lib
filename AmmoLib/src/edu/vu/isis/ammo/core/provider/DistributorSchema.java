package edu.vu.isis.ammo.core.provider;

import android.provider.BaseColumns;

public class DistributorSchema extends DistributorSchemaBase {

   public static final int DATABASE_VERSION = 5;

      public static class PostalTableSchema extends PostalTableSchemaBase {

         protected PostalTableSchema() { super(); }

         public static final String PRIORITY_SORT_ORDER = 
        	 BaseColumns._ID + " ASC";
               //PostalTableSchemaBase.EXPIRATION + " DESC, " +
               //PostalTableSchemaBase.MODIFIED_DATE + " DESC ";
      }    
    
      public static class RetrievalTableSchema extends RetrievalTableSchemaBase {

         protected RetrievalTableSchema() { super(); }

         public static final String PRIORITY_SORT_ORDER = 
        	 BaseColumns._ID + " ASC";
             //  RetrievalTableSchemaBase.EXPIRATION + " DESC, " +
             //  RetrievalTableSchemaBase.MODIFIED_DATE + " DESC ";
      }    
      public static class PublicationTableSchema extends PublicationTableSchemaBase {

         protected PublicationTableSchema() { super(); }

         public static final String PRIORITY_SORT_ORDER = 
        	 BaseColumns._ID + " ASC";
             //  PublicationTableSchemaBase.EXPIRATION + " DESC, " +
             //  PublicationTableSchemaBase.MODIFIED_DATE + " DESC ";
      }    
      public static class SubscriptionTableSchema extends SubscriptionTableSchemaBase {

         protected SubscriptionTableSchema() { super(); }

         public static final String PRIORITY_SORT_ORDER = 
        	 BaseColumns._ID + " ASC";
             //  SubscriptionTableSchemaBase.EXPIRATION + " DESC, " +
             //  SubscriptionTableSchemaBase.MODIFIED_DATE + " DESC ";
      }    
      
}
