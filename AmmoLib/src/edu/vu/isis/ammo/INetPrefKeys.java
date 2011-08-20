package edu.vu.isis.ammo;

/**
 * Collection of all preference values used by Ammo.
 * @author Demetri Miller
 *
 */
public interface INetPrefKeys extends edu.vu.isis.ammo.IPrefKeys {
	// ====================================
	// Ammo Core
	// ====================================	
	
// keys for network configuration
	

	public static final String PREF_TRANSMISSION_TIMEOUT = "CORE_TRANSMISSION_TIMEOUT";
	public static final String CORE_IS_JOURNALED = "CORE_IS_JOURNALED";
	
	public static final String CORE_DEVICE_ID = "CORE_DEVICE_ID";
	public static final String CORE_OPERATOR_KEY = "CORE_OPERATOR_KEY";
	public static final String CORE_OPERATOR_ID = "CORE_OPERATOR_ID";
	
// keys for managing network connections
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
	


	
	/*
	 * Gateway Network Settings
	 */
	public static final String NET_CONN_FLAT_LINE_TIME = NET_CONN_PREF + "FLAT_LINE_TIME";
	public static final String GATEWAY_SHOULD_USE = "GATEWAY_SHOULD_USE";
	public static final String CORE_IP_ADDR = "CORE_IP_ADDRESS";
	public static final String CORE_IP_PORT = "CORE_IP_PORT";
	public static final String CORE_SOCKET_TIMEOUT = "CORE_SOCKET_TIMEOUT";
	
	/*
	 * Multicast Network Settings
	 */
	public static final String MULTICAST_SHOULD_USE = "MULTICAST_SHOULD_USE";
	public static final String MULTICAST_IP_ADDRESS = "MULTICAST_IP_ADDRESS";
	public static final String MULTICAST_PORT = "MULTICAST_PORT";
	public static final String MULTICAST_NET_CONN_TIMEOUT = "MULTICAST_NET_CONN_TIMEOUT";
	public static final String MULTICAST_CONN_IDLE_TIMEOUT = "MULTICAST_CONN_IDLE_TIMEOUT";
	
	/*
	 * Serial Network Settings
	 */
	public static final String SERIAL_SHOULD_USE = "SERIAL_SHOULD_USE";
	public static final String SERIAL_DEVICE = "SERIAL_DEVICE";
	public static final String SERIAL_BAUD_RATE = "SERIAL_BAUD_RATE";
	public static final String SERIAL_SLOT_NUMBER = "SERIAL_SLOT_NUMBER";
	public static final String SERIAL_DEBUG_PERIOD = "SERIAL_DEBUG_PERIOD";
	public static final String SERIAL_SEND_ENABLED = "SERIAL_SEND_ENABLED";
	public static final String SERIAL_RECEIVE_ENABLED = "SERIAL_RECEIVE_ENABLED";
}
