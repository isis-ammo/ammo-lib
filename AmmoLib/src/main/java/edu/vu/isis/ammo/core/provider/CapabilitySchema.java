package edu.vu.isis.ammo.core.provider;

import java.util.ArrayList;

import android.provider.BaseColumns;
import edu.vu.isis.ammo.util.EnumUtils;

public enum CapabilitySchema implements RelationSchema {
	/** This is a locally unique identifier for the request */
	ID(BaseColumns._ID, "TEXT"),
	
	/** This is a universally unique identifier for the request */
	UUID("TEXT"),

	/** When the request was made */
	ORIGIN("TEXT"),
	
	/** Who last modified the request */
	OPERATOR( "TEXT"),

	/** The data topic of the objects being subscribed to */
	TOPIC("TEXT"),

	/** Qualifying parameters for the topic (optional : NULL) */
	SUBTOPIC("TEXT"),

	/** 
	 * Use the PresenceState enum class to make these values useful.
	 * e.g. 
	 * @code{
	 * final EnumSet<PresenceState> set = Presence.decode(cursor.getLong(cursor.getColumnIndex(ix)));
	 * }
	 * You can set the value in a similar fashion.
	 * @code{
	 * final long encodedValue = Presence.encode(EnumSet.of(PresenceState.RARE));
	 * cv.put(CapablitySchema.STATE.field, encodedValue);
	 * }
	 * The encoded value is placed in the state field.
	 */
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

	/** textual field name */
	final public String field; 
	
	/** type */
	final public String type; 
	
	private CapabilitySchema( String type) {
		this.field = this.name();
		this.type = type;
	}
	
	private CapabilitySchema( String field, String type) {
		this.field = field;
		this.type = type;
	}
	
	/**
	 * an array of all field names
	 */
	public static final String[] FIELD_NAMES 
		= EnumUtils.buildFieldNames(CapabilitySchema.class);
	
	/**
	 * map an array of field names to fields.
	 * 
	 * @param names an array of field names
	 * @return an array of fields
	 */
	public static ArrayList<CapabilitySchema> mapFields(final String[] names) {
		return EnumUtils.getFields(CapabilitySchema.class, names);
	}

	@Override
	public String getField() {
		return this.field;
	}
	
}
