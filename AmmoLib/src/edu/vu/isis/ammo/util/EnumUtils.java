package edu.vu.isis.ammo.util;


public class EnumUtils {
	/**
	 * an array of all field names
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
	 * 
	 * @param names an array of field names
	 * @return an array of fields
	 */
	public static <E extends Enum<E>> E[] mapFields(Class<E> clazz, final String[] names) {
		@SuppressWarnings("unchecked")
		final E[] fields = (E[]) new Object[names.length];
		int ix = 0;
		for (final String name : names) {
			fields[ix] = E.valueOf(clazz, name);
			ix++;
		}
		return fields;
	}
}
