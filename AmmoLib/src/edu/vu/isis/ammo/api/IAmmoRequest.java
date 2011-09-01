// IAmmoRequest.java
// See docs/java/ammo-api.nw for documentation
package edu.vu.isis.ammo.api;
import java.util.Map;

import android.content.ContentValues;
import android.net.Uri;
import android.os.RemoteException;
import android.os.Parcel;

import edu.vu.isis.ammo.api.type.DeliveryScope;
import edu.vu.isis.ammo.api.type.Oid;
import edu.vu.isis.ammo.api.type.TimeInterval;
import edu.vu.isis.ammo.api.type.TimeStamp;

public interface IAmmoRequest {
   public String uuid();  
   public IAmmoRequest replace(IAmmoRequest req) throws RemoteException;
   public IAmmoRequest replace(String uuid) throws RemoteException;
   public Event[] cancel(); 
   public void metricTimespan(Integer val);
   public TimeStamp lastMessage();
   public void resetMetrics(Integer val);
   public Event[] eventSet(); 
   public static final Uri PROVIDER_DEFAULT = null;
   public static final String PAYLOAD_DEFAULT = "";

   public static final String TOPIC_DEFAULT = "";

   public static final TimeInterval EXPIRE_UNLIMITED = 
         new TimeInterval(TimeInterval.UNLIMITED);
   public static final TimeInterval EXPIRE_DEFAULT = 
         new TimeInterval(TimeInterval.Unit.HOUR);
   public static final Integer DURABILITY_VOLATILE = 1;
   public static final Integer DURABILITY_PERSISTENT = 2;

   public static final Integer DURABILITY_DEFAULT = DURABILITY_PERSISTENT ;
   public static final Integer PRIORITY_BACKGROUND = -1000;
   public static final Integer PRIORITY_LOW = -10;
   public static final Integer PRIORITY_NORMAL = 0;
   public static final Integer PRIORITY_HIGH = 10;
   public static final Integer PRIORITY_URGENT = 1000;

   public static final Integer PRIORITY_DEFAULT = PRIORITY_NORMAL ;
   public static final Integer ORDER_OLDEST_FIRST = 1;
   public static final Integer ORDER_NEWEST_ONLY = 2;
   public static final Integer ORDER_NEWEST_FIRST = 3;

   public static final Integer ORDER_DEFAULT = ORDER_OLDEST_FIRST ;
   public static final Integer WORTH_DEFAULT = 100;

   public static final TimeInterval START_DEFAULT = 
        new TimeInterval(TimeInterval.Unit.MINUTE);
   public static String[] PROJECT_ALL = null;

   public static String[] PROJECT_DEFAULT = PROJECT_ALL ;
   public static final Query SELECT_ALL = null;

   public static final Query SELECT_DEFAULT = SELECT_ALL ;
   public static Form FORM_DEFAULT = null;
   public static final String FILTER_NO = "";

   public static final String FILTER_DEFAULT = FILTER_NO ;
   public static final Integer DOWNSAMPLE_NO = 0;
   public static final Integer DOWNSAMPLE_DEFAULT = DOWNSAMPLE_NO ;
   public static final Notice NOTICE_DEFAULT = null;
   public static final IAnon ANON_ANY = null;
   public static final IAnon RECIPIENT_DEFAULT = ANON_ANY ;
   public static final IAnon ORIGINATOR_DEFAULT = ANON_ANY ;

   public static final DeliveryScope SCOPE_DEFAULT = DeliveryScope.GLOBAL;
   public static final Integer THROTTLE_UNLIMITED = -1;

   public static final Integer THROTTLE_DEFAULT = THROTTLE_UNLIMITED ;
   public static final String UID_DEFAULT = "";
   public static final Integer DEPTH_DEFAULT = 0;
   public static final Integer LIMIT_NONE = -1;
   public static final Integer LIMIT_DEFAULT = LIMIT_NONE ;

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
        public Builder durability(Integer val);
        public Builder recipient(IAnon val);
        public Builder recipient(String val);
        public Builder originator(IAnon val);
        public Builder originator(String val);
        public Builder priority(Integer val);
        public Builder order(Integer val);
        public Builder order(String[] val);
        public Builder worth(Integer val);
        public Builder start(TimeStamp val); 
        public Builder start(TimeInterval val); 
        public Builder scope(DeliveryScope val);
        public Builder throttle(Integer val);
        public Builder filter(String val); 
        public Builder downsample(Integer maxSize); 
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
