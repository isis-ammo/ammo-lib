package edu.vu.isis.ammo.util;

import java.util.ArrayList;
import java.util.EnumSet;

import edu.vu.isis.ammo.core.provider.RelationSchema;


public class EnumUtils {
	/**
	 * an array of all field names for an enumeration
	 * 
	 * @param clazz the enumeration for which the field names are extracted.
	 */
	 public static <E extends Enum<E> & RelationSchema> String[] buildFieldNames(Class<E> clazz) {
		final EnumSet<E> set = EnumSet.allOf(clazz);
		final String[] result = new String[set.size()];
		int ix = 0;
		for (final E cap : set) {
			result[ix] = cap.getField();
			ix++;
		}
		return result;
	}
	
	/**
	 * map an array of field names to fields.
	 * If an array of fields are required the toArray() methods may be used.
	 * 
	 * @param names an array of field names
	 * @return an array of fields
	 */
	public static <E extends Enum<E>> ArrayList<E> getFields(Class<E> clazz, final String[] names) {
		final ArrayList<E> fields = new ArrayList<E>(names.length);
		for (final String name : names) {
			fields.add(E.valueOf(clazz, name));
		}
		return fields;
	}
	
	public static <E extends Enum<E>> ArrayList<E> getField(Class<E> clazz, final String[] names) {
		final ArrayList<E> fields = new ArrayList<E>(names.length);
		for (final String name : names) {
			fields.add(E.valueOf(clazz, name));
		}
		return fields;
	}
}
