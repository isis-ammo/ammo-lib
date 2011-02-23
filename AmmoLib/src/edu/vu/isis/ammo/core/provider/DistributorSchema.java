package edu.vu.isis.ammo.core.provider;

public class DistributorSchema extends DistributorSchemaBase {

   public static final int DATABASE_VERSION = 3;

      public static class DeliveryMechanismTableSchema extends DeliveryMechanismTableSchemaBase {

         protected DeliveryMechanismTableSchema() { super(); }

      }    
      public static class PostalTableSchema extends PostalTableSchemaBase {

         protected PostalTableSchema() { super(); }

         public static final String PRIORITY_SORT_ORDER = 
               PostalTableSchemaBase.EXPIRATION + " DESC, " +
               PostalTableSchemaBase.MODIFIED_DATE + " DESC ";
      }    
    
      public static class RetrivalTableSchema extends RetrivalTableSchemaBase {

         protected RetrivalTableSchema() { super(); }

         public static final String PRIORITY_SORT_ORDER = 
               RetrivalTableSchemaBase.EXPIRATION + " DESC, " +
               RetrivalTableSchemaBase.MODIFIED_DATE + " DESC ";
      }    
      public static class PublicationTableSchema extends PublicationTableSchemaBase {

         protected PublicationTableSchema() { super(); }

         public static final String PRIORITY_SORT_ORDER = 
               PublicationTableSchemaBase.EXPIRATION + " DESC, " +
               PublicationTableSchemaBase.MODIFIED_DATE + " DESC ";
      }    
      public static class SubscriptionTableSchema extends SubscriptionTableSchemaBase {

         protected SubscriptionTableSchema() { super(); }

         public static final String PRIORITY_SORT_ORDER = 
               SubscriptionTableSchemaBase.EXPIRATION + " DESC, " +
               SubscriptionTableSchemaBase.MODIFIED_DATE + " DESC ";
      }    
      
}
