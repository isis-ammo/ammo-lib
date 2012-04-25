package edu.vu.isis.ammo.api.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Parcel;
import edu.vu.isis.ammo.api.IncompleteRequest;

/**
 * deprecated:
 * DIRECTED_POSTAL, DIRECTED_SUBSCRIBE, PUBLISH
 * 
 */
public enum Action {
	NONE(-1),
	POSTAL(0), DIRECTED_POSTAL(1), 
	PUBLISH(2), 
	RETRIEVAL(3), 
	SUBSCRIBE(4), DIRECTED_SUBSCRIBE(5);

	public static final Logger logger = LoggerFactory.getLogger("class.Action");
	public static final Logger IPC_REQ_IN = LoggerFactory.getLogger( "ipc.request.inbound" );

	public int o;

	private Action(int ordinal) {
		this.o = ordinal;
	}

	@Override
	public String toString() {
		switch (this) {
		case NONE: return "NONE";
		case POSTAL: return "POSTAL";
		case RETRIEVAL: return "RETRIEVAL";
		case SUBSCRIBE: return "SUBSCRIBE";
		default: 
			return null;
		}
	}

	static public void writeToParcel(final Parcel dest, final Action action) {
		logger.trace("index: {}", action.o);
		dest.writeInt(action.o);
	}

	static public Action getInstance(Parcel in) throws IncompleteRequest { 
		final int ordinal = in.readInt();
		try {
			if (ordinal == NONE.o) return NONE;
			if (ordinal == POSTAL.o) return POSTAL;
			if (ordinal == RETRIEVAL.o) return RETRIEVAL;
			if (ordinal == SUBSCRIBE.o) return SUBSCRIBE;
			IncompleteRequest.logger.error("bad action index {}", ordinal);
			throw new IncompleteRequest("bad action");

		} catch (Exception ex) {
			IncompleteRequest.logger.error("bad action index {}", ordinal);
			throw new IncompleteRequest(ex);
		}
	}
}