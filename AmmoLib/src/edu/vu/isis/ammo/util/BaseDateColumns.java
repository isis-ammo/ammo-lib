/*
Copyright(c) 2010-2012

This software was developed by the Institute for Software Integrated
Systems (ISIS) at Vanderbilt University, Tennessee, USA for the 
Transformative Apps program under contract [contract citation, subcontract and prime contract]. 
The United States Government has unlimited rights to this software. 
The US government has the right to use, modify, reproduce, release, 
perform, display, or disclose computer software or computer software 
documentation in whole or in part, in any manner and for any 
purpose whatsoever, and to have or authorize others to do so.

 */

package edu.vu.isis.ammo.util;

public interface BaseDateColumns {
	/**
	 * The timestamp when the row was registered with the server.
	 * <P>Type: INTEGER (long)</P>
	 * from System.currentTimeMillis()
	 */
	public static final String CREATED_DATE = "created_date";
	
	/**
	 * Timestamp when a the row was last modified
	 * <P>Type: INTEGER (long)</P>
	 * from System.currentTimeMillis()
	 */
	public static final String MODIFIED_DATE = "modified_date";
	
}
