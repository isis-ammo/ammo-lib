// Ammo.java
package edu.vu.isis.ammo.api;

abstract public class Ammo implements IAmmo {

   public abstract static class  NetworkInterface implements IAmmo.NetworkInterface {
      static public NetworkInterface create(String name) { return null; }
   }

   public abstract static class  Gateway implements IAmmo.Gateway {
      static public Gateway create(String name) { return null; }
   }

   public abstract static class  NetControl implements IAmmo.NetControl {
      static public NetControl create(String name) { return null; }
   }
}
