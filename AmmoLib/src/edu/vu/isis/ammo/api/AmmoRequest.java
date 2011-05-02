package edu.vu.isis.ammo.api;

import android.app.PendingIntent;
import android.content.Intent;
	
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
	
	/**
	 * When the transition is noticed the PendingIntent is run.
	 */
	public abstract static class NoticePendingIntent implements IAmmoRequest.Notice {
		protected NoticePendingIntent() {}
		
		static public IAmmoRequest.Notice make(State level, PendingIntent action) { 
			return null; 
		} 
		abstract public PendingIntent getAction();
	}
	/**
	 * When the transition is noticed the content observer it notified.
	 */
	public abstract static class NoticeRunnable implements IAmmoRequest.Notice {
		protected NoticeRunnable() {}
		
		static public IAmmoRequest.Notice make(State level, Runnable actor) { 
			return null; 
		} 
		abstract public PendingIntent getAction();
	}
	/**
	 * When the transition is noticed the Intent is send as a broadcast intent
	 */
	public abstract static class NoticeIntent implements IAmmoRequest.Notice {
		protected NoticeIntent() {}
	
		static public Notice make(State level, Intent intent) { 
			return null; 
		} 
		abstract public PendingIntent getAction();
	}
	
	public abstract static class Recipient implements IAmmoRequest.Recipient {
		static public Recipient make(String name) { return null; }
	}
	
}
