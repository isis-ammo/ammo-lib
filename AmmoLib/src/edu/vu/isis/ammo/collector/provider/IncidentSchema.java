package edu.vu.isis.ammo.collector.provider;

public class IncidentSchema extends IncidentSchemaBase {

   public static final int DATABASE_VERSION = 1;

      public static class MediaTableSchema extends MediaTableSchemaBase {

         protected MediaTableSchema() { } // no instantiation

	 public static final String IMAGE_DATA_TYPE = "image/jpeg";
         public static final String AUDIO_DATA_TYPE = "audio/basic";
         public static final String TEXT_DATA_TYPE = "text/plain";
         public static final String VIDEO_DATA_TYPE = "video/3gpp";
         public static final String TEMPLATE_DATA_TYPE = "text/template";
      }    
      public static class EventTableSchema extends EventTableSchemaBase {

         public static final String TIGR_TOPIC = "application/vnd.edu.vu.isis.ammo.map.object";
         protected EventTableSchema() { super(); }

         public static final int STATUS_DRAFT = 1;
         public static final int STATUS_LOCAL_PENDING = 2;
         public static final int STATUS_SENT = 3;
         public static final int _DISPOSITION_DRAFT = 4;
         public static final int _DISPOSITION_LOCAL_PENDING = 5;
      }    
      public static class CategoryTableSchema extends CategoryTableSchemaBase {

         public static final String RELOAD = "edu.vu.isis.ammo.collector.provider.incident.category.action.RELOAD";
         public static final String RELOAD_FINISHED = "edu.vu.isis.ammo.collector.RELOAD_FINISHED";
         
         protected CategoryTableSchema() { super(); }
      }    
}
	
