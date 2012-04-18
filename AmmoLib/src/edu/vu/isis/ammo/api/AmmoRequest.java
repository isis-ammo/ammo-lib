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
package edu.vu.isis.ammo.api;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;
import android.os.RemoteException;
import edu.vu.isis.ammo.api.type.Action;
import edu.vu.isis.ammo.api.type.DeliveryScope;
import edu.vu.isis.ammo.api.type.Form;
import edu.vu.isis.ammo.api.type.Limit;
import edu.vu.isis.ammo.api.type.SerialMoment;
import edu.vu.isis.ammo.api.type.Notice;
import edu.vu.isis.ammo.api.type.Notice.Via;
import edu.vu.isis.ammo.api.type.Oid;
import edu.vu.isis.ammo.api.type.Order;
import edu.vu.isis.ammo.api.type.Payload;
import edu.vu.isis.ammo.api.type.Provider;
import edu.vu.isis.ammo.api.type.Quantifier;
import edu.vu.isis.ammo.api.type.Query;
import edu.vu.isis.ammo.api.type.Selection;
import edu.vu.isis.ammo.api.type.TimeInterval;
import edu.vu.isis.ammo.api.type.TimeStamp;
import edu.vu.isis.ammo.api.type.TimeTrigger;
import edu.vu.isis.ammo.api.type.Topic;

/**
 * see docs/dev-guide/developer-guide.pdf
 * 
 * The request has many options.
 * 
 * Option usage:
 *
 * 
 */
public class AmmoRequest extends AmmoRequestBase implements IAmmoRequest, Parcelable {
	private static final Logger logger = LoggerFactory.getLogger("api.request");
	private static final Logger plogger = LoggerFactory.getLogger("api.parcel");

	// **********************
	// PUBLIC PROPERTIES
	// **********************
	final public Action action;
	final public String uuid; // the request globally unique identifier
	final public String uid;  // the application object unique identifier

	final public Provider provider;
	final public Payload payload;
	final public SerialMoment moment;
	final public Topic topic;
	final public Topic subtopic;
	final public Quantifier quantifier;

	final public Integer downsample;
	final public Integer durability;

	final public Integer priority;
	final public Order order;

	final public TimeTrigger start;
	final public TimeTrigger expire;
	final public Limit limit;

	final public DeliveryScope scope;
	final public Integer throttle;

	final public String[] project;
	final public Selection select;

	final public Integer worth;
	final public Notice notice;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.action != null) sb.append(this.action.toString()).append(" Request ");
		if (this.uuid != null) sb.append(this.uuid).append(" ");
		if (this.uid != null) sb.append(this.uid).append(" ");
		if (this.topic != null) sb.append(this.topic).append(' ');
		return sb.toString();
	}
	
	public String toShow() {
		StringBuilder sb = new StringBuilder();
		if (this.action != null) sb.append(this.action.toString()).append(" Request ");
		if (this.uuid != null) sb.append('[').append(this.uuid).append("]");
		if (this.uid != null) sb.append(":[").append(this.uid).append("] ");
		if (this.topic != null) sb.append('@').append(this.topic);
		if (this.subtopic != null) sb.append('&').append(this.subtopic);
		if (this.quantifier != null) sb.append('&').append(this.quantifier);
		sb.append(' ');
		
		return sb.toString();
	}

	// ****************************
	// Parcelable Support
	// ****************************

	public static final Parcelable.Creator<AmmoRequest> CREATOR = new Parcelable.Creator<AmmoRequest>() {

		@Override
		public AmmoRequest createFromParcel(Parcel source) {
			try {
				return new AmmoRequest(source);
				
			} catch (IncompleteRequest ex) {
				return null;
				
			} catch (Throwable ex) {
				final int capacity = source.dataCapacity();
				final int size = (capacity < 50) ? capacity : 50;
				final byte[] data = new byte[size];
				source.unmarshall(data, 0, size);
				plogger.error("PARCEL UNMARSHALLING PROBLEM: size {} data {}", 
						new Object[] { capacity, data }, ex ); 
				return null;
			}
		}

		@Override
		public AmmoRequest[] newArray(int size) {
			return new AmmoRequest[size];
		}
	};

	/**
	 * The this.provider.writeToParcel(dest, flags) form is not used rather
	 * Class.writeToParcel(this.provider, dest, flags) so that when the null
	 * will will be handled correctly.
	 */
	private final byte VERSION = (byte) 0x04;

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		plogger.trace("version: {}", VERSION);
		dest.writeByte(VERSION);
		
		plogger.trace("request: [{}:{}]", this.uuid, this.uid);
		dest.writeValue(this.uuid);
		dest.writeValue(this.uid);
		plogger.trace("action: {}", this.action);
		Action.writeToParcel(dest, this.action);

		plogger.trace("provider: {}", this.provider);
		Provider.writeToParcel(this.provider, dest, flags);
		plogger.debug("payload: {}", this.payload);
		Payload.writeToParcel(this.payload, dest, flags);
		plogger.trace("moment: {}", this.moment);
		SerialMoment.writeToParcel(this.moment, dest, flags);
		plogger.trace("topic: [{}]+[{}]", this.topic, this.subtopic);
		Topic.writeToParcel(this.topic, dest, flags);
		Topic.writeToParcel(this.subtopic, dest, flags);
		plogger.trace("quantifier: {}", this.quantifier);
		Notice.writeToParcel(this.quantifier, dest, flags);

		plogger.debug("downsample: {}", this.downsample);
		dest.writeValue(this.downsample);
		plogger.debug("durabliity: {}", this.durability);
		dest.writeValue(this.durability);

		plogger.debug("priority: {}", this.priority);
		dest.writeValue(this.priority);
		plogger.debug("order: {}", this.order);
		Order.writeToParcel(this.order, dest, flags);

		plogger.debug("start: {}", this.start);
		TimeTrigger.writeToParcel(this.start, dest, flags);
		plogger.debug("expire: {}", this.expire);
		TimeTrigger.writeToParcel(this.expire, dest, flags);
		plogger.debug("limit: {}", this.limit);
		Limit.writeToParcel(this.limit, dest, flags);

		plogger.debug("scope: {}", this.scope);
		DeliveryScope.writeToParcel(this.scope, dest, flags);
		plogger.debug("throttle: {}", this.throttle);
		dest.writeValue(this.throttle);
		plogger.debug("worth: {}", this.worth);
		dest.writeValue(this.worth);
		plogger.trace("notice: {}", this.notice);
		Notice.writeToParcel(this.notice, dest, flags);

		plogger.trace("selection: {}", this.select);
		Selection.writeToParcel(this.select, dest, flags);
		plogger.debug("projection: {}", this.project);
		dest.writeStringArray(this.project);
	}

	/**
	 * 
	 * @param in
	 * @throws IncompleteRequest 
	 */
	private AmmoRequest(Parcel in) throws IncompleteRequest  {
		final byte version;
		try {
			version = in.readByte();
			if (version < VERSION) {
				plogger.info("AMMO REQUEST VERSION MISMATCH, received {}, expected {}",
						version, VERSION);
			} else if (version > VERSION ){
				plogger.warn("AMMO REQUEST VERSION MISMATCH, received {}, expected {}",
						version, VERSION);
				throw new ParcelFormatException("AMMO REQUEST VERSION MISMATCH");
			} else {
				plogger.trace("AMMO REQUEST VERSION MATCH: {}", version);
			}
		} catch (Exception ex) {
			plogger.error("unmarshall on version {} {}", ex.getLocalizedMessage(), ex.getStackTrace());
			throw new IncompleteRequest(ex);
		}
		try {
			this.uuid = (String) in.readValue(String.class.getClassLoader());
			this.uid  = (version < (byte)3) ? this.uuid : (String) in.readValue(String.class.getClassLoader());
			plogger.trace("uuid: [{}:{}]", this.uuid, this.uid);
		} catch (Exception ex) {
			plogger.error("decoding uid: {}", ex);
			throw new IncompleteRequest(ex);
		}
		
		try {
			this.action = Action.getInstance(in);
			plogger.trace("action: {}", this.action);
		} catch (Exception ex) {
			plogger.error("decoding action: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.provider = Provider.readFromParcel(in);
			plogger.trace("provider: {}", this.provider);
		} catch (Exception ex) {
			plogger.error("decoding provider: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.payload = Payload.readFromParcel(in);
			plogger.trace("payload: {}", this.payload);	
		} catch (Exception ex) {
			plogger.error("decoding payload: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.moment = (version < (byte) 4) ? SerialMoment.DEFAULT : SerialMoment.readFromParcel(in);
			plogger.trace("moment: {}", this.moment);
		} catch (Exception ex) {
			plogger.error("decoding moment: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.topic = Topic.readFromParcel(in);
			plogger.trace("topic: {}", this.topic);
		} catch (Exception ex) {
			plogger.error("decoding topic: {}", ex);
			throw new IncompleteRequest(ex);
		}

		if (version < (byte) 3) {
			// unused read slack bytes
			this.subtopic = new Topic("");
			this.quantifier = new Quantifier(Quantifier.Type.BULLETIN);
		} else {
			try {
				this.subtopic = Topic.readFromParcel(in);
				plogger.trace("subtopic: {}", this.subtopic);
			} catch (Exception ex) {
				plogger.error("decoding subtopic: {}", ex);
				throw new IncompleteRequest(ex);
			}
			try {
				this.quantifier = Quantifier.readFromParcel(in);
				plogger.trace("quantifier: {}", this.quantifier);
			} catch (Exception ex) {
				plogger.error("decoding quantifier: {}", ex);
				throw new IncompleteRequest(ex);
			}
		} 
		try {
			this.downsample = (Integer) in.readValue(Integer.class.getClassLoader());
			plogger.trace("downsample: {}", this.downsample);
		} catch (Exception ex) {
			plogger.error("decoding downsample: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.durability = (Integer) in.readValue(Integer.class.getClassLoader());
			plogger.trace("durability: {}", this.durability);
		} catch (Exception ex) {
			plogger.error("decoding durability: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {

			this.priority = (Integer) in.readValue(Integer.class.getClassLoader());
			plogger.trace("priority: {}", this.priority);
		} catch (Exception ex) {
			plogger.error("decoding priority: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.order = Order.readFromParcel(in);
			plogger.trace("order: {}", this.order);
		} catch (Exception ex) {
			plogger.error("decoding order: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.start = TimeTrigger.readFromParcel(in);
			plogger.trace("start: {}", this.start);
		} catch (Exception ex) {
			plogger.error("unmarshall start {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.expire = TimeTrigger.readFromParcel(in);
			plogger.trace("expire: {}", this.expire);
		} catch (Exception ex) {
			plogger.error("decoding expire: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.limit = (version < (byte)2) ? new Limit(100) : Limit.readFromParcel(in);
			plogger.trace("limit: {}", this.limit);
		} catch (Exception ex) {
			plogger.error("decoding limit: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.scope = DeliveryScope.readFromParcel(in);
			plogger.trace("scope: {}", this.scope);
		} catch (Exception ex) {
			plogger.error("decoding scope: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.throttle = (Integer) in.readValue(Integer.class.getClassLoader());
			plogger.trace("throttle: {}", this.throttle);
		} catch (Exception ex) {
			plogger.error("unmarshall throttle {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.worth = (Integer) in.readValue(Integer.class.getClassLoader());
			plogger.trace("worth: {}", this.worth);
		} catch (Exception ex) {
			plogger.error("decoding worth: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.notice = (version < 4) ? new Notice() : Notice.readFromParcel(in);
			plogger.trace("notice: {}", this.notice);
		} catch (Exception ex) {
			plogger.error("decoding notice: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.select = Selection.readFromParcel(in);
			plogger.trace("select: {}", this.select);
		} catch (Exception ex) {
			plogger.error("decoding select: {}", ex);
			throw new IncompleteRequest(ex);
		}
		try {
			this.project = in.createStringArray();
			plogger.trace("projection: {}", this.project);
		} catch (Exception ex) {
			plogger.error("decoding projection: {}", ex);
			throw new IncompleteRequest(ex);
		}

	}

	@Override
	public int describeContents() {
		return 0;
	}

	// *********************************
	// IAmmoReques Support
	// *********************************

	private AmmoRequest(Action action, Builder builder) {
		this.action = action;
		this.uid = builder.uid;

		this.provider = builder.provider;
		this.payload = builder.payload;
		this.moment = builder.moment;

		this.topic = builder.topic;
		this.subtopic = builder.subtopic;
		this.quantifier = builder.quantifier;

		this.downsample = builder.downsample;
		this.durability = builder.durability;

		this.priority = builder.priority;
		this.order = builder.order;

		this.start = builder.start;
		this.expire = builder.expire;
		this.limit = builder.limit;

		this.scope = builder.scope;
		this.throttle = builder.throttle;

		this.project = builder.project;
		this.select = builder.select;

		this.worth = builder.worth;
		this.notice = builder.notice;

		this.uuid = UUID.randomUUID().toString();
	}

	@Override
	public IAmmoRequest replace(IAmmoRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAmmoRequest replace(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Builder newBuilder(Context context) {
		return new AmmoRequest.Builder(context).reset();
	}

	public static Builder newBuilder(Context context, BroadcastReceiver receiver) {
		return new AmmoRequest.Builder(context, receiver).reset();
	}

	// public static Builder newBuilder(IBinder service) {
	// return new AmmoRequest.Builder(service).reset();
	// }
	//  

	// **************
	// CONTROL
	// **************
	@Override
	public void metricTimespan(Integer val) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetMetrics(Integer val) {
		// TODO Auto-generated method stub
	}

	// **************
	// STATISTICS
	// **************

	@Override
	public TimeStamp lastMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * The builder makes requests to the Distributor via AIDL methods.
	 * 
	 */
	private static final Intent DISTRIBUTOR_SERVICE = new Intent(IDistributorService.class.getCanonicalName());
	private static final Intent MAKE_DISTRIBUTOR_REQUEST = new Intent("edu.vu.isis.ammo.api.MAKE_REQUEST");

	public static class Builder implements IAmmoRequest.Builder {

		private enum ConnectionMode {
			BIND, PEEK, COMMAND, NONE;
		}

		private final AtomicReference<ConnectionMode> mode;
		private final AtomicReference<IDistributorService> distributor;
		private final Context context;

		private Builder(Context context, BroadcastReceiver receiver) {

			this.mode = new AtomicReference<ConnectionMode>(ConnectionMode.COMMAND);
			this.distributor = new AtomicReference<IDistributorService>(null);
			this.context = context;

			final IBinder service = receiver.peekService(context, DISTRIBUTOR_SERVICE);
			if (service == null) {
				logger.warn("ammo not peekable");
				return;
			}
			logger.warn("ammo available");
			this.distributor.set(IDistributorService.Stub.asInterface(service));
			this.mode.set(ConnectionMode.PEEK);
		}

		final private ServiceConnection conn = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				logger.trace("service connected");
				Builder.this.distributor.set(IDistributorService.Stub.asInterface(service));
				Builder.this.mode.set(ConnectionMode.BIND);
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				logger.trace("service {} disconnected", name.flattenToShortString());
				Builder.this.mode.set(ConnectionMode.COMMAND);
				Builder.this.distributor.set(null);
			}
		};

		private Builder(Context context) {
			this.mode = new AtomicReference<ConnectionMode>(ConnectionMode.COMMAND);
			this.distributor = new AtomicReference<IDistributorService>(null);
			this.context = context;
			try {
				final boolean isBound = this.context.bindService(DISTRIBUTOR_SERVICE, this.conn, Context.BIND_AUTO_CREATE);
				logger.trace("is the service bound? {}", isBound);
			} catch (ReceiverCallNotAllowedException ex) {
				logger.error("the service cannot be bound");

			}
		}

		private String uid;

		private Provider provider;
		private Payload payload;
		private SerialMoment moment;
		private Topic topic;
		private Topic subtopic;
		private Quantifier quantifier;

		private Integer downsample;
		private Integer durability;

		private Integer priority;
		private Order order;

		private TimeTrigger start;
		private TimeTrigger expire;
		private Limit limit;

		private DeliveryScope scope;
		private Integer throttle;

		private String[] project;
		private Selection select;

		private Integer worth;
		private Notice notice;

		// ***************
		// ACTIONS
		// ***************

		/**
		 * Generally the BIND approach should be used as it has the best
		 * performance. Sometimes this is not possible and the
		 * getService()/COMMAND method must be used (in the case of
		 * BroadcastReceiver). There are cases where the PEEK method may be used
		 * (BroadcastReceiver) but it is not particularly reliable so the
		 * fall-back to COMMAND is necessary. It may also be the case that the
		 * service has not yet started and the binder has not yet been obtained.
		 * In that interim case the COMMAND mode should be used.
		 * 
		 */
		private IAmmoRequest makeRequest(final AmmoRequest request) throws RemoteException {
			switch (this.mode.get()) {
			case BIND:
			case PEEK:
				final String ident = this.distributor.get().makeRequest(request);
				logger.info("service bind : {} {}", request, ident);
				break;
			case COMMAND:
			default:
				final Intent parcelIntent = MAKE_DISTRIBUTOR_REQUEST.cloneFilter();
				parcelIntent.putExtra("request", request);
				final ComponentName componentName = this.context.startService(parcelIntent);
				if (componentName != null) {
					logger.debug("service command : {}", componentName.getClassName());
				} else {
					logger.error("service command : {}", parcelIntent);
				}
				break;
			}
			return request;
		}

		@Override
		public IAmmoRequest base() {
			return new AmmoRequest(Action.NONE, this);
		}
		
		@Override
		public IAmmoRequest post() throws RemoteException {
			return this.makeRequest(new AmmoRequest(Action.POSTAL, this));
		}


		@Override
		public IAmmoRequest retrieve() throws RemoteException {
			return this.makeRequest(new AmmoRequest(Action.RETRIEVAL, this));
		}

		@Override
		public IAmmoRequest subscribe() throws RemoteException {
			return this.interest();
		}

		@Override
		public IAmmoRequest interest() throws RemoteException {
			return this.makeRequest(new AmmoRequest(Action.INTEREST, this));
		}

		@Override
		public IAmmoRequest duplicate() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IAmmoRequest getInstance(String uuid) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void releaseInstance() {
			try {
				this.context.unbindService(this.conn);	
			} catch (IllegalArgumentException e) {
				logger.warn("the service is not bound or registered {}", e.getLocalizedMessage());
			}

		}

		// **************
		// SET PROPERTIES
		// **************
		@Override
		public Builder reset() {
			this.downsample(DOWNSAMPLE_DEFAULT);
			this.durability(DURABILITY_DEFAULT);
			this.order(ORDER_DEFAULT);
			this.payload(PAYLOAD_DEFAULT);
			this.moment(SerialMoment.DEFAULT);
			this.priority(PRIORITY_DEFAULT);
			this.provider(PROVIDER_DEFAULT);
			this.scope(SCOPE_DEFAULT);
			this.start(START_DEFAULT);
			this.throttle(THROTTLE_DEFAULT);
			this.topic(TOPIC_DEFAULT);
			this.subtopic(SUBTOPIC_DEFAULT);
			this.quantifier(QUANTIFIER_DEFAULT);
			this.uid(UID_DEFAULT);
			this.expire(EXPIRE_DEFAULT);
			this.project(PROJECT_DEFAULT);
			this.select(SELECT_DEFAULT);
			this.filter(FILTER_DEFAULT);
			this.worth(WORTH_DEFAULT);
			return this;
		}

		public Builder downsample(String max) {
			if (max == null)
				return this;
			this.downsample = Integer.parseInt(max);
			return this;
		}

		@Override
		public Builder downsample(Integer maxSize) {
			this.downsample = maxSize;
			return this;
		}

		public Builder durability(String val) {
			if (val == null)
				return this;
			this.durability = Integer.parseInt(val);
			return this;
		}

		@Override
		public Builder durability(Integer val) {
			this.durability = val;
			return this;
		}

		public Builder order(String val) {
			if (val == null)
				return this;
			return this.order(new Order(val));
		}

		@Override
		public Builder order(Order val) {
			this.order = val;
			return this;
		}

		@Override
		public Builder payload(String val) {
			if (val == null)
				return this;
			this.payload = new Payload(val);
			return this;
		}

		@Override
		public Builder payload(byte[] val) {
			if (val == null)
				return this;
			this.payload = new Payload(val);
			return this;
		}

		@Override
		public Builder payload(ContentValues val) {
			if (val == null)
				return this;
			this.payload = new Payload(val);
			return this;
		}

		@Override
		public Builder payload(AmmoValues val) {
			if (val == null)
				return this;
			return this.payload(val.asContentValues());
		}

		@Override
		public Builder moment(String val) {
			if (val == null) {
				return this.moment(SerialMoment.DEFAULT);
			}
			return this.moment(new SerialMoment(val));
		}

		@Override
		public Builder moment(SerialMoment val) {
			this.moment = val;
			return this;
		}


		public Builder priority(String val) {
			if (val == null)
				return this;
			return this.priority(Integer.parseInt(val));
		}

		@Override
		public Builder priority(Integer val) {
			this.priority = val;
			return this;
		}

		public Builder provider(String val) {
			if (val == null) {
				return this.provider(Provider.DEFAULT);
			}
			return this.provider(Uri.parse(val));
		}

		@Override
		public Builder provider(Uri val) {
			this.provider = new Provider(val);
			return this;
		}

		public Builder scope(String val) {
			if (val == null) {
				return this.scope(DeliveryScope.DEFAULT);
			}
			return this.scope(new DeliveryScope(val));
		}

		@Override
		public Builder scope(DeliveryScope val) {
			this.scope = val;
			return this;
		}

		public Builder throttle(String val) {
			if (val == null)
				return this;
			this.throttle = Integer.parseInt(val);
			return this;
		}

		@Override
		public Builder throttle(Integer val) {
			this.throttle = val;
			return this;
		}

		@Override
		public Builder topic(String val) {
			this.topic = new Topic(val);
			return this;
		}

		@Override
		public Builder topic(Oid val) {
			this.topic = new Topic(val);
			return this;
		}


		@Override
		public Builder subtopic(String val) {
			this.subtopic = new Topic(val);
			return this;
		}

		@Override
		public Builder subtopic(Oid val) {
			this.subtopic = new Topic(val);
			return this;
		}

		@Override
		public Builder quantifier(String type) {
			this.quantifier(type);
			return this;
		}

		@Override
		public Builder quantifier(Quantifier.Type type) {
			this.quantifier = new Quantifier(type);
			return this;
		}

		@Override
		public Builder topic(String topic, String subtopic, String quantifier) {
			this.topic(topic);
			this.subtopic(subtopic);
			this.quantifier(quantifier);
			return this;
		}

		@Override
		public Builder topic(Oid topic, Oid subtopic, Quantifier.Type quantifier) {
			this.topic(topic);
			this.subtopic(subtopic);
			this.quantifier(quantifier);
			return this;
		}



		public Builder topicFromProvider() {
			if (this.provider == null) {
				logger.error("you must first set the provider");
				return this;
			}
			final String topic = this.context
					.getContentResolver()
					.getType(this.provider.asUri());
			this.topic(topic);
			return this;
		}


		@Override
		public Builder uid(String val) {
			this.uid = val;
			return this;
		}

		public Builder start(String val) {
			if (val == null)
				return this;
			return this.start(new TimeStamp(val));
		}

		@Override
		public Builder start(TimeStamp val) {
			this.start = new TimeTrigger(val);
			return this;
		}

		@Override
		public Builder start(TimeInterval val) {
			this.start = new TimeTrigger(val);
			return this;
		}

		public Builder expire(String val) {
			if (val == null)
				return this;
			return this.expire(new TimeStamp(val));
		}

		@Override
		public Builder expire(TimeInterval val) {
			this.expire = new TimeTrigger(val);
			return this;
		}

		@Override
		public Builder expire(TimeStamp val) {
			this.expire = new TimeTrigger(val);
			return this;
		}

		public Builder limit(String val) {
			this.limit = new Limit(val);
			return null;
		}

		@Override
		public Builder limit(int val) {
			this.limit = new Limit(Limit.Type.NEWEST, val);
			return this;
		}

		@Override
		public Builder limit(Limit val) {
			this.limit = val;
			return this;
		}

		public Builder project(String val) {
			if (val == null)
				return this;
			if (val.length() < 1)
				return this;
			this.project(val.substring(1).split(val.substring(0, 1)));
			return this;
		}

		@Override
		public Builder project(String[] val) {
			this.project = val;
			return this;
		}

		public Builder select(String val) {
			if (val == null)
				return this;
			this.select = new Selection(val);
			return this;
		}

		@Override
		public Builder select(Query val) {
			this.select = new Selection(val);
			return this;
		}

		@Override
		public Builder select(Form val) {
			this.select = new Selection(val);
			return this;
		}

		@Override
		public Builder filter(String val) {
			// this.filter = new Filter(val);
			return this;
		}

		public Builder worth(String val) {
			if (val == null)
				return this;
			this.worth = Integer.parseInt(val);
			return null;
		}

		@Override
		public Builder worth(Integer val) {
			this.worth = val;
			return this;
		}

		/**
		 *  To clear the notices use notice(null).
		 */
		public Builder notice(Notice.Threshold threshold, Via.Type type) {
			if (this.notice == null) this.notice = Notice.newInstance();
			this.notice.setItem(threshold, type);
			return this;
		}

		/**
		 *  It replaces the current notice object with the argument.
		 *  The notice set can be cleared by using this method
		 *  with a null object.
		 */
		@Override
		public Builder notice(Notice val) {
			if (val == null) {

			}
			this.notice = val;
			return this;
		}
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub

	}

}
