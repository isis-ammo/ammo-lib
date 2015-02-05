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

package edu.vu.isis.ammo;

/**
 * keys for managing network connections These are not intended to be modified
 * by the user. These indicate the derived state of the network. These should be
 * maintained in a separate shared preference file.
 */
public interface INetDerivedKeys {

    public static final String ARE_PREFS_CREATED = "prefsCreated";

    public static final String NET_IS_ACTIVE = "IS_ACTIVE";
    public static final String NET_SHOULD_USE = "SHOULD_USE";
    public static final String NET_IS_AVAILABLE = "IS_AVAILABLE";
    public static final String NET_IS_STALE = "IS_STALE";

    public static final String WIRED_PREF = "AMMO_PHYSICAL_LINK_";
    public static final String WIFI_PREF = "AMMO_WIFI_LINK_";
    public static final String PHONE_PREF = "AMMO_PHONE_LINK_";
    public static final String NET_CONN_PREF = "AMMO_NET_CONN_";

    public static final String PHYSICAL_LINK_PREF_IS_ACTIVE = WIRED_PREF + NET_IS_ACTIVE;
    public static final String WIRED_PREF_SHOULD_USE = WIRED_PREF + NET_SHOULD_USE;
    public static final String PHYSICAL_LINK_PREF_IS_AVAILABLE = WIRED_PREF + NET_IS_AVAILABLE;

    public static final String WIFI_PREF_IS_ACTIVE = WIFI_PREF + NET_IS_ACTIVE;
    public static final String WIFI_PREF_SHOULD_USE = WIFI_PREF + NET_SHOULD_USE;
    public static final String WIFI_PREF_IS_AVAILABLE = WIFI_PREF + NET_IS_AVAILABLE;

    public static final String PHONE_PREF_IS_ACTIVE = PHONE_PREF + NET_IS_ACTIVE;
    public static final String PHONE_PREF_SHOULD_USE = PHONE_PREF + NET_SHOULD_USE;
    public static final String PHONE_PREF_IS_AVAILABLE = PHONE_PREF + NET_IS_AVAILABLE;

    public static final String NET_CONN_PREF_IS_STALE = NET_CONN_PREF + NET_IS_STALE;
    public static final String NET_CONN_PREF_SHOULD_USE = NET_CONN_PREF + NET_SHOULD_USE;
    public static final String NET_CONN_PREF_IS_ACTIVE = NET_CONN_PREF + NET_IS_ACTIVE;

}
