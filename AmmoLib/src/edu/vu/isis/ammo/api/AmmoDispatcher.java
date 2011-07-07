package edu.vu.isis.ammo.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import edu.vu.isis.ammo.core.provider.PreferenceSchema;
import edu.vu.isis.ammo.core.provider.DistributorSchema.PublicationTableSchema;
import edu.vu.isis.ammo.core.provider.DistributorSchema.SubscriptionTableSchema;
import edu.vu.isis.ammo.core.provider.DistributorSchemaBase.PostalTableSchemaBase;
import edu.vu.isis.ammo.core.provider.DistributorSchemaBase.PublicationTableSchemaBase;
import edu.vu.isis.ammo.core.provider.DistributorSchemaBase.RetrievalTableSchemaBase;
import edu.vu.isis.ammo.core.provider.DistributorSchemaBase.SubscriptionTableSchemaBase;

/**
 * see https://ammo.isis.vanderbilt.edu/redmine/boards/2/topics/3
 * 
 * @author phreed
 * 
 */
public class AmmoDispatcher {
	String selection = PreferenceSchema.AMMO_PREF_TYPE_STRING;
	
	/**
	 * Access mode for the file. 
	 * May be "r" for read-only access, 
	 * "w" for write-only access (erasing whatever data is currently in the file), 
	 * "wa" for write-only access to append to any existing data, 
	 * "rw" for read and write access on any existing data, and 
	 * "rwt" for read and write access that truncates any existing file.
	 */
	private static AmmoDispatcher instance = null;
	private final static long MAXIMUM_FIELD_SIZE = 9046;
	/**
	 * Posting information which is not persistent on the mobile device. 
	 * Once the item has been sent it is removed.
	 */
	// posting with explicit expiration and worth
	// static private File dir = new File(Environment.getExternalStorageDirectory(),"ammo_distributor_cache");
	
	// static final private String selectUri = "\""+RetrievalTableSchema.URI+"\" = '?'";
	static final private String selectRetrievalUri = "\""+RetrievalTableSchemaBase.URI+"\" = ";
	static final private String selectSubscriptionUri = "\""+SubscriptionTableSchemaBase.URI+"\" = ";
	static final private String selectPublicationUri = "\""+PublicationTableSchemaBase.URI+"\" = ";

	public static AmmoDispatcher getInstance(Context context) {
		if (instance == null) {
			instance = new AmmoDispatcher(context, context.getContentResolver());
		}
		return instance;
	}

	final private Context context;

	final private ContentResolver resolver;
	private AmmoDispatcher(Context context, ContentResolver resolver) {
		this.context = context;
		this.resolver = resolver;
	}
	
	private byte[] serializePendingIntent(PendingIntent pi) {
		if (pi != null) return null;
	    Parcel parcel = Parcel.obtain();
		PendingIntent.writePendingIntentOrNullToParcel(pi, parcel);
		return parcel.marshall();
	}
	
	/**
	 * Posting with implicit expiration and worth, delivery is ASAP.
	 *  
	 * @param topic
	 * @param value
	 * @return
	 */
	public boolean post(String topic, String serializedString) {
		return this.post(topic, serializedString, null, Double.NaN);
	}

	/**
	 * Directly post a string.
	 * 
	 * @param topic
	 * @param value
	 * @param expiration
	 * @param worth
	 * @return
	 */
	public boolean post(String topic, String serializedString, Calendar expiration, double worth) {
		return post(topic, serializedString, expiration, worth, null);
	}
	public boolean post(String topic, String serializedString, Calendar expiration, double worth, PendingIntent notice) 
	{
//		File filename = new File(dir, Long.toHexString(System.currentTimeMillis()));
//        try {
//            // put data into file
//            FileOutputStream fileStream = new FileOutputStream(filename);
//            byte[] buffer = serializedString.getBytes();
//            fileStream.write(buffer, 0, buffer.length);
//            fileStream.close();
//        } catch (IOException ex) {
//			ex.printStackTrace();
//		}
		if (expiration == null) {
			expiration = Calendar.getInstance();
			expiration.setTimeInMillis(System.currentTimeMillis() + (120 * 1000));
		}
		
		ContentValues values = new ContentValues();
		values.put(PostalTableSchemaBase.CP_TYPE, topic);
		// values.put(PostalTableSchema.URI, provider.toString());
		values.put(PostalTableSchemaBase.DISPOSITION, PostalTableSchemaBase.DISPOSITION_PENDING);
		values.put(PostalTableSchemaBase.SERIALIZE_TYPE, PostalTableSchemaBase.SERIALIZE_TYPE_DIRECT);
		values.put(PostalTableSchemaBase.EXPIRATION, expiration.getTimeInMillis());
		// System.currentTimeMillis() + (120 * 1000));
		values.put(PostalTableSchemaBase.UNIT, 50);
		values.put(PostalTableSchemaBase.VALUE, worth);
		if (notice != null) 
		    values.put(PostalTableSchemaBase.NOTICE, serializePendingIntent(notice));
		values.put(PostalTableSchemaBase.CREATED_DATE, System.currentTimeMillis());
		
		Uri provider;
		if (serializedString.length() < MAXIMUM_FIELD_SIZE) {
			values.put(PostalTableSchemaBase.DATA, serializedString);
			provider = resolver.insert(PostalTableSchemaBase.CONTENT_URI, values);
			return true;
		}
		// the data field will be left null
		provider = resolver.insert(PostalTableSchemaBase.CONTENT_URI, values);
		try {
			OutputStream ostream = resolver.openOutputStream(provider);
			byte[] buffer = serializedString.getBytes();
			ostream.write(buffer, 0, buffer.length);
			ostream.flush();
			ostream.close();
		} catch (FileNotFoundException e) {
			Log.e("AmmoDispatcher","distributor could not open file");
			return false;
		} catch (IOException e) {
			Log.e("AmmoDispatcher","could not write to distributor");
			return false;
		}
		return (provider != null);
	}
	
	/**
	 * Posting with implicit expiration and worth, delivery is ASAP.
	 *  
	 * @param topic
	 * @param value
	 * @return
	 */
	public boolean post(String topic, ContentValues value) {
		return this.post(topic, value, null, Double.NaN);
	}

	/**
	 * Directly post a set of content values.
	 * 
	 * @param topic
	 * @param value
	 * @param expiration
	 * @param worth
	 * @return
	 */
	public boolean post(String topic, ContentValues value, Calendar expiration, double worth) 
	{
		Log.i("AmmoDispatcher", "post(" + value.toString());
		Gson gson = new Gson();
        String serializedString = gson.toJson(value);
        return post(topic, serializedString, expiration, worth);
	}

	/**
	 * Posting with implicit expiration and worth, their values are obtained from the content provider.
	 * The topic type is obtained from the content provider.
	 * 
	 * @param provider
	 * @return
	 */
	public boolean post(Uri provider) {
		return this.post(provider, this.resolver.getType(provider), null, Double.NaN);
	}

	/**
	 * /**
	 * Posting information which is persistent on the mobile device.
	 * Is the distribution policy is retained?
	 * 
	 * Posting with explicit expiration and worth
	 * 
	 * @param provider
	 * @param expiration
	 * @param worth
	 * @return
	 */
	public boolean post(Uri provider, Calendar expiration, double worth) {
		return this.post(provider, this.resolver.getType(provider), expiration, worth);
	}
	
	/**
	 * Posting some information to be distributed from a content provider.
	 * 
	 * @param provider of the specific item to be posted, the content provider tuple identifier
	 * @param topic the topic type of the tuple being posted, used for distribution by the gateway
	 * @param expiration how long before the item must be posted, the journal time
	 * @param worth how valuable is the information
	 * @return was the distribution content provider updated correctly.
	 */
	public boolean post(Uri provider, String topic, Calendar expiration, double worth) {
		return post(provider,topic,expiration,worth,null);
	}
	
	public boolean post(Uri provider, String topic) {
		return post(provider,topic,null,Double.NaN,null);
	}
	
	
	public boolean post(Uri provider, String topic, Calendar expiration, double worth, PendingIntent notice) {
		
		Log.i("AmmoDispatcher", "real post [" + provider + "]");
		// check that the provider is valid
		if (provider == null) return false;
		
		if (null == resolver.getType(PostalTableSchemaBase.CONTENT_URI)) {
			return false;
		}
		
		if (expiration == null) {
			expiration = Calendar.getInstance();
			expiration.setTimeInMillis(System.currentTimeMillis() + (120 * 1000));
		}
		
		ContentValues values = new ContentValues();
		values.put(PostalTableSchemaBase.CP_TYPE, topic);
		values.put(PostalTableSchemaBase.URI, provider.toString());
		values.put(PostalTableSchemaBase.SERIALIZE_TYPE, PostalTableSchemaBase.SERIALIZE_TYPE_INDIRECT);
		values.put(PostalTableSchemaBase.DISPOSITION, PostalTableSchemaBase.DISPOSITION_PENDING);
		values.put(PostalTableSchemaBase.EXPIRATION, expiration.getTimeInMillis());
		// System.currentTimeMillis() + (120 * 1000));
		values.put(PostalTableSchemaBase.UNIT, 50);
		values.put(PostalTableSchemaBase.VALUE, worth);
		if (notice != null) 
		    values.put(PostalTableSchemaBase.NOTICE, serializePendingIntent(notice));
		values.put(PostalTableSchemaBase.CREATED_DATE, System.currentTimeMillis());
		
		resolver.insert(PostalTableSchemaBase.CONTENT_URI, values);
		return true;
	}
	

	/**
	 * Get the status of items in the retrieval table.
	 * @return
	 */
	public List<Map<String,String>> postal() {
		Cursor queryCursor = resolver.query(PostalTableSchemaBase.CONTENT_URI, null, null, null, null);
		if (queryCursor.getCount() < 1) return null;
		
		List<Map<String,String>> tuples = new ArrayList<Map<String,String>>();
		for (boolean more = queryCursor.moveToFirst(); more; ) {
			Map<String,String> tuple = new HashMap<String,String>();	
			for (int cx = 0; cx < queryCursor.getColumnCount(); ++cx) {
				String field = queryCursor.getColumnName(cx);
				tuple.put(field, queryCursor.getString(cx));
			}
			tuples.add(tuple);
		}
		return tuples;
	}
	
	/**
	 * Pulling with explicit expiration, worth, and query.
	 *  
	 * @param provider
	 * @param expiration
	 * @param worth
	 * @param query
	 * @return
	 */
	public boolean pull(Uri provider, Calendar expiration, double worth, String query) {
		return this.pull(provider, this.resolver.getType(provider), expiration, worth, query, null);
	}
	
	/**
	 * Sets the lifetime in seconds.
	 * 
	 * @param provider
	 * @param lifetime
	 * @param worth
	 * @param query
	 * @return
	 */
	public boolean pull(Uri provider, int lifetime, double worth, String query) {
		return this.pull(provider, Calendar.SECOND, lifetime, worth, query);
	}

	/**
	 * Rather than providing an absolute time this method specifies a
	 * relative time (starting now) of how long the request should remain active.
	 * This also uses the topic type from the content provider.
	 * 
	 * e.g.
	 * ammo_dispatcher.pull(EventTableSchemaBase.CONTENT_URI, Calendar.MINUTE, 500, 10.0, ":event");
	 * 
	 * EventTableSchemaBase.CONTENT_URI = "content://edu.vu.isis.ammo.dash.provider.incidentprovider/event"
	 * 
	 * @param provider
	 * @param field {@link Calendar} 
	 * @param expiration
	 * @param worth
	 * @param query
	 * @return
	 */
	public boolean pull(Uri provider, int field, int lifetime, double worth, String query) {
		return this.pull(provider, null , field, lifetime, worth, query);
	}
	/**
	 * Sets the lifetime to one hour.
	 * This is the simplest use of the call.
	 *  e.g.
	 * ammo_dispatcher.pull(EventTableSchemaBase.CONTENT_URI, "urn:something:api/service/location/");
	 * 
	 * 
	 * @param provider
	 * @param lifetime
	 * @param worth
	 * @param query
	 * @return
	 */
	public boolean pull(Uri provider, String topic) {
		return this.pull(provider, topic, Calendar.HOUR, 1, 0.0, "");
	}
	

        public boolean pull(Uri provider, String topic, String query) {
	    return this.pull(provider, topic, Calendar.HOUR, 1, 0.0, query);
	}

	/**
	 * Force the topic type rather than using the resolver to acquire it.
	 * 
	 * @param provider
	 * @param topic
	 * @param field
	 * @param lifetime
	 * @param worth
	 * @param query
	 * @return
	 */
	public boolean pull(Uri provider, String topic, int field, int lifetime, double worth, String query) {
		Calendar expiration = Calendar.getInstance(); 
		expiration.add(field, lifetime);
		if (topic == null) topic = this.resolver.getType(provider);
		return this.pull(provider, topic, expiration, worth, query, null);
	}
	
	/**
	 * The base pull mechanism with all parameters.
	 * The provider is the name of the content provider which will deserialize the response.
	 * The topic is the name in which the target service has expressed an interest.
	 * 
	 * The expiration indicates when the the pull request is no longer relevant.
	 * The worth indicates the value of the data requested.
	 * The query will typically be a json string and will contain whatever 
	 * additional information the interest expressing service needs.
	 * 
	 * e.g. 
	 *   Suppose there is a table 'people' in content provider 'nevada' with sponsor 'com.aterrasys.nevada'.
	 *   The client program wishes to initialize the table with a request to service.
	 *   
	 *   In preparation the service has expressed interest in the type
	 *   'urn:aterrasys.com:/api/rtc/people/list/'
	 *   The call here provides this same type as the topic to the pull request.
	 *   
	 * With pull, a request for information to be placed into a content provider owned by the dispatcher.
	 * This mechanism establishes a relationship between topic type and target provider.
	 * The target provider is not sent to the gateway, rather the content provider of the 
	 * subscriber::querytable tuple is sent as the request_id.
	 * Subsequently when a pull response is received the request_id is used to
	 * look up the entry in the subscriber::queryTable which provides the target 
	 * content provider uri.
	 * 
	 * @param provider of the the content provider to receive the tuples
	 * @param topic the topic type of the tuples being requested, used for retrieval by the gateway
	 * @param expiration how long does the subscription last?
	 * @param worth how valuable is the information
	 * @param query
	 * @return was the subscriber content provider updated correctly.
	 */
	private boolean pull(Uri provider, String topic, Calendar expiration, double worth, String query, PendingIntent notice) {
	    if (expiration == null) {
			expiration = Calendar.getInstance();
			expiration.setTimeInMillis(System.currentTimeMillis() + (120 * 1000));
	    }
	    ContentValues values = new ContentValues();
	    values.put(RetrievalTableSchemaBase.MIME, topic);
	    values.put(RetrievalTableSchemaBase.URI, provider.toString());
	    values.put(RetrievalTableSchemaBase.DISPOSITION, RetrievalTableSchemaBase.DISPOSITION_PENDING);
	    values.put(RetrievalTableSchemaBase.EXPIRATION, expiration.getTimeInMillis());
		
	    values.put(RetrievalTableSchemaBase.SELECTION, query);
	    values.put(RetrievalTableSchemaBase.PROJECTION, "");
	    values.put(RetrievalTableSchemaBase.CREATED_DATE, System.currentTimeMillis());
	    if (notice != null) 
		    values.put(RetrievalTableSchemaBase.NOTICE, serializePendingIntent(notice));
		
	    String[] projection = {BaseColumns._ID};
	    String[] selectArgs = {provider.toString()};
	    // Cursor queryCursor = resolver.query(RetrievalTableSchema.CONTENT_URI, projection, selectUri, selectArgs, null);
	    Cursor queryCursor = resolver.query(RetrievalTableSchemaBase.CONTENT_URI, projection, selectRetrievalUri+"'"+provider.toString()+"'",null, null);
	    if (queryCursor == null) {
		Toast.makeText(context, "missing pull content provider", Toast.LENGTH_LONG).show();
		return false;
	    }
	    if (queryCursor.getCount() == 1) {
		Log.d("AmmoLib", "found an existing pull request in the retrieval table ... updating ...");
		for (boolean more = queryCursor.moveToFirst(); more; ) {
		    long queryId = queryCursor.getLong(queryCursor.getColumnIndex(BaseColumns._ID));
		    Uri queryUri = ContentUris.withAppendedId(RetrievalTableSchemaBase.CONTENT_URI, queryId);
		    resolver.update(queryUri, values, null, null);
		    break; // there is only one
		}
	    } else if  (queryCursor.getCount() > 1) {
			Toast.makeText(context, "corrupted subscriber content provider; removing offending tuples", Toast.LENGTH_LONG).show();
			resolver.delete(RetrievalTableSchemaBase.CONTENT_URI, selectRetrievalUri, selectArgs);
			resolver.insert(RetrievalTableSchemaBase.CONTENT_URI, values);
	    } else {
			Log.d("AmmoLib", "creating a pull request in retrieval table ... inserting ...");
			resolver.insert(RetrievalTableSchemaBase.CONTENT_URI, values);
	    }
	    return true;
	}
	
	/**
	 * Get the status of items in the retrieval table.
	 * @return
	 */
	public List<Map<String,String>> retrieval() {
		Cursor queryCursor = resolver.query(RetrievalTableSchemaBase.CONTENT_URI, null, null, null, null);
		if (queryCursor.getCount() < 1) return null;
		
		List<Map<String,String>> tuples = new ArrayList<Map<String,String>>();
		for (boolean more = queryCursor.moveToFirst(); more; ) {
			Map<String,String> tuple = new HashMap<String,String>();	
			for (int cx = 0; cx < queryCursor.getColumnCount(); ++cx) {
				String field = queryCursor.getColumnName(cx);
				tuple.put(field, queryCursor.getString(cx));
			}
			tuples.add(tuple);
		}
		return tuples;
	}
	
	/**
	 * Subscribing with explicit expiration, worth, and filter.
	 *  
	 * @param provider
	 * @param expiration
	 * @param worth
	 * @param filter
	 * @return
	 */
	public boolean subscribe(Uri provider, Calendar expiration, double worth, String filter) {
		return this.subscribe(provider, this.resolver.getType(provider), expiration, worth, filter);
	}
	

	/**
	 * Sets the lifetime in seconds.
	 * 
	 * @param provider
	 * @param lifetime
	 * @param worth
	 * @param filter
	 * @return
	 */
	public boolean subscribe(Uri provider, int lifetime, double worth, String filter) {
		return this.subscribe(provider, Calendar.SECOND, lifetime, worth, filter);
	}

	/**
	 * Rather than providing a absolute time this method specifies a
	 * relative time (starting now) of how long the request should remain active.
	 * e.g.
	 * ammo_dispatcher.subscribe(EventTableSchemaBase.CONTENT_URI, Calendar.MINUTE, 500, 10.0, ":event");
	 * 
	 * EventTableSchemaBase.CONTENT_URI = "content://edu.vu.isis.ammo.dash.provider.incidentprovider/event"
	 * 
	 * @param provider
	 * @param field {@link Calendar} 
	 * @param expiration
	 * @param worth
	 * @param filter
	 * @return
	 */
	public boolean subscribe(Uri provider, int field, int lifetime, double worth, String filter) {
		return this.subscribe(provider, null , field, lifetime, worth, filter);
	}
	/**
	 * Defaults for pretty much everything.
	 * 
	 * @param provider
	 * @param lifetime
	 * @param worth
	 * @param filter
	 * @return
	 */
	public boolean subscribe(Uri provider, String topic) {
		if (topic == null) topic = this.resolver.getType(provider);
		return this.subscribe(provider, topic, Calendar.HOUR, 1, 0, "");
	}
	/**
	 * Subscribe to a topic, a request for information to be placed into a content provider.
	 * This mechanism establishes a relationship between topic type and target provider.
	 * However the provider is not sent to the gateway, rather the content provider of the 
	 * subscriber::filtertable tuple is sent as the request_id.
	 * Subsequently when a pull response is received the request_id is used to
	 * look up the entry in the subscriber::filterTable which provides the target 
	 * content provider uri.
	 * 
	 * @param provider of the the content provider to receive the tuples
	 * @param topic the topic type of the tuples being requested, used for retrieval by the gateway
	 * @param expiration how long does the subscription last?
	 * @param worth how valuable is the information
	 * @param filter
	 * @return was the subscriber content provider updated correctly.
	 */
	private boolean subscribe(Uri provider, String topic, Calendar expiration, double worth, String filter) {
		return subscribe(provider, topic, expiration, worth, filter, null);
	}
	
	private boolean subscribe(Uri provider, String topic, Calendar expiration, double worth, String filter, PendingIntent notice) {
		if (expiration == null) {
			expiration = Calendar.getInstance();
			expiration.setTimeInMillis(System.currentTimeMillis() + (120 * 1000));
		}
		Log.d("AmmoDispatcher", "::subscribe with provider: " + provider.toString() + " topic: " + topic);
		
		ContentValues values = new ContentValues();
		values.put(SubscriptionTableSchemaBase.MIME, topic);
		values.put(SubscriptionTableSchemaBase.URI, provider.toString());
		values.put(SubscriptionTableSchemaBase.EXPIRATION, expiration.getTimeInMillis());
		
		values.put(SubscriptionTableSchemaBase.SELECTION, filter);
		if (notice != null) 
		    values.put(SubscriptionTableSchemaBase.NOTICE, serializePendingIntent(notice));
		values.put(SubscriptionTableSchemaBase.CREATED_DATE, System.currentTimeMillis());
		
		String[] projection = {BaseColumns._ID,SubscriptionTableSchemaBase.EXPIRATION};
		String[] selectArgs = {provider.toString()};
		// Cursor filterCursor = resolver.query(SubscriptionTableSchema.CONTENT_URI, projection, selectUri, selectArgs, null);

		String selection = selectSubscriptionUri + "'" + provider.toString() + "'" + " AND \"" + SubscriptionTableSchema.MIME + "\" = '" + topic + "'";

		Cursor queryCursor = resolver.query(SubscriptionTableSchema.CONTENT_URI, projection, selection, null, null);
		if (queryCursor == null) {
           Toast.makeText(context, "missing subscriber content provider", Toast.LENGTH_LONG).show();
           return false;
	    }
		if (queryCursor.getCount() == 1) {
			for (boolean more = queryCursor.moveToFirst(); more; ) {
				long queryId = queryCursor.getLong(queryCursor.getColumnIndex(SubscriptionTableSchema._ID));
				long queryExpiration = queryCursor.getLong(queryCursor.getColumnIndex(SubscriptionTableSchema.EXPIRATION));

				if (queryExpiration == 0) // if this was an expired subscription then set its DISPOSITION to PENDING
				    values.put(SubscriptionTableSchema.DISPOSITION, SubscriptionTableSchema.DISPOSITION_PENDING);

				Uri queryUri = ContentUris.withAppendedId(SubscriptionTableSchema.CONTENT_URI, queryId);
				resolver.update(queryUri, values, null, null);
				break; // there is only one
			}
		} else if  (queryCursor.getCount() > 1) {
			Toast.makeText(context, "corrupted subscriber content provider; removing offending tuples", Toast.LENGTH_LONG).show();
			resolver.delete(SubscriptionTableSchema.CONTENT_URI, selectSubscriptionUri, selectArgs);
			resolver.insert(SubscriptionTableSchema.CONTENT_URI, values);
		} else {
		    // if its a new entry set the DISPOSITION to pending - else leave it as is ...
		    values.put(SubscriptionTableSchema.DISPOSITION, SubscriptionTableSchema.DISPOSITION_PENDING);

		    Uri uri2 = resolver.insert(SubscriptionTableSchema.CONTENT_URI, values);
		    Log.i("AmmoDispatcher",uri2.toString());
		}
		return true;
	}
	
	
	/**
	 * Force the topic type rather than using the resolver to acquire it.
	 * @param provider
	 * @param topic
	 * @param field
	 * @param lifetime
	 * @param worth
	 * @param filter
	 * @return
	 */
	public boolean subscribe(Uri provider, String topic, int field, int lifetime, double worth, String filter) {
		Calendar expiration = Calendar.getInstance(); 
		expiration.add(field, lifetime);
		if (topic == null) topic = this.resolver.getType(provider);
		return this.subscribe(provider, topic, expiration, worth, filter);
	}
	

	/**
	 * For getting the status of items in the subscription table.
	 * @return
	 */
	public List<Map<String,String>> subscription() {
		Cursor queryCursor = resolver.query(SubscriptionTableSchema.CONTENT_URI, null, null, null, null);
		if (queryCursor.getCount() < 1) return null;
		
		List<Map<String,String>> tuples = new ArrayList<Map<String,String>>();
		for (boolean more = queryCursor.moveToFirst(); more; ) {
			Map<String,String> tuple = new HashMap<String,String>();	
			for (int cx = 0; cx < queryCursor.getColumnCount(); ++cx) {
				String field = queryCursor.getColumnName(cx);
				tuple.put(field, queryCursor.getString(cx));
			}
			tuples.add(tuple);
		}
		return tuples;
	}
	
	
	public boolean unsubscribe(Uri provider) {
		ContentValues values = new ContentValues();
		values.put(SubscriptionTableSchema.EXPIRATION, 0);
		String where = "\""+SubscriptionTableSchema.URI+"\" = '"+provider.toString()+"'";
		resolver.update(SubscriptionTableSchema.CONTENT_URI, values, where, null);
		return true;
	}
	
	public boolean unsubscribe(Uri provider, String topic) {
		ContentValues values = new ContentValues();
		values.put(SubscriptionTableSchema.EXPIRATION, 0);
		values.put(SubscriptionTableSchema.MIME, topic);
		String where = "\""+SubscriptionTableSchema.URI+"\" = '"+provider.toString()+"'" 
		+ " AND " + "\"" + SubscriptionTableSchema.MIME+"\" = \"" + topic + "\"";
		resolver.update(SubscriptionTableSchema.CONTENT_URI, values, where, null);
		return true;
	}
	
	/**
	 * Publish a content provider to a topic.
	 * This mechanism establishes a relationship between topic type and target provider.
	 * 
	 * @param provider of the the content provider to receive the tuples
	 * @param topic the topic type of the tuples being requested, used for retrieval by the gateway
	 * @param expiration how long does the subscription last?
	 * @return was the published content provider updated correctly?
	 */
	public boolean publish(Uri provider, String topic) {
		return this.publish(provider, topic, (Calendar) null);
	}
	public boolean publish(Uri provider, String topic, Calendar expiration) {
		if (expiration == null) {
			expiration = Calendar.getInstance();
			expiration.setTimeInMillis(System.currentTimeMillis() + (120 * 1000));
		}
		ContentValues values = new ContentValues();
		values.put(PublicationTableSchema.MIME, topic);
		values.put(PublicationTableSchema.URI, provider.toString());
		values.put(PublicationTableSchema.DISPOSITION, PublicationTableSchema.DISPOSITION_PENDING);
		values.put(PublicationTableSchema.EXPIRATION, expiration.getTimeInMillis());
		
		values.put(PublicationTableSchema.CREATED_DATE, System.currentTimeMillis());
		
		String[] projection = {PublicationTableSchema._ID};
		String[] selectArgs = {provider.toString()};
		// Cursor queryCursor = resolver.query(PublicationTableSchema.CONTENT_URI, projection, selectUri, selectArgs, null);
		Cursor queryCursor = resolver.query(PublicationTableSchema.CONTENT_URI, projection, selectPublicationUri+"'"+provider.toString()+"'",null, null);
		if (queryCursor == null) {
           Toast.makeText(context, "missing subscriber content provider", Toast.LENGTH_LONG).show();
           return false;
	    }
		if (queryCursor.getCount() == 1) {
			for (boolean more = queryCursor.moveToFirst(); more; ) {
				long queryId = queryCursor.getLong(queryCursor.getColumnIndex(PublicationTableSchema._ID));
				Uri queryUri = ContentUris.withAppendedId(PublicationTableSchema.CONTENT_URI, queryId);
				resolver.update(queryUri, values, null, null);
				break; // there is only one
			}
		} else if  (queryCursor.getCount() > 1) {
			Toast.makeText(context, "corrupted subscriber content provider; removing offending tuples", Toast.LENGTH_LONG).show();
			resolver.delete(PublicationTableSchema.CONTENT_URI, selectPublicationUri, selectArgs);
			resolver.insert(PublicationTableSchema.CONTENT_URI, values);
		} else {
		    resolver.insert(PublicationTableSchema.CONTENT_URI, values);
		}
		return true;
	}

}
