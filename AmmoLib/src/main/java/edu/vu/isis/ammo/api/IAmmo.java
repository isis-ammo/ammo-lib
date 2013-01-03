/*Copyright (C) 2010-2012 Institute for Software Integrated Systems (ISIS)
This software was developed by the Institute for Software Integrated
Systems (ISIS) at Vanderbilt University, Tennessee, USA for the 
Transformative Apps program under DARPA, Contract # HR011-10-C-0175.
The United States Government has unlimited rights to this software. 
The US government has the right to use, modify, reproduce, release, 
perform, display, or disclose computer software or computer software 
documentation in whole or in part, in any manner and for any 
purpose whatsoever, and to have or authorize others to do so.
*/
// IAmmo.java
package edu.vu.isis.ammo.api;

import edu.vu.isis.ammo.api.type.TimeInterval;
import edu.vu.isis.ammo.api.type.TimeStamp;

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
    // no gateway controls
    public GatewayState state();
    public Gateway setMetricTimespan(TimeInterval span);
    public float liveness();
    public Integer latency();
    public float throughput();
    public Integer activeConnectionCount();
    public NetworkInterface[] networkLinks();
    public TimeStamp time();
    public float worth();
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

  public interface Distributor {
     public Distributor maxSize(long val);
     public Distributor queueServiceOrder(int[] val);
     public Distributor priorityQueueWeights(int[] val);
     public Distributor typePriority(String type, Integer priority);
     public Integer state(long val);
     public Integer managedDataStore(Integer val);
  }
    public interface DistributorStore {
       public Distributor remove(int rowid);
       public Distributor priority(int rowid, Integer priority);
       public Distributor queueOrder(int rowid, int order);
       public int pendingItemCount();
       public int pendingItemSize();

       public int minQueueSize();
       public int maxQueueSize();
       public int avgQueueSize();

       public int minItemSize();
       public int maxItemSize();
       public int avgItemSize();

       public int itemSubmitted();
       public int itemDispatched();
    }

  public interface NetworkService {
     public NetworkService linksToUse(String[] val);
     // public NetworkService channelToLink(Pair<String,String>[] val);
     public NetworkService distribution(Integer val);
     public int state();
     public int[] hasConnection();
     public int[] activeLinks();
     public int[] activeChannels();

     public int sentCount();
     public int receivedCount();

     public int minSentThrough();
     public int maxSentThrough();

     public int minReceivedThrough();
     public int maxReceivedThrough();

     public int minLatency();
     public int maxLatency();
  }
  public interface NetworkInterface {
     public NetworkInterfaceState state();

     public Gateway setMetricTimespan(TimeInterval span);
     public int latency();
     public int throughput();
     
     public Gateway[] gatewaySet();
  }
    public interface Channel {
       
       
    }

   // Radio Control

  public interface NetControl {
     public NetworkInterface[] networkLinks();
     public Gateway[] gateways();
     public Identity identity();
  }
}
