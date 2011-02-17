package edu.vu.isis.ammo.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;
import android.util.Log;

import com.google.gson.Gson;

import edu.vu.isis.ammo.core.provider.DistributorSchema.PostalTableSchema;
import edu.vu.isis.ammo.core.provider.DistributorSchema.SerializedTableSchema;
import edu.vu.isis.ammo.core.provider.DistributorSchema.RetrivalTableSchema;

import edu.vu.isis.ammo.core.provider.DistributorSchema.SubscriptionTableSchema;
import edu.vu.isis.ammo.core.provider.DistributorSchema.PublicationTableSchema;

/**
 * see https://ammo.isis.vanderbilt.edu/redmine/boards/2/topics/3
 * 
 * @author phreed
 * 
 */
public class AmmoDispatcher {
	
	/**
	 * Access mode for the file. 
	 * May be "r" for read-only access, 
	 * "w" for write-only access (erasing whatever data is currently in the file), 
	 * "wa" for write-only access to append to any existing data, 
	 * "rw" for read and write access on any existing data, and 
	 * "rwt" for read and write access that truncates any existing file.
	 */
	private static AmmoDispatcher instance = null;
	/**
	 * Posting information which is not persistent on the mobile device. 
	 * Once the item has been sent it is removed.
	 */
	// posting with explicit expiration and worth
	static private File dir = new File(Environment.getExternalStorageDirectory(),"ammo_distributor_cache");
	
	// static final private String selectUri = "\""+RetrivalTableSchema.URI+"\" = '?'";
	static final private String selectUri = "\""+RetrivalTableSchema.URI+"\" = ";

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
	
	/**
	 * Posting with implicit expiration and worth, delivery is ASAP.
	 *  
	 * @param mimeType
	 * @param value
	 * @return
	 */
	public boolean post(String mimeType, String serializedString) {
		return this.post(mimeType, serializedString, null, Double.NaN);
	}

	/**
	 * Directly post a string.
	 * 
	 * @param mimeType
	 * @param value
	 * @param expiration
	 * @param worth
	 * @return
	 */
	public boolean post(String mimeType, String serializedString, Calendar expiration, double worth) 
	{
		File filename = new File(dir, Long.toHexString(System.currentTimeMillis()));
        try {
            //get data from file
            FileOutputStream fileStream = new FileOutputStream(filename);
            byte[] buffer = serializedString.getBytes();
            fileStream.write(buffer, 0, buffer.length);
            fileStream.close();
        } catch (IOException ex) {
			ex.printStackTrace();
		}
	
		ContentValues values = new ContentValues();
		values.put(SerializedTableSchema.URI, "");
		values.put(SerializedTableSchema.MIME_TYPE, mimeType);
		try {
			values.put(SerializedTableSchema.FILE, filename.getCanonicalPath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Uri uri = resolver.insert(SerializedTableSchema.CONTENT_URI, values);
		
		post(uri, mimeType, expiration, worth);
		return true;
	}
	
	/**
	 * Posting with implicit expiration and worth, delivery is ASAP.
	 *  
	 * @param mimeType
	 * @param value
	 * @return
	 */
	public boolean post(String mimeType, ContentValues value) {
		return this.post(mimeType, value, null, Double.NaN);
	}

	/**
	 * Directly post a set of content values.
	 * 
	 * @param mimeType
	 * @param value
	 * @param expiration
	 * @param worth
	 * @return
	 */
	public boolean post(String mimeType, ContentValues value, Calendar expiration, double worth) 
	{
		Gson gson = new Gson();
        String serializedString = gson.toJson(value);
        return post(mimeType, serializedString, expiration, worth);
	}

	/**
	 * Posting with implicit expiration and worth, their values are obtained from the content provider.
	 * The mime type is obtained from the content provider.
	 * 
	 * @param uri
	 * @return
	 */
	public boolean post(Uri uri) {
		return this.post(uri, this.resolver.getType(uri), null, Double.NaN);
	}

	/**
	 * /**
	 * Posting information which is persistent on the mobile device.
	 * Is the distribution policy is retained?
	 * 
	 * Posting with explicit expiration and worth
	 * 
	 * @param uri
	 * @param expiration
	 * @param worth
	 * @return
	 */
	public boolean post(Uri uri, Calendar expiration, double worth) {
		return this.post(uri, this.resolver.getType(uri), expiration, worth);
	}
	
	/**
	 * Posting some information to be distributed from a content provider.
	 * 
	 * @param uri of the specific item to be posted, the content provider tuple identifier
	 * @param mimeType the mime type of the tuple being posted, used for distribution by the gateway
	 * @param expiration how long before the item must be posted, the journal time
	 * @param worth how valuable is the information
	 * @return was the distribution content provider updated correctly.
	 */
	public boolean post(Uri uri, String mimeType, Calendar expiration, double worth) {
		// check that the uri is valid
		if (null == resolver.getType(PostalTableSchema.CONTENT_URI)) {
			return false;
		}
		
		if (expiration == null) {
			expiration = Calendar.getInstance();
			expiration.setTimeInMillis(System.currentTimeMillis() + (120 * 1000));
		}
		
		ContentValues values = new ContentValues();
		values.put(PostalTableSchema.CP_TYPE, mimeType);
		values.put(PostalTableSchema.URI, uri.toString());
		values.put(PostalTableSchema.DISPOSITION, PostalTableSchema.DISPOSITION_PENDING);
		values.put(PostalTableSchema.EXPIRATION, expiration.getTimeInMillis());
		// System.currentTimeMillis() + (120 * 1000));
		values.put(PostalTableSchema.UNIT, 50);
		values.put(PostalTableSchema.VALUE, worth);
		values.put(PostalTableSchema.CREATED_DATE, System.currentTimeMillis());
		
		resolver.insert(PostalTableSchema.CONTENT_URI, values);
		return true;
	}
	
	/**
	 * Pulling with explicit expiration, worth, and query.
	 *  
	 * @param uri
	 * @param expiration
	 * @param worth
	 * @param query
	 * @return
	 */
	public boolean pull(Uri uri, Calendar expiration, double worth, String query) {
		return this.pull(uri, this.resolver.getType(uri), expiration, worth, query);
	}
	
	/**
	 * Sets the lifetime in seconds.
	 * 
	 * @param uri
	 * @param lifetime
	 * @param worth
	 * @param query
	 * @return
	 */
	public boolean pull(Uri uri, int lifetime, double worth, String query) {
		return this.pull(uri, Calendar.SECOND, lifetime, worth, query);
	}

	/**
	 * Rather than providing an absolute time this method specifies a
	 * relative time (starting now) of how long the request should remain active.
	 * This also uses the mime type from the content provider.
	 * 
	 * e.g.
	 * ammo_dispatcher.pull(EventTableSchemaBase.CONTENT_URI, Calendar.MINUTE, 500, 10.0, ":event");
	 * 
	 * EventTableSchemaBase.CONTENT_URI = "content://edu.vu.isis.ammo.collector.provider.incidentprovider/event"
	 * 
	 * @param uri
	 * @param field {@link Calendar} 
	 * @param expiration
	 * @param worth
	 * @param query
	 * @return
	 */
	public boolean pull(Uri uri, int field, int lifetime, double worth, String query) {
		return this.pull(uri, null , field, lifetime, worth, query);
	}
	/**
	 * Sets the lifetime to one hour.
	 * This is the simplest use of the call.
	 *  e.g.
	 * ammo_dispatcher.pull(EventTableSchemaBase.CONTENT_URI, "urn:something:api/service/location/");
	 * 
	 * 
	 * @param uri
	 * @param lifetime
	 * @param worth
	 * @param query
	 * @return
	 */
	public boolean pull(Uri uri, String mime) {
		return this.pull(uri, mime, Calendar.HOUR, 1, 0.0, "");
	}
	
	/**
	 * Force the mime type rather than using the resolver to acquire it.
	 * 
	 * @param uri
	 * @param mime
	 * @param field
	 * @param lifetime
	 * @param worth
	 * @param query
	 * @return
	 */
	public boolean pull(Uri uri, String mime, int field, int lifetime, double worth, String query) {
		Calendar expiration = Calendar.getInstance(); 
		expiration.add(field, lifetime);
		if (mime == null) mime = this.resolver.getType(uri);
		return this.pull(uri, mime, expiration, worth, query);
	}
	
	/**
	 * The base pull mechanism with all parameters.
	 * The uri is the name of the content provider which will deserialize the response.
	 * The mimeType is the name in which the target service has expressed an interest.
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
	 *   The call here provides this same type as the mimeType to the pull request.
	 *   
	 * With pull, a request for information to be placed into a content provider owned by the dispatcher.
	 * This mechanism establishes a relationship between mime type and target uri.
	 * The target uri is not sent to the gateway, rather the content uri of the 
	 * subscriber::querytable tuple is sent as the request_id.
	 * Subsequently when a pull response is received the request_id is used to
	 * look up the entry in the subscriber::queryTable which provides the target 
	 * content provider uri.
	 * 
	 * @param uri of the the content provider to receive the tuples
	 * @param mimeType the mime type of the tuples being requested, used for retrieval by the gateway
	 * @param expiration how long does the subscription last?
	 * @param worth how valuable is the information
	 * @param query
	 * @return was the subscriber content provider updated correctly.
	 */
	private boolean pull(Uri uri, String mimeType, Calendar expiration, double worth, String query) {
	    if (expiration == null) {
			expiration = Calendar.getInstance();
			expiration.setTimeInMillis(System.currentTimeMillis() + (120 * 1000));
	    }
	    ContentValues values = new ContentValues();
	    values.put(RetrivalTableSchema.MIME, mimeType);
	    values.put(RetrivalTableSchema.URI, uri.toString());
	    values.put(RetrivalTableSchema.DISPOSITION, RetrivalTableSchema.DISPOSITION_PENDING);
	    values.put(RetrivalTableSchema.EXPIRATION, expiration.getTimeInMillis());
		
	    values.put(RetrivalTableSchema.SELECTION, query);
	    values.put(RetrivalTableSchema.PROJECTION, "");
	    values.put(RetrivalTableSchema.CREATED_DATE, System.currentTimeMillis());
		
	    String[] projection = {RetrivalTableSchema._ID};
	    String[] selectArgs = {uri.toString()};
	    // Cursor queryCursor = resolver.query(RetrivalTableSchema.CONTENT_URI, projection, selectUri, selectArgs, null);
	    Cursor queryCursor = resolver.query(RetrivalTableSchema.CONTENT_URI, projection, selectUri+"'"+uri.toString()+"'",null, null);
	    if (queryCursor == null) {
		Toast.makeText(context, "missing pull content provider", Toast.LENGTH_LONG).show();
		return false;
	    }
	    if (queryCursor.getCount() == 1) {
		Log.d("AmmoLib", "found an existing pull request in the retrival table ... updating ...");
		for (boolean more = queryCursor.moveToFirst(); more; ) {
		    long queryId = queryCursor.getLong(queryCursor.getColumnIndex(RetrivalTableSchema._ID));
		    Uri queryUri = ContentUris.withAppendedId(RetrivalTableSchema.CONTENT_URI, queryId);
		    resolver.update(queryUri, values, null, null);
		    break; // there is only one
		}
	    } else if  (queryCursor.getCount() > 1) {
			Toast.makeText(context, "corrupted subscriber content provider; removing offending tuples", Toast.LENGTH_LONG).show();
			resolver.delete(RetrivalTableSchema.CONTENT_URI, selectUri, selectArgs);
			resolver.insert(RetrivalTableSchema.CONTENT_URI, values);
	    } else {
			Log.d("AmmoLib", "creating a pull request in retrival table ... updating ...");
			resolver.insert(RetrivalTableSchema.CONTENT_URI, values);
	    }
	    return true;
	}
	
	
	
	
	/**
	 * Subscribing with explicit expiration, worth, and filter.
	 *  
	 * @param uri
	 * @param expiration
	 * @param worth
	 * @param filter
	 * @return
	 */
	public boolean subscribe(Uri uri, Calendar expiration, double worth, String filter) {
		return this.subscribe(uri, this.resolver.getType(uri), expiration, worth, filter);
	}
	

	/**
	 * Sets the lifetime in seconds.
	 * 
	 * @param uri
	 * @param lifetime
	 * @param worth
	 * @param filter
	 * @return
	 */
	public boolean subscribe(Uri uri, int lifetime, double worth, String filter) {
		return this.subscribe(uri, Calendar.SECOND, lifetime, worth, filter);
	}

	/**
	 * Rather than providing a absolute time this method specifies a
	 * relative time (starting now) of how long the request should remain active.
	 * e.g.
	 * ammo_dispatcher.subscribe(EventTableSchemaBase.CONTENT_URI, Calendar.MINUTE, 500, 10.0, ":event");
	 * 
	 * EventTableSchemaBase.CONTENT_URI = "content://edu.vu.isis.ammo.collector.provider.incidentprovider/event"
	 * 
	 * @param uri
	 * @param field {@link Calendar} 
	 * @param expiration
	 * @param worth
	 * @param filter
	 * @return
	 */
	public boolean subscribe(Uri uri, int field, int lifetime, double worth, String filter) {
		return this.subscribe(uri, null , field, lifetime, worth, filter);
	}
	/**
	 * Defaults for pretty much everything.
	 * 
	 * @param uri
	 * @param lifetime
	 * @param worth
	 * @param filter
	 * @return
	 */
	public boolean subscribe(Uri uri, String mime) {
		if (mime == null) mime = this.resolver.getType(uri);
		return this.subscribe(uri, mime, Calendar.HOUR, 1, 0, "");
	}
	/**
	 * Subscribe to a topic, a request for information to be placed into a content provider.
	 * This mechanism establishes a relationship between mime type and target uri.
	 * However the uri is not sent to the gateway, rather the content uri of the 
	 * subscriber::filtertable tuple is sent as the request_id.
	 * Subsequently when a pull response is received the request_id is used to
	 * look up the entry in the subscriber::filterTable which provides the target 
	 * content provider uri.
	 * 
	 * @param uri of the the content provider to receive the tuples
	 * @param mimeType the mime type of the tuples being requested, used for retrieval by the gateway
	 * @param expiration how long does the subscription last?
	 * @param worth how valuable is the information
	 * @param filter
	 * @return was the subscriber content provider updated correctly.
	 */
	private boolean subscribe(Uri uri, String mimeType, Calendar expiration, double worth, String filter) {
		if (expiration == null) {
			expiration = Calendar.getInstance();
			expiration.setTimeInMillis(System.currentTimeMillis() + (120 * 1000));
		}
		ContentValues values = new ContentValues();
		values.put(SubscriptionTableSchema.MIME, mimeType);
		values.put(SubscriptionTableSchema.URI, uri.toString());
		values.put(SubscriptionTableSchema.DISPOSITION, SubscriptionTableSchema.DISPOSITION_PENDING);
		values.put(SubscriptionTableSchema.EXPIRATION, expiration.getTimeInMillis());
		
		values.put(SubscriptionTableSchema.SELECTION, filter);
		values.put(SubscriptionTableSchema.CREATED_DATE, System.currentTimeMillis());
		
		String[] projection = {SubscriptionTableSchema._ID};
		String[] selectArgs = {uri.toString()};
		// Cursor filterCursor = resolver.query(SubscriptionTableSchema.CONTENT_URI, projection, selectUri, selectArgs, null);
		Cursor queryCursor = resolver.query(SubscriptionTableSchema.CONTENT_URI, projection, selectUri+"'"+uri.toString()+"'",null, null);
		if (queryCursor == null) {
           Toast.makeText(context, "missing subscriber content provider", Toast.LENGTH_LONG).show();
           return false;
	    }
		if (queryCursor.getCount() == 1) {
			for (boolean more = queryCursor.moveToFirst(); more; ) {
				long queryId = queryCursor.getLong(queryCursor.getColumnIndex(SubscriptionTableSchema._ID));
				Uri queryUri = ContentUris.withAppendedId(SubscriptionTableSchema.CONTENT_URI, queryId);
				resolver.update(queryUri, values, null, null);
				break; // there is only one
			}
		} else if  (queryCursor.getCount() > 1) {
			Toast.makeText(context, "corrupted subscriber content provider; removing offending tuples", Toast.LENGTH_LONG).show();
			resolver.delete(SubscriptionTableSchema.CONTENT_URI, selectUri, selectArgs);
			resolver.insert(SubscriptionTableSchema.CONTENT_URI, values);
		} else {
		    resolver.insert(SubscriptionTableSchema.CONTENT_URI, values);
		}
		return true;
	}
	
	/**
	 * Force the mime type rather than using the resolver to acquire it.
	 * @param uri
	 * @param mime
	 * @param field
	 * @param lifetime
	 * @param worth
	 * @param filter
	 * @return
	 */
	public boolean subscribe(Uri uri, String mime, int field, int lifetime, double worth, String filter) {
		Calendar expiration = Calendar.getInstance(); 
		expiration.add(field, lifetime);
		if (mime == null) mime = this.resolver.getType(uri);
		return this.subscribe(uri, mime, expiration, worth, filter);
	}
	
	public boolean unsubscribe(Uri uri) {
		ContentValues values = new ContentValues();
		values.put(SubscriptionTableSchema.EXPIRATION, 0);
		String where = "\""+SubscriptionTableSchema.URI+"\" = '"+uri.toString()+"'";
		resolver.update(SubscriptionTableSchema.CONTENT_URI, values, where, null);
		return true;
	}
	
	/**
	 * Subscribe to a topic, a request for information to be placed into a content provider.
	 * This mechanism establishes a relationship between mime type and target uri.
	 * However the uri is not sent to the gateway, rather the content uri of the 
	 * subscriber::filtertable tuple is sent as the request_id.
	 * Subsequently when a pull response is received the request_id is used to
	 * look up the entry in the subscriber::filterTable which provides the target 
	 * content provider uri.
	 * 
	 * @param uri of the the content provider to receive the tuples
	 * @param mimeType the mime type of the tuples being requested, used for retrieval by the gateway
	 * @param expiration how long does the subscription last?
	 * @return was the subscriber content provider updated correctly.
	 */
	private boolean publish(Uri uri, String mimeType, Calendar expiration) {
		if (expiration == null) {
			expiration = Calendar.getInstance();
			expiration.setTimeInMillis(System.currentTimeMillis() + (120 * 1000));
		}
		ContentValues values = new ContentValues();
		values.put(PublicationTableSchema.MIME, mimeType);
		values.put(PublicationTableSchema.URI, uri.toString());
		values.put(PublicationTableSchema.DISPOSITION, PublicationTableSchema.DISPOSITION_PENDING);
		values.put(PublicationTableSchema.EXPIRATION, expiration.getTimeInMillis());
		
		values.put(PublicationTableSchema.CREATED_DATE, System.currentTimeMillis());
		
		String[] projection = {PublicationTableSchema._ID};
		String[] selectArgs = {uri.toString()};
		// Cursor queryCursor = resolver.query(PublicationTableSchema.CONTENT_URI, projection, selectUri, selectArgs, null);
		Cursor queryCursor = resolver.query(PublicationTableSchema.CONTENT_URI, projection, selectUri+"'"+uri.toString()+"'",null, null);
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
			resolver.delete(PublicationTableSchema.CONTENT_URI, selectUri, selectArgs);
			resolver.insert(PublicationTableSchema.CONTENT_URI, values);
		} else {
		    resolver.insert(PublicationTableSchema.CONTENT_URI, values);
		}
		return true;
	}
	
	
}
