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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import edu.vu.isis.ammo.api.IncompleteRequest;

public class Topic extends AmmoType {

    static final Logger logger = LoggerFactory.getLogger("type.topic");

    public static final String ACTION_INVITATION = "edu.vu.isis.ammo.ACTION_MESSAGE_INVITATION";
    
    public static final String EXTRA_TOPIC = "topic";
    public static final String EXTRA_SUBTOPIC = "subtopic";
    public static final String EXTRA_UID = "uid";
    public static final String EXTRA_CHANNEL = "channel";

    static final public Topic RESET = null;

    public static final String DEFAULT = "";

    static final private int OID_ID = 0;
    static final private int STR_ID = 1;

    static final Topic NONE = new Topic(Oid.EMPTY);

    public enum Type {
        /**
         * An object identifier (list of integers)
         */
        OID(OID_ID),
        /**
         * A topic as a string.
         */
        STR(STR_ID);

        final public int id;

        private Type(final int id) {
            this.id = id;
        }

        static public Type getInstance(final int id) {
            switch (id) {
                case OID_ID:
                    return OID;
                case STR_ID:
                    return STR;
            }
            return null;
        }
    };

    final private Type type;
    final private String str;
    final private Oid oid;

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Topic> CREATOR =
            new Parcelable.Creator<Topic>() {

                @Override
                public Topic createFromParcel(Parcel source) {
                    try {
                        return new Topic(source);
                    } catch (IncompleteRequest ex) {
                        return null;
                    }
                }

                @Override
                public Topic[] newArray(int size) {
                    return new Topic[size];
                }
            };

    public static Topic readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        try {
            return new Topic(source);
        } catch (IncompleteRequest ex) {
            return null;
        }
    }

    public static Topic[] readArrayFromParcel(Parcel source) {
        return source.createTypedArray(CREATOR);
    }

    public static Topic[] readSingleFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        try {
            return new Topic[] {
                    new Topic(source)
            };
        } catch (IncompleteRequest ex) {
            return null;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall topic {}", this);
        dest.writeInt(this.type.id);

        switch (this.type) {
            case OID:
                this.oid.writeToParcel(dest, flags);
                return;
            case STR:
                dest.writeString(this.str);
                return;
        }
    }

    public static void writeToParcel(final Topic[] subtopic, final Parcel dest, int flags) {
        dest.writeTypedArray(subtopic, flags);
    }

    public Topic(Parcel in) throws IncompleteRequest {
        int ordinal = -1;
        try {
            ordinal = in.readInt();

            this.type = Type.getInstance(ordinal);
            if (this.type == null) {
                this.str = null;
                this.oid = null;
            } else
                switch (this.type) {
                    case OID:
                        this.str = null;
                        this.oid = Oid.CREATOR.createFromParcel(in);
                        break;
                    case STR:
                        this.str = in.readString();
                        this.oid = null;
                        break;
                    default:
                        this.str = null;
                        this.oid = null;
                }

        } catch (ArrayIndexOutOfBoundsException ex) {
            plogger.error("bad topic {} {}", ordinal, ex);
            throw new IncompleteRequest(ex);
        }
        plogger.trace("unmarshall topic {}", this);
    }

    // *********************************
    // Standard Methods
    // *********************************
    /**
     * Do not used toString() for serializing the topic. Use asString() instead.
     * toString() is intended for reading by humans.
     */
    @Override
    public String toString() {
        if (this.type == null) {
            return "<no type>";
        }
        switch (this.type) {
            case OID:
                return "oid:" + this.oid.toString();
            case STR:
                return "str:" + this.str;
            default:
                return "<unknown type>" + this.type;
        }
    }

    // *********************************
    // IAmmoReques Support
    // *********************************

    /**
     * The constructor taking a string is symmetric with the asString() method.
     * 
     * @param val
     */
    public Topic(String val) {
        this.type = Type.STR;
        this.str = val;
        this.oid = null;
    }

    public Topic(Oid val) {
        this.type = Type.OID;
        this.str = null;
        this.oid = val;
    }

    /**
     * When the topic is to be transmitted as a string this is the method which
     * should be used <b>NOT</b> toString().
     * 
     * @return
     */
    @Override
    public String asString() {
        if (this.type == null) {
            return "<no type>";
        }
        switch (this.type) {
            case OID:
                return this.oid.toString();
            case STR:
                return this.str;
            default:
                return "<unknown type>" + this.type;
        }
    }

    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Topic))
            return false;
        final Topic that = (Topic) obj;
        if (AmmoType.differ(this.type, that.type))
            return false;
        switch (this.type) {
            case STR:
                if (AmmoType.differ(this.str, that.str))
                    return false;
                return true;
            case OID:
                if (AmmoType.differ(this.oid, that.oid))
                    return false;
                return true;
            default:
                plogger.warn("invalid type {}", this.type);
                return false;
        }
    }

    @Override
    public synchronized int hashCode() {
        if (!this.dirtyHashcode.getAndSet(false))
            return this.hashcode;
        final HashBuilder hb = AmmoType.HashBuilder.newBuilder()
                .increment(this.type);
        switch (this.type) {
            case STR:
                hb.increment(this.str);
                break;
            case OID:
                hb.increment(this.oid);
                break;
            default:
                hb.increment(this.str);
                break;
        }
        this.hashcode = hb.hashCode();
        return this.hashcode;
    }

    /**
     * A set of methods for creating lists of topics.
     * 
     * @param val
     * @return
     */
    public static Topic[] newList(List<String> val) {
        final Topic[] subtopic = new Topic[val.size()];
        int ix = 0;
        for (final String item : val) {
            subtopic[ix] = new Topic(item);
            ix++;
        }
        return subtopic;
    }

    public static Topic[] newList(Oid val) {
        final Topic[] subtopic = new Topic[1];
        subtopic[0] = new Topic(val);
        return subtopic;
    }

    public static Topic[] newList(String val) {
        final Topic[] subtopic = new Topic[1];
        subtopic[0] = new Topic(val);
        return subtopic;
    }

    public static Topic[] newEmptyList() {
        return new Topic[0];
    }

    public static String[] asString(final Topic[] subtopic) {
        final String[] result = new String[subtopic.length];
        for (int ix = 0; ix < result.length; ++ix) {
            result[ix] = subtopic[ix].asString();
        }
        return result;
    }

    public static IntentBuilder getIntentBuilder() {
        return new IntentBuilder();
    }

    static public class IntentBuilder {

        private String[] topic = null;
        private String auid = null;
        private String channel = null;

        public IntentBuilder() {
        }

        public IntentBuilder topic(final String[] topic) {
            this.topic = topic;
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

        public Intent build(final Context context, final String action) {
            final Uri.Builder uriBuilder = new Uri.Builder()
                    .scheme("ammo")
                    .authority(this.topic[0]);

            if (this.topic != null) {
                for (final String st : this.topic) {
                    uriBuilder.appendPath(st);
                }
            }

            final Intent invited = new Intent().setAction(action)
                    .setData(uriBuilder.build())
                    .putExtra(EXTRA_TOPIC, this.topic);

            if (this.topic != null)
                invited.putExtra(EXTRA_SUBTOPIC, this.topic);

            if (this.auid != null)
                invited.putExtra(EXTRA_UID, this.auid);

            if (this.channel != null)
                invited.putExtra(EXTRA_CHANNEL, this.channel);

            return invited;
        }
    }

}
