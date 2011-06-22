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
      public IAmmoRequest directedPost(Entity recipient);
      public IAmmoRequest publish();
      public IAmmoRequest subscribe();
      public IAmmoRequest retrieve();
      public IAmmoRequest interest();
      public IAmmoRequest replace(IAmmoRequest req);
      public IAmmoRequest replace(String uuid);
      public IAmmoRequest recover(String uuid);
        public static final String DEFAULT_PROVIDER = null;
        public Builder provider(Uri val);
        public static final String DEFAULT_PAYLOAD = "";
        public Builder payload(String val);
        public static final String DEFAULT_TOPIC = "";
        public Builder id(String val);
        public static final Query DEFAULT_DOWNSAMPLE = 0;
        public Builder downsample(Downsample val); 
        public Builder downsample(char val); 
        public static final final int VOLAILE_DURABILITY = 1;
        public static final final int PERSISTENT_DURABILITY = 2;

        public static Duration DEFAULT_DURABILITY = PERSISTENT_DURABILITY;
        public Builder durability(int val);
        public static final int ANY_RECIPIENT = null;

        public static final int DEFAULT_RECIPIENT = ANY_RECIPIENT;
        public Builder recipient(Entity val);
        public Builder source(Entity val);
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
        public Builder priority(int val);
        public static final int DEFAULT_WORTH = 100;
        public Builder worth(int val);
        public static final int DEFAULT_LIVENESS = 0;
        public Builder liveness(int val);
        public static final int DEFAULT_START = Calendar.NOW;
        public Builder start(Calendar val); 
        public static final int DEFAULT_SCOPE = -1;
        public static final int DEFAULT_SCOPE = -1;
        public Builder scope(int val);
        public static final int UNLIMITED_THROTTLE = -1;

        public static final int DEFAULT_THROTTLE = UNLIMITED_THROTTLE;
        public Builder throttle(int val);
         public Event[] getEvent(); 
         public Event[] cancel(); 
         public Event[] expiration(Duration val);
         public String getUuid(); // 
         public void setMetricTimespan(int val);
         public int getTransmissionRate();
         public Calendar getLastMessage();
         public void resetMetrics(int val);
         public int getTotalMessages();
   }
   public interface Entity {
      public String getCallSign();
      public String[] getGroups();
      public String getName(String type); // used e.g. tigr
      public String getName(); // canonical name
   }
   public interface Query {
      public String selection();
      public String[] args();

      public Query selection(String val);
      public Query args(String[] val);
   }
   public interface Form public interface Map<String, String>;
   public interface Downsample {
      public int getMaxSize();
      public double getFraction();
   }
   public enum Place { DISPATCHED , DISTRIBUTED, DELIVERED, COMPLETED }
   public enum Color { SUCCESS, FAIL,  UNKNOWN, REJECTED };
   public interface Event {
      public Place getPlace();
      public Event setPlace(Place val);
      
      public Color getColor();
      public Event setColor(Color val);
   }
   public interface Notice {
      public Event getTarget();
      public Event setTarget(Event val);
      public Event getSource();
      public Event setSource(Event val);
         
      public boolean runAction();
      public Object getAction();
   }
}
