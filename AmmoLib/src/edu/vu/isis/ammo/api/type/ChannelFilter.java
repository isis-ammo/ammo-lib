package edu.vu.isis.ammo.api.type;

import android.os.Parcel;
import android.os.Parcelable;
import edu.vu.isis.ammo.api.IncompleteRequest;

public class ChannelFilter extends AmmoType {
	
	public final static String SERIAL = "serial";
	public final static String GATEWAY = "gateway";
	public final static String MULTICAST = "multicast";
	public final static String RELIABLE_MULTICAST = "reliablemulticast";
	public final static String JOURNAL = "journal";

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
		if (AmmoType.isNull(source)) return null;
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

	public ChannelFilter(String val) {
		this.name = val;
	}

}
