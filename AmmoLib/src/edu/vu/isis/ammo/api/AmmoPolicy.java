// AmmoPolicy.java

package edu.vu.isis.ammo.api;
/**
  See docs/java/ammo-api.nw for documentation
*/ 
abstract public class AmmoPolicy implements IAmmoPolicy {

   public abstract static class  Netlink implements IAmmoPolicy.Netlink {
      static public Netlink make(String name) { return null; }
   }

   public abstract static class Gateway implements IAmmoPolicy.Gateway {
      static public Gateway make(String name) { return null; }
   }

   public abstract static class NetControl implements IAmmoPolicy.NetControl {
      static public NetControl make(String name) { return null; }
   }
}

