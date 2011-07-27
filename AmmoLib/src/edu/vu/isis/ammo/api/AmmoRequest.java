package edu.vu.isis.ammo.api;

import java.util.HashMap;
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
import edu.vu.isis.ammo.api.IAmmoRequest.Builder.DeliveryScope;
import edu.vu.isis.ammo.api.IDistributorService;


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

    final public Uri provider;
    
    private Builder.PayloadType payload_type;
    final public String payload_str;
    final public byte[] payload_byte;
    final public ContentValues payload_cv;

    private Builder.TopicEncoding topic_type;
    final public String topic_str;
    final public Oid topic_oid;
    
    final public int downsample;
    final public int durability;

    final public Anon recipient;
    final public Anon originator;

    final public int priority;
    final public int order;
    
    final public Builder.StartType start_type;
    final public TimeStamp start_abs;
    final public TimeInterval start_rel;
    
    private Builder.ExpireType expire_type;
    private TimeStamp expire_abs;
    private TimeInterval expire_rel;
    
    final public DeliveryScope scope;
    final public int throttle;
    
    final public String[] project;
    
    final public Builder.SelectType select_type;
    final public IAmmoRequest.Query select_query;
    final public IAmmoRequest.Form select_form;
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(this.action.toString()).append(" Request ");
    	sb.append(this.uuid).append(" ");
    	switch(this.topic_type) {
    	case STR: sb.append("topic ").append(this.topic_str); break;
    	case OID: sb.append("topic ").append(this.topic_oid); break;
    	}
    	return sb.toString();
    }
    

    private AmmoRequest(IAmmoRequest.Action action, Builder builder) {
        this.action = action;

        this.provider = builder.provider;
        
        this.payload_type = builder.payload_type;
        switch (this.payload_type) {
        case CV:
            this.payload_str = null;
            this.payload_byte = null;
            this.payload_cv = builder.payload_cv;
            break;
        case BYTE:
            this.payload_str = null;
            this.payload_byte = builder.payload_byte;
            this.payload_cv = null;
            break;
        case STR:
            this.payload_str = builder.payload_str;
            this.payload_byte = null;
            this.payload_cv = null;
            break;
        default:
            this.payload_str = null;
            this.payload_byte = null;
            this.payload_cv = null;
        }

        this.topic_type = builder.topic_enc;
        switch (this.topic_type) {
        case STR:
            this.topic_str = builder.topic_str;
            this.topic_oid = null;
            break;
        case OID:
            this.topic_str = null;
            this.topic_oid = builder.topic_oid;
            break;
        default:
            this.topic_str = null;
            this.topic_oid = null;
        }
        
        this.downsample = builder.downsample;
        this.durability = builder.durability;

        this.recipient = builder.recipient;
        this.originator = builder.originator;

        this.priority = builder.priority;
        this.order = 0; // TODO builder.order;
        
        this.start_type = builder.start_type;
        switch (this.start_type) {
        case ABS:
            this.start_abs = builder.start_abs;
            this.start_rel = null;
            break;
        case REL:
            this.start_abs = null;
            this.start_rel = builder.start_rel;
            break;
        default:
            this.start_rel = null;
            this.start_abs = null;
        }
        
        this.expire_type = builder.expire_type;
        switch (this.expire_type) {
        case ABS:
            this.expire_abs = builder.expire_abs;
            this.expire_rel = null;
            break;
        case REL:
            this.expire_abs = null;
            this.expire_rel = builder.expire_rel;
            break;
        default:
            this.expire_abs = null;
            this.expire_rel = null;
        }
        
        this.scope = builder.scope;
        this.throttle = builder.throttle;
        
        this.project = builder.project;
        
        this.select_type = builder.select_type;
        switch (this.select_type) {
        case QUERY:
            this.select_query = builder.select_query;
            this.select_form = null;
            break;
        case FORM:
            this.select_query = null;
            this.select_form = builder.select_form;
            break;
        default:
            this.select_query = null;
            this.select_form = null;
        }
        
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
    
    public static Builder newBuilder(IBinder service) {
        return new AmmoRequest.Builder(service).reset();
    }
  


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
        
        private Builder(Context context) {
            this.distributor = new AtomicReference<IDistributorService>(null);
            
            final ServiceConnection conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    logger.trace("service connected");
                    distributor.set((IDistributorService) service);
                }
                @Override
                public void onServiceDisconnected(ComponentName name) {
                    logger.trace("service disconnected");
                    distributor.set(null);
                }
            };
            boolean isBound = context.bindService(DISTRIBUTOR_SERVICE, conn, Context.BIND_AUTO_CREATE);
            logger.info("is the service bound? {}", isBound);
        }
        
        private Builder(IBinder service) {
            this.distributor = new AtomicReference<IDistributorService>(null);
            this.distributor.set((IDistributorService) service);
            logger.info("is the service bound?");
        }


        private Uri provider;
        
        private PayloadType payload_type;
        private String payload_str;
        private byte[] payload_byte;
        private ContentValues payload_cv;

        private TopicEncoding topic_enc;
        private String topic_str;
        private Oid topic_oid;
        
        private int downsample;
        private int durability;

        private Anon recipient;
        private Anon originator;

        private int priority;
        private int[] order;
        
        private StartType start_type;
        private TimeStamp start_abs;
        private TimeInterval start_rel;
        
        private ExpireType expire_type;
        private TimeStamp expire_abs;
        private TimeInterval expire_rel;
        
        private DeliveryScope scope;
        private int throttle;
        
        private String[] project;
        
        private SelectType select_type;
        private IAmmoRequest.Query select_query;
        private IAmmoRequest.Form select_form;
        
        private String uid;

        // ***************
        // ACTIONS
        // ***************

        @Override
        public IAmmoRequest directedPost(IAmmoRequest.Anon recipient) throws RemoteException {
            AmmoRequest request = new AmmoRequest(IAmmoRequest.Action.DIRECTED_POSTAL, this);
            distributor.get().makeRequest(request);
            return request;
        }

        @Override
        public IAmmoRequest directedSubscribe(IAmmoRequest.Anon originator) throws RemoteException {
            return new AmmoRequest(IAmmoRequest.Action.DIRECTED_SUBSCRIBE, this);
        }

        @Override
        public IAmmoRequest post() throws RemoteException {
            AmmoRequest request = new AmmoRequest(IAmmoRequest.Action.POSTAL, this);
            if (distributor.get() == null)
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    // TODO Auto-generated catch block
                    logger.info("post failed {}", ex.getStackTrace());
                }
            String ident = distributor.get().makeRequest(request);
            logger.info("post {}", ident);
            return request;
        }

        @Override
        public IAmmoRequest publish() throws RemoteException {
            AmmoRequest request = new AmmoRequest(IAmmoRequest.Action.PUBLISH, this);
            distributor.get().makeRequest(request);
            return request;
        }

        @Override
        public IAmmoRequest retrieve() throws RemoteException {
            AmmoRequest request = new AmmoRequest(IAmmoRequest.Action.RETRIEVAL, this);
            distributor.get().makeRequest(request);
            return request;
        }
        @Override
        public IAmmoRequest subscribe() throws RemoteException {
            AmmoRequest request = new AmmoRequest(IAmmoRequest.Action.SUBSCRIBE, this);
            distributor.get().makeRequest(request);
            return request;
        }

        @Override
        public IAmmoRequest duplicate() {
            // TODO Auto-generated method stub
            return null;
        }
        
        @Override
        public IAmmoRequest getInstance(String uuid) {
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
            if (this.order == null) this.order = new int[2];
            this.order[0] = val;
            return this;
        }

        @Override
        public Builder order(String[] val) {
            // TODO this.order = val;
            return this;
        }

        @Override
        public Builder originator(IAmmoRequest.Anon val) {
            this.originator = (Anon) val;
            return this;
        }

        @Override
        public Builder payload(String val) {
            this.payload_type = PayloadType.STR;
            this.payload_str = val;
            return this;
        }

        @Override
        public Builder payload(byte[] val) {
            this.payload_type = PayloadType.BYTE;
            this.payload_byte = val;
            return this;
        }

        @Override
        public Builder payload(ContentValues val) {
            this.payload_type = PayloadType.CV;
            this.payload_cv = val;
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
            this.provider = val;
            return this;
        }

        @Override
        public Builder recipient(IAmmoRequest.Anon val) {
            this.recipient = (Anon) val;
            return this;
        }

        @Override
        public Builder scope(DeliveryScope val) {
            this.scope = val;
            return this;
        }

        @Override
        public Builder start(TimeStamp val) {
            this.start_type = StartType.ABS;
            this.start_abs = val;
            return this;
        }

        @Override
        public Builder start(TimeInterval val) {
            this.start_type = StartType.REL;
            this.start_rel = val;
            return this;
        }


        @Override
        public Builder throttle(int val) {
            this.throttle = val;
            return this;
        }

        @Override
        public Builder topic(String val) {
            this.topic_enc = TopicEncoding.STR;
            this.topic_str = val;
            return this;
        }

        @Override
        public Builder topic(Oid val) {
            this.topic_enc = TopicEncoding.OID;
            this.topic_oid = val;
            return this;
        }

        @Override
        public Builder uid(String val) {
            this.uid = val;
            return this;
        }

        @Override
        public Builder expire(TimeInterval val) {
            this.expire_type = ExpireType.REL;
            this.expire_rel = val;
            return this;
        }

        @Override
        public Builder expire(TimeStamp val) {
            this.expire_type = ExpireType.ABS;
            this.expire_abs = val;
            return this;
        }

        @Override
        public Builder project(String[] val) {
            this.project = val;
            return this;
        }

        @Override
        public Builder select(IAmmoRequest.Query val) {
            this.select_type = SelectType.QUERY;
            this.select_query = val;
            return this;
        }

        @Override
        public Builder select(IAmmoRequest.Form val) {
            this.select_type = SelectType.FORM;
            this.select_form = val;
            return this;
        }

        @Override
        public Builder filter(String val) {
            // TODO this.filter = val;
            return this;
        }


    }

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Query> QUERY_CREATOR = 
        new Parcelable.Creator<Query>() {

        @Override
        public Query createFromParcel(Parcel source) {
            return new Query(source);
        }

        @Override
        public Query[] newArray(int size) {
            return new Query[size];
        }

    };
    public static class Query implements IAmmoRequest.Query, Parcelable {
        final private String select;
        final private String[] args;

        public Query(String select, String[] args) {
            this.select = select;
            this.args = args;
        }

        public Query(String select) {
            this.select = select;
            this.args = null;
        }

        public String select() {
            return this.select;
        }

        public String[] args() {
            return this.args();
        }

        public Query args(String[] args) {
            return new Query(this.select, args);
        }
        
        // Parcelable Support
        
        private Query(Parcel in) {
            this.select = in.readString();
            this.args = in.createStringArray();
        }
        
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.select);
            dest.writeStringArray(this.args);
        }
    }

    public static final Parcelable.Creator<Form> FORM_CREATOR = 
        new Parcelable.Creator<Form>() {

        @Override
        public Form createFromParcel(Parcel source) {
            return new Form(source);
        }

        @Override
        public Form[] newArray(int size) {
            return new Form[size];
        }

    };
    public static class Form extends HashMap<String, String> 
    implements IAmmoRequest.Form, Parcelable {
        private static final long serialVersionUID = 4787609325728657052L;

        public Form() {
            super();
        }
        
        // Parcelable Support
        
        private Form(Parcel in) {
            // in.readMap(this, loader)
        }
        
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeMap(this);
        }
    }
    
    public static final Parcelable.Creator<Anon> ANON_CREATOR = 
        new Parcelable.Creator<Anon>() {

        @Override
        public Anon createFromParcel(Parcel source) {
            return new Anon(source);
        }

        @Override
        public Anon[] newArray(int size) {
            return new Anon[size];
        }

    };
    public static class Anon implements IAmmoRequest.Anon, Parcelable {

        @Override
        public String name() {
            return null;
        }

        // *********************************
        // Parcelable Support
        // *********************************

        private Anon(Parcel in) {
            // TODO Auto-generated method stub
        }
        
        @Override
        public int describeContents() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            // TODO Auto-generated method stub
            
        }
    }

    /**
     * Posting information which is not persistent on the mobile device. Once
     * the item has been sent it is removed.
     */
    // posting with explicit expire and worth
    // static private File dir = new
    // File(Environment.getExternalStorageDirectory(),"ammo_distributor_cache");


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

    // De-Serialize or Un-Marshall
    private AmmoRequest(Parcel in) {
        this.uuid = in.readString();
        
        this.action = IAmmoRequest.Action.values()[in.readInt()];

        this.provider = Uri.parse(in.readString());
        
        this.payload_type = Builder.PayloadType.values()[in.readInt()];
        switch (this.payload_type) {
        case CV:
            this.payload_str = null;
            this.payload_byte = null;
            this.payload_cv = ContentValues.CREATOR.createFromParcel(in);
            break;
        case BYTE:
            this.payload_str = null;
            this.payload_byte = in.createByteArray();
            this.payload_cv = null;
            break;
        case STR:
            this.payload_str = in.readString();
            this.payload_byte = null;
            this.payload_cv = null;
            break;
        default:
            this.payload_str = null;
            this.payload_byte = null;
            this.payload_cv = null;
        }

        this.topic_type = Builder.TopicEncoding.values()[in.readInt()];
        switch (this.topic_type) {
        case STR:
            this.topic_str = in.readString();
            this.topic_oid = null;
            break;
        case OID:
            this.topic_str = null;
            this.topic_oid = Oid.CREATOR.createFromParcel(in);
            break;
        default:
            this.topic_str = null;
            this.topic_oid = null;
        }
        
        this.downsample = in.readInt();
        this.durability = in.readInt();

        this.recipient = ANON_CREATOR.createFromParcel(in);
        this.originator = ANON_CREATOR.createFromParcel(in);

        this.priority = in.readInt();
        this.order = in.readInt();
       
        this.start_type = Builder.StartType.values()[in.readInt()];
        switch (this.start_type) {
        case ABS:
            this.start_abs = TimeStamp.CREATOR.createFromParcel(in);
            this.start_rel = null;
            break;
        case REL:
            this.start_abs = null;
            this.start_rel = TimeInterval.CREATOR.createFromParcel(in);
            break;
        default:
            this.start_rel = null;
            this.start_abs = null;
        }

        this.expire_type = Builder.ExpireType.values()[in.readInt()];
        switch (this.expire_type) {
        case ABS:
            this.expire_abs = TimeStamp.CREATOR.createFromParcel(in);
            this.expire_rel = null;
            break;
        case REL:
            this.expire_abs = null;
            this.expire_rel = TimeInterval.CREATOR.createFromParcel(in);
            break;
        default:
            this.expire_abs = null;
            this.expire_rel = null;
        }
        
        this.scope = Builder.DeliveryScope.values()[in.readInt()];
        this.throttle = in.readInt();
        
        this.project = in.createStringArray();
        
        this.select_type = Builder.SelectType.values()[in.readInt()];
        switch (this.select_type) {
        case QUERY:
            this.select_query = QUERY_CREATOR.createFromParcel(in);
            this.select_form = null;
            break;
        case FORM:
            this.select_query = null;
            this.select_form = FORM_CREATOR.createFromParcel(in);
            break;
        default:
            this.select_query = null;
            this.select_form = null;
        }
    }

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

    // Serialize or Marshal
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeString(this.uuid);
        
        dest.writeInt(this.action.ordinal());

        dest.writeString(this.provider.toString());
        
        dest.writeInt(this.payload_type.ordinal());
        switch (this.payload_type) {
        case CV:
            this.payload_cv.writeToParcel(dest, flags); 
            break;
        case BYTE:
            dest.writeByteArray(this.payload_byte);
            break;
        case STR:
            dest.writeString(this.payload_str);
            break;
        }
        
        dest.writeInt(this.topic_type.ordinal());
        switch (this.topic_type) {
        case STR:
            dest.writeString(this.topic_str);
            break;
        case OID:
            this.topic_oid.writeToParcel(dest, flags);
            break;
        }
        
        dest.writeInt(this.downsample);
        dest.writeInt(this.durability);

        this.recipient.writeToParcel(dest, flags);
        this.originator.writeToParcel(dest, flags);

        dest.writeInt(this.priority);
        dest.writeInt(this.order);
        
        dest.writeInt(this.start_type.ordinal());
        switch (this.start_type) {
        case ABS:
            this.start_abs.writeToParcel(dest, flags);
            break;
        case REL:
            this.start_rel.writeToParcel(dest, flags);
            break;
        }

        dest.writeInt(this.expire_type.ordinal());
        switch (this.expire_type) {
        case ABS:
            this.expire_abs.writeToParcel(dest, flags);
            break;
        case REL:
            this.expire_rel.writeToParcel(dest, flags);
            break;
        }
        
        dest.writeInt(this.scope.ordinal());
        dest.writeInt(this.throttle);
    }

}
