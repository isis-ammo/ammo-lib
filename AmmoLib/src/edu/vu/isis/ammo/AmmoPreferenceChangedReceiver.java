package edu.vu.isis.ammo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AmmoPreferenceChangedReceiver extends BroadcastReceiver {

	private IAmmoPreferenceChangedListener listener;
	public AmmoPreferenceChangedReceiver(IAmmoPreferenceChangedListener listener) {
		super();
		this.listener = listener;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		listener.onAmmoPreferenceChanged(context, intent);
	}
}
