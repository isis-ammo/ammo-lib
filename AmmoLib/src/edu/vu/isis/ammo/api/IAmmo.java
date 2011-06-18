// IAmmo.java
package edu.vu.isis.ammo.api;

import javax.xml.datatype.Duration;
import java.util.Calendar;

public interface IAmmo {
  
  
  

  public interface NetworkInterface {
     public NetworkInterfaceState getState();

     public Gateway setMetricTimespan(Duration span);
     public int getLatency();
     public int getThroughput();
     
     public Gateway[] getGateways();
  }



  
    

  
  
    

  % Radio Control

  public interface NetControl {
     public NetworkInterface[] getNetworkLinks();
     public Gateway[] getGateways();
     public Identity getIdentity();
  }
}

