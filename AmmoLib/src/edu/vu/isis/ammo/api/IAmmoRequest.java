// IAmmoRequest.java
package edu.vu.isis.ammo.api;
/**
<<<<<<< HEAD
<<<<<<< HEAD
  See docs/java/ammo-api.nw for documentation
*/ 
=======
 * See also AmmoRequest.java IAmmoPolicy.java and AmmoPolicy.java
 * 
 * An IAmmoRequest is an immutable object.
 * They come in two main varieties: Data, and Interest.
 * Data is a request for new content to be injected into the system.
 * Interest is a request to have content delivered.
 * 
 * Requests are immutable so that when a named piece of data
 * is obtained it is known that it is correct and is not waiting
 * for additional elements. (data-flow variables)
 * 
 */


>>>>>>> origin/newnet
=======
  See docs/java/ammo-api.nw for documentation
*/ 
>>>>>>> master
import java.util.Calendar;

import javax.xml.datatype.Duration;

import android.content.Intent;
import android.net.Uri;

public interface IAmmoRequest {
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> master
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

        public Builder provider(Uri val);
        public Builder payload(String val);
        public Builder type(String val);
        public Builder name(String val);
        public Builder filter(Filter val); 
        public Builder query(Query val);
        public Builder downsample(Downsample val); 
        public Builder downsample(char val); 


<<<<<<< HEAD
        public Builder longevity(Duration val);
        public Builder longevity(Calendar val);

        public Builder priority(int val);

        public Builder worth(int val);

              
        public Builder liveness(int val);
        public Builder start(Calendar val); 
        public Builder scope(int val);
        public Builder recipient(Recipient val);
        public Builder maxTransmissionRate(int val);

              

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

=======
	
	/**
	 * the operator has been authorized 
	 */
	public static final Intent LOGIN = new Intent("edu.vu.isis.ammo.auth.LOGIN");
	public static final Intent LOGOUT = new Intent("edu.vu.isis.ammo.auth.LOGOUT");
	
	/**
	 * the gateway event is raised when the authorized gateway changes.
	 */
	public static final Intent GATEWAY_EVENT = new Intent("edu.vu.isis.ammo.auth.GATEWAY");
    
	public interface Builder {
		/**
		 * duplicate (and reset?)
		 * @return
		 */
		public IAmmoRequest duplicate();
		/**
		 * The following are factory actions which produce Ammo requests
		 * @return 
		 */
		public IAmmoRequest post();
		public IAmmoRequest directedPost(Recipient recipient);
		public IAmmoRequest publish();
		public IAmmoRequest subscribe();
		public IAmmoRequest retrieve();
		
		/**
		 * rather than trying to dynamically update an object.
		 * These methods are used to replace an existing request.
		 */
		public IAmmoRequest replace(String uuid);
		public IAmmoRequest replace(IAmmoRequest req);
		
		/**
		 * recover a previously built request, by name
		 * @param uuid
		 * @return
		 */
		public IAmmoRequest recover(String uuid);
		
		/**
		 * The following parameters are known by the builder.
		*/
		/**
		 * reset all settings to default values.
		 */
		public Builder reset();
		/**
		 * The content provider identifier, may also contain a specific row.
		 * @param val
		 * @return
		 */
		public Builder provider(Uri val);
		/**
		 * may be used in lieu of provider.
		 * @param val
		 * @return
		 */
		public Builder payload(String val);
		
		/**
		 * The type of the data being injected.
		 * 
		 * @param val
		 * @return
		 */
		public Builder type(String val);
		
		/**
		 * The unique name of the data being injected.
		 * The name only need be unique in the context of the type.
		 * 
		 * @param val
		 * @return
		 */
		public Builder name(String val);
		
		/**
		 * How long will the data item persist.
		 * 
		 * from the time the request is posted.
		 * @param val
         * @return
		 */
        public Builder longevity(Duration val);
        /**
         * absolute time.
         * @param val
         * @return
         */
		public Builder longevity(Calendar val);
		/**
		 * 0 : normal, 
		 * >0 : higher priority, (these are sent first)
		 * <0 : lower priority
		 * @param val
		 * @return
		 */
		public Builder priority(int val);
		/**
		 * notifications to be sent: what, when and why
		 * @param val
		 * @return
		 */
		public Builder notify(Notice[] val);
		public Builder notify(Notice val);
		
		public Builder liveness();
        /**
         * from what time to you want data
         * @param val
         * @return
         */
		public Builder start(Calendar val); 
		/**
		 * acts as a check against priority (does not effect request delivery, but status
		 * @param val
		 * @return
		 */
		public Builder worth(int val);
		/**
		 * max number of gateway hops, <= 0 unlimited, generally 0 or 1
		 * @param val
		 * @return
		 */
		public Builder reach(int val);
		/**
		 * who will get this request?
		 * @param val
		 * @return
		 */
		public Builder recipient(Recipient val);
		
		/**
		 * The maximum number of bits per second.
		 * @param val
		 * @return
		 */
		public Builder maxTransmissionRate(int val);
		
		/**
		 * various filtering mechanisms
		 */
		public Builder filter(Filter val); 
		public Builder query(Query val);
		public Builder downsample(Downsample val); 
		   
		/**
		   quality of service:
		 */
		
	}
>>>>>>> origin/newnet
=======
        public Builder longevity(Duration val);
        public Builder longevity(Calendar val);

        public Builder priority(int val);

        public Builder worth(int val);

              
        public Builder liveness(int val);
        public Builder start(Calendar val); 
        public Builder scope(int val);
        public Builder recipient(Recipient val);
        public Builder maxTransmissionRate(int val);

              

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

>>>>>>> master


}
   
