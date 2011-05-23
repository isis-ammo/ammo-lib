// AmmoStatus.java
package edu.vu.isis.ammo.api;
/**
  See docs/java/ammo-api.nw for documentation
*/ 
abstract public class AmmoStatus implements IAmmoStatus {

   public abstract static class  NetLink implements IAmmoStatus.NetLink {
      static public NetLink make(String name) { return null; }
   }

   public abstract static class  Gateway implements IAmmoStatus.Gateway {
      static public Gateway make(String name) { return null; }
   }

   public abstract static class  NetworkController implements IAmmoStatus.NetworkController {
      static public NetworkController make(String name) { return null; }
   }
}

// not implemented

