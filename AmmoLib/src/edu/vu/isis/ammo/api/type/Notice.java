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
 *        
 *  and depending on the Via the intent will be sent (see Via).
 *  
 *  
 */

public class Notice extends AmmoType  {

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
			.append("@[").append(this.threshold).append("]->")
			.append("").append(this.via)
			.toString();
		}

		public void writeParcel(Parcel dest, int flags) {
			dest.writeInt(this.threshold.ordinal());
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

	public enum Threshold {
		SENT(0x01, "sent"),                   // the message has left the hand held
		GATE_IN(0x02, "gateway in-bound"),    // hand held dispatches request to android plugin
		GATE_OUT(0x04, "gateway out-bound"),  // arrived at an outgoing gateway plugin
		DELIVERED(0x08, "delivered"),         // delivered to a hand held
		RECEIVED(0x10, "received");           // processed by a dispatcher on a handheld

		public final int p;
		public final String d;

		private Threshold(int bitpos, String description) {
			this.p = bitpos;
			this.d = description;
		}
	}

	public int cv() {
		//int notice = 0;  // notice nothing
		//if (atSend.)
		return 0;
	}

	final public Item atSend; 
	final public Item atGateIn;
	final public Item atGateOut;
	final public Item atDelivery; 
	final public Item atReceipt;

	public void setItem(Threshold threshold, Via.Type type) {
		switch(threshold) {
		case SENT: atSend.via.set(type); return;
		case GATE_IN: atGateIn.via.set(type); return;
		case GATE_OUT: atGateOut.via.set(type); return;
		case DELIVERED: atDelivery.via.set(type); return;
		case RECEIVED: atReceipt.via.set(type); return;
		}
	}
	
	public void setItem(Threshold threshold, int aggregate) {
		switch(threshold) {
		case SENT: atSend.via.set(aggregate); return;
		case GATE_IN: atGateIn.via.set(aggregate); return;
		case GATE_OUT: atGateOut.via.set(aggregate); return;
		case DELIVERED: atDelivery.via.set(aggregate); return;
		case RECEIVED: atReceipt.via.set(aggregate); return;
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
			this.v &= type.v; 
		}
		
		public void set(int aggregate) {
			this.v = aggregate;
		}

		public boolean isActive() {
			return ! (this.v == Type.NONE.v);
		}
		
		public boolean isHeartbeat() {
			return (0 < (this.v | Type.HEARTBEAT.v));
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
		plogger.trace("marshall notice {}", this);
		dest.writeInt(5); // the number of items

		this.atSend.writeParcel(dest, flags);
		this.atGateIn.writeParcel(dest, flags);
		this.atGateOut.writeParcel(dest, flags);
		this.atDelivery.writeParcel(dest, flags);
		this.atReceipt.writeParcel(dest, flags);
	}

	private Notice(Parcel in) {
		final int count = in.readInt();

		final Map<Threshold, Item> items = new HashMap<Threshold,Item>(count);

		for (int ix = 0; ix < count; ++ix) {
			final Threshold threshold = Threshold.values()[in.readInt()];
			final Via via = Via.newInstance(in.readInt());

			items.put(threshold, new Item(threshold, via));
		}
		this.atSend = items.get(Threshold.SENT);
		this.atGateIn = items.get(Threshold.GATE_IN);
		this.atGateOut = items.get(Threshold.GATE_OUT);
		this.atDelivery = items.get(Threshold.DELIVERED);
		this.atReceipt = items.get(Threshold.RECEIVED);

		plogger.trace("unmarshall notice {}", this);
	}

	// *********************************
	// Standard Methods
	// *********************************
	@Override
	public String toString() {
		return new StringBuilder()
		.append(this.atSend.toString()).append(' ')
		.append(this.atGateIn.toString()).append(' ')
		.append(this.atGateOut.toString()).append(' ')
		.append(this.atDelivery.toString()).append(' ')
		.append(this.atReceipt.toString()).append(' ')
		.toString();
	}

	// *********************************
	// IAmmoRequest Support
	// *********************************

	public Notice() {
		this.atSend = new Item(Threshold.SENT, Via.newInstance());
		this.atGateIn = new Item(Threshold.GATE_IN, Via.newInstance());
		this.atGateOut = new Item(Threshold.GATE_OUT, Via.newInstance());	
		this.atDelivery = new Item(Threshold.DELIVERED, Via.newInstance());	
		this.atReceipt = new Item(Threshold.RECEIVED, Via.newInstance());
	}

}
