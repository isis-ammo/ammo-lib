package edu.vu.isis.ammo.api;

import edu.vu.isis.ammo.api.AmmoRequestImpl;

interface IAmmoDistributorService {

  // List<AmmoRequestImpl> getRequestList();
  AmmoRequestImpl subscribe(in AmmoRequestImpl request);
  
  String getOperator();
  
  void refresh();
}

