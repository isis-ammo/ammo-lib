
package edu.vu.isis.ammo.api.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Parcel;
import android.os.Parcelable;
import edu.vu.isis.ammo.api.IncompleteRequest;

/**
 * <p>
 * The channel filter provides control to the application to restrict which
 * channels are eligible for delivery of the message in question.
 * <p>
 * In particular the filtered channel is <b>NOT</b> to be used.
 */
public class ChannelFilter extends AmmoType {
    static final Logger logger = LoggerFactory.getLogger("type.channel-filter");

    public final static String SERIAL = "serial";
    public final static String GATEWAY = "gateway";
    public final static String GATEWAYMEDIA = "gatewaymedia";
    public final static String MULTICAST = "multicast";
    public final static String RELIABLE_MULTICAST = "reliablemulticast";
    public final static String JOURNAL = "journal";
    public final static String MOCK = "mock";

    final private String name;

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<ChannelFilter> CREATOR =
            new Parcelable.Creator<ChannelFilter>() {

                @Override
                public ChannelFilter createFromParcel(Parcel source) {
                    try {
                        return new ChannelFilter(source);
                    } catch (IncompleteRequest ex) {
                        return null;
                    }
                }

                @Override
                public ChannelFilter[] newArray(int size) {
                    return new ChannelFilter[size];
                }
            };

    public static ChannelFilter readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        try {
            return new ChannelFilter(source);
        } catch (IncompleteRequest ex) {
            return null;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall channel {}", this);

        dest.writeString(this.name);
        return;
    }

    public ChannelFilter(Parcel in) throws IncompleteRequest {
        int ordinal = -1;
        try {
            this.name = in.readString();

        } catch (ArrayIndexOutOfBoundsException ex) {
            plogger.error("bad channel {} {}", ordinal, ex);
            throw new IncompleteRequest(ex);
        }
        plogger.trace("unmarshall channel {}", this);
    }

    // *********************************
    // Standard Methods
    // *********************************
    @Override
    public String toString() {
        return this.name;
    }

    public String cv() {
        return this.name;
    }

    // *********************************
    // IAmmoRequest Support
    // *********************************

    /**
     * Any value is allowed. null implies that there is no filter applied. Any
     * other values represent channels which should <b>NOT</b> be used for
     * delivery. The blank value would imply that any channel named blank would
     * not be used.
     */
    public ChannelFilter(String val) {
        this.name = val;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ChannelFilter))
            return false;
        final ChannelFilter that = (ChannelFilter) obj;
        if (AmmoType.differ(this.name, that.name))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String asString() {
        logger.error("asString() not implemented");
        return null;
    }

}
