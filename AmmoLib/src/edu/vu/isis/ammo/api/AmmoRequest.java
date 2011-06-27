// AmmoRequest.java
//  See docs/java/ammo-api.nw for documentation
package edu.vu.isis.ammo.api;
import android.app.PendingIntent;
import android.content.Intent;

abstract public class AmmoRequest implements IAmmoRequest {
  public abstract static class Builder implements IAmmoRequest.Builder {
     static public Builder create() { return null; }
  }
}
