// IAmmoRequest.java
// See docs/java/ammo-api.nw for documentation
package edu.vu.isis.ammo.api;
import java.util.Map;

import android.content.ContentValues;
import android.net.Uri;
import android.os.RemoteException;

public interface IAmmoRequest {
   public String uuid();  
   public IAmmoRequest replace(IAmmoRequest req) throws RemoteException;
   public IAmmoRequest replace(String uuid) throws RemoteException;
   public Event[] cancel(); 
   public void metricTimespan(int val);
   public TimeStamp lastMessage();
   public void resetMetrics(int val);
   public Event[] eventSet(); 

   public interface Builder {
      public IAmmoRequest duplicate();
      public Builder reset();
      public IAmmoRequest post() throws RemoteException;
      public IAmmoRequest directedSubscribe(Anon originator) throws RemoteException;
      public IAmmoRequest directedPost(Anon recipient) throws RemoteException;
      public IAmmoRequest publish() throws RemoteException;
      public IAmmoRequest subscribe() throws RemoteException;
      public IAmmoRequest retrieve() throws RemoteException;
      public IAmmoRequest getInstance(String uuid) throws RemoteException;
        public Builder provider(Uri val);

        public static final Uri DEFAULT_PROVIDER = null;

        public enum PayloadType { NONE, STR, BYTE, CV };
        public static final String DEFAULT_PAYLOAD = "";

        public Builder payload(String val);
        public Builder payload(byte[] val);
        public Builder payload(ContentValues val);
        public Builder payload(AmmoValues val);
        public enum TopicEncoding { STR, OID };

        public Builder topic(String val);
        public Builder topic(Oid val); 
        // \availability{2.0}

        public static final String DEFAULT_TOPIC = "";

        public static final String DEFAULT_UID = "";
        public Builder uid(String val);
        public enum ExpireType { ABS, REL };

        public Builder expire(TimeInterval val);
        public Builder expire(TimeStamp val);

        public static final TimeInterval UNLIMITED_EXPIRE = 
              new TimeInterval(TimeInterval.UNLIMITED);
        public static final TimeInterval DEFAULT_EXPIRE = 
              new TimeInterval(TimeInterval.Unit.HOUR);

        public static final int VOLATILE_DURABILITY = 1;
        public static final int PERSISTENT_DURABILITY = 2;

        public static final int DEFAULT_DURABILITY = PERSISTENT_DURABILITY;
        public Builder durability(int val);
        public static final Anon ANY_ANON = null;

        public Builder recipient(Anon val);
        public Builder originator(Anon val);

        public static final Anon DEFAULT_RECIPIENT = ANY_ANON;
        public static final Anon DEFAULT_ORIGINATOR = ANY_ANON;

        public static final int BACKGROUND_PRIORITY = -1000;
        public static final int LOW_PRIORITY = -10;
        public static final int NORMAL_PRIORITY = 0;
        public static final int HIGH_PRIORITY = 10;
        public static final int URGENT_PRIORITY = 1000;

        public Builder priority(int val);

        public static final int DEFAULT_PRIORITY = NORMAL_PRIORITY;

        public static final int OLDEST_FIRST_ORDER = 1;
        public static final int NEWEST_ONLY_ORDER = 2;
        public static final int NEWEST_FIRST_ORDER = 3;

        public Builder order(int val);
        public Builder order(String[] val);

        public static final int DEFAULT_ORDER = OLDEST_FIRST_ORDER;

        public enum StartType { ABS, REL };

        public Builder start(TimeStamp val); 
        public Builder start(TimeInterval val); 

        public static final TimeInterval DEFAULT_START = 
             new TimeInterval(TimeInterval.Unit.MINUTE);

        public enum DeliveryScope {
           IMMEDIATE, LOCAL, GLOBAL, RECIPIENT 
        };

        public Builder scope(DeliveryScope val);

        public static final DeliveryScope DEFAULT_SCOPE = DeliveryScope.GLOBAL;
        public static final int UNLIMITED_THROTTLE = -1;

        public Builder throttle(int val);

        public static final int DEFAULT_THROTTLE = UNLIMITED_THROTTLE;

        public static final String NO_FILTER = "";

        public Builder filter(String val); 

        public static final String DEFAULT_FILTER = NO_FILTER;

        public static final int NO_DOWNSAMPLE = 0;
        public static final int DEFAULT_DOWNSAMPLE = NO_DOWNSAMPLE;

        public Builder downsample(int maxSize); 
        public static String[] ALL_PROJECT = null;

        public Builder project(String[] val);

        public static String[] DEFAULT_PROJECT = ALL_PROJECT;

        public static final Query ALL_SELECT = null;
        public enum SelectType { QUERY, FORM };

        public Builder select(Query val);

        public static final Query DEFAULT_SELECT = ALL_SELECT;

        public static Form DEFAULT_FORM = null;
        public Builder select(Form val); 
   }
   public enum Action {
     POSTAL, DIRECTED_POSTAL, PUBLISH, RETRIEVAL, SUBSCRIBE, DIRECTED_SUBSCRIBE
   };

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
      public String select();
      public String[] args();

      public Query args(String[] val);
   }
   public interface Form extends Map<String, String> {}
   public enum DeliveryProgress { 
      DISPATCHED, DISTRIBUTED, 
      DELIVERED, COMPLETED 
   };
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
