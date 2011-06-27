// AmmoRequest.java
//  See docs/java/ammo-api.nw for documentation
package edu.vu.isis.ammo.api;

abstract public class AmmoRequest implements IAmmoRequest {
  public abstract static class Builder implements IAmmoRequest.Builder {
     static public Builder create() { return null; }
  }
  public static AmmoRequest getInstance(String uuid) {
	return null;
  } 
}
