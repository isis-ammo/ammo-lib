// IAmmoRequest.java
// See docs/java/ammo-api.nw for documentation
package edu.vu.isis.ammo.api;
import java.util.Map;

import android.content.ContentValues;
import android.net.Uri;
import android.os.RemoteException;
import android.os.Parcel;

import edu.vu.isis.ammo.api.type.DeliveryScope;
import edu.vu.isis.ammo.api.type.Limit;
import edu.vu.isis.ammo.api.type.Notice.Via;
import edu.vu.isis.ammo.api.type.Oid;
import edu.vu.isis.ammo.api.type.Order;
import edu.vu.isis.ammo.api.type.Moment;
import edu.vu.isis.ammo.api.type.Notice;
import edu.vu.isis.ammo.api.type.Quantifier;
import edu.vu.isis.ammo.api.type.TimeInterval;
import edu.vu.isis.ammo.api.type.TimeStamp;

public interface IAmmoRequest {
   public IAmmoRequest replace(IAmmoRequest req) throws RemoteException;
   public IAmmoRequest replace(String uuid) throws RemoteException;
   public void cancel(); 
   public void metricTimespan(Integer val);
   public TimeStamp lastMessage();
   public void resetMetrics(Integer val);
   public static final Uri PROVIDER_DEFAULT = null;
   public static final String PAYLOAD_DEFAULT = "";
   public static final Moment MOMENT_APRIORI = Moment.APRIORI;
   public static final Moment MOMENT_EAGER = Moment.EAGER;
   public static final Moment MOMENT_LAZY = Moment.LAZY;

   public static final Moment MOMENT_DEFAULT = MOMENT_LAZY ;

   public static final String TOPIC_DEFAULT = "";
   public static final String SUBTOPIC_DEFAULT = "";
   public static final Quantifier.Type QUANTIFIER_DEFAULT = Quantifier.Type.BULLETIN;

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
   public static final Order ORDER_OLDEST_FIRST = Order.OLDEST_FIRST;
   public static final Order ORDER_NEWEST_FIRST = Order.NEWEST_FIRST;

   public static final Order ORDER_DEFAULT = ORDER_OLDEST_FIRST ;
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
   
   public static final DeliveryScope SCOPE_DEFAULT = DeliveryScope.GLOBAL;
   public static final Integer THROTTLE_UNLIMITED = -1;

   public static final Integer THROTTLE_DEFAULT = THROTTLE_UNLIMITED ;
   public static final String UID_DEFAULT = "";
   public static final Limit LIMIT_NONE = new Limit();
   public static final Limit LIMIT_DEFAULT = LIMIT_NONE ;

   public interface Builder {
      public IAmmoRequest duplicate() throws RemoteException;
      public Builder reset();
      public IAmmoRequest post() throws RemoteException;
      public IAmmoRequest interest() throws RemoteException;
      public IAmmoRequest subscribe() throws RemoteException;
      public IAmmoRequest retrieve() throws RemoteException;
      public IAmmoRequest getInstance(String uuid) throws RemoteException;
      public void releaseInstance();
      public Builder notice(Notice.Threshold threshold, Via via);
      public Builder notice(Notice val);
        public Builder provider(Uri val);
        public Builder payload(String val);
        public Builder payload(byte[] val);
        public Builder payload(ContentValues val);
        public Builder payload(AmmoValues val);
        public Builder topic(String val);
        public Builder subtopic(String val);
        public Builder quantifier(String val);
        public Builder topic(String major, String minor, String quantifier);

        public Builder topic(Oid val); 
        public Builder subtopic(Oid val);
        public Builder quantifier(Quantifier.Type quantifier);
        public Builder topic(Oid major, Oid minor, Quantifier.Type quantifier); 


        // \availability{2.0}
        public Builder uid(String val);
        public Builder expire(TimeInterval val);
        public Builder expire(TimeStamp val);
        public Builder limit(int val);
        public Builder limit(Limit val);
        public Builder durability(Integer val);
        public Builder moment(String val);
        public Builder moment(Moment val);
        public Builder priority(Integer val);
        public Builder order(Order val);
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
     POSTAL, RETRIEVAL, INTEREST;

     @Override
     public String toString() {
         switch (this) {
         case POSTAL: return "POSTAL";
         case RETRIEVAL: return "RETRIEVAL";
         case INTEREST: return "INTEREST";
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

   public interface Query {
      public String select();
      public String[] args();

      public Query args(String[] val);
   }
   public interface Form extends Map<String, String> {}

}
