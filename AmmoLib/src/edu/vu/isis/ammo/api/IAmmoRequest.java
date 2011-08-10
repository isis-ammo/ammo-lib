// IAmmoRequest.java
// See docs/java/ammo-api.nw for documentation
package edu.vu.isis.ammo.api;
import java.util.Map;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Parcel;
import android.os.RemoteException;
import edu.vu.isis.ammo.api.type.DeliveryScope;
import edu.vu.isis.ammo.api.type.Oid;
import edu.vu.isis.ammo.api.type.TimeInterval;
import edu.vu.isis.ammo.api.type.TimeStamp;

public interface IAmmoRequest {

   public String uuid();  
   public IAmmoRequest replace(IAmmoRequest req) throws RemoteException;
   public IAmmoRequest replace(String uuid) throws RemoteException;
   public Event[] cancel(); 
   public void metricTimespan(int val);
   public TimeStamp lastMessage();
   public void resetMetrics(int val);
   public Event[] eventSet(); 
   public static final Uri DEFAULT_PROVIDER = null;
   public static final String DEFAULT_PAYLOAD = "";
   
   public static final String DEFAULT_TOPIC = "";

   public static final TimeInterval UNLIMITED_EXPIRE = 
         new TimeInterval(TimeInterval.UNLIMITED);
   public static final TimeInterval DEFAULT_EXPIRE = 
         new TimeInterval(TimeInterval.Unit.HOUR);
   public static final int VOLATILE_DURABILITY = 1;
   public static final int PERSISTENT_DURABILITY = 2;

   public static final int DEFAULT_DURABILITY = PERSISTENT_DURABILITY;
   public static final int BACKGROUND_PRIORITY = -1000;
   public static final int LOW_PRIORITY = -10;
   public static final int NORMAL_PRIORITY = 0;
   public static final int HIGH_PRIORITY = 10;
   public static final int URGENT_PRIORITY = 1000;

   public static final int DEFAULT_PRIORITY = NORMAL_PRIORITY;
   public static final int OLDEST_FIRST_ORDER = 1;
   public static final int NEWEST_ONLY_ORDER = 2;
   public static final int NEWEST_FIRST_ORDER = 3;

   public static final int DEFAULT_ORDER = OLDEST_FIRST_ORDER;
   public static final int DEFAULT_WORTH = 100;

   public static final TimeInterval DEFAULT_START = 
        new TimeInterval(TimeInterval.Unit.MINUTE);
   public static String[] ALL_PROJECT = null;

   public static String[] DEFAULT_PROJECT = ALL_PROJECT;
   public static final Query ALL_SELECT = null;

   public static final Query DEFAULT_SELECT = ALL_SELECT;
   public static Form DEFAULT_FORM = null;
   public static final String NO_FILTER = "";

   public static final String DEFAULT_FILTER = NO_FILTER;
   public static final int NO_DOWNSAMPLE = 0;
   public static final int DEFAULT_DOWNSAMPLE = NO_DOWNSAMPLE;
   public static final Notice DEFAULT_NOTICE = null;
   public static final IAnon ANY_ANON = null;
   public static final IAnon DEFAULT_RECIPIENT = ANY_ANON;
   public static final IAnon DEFAULT_ORIGINATOR = ANY_ANON;

   public static final DeliveryScope DEFAULT_SCOPE = DeliveryScope.GLOBAL;
   public static final int UNLIMITED_THROTTLE = -1;

   public static final int DEFAULT_THROTTLE = UNLIMITED_THROTTLE;
   public static final String DEFAULT_UID = "";
   public static final int DEFAULT_DEPTH = 0;
   public static final int UN_LIMIT = -1;
   public static final int DEFAULT_LIMIT = UN_LIMIT;

   public interface Builder {
      public IAmmoRequest duplicate() throws RemoteException;
      public Builder reset();
      public IAmmoRequest post() throws RemoteException;
      public IAmmoRequest directedSubscribe(IAnon originator) throws RemoteException;
      public IAmmoRequest directedPost(IAnon recipient) throws RemoteException;
      public IAmmoRequest publish() throws RemoteException;
      public IAmmoRequest subscribe() throws RemoteException;
      public IAmmoRequest retrieve() throws RemoteException;
      public IAmmoRequest getInstance(String uuid) throws RemoteException;
        public Builder provider(Uri val);
        public Builder payload(String val);
        public Builder payload(byte[] val);
        public Builder payload(ContentValues val);
        public Builder payload(AmmoValues val);
        public Builder topic(String val);
        public Builder topic(Oid val); 
        // \availability{2.0}
        public Builder uid(String val);
        public Builder expire(TimeInterval val);
        public Builder expire(TimeStamp val);
        public Builder durability(int val);
        public Builder recipient(IAnon val);
        public Builder recipient(String val); 
        public Builder originator(IAnon val);
        public Builder originator(String val); 
        public Builder priority(int val);
        public Builder order(int val);
        public Builder order(String[] val);
        public Builder worth(int val);
        public Builder start(TimeStamp val); 
        public Builder start(TimeInterval val); 
        public Builder scope(DeliveryScope val);
        public Builder throttle(int val);
        public Builder filter(String val); 
        public Builder downsample(int maxSize); 
        public Builder project(String[] val);
        public Builder select(Query val);
        public Builder select(Form val);
		
   }
   public enum Action {
     POSTAL, DIRECTED_POSTAL, PUBLISH, RETRIEVAL, SUBSCRIBE, DIRECTED_SUBSCRIBE;

     @Override
     public String toString() {
         switch (this) {
         case POSTAL: return "POSTAL";
         case DIRECTED_POSTAL: return "DIRECTED POST";
         case PUBLISH: return "PUBLISH";
         case RETRIEVAL: return "RETRIEVAL";
         case SUBSCRIBE: return "SUBSCRIBE";
         case DIRECTED_SUBSCRIBE: return "DIRECTED SUBSCRIBE";
         default: 
             return null;
         }
     }

	static public void writeToParcel(Parcel dest, Action action) {
		dest.writeInt(action.ordinal());
	}
	static public Action getInstance(Parcel in) { 
		return Action.values()[in.readInt()];
	}

   };

   public interface IAnon {
      public String name(); // canonical name
   }

   public interface Warfighter extends IAnon {
      public String callSign();
      public String[] groups();
      public String tigr(); 
   }

   public interface Group extends IAnon {}
   public interface Server extends IAnon {}
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
