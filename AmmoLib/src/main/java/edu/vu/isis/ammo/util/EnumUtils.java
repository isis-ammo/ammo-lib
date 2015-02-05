/* Copyright (c) 2010-2015 Vanderbilt University
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


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
