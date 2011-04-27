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
	 * Some broadcast intents
	 */
	public static final Intent LOGIN = new Intent("edu.vu.isis.ammo.auth.LOGIN");
	public static final Intent LOGOUT = new Intent("edu.vu.isis.ammo.auth.LOGOUT");
	
    
	public interface Builder {
		public IAmmoRequest duplicate(); // duplicate (and reset?)
		/**
		 * The following are factory actions which produce Ammo requests
		 * @return
		 */
		public IAmmoRequest post();
		public IAmmoRequest directedPost();
		public IAmmoRequest publish();
		public IAmmoRequest subscribe();
		public IAmmoRequest retrieve();
		
		public IAmmoRequest recover(String uuid); // recover a previously built request, by name
		
		/**
		 * The following parameters are known by the builder.
		*/
		public Builder reset(); // reset all settings to default values.
		public Builder provider(Uri val);
		public Builder payload(String val); // may be used in lieu of provider.
		
		public Builder name(String val);
		public Builder longevity(Duration val); // from the time the request is posted.
		public Builder longevity(Calendar val); // absolute time.
		
		public Builder priority(int val); // 0 : normal, >0 : higher priority, <0 : lower priority
		public Builder notify(State[] val); // notifications to be sent: what, when and why
		public Builder notify(State val);
		
		public Builder liveness();
		public Builder start(Calendar val); // from what time to you want data
		public Builder worth(int val); // acts as a check against priority (does not effect request delivery, but status
		
		public Builder reach(int val); // max number of gateway hops, <= 0 unlimited, generally 0 or 1
		public Builder recipient(Recipient val); // who will get this request?
	}

	public enum Status {
		SUCCESS,  // a final status
		FAIL,     // a final status
		UNKNOWN,  // a mutable status
		REJECTED, // a mutable status (just because one target rejected the task another may accept
	}
	
	public enum Event {
		QUEUED , // placed under the control of the distributor
		DISTRIBUTED, // placed under the control of a gateway
		DELIVERED, // obtained by at least one of the targets
		COMPLETED, // a target has completed handling the task
	}
	
	/** 
	 * A record of the current state of the object
	 *
	 */
	public interface State {
		public Event getEvent();
		public State setEvent(Event val);
		
		public Status getStatus();
		public State setStatus(Status val);
	}
	
	public interface Notice {
		public State getState();
		public State setState(State val);
	}
	
	/**
	 * Works in conjunction with the contact manager.
	 * 
	 */
	public interface Recipient {
		public String getCallSign();
		public String[] getGroups();
		public String getName(String type); // used e.g. tigr
		public String getName(); // canonical name
	}
	
	/**
	 * request control methods
	 * 
	 */
	public State[] getState(); // get the current state of the request
	public State[] cancel(); // cancel the request
	public State[] expiration(Duration val); // change the expiration (longevity) of the request
	
	public String getUuid(); // 
	
	/** 
	 * The methods which change the routing policy are not 
	 * generally available to applications programs.
	 * Only the getters will function for applications programs.
	 */
	public enum NetLinkState {
		
	}
	
	public interface Control {
		public NetLinkState getLinkState();
	}
	
}
