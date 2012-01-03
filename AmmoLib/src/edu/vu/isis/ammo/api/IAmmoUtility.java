/*
Copyright(c) 2010-2012

This software was developed by the Institute for Software Integrated
Systems (ISIS) at Vanderbilt University, Tennessee, USA for the 
Transformative Apps program under contract [contract citation, subcontract and prime contract]. 
The United States Government has unlimited rights to this software. 
The US government has the right to use, modify, reproduce, release, 
perform, display, or disclose computer software or computer software 
documentation in whole or in part, in any manner and for any 
purpose whatsoever, and to have or authorize others to do so.

 */

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
