package edu.vu.isis.ammo.api;

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
		public IAmmoRequest directedPost();
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
		
		public Builder name(String val);
		/**
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
		 * >0 : higher priority, 
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
		public Builder notify(State[] val);
		public Builder notify(State val);
		
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
		
	}

	/**
	 * rejected is mutable because although one target rejected the task another may accept
	 */
	public enum Status {
		SUCCESS,  // a final status
		FAIL,     // a final status
		UNKNOWN,  // a mutable status
		REJECTED, // a mutable status 
	}
	
	public enum Event {
		QUEUED , // placed under the control of the distributor
		DISTRIBUTED, // placed under the control of a gateway
		DELIVERED, // obtained by at least one of the targets
		COMPLETED, // a target has completed handling the task
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
	 */
	public interface Transition {
		public State getTarget();
		public State setTarget(State val);
		public State getSource();
		public State setSource(State val);
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
	 * @return
	 */
	public String getUuid(); // 
	
	/** 
	 * The methods which change the routing policy are not 
	 * generally available to applications programs.
	 * Only the getters will function for applications programs.
	 */
	public enum NetLinkState {
		DISCONNECTED     ("Disconnected"),
		IDLE             ("Idle"),
		SCANNING         ("Scanning"),
		CONNECTING       ("Connecting"),
		AUTHENTICATING   ("Authenticating"),
		OBTAINING_IPADDR ("Obtaining IP Address"),
		FAILED           ("Failed"),
		CONNECTED        ("Connected");

		private final String text;  
		NetLinkState(String text) {
			this.text = text;
		}
		public String text()   { return this.text; }
	}

	public interface NetLink {
		public NetLinkState getState();
		/**
		 * Set the timespan for collecting metrics
		 */
		public Gateway setMetricTimespan(Duration span);
		public int getLatencyTypical();
		public int getThroughput();
		
		public Gateway[] getGateways();
	}

	public enum GatewayState {
		DISCONNECTED     ("Disconnected"),
		IDLE             ("Idle"),
		AUTHENTICATING   ("Authenticating"),
		FAILED           ("Failed"),
		CONNECTED        ("Connected");

		private final String text;  
		GatewayState(String text) {
			this.text = text;
		}
		public String text()   { return this.text; }
	}
	public interface Gateway {
		public GatewayState getState();
		/**
		 * Set the timespan for collecting metrics
		 */
		public Gateway setMetricTimespan(Duration span);
		public int getLatencyTypical();
		public int getThroughput();
		
		public NetLink[] getNetworkLinks();
	}
	
	public interface NetworkController {
		public NetLink[] getNetworkLinks();
		public Gateway[] getGateways();
	}
	
	/**
	 * TODO:
	 */
	/*
	 subscribe/publish/post/interest:
	   data transmission rate
	   last message
	   total messages
	   setMetricTimespan()
	   
	   notify() functor
	   
	   routing policy
	     gateway: 
	       number of active connections
	       number of load requests
	       
	   click time: set and get
	   
	   gateway: 
	     liveness (% up time)
	     latency (when live)
	     throughput (rate when alive)
	     
	   economy:
	     measures of efficient and effective use
	     
	   various filters:
	     filter - match
	     query - set theory (sql like)
	     downsample - for images and audio
	   
	   quality of service:
	     
	 */
	
}
