package edu.vu.isis.ammo.api;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.ContentObserver;

/**
 * This API supersedes the AmmoDispatcher calls.
 * Those methods will still work but they are deprecated.
 * 
 * This abstract class supplies factory methods
 * @author phreed
 *
 */
abstract public class AmmoRequest implements IAmmoRequest {
	
	/**
	 * Used to reconnect to a lost request.
	 * This would be used by an Activity which is restarting and needs
	 * to reestablish an AmmoRequest object.
	 * @param uuid
	 * @return
	 */
	
	public abstract static class Builder implements IAmmoRequest.Builder {
		static public Builder make() { return null; }
	}
	public abstract static class State implements IAmmoRequest.State {
		static public State make(Event level, Status state) { return null; }
	}
	
	public abstract static class Transition implements IAmmoRequest.Transition {
		/**
		 *  an action to be taken
		 * @param level
		 * @param action
		 * @return
		 */
		static public Transition make(State level, PendingIntent action) { return null; } 
		/**
		 * an observer to be triggered
		 * @param level
		 * @param observer
		 * @return
		 */
		static public Transition make(State level, ContentObserver observer) { return null; } 
		/**
		 * an intent to be broadcast
		 * @param level
		 * @param intent
		 * @return
		 */
		static public Transition make(State level, Intent intent) { return null; } 
	}
	
	public abstract static class Recipient implements IAmmoRequest.Recipient {
		static public Recipient make(String name) { return null; }
	}
	
	public abstract static class  NetLink implements IAmmoRequest.NetLink {
		static public NetLink make(String name) { return null; }
	}

	public abstract static class  Gateway implements IAmmoRequest.Gateway {
		static public Gateway make(String name) { return null; }	
	}
	
	public abstract static class  NetworkController implements IAmmoRequest.NetworkController {
		static public NetworkController make(String name) { return null; }
	}
}
