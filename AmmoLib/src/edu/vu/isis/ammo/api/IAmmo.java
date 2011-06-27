// IAmmo.java
package edu.vu.isis.ammo.api;

import javax.xml.datatype.Duration;

public interface IAmmo {
  
  
  public interface NetworkInterfaceState { }

  public interface NetworkInterface {
     public NetworkInterfaceState state();

     public Gateway setMetricTimespan(Duration span);
     public int latency();
     public int throughput();
     
     public Gateway[] gatewaySet();
  }


  
   public interface Gateway { } 

   public interface Identity { }
  
    

   // Radio Control

  public interface NetControl {
     public NetworkInterface[] networkLinks();
     public Gateway[] gateways();
     public Identity identity();
  }
}

