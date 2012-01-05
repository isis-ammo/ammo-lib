/*Copyright (C) 2010-2012 Institute for Software Integrated Systems (ISIS)
This software was developed by the Institute for Software Integrated
Systems (ISIS) at Vanderbilt University, Tennessee, USA for the 
Transformative Apps program under DARPA, Contract # HR011-10-C-0175.
The United States Government has unlimited rights to this software. 
The US government has the right to use, modify, reproduce, release, 
perform, display, or disclose computer software or computer software 
documentation in whole or in part, in any manner and for any 
purpose whatsoever, and to have or authorize others to do so.
*/
package edu.vu.isis.ammo.core.provider;

/**
 * Miscellaneous things required by the Ammo content provider schemas.
 * 
 * @author feisele
 */
public interface IAmmoSchema {
	
	// for use by ContentResolver.openFile() and company.
	public static final String FILE_MODE_READ_ONLY = "r";
	public static final String FILE_MODE_WRITE_ONLY = "w";
	public static final String FILE_MODE_WRITE_APPEND = "wa";
	public static final String FILE_MODE_READ_WRITE = "rw";
	public static final String FILE_MODE_READ_WRITE_TRUNC = "rwt";

}
