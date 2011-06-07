package edu.vu.isis.ammo.api;

import edu.vu.isis.ammo.api.AmmoRequestImpl;

interface IAmmoDistributorService {

  // List<AmmoRequestImpl> getRequestList();
  AmmoRequestImpl interest(in AmmoRequestImpl request);
  AmmoRequestImpl subscribe(in AmmoRequestImpl request);
  AmmoRequestImpl pull(in AmmoRequestImpl request);
  
  AmmoRequestImpl publish(in AmmoRequestImpl request);
  AmmoRequestImpl post(in AmmoRequestImpl request);  
    
  String getOperator();
  String getDeviceId();
  
  void refresh();
}

