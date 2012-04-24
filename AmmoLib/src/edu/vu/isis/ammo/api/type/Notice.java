/*Copyright (C) 2010-2012 Institute for Software Integrated Systems (ISIS)
This software was developed by the Institute for Software Integrated
Systems (ISIS) at Vanderbilt University, Tennessee, USA for the 
Transformative Apps program under DARPA, Contract # HR011-10-C-0175.
The United States Government has unlimited rights to this software. 
The US government has the right to use, modify, reproduce, release, 
perform, display, or disclose computer software or computer software 
documentation in whole or in part, in any manner and for any 
purpose whatsoever, and to have or authorize others to do so.
 */
package edu.vu.isis.ammo.api.type;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * The Notice is used to specify intents to generate
 * as certain thresholds are crossed, and the 
 * delivery mode to use.
 * 
 * A Notice.Item consists of two parts:
 *  the [[threshold]], which may trigger the notification, and 
 *  the associated [[mode]].
 *  
 * final AmmoRequest.Builder ab = AmmoRequest.newBuilder();
 * 
 * final AmmoRequest ar = ab
 *   .topic("flintstones", "wilma")
 *   .provider("content://flintstones/characters/wilma")
 *   .notice(Notice.Threshold.SENT, Notice.Via.BROADCAST)
 *   .post();
 * 
 * When the message is sent (leaves the handheld) the following
 * broadcast intent will be generated.
 * 
 *    final Intent notice = new Intent()
 *        .setAction(ACTION_MSG_SENT)
 *        .setData(Uri.Builder()
 *                 .scheme("ammo")
 *                 .authority(ack.topic)
 *                 .path(ack.subtopic)
 *                 .build())
 *        .putExtra(EXTRA_STATUS, ack.status.toString())
 *        .putExtra(EXTRA_TOPIC, ack.topic.toString())
 *        .putExtra(EXTRA_UID, ack.auid.toString())
 *        .putExtra(EXTRA_CHANNEL, ack.channel.toString());
 *        
 *  For more examples for each of the intents generated see Threshold.
 *  Depending on the Via the intent will be sent (see Via).
 *  
 */

public class Notice extends AmmoType  {

	static final public Notice RESET = null;

	/**
	 * The following constants are used to produce intents.
	 */
	public static final String ACTION_MSG_SENT = "edu.vu.isis.ammo.ACTION_MESSAGE_SENT";
	public static final String ACTION_GW_DELIVERED = "edu.vu.isis.ammo.ACTION_MESSAGE_GATEWAY_DELIVERED";
	public static final String ACTION_HH_DELIVERED = "edu.vu.isis.ammo.ACTION_MESSAGE_DEVICE_DELIVERED";
	public static final String ACTION_PLUGIN_DELIVERED = "edu.vu.isis.ammo.ACTION_MESSAGE_PLUGIN_DELIVERED";

	public static final String EXTRA_TOPIC = "topic";
	public static final String EXTRA_SUBTOPIC = "subtopic";
	public static final String EXTRA_UID = "uid";
	public static final String EXTRA_CHANNEL = "channel";
	public static final String EXTRA_STATUS = "status";
	public static final String EXTRA_DEVICE = "device";
	public static final String EXTRA_OPERATOR = "operator";

	/**
	 * A class to build those intents which may be generated in
	 * response to the message crossing a noted threshold.
	 * 
	 */
	public static IntentBuilder getIntentBuilder(final Notice notice) {
		return new IntentBuilder(notice);
	}
	static public class IntentBuilder {

		@SuppressWarnings("unused")
		private final Notice notice;
		private String topic = null;
		private String subtopic = null;
		private String auid = null;
		private String channel = null;
		private String status = null;
		private String device = null;
		private String operator = null;

		private IntentBuilder(final Notice notice) {
			this.notice = notice;
		}

		public IntentBuilder topic(final Topic topic) {
			this.topic = topic.toString();
			return this;
		}
		public IntentBuilder topic(final String topic) {
			this.topic = topic;
			return this;
		}
		public IntentBuilder subtopic(final Topic subtopic) {
			this.subtopic = subtopic.toString();
			return this;
		}
		public IntentBuilder subtopic(final String subtopic) {
			this.subtopic = subtopic;
			return this;
		}
		public IntentBuilder auid(final String auid) {
			this.auid = auid;
			return this;
		}
		public IntentBuilder channel(final String channel) {
			this.channel = channel;
			return this;
		}
		public IntentBuilder status(final String status) {
			this.status = status;
			return this;
		}
		public IntentBuilder device(final String device) {
			this.device = device;
			return this;
		}
		public IntentBuilder operator(final String operator) {
			this.operator = operator;
			return this;
		}

		public Intent buildSent(final Context context) {
			return this.build(context, ACTION_MSG_SENT);
		}
		public Intent buildDeviceDelivered(final Context context) {
			return this.build(context, ACTION_HH_DELIVERED);
		}
		public Intent buildGatewayDelivered(final Context context) {
			return this.build(context, ACTION_GW_DELIVERED);
		}
		public Intent buildPluginDelivered(final Context context) {
			return this.build(context, ACTION_PLUGIN_DELIVERED);
		}

		private Intent build(final Context context, final String action) {
			final Uri.Builder uriBuilder = new Uri.Builder()
			.scheme("ammo")
			.authority(this.topic);

			if (this.subtopic != null) 
				uriBuilder.path(this.subtopic);

			final Intent noticed = new Intent()
			.setAction(action)
			.setData(uriBuilder.build())
			.putExtra(EXTRA_TOPIC, this.topic);

			if (this.subtopic != null)
				noticed.putExtra(EXTRA_SUBTOPIC, this.subtopic);

			if (this.auid != null)
				noticed.putExtra(EXTRA_UID, this.auid);

			if (this.channel != null)
				noticed.putExtra(EXTRA_CHANNEL, this.channel);

			if (this.status != null)
				noticed.putExtra(EXTRA_STATUS, this.status);
			
			if (this.device != null)
				noticed.putExtra(EXTRA_DEVICE, this.status);
			
			if (this.operator != null)
				noticed.putExtra(EXTRA_OPERATOR, this.operator);

			return noticed;
		}
	}


	/**
	 * Class for adding items to the notice.
	 * An item consists of a threshold which may be crossed 
	 * and the aggregate of those intents to be generated when that happens.
	 *
	 */
	public class Item {	   
		public final Threshold threshold;
		public Via via;

		private Item(Threshold threshold, Via via) {
			this.threshold = threshold;
			this.via = via;
		}

		@Override
		public String toString() {
			return new StringBuilder()
			.append("@").append(this.threshold).append("->")
			.append("[").append(this.via).append(']')
			.toString();
		}

		public void writeParcel(Parcel dest, int flags) {
			dest.writeInt(this.threshold.id);
			dest.writeInt(this.via.v);
		}

	}

	public static Notice newInstance() {
		return new Notice();
	}


	/**
	 * 
	 * The request progresses through the system.   
	 * As it does, it crosses certain thresholds.
	 * These thresholds specify triggers where acknowledgements may be generated.
	 * 
	 * \begin{table}[h]
	 * \center
	 * \begin{tabular}{rl}
	 *  Name & Meaning \\ \hline
	 *  NONE         & placed under the control of the distributor \\
	 *  SENT         & sent over a channel \\
	 *  DISPATCHED   & placed under the control of an Android plugin \\
	 *  DELIVERED    & a plugin acknowledges delivery of a message  \\
	 *  RECEIVED     & a target device acknowledges receipt of a message \\
	 *  \end{tabular}
	 *  \caption{message thresholds indicating progress}
	 *  \end{table}
	 *  
	 *  NONE: no acknowledgment is generated and thus no intent is produced
	 *  
	 *  SENT:
	 *    final Intent notice = new Intent()
	 *        .setAction(ACTION_MSG_SENT)
	 *        .setData(Uri.Builder()
	 *                 .scheme("ammo")
	 *                 .authority(ack.topic)
	 *                 .path(ack.subtopic)
	 *                 .build())
	 *        .putExtra(EXTRA_STATUS, ack.status.toString())
	 *        .putExtra(EXTRA_TOPIC, ack.topic.toString())
	 *        .putExtra(EXTRA_UID, ack.auid.toString())
	 *        .putExtra(EXTRA_CHANNEL, ack.channel.toString());
	 *        
	 *  RECEIVED:
	 *    final Intent notice = new Intent()
	 *        .setAction(ACTION_MSG_RECEIVED)
	 *        .setData(Uri.Builder()
	 *                 .scheme("ammo")
	 *                 .authority(ack.topic)
	 *                 .path(ack.subtopic)
	 *                 .build())
	 *        .putExtra(EXTRA_STATUS, ack.status.toString())
	 *        .putExtra(EXTRA_TOPIC, ack.topic.toString())
	 *        .putExtra(EXTRA_UID, ack.auid.toString())
	 *        .putExtra(EXTRA_CHANNEL, ack.channel.toString())
	 *        .putExtra(EXTRA_DEVICE, ack.device.toString());
	 *        
	 */

	static private final int SENT_ID = 0x01;
	static private final int GATEWAY_ID = 0x02;
	static private final int PLUGIN_ID = 0x04;
	static private final int DEVICE_ID = 0x08;

	public enum Threshold {
		SENT(SENT_ID, "sent"),                                // the message has left the hand held
		GATE_DELIVERY(GATEWAY_ID, "gateway in-bound"),        // hand held dispatches request to android plugin
		PLUGIN_DELIVERY(PLUGIN_ID, "gateway to plugin"),    // arrived at an outgoing gateway plugin
		DEVICE_DELIVERY(DEVICE_ID, "handheld delivered");  // delivered to a hand held

		public final int id;
		public final String t;

		private Threshold(int id, String title) {
			this.id = id;
			this.t = title;
		}

		public static Threshold getInstance(int id) {
			switch (id) {
			case SENT_ID: return Threshold.SENT;
			case GATEWAY_ID: return Threshold.GATE_DELIVERY;
			case PLUGIN_ID: return Threshold.PLUGIN_DELIVERY;
			case DEVICE_ID: return Threshold.DEVICE_DELIVERY;
			}
			return null;
		}
	}

	public int cv() {
		//int notice = 0;  // notice nothing
		//if (atSend.)
		return 0;
	}

	final public Item atSend; 
	final public Item atGatewayDelivered;
	final public Item atPluginDelivered;
	final public Item atDeviceDelivered; 

	/**
	 * Used by the request builder
	 * 
	 * @param threshold
	 * @param type
	 */
	public void setItem(Threshold threshold, Via.Type type) {
		plogger.debug("set notice item: @{}->[{}]",
				threshold, type);
		switch(threshold) {
		case SENT: atSend.via.set(type); return;
		case GATE_DELIVERY: atGatewayDelivered.via.set(type); return;
		case PLUGIN_DELIVERY: atPluginDelivered.via.set(type); return;
		case DEVICE_DELIVERY: atDeviceDelivered.via.set(type); return;
		}
	}

	/**
	 * Used by the data store
	 * Note this takes an aggregate type.
	 * 
	 * @param threshold
	 * @param aggregate
	 */
	public void setItem(Threshold threshold, int aggregate) {
		switch(threshold) {
		case SENT: atSend.via.set(aggregate); return;
		case GATE_DELIVERY: atGatewayDelivered.via.set(aggregate); return;
		case PLUGIN_DELIVERY: atPluginDelivered.via.set(aggregate); return;
		case DEVICE_DELIVERY: atDeviceDelivered.via.set(aggregate); return;
		}
	}

	/**
	 * 
	 * As acknowledgments of the message are generated they
	 * will contain the threshold status.
	 * 
	 * \begin{table}[h]
	 * \center
	 * \begin{tabular}{rl}
	 * Name & Meaning \\ \hline
	 * SUCCESS   & the place was reached without incident \\
	 * FAIL      & the place was reached, but the request failed and will be canceled   \\
	 * UNKNOWN   & the request is of an indeterminate disposition \\
	 * REJECTED  & the place rejected the request, another may yet accept it \\
	 * \end{tabular}
	 * \caption{message acknowledgment states}
	 * \end{table}
	 */
	public enum DeliveryState { SUCCESS, FAIL,  UNKNOWN, REJECTED };

	/**
	 * As the request reaches the places mentioned,
	 * it will cause an intent to generated and sent via one of the following:
	 * 
	 * \begin{description}
	 * \item[ACTIVITY] start an activity with the intent
	 * \item[BROADCAST] broadcast the intent
	 * \item[STICKY_BROADCAST] like broadcast but a register will pick up the last intent
	 * \item[SERVICE] start a service with the intent
	 * \item[SERVICE] start a service with the intent
	 * \end{description}
	 * 
	 * The structure of the intent varies based on the NoticeThreshold type.
	 * One of the following delivery mechanism will be used.
	 *
	 * context.sendBroadcast(notice);
	 * context.sendStickyBroadcast(notice);
	 * context.startService(notice);
	 * context.startActivity(notice);
	 */

	static public class Via { 

		public enum Type {
			NONE(0x00), 
			ACTIVITY(0x01), 
			BROADCAST(0x02), 
			STICKY_BROADCAST(0x04),
			SERVICE(0x08), 
			HEARTBEAT(0x10);
			// maybe RECORD indicating that the individual acknowledgments are to be recorded, in the RECIPIENT table

			public int v;

			private Type(int v) {
				this.v = v;
			}
		};

		public int v;

		public String asBits() {
			return Integer.toBinaryString(this.v);
		}

		/**
		 * multi-value flag, NONE resets it.
		 * 
		 * @param via
		 */
		public void set(Type type) {
			if (type.v == Type.NONE.v) {
				this.v = Type.NONE.v; 
				return;
			}
			this.v |= type.v; 
		}

		public void set(int aggregate) {
			this.v = aggregate;
		}

		public boolean isActive() {
			return ! (this.v == Type.NONE.v);
		}

		public boolean hasHeartbeat() {
			return (0 < (this.v & Type.HEARTBEAT.v));
		}

		private Via(int val) {
			this.v = val;
		}
		public static Via newInstance(int val) {
			return new Via(val);
		}

		private Via() {
			this.v = Type.NONE.v;
		}
		public static Via newInstance() {
			return new Via();
		}

		@Override
		public String toString() {
			if (this.v == Type.NONE.v) return "NONE"; 

			final StringBuilder sb = new StringBuilder().append(':');
			if (0< (this.v & Type.ACTIVITY.v)) {
				sb.append("activity").append(':');
			}
			if (0< (this.v & Type.SERVICE.v)) {
				sb.append("service").append(':');
			}
			if (0< (this.v & Type.BROADCAST.v)) {
				sb.append("broadcast").append(':');
			}
			if (0< (this.v & Type.STICKY_BROADCAST.v)) {
				sb.append("sticky").append(':');
			}
			if (0< (this.v & Type.HEARTBEAT.v)) {
				sb.append("heartbeat").append(':');
			}
			return sb.toString();
		}

	}


	// *********************************
	// Parcelable Support
	// *********************************

	public static final Parcelable.Creator<Notice> CREATOR = 
			new Parcelable.Creator<Notice>() {

		@Override
		public Notice createFromParcel(Parcel source) {
			return new Notice(source);
		}
		@Override
		public Notice[] newArray(int size) {
			return new Notice[size];
		}
	};

	public static Notice readFromParcel(Parcel source) {
		if (AmmoType.isNull(source)) return null;
		return new Notice(source);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		plogger.trace("origin notice: {}", this);

		dest.writeInt(5); // the number of items

		this.atSend.writeParcel(dest, flags);
		this.atGatewayDelivered.writeParcel(dest, flags);
		this.atPluginDelivered.writeParcel(dest, flags);
		this.atDeviceDelivered.writeParcel(dest, flags);
	}

	private Notice(Parcel in) {
		final int count = in.readInt();

		final Map<Threshold, Item> items = new HashMap<Threshold,Item>(count);

		for (int ix = 0; ix < count; ++ix) {
			final Threshold threshold = Threshold.getInstance(in.readInt());
			final Via via = Via.newInstance(in.readInt());

			items.put(threshold, new Item(threshold, via));
		}
		this.atSend = items.get(Threshold.SENT);
		this.atGatewayDelivered = items.get(Threshold.GATE_DELIVERY);
		this.atPluginDelivered = items.get(Threshold.PLUGIN_DELIVERY);
		this.atDeviceDelivered = items.get(Threshold.DEVICE_DELIVERY);

		plogger.trace("decoded notice: {}", this);
	}

	// *********************************
	// Standard Methods
	// *********************************
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		boolean existsAnActive = false;
		if (this.atSend.via.isActive()) {
			sb.append(this.atSend.toString()).append(' ');
			existsAnActive = true;
		} 
		if (this.atGatewayDelivered.via.isActive()) {
			sb.append(this.atGatewayDelivered.toString()).append(' ');
			existsAnActive = true;
		} 
		if (this.atPluginDelivered.via.isActive()) {
			sb.append(this.atPluginDelivered.toString()).append(' ');
			existsAnActive = true;
		}
		if (this.atDeviceDelivered.via.isActive()) {
			sb.append(this.atDeviceDelivered.toString()).append(' ');
			existsAnActive = true;
		}
		if (! existsAnActive) {
			return "<none requested>";
		}
		return sb.toString();
	}
	/**
	 * This indicates whether there exists an 
	 * active remote threshold to be noticed.
	 * (The sent threshold is local not remote)
	 * @return
	 */
	public boolean isRemoteActive() {
		if (this.atGatewayDelivered.via.isActive()) {
			return true;
		} 
		if (this.atPluginDelivered.via.isActive()) {
			return true;
		}
		if (this.atDeviceDelivered.via.isActive()) {
			return true;
		}
		return false;
	}

	/**
	 * The default constructor 
	 * Sets all the thresholds.
	 */
	public Notice() {
		this.atSend = new Item(Threshold.SENT, Via.newInstance());
		this.atGatewayDelivered = new Item(Threshold.GATE_DELIVERY, Via.newInstance());
		this.atPluginDelivered = new Item(Threshold.PLUGIN_DELIVERY, Via.newInstance());	
		this.atDeviceDelivered = new Item(Threshold.DEVICE_DELIVERY, Via.newInstance());	
	}

}
