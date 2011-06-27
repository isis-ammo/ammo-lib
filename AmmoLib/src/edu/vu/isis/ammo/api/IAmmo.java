// IAmmo.java
package edu.vu.isis.ammo.api;

import javax.xml.datatype.Duration;
import java.util.Calendar;

public interface IAmmo {
  public interface Identity {
    // no means from programmatic control over identity.
    public Credential[] credentialSet();
    public String[] name(); // given name, first, middle, etc
    public String surname(); // surname, a.k.a. last name
    public String userid(); // tigr identifier
    public String callsign();
    public String[] unit(); // 
    public String rank(); // 
    public Location location(); // 
    public Presence presence(); // 
  }
  public interface Location {}
  public interface Presence {}
  public interface Credential {}
  
  

  public interface Gateway {
     public GatewayState state();

     public Gateway setMetricTimespan(Duration span);
     
     /**
      * liveness in proportion of time up.
      */
     public float liveness();
     /**
      * when up how long does a message take in response 
      * to a request in milliseconds
      */
     public int latency();
     /**
      * when up how many bits per second
      */
     public float throughput();
     /**
      * how many active gateway connections, load measure
      */
     public int activeConnectionCount();
     
     public NetworkInterface[] networkLinks();
     public Calendar time();
     
     /**
      * measures of efficient and effective use
      * Compares the worth of the messages sent against the cost.
      */
     public float economy();
     public float cost();
  }
  public enum GatewayState {
     DISCONNECTED     ("Disconnected"),
     IDLE             ("Idle"),
     AUTHENTICATING   ("Authenticating"),
     FAILED           ("Failed"),
     CONNECTED        ("Connected");

     private final String text;  
     GatewayState(String text) {
        this.text = text;
     }
     public String text()   { return this.text; }
  }
     

  public enum NetworkInterfaceState {
     DISCONNECTED     ("Disconnected"),
     IDLE             ("Idle"),
     SCANNING         ("Scanning"),
     CONNECTING       ("Connecting"),
     AUTHENTICATING   ("Authenticating"),
     OBTAINING_IPADDR ("Obtaining IP Address"),
     FAILED           ("Failed"),
     CONNECTED        ("Connected");

     private final String text;  
     NetworkInterfaceState(String text) {
        this.text = text;
     }
     public String text()   { return this.text; }
  }

  
    

  
  public interface NetworkInterface {
     public NetworkInterfaceState state();

     public Gateway setMetricTimespan(Duration span);
     public int latency();
     public int throughput();
     
     public Gateway[] gatewaySet();
  }

    

   // Radio Control

  public interface NetControl {
     public NetworkInterface[] networkLinks();
     public Gateway[] gateways();
     public Identity identity();
  }
}

