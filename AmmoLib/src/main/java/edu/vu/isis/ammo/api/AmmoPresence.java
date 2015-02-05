/* Copyright (c) 2010-2015 Vanderbilt University
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package edu.vu.isis.ammo.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import edu.vu.isis.ammo.core.provider.DistributorSchema;
import edu.vu.isis.ammo.core.provider.PresenceSchema;
import edu.vu.isis.ammo.core.provider.Relations;
import edu.vu.isis.ammo.core.provider.TemporalState;

/**
 * Class encapsulates user network presence information from Ammo
 */
public class AmmoPresence {
    private static final Logger logger = LoggerFactory.getLogger("api.ammo.presence");

    /**
     * constants for presence state
     */
    public static final int PRESENT = TemporalState.PRESENT.code;
    public static final int RARE = TemporalState.RARE.code;
    public static final int MISSED = TemporalState.MISSED.code;
    public static final int LOST = TemporalState.LOST.code;
    public static final int ABSENT = TemporalState.ABSENT.code;

    /**
     * this value will be returned if an error occurs when querying for a user's
     * status
     */
    public static final int ERROR_STATUS_UNDEFINED = -1;

    private Context mContext;

    public AmmoPresence() {
        mContext = null;
    }

    private AmmoPresence(Context context) {
        mContext = context;
    }

    public static AmmoPresence newInstance(Context context) {
        return new AmmoPresence(context);
    }

    /**
     * Container for the network presence status for a single user.
     */
    static public class UserStatus {
        public UserStatus() {
        }

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
     * @return arraylist of UserStatus objects, each containing the status of a
     *         user available on the network.
     */
    public List<UserStatus> getAllAvailableUsers() {
        List<UserStatus> userList = queryForAllUsers();
        if (userList == null) {
            logger.error("null users list");
        }
        return userList;
    }

    /**
     * Determine whether a given user is currently available on the network.
     * e.g. <code>
     import edu.vu.isis.ammo.api.AmmoPresence;
     import edu.vu.isis.ammo.api.AmmoPresence.UserStatus;

     AmmoPresence p = AmmoPresence.newInstance(mContext);

     // Status of single, named user
     final int status = p.getUserPresenceStatus("bubba");
     if (status == AmmoPresence.ABSENT) {
     Log.d(TAG, "   --> user is absent");
     }
     </code>
     * 
     * @param The userid (string), e.g. 'john.doe', to query for availability.
     * @return Integer value corresponding to PRESENT, RARE, LOST, ABSENT
     */
    public int getUserPresenceStatus(String userId) {
        int status = queryUserPresence(userId);
        return status;
    }

    /**
     * Report on all observed users available on the network. e.g. <code>
     import edu.vu.isis.ammo.api.AmmoPresence;
     import edu.vu.isis.ammo.api.AmmoPresence.UserStatus;

     AmmoPresence p = AmmoPresence.newInstance(mContext);

     // List of all users whose status is known
     ArrayList<UserStatus> userStatusList = p.getAllAvailableUsers();
     for (UserStatus ustat : userStatusList) {
     logger.info("user={} status={}", ustat.userId, ustat.status);
     </code>
     * 
     * @return Integer value corresponding to PRESENT, RARE, LOST, ABSENT
     */
    private List<UserStatus> queryForAllUsers() {
        Cursor presenceCursor = null;
        ArrayList<UserStatus> list = new ArrayList<UserStatus>();
        try {
            Uri presenceUri = DistributorSchema.CONTENT_URI.get(Relations.PRESENCE);
            String[] projection = {
                    PresenceSchema.OPERATOR.field, PresenceSchema.STATE.field
            };
            String selection = null;
            String[] selectionArgs = null;
            presenceCursor = mContext.getContentResolver().query(presenceUri, projection,
                    selection, selectionArgs, null);

            if (presenceCursor == null) {
                logger.error("queryForAllUsers: null cursor");
                return null;
            }

            int count = presenceCursor.getCount();
            if (count <= 0) {
                logger.error("queryForAllUsers: no rows in cursor");
                return list;
            }

            for (int i = 0; i < count; i++) {
                presenceCursor.moveToNext();

                String userId = presenceCursor.getString(presenceCursor
                        .getColumnIndex(PresenceSchema.OPERATOR.field));
                final int status = presenceCursor.getInt(presenceCursor
                        .getColumnIndex(PresenceSchema.STATE.field));

                int decodeStatus = TemporalState.decodeState(status).code;

                logger.debug("queryForAllUsers: user={} status={}",
                        userId, decodeStatus);

                UserStatus u = new UserStatus();
                u.setUserId(userId);
                u.setStatus(decodeStatus);

                list.add(u);
            }
            return list;
        } catch (IllegalArgumentException e) {
            logger.error("Error while querying for presence", e);
            return null;
        } catch (CursorIndexOutOfBoundsException e) {
            logger.error("Cursor out of bounds", e);
            e.printStackTrace();
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("Array index out of bounds", e);
            return null;
        } finally {
            if (presenceCursor != null) {
                presenceCursor.close();
            }
        }
    }

    private int queryUserPresence(String userId) {
        Cursor presenceCursor = null;
        try {
            logger.debug("queryUserPresence: {}", userId);
            Uri presenceUri = DistributorSchema.CONTENT_URI.get(Relations.PRESENCE);
            String[] projection = {
                    PresenceSchema.STATE.field
            };
            String selection = PresenceSchema.WHERE_OPERATOR_IS;
            String[] selectionArgs = {
                    userId
            };
            presenceCursor = mContext.getContentResolver().query(presenceUri, projection,
                    selection, selectionArgs, null);

            if (presenceCursor == null) {
                logger.error("queryUserPresence: null cursor");
                return ERROR_STATUS_UNDEFINED;
            }
            if (presenceCursor.getCount() <= 0) {
                logger.info("queryUserPresence: cursor empty");
                return ERROR_STATUS_UNDEFINED;
            }
            if (!presenceCursor.moveToFirst()) {
                logger.error("queryUserPresence: cursor error (count = {})",
                        presenceCursor.getCount());
                return ERROR_STATUS_UNDEFINED;
            }

            final int status = presenceCursor.getInt(presenceCursor
                    .getColumnIndex(PresenceSchema.STATE.field));
            logger.debug("queryUserPresence: status={} ", status);

            TemporalState ts = TemporalState.decodeState(status);
            int decodeStatus = ERROR_STATUS_UNDEFINED;
            if (ts != null) {
                decodeStatus = ts.code;
            } else {
                logger.error("queryUserPresence: null TemporalState - status={} ", status);
            }

            logger.debug("queryUserPresence: status={} coded={}", status, decodeStatus);
            return decodeStatus;

        } catch (IllegalArgumentException e) {
            logger.error("Error while querying for presence", e);
            return ERROR_STATUS_UNDEFINED;
        } finally {
            if (presenceCursor != null) {
                presenceCursor.close();
            }
        }
        // Shouldn't make it to here
    }

    /**
     * This method has not yet been fully specified. The general intent is that
     * each time the state of the user changes the runnable will be invoked.
     * 
     * @param userId
     * @param runnable
     */
    /*
     * public void setOnChangeCallback(String userId, Runnable runnable) { if
     * (userId == null) { // all users } }
     */

}
