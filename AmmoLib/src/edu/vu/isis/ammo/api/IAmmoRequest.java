// IAmmoRequest.java
package edu.vu.isis.ammo.api;
/**
  See docs/java/ammo-api.nw for documentation
*/ 
import java.util.Calendar;

import javax.xml.datatype.Duration;

import android.content.Intent;
import android.net.Uri;

public interface IAmmoRequest {
   public interface Builder {
      public IAmmoRequest duplicate();

      public Builder reset();

      public IAmmoRequest post();
      public IAmmoRequest directedPost(Recipient recipient);
      public IAmmoRequest publish();
      public IAmmoRequest subscribe();
      public IAmmoRequest retrieve();
      public IAmmoRequest interest();
      public IAmmoRequest replace(IAmmoRequest req);
      public IAmmoRequest replace(String uuid);

      public IAmmoRequest recover(String uuid);

        public Builder provider(Uri val);
        public Builder payload(String val);
        public Builder type(String val);
        public Builder name(String val);
        public Builder filter(Filter val); 
        public Builder query(Query val);
        public Builder downsample(Downsample val); 
        public Builder downsample(char val); 


        public Builder longevity(Duration val);
        public Builder longevity(Calendar val);

        public Builder priority(int val);

        public Builder worth(int val);

              
        public Builder liveness(int val);
        public Builder start(Calendar val); 
        public Builder scope(int val);
        public Builder recipient(Recipient val);
        public Builder maxTransmissionRate(int val);

              

      public interface Recipient {
         public String getCallSign();
         public String[] getGroups();
         public String getName(String type); // used e.g. tigr
         public String getName(); // canonical name
      }
      public interface Filter {
         public Filter get();
      }
         
         
      public interface Query {
         public String[] getProjection();
         public String getSelection();
         public String[] getArgs();
         public String[] getGroupBy();
         public String[] getOrderBy();
      }

      public interface Downsample {
         public int getMaxSize();
         public double getFraction();
      }

      public enum Place { QUEUE , DISTRIBUTE, DELIVER, COMPLETE }
         
      public enum Color { SUCCESS, FAIL,  UNKNOWN, REJECTED };
         
      public interface Event {
         public Place getPlace();
         public Event setPlace(Place val);
         
         public Color getColor();
         public Event setColor(Color val);
      }
         
      public interface Notice {
         public Event getTarget();
         public Event setTarget(Event val);
         public Event getSource();
         public Event setSource(Event val);
            
         public boolean runAction();
         public Object getAction();
      }

         public Event[] getEvent(); 

         public Event[] cancel(); 

         public Event[] expiration(Duration val);

         public String getUuid(); // 
         
         public void setMetricTimespan(int val);
         public int getTransmissionRate();
         
         public Calendar getLastMessage();
         
         public void resetMetrics(int val);
         public int getTotalMessages();

   }

}
   
