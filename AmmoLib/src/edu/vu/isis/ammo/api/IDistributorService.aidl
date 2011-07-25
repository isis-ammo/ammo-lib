package edu.vu.isis.ammo.api;

import edu.vu.isis.ammo.api.AmmoRequest;

interface IDistributorService 
{
   /**
    makeRequest returns a unique identifier which may be used by
    recoverRequest to get the request back
    */
   String makeRequest(in AmmoRequest request); 
   AmmoRequest recoverRequest(in String uuid);
   
   
}   
