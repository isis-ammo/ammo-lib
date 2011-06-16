// AmmoControl.java

package edu.vu.isis.ammo.api;
/**
  See docs/java/ammo-api.nw for documentation
*/ 
abstract public class AmmoControl implements IAmmoControl {

   public abstract static class  Netlink implements IAmmoControl.Netlink {
      static public Netlink make(String name) { return null; }
   }

   public abstract static class Gateway implements IAmmoControl.Gateway {
      static public Gateway make(String name) { return null; }
   }

   public abstract static class NetControl implements IAmmoControl.NetControl {
      static public NetControl make(String name) { return null; }
   }
}
