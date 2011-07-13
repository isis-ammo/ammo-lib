package edu.vu.isis.ammo.api;

import java.util.Calendar;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import edu.vu.isis.ammo.api.IAmmoRequest.Builder.DeliveryScope;

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
    final public String payload_str;
    final public byte[] payload_byte;
    final public ContentValues payload_cv;

    final public String type_str;
    final public Oid type_oid;
    
    final public int downsample;
    final public int durability;

    final public Anon recipient;
    final public Anon originator;

    final public int priority;
    final public int order;
    final public Calendar start_abs;
    final public TimeInterval start_rel;
    final public DeliveryScope scope;
    final public int throttle;

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

    public static Builder createBuilder() {
        return new AmmoRequest.Builder();
    }
    
	@Override
	public String uuid() {
		return this.uuid;
	}

    /**
     * The builder makes requests to the Distributor via AIDL methods.
     *
     */
    public static class Builder implements IAmmoRequest.Builder {

        //final private AmmoDispatcher.IService service;
        private Builder() {
        }

        private Uri provider;
        
        public enum PayloadType { NONE, STR, BYTE, CV };
        private PayloadType payload_type;
        private String payload_str;
        private byte[] payload_byte;
        private ContentValues payload_cv;

        public enum DataType { STR, OID };
        private DataType data_type;
        private String type_str;
        private Oid type_oid;
        
        private int downsample;
        private int durability;

        private Anon recipient;
        private Anon originator;

        private int priority;
        private int[] order;
        
        public enum StartType { ABS, REL };
        private StartType start_type;
        private Calendar start_abs;
        private TimeInterval start_rel;
        
        public enum ExpirationType { ABS, REL };
        private ExpirationType expiration_type;
        private Calendar expiration_abs;
        private TimeInterval expiration_rel;
        
        private DeliveryScope scope;
        private int throttle;
        
        private String[] projection;
        
        public enum SelectType { QUERY, FORM };
        private SelectType select_type;
        private IAmmoRequest.Query select_query;
        private IAmmoRequest.Form select_form;
		private String uid;

        // ***************
        // ACTIONS
        // ***************

        @Override
        public IAmmoRequest directedPost(Anon recipient) {
            return new AmmoRequest(IAmmoRequest.Action.DIRECTED_POSTAL, this);
        }

        @Override
        public IAmmoRequest directedSubscribe(Anon originator) {
            return new AmmoRequest(IAmmoRequest.Action.DIRECTED_SUBSCRIBE, this);
        }

        @Override
        public IAmmoRequest post() {
            return new AmmoRequest(IAmmoRequest.Action.POSTAL, this);
        }

        @Override
        public IAmmoRequest publish() {
            return new AmmoRequest(IAmmoRequest.Action.PUBLISH, this);
        }

        @Override
        public IAmmoRequest retrieve() {
            return new AmmoRequest(IAmmoRequest.Action.RETRIEVAL, this);
        }
        @Override
        public IAmmoRequest subscribe() {
            return new AmmoRequest(IAmmoRequest.Action.SUBSCRIBE, this);
        }

        @Override
        public IAmmoRequest duplicate() {
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public Event[] cancel() {
            // TODO Auto-generated method stub
            return null;
        }
        
        @Override
        public IAmmoRequest getInstance(String uuid) {
            // TODO Auto-generated method stub
            return null;
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

        // **************
        // STATISTICS
        // **************
        @Override
        public Event[] eventSet() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Calendar lastMessage() {
            // TODO Auto-generated method stub
            return null;
        }
        


        // **************
        // SET PROPERTIES
        // **************
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
        	this.order[0] = val;
            return this;
        }

        @Override
        public Builder order(String[] val) {
        	// TODO this.order = val;
            return this;
        }

        @Override
        public Builder originator(Anon val) {
        	this.originator = val;
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
        public Builder recipient(Anon val) {
        	this.recipient = val;
            return this;
        }


        @Override
        public Builder reset() {
            
            return this;
        }

        @Override
        public Builder scope(DeliveryScope val) {
        	this.scope = val;
            return this;
        }

        @Override
        public Builder start(Calendar val) {
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
        public Builder type(String val) {
        	this.data_type = DataType.STR;
        	this.type_str = val;
            return this;
        }

        @Override
        public Builder type(Oid val) {
        	this.data_type = DataType.OID;
        	this.type_oid = val;
            return this;
        }

        @Override
        public Builder uid(String val) {
        	this.uid = val;
            return this;
        }

        @Override
        public Builder expiration(TimeInterval val) {
        	this.expiration_type = ExpirationType.REL;
        	this.expiration_rel = val;
            return this;
        }

        @Override
        public Builder expiration(Calendar val) {
        	this.expiration_type = ExpirationType.ABS;
        	this.expiration_abs = val;
            return this;
        }

        @Override
        public Builder projection(String[] val) {
        	this.projection = val;
            return this;
        }

        @Override
        public Builder selection(IAmmoRequest.Query val) {
        	this.select_type = SelectType.QUERY;
        	this.select_query = val;
            return this;
        }

        @Override
        public Builder selection(IAmmoRequest.Form val) {
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

    public static class Query implements IAmmoRequest.Query {
        final private String selection;
        final private String[] args;

        public Query(String selection, String[] args) {
            this.selection = selection;
            this.args = args;
        }

        public Query(String selection) {
            this.selection = selection;
            this.args = null;
        }

        public String selection() {
            return this.selection;
        }

        public String[] args() {
            return this.args();
        }

        public Query args(String[] args) {
            return new Query(this.selection, args);
        }
    }

    public class Form extends HashMap<String, String> implements
        IAmmoRequest.Form {
        private static final long serialVersionUID = 4787609325728657052L;

        public Form() {
            super();
        }
    }

    /**
     * Access mode for the file. May be "r" for read-only access, "w" for
     * write-only access (erasing whatever data is currently in the file), "wa"
     * for write-only access to append to any existing data, "rw" for read and
     * write access on any existing data, and "rwt" for read and write access
     * that truncates any existing file.
     */
    private final static long MAXIMUM_FIELD_SIZE = 9046;

    /**
     * Posting information which is not persistent on the mobile device. Once
     * the item has been sent it is removed.
     */
    // posting with explicit expiration and worth
    // static private File dir = new
    // File(Environment.getExternalStorageDirectory(),"ammo_distributor_cache");

    private AmmoRequest(IAmmoRequest.Action action, Builder builder) {
        this.action = action;

        this.provider = builder.provider;
        this.payload_str = builder.payload_str;
        this.payload_byte = builder.payload_byte;
        this.payload_cv = builder.payload_cv;

        this.type_str = builder.type_str;
        this.type_oid = builder.type_oid;
        
        this.downsample = builder.downsample;
        this.durability = builder.durability;

        this.recipient = builder.recipient;
        this.originator = builder.originator;

        this.priority = builder.priority;
        this.order = 0; // TODO builder.order;
        this.start_abs = builder.start_abs;
        this.start_rel = builder.start_rel;
        this.scope = builder.scope;
        this.throttle = builder.throttle;
        
        this.uuid = generateUuid();
    }
    
    private String generateUuid() {
    	return "a uuid";
    }

    // ****************************
    // Parcelable Support
    // ****************************

    public static final Parcelable.Creator<AmmoRequest> CREATOR = new Parcelable.Creator<AmmoRequest>() {

        @Override
        public AmmoRequest createFromParcel(Parcel source) {
            return new AmmoRequest(source);
        }

        @Override
        public AmmoRequest[] newArray(int size) {
            return new AmmoRequest[size];
        }

    };

    private AmmoRequest(Parcel in) {
        this.action = IAmmoRequest.Action.values()[in.readInt()];

        this.provider = Uri.parse(in.readString());

        this.payload_str = in.readString();
        this.payload_byte = null; // in.readString();
        this.payload_cv = null; // in.readString();

        this.type_str = in.readString();
        this.type_oid = null; // in.readString();
        this.uuid = in.readString();
        this.downsample = in.readInt();
        this.durability = in.readInt();

        this.recipient = null; // in.readString();
        this.originator = null; // in.readString();

        this.priority = in.readInt();
        this.order = in.readInt();
        this.start_abs = null; // in.readInt();
        this.start_rel = null; // in.readInt();
        this.scope = null; // in.readInt();
        this.throttle = in.readInt();
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.action.ordinal());
        dest.writeString(this.provider.toString());

        dest.writeString(this.payload_str);

        dest.writeString(this.type_str);
        dest.writeString(this.uuid);

        dest.writeInt(this.downsample);
        dest.writeInt(this.durability);

        // this.recipient = in.readString();
        // this.originator = in.readString();

        dest.writeInt(this.priority);
        dest.writeInt(this.order);
        // dest.writeInt(this.start_abs = in.readInt();
        // dest.writeInt(this.start_rel = in.readInt();
        // dest.writeInt(this.scope = in.readInt();
        dest.writeInt(this.throttle);
    }

}
