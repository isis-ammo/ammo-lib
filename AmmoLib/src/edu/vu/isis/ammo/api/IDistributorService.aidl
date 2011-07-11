package edu.vu.isis.ammo.api;


interface IDistributorService 
{
   AmmoRequest request(String uuid);
   String request(AmmoRequest request);
}   
