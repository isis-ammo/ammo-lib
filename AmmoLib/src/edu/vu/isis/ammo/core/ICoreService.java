package edu.vu.isis.ammo.core;

import android.content.Intent;

public interface ICoreService {

	public static final String PREPARE_FOR_STOP = "edu.vu.isis.ammo.core.CoreService.PREPARE_FOR_STOP";
	public static final String LAUNCH = "edu.vu.isis.ammo.core.CoreService.LAUNCH";
	public static final String BIND = "edu.vu.isis.ammo.core.CoreService.BIND";
	
	/* intent broadcast when Ammo Core is ready */
	public static final String AMMO_READY = "edu.vu.isis.ammo.core.AMMO_READY";
	/* intent broadcast when Ammo Login happens or Login id is changed, either via preferences or via the login app */
	public static final String AMMO_LOGIN = "edu.vu.isis.ammo.core.AMMO_LOGIN";
	/* intent broadcast when Ammo Core is connected to Gateway */
	public static final String AMMO_CONNECTED = "edu.vu.isis.ammo.core.AMMO_CONNECTED";
	/* intent broadcast when Ammo Core is disconnected from Gateway */
	public static final String AMMO_DISCONNECTED = "edu.vu.isis.ammo.core.AMMO_DISCONNECTED";
	
	public static final Intent CORE_APPLICATION_LAUNCH_SERVICE_INTENT = new Intent(ICoreService.LAUNCH);

}
