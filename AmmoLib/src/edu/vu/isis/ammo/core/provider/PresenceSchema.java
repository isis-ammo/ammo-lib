package edu.vu.isis.ammo.core.provider;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

import android.provider.BaseColumns;
import edu.vu.isis.ammo.util.EnumUtils;

public enum PresenceSchema {
	/** This is a locally unique identifier for the request */
	ID(BaseColumns._ID,"TEXT"),
	
	/** This is a universally unique identifier for the request */
	UUID("TEXT"),

	/** Device originating the request */
	ORIGIN("TEXT"),

	/** Who last modified the request */
	OPERATOR("TEXT"),

	/** Presence state: 1=available, 2=not available, etc.*/
	STATE("INTEGER"),

	/** The time when first observed (millisec); indicates the first time the peer was observed.*/
	FIRST("INTEGER"),

	/** when last observed (millisec);
	 * When the operator was last seen "speaking" on the channel.
	 * The latest field indicates the last time the peer was observed. */
	LATEST("INTEGER"),

	/** how many times seen since first.
	 * How many times the peer has been seen since FIRST.
	 * Each time LATEST is changed this COUNT should be incremented*/
	COUNT("INTEGER"),

	/** The time when no longer relevant (millisec);
	 * the request becomes stale and may be discarded. */
	EXPIRATION("INTEGER");
	
	/**
	 * Valid values for the STATE field.
	 * The codes are independent, they may be combined to form the state.
	 * If that is the case they can be teased apart with the encode/decode methods.
	 */
	public enum State {
		/** There is every reason to believe the device is present */
		PRESENT(0x01), 
		/** The device is seen regularly but intermittently */
		RARE(0x02), 
		/** The device is probably not currently present */
		MISSED(0x04),
		/** The device is almost certainly not present */
		LOST(0x08),
		/** There is no record for that device */
		ABSENT(0x10);
		
		public final long code;
		private State(int code) { this.code = code; }
		static public State lookup(long lowMask) {
			return State.lookupMap.get(lowMask);
		}
		private static final HashMap<Long, State> lookupMap;
		static {
			final EnumSet<State> set = EnumSet.allOf(State.class);
			lookupMap = new HashMap<Long, State>(set.size());
			for (final State state : set) {
				lookupMap.put(Long.valueOf(state.code), state);
			}
		}
	}
	/**
	 * Provide a set of states to be encoded into a long integer.
	 * 
	 * @param stateSet
	 * @return
	 */
	public long encodeState(Set<State> stateSet) {
		long encodedState = 0;
		for (final State state : stateSet) {
			encodedState |= state.code;
		}
		return encodedState;
	}
	/**
	 * Produce a set of states from an encoded long integer.
	 * 
	 * @param stateSet an integer of states.
	 * @return
	 */
	public Set<State> decodeStates( long encodedState) {
		long lowMask = Long.lowestOneBit(encodedState);
		if (lowMask < 1) return null;
		final EnumSet<State> decodedState = EnumSet.of(State.lookup(lowMask));
		long highMask = Long.highestOneBit(encodedState);
		if (lowMask == highMask) return decodedState;
		lowMask = lowMask << 1;
		while (lowMask != highMask) {
			decodedState.add(State.lookup(lowMask));
			lowMask = lowMask << 1;
		}
		decodedState.add(State.lookup(lowMask));
		return decodedState;
	}
	
	/** textual field name */
	final public String field; 
	
	/** type */
	final public String type; 
	
	private PresenceSchema( String type) {
		this.field = this.name();
		this.type = type;
	}
	
	private PresenceSchema( String field, String type) {
		this.field = field;
		this.type = type;
	}
	
	/**
	 * an array of all field names
	 */
	public static final String[] FIELD_NAMES 
		= EnumUtils.buildFieldNames(PresenceSchema.class);
	
	/**
	 * map an array of field names to fields.
	 * 
	 * @param names an array of field names
	 * @return an array of fields
	 */
	public static ArrayList<PresenceSchema> mapFields(final String[] names) {
		return EnumUtils.getFields(PresenceSchema.class, names);
	}
	
	@Override
	public String toString() {
	    return this.field;
	}
}
