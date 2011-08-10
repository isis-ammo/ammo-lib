package edu.vu.isis.ammo.core.provider;

import android.provider.BaseColumns;

public class DistributorSchema extends DistributorSchemaBase {

   public static final int DATABASE_VERSION = 6;

      public static class PostalTableSchema extends PostalTableSchemaBase {

         protected PostalTableSchema() { super(); }

         public static final String PRIORITY_SORT_ORDER;
         static {
            StringBuilder sb = new StringBuilder();
            sb.append('"').append(PostalTableSchemaBase.PRIORITY).append('"').append(" DESC ");
            sb.append(',');
            sb.append('"').append(BaseColumns._ID).append('"').append(" ASC ");
            PRIORITY_SORT_ORDER = sb.toString();
         }
      }    
    
      public static class RetrievalTableSchema extends RetrievalTableSchemaBase {

         protected RetrievalTableSchema() { super(); }

         public static final String PRIORITY_SORT_ORDER;
         static {
            StringBuilder sb = new StringBuilder();
            sb.append('"').append(RetrievalTableSchemaBase.PRIORITY).append('"').append(" DESC ");
            sb.append(',');
            sb.append('"').append(BaseColumns._ID).append('"').append(" ASC ");
            PRIORITY_SORT_ORDER = sb.toString();
         }
      }    
      public static class PublicationTableSchema extends PublicationTableSchemaBase {

         protected PublicationTableSchema() { super(); }

         public static final String PRIORITY_SORT_ORDER;
         static {
            StringBuilder sb = new StringBuilder();
            sb.append('"').append(PostalTableSchemaBase.PRIORITY).append('"').append(" DESC ");
            sb.append(',');
            sb.append('"').append(BaseColumns._ID).append('"').append(" ASC ");
            PRIORITY_SORT_ORDER = sb.toString();
         }
      }    
      public static class SubscriptionTableSchema extends SubscriptionTableSchemaBase {

         protected SubscriptionTableSchema() { super(); }

         public static final String PRIORITY_SORT_ORDER;
         static {
            StringBuilder sb = new StringBuilder();
            sb.append('"').append(PostalTableSchemaBase.PRIORITY).append('"').append(" DESC ");
            sb.append(',');
            sb.append('"').append(BaseColumns._ID).append('"').append(" ASC ");
            PRIORITY_SORT_ORDER = sb.toString();
         }
      }    
      
}
