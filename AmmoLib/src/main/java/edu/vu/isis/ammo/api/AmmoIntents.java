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

public interface AmmoIntents {
	public static final String AMMO_ACTION_ETHER_LINK_CHANGE = "edu.vu.isis.ACTION_ETHER_LINK_CHANGE";
	public static final String AMMO_ACTION_WIFI_LINK_CHANGE = "edu.vu.isis.ACTION_WIFI_LINK_CHANGE";
	public static final String AMMO_ACTION_GATEWAY_STATUS_CHANGE = "edu.vu.isis.ACTION_GATEWAY_STATUS_CHANGE";
	public static final String AMMO_ACTION_CONNECTION_STATUS_CHANGE = "edu.vu.isis.ACTION_CONNECTION_STATUS_CHANGE";
	public static final String AMMO_ACTION_NETLINK_STATUS_CHANGE = "edu.vu.isis.ACTION_NETLINK_STATUS_CHANGE";
	public static final String ACTION_SERIAL_LINK_CHANGE = "edu.vu.isis.ACTION_SERIAL_LINK_CHANGE";
	public static final int LINK_UP = 1;
	public static final int LINK_DOWN = 2;
	
	/**
	 * Fields for naming extras.
	 */
	public static final String EXTRA_CHANNEL = "channel";
	public static final String EXTRA_CONNECT_STATUS = "connect-status";
	
}
