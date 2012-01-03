package edu.vu.isis.ammo.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;
import edu.vu.isis.ammo.api.type.Query;
import edu.vu.isis.ammo.api.type.TimeStamp;

/**
 * see https://ammo.isis.vanderbilt.edu/redmine/boards/2/topicTypes/3
 */
public class AmmoDispatch  {
	private static final Logger logger = LoggerFactory.getLogger("ammo:api-d");


	final private AmmoRequest.Builder ab;
	final private ContentResolver resolver;

	private AmmoDispatch(Context context, BroadcastReceiver receiver) {
		if (receiver == null) {
		this.ab = AmmoRequest.newBuilder(context);
		} else {
			this.ab = AmmoRequest.newBuilder(context, receiver);
		}
		this.resolver = context.getContentResolver();
	}

	public static AmmoDispatch newInstance(Context context) {
		return new AmmoDispatch(context, null);
	}

	public static AmmoDispatch newInstance(Context context, BroadcastReceiver receiver) {
		return new AmmoDispatch(context, receiver);
	}

	
	public void releaseInstance() {
		this.ab.releaseInstance();
	}


	/**
	 * Posting with implicit lifetime and worth, delivery is ASAP.
	 *
	 * @param topicType
	 * @param serializedString
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean post(String topicType, String serializedString) throws RemoteException {
		return this.post(topicType, serializedString, null, Double.NaN);
	}

	/**
	 * Directly post a string.
	 *
	 * @param topicType
	 * @param payload
	 * @param lifetime
	 * @param worth
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean post(String topicType, String payload, Calendar lifetime, double worth) throws RemoteException {
		return post(topicType, payload, lifetime, worth, null);
	}
	public boolean post(String topicType, String payload, Calendar lifetime, double worth, PendingIntent notice) throws RemoteException {
		logger.trace("post payload {} {} {} {} {}", new Object[] {topicType, payload, lifetime, worth, notice});
		if (lifetime == null) {
			lifetime = Calendar.getInstance();
			lifetime.setTimeInMillis(System.currentTimeMillis() + (120 * 1000));
		}

		IAmmoRequest ar = this.ab
				.topic(topicType)
				.payload(payload)
				.expire(new TimeStamp(lifetime))
				//.worth(worth)
				//.notice(notice)
				.post();
		return ar != null;
	}

	/**
	 * Posting with implicit lifetime and worth, delivery is ASAP.
	 *
	 * @param topicType
	 * @param payload
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean post(String topicType, ContentValues payload) throws RemoteException {
		return this.post(topicType, payload, null, Double.NaN);
	}

	/**
	 * Directly post a set of content values.
	 *
	 * @param topicType
	 * @param payload
	 * @param lifetime
	 * @param worth
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean post(String topicType, ContentValues payload, Calendar lifetime, double worth) throws RemoteException {
		final IAmmoRequest ar = this.ab
				.topic(topicType)
				.payload(payload)
				.expire(new TimeStamp(lifetime))
				//.worth(worth)
				//.notice(notice)
				.post();
		return ar != null;
	}


	/**
	 * Posting some information to be distributed from a content provider.
	 *
	 * @param provider of the specific item to be posted, the content provider tuple identifier
	 * @param topicType the topicType type of the tuple being posted, used for distribution by the gateway
	 * @param lifetime how long before the item must be posted, the journal time
	 * @param worth how valuable is the information
	 * @return was the distribution content provider updated correctly.
	 * @throws RemoteException 
	 */
	public boolean post(Uri provider, String topicType, Calendar lifetime, double worth) throws RemoteException {
		return post(provider,topicType,lifetime,worth,null);
	}

	public boolean post(Uri provider) throws RemoteException {
		return post(provider,this.resolver.getType(provider),null,Double.NaN,null);
	}
	public boolean post(Uri provider, String topicType) throws RemoteException {
		return post(provider,topicType,null,Double.NaN,null);
	}

	public boolean post(Uri provider, Calendar lifetime, double worth) throws RemoteException {
		return post(provider,this.resolver.getType(provider),lifetime,worth,null);
	}

	public boolean post(Uri provider, String topicType, Calendar lifetime, double worth, PendingIntent notice) throws RemoteException {
		logger.trace("post provider {} {} {} {} {}", new Object[] {topicType, provider, lifetime, worth, notice});
		// check that the provider is valid
		if (provider == null) return false;

		final IAmmoRequest ar = this.ab
				.topic(topicType)
				.provider(provider)
				.expire(new TimeStamp(lifetime))
				//.worth(worth)
				//.notice(notice)
				.post();
		return ar != null;
	}


	/**
	 * Get the status of items in the retrieval table.
	 * @return a list of sample results
	 */
	public List<Map<String,String>> postal() {
		return newSampleResult();
	}

	/**
	 * @return a sample result when no real results are available.
	 */
	private List<Map<String,String>> newSampleResult() {
		List<Map<String,String>> result = new ArrayList<Map<String,String>>(2);
		Map<String,String> map_A = new HashMap<String,String>();
		map_A.put("sample", "sample value");
		result.add(map_A);
		return result;
	}

	/**
	 * Pulling with explicit lifetime, worth, and query.
	 *  
	 * @param uri
	 * @param lifetime
	 * @param worth
	 * @param query
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean pull(Uri uri, Calendar lifetime, double worth, String query) throws RemoteException {
		return this.pull(uri, this.resolver.getType(uri), lifetime, worth, query, null);
	}
	/**
	 * Sets the lifetime in seconds.
	 *
	 * @param provider
	 * @param lifetime
	 * @param worth
	 * @param query
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean pull(Uri provider, int lifetime, double worth, String query) throws RemoteException {
		return this.pull(provider, Calendar.SECOND, lifetime, worth, query);
	}

	/**
	 * Rather than providing an absolute time this method specifies a
	 * relative time (starting now) of how long the request should remain active.
	 * This also uses the topicType type from the content provider.
	 *
	 * e.g.
	 * ammo_dispatcher.pull(EventTableSchemaBase.CONTENT_URI, Calendar.MINUTE, 500, 10.0, ":event");
	 *
	 * EventTableSchemaBase.CONTENT_URI = "content://edu.vu.isis.ammo.dash.provider.incidentprovider/event"
	 *
	 * @param provider
	 * @param field {@link Calendar}
	 * @param lifetime
	 * @param worth
	 * @param query
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean pull(Uri provider, int field, int lifetime, double worth, String query) throws RemoteException {
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
	 * @param topicType
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean pull(Uri provider, String topicType) throws RemoteException {
		return this.pull(provider, topicType, Calendar.HOUR, 1, 0.0, "");
	}


	public boolean pull(Uri provider, String topicType, String query) throws RemoteException {
		return this.pull(provider, topicType, Calendar.HOUR, 1, 0.0, query);
	}

	/**
	 * Force the topicType type rather than using the resolver to acquire it.
	 *
	 * @param provider
	 * @param topicType
	 * @param field
	 * @param lifetime
	 * @param worth
	 * @param query
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean pull(Uri provider, String topicType, int field, int lifetime, double worth, String query) throws RemoteException {
		Calendar expiration = Calendar.getInstance();
		expiration.add(field, lifetime);
		return this.pull(provider, topicType, expiration, worth, query, null);
	}

	/**
	 * The base pull mechanism with all parameters.
	 * The provider is the name of the content provider which will deserialize the response.
	 * The topicType is the name in which the target service has expressed an interest.
	 *
	 * The lifetime indicates when the the pull request is no longer relevant.
	 * The worth indicates the value of the data requested.
	 * The query will typically be a json string and will contain whatever
	 * additional information the interest expressing service needs.
	 *
	 * e.g.
	 *   Suppose there is a table 'people' in content provider 'nevada' with sponsor 'edu.vu.isis.ammo.exercise'.
	 *   The client program wishes to initialize the table with a request to service.
	 *
	 *   In preparation the service has expressed interest in the type
	 *   'urn:aterrasys.com:/api/rtc/people/list/'
	 *   The call here provides this same type as the topicType to the pull request.
	 *
	 * With pull, a request for information to be placed into a content provider owned by the dispatcher.
	 * This mechanism establishes a relationship between topicType type and target provider.
	 * The target provider is not sent to the gateway, rather the content provider of the
	 * subscriber::querytable tuple is sent as the request_id.
	 * Subsequently when a pull response is received the request_id is used to
	 * look up the entry in the subscriber::queryTable which provides the target
	 * content provider uri.
	 *
	 * @param provider of the the content provider to receive the tuples
	 * @param topicType the topicType type of the tuples being requested, used for retrieval by the gateway
	 * @param lifetime how long does the subscription last?
	 * @param worth how valuable is the information
	 * @param query
	 * @return was the subscriber content provider updated correctly.
	 * @throws RemoteException 
	 */
	private boolean pull(Uri provider, String topicType, Calendar lifetime, double worth, String query, PendingIntent notice) throws RemoteException {
		logger.trace("pull {} {} {} {} {} {}", new Object[] {topicType, provider, lifetime, worth, query, notice});
		if (lifetime == null) {
			lifetime = Calendar.getInstance();
			lifetime.setTimeInMillis(System.currentTimeMillis() + (120 * 1000));
		}

		IAmmoRequest ar = this.ab
				.topic(topicType)
				.provider(provider)
				.expire(new TimeStamp(lifetime))
				.select(new Query(query))
				// .notice(notice)
				.retrieve();
		return ar != null;
	}

	/**
	 * Get the status of items in the retrieval table.
	 * @return true if successful
	 */
	public List<Map<String,String>> retrieval() {
		return newSampleResult();
	}

	/**
	 * Subscribing with explicit lifetime, worth, and filter.
	 *
	 * @param provider
	 * @param lifetime
	 * @param worth
	 * @param filter
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean subscribe(Uri provider, Calendar lifetime, double worth, String filter) throws RemoteException {
		return this.subscribe(provider, this.resolver.getType(provider), lifetime, worth, filter);
	}


	/**
	 * Sets the lifetime in seconds.
	 *
	 * @param provider
	 * @param lifetime
	 * @param worth
	 * @param filter
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean subscribe(Uri provider, int lifetime, double worth, String filter) throws RemoteException {
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
	 * @param lifetime
	 * @param worth
	 * @param filter
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean subscribe(Uri provider, int field, int lifetime, double worth, String filter) throws RemoteException {
		return this.subscribe(provider, null , field, lifetime, worth, filter);
	}
	/**
	 * Defaults for pretty much everything.
	 *
	 * @param provider
	 * @param topicType
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean subscribe(Uri provider, String topicType) throws RemoteException {
		if (topicType == null) topicType = this.resolver.getType(provider);
		return this.subscribe(provider, topicType, Calendar.HOUR, 1, 0, "");
	}
	/**
	 * Subscribe to a topicType, a request for information to be placed into a content provider.
	 * This mechanism establishes a relationship between topicType type and target provider.
	 * However the provider is not sent to the gateway, rather the content provider of the
	 * subscriber::filtertable tuple is sent as the request_id.
	 * Subsequently when a pull response is received the request_id is used to
	 * look up the entry in the subscriber::filterTable which provides the target
	 * content provider uri.
	 *
	 * @param provider of the the content provider to receive the tuples
	 * @param topicType the topicType type of the tuples being requested, used for retrieval by the gateway
	 * @param lifetime how long does the subscription last?
	 * @param worth how valuable is the information
	 * @param filter
	 * @return was the subscriber content provider updated correctly.
	 * @throws RemoteException 
	 */
	private boolean subscribe(Uri provider, String topicType, Calendar lifetime, double worth, String filter) throws RemoteException {
		return subscribe(provider, topicType, lifetime, worth, filter, null);
	}

	private boolean subscribe(Uri provider, String topicType, Calendar lifetime, double worth, String filter, PendingIntent notice) throws RemoteException {
		logger.trace("subscribe {} {} {} {} {} {}", new Object[] {topicType, provider, lifetime, worth, filter, notice});
		if (lifetime == null) {
			lifetime = Calendar.getInstance();
			lifetime.setTimeInMillis(System.currentTimeMillis() + (120 * 1000));
		}

		final IAmmoRequest ar = this.ab
				.topic(topicType)
				.provider(provider)
				.expire(new TimeStamp(lifetime))
				.filter(filter)
				// .notice(notice)
				.subscribe();
		return ar != null;
	}


	/**
	 * Force the topicType type rather than using the resolver to acquire it.
	 * @param provider
	 * @param topicType
	 * @param field
	 * @param lifetime
	 * @param worth
	 * @param filter
	 * @return true if successful
	 * @throws RemoteException 
	 */
	public boolean subscribe(Uri provider, String topicType, int field, int lifetime, double worth, String filter) throws RemoteException {
		final Calendar expiration = Calendar.getInstance();
		expiration.add(field, lifetime);
		if (topicType == null) topicType = this.resolver.getType(provider);
		final IAmmoRequest ar = this.ab
				.topic(topicType)
				.provider(provider)
				.expire(new TimeStamp(expiration))
				.filter(filter)
				// .notice(notice)
				.subscribe();
		return ar != null;
	}


	/**
	 * For getting the status of items in the subscription table.
	 * @return true if successful
	 */
	public List<Map<String,String>> subscription() {
		//        Cursor queryCursor = resolver.query(SubscriptionTableSchema.CONTENT_URI, null, null, null, null);
		//        if (queryCursor.getCount() < 1) return null;
		//
		//        List<Map<String,String>> tuples = new ArrayList<Map<String,String>>();
		//        for (boolean more = queryCursor.moveToFirst(); more; ) {
		//            Map<String,String> tuple = new HashMap<String,String>();
		//            for (int cx = 0; cx < queryCursor.getColumnCount(); ++cx) {
		//                String field = queryCursor.getColumnName(cx);
		//                tuple.put(field, queryCursor.getString(cx));
		//            }
		//            tuples.add(tuple);
		//        }
		//        return tuples;
		return newSampleResult();
	}


	public boolean unsubscribe(Uri provider) throws RemoteException {
		IAmmoRequest ar = this.ab
				.provider(provider)
				.subscribe();
		ar.cancel();
		return ar != null;
	}

	public boolean unsubscribe(Uri provider, String topicType) throws RemoteException {
		IAmmoRequest ar = this.ab
				.topic(topicType)
				.provider(provider)
				.subscribe();
		ar.cancel();
		return ar != null;
	}

	/**
	 * Publish a content provider to a topicType.
	 * This mechanism establishes a relationship between topicType type and target provider.
	 *
	 * @param provider of the the content provider to receive the tuples
	 * @param topicType the topicType type of the tuples being requested, used for retrieval by the gateway
	 * @return was the published content provider updated correctly?
	 * @throws RemoteException 
	 */
	public boolean publish(Uri provider, String topicType) throws RemoteException {
		return this.publish(provider, topicType, (Calendar) null);
	}
	public boolean publish(Uri provider, String topicType, Calendar lifetime) throws RemoteException {
		logger.trace("publish {} {} {}", new Object[] {topicType, provider, lifetime});
		if (lifetime == null) {
			lifetime = Calendar.getInstance();
			lifetime.setTimeInMillis(System.currentTimeMillis() + (120 * 1000));
		}
		IAmmoRequest ar = this.ab
				.provider(provider)
				.expire(new TimeStamp(lifetime))
				.publish();
		return ar != null;
	}

}
