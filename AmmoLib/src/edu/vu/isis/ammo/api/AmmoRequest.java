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

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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
import edu.vu.isis.ammo.api.type.BroadIntent;
import edu.vu.isis.ammo.api.type.ChannelFilter;
import edu.vu.isis.ammo.api.type.DeliveryScope;
import edu.vu.isis.ammo.api.type.Form;
import edu.vu.isis.ammo.api.type.Limit;
import edu.vu.isis.ammo.api.type.Notice;
import edu.vu.isis.ammo.api.type.Notice.Via;
import edu.vu.isis.ammo.api.type.Oid;
import edu.vu.isis.ammo.api.type.Order;
import edu.vu.isis.ammo.api.type.Payload;
import edu.vu.isis.ammo.api.type.Provider;
import edu.vu.isis.ammo.api.type.Quantifier;
import edu.vu.isis.ammo.api.type.Query;
import edu.vu.isis.ammo.api.type.Selection;
import edu.vu.isis.ammo.api.type.SerialMoment;
import edu.vu.isis.ammo.api.type.TimeInterval;
import edu.vu.isis.ammo.api.type.TimeStamp;
import edu.vu.isis.ammo.api.type.TimeTrigger;
import edu.vu.isis.ammo.api.type.Topic;

/**
 * see docs/dev-guide/developer-guide.pdf The request has many options. Option
 * usage:
 */
public class AmmoRequest implements IAmmoRequest, Parcelable {
    private static final Logger logger = LoggerFactory.getLogger("api.request");
    private static final Logger plogger = LoggerFactory.getLogger("api.parcel");
    /**
     * Typically logging by clients is suppressed.
     */
    private static final boolean CLIENT_LOGGING = false;

    // **********************
    // PUBLIC PROPERTIES
    // **********************
    final public Action action;
    final public String uuid; // the request globally unique identifier
    final public String uid; // the application object unique identifier

    /**
     * the data store which holds the object.
     */
    final public Provider provider;
    /**
     * the data is to be sent as a broadcast intent.
     */
    final public BroadIntent intent;
    /**
     * the serialized content data.
     */
    final public Payload payload;
    final public SerialMoment moment;
    /**
     * the general uid and data type. This is a prefix match pattern.
     */
    final public Topic topic;
    final public Topic subtopic;
    final public Quantifier quantifier;

    final public Integer downsample;
    /**
     * indicates the volatility of the value. It amounts to deciding the allowed
     * sources of the content. It can be considered a measure of number of
     * sources.
     */
    final public Integer durability;

    /**
     * the preferred delivery order for the content. This is used to select
     * between objects of differing types.
     */
    final public Integer priority;
    /**
     * the preferred delivery order for the content. Unlike priority, this is
     * used when there are multiple versions of the same item.
     */
    final public Order order;

    /**
     * states from which time 'missed' data should be retrieved. This is
     * typically used only on the retrieve or interest actions.
     */
    final public TimeTrigger start;
    /**
     * specifies the time until the subscription is dropped.
     */
    final public TimeTrigger expire;
    /**
     * obtain no more than the specified number of items.
     */
    final public Limit limit;

    /**
     * how far the request is allowed to travel. It can be considered a measure
     * of distance travelled.
     */
    final public DeliveryScope scope;
    /**
     * constrains the message rate to lower the load on the network. The
     * parameter is the maximum number of bits per second.
     */
    final public Integer throttle;

    /**
     * filter out (or in) the unnecessary fields.
     */
    final public String[] project;
    /**
     * reduce the quantity of items returned.
     */
    final public Selection select;

    /**
     * used as a check against priority. This does not affect request delivery,
     * but it will impact status.
     */
    final public Integer worth;
    /**
     * provides delivery notices concerning the progress of requests which meet
     * the subscription type/uid.
     */
    final public Notice notice;

    final public ChannelFilter channelFilter;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.action != null)
            sb.append(this.action.toString()).append(" Request ");
        if (this.uuid != null)
            sb.append(this.uuid).append(" ");
        if (this.uid != null)
            sb.append(this.uid).append(" ");
        if (this.topic != null)
            sb.append(this.topic).append(' ');
        return sb.toString();
    }

    public String toShow() {
        StringBuilder sb = new StringBuilder();
        if (this.action != null)
            sb.append(this.action.toString()).append(" Request ");
        if (this.uuid != null)
            sb.append('[').append(this.uuid).append("]");
        if (this.uid != null)
            sb.append(":[").append(this.uid).append("] ");
        if (this.topic != null)
            sb.append('@').append(this.topic);
        if (this.subtopic != null)
            sb.append('&').append(this.subtopic);
        if (this.quantifier != null)
            sb.append('&').append(this.quantifier);
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
                // final int size = (capacity < 50) ? capacity : 50;
                // final byte[] data = new byte[size];
                // source.unmarshall(data, 0, size);
                final byte[] data = source.marshall();
                plogger.error("PARCEL UNMARSHALLING PROBLEM: size {} data {}",
                        new Object[] {
                                capacity, data
                        }, ex);
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
    private final byte VERSION = (byte) 0x05;

    /**
     * The first few fields are required and are positional. The remainder are
     * optional, their presence is indicated by their nominal values.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.debug("version: {}", VERSION);
        dest.writeByte(VERSION);

        plogger.debug("request: [{}:{}]", this.uuid, this.uid);
        dest.writeValue(this.uuid);
        dest.writeValue(this.uid);
        if (CLIENT_LOGGING)
            plogger.debug("action: {}", this.action);
        Action.writeToParcel(dest, this.action);

        // PROVIDER
        if (CLIENT_LOGGING)
            plogger.debug("provider: {}", this.provider);
        Nominal.PROVIDER.writeToParcel(dest, flags);
        Provider.writeToParcel(this.provider, dest, flags);

        // PAYLOAD
        if (CLIENT_LOGGING)
            plogger.debug("payload: {}", this.payload);
        Nominal.PAYLOAD.writeToParcel(dest, flags);
        Payload.writeToParcel(this.payload, dest, flags);

        // INTENT

        // if (CLIENT_LOGGING)
        // plogger.debug("intent: {}", this.intent);
        // Nominal.INTENT.writeToParcel(dest, flags);
        // Payload.writeToParcel(this.intent, dest, flags);

        // SERIAL MOMENT
        if (CLIENT_LOGGING)
            plogger.debug("moment: {}", this.moment);
        Nominal.MOMENT.writeToParcel(dest, flags);
        SerialMoment.writeToParcel(this.moment, dest, flags);

        // TOPIC
        if (CLIENT_LOGGING)
            plogger.debug("topic: [{}]+[{}]", this.topic, this.subtopic);
        Nominal.TOPIC.writeToParcel(dest, flags);
        Topic.writeToParcel(this.topic, dest, flags);

        Nominal.SUBTOPIC.writeToParcel(dest, flags);
        Topic.writeToParcel(this.subtopic, dest, flags);

        // QUANTIFIER
        if (CLIENT_LOGGING)
            plogger.debug("quantifier: {}", this.quantifier);
        Nominal.QUANTIFIER.writeToParcel(dest, flags);
        Quantifier.writeToParcel(this.quantifier, dest, flags);

        // DOWNSAMPLE
        if (CLIENT_LOGGING)
            plogger.debug("downsample: {}", this.downsample);
        Nominal.DOWNSAMPLE.writeToParcel(dest, flags);
        dest.writeValue(this.downsample);

        // DURABILITY
        if (CLIENT_LOGGING)
            plogger.debug("durability: {}", this.durability);
        Nominal.DURABLILITY.writeToParcel(dest, flags);
        dest.writeValue(this.durability);

        // PRIORITY
        if (CLIENT_LOGGING)
            plogger.debug("priority: {}", this.priority);
        Nominal.PRIORITY.writeToParcel(dest, flags);
        dest.writeValue(this.priority);

        // ORDER
        if (CLIENT_LOGGING)
            plogger.debug("order: {}", this.order);
        Nominal.ORDER.writeToParcel(dest, flags);
        Order.writeToParcel(this.order, dest, flags);

        // START
        if (CLIENT_LOGGING)
            plogger.debug("start: {}", this.start);
        Nominal.START.writeToParcel(dest, flags);
        TimeTrigger.writeToParcel(this.start, dest, flags);

        // EXPIRE
        if (CLIENT_LOGGING)
            plogger.debug("expire: {}", this.expire);
        Nominal.EXPIRE.writeToParcel(dest, flags);
        TimeTrigger.writeToParcel(this.expire, dest, flags);

        // LIMIT
        if (CLIENT_LOGGING)
            plogger.debug("limit: {}", this.limit);
        Nominal.LIMIT.writeToParcel(dest, flags);
        Limit.writeToParcel(this.limit, dest, flags);

        // DELIVERY SCOPE
        if (CLIENT_LOGGING)
            plogger.debug("scope: {}", this.scope);
        Nominal.DELIVERY_SCOPE.writeToParcel(dest, flags);
        DeliveryScope.writeToParcel(this.scope, dest, flags);

        // THROTTLE
        if (CLIENT_LOGGING)
            plogger.debug("throttle: {}", this.throttle);
        Nominal.THROTTLE.writeToParcel(dest, flags);
        dest.writeValue(this.throttle);

        // WORTH
        if (CLIENT_LOGGING)
            plogger.debug("worth: {}", this.worth);
        Nominal.WORTH.writeToParcel(dest, flags);
        dest.writeValue(this.worth);

        // NOTICE
        if (CLIENT_LOGGING)
            plogger.debug("notice: {}", this.notice);
        Nominal.NOTICE.writeToParcel(dest, flags);
        Notice.writeToParcel(this.notice, dest, flags);

        // SELECTION
        if (CLIENT_LOGGING)
            plogger.debug("selection: {}", this.select);
        Nominal.SELECTION.writeToParcel(dest, flags);
        Selection.writeToParcel(this.select, dest, flags);

        // PROJECTION
        if (CLIENT_LOGGING)
            plogger.debug("projection: {}", this.project);
        Nominal.PROJECTION.writeToParcel(dest, flags);
        dest.writeStringArray(this.project);

        // CHANNEL FILTER
        if (CLIENT_LOGGING)
            plogger.debug("channelFilter: [{}]", this.channelFilter);
        Nominal.CHANNEL_FILTER.writeToParcel(dest, flags);
        ChannelFilter.writeToParcel(this.channelFilter, dest, flags);
    }

    /**
     * When the request is placed into a parcel the fields have nominal
     * identifiers.
     */
    private enum Nominal {
        PROVIDER(2),
        PAYLOAD(3),
        MOMENT(4),
        TOPIC(5),
        SUBTOPIC(6),
        QUANTIFIER(7),
        DOWNSAMPLE(8),
        DURABLILITY(9),
        PRIORITY(10),
        ORDER(11),
        START(12),
        EXPIRE(13),
        LIMIT(14),
        DELIVERY_SCOPE(15),
        THROTTLE(16),
        WORTH(17),
        NOTICE(18),
        SELECTION(19),
        PROJECTION(20),
        CHANNEL_FILTER(21),
        INTENT(22);

        public final int code;

        private Nominal(int code) {
            this.code = code;
        }

        public void writeToParcel(Parcel dest, int flags) {
            // TODO Auto-generated method stub

        }

        public static final Map<Integer, Nominal> lookup = new HashMap<Integer, Nominal>();
        static {
            for (Nominal nominal : EnumSet.allOf(Nominal.class)) {
                lookup.put(nominal.code, nominal);
            }
        }
    }

    private Nominal getNominalFromParcel(Parcel in) {
        final int nominalRaw = in.readInt();
        return Nominal.lookup.get(new Integer(nominalRaw));
    }

    /**
     * @param in
     * @throws IncompleteRequest
     */
    private AmmoRequest(Parcel in) throws IncompleteRequest {
        final byte version;
        try {
            version = in.readByte();
            if (version < VERSION) {
                plogger.info("AMMO REQUEST VERSION MISMATCH, received {}, expected {}",
                        version, VERSION);
            } else if (version > VERSION) {
                plogger.warn("AMMO REQUEST VERSION MISMATCH, received {}, expected {}",
                        version, VERSION);
                throw new ParcelFormatException("AMMO REQUEST VERSION MISMATCH");
            } else {
                plogger.trace("AMMO REQUEST VERSION MATCH: {}", version);
            }
        } catch (Exception ex) {
            plogger.error("unmarshall on version", ex);
            throw new IncompleteRequest(ex);
        }
        if (version < (byte) 6) {
            try {
                this.uuid = (String) in.readValue(String.class.getClassLoader());
                this.uid = (version < (byte) 3) ? this.uuid : (String) in.readValue(String.class
                        .getClassLoader());
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
                this.moment = (version < (byte) 4) ? SerialMoment.DEFAULT : SerialMoment
                        .readFromParcel(in);
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
                this.limit = (version < (byte) 2) ? new Limit(100) : Limit.readFromParcel(in);
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
            try {
                this.channelFilter = (version < (byte) 5) ? null : ChannelFilter.readFromParcel(in);
                plogger.trace("channelFilter: {}", this.channelFilter);
            } catch (Exception ex) {
                plogger.error("decoding channelFilter: {}", ex);
                throw new IncompleteRequest(ex);
            }
            this.intent = null;
            return;
        }

        try {
            this.uuid = ((String) in.readValue(String.class.getClassLoader()));
            this.uid = (String) in.readValue(String.class.getClassLoader());
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
        final Builder builder = newBuilder(null);
        builder.limit = new Limit(100);
        builder.moment = SerialMoment.DEFAULT;
        for (Nominal nominal = getNominalFromParcel(in); nominal != null; nominal = getNominalFromParcel(in)) {
            switch (nominal) {
                case PROVIDER:
                    try {
                        builder.provider = Provider.readFromParcel(in);
                        plogger.trace("provider: {}", builder.provider);
                    } catch (Exception ex) {
                        plogger.error("decoding provider: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case PAYLOAD:
                    try {
                        builder.payload = Payload.readFromParcel(in);
                        plogger.trace("payload: {}", builder.payload);
                    } catch (Exception ex) {
                        plogger.error("decoding payload: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case MOMENT:
                    try {
                        builder.moment = SerialMoment.readFromParcel(in);
                        plogger.trace("moment: {}", builder.moment);
                    } catch (Exception ex) {
                        plogger.error("decoding moment: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case TOPIC:
                    try {
                        builder.topic = Topic.readFromParcel(in);
                        plogger.trace("topic: {}", builder.topic);
                    } catch (Exception ex) {
                        plogger.error("decoding topic: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                case SUBTOPIC:
                    try {
                        builder.subtopic = Topic.readFromParcel(in);
                        plogger.trace("subtopic: {}", builder.subtopic);
                    } catch (Exception ex) {
                        plogger.error("decoding subtopic: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case QUANTIFIER:
                    try {
                        builder.quantifier = Quantifier.readFromParcel(in);
                        plogger.trace("quantifier: {}", builder.quantifier);
                    } catch (Exception ex) {
                        plogger.error("decoding quantifier: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case DOWNSAMPLE:
                    try {
                        builder.downsample = (Integer) in.readValue(Integer.class.getClassLoader());
                        plogger.trace("downsample: {}", builder.downsample);
                    } catch (Exception ex) {
                        plogger.error("decoding downsample: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case DURABLILITY:
                    try {
                        builder.durability = (Integer) in.readValue(Integer.class.getClassLoader());
                        plogger.trace("durability: {}", builder.durability);
                    } catch (Exception ex) {
                        plogger.error("decoding durability: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case PRIORITY:
                    try {
                        builder.priority = (Integer) in.readValue(Integer.class.getClassLoader());
                        plogger.trace("priority: {}", builder.priority);
                    } catch (Exception ex) {
                        plogger.error("decoding priority: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case ORDER:
                    try {
                        builder.order = Order.readFromParcel(in);
                        plogger.trace("order: {}", builder.order);
                    } catch (Exception ex) {
                        plogger.error("decoding order: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case START:
                    try {
                        builder.start = TimeTrigger.readFromParcel(in);
                        plogger.trace("start: {}", builder.start);
                    } catch (Exception ex) {
                        plogger.error("unmarshall start {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case EXPIRE:
                    try {
                        builder.expire = TimeTrigger.readFromParcel(in);
                        plogger.trace("expire: {}", builder.expire);
                    } catch (Exception ex) {
                        plogger.error("decoding expire: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case LIMIT:
                    try {
                        builder.limit = Limit.readFromParcel(in);
                        plogger.trace("limit: {}", builder.limit);
                    } catch (Exception ex) {
                        plogger.error("decoding limit: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case DELIVERY_SCOPE:
                    try {
                        builder.scope = DeliveryScope.readFromParcel(in);
                        plogger.trace("scope: {}", builder.scope);
                    } catch (Exception ex) {
                        plogger.error("decoding scope: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case THROTTLE:
                    try {
                        builder.throttle = (Integer) in.readValue(Integer.class.getClassLoader());
                        plogger.trace("throttle: {}", builder.throttle);
                    } catch (Exception ex) {
                        plogger.error("unmarshall throttle {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case WORTH:
                    try {
                        builder.worth = (Integer) in.readValue(Integer.class.getClassLoader());
                        plogger.trace("worth: {}", builder.worth);
                    } catch (Exception ex) {
                        plogger.error("decoding worth: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case NOTICE:
                    try {
                        builder.notice = Notice.readFromParcel(in);
                        plogger.trace("notice: {}", builder.notice);
                    } catch (Exception ex) {
                        plogger.error("decoding notice: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case SELECTION:
                    try {
                        builder.select = Selection.readFromParcel(in);
                        plogger.trace("select: {}", builder.select);
                    } catch (Exception ex) {
                        plogger.error("decoding select: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case PROJECTION:
                    try {
                        builder.project = in.createStringArray();
                        plogger.trace("projection: {}", builder.project);
                    } catch (Exception ex) {
                        plogger.error("decoding projection: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case CHANNEL_FILTER:
                    try {
                        builder.channelFilter = ChannelFilter.readFromParcel(in);
                        plogger.trace("channelFilter: {}", builder.channelFilter);
                    } catch (Exception ex) {
                        plogger.error("decoding channelFilter: {}", ex);
                        throw new IncompleteRequest(ex);
                    }
                    break;
                case INTENT:
                    builder.intent = BroadIntent.readFromParcel(in);
                    break;
                default:
            }
        }

        this.provider = builder.provider;
        this.intent = builder.intent;
        this.payload = builder.payload;
        this.moment = builder.moment;

        this.topic = builder.topic;
        this.subtopic = builder.subtopic;
        this.quantifier = builder.quantifier;
        this.channelFilter = builder.channelFilter;

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
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // *********************************
    // IAmmoRequest Support
    // *********************************

    private AmmoRequest(Action action, Builder builder) {
        this.action = action;
        this.uid = builder.uid;

        this.provider = builder.provider;
        this.intent = builder.intent;
        this.payload = builder.payload;
        this.moment = builder.moment;

        this.topic = builder.topic;
        this.subtopic = builder.subtopic;
        this.quantifier = builder.quantifier;
        this.channelFilter = builder.channelFilter;

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

    /**
     * Replace the request with req.
     */
    @Override
    public IAmmoRequest replace(IAmmoRequest req) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Replace the named request with ?
     */
    @Override
    public IAmmoRequest replace(String uuid) {
        return null;
    }

    /**
     * The principle factory method for obtaining a request builder.
     * 
     * @param context
     * @return
     */
    public static Builder newBuilder(Context context) {
        return new AmmoRequest.Builder(context).reset();
    }

    /**
     * This method is deprecated. The resolver is no longer needed.
     * 
     * @param context
     * @param resolver
     * @return
     */
    public static Builder newBuilder(Context context, BroadcastReceiver resolver) {
        return new AmmoRequest.Builder(context).reset();
    }

    /**
     * This method (and its accompanying constructor
     * 
     * @param context
     * @param serviceBinder
     * @return
     */
    public static Builder newBuilder(Context context, IBinder serviceBinder) {
        return new AmmoRequest.Builder(context, serviceBinder).reset();
    }

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
     */
    private static final Intent MAKE_DISTRIBUTOR_REQUEST = new Intent(
            "edu.vu.isis.ammo.api.MAKE_REQUEST");

    public static class Builder implements IAmmoRequest.Builder {

        private enum ConnectionMode {
            /**
             * For some reason the service is not running.
             */
            UNAVAILABLE,
            /**
             * A connection has been requested but not yet granted.
             */
            BINDING,
            /**
             * Asynchronous request to obtain a connection over which
             * synchronous requests are made.
             */
            BOUND,
            /** Asynchronous request without a response */
            UNBOUND,
            /** No connection */
            NONE;
        }

        private final AtomicReference<ConnectionMode> mode;
        private final AtomicReference<IDistributorService> distributor;
        private final Context context;
        private final BlockingQueue<AmmoRequest> pendingRequestQueue;

        final private ServiceConnection conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                logger.info("service connected [{}] outstanding requests",
                        Builder.this.pendingRequestQueue.size());
                final IDistributorService distributor = IDistributorService.Stub
                        .asInterface(service);

                try {
                    for (final AmmoRequest request : Builder.this.pendingRequestQueue) {
                        final String ident = distributor.makeRequest(request);
                        logger.info("service bound : {} {}", request, ident);
                    }
                } catch (RemoteException ex) {
                    logger.error("no connection on recently bound connection", ex);
                    return;
                }
                Builder.this.distributor.set(distributor);
                Builder.this.mode.set(ConnectionMode.BOUND);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                logger.trace("service {} disconnected", name.flattenToShortString());
                Builder.this.mode.set(ConnectionMode.UNBOUND);
                Builder.this.distributor.set(null);
            }
        };

        /**
         * The builder acquires a connection to the service. The status of the
         * connection is managed. If the connection is not ready but there is a
         * reasonable expectation that it will be made then requests are placed
         * in a queue. The queue will be drained when the connection is
         * established. This works with the makeRequest() and
         * onServiceConnected() methods.
         * 
         * @param context
         */
        private Builder(Context context) {
            this.mode = new AtomicReference<ConnectionMode>(ConnectionMode.UNBOUND);
            this.distributor = new AtomicReference<IDistributorService>(null);
            this.context = context;
            this.pendingRequestQueue = new LinkedBlockingQueue<AmmoRequest>();
            try {
                final boolean isBound = this.context.bindService(MAKE_DISTRIBUTOR_REQUEST,
                        this.conn,
                        Context.BIND_AUTO_CREATE);
                logger.trace("is the service bound? {}", isBound);
                this.mode.compareAndSet(ConnectionMode.UNBOUND,
                        (isBound ? ConnectionMode.BINDING : ConnectionMode.UNAVAILABLE));
            } catch (ReceiverCallNotAllowedException ex) {
                logger.error("the service cannot be bound");
            }
        }

        /**
         * This constructor is for direct connections to the service (not IPC).
         * Primarily for testing.
         * 
         * @param context
         * @param serviceBinder
         */
        private Builder(Context context, IBinder serviceBinder) {
            this.context = context;
            this.pendingRequestQueue = null;
            this.mode = new AtomicReference<ConnectionMode>(ConnectionMode.BOUND);
            this.distributor = new AtomicReference<IDistributorService>(
                    IDistributorService.Stub.asInterface(serviceBinder));

        }

        private String uid;

        private Provider provider;
        public BroadIntent intent;
        private Payload payload;

        private SerialMoment moment;
        private Topic topic;
        private Topic subtopic;
        private Quantifier quantifier;
        private ChannelFilter channelFilter;

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
         * Generally the BOUND approach should be used as it has the best
         * performance. Sometimes this is not possible and the startService()
         * method must be used (in the case of BroadcastReceiver). It may also
         * be the case that the service has not yet started and the binder has
         * not yet been obtained. In that interim case the requests are put in a
         * queue in anticipation of a connection mode should be used.
         */
        private IAmmoRequest makeRequest(final AmmoRequest request) throws RemoteException {
            logger.info("make service request {} {}", this.mode, request);
            switch (this.mode.get()) {
                case BOUND:
                    final String ident = this.distributor.get().makeRequest(request);
                    logger.info("service bound : {} {}", request, ident);
                    break;
                case UNBOUND:
                    final Intent parcelIntent = MAKE_DISTRIBUTOR_REQUEST.cloneFilter();
                    parcelIntent.putExtra("request", request);
                    final ComponentName componentName = this.context.startService(parcelIntent);
                    if (componentName != null) {
                        logger.debug("service binding : {}", componentName.getClassName());
                    } else {
                        logger.error("service binding : {}", parcelIntent);
                    }
                    break;
                case BINDING:
                    try {
                        this.pendingRequestQueue.put(request);
                    } catch (InterruptedException ex) {
                        logger.debug("make request interrupted ", ex);
                    }
                    break;
                case NONE:

                case UNAVAILABLE:
                default:
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
        public IAmmoRequest unpost() throws RemoteException {
            return this.makeRequest(new AmmoRequest(Action.UNPOSTAL, this));
        }

        @Override
        public IAmmoRequest retrieve() throws RemoteException {
            return this.makeRequest(new AmmoRequest(Action.RETRIEVAL, this));
        }

        @Override
        public IAmmoRequest unretrieve() throws RemoteException {
            return this.makeRequest(new AmmoRequest(Action.UNRETRIEVAL, this));
        }

        @Override
        public IAmmoRequest subscribe() throws RemoteException {
            return this.makeRequest(new AmmoRequest(Action.SUBSCRIBE, this));
        }

        @Override
        public IAmmoRequest unsubscribe() throws RemoteException {
            return this.makeRequest(new AmmoRequest(Action.UNSUBSCRIBE, this));
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
                if (this.conn == null)
                    return;
                this.context.unbindService(this.conn);
            } catch (IllegalArgumentException ex) {
                logger.warn("the service is not bound or registered", ex);
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
            this.topic(Topic.DEFAULT);
            this.subtopic(Topic.DEFAULT);
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

        /**
         *  
         */
        @Override
        public Builder useChannel(String val) {
            if (val == null) {
                this.channelFilter = null;
                return this;
            }
            this.channelFilter = new ChannelFilter(val);
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
         * To clear the notices use notice(Notice.RESET).
         */
        public Builder notice(Notice.Threshold threshold, Via.Type type) {
            if (this.notice == null)
                this.notice = Notice.newInstance();
            this.notice.setItem(threshold, type);
            plogger.trace("notice=[{}]", this.notice);
            return this;
        }

        /**
         * It replaces the current notice object with the argument. The notice
         * set can be cleared by using this method with the Notice.RESET object.
         */
        @Override
        public Builder notice(Notice val) {
            this.notice = val;
            return this;
        }
    }

    @Override
    public void cancel() {
        // TODO Auto-generated method stub

    }

}
