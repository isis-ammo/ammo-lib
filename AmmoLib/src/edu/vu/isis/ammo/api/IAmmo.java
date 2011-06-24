// IAmmo.java
package edu.vu.isis.ammo.api;

import javax.xml.datatype.Duration;
import java.util.Calendar;

public interface IAmmo {
  
  
  

  public interface NetworkInterface {
     public NetworkInterfaceState state();

     public Gateway setMetricTimespan(Duration span);
     public int latency();
     public int throughput();
     
     public Gateway[] gatewaySet();
  }


  
    

  
  
    

   Radio Control

  public interface NetControl {
     public NetworkInterface[] networkLinks();
     public Gateway[] gateways();
     public Identity identity();
  }
}

