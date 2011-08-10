package edu.vu.isis.ammo.api;

import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import edu.vu.isis.ammo.api.type.Anon;
import edu.vu.isis.ammo.api.type.DeliveryScope;
import edu.vu.isis.ammo.api.type.Oid;
import edu.vu.isis.ammo.api.type.Payload;
import edu.vu.isis.ammo.api.type.Provider;
import edu.vu.isis.ammo.api.type.Selection;
import edu.vu.isis.ammo.api.type.StartTime;
import edu.vu.isis.ammo.api.type.TimeInterval;
import edu.vu.isis.ammo.api.type.TimeStamp;
import edu.vu.isis.ammo.api.type.Topic;


/**
 * see docs/dev-guide/developer-guide.pdf
 */
public class AmmoRequest extends AmmoRequestBase implements IAmmoRequest, Parcelable {
    private static final Logger logger = LoggerFactory.getLogger(AmmoRequest.class);

    // **********************
    // PUBLIC PROPERTIES
    // **********************
    final public IAmmoRequest.Action action;
    final public String uuid;

    final public Provider provider;
    final public Payload payload;
    final public Topic topic;
    
    final public Integer downsample;
    final public Integer durability;

    final public Anon recipient;
    final public Anon originator;

    final public Integer priority;
    final public Integer order;
    
    final public StartTime start;
    final public StartTime expire;
    
    final public DeliveryScope scope;
    final public int throttle;
    
    final public String[] project;
    final public Selection select;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.action.toString()).append(" Request ");
        sb.append(this.uuid).append(" ");
        sb.append(this.topic).append(' ');
        return sb.toString();
    }
    

    // ****************************
    // Parcelable Support
    // ****************************

    public static final Parcelable.Creator<AmmoRequest> CREATOR = 
        new Parcelable.Creator<AmmoRequest>() {

        @Override
        public AmmoRequest createFromParcel(Parcel source) {
            return new AmmoRequest(source);
        }

        @Override
        public AmmoRequest[] newArray(int size) {
            return new AmmoRequest[size];
        }

    };

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshaled representation.
     *
     * @return a bitmask indicating the set of special object types
     *     marshaled by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *  De-Serialize the parcel
     * @param in
     */
    private AmmoRequest(Parcel in) {
        // final byte[] show = in.marshall();
        
        this.uuid = (String) in.readValue(Integer.class.getClassLoader());
        this.action = Action.getInstance(in);

        this.provider = in.readParcelable(Provider.class.getClassLoader());
        this.payload = in.readParcelable(Payload.class.getClassLoader());
        this.topic = in.readParcelable(Topic.class.getClassLoader());
        
        this.recipient = in.readParcelable(Anon.class.getClassLoader());
        this.originator = in.readParcelable(Anon.class.getClassLoader());

        this.downsample = (Integer) in.readValue(Integer.class.getClassLoader());
        this.durability = (Integer) in.readValue(Integer.class.getClassLoader());
        
        this.priority = (Integer) in.readValue(Integer.class.getClassLoader());
        this.order = (Integer) in.readValue(Integer.class.getClassLoader());
        
        this.start = in.readParcelable(StartTime.class.getClassLoader());
        this.expire = in.readParcelable(StartTime.class.getClassLoader());

        this.scope = in.readParcelable(DeliveryScope.class.getClassLoader());
        this.throttle = (Integer) in.readValue(Integer.class.getClassLoader());
        
        this.project = in.createStringArray();
        this.select = in.readParcelable(Selection.class.getClassLoader());
    }

    /**
     *  Serialize to Parcel
     *  This works in conjunction with the constructor...
     *  AmmoRequest(Parcel in)
     *  
     */
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeValue(this.uuid);
        Action.writeToParcel(dest, this.action);

        dest.writeParcelable(this.provider, flags);
        dest.writeParcelable(this.payload, flags);
        dest.writeParcelable(this.topic, flags);
        
        dest.writeParcelable(this.recipient, flags);
        dest.writeParcelable(this.originator, flags);

        dest.writeValue(this.downsample);
        dest.writeValue(this.durability);

        dest.writeValue(this.priority);
        dest.writeValue(this.order);
        
        dest.writeParcelable(this.start, flags);
        dest.writeParcelable(this.expire, flags);
       
        dest.writeParcelable(this.scope, flags);
        dest.writeValue(this.throttle);
        
        dest.writeStringArray(this.project);
        dest.writeParcelable(this.select, flags);
        
        // final byte[] show = dest.marshall();
    }
    
    
    private AmmoRequest(IAmmoRequest.Action action, Builder builder) {
        this.action = action;

        this.provider = builder.provider;
        this.payload = builder.payload;

        this.topic = builder.topic;

        
        this.downsample = builder.downsample;
        this.durability = builder.durability;

        this.recipient = builder.recipient;
        this.originator = builder.originator;

        this.priority = builder.priority;
        this.order = 0; // TODO builder.order;
        
        this.start = builder.start;
        this.expire = builder.expire;
        
        this.scope = builder.scope;
        this.throttle = builder.throttle;
        
        this.project = builder.project;
        
        this.select = builder.select;
        
        this.uuid = generateUuid();
    }
    
    private String generateUuid() {
        return "a uuid";
    }
    @Override
    public IAmmoRequest replace(IAmmoRequest req) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IAmmoRequest replace(String uuid) {
        // TODO Auto-generated method stub
        return null;
    }

    public static Builder newBuilder(Context context) {
        return new AmmoRequest.Builder(context).reset();
    }
    
//    public static Builder newBuilder(IBinder service) {
//        return new AmmoRequest.Builder(service).reset();
//    }
//  


    // **************
    // CONTROL
    // **************
    @Override
    public void metricTimespan(int val) {
        // TODO Auto-generated method stub

    }
    
    @Override
    public void resetMetrics(int val) {
        // TODO Auto-generated method stub
    }
    
    @Override
    public String uuid() {
        return this.uuid;
    }
    
    @Override
    public Event[] cancel() {
        // TODO Auto-generated method stub
        return null;
    }

    // **************
    // STATISTICS
    // **************
    @Override
    public Event[] eventSet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TimeStamp lastMessage() {
        // TODO Auto-generated method stub
        return null;
    }
    


    /**
     * The builder makes requests to the Distributor via AIDL methods.
     *
     */
    private static final Intent DISTRIBUTOR_SERVICE = new Intent(IDistributorService.class.getName());

    
    public static class Builder implements IAmmoRequest.Builder {

        private final AtomicReference<IDistributorService> distributor;
        private final Context context;
        
        private Builder(Context context) {
            this.distributor = new AtomicReference<IDistributorService>(null);
            this.context = context;
            this.prepareDistributorConnection();
        }
        
//        private Builder(IBinder service) {
//            this(service.)
//            this.distributor = new AtomicReference<IDistributorService>(null);
//            this.distributor.set((IDistributorService) service);
//            logger.info("is the service bound?");
//        }
        

        
        private boolean prepareDistributorConnection() {
            if (distributor.get() != null) return true;
            // throw new RemoteException();
            final ServiceConnection conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    logger.trace("service connected");
                    distributor.set(IDistributorService.Stub.asInterface(service));
                }
                @Override
                public void onServiceDisconnected(ComponentName name) {
                    logger.trace("service {} disconnected", name.flattenToShortString());
                    distributor.set(null);
                }
            };
            boolean isBound = context.bindService(DISTRIBUTOR_SERVICE, conn, Context.BIND_AUTO_CREATE);
            logger.info("is the service bound? {}", isBound);
            return false;
        }

        private Provider provider;
        private Payload payload;
        private Topic topic;
        
        private Integer downsample;
        private Integer durability;

        private Anon recipient;
        private Anon originator;

        private Integer priority;
        private Integer[] order;
        
        private StartTime start;
        private StartTime expire;
        
        private DeliveryScope scope;
        private int throttle;
        
        private String[] project;
        private Selection select;
        
        @SuppressWarnings("unused")
        private String uid;

        // ***************
        // ACTIONS
        // ***************

        @Override
        public IAmmoRequest directedPost(IAmmoRequest.IAnon recipient) throws RemoteException {
            if (!prepareDistributorConnection()) throw new RemoteException();
            AmmoRequest request = new AmmoRequest(IAmmoRequest.Action.DIRECTED_POSTAL, this);
            distributor.get().makeRequest(request);
            return request;
        }

        @Override
        public IAmmoRequest directedSubscribe(IAmmoRequest.IAnon originator) throws RemoteException {
            if (!prepareDistributorConnection()) throw new RemoteException();
            return new AmmoRequest(IAmmoRequest.Action.DIRECTED_SUBSCRIBE, this);
        }

        @Override
        public IAmmoRequest post() throws RemoteException {
            if (!prepareDistributorConnection()) throw new RemoteException();
            AmmoRequest request = new AmmoRequest(IAmmoRequest.Action.POSTAL, this);
            String ident = distributor.get().makeRequest(request);
            logger.info("post {}", ident);
            return request;
        }

        @Override
        public IAmmoRequest publish() throws RemoteException {
            if (!prepareDistributorConnection()) throw new RemoteException();
            AmmoRequest request = new AmmoRequest(IAmmoRequest.Action.PUBLISH, this);
            distributor.get().makeRequest(request);
            return request;
        }

        @Override
        public IAmmoRequest retrieve() throws RemoteException {
            if (!prepareDistributorConnection()) throw new RemoteException();
            AmmoRequest request = new AmmoRequest(IAmmoRequest.Action.RETRIEVAL, this);
            distributor.get().makeRequest(request);
            return request;
        }
        @Override
        public IAmmoRequest subscribe() throws RemoteException {
            if (!prepareDistributorConnection()) throw new RemoteException();
            AmmoRequest request = new AmmoRequest(IAmmoRequest.Action.SUBSCRIBE, this);
            distributor.get().makeRequest(request);
            return request;
        }

        @Override
        public IAmmoRequest duplicate() throws RemoteException {
            if (!prepareDistributorConnection()) throw new RemoteException();
            // TODO Auto-generated method stub
            return null;
        }
        
        @Override
        public IAmmoRequest getInstance(String uuid) throws RemoteException {
            if (!prepareDistributorConnection()) throw new RemoteException();
            // TODO Auto-generated method stub
            return null;
        }


        // **************
        // SET PROPERTIES
        // **************
        @Override
        public Builder reset() {
            this.downsample(DEFAULT_DOWNSAMPLE);
            this.durability(DEFAULT_DURABILITY);
            this.order(DEFAULT_ORDER);
            this.originator(DEFAULT_ORIGINATOR);
            this.payload(DEFAULT_PAYLOAD);
            this.priority(DEFAULT_PRIORITY);
            this.provider(DEFAULT_PROVIDER);
            this.recipient(DEFAULT_RECIPIENT);
            this.scope(DEFAULT_SCOPE);
            this.start(DEFAULT_START);
            this.throttle(DEFAULT_THROTTLE);
            this.topic(DEFAULT_TOPIC);
            this.uid(DEFAULT_UID);
            this.expire(DEFAULT_EXPIRE);
            this.project(DEFAULT_PROJECT);
            this.select(DEFAULT_SELECT);
            this.filter(DEFAULT_FILTER);
            return this;
        }
        
        @Override
        public Builder downsample(int maxSize) {
            this.downsample = maxSize;
            return this;
        }

        @Override
        public Builder durability(int val) {
            this.durability = val;
            return this;
        }


        @Override
        public Builder order(int val) {
            if (this.order == null) this.order = new Integer[2];
            this.order[0] = val;
            return this;
        }

        @Override
        public Builder order(String[] val) {
            // TODO this.order = val;
            return this;
        }

        @Override
        public Builder originator(IAmmoRequest.IAnon val) {
            this.originator = (Anon) val;
            return this;
        }
        
        @Override
        public Builder originator(String val) {
            this.originator =  new Anon(val);
            return this;
        }

        @Override
        public Builder payload(String val) {
            this.payload = new Payload(val);
            return this;
        }

        @Override
        public Builder payload(byte[] val) {
            this.payload = new Payload(val);
            return this;
        }

        @Override
        public Builder payload(ContentValues val) {
            this.payload = new Payload(val);
            return this;
        }
        

        @Override
        public Builder payload(AmmoValues val) {
            return this.payload(val.asContentValues());
        }


        @Override
        public Builder priority(int val) {
            this.priority = val;
            return this;
        }

        @Override
        public Builder provider(Uri val) {
            this.provider = new Provider(val);
            return this;
        }

        @Override
        public Builder recipient(IAmmoRequest.IAnon val) {
            this.recipient = (Anon) val;
            return this;
        }
        
        @Override
        public Builder recipient(String val) {
            this.recipient =  new Anon(val);
            return this;
        }

        @Override
        public Builder scope(DeliveryScope val) {
            this.scope = val;
            return this;
        }

        @Override
        public Builder start(TimeStamp val) {
            this.start = new StartTime(val);
            return this;
        }

        @Override
        public Builder start(TimeInterval val) {
            this.start = new StartTime(val);
            return this;
        }


        @Override
        public Builder throttle(int val) {
            this.throttle = val;
            return this;
        }

        @Override
        public Builder topic(String val) {
            this.topic = new Topic(val);
            return this;
        }

        @Override
        public Builder topic(Oid val) {
            this.topic = new Topic(val);
            return this;
        }

        @Override
        public Builder uid(String val) {
            this.uid = val;
            return this;
        }

        @Override
        public Builder expire(TimeInterval val) {
            this.expire = new StartTime(val);
            return this;
        }

        @Override
        public Builder expire(TimeStamp val) {
            this.expire = new StartTime(val);
            return this;
        }

        @Override
        public Builder project(String[] val) {
            this.project = val;
            return this;
        }

        @Override
        public Builder select(IAmmoRequest.Query val) {
            this.select = new Selection(val);
            return this;
        }

        @Override
        public Builder select(IAmmoRequest.Form val) {
            this.select = new Selection(val);
            return this;
        }

        @Override
        public Builder filter(String val) {
            // TODO this.filter = val;
            return this;
        }

        @Override
        public IAmmoRequest.Builder worth(int val) {
            // TODO Auto-generated method stub
            return null;
        }


    }


}
