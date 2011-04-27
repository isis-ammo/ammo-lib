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
	
	public abstract static class Builder implements IAmmoRequest.Builder {
		static public Builder make() { return null; }
	}
	public abstract static class State implements IAmmoRequest.State {
		static public State make(Event level, Status state) { return null; }
	}
	
	public abstract static class Notice implements IAmmoRequest.Notice {
		static public Notice make(State level, PendingIntent action) { return null; } // an action to be taken
		static public Notice make(State level, ContentObserver observer) { return null; } // an observer to be triggered
		static public Notice make(State level, Intent intent) { return null; } // and intent to be broadcast
	}
	
	public abstract static class Recipient implements IAmmoRequest.Recipient {
		static public Recipient make(String name) { return null; }
	}
}
