// AmmoRequest.java
package edu.vu.isis.ammo.api;
/**
  See docs/java/ammo-api.nw for documentation
*/ 
import android.app.PendingIntent;
import android.content.Intent;

public class AmmoRequest implements IAmmoRequest {
  public abstract static class Builder implements IAmmoRequest.Builder {
     static public Builder make() { return null; }
  }

  public abstract static class Event implements IAmmoRequest.Event {
     static public Color make(Place place, Color color) { return null; }
  }
     
  public abstract static class NoticePendingIntent implements IAmmoRequest.Notice {
     protected NoticePendingIntent() {}
     
     static public IAmmoRequest.Notice make(Event level, PendingIntent action) { 
        return null; 
     } 
     abstract public PendingIntent getAction();
  }

  public abstract static class NoticeRunnable implements IAmmoRequest.Notice {
     protected NoticeRunnable() {}
     
     static public IAmmoRequest.Notice make(Event level, Runnable actor) { 
        return null; 
     } 
     abstract public PendingIntent getAction();
  }

  public abstract static class NoticeIntent implements IAmmoRequest.Notice {
     protected NoticeIntent() {}

     static public Notice make(Event level, Intent intent) { 
        return null; 
     } 
     abstract public PendingIntent getAction();
  }
     
  public abstract static class Recipient implements IAmmoRequest.Recipient {
     static public Recipient make(String name) { return null; }
  }
}

