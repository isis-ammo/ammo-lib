package edu.vu.isis.ammo.api;

import java.util.Calendar;
import java.util.HashMap;

import javax.xml.datatype.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import edu.vu.isis.ammo.api.IAmmoRequest.Builder.DeliveryScope;
import edu.vu.isis.ammo.core.provider.PreferenceSchema;

/**
 * see https://ammo.isis.vanderbilt.edu/redmine/boards/2/topics/3
 */
public class AmmoRequest implements IAmmoRequest, Parcelable {
    private static final Logger logger = LoggerFactory.getLogger(AmmoRequest.class);

    public interface IService {
        String Stub = null;
    }

    String selection = PreferenceSchema.AMMO_PREF_TYPE_STRING;

    final private IAmmoRequest.Action action;

    final private Uri provider;
    final private String payload_str;
    final private byte[] payload_byte;
    final private ContentValues payload_cv;

    final private String type_str;
    final private Oid type_oid;
    final private String uuid;
    final private int downsample;
    final private int durability;

    final private Anon recipient;
    final private Anon originator;

    final private int priority;
    final private int order;
    final private Calendar start_abs;
    final private Duration start_rel;
    final private DeliveryScope scope;
    final private int throttle;

    // final private AmmoRequest.IService service;

    public static Builder createBuilder() {
        return new AmmoRequest.Builder();
    }

    /**
     * The builder makes requests to the Distributor via AIDL methods.
     *
     */
    public static class Builder implements IAmmoRequest.Builder {

        //final private AmmoDispatcher.IService service;
        private Builder() {
             final ServiceConnection connection = new ServiceConnection() {
                 @Override
                 public void onServiceConnected(ComponentName clazz, IBinder service) {
                     // AmmoDispatcher.this.service = AmmoDispatcher.IService.Stub.asInterface(service);
                 }

                 @Override
                 public void onServiceDisconnected(ComponentName className) {
                     logger.info("Service has unexpectedly disconnected");
                     //AmmoDispatcher.this.service = null;
                 }
             };
        }

        private Uri provider;
        private String payload_str;
        private byte[] payload_byte;
        private ContentValues payload_cv;

        private String type_str;
        private Oid type_oid;
        private String uuid;
        private int downsample;
        private int durability;

        private Anon recipient;
        private Anon originator;

        private int priority;
        private int order;
        private Calendar start_abs;
        private Duration start_rel;
        private DeliveryScope scope;
        private int throttle;

        // ***************
        // ACTIONS
        // ***************

        @Override
        public IAmmoRequest directedPost(Anon recipient) {
            return new AmmoRequest(IAmmoRequest.Action.DIRECTED_POST, this);
        }

        @Override
        public IAmmoRequest directedSubscribe(Anon originator) {
            return new AmmoRequest(IAmmoRequest.Action.DIRECTED_SUBSCRIBE, this);
        }

        @Override
        public IAmmoRequest post() {
            return new AmmoRequest(IAmmoRequest.Action.POST, this);
        }

        @Override
        public IAmmoRequest publish() {
            return new AmmoRequest(IAmmoRequest.Action.PUBLISH, this);
        }
        
        @Override
        public IAmmoRequest retrieve() {
            return new AmmoRequest(IAmmoRequest.Action.RETRIEVE, this);
        }
        @Override
        public IAmmoRequest subscribe() {
            return new AmmoRequest(IAmmoRequest.Action.SUBSCRIBE, this);
        }


        // **************
        // SET PROPERTIES
        // **************
        @Override
        public Builder downsample(int maxSize) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Event[] cancel() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public IAmmoRequest duplicate() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder durability(int val) {
            // TODO Auto-generated method stub
            return null;
        }

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

        @Override
        public void metricTimespan(int val) {
            // TODO Auto-generated method stub

        }

        @Override
        public Builder order(int val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder order(String[] val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder originator(Anon val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder payload(String val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder payload(byte[] val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder payload(ContentValues val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder priority(int val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder provider(Uri val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder recipient(Anon val) {
            // TODO Auto-generated method stub
            return null;
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

        @Override
        public Builder reset() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void resetMetrics(int val) {
            // TODO Auto-generated method stub

        }

        @Override
        public Builder scope(DeliveryScope val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder start(Calendar val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder start(TimeInterval val) {
            // TODO Auto-generated method stub
            return null;
        }

        
        @Override
        public Builder throttle(int val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder type(String val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder type(Oid val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder uid(String val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String uuid() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder expiration(TimeInterval val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder expiration(Calendar val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public IAmmoRequest getInstance(String uuid) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder projection(String[] val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder selection(IAmmoRequest.Query val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder selection(IAmmoRequest.Form val) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Builder filter(String val) {
            // TODO Auto-generated method stub
            return null;
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
        this.uuid = builder.uuid();
        this.downsample = builder.downsample;
        this.durability = builder.durability;

        this.recipient = builder.recipient;
        this.originator = builder.originator;

        this.priority = builder.priority;
        this.order = builder.order;
        this.start_abs = builder.start_abs;
        this.start_rel = builder.start_rel;
        this.scope = builder.scope;
        this.throttle = builder.throttle;
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
