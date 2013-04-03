package edu.vu.isis.ammo.provider;

/**
 * Reply to the various ammo sync requests.
 * @see AmmoSyncRequest.aidl
 */

import android.content.ContentValues;
 
interface AmmoSyncCallback {
    oneway void returnMeta (in String contract);
    oneway void returnPayloadSmall (in byte[] payload);
    oneway void returnPayload (in ParcelFileDescriptor payload);
    oneway void returnContentValues (in ContentValues payload);
    oneway void returnFault (in String msg);
}
