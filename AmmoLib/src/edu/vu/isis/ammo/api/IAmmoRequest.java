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
      public IAmmoRequest directedPost(Recipient recipient);
      public IAmmoRequest publish();
      public IAmmoRequest subscribe();
      public IAmmoRequest retrieve();
      public IAmmoRequest interest();
      public IAmmoRequest replace(IAmmoRequest req);
      public IAmmoRequest replace(String uuid);
      public IAmmoRequest recover(String uuid);
        public static String DEFAULT_PROVIDER = null;
        public Builder provider(Uri val);
        public static String DEFAULT_PAYLOAD = "";
        public Builder payload(String val);
        public static String DEFAULT_TYPE = "";
        public Builder type(String val);

        public static String DEFAULT_TITLE = "";
        public Builder title(String val);
        public static Filter DEFAULT_FILTER = null;
        public Builder filter(Filter val); 
        public static Query DEFAULT_QUERY = null;
        public Builder query(Query val);
        public static Query DEFAULT_DOWNSAMPLE = 0;
        public Builder downsample(Downsample val); 
        public Builder downsample(char val); 
        public static Duration DEFAULT_LOGEVITY = Duration.HOUR;
        public Builder longevity(Duration val);
        public Builder longevity(Calendar val);
        public static int LOW_PRIORITY = -10;
        public static int NORMAL_PRIORITY = 0;
        public static int HIGH_PRIORITY = 10;
        public static int URGENT = 1000;

        public static int DEFAULT_PRIORITY = NORMAL_PRIORITY;
        public Builder priority(int val);
        public static int DEFAULT_WORTH = 100;
        public Builder worth(int val);
        public static int DEFAULT_LIVENESS = 0;
        public Builder liveness(int val);
        public static int DEFAULT_START = Calendar.NOW;
        public Builder start(Calendar val); 
        public static int DEFAULT_SCOPE = -1;
        public Builder scope(int val);
        public static int ANY_RECIPIENT = null;

        public static int DEFAULT_RECIPIENT = ANY_RECIPIENT;
        public Builder recipient(Recipient val);
        public static int UNLIMITED_THROTTLE = -1;

        public static int DEFAULT_THROTTLE = UNLIMITED_THROTTLE;
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
   public interface Recipient {
      public String getCallSign();
      public String[] getGroups();
      public String getName(String type); // used e.g. tigr
      public String getName(); // canonical name
   }
   public interface Filter {
      public Filter get();
   }
   public interface Query {
      public String[] getProjection();
      public String getSelection();
      public String[] getArgs();
      public String[] getGroupBy();
      public String[] getOrderBy();
   }
   public interface Downsample {
      public int getMaxSize();
      public double getFraction();
   }
   public enum Place { QUEUE , DISTRIBUTE, DELIVER, COMPLETE }
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
