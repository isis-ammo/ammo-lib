package edu.vu.isis.ammo.api;

/**
 * See also AmmoRequest.java IAmmoRequest.java and AmmoPolicy.java
 */

/**
 * This API provides assorted methods considered part of an operating system.
 * 
 */
public interface IAmmoUtility {
	
	/** 
	 * The methods which change the routing policy are not 
	 * generally available to applications programs.
	 * Only the getters will function for applications programs.
	 */
	public enum Clock {
		GATEWAY     ("Gateway"),
		LOCAL       ("Local"),
		ROUTER      ("Router");

		private final String text;  
		Clock(String text) {
			this.text = text;
		}
		public String text()   { return this.text; }
	}


	/**
	 * Approximate Current Time.
	 * If a clock type is provided that is the clock returned.
	 */
	public float getTime();
	public float getTime(Clock clock);
	
		
}
