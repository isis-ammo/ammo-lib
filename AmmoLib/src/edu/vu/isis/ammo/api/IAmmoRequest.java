// IAmmoRequest.java
// See docs/java/ammo-api.nw for documentation
package edu.vu.isis.ammo.api;
import java.util.Calendar;

import javax.xml.datatype.Duration;

import android.content.Intent;
import android.net.Uri;

public interface IAmmoRequest {
   public interface Builder {
      public IAmmoRequest duplicate();
      public Builder reset();
      public IAmmoRequest post();
      public IAmmoRequest directedPost(Anon recipient);
      public IAmmoRequest publish();
      public IAmmoRequest subscribe();
      public IAmmoRequest retrieve();
      public IAmmoRequest replace(IAmmoRequest req);
      public IAmmoRequest replace(String uuid);
        public static final String DEFAULT_PROVIDER = null;
        public Builder provider(Uri val);
        public static final String DEFAULT_PAYLOAD = "";

        public Builder payload(String val);
        public Builder payload(byte[] val);
        public Builder payload(ContentValues val);
        public static final String DEFAULT_TYPE = "";
        public Builder type(String val);
        public Builder type(Oid val);  // V:2.0
        public static final String DEFAULT_UID = "";
        public Builder uid(String val);
        public static final int NO_DOWNSAMPLE = 0;

        public static final int DEFAULT_DOWNSAMPLE = NO_DOWNSAMPLE;
        public Builder downsample(int maxSize); 
        public static final final int VOLAILE_DURABILITY = 1;
        public static final final int PERSISTENT_DURABILITY = 2;

        public static Duration DEFAULT_DURABILITY = PERSISTENT_DURABILITY;
        public Builder durability(int val);
        public static final int ANY_ANON = null;

        public static final int DEFAULT_RECIPIENT = ANY_ANON;
        public static final int DEFAULT_ORIGINATOR = ANY_ANON;
        public Builder recipient(Anon val);
        public Builder originator(Anon val);
        public static final int BACKGROUND_PRIORITY = -1000;
        public static final int LOW_PRIORITY = -10;
        public static final int NORMAL_PRIORITY = 0;
        public static final int HIGH_PRIORITY = 10;
        public static final int URGENT_PRIORITY = 1000;

        public static final int DEFAULT_PRIORITY = NORMAL_PRIORITY;
        public Builder priority(int val);
        public static final int OLDEST_FIRST_ORDER = 1;
        public static final int NEWEST_ONLY_ORDER = 2;
        public static final int NEWEST_FIRST_ORDER = 3;

        public static final int DEFAULT_ORDER = OLDEST_FIRST_ORDER;
        public Builder order(int val);
        public Builder order(String[] val);
        public static final int DEFAULT_START = Calendar.NOW;
        public Builder start(Calendar val); 
        public Builder start(Duration val); 
        public static final int IMMEDIATE_SCOPE = 0;
        public static final int LOCAL_SCOPE = 1;
        public static final int GATEWAY_SCOPE = 2;
        public static final int UNLIMITED_SCOPE = -1;

        public static final int DEFAULT_SCOPE = UNLIMITED_SCOPE;
        public Builder scope(int val);
        public static final int UNLIMITED_THROTTLE = -1;

        public static final int DEFAULT_THROTTLE = UNLIMITED_THROTTLE;
        public Builder throttle(int val);
         public String uuid();  
         public Event[] cancel(); 
         public void metricTimespan(int val);
         public Calendar lastMessage();
         public void resetMetrics(int val);
         public Event[] eventSet(); 
   }
   public interface Anon {
      public String name(); // canonical name
   }

   public interface Warfighter extends Anon {
      public String callSign();
      public String[] groups();
      public String tigr(); 
   }

   public interface Group extends Anon {}
   public interface Server extends Anon {}
   public interface Query {
      public String selection();
      public String[] args();

      public Query selection(String val);
      public Query args(String[] val);
   }
   public interface Form public interface Map<String, String>;
   public enum DeliveryProgress { DISPATCHED , DISTRIBUTED, DELIVERED, COMPLETED }
   public enum DeliveryState { SUCCESS, FAIL,  UNKNOWN, REJECTED };
   public interface Event {
      public DeliveryProgress progress();
      public Event progress(DeliveryProgress val);
      
      public DeliveryState state();
      public Event state(DeliveryState val);
   }
   public interface Notice {
      public Event target();
      public Notice target(Event val);

      public Event source();
      public Notice source(Event val);
         
      public boolean act();
      public Object action();
      public Notice action(Object val);
   }
}
