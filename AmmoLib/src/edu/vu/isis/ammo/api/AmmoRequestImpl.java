// AmmoRequest.java
package edu.vu.isis.ammo.api;
/**
  See docs/java/ammo-api.nw for documentation
  
  Objects of this class communicates with the Ammo Distributor Service.
  
*/ 
import java.util.Calendar;

import javax.xml.datatype.Duration;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class AmmoRequestImpl extends AmmoRequest implements Parcelable {

	public static final Parcelable.Creator<AmmoRequestImpl> CREATOR = 
		new Parcelable.Creator<AmmoRequestImpl>() {

			@Override
			public AmmoRequestImpl createFromParcel(Parcel source) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public AmmoRequestImpl[] newArray(int size) {
				// TODO Auto-generated method stub
				return null;
			}
		
	};
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	public class Builder extends AmmoRequest.Builder {

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Event[] cancel() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IAmmoRequest directedPost(
				edu.vu.isis.ammo.api.IAmmoRequest.Recipient recipient) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder downsample(
				Downsample val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder downsample(char val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IAmmoRequest duplicate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Event[] expiration(Duration val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder filter(Filter val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Event[] getEvent() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Calendar getLastMessage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getTotalMessages() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getTransmissionRate() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getUuid() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IAmmoRequest interest() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder liveness(int val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder longevity(Duration val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder longevity(Calendar val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder maxTransmissionRate(
				int val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder name(String val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder payload(String val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IAmmoRequest post() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder priority(int val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder provider(Uri val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IAmmoRequest publish() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder query(Query val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder recipient(
				edu.vu.isis.ammo.api.IAmmoRequest.Recipient val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IAmmoRequest recover(String uuid) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IAmmoRequest replace(IAmmoRequest req) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IAmmoRequest replace(String uuid) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder reset() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void resetMetrics(int val) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public IAmmoRequest retrieve() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder scope(int val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setMetricTimespan(int val) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder start(Calendar val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IAmmoRequest subscribe() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder type(String val) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public edu.vu.isis.ammo.api.IAmmoRequest.Builder worth(int val) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
 
}

