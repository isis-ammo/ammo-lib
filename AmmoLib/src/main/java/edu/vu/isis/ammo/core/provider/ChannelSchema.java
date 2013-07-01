package edu.vu.isis.ammo.core.provider;

import android.net.Uri;

public interface ChannelSchema {
    
    // Authority and path strings
	String AUTHORITY = "edu.vu.isis.ammo.core.provider.channel";
	String MULTICAST_PATH = "multicast";
	String RELIABLE_MULTICAST_PATH = "reliable_multicast";
	String GATEWAY_PATH = "gateway";
	String GATEWAY_MEDIA_PATH = "gateway_media";
	String SERIAL_PATH = "serial";
	
	String CONTENT_URI_STRING = "content://" + AUTHORITY;
	
	// MIME types
	String MULTIPLE_CHANNELS_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY;
	String SINGLE_CHANNEL_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY;
	
	// Pre-parsed Uris for querying the provider
	Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);
	Uri MULTICAST_URI = Uri.withAppendedPath(CONTENT_URI, MULTICAST_PATH);
	Uri RELIABLE_MULTICAST_URI = Uri.withAppendedPath(CONTENT_URI, RELIABLE_MULTICAST_PATH);
	Uri GATEWAY_URI = Uri.withAppendedPath(CONTENT_URI, GATEWAY_PATH);
	Uri GATEWAY_MEDIA_URI = Uri.withAppendedPath(CONTENT_URI, GATEWAY_MEDIA_PATH);
	Uri SERIAL_URI = Uri.withAppendedPath(CONTENT_URI, SERIAL_PATH);
	Uri ALL_URI = Uri.withAppendedPath(CONTENT_URI, "*");
	
	interface ChannelColumns {
	    String FORMAL_IP = "formalIP";
	    String NAME = "name";
	    String CONNECTION_STATE = "cState";
	    String SENDER_STATE = "sState";
	    String RECEIVER_STATE = "rState";
	    String SEND_RECEIVE_COUNTS = "sendReceive";
	    String SEND_BIT_STATS = "sendBitStats";
	    String RECEIVE_BIT_STATS = "receiveBitStats";
	}
}
