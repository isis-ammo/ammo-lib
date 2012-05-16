package edu.vu.isis.ammo.api;

interface IDistributorAdaptor 
{
   /**
    serialize a tuple from a content provider.
    */
   byte[] serialize(in String encoding, in String relationName, in String tupleId); 
    /**
    Request that a payload be deserialized into a tuple from a content provider.
    */
   String deserialize(in String encoding, in String relationName, in byte[] payload);
   
   
}   
