/*Copyright (C) 2010-2012 Institute for Software Integrated Systems (ISIS)
This software was developed by the Institute for Software Integrated
Systems (ISIS) at Vanderbilt University, Tennessee, USA for the 
Transformative Apps program under DARPA, Contract # HR011-10-C-0175.
The United States Government has unlimited rights to this software. 
The US government has the right to use, modify, reproduce, release, 
perform, display, or disclose computer software or computer software 
documentation in whole or in part, in any manner and for any 
purpose whatsoever, and to have or authorize others to do so.
*/
package edu.vu.isis.ammo.api;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.util.Log;
import edu.vu.isis.ammo.core.provider.DistributorSchema;
import edu.vu.isis.ammo.core.provider.PresenceSchema;
import edu.vu.isis.ammo.core.provider.Relations;
import edu.vu.isis.ammo.core.provider.TemporalState;

/**
 * Class encapsulates user network presence information from Ammo
 * 
 */
public class AmmoPresence {

    private static final String TAG = "AmmoPresence";

    /**
     * constants for presence state
     */
    public static final int PRESENT = TemporalState.PRESENT.code;
    public static final int RARE = TemporalState.RARE.code;
    public static final int MISSED = TemporalState.MISSED.code;
    public static final int LOST = TemporalState.LOST.code;
    public static final int ABSENT = TemporalState.ABSENT.code;

    @SuppressWarnings("unused")
    private ContentResolver mResolver;
    private Context mContext;

    public AmmoPresence() {
        mResolver = null;
	mContext = null;
    }


    private AmmoPresence(Context context) {
        this.mResolver = context.getContentResolver();
	mContext = context;
    }

    public static AmmoPresence newInstance(Context context) {
        return new AmmoPresence(context);
    }

    /**
     * Container for the network presence status for a single user.
     */
    static public class UserStatus {
        public UserStatus() { }

	// Username
        private String userId;
	public String getUserId() {
            return this.userId;
        }
        public UserStatus setUserId(String val) {
            this.userId = val;
            return this;
        }
	
	// Presence status
	private int status;
	public int getStatus() {
            return this.status;
        }
        public UserStatus setStatus(int val) {
            this.status = val;
            return this;
        }
    }
    
    /**
     * Get list of all currently available users.
     * 
     * @return arraylist of UserStatus objects, each containing
     * the status of a user available on the network.
     */
    public ArrayList<UserStatus> getAllAvailableUsers() {
	ArrayList<UserStatus> userList = queryForAllUsers();
	if (userList == null) {
	    Log.e(TAG, "null users list");
	}
	return userList;
    }
    
    /**
     * Determine whether a given user is currently available on the 
     * network.
     * 
     * e.g.
     * <code>
        import edu.vu.isis.ammo.api.AmmoPresence;
        import edu.vu.isis.ammo.api.AmmoPresence.UserStatus;
        
        AmmoPresence p = AmmoPresence.newInstance(mContext);
        
        // Status of single, named user
        final int status = p.getUserPresenceStatus("bubba");
        if (status == AmmoPresence.ABSENT) {
            Log.d(TAG, "   --> user is absent");
        }
        
        // List of all users whose status is known
        ArrayList<UserStatus> userStatusList = p.getAllAvailableUsers();
        for (UserStatus ustat : userStatusList) {
            logger.info("user={} status={}", ustat.userId, ustat.status);
        </code>
     * 
     * @param The userid (string), e.g. 'john.doe', to query for 
     *        availability. 
     * @return Integer value corresponding to PRESENT, RARE, LOST, ABSENT
     */
    public int getUserPresenceStatus(String userId) {
	int status = queryUserPresence(userId);
	return status;
    }

    private ArrayList<UserStatus> queryForAllUsers() {
	Cursor presenceCursor = null;
	ArrayList<UserStatus> list = new ArrayList<UserStatus>();
        try {
	    Uri presenceUri = DistributorSchema.CONTENT_URI.get(Relations.PRESENCE);
            String[] projection = {PresenceSchema.OPERATOR.field, PresenceSchema.STATE.field};
            String selection = null;
            String[] selectionArgs = null;
            presenceCursor = mContext.getContentResolver().query(presenceUri, projection,
                                                                 selection, selectionArgs, null);

	    if (presenceCursor == null) { 
		Log.e(TAG, "queryForAllUsers: null cursor") ; 
		return null; 
	    }

	    int count = presenceCursor.getCount();
	    if (count <= 0) {
		Log.e(TAG, "queryForAllUsers: no rows in cursor");
		return null;
	    }

	    for (int i = 0; i < count; i++) {
		presenceCursor.moveToNext();

		String userId = presenceCursor.getString(presenceCursor.getColumnIndex(PresenceSchema.OPERATOR.field));
		long status = presenceCursor.getLong(presenceCursor.getColumnIndex(PresenceSchema.STATE.field));

		Long l = new Long(status);
		//int decodeStatus = (int)TemporalState.decodeState(status).code;
		int decodeStatus = l.intValue();

		//if (Log.isLoggable(TAG, Log.VERBOSE)) {
		Log.d(TAG, "queryForAllUsers: user=" + userId + "   status=" + decodeStatus);
		//}

		UserStatus u = new UserStatus();
		u.setUserId(userId);
		u.setStatus(decodeStatus);

		list.add(u);
	    }
	    return list;
	} catch (IllegalArgumentException e) {
            Log.e(TAG, "Error while querying for presence: " + e.toString());
            e.printStackTrace();
            return null;
	} catch (CursorIndexOutOfBoundsException e) {
	    Log.e(TAG, "Cursor out of bounds: " + e.getMessage());
	    e.printStackTrace();
	    return null;
	} catch (ArrayIndexOutOfBoundsException e) {
	    Log.e(TAG, "Array index out of bounds: " + e.getMessage());
	    e.printStackTrace();
	    return null;
        } finally {
            if (presenceCursor != null) {
                presenceCursor.close();
            }
        }
    }

    private int queryUserPresence(String userId) {
	final int FALLBACK_RETVAL = -1;
	Cursor presenceCursor = null;
        try {
	    //if (Log.isLoggable(TAG, Log.VERBOSE)) {
	    Log.d(TAG, "queryUserPresence: " + userId);
	    //}
	    Uri presenceUri = DistributorSchema.CONTENT_URI.get(Relations.PRESENCE);
            String[] projection = {PresenceSchema.STATE.field};
            String selection = new StringBuilder().append(PresenceSchema.OPERATOR).append("=?").toString() ;
            String[] selectionArgs = {userId};
            presenceCursor = mContext.getContentResolver().query(presenceUri, projection,
                                                                 selection, selectionArgs, null);

	    if (presenceCursor == null) { 
		Log.e(TAG, "queryUserPresence: null cursor") ; 
		return FALLBACK_RETVAL; 
	    }
            if (!presenceCursor.moveToFirst()) { 
		Log.e(TAG, "queryUserPresence: cursor error");
		return FALLBACK_RETVAL; 
	    }

	    long status = presenceCursor.getLong(presenceCursor.getColumnIndex(PresenceSchema.STATE.field));
	    Long l = new Long(status);
	    //int decodeStatus = (int)TemporalState.decodeState(status).code;
	    int decodeStatus = l.intValue();
	    //if (Log.isLoggable(TAG, Log.VERBOSE)) {
	    Log.d(TAG, "queryUserPresence: (long) status=" + Long.toString(status));
	    Log.d(TAG, "queryUserPresence: (int)  status=" + decodeStatus);
	    //}
	    return decodeStatus;

	} catch (IllegalArgumentException e) {
            Log.e(TAG, "Error while querying for presence: " + e.toString());
            e.printStackTrace();
            return FALLBACK_RETVAL;
        } finally {
            if (presenceCursor != null) {
                presenceCursor.close();
            }
        }
	// Shouldn't make it to here
    }


    public void setOnChangeCallback(String userId) {

	if (userId == null) {
	    // all users
	}

    }

}

