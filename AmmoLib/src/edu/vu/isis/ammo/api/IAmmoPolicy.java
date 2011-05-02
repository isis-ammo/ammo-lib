package edu.vu.isis.ammo.api;

import javax.xml.datatype.Duration;
import java.util.Calendar;

/**
 * This API provides insight into the network policy
 * and the resulting performance and economy.
 * 
 */
public interface IAmmoPolicy {
	
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
		public int getLatency();
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
		 * Set the time span for collecting metrics
		 * i.e. throughput and latency
		 */
		public Gateway setMetricTimespan(Duration span);
		
		/**
		 * liveness in proportion of time up.
		 */
		public float getLiveness();
		/**
		 * when up how long does a message take in response 
		 * to a request in milliseconds
		 */
		public int getLatency();
		/**
		 * when up how many bits per second
		 */
		public float getThroughput();
		/**
		 * how many active gateway connections, load measure
		 */
		public int getActiveConnectionCount();
		
		public NetLink[] getNetworkLinks();
		public Calendar getTime();
		
		/**
		 * measures of efficient and effective use
		 * Compares the worth of the messages sent against the cost.
		 */
		public float getEconomy();
		public float getCost();
	}
	
	public interface NetworkController {
		public NetLink[] getNetworkLinks();
		public Gateway[] getGateways();
	}
	
}
