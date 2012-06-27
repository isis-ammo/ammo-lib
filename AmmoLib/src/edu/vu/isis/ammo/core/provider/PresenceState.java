package edu.vu.isis.ammo.core.provider;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

/**
 * Valid values for the STATE field.
 * The codes are independent, they may be combined to form the state.
 * If that is the case they can be teased apart with the encode/decode methods.
 * 
 */
public enum PresenceState {

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
	private PresenceState(int code) { this.code = code; }
	static public PresenceState lookup(long lowMask) {
		return PresenceState.lookupMap.get(lowMask);
	}
	private static final HashMap<Long, PresenceState> lookupMap;
	static {
		final EnumSet<PresenceState> set = EnumSet.allOf(PresenceState.class);
		lookupMap = new HashMap<Long, PresenceState>(set.size());
		for (final PresenceState state : set) {
			lookupMap.put(Long.valueOf(state.code), state);
		}
	}
	/**
	 * Provide a set of states to be encoded into a long integer.
	 * 
	 * @param stateSet
	 * @return
	 */
	public static long encodeState(Set<PresenceState> stateSet) {
		long encodedState = 0;
		for (final PresenceState state : stateSet) {
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
	public static Set<PresenceState> decodeStates(long encodedState) {
		long lowMask = Long.lowestOneBit(encodedState);
		if (lowMask < 1) return null;
		final EnumSet<PresenceState> decodedState = EnumSet.of(PresenceState.lookup(lowMask));
		long highMask = Long.highestOneBit(encodedState);
		if (lowMask == highMask) return decodedState;
		lowMask = lowMask << 1;
		while (lowMask != highMask) {
			decodedState.add(PresenceState.lookup(lowMask));
			lowMask = lowMask << 1;
		}
		decodedState.add(PresenceState.lookup(lowMask));
		return decodedState;
	}
	/**
	 * Produce a set of states from an encoded long integer.
	 * 
	 * @param stateSet an integer of states.
	 * @return
	 */
	public static PresenceState decodeState( long encodedState) {
		long lowMask = Long.lowestOneBit(encodedState);
		if (lowMask < 1) return PresenceState.ABSENT;
		return PresenceState.lookup(lowMask);
	}
}
