package edu.vu.isis.ammo.core.provider;

import java.util.ArrayList;

import android.provider.BaseColumns;
import edu.vu.isis.ammo.util.EnumUtils;

public enum InviteSchema implements RelationSchema {
	/** This is a locally unique identifier for the request */
	ID(BaseColumns._ID,"TEXT"),
	
	/** This is a universally unique identifier for the request */
	UUID("TEXT"),

	/** When the request was made */
	ORIGIN("TEXT"),
	
	/** Who is invited to the new sub-topic */
	INVITEE("TEXT"),

	/** The data topic of the objects being subscribed to */
	TOPIC("TEXT"),

	/** Qualifying parameters for the topic (optional : NULL) */
	SUBTOPIC("TEXT"),

	/** The time when no longer relevant (millisec);
	 * the request becomes stale and may be discarded. */
	EXPIRATION("INTEGER");

	/** textual field name */
	final public String field; 
	
	/** type */
	final public String type; 
	
	private InviteSchema( String type) {
		this.field = this.name();
		this.type = type;
	}
	
	private InviteSchema( String field, String type) {
		this.field = field;
		this.type = type;
	}
	
	/**
	 * an array of all field names
	 */
	public static final String[] FIELD_NAMES 
		= EnumUtils.buildFieldNames(InviteSchema.class);
	
	/**
	 * map an array of field names to fields.
	 * 
	 * @param names an array of field names
	 * @return an array of fields
	 */
	public static ArrayList<InviteSchema> mapFields(final String[] names) {
		return EnumUtils.getFields(InviteSchema.class, names);
	}

	@Override
	public String getField() {
		return this.field;
	}
	
}
