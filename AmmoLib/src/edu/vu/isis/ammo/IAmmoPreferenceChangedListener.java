package edu.vu.isis.ammo;

import android.content.Context;
import android.content.Intent;

public interface IAmmoPreferenceChangedListener {
	
	public void onAmmoPreferenceChanged(Context context, Intent intent);
	
	/**
	 * You should set up your AmmoPreferenceChangedReceiver here.
	 */
	public void initializeAmmoPreferenceChangedReceiver();
	public void uninitializeAmmoPreferenceChangedReceiver();
}
