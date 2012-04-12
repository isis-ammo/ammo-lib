package edu.vu.isis.ammo.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncompleteRequest extends Exception {
	public static final Logger logger = LoggerFactory.getLogger("class.IncompleteRequest");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IncompleteRequest( Exception ex ) {
		super(ex);
	}

}
