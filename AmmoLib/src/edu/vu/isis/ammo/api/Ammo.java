// Ammo.java
package edu.vu.isis.ammo.api;

abstract public class Ammo implements IAmmo {

   public abstract static class  Netlink implements IAmmo.Netlink {
      static public Netlink getInstance(String name) { return null; }
   }

   public abstract static class  Gateway implements IAmmo.Gateway {
      static public Gateway getInstance(String name) { return null; }
   }

   public abstract static class  NetControl implements IAmmo.NetControl {
      static public NetControl getInstance(String name) { return null; }
   }
}
