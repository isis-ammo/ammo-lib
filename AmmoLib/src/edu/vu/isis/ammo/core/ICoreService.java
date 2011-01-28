package edu.vu.isis.ammo.core;

import android.content.Intent;

public interface ICoreService {

	public static final String PREPARE_FOR_STOP = "edu.vu.isis.ammo.core.CoreService.PREPARE_FOR_STOP";
	public static final String LAUNCH = "edu.vu.isis.ammo.core.CoreService.LAUNCH";
	public static final String BIND = "edu.vu.isis.ammo.core.CoreService.BIND";
	
	public static final Intent CORE_APPLICATION_LAUNCH_SERVICE_INTENT = new Intent(ICoreService.LAUNCH);

}
