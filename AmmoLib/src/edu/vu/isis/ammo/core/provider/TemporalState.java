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
public enum TemporalState {

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
	private TemporalState(int code) { this.code = code; }
	static public TemporalState lookup(long lowMask) {
		return TemporalState.lookupMap.get(lowMask);
	}
	private static final HashMap<Long, TemporalState> lookupMap;
	static {
		final EnumSet<TemporalState> set = EnumSet.allOf(TemporalState.class);
		lookupMap = new HashMap<Long, TemporalState>(set.size());
		for (final TemporalState state : set) {
			lookupMap.put(Long.valueOf(state.code), state);
		}
	}
	/**
	 * Provide a set of states to be encoded into a long integer.
	 * 
	 * @param stateSet
	 * @return
	 */
	public static long encodeState(Set<TemporalState> stateSet) {
		long encodedState = 0;
		for (final TemporalState state : stateSet) {
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
	public static Set<TemporalState> decodeStates(long encodedState) {
		long lowMask = Long.lowestOneBit(encodedState);
		if (lowMask < 1) return null;
		final EnumSet<TemporalState> decodedState = EnumSet.of(TemporalState.lookup(lowMask));
		long highMask = Long.highestOneBit(encodedState);
		if (lowMask == highMask) return decodedState;
		lowMask = lowMask << 1;
		while (lowMask != highMask) {
			decodedState.add(TemporalState.lookup(lowMask));
			lowMask = lowMask << 1;
		}
		decodedState.add(TemporalState.lookup(lowMask));
		return decodedState;
	}
	/**
	 * Produce a set of states from an encoded long integer.
	 * 
	 * @param stateSet an integer of states.
	 * @return
	 */
	public static TemporalState decodeState( long encodedState) {
		long lowMask = Long.lowestOneBit(encodedState);
		if (lowMask < 1) return TemporalState.ABSENT;
		return TemporalState.lookup(lowMask);
	}
}
