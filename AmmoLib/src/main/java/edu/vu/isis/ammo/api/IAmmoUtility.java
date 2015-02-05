/* Copyright (c) 2010-2015 Vanderbilt University
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
