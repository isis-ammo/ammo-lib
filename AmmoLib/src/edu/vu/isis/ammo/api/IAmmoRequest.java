
package edu.vu.isis.ammo.api;
/**
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


import java.util.Calendar;

import javax.xml.datatype.Duration;

import android.content.Intent;
import android.net.Uri;

/**
 * This API supersedes the AmmoDispatcher calls.
 * Those methods will still work but they are deprecated.
 * 
 * @author phreed
 *
 */
public interface IAmmoRequest {
	
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
		 * The name of the data being injected or the
		 * prefix of the data being requested.
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

	/**
	 * rejected is mutable because although one target rejects a task another may accept
	 */

	public enum Event {
		QUEUE , // placed under the control of the distributor
		DISTRIBUTE, // placed under the control of a gateway
		DELIVER, // obtained by at least one of the targets
		COMPLETE, // a target has completed handling the task
	}
	
	public enum Status {
		SUCCESS,  // a final status
		FAIL,     // a final status
		UNKNOWN,  // a mutable status
		REJECTED, // a mutable status 
	}
	
	/** 
	 * A record of the current state of the object
	 */
	public interface State {
		public Event getEvent();
		public State setEvent(Event val);
		
		public Status getStatus();
		public State setStatus(Status val);
	}
	
	/**
	 * When a change is made from one state to another.
	 * A null state indicates any.
	 * The action 
	 */
	public interface Notice {
		public State getTarget();
		public State setTarget(State val);
		public State getSource();
		public State setSource(State val);
		
		public boolean runAction();
		public Object getAction();
	}
	
	
	/**
	 * Works in conjunction with the contact manager.
	 */
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
	
	/**
	 * An object can be down sampled to either a maximum size
	 * or a fraction of its original size.
	 */
	public interface Downsample {
		public int getMaxSize();
		public double getFraction();
	}
	
	// ********** Request control methods ***************
	/**
	 * get the current state of the request
	 */
	public State[] getState(); 
	/**
	 * cancel the request
	 * @return
	 */
	public State[] cancel(); 
	/**
	 *  change the expiration (longevity) of the request
	 * @param val
	 * @return
	 */
	public State[] expiration(Duration val);
	/**
	 * This is provided to work in conjunction with AmmoRequest.make(uuid);
	 * It is used to preserve references to AmmoRequest objects across a restart
	 * @return
	 */
	public String getUuid(); // 
	
	/**
	 * Sets the timespan used to measure various metrics.
	 * e.g. transmission rate
	 */
	public void setMetricTimespan(int val);
	public int getTransmissionRate();
	
	/**
	 * When was the last message received.
	 */
	public Calendar getLastMessage();
	
	/**
	 * Reset all statistics, counters, averages, but not time span
	 * @param val
	 */
	public void resetMetrics(int val);
	public int getTotalMessages();
	 
}
