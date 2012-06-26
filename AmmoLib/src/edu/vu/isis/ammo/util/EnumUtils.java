package edu.vu.isis.ammo.util;

import java.util.ArrayList;


public class EnumUtils {
	/**
	 * an array of all field names for an enumeration
	 * 
	 * @param clazz the enumeration for which the field names are extracted.
	 */
	public static <E extends Enum<E>> String[] buildFieldNames(Class<E> clazz) {
		final E[] es = clazz.getEnumConstants();
		final String[] result = new String[es.length];
		int ix = 0;
		for (final E cap : es) {
			result[ix] = cap.name();
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
}
