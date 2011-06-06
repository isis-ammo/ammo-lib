// AmmoStatus.java
package edu.vu.isis.ammo.api;
/**
  See docs/java/ammo-api.nw for documentation
*/ 
abstract public class AmmoStatus implements IAmmoStatus {

   public abstract static class  Netlink implements IAmmoStatus.Netlink {
      static public Netlink make(String name) { return null; }
   }
<<<<<<< HEAD

   public abstract static class  Gateway implements IAmmoStatus.Gateway {
      static public Gateway make(String name) { return null; }
   }

=======

   public abstract static class  Gateway implements IAmmoStatus.Gateway {
      static public Gateway make(String name) { return null; }
   }

>>>>>>> master
   public abstract static class  NetControl implements IAmmoStatus.NetControl {
      static public NetControl make(String name) { return null; }
   }
}

<<<<<<< HEAD
=======
// not implemented

>>>>>>> master
