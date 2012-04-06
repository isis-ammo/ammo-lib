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

import java.util.ArrayList;
import java.util.List;

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
		public final Via via;
		
		private Item(Threshold threshold, Via via) {
			this.threshold = threshold;
			this.via = via;
		}

		@Override
		public String toString() {
		return new StringBuilder()
			.append("threshold [").append(this.threshold).append("], ")
			.append("via [").append(this.via).append("]")
			.toString();
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
    	SENT(1, "sent"),
    	DISPATCHED(2, "dispatched"),
    	DELIVERED(4, "delivered"),
    	RECEIVED(4, "received");

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
    
    
    final private List<Item> items;
 	private Item atSend; 
 	private Item atDispatch;
 	private Item atDelivery; 
 	private Item atReceipt;
 	
 	public Item whenSent() { return atSend; }
 	public Item whenDispatched() { return atDispatch; }
 	public Item whenDelivered() { return atDelivery; }
 	public Item whenReceived() { return atReceipt; }
	
	public Item setItem(Threshold threshold, Via via) {
		final Item item = new Item(threshold, via);
		//this.items.add(item);
		switch (threshold) {
		case SENT:
			atSend = item;
			break;
		case DISPATCHED:
			atDispatch = item;
			break;
		case DELIVERED:
			atDelivery = item;
			break;
		case RECEIVED:
			atReceipt = item;
		    break;
		}
		return item;
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
    
    public enum Via { NONE, ACTIVITY, BROADCAST, SERVICE, STICKY_BROADCAST };
    

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
		dest.writeInt(this.items.size());
		
		for (Item item : this.items ) {
			dest.writeInt(item.threshold.ordinal());
			dest.writeInt(item.via.ordinal());
		}
	}

	private Notice(Parcel in) {
		final int count = in.readInt();
		this.items = new ArrayList<Item>(count);
		for (int ix = 0; ix < count; ++ix) {
			final Threshold threshold = Threshold.values()[in.readInt()];
			final Via via = Via.values()[in.readInt()];
			this.items.add(this.setItem(threshold, via));
		}
		plogger.trace("unmarshall notice {}", this);
	}

	// *********************************
	// Standard Methods
	// *********************************
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
	        sb.append("notice :");
		for (Item item : this.items){ 
		    sb.append('\n')
		      .append("@ [").append(item.threshold).append("]")
			  .append("->[").append(item.via).append("]");
		}
		return sb.toString();
	}

	// *********************************
	// IAmmoRequest Support
	// *********************************

	public Notice() {
		this.items = new ArrayList<Item>();
		this.setItem(Threshold.SENT, Via.NONE);
	 	this.setItem(Threshold.DISPATCHED, Via.NONE);
	 	this.setItem(Threshold.DELIVERED, Via.NONE);
	 	this.setItem(Threshold.RECEIVED, Via.NONE);
	}

}
