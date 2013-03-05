package edu.vu.isis.ammo.provider;

/**
 * Request to the AmmoService.
 * <p>
 * 
 * If the payload is large it cannot be passed as a byte array,
 * in that case it can be passed as a file descriptor into shared memory.
 * <p>
 * The data store is required by "putPayload" and "getPayload".
 * The data store is not used for any of the other methods. 
 * <p>
 * see http://notjustburritos.tumblr.com/post/21442138796/an-introduction-to-android-shared-memory
 * for detail on the ParcelFileDescriptor mechanism.
 */
import android.content.ContentValues;
import edu.vu.isis.ammo.provider.AmmoSyncCallback;
    
interface AmmoSyncRequest {
  
    /**
     * get the payload by the Uri of its tuple.
     * if there are binary values return those as part of a file descriptor in key value pairs.
     */
    oneway void getContentValuesPlus(in Uri url, in AmmoSyncCallback callback);
    /**
     * put the payload by the Uri of its relation.
     * If there are content values which are too large they are placed into the values file descriptor.
     * In that case the keys and values are stored as:
     * key : a set of non-null bytes terminated by a null
     * null : a null delimiter 
     * length : a bigendian 32 bit signed integer indicating the size of the value.
     * value : the value associated with the key
     * para-checksum : split into two parts, 1 byte representing LARGE or SMALL, 3 least-significant-bytes of length.
     * 
     */
    oneway void putSmall(in Uri url, in ContentValues payload, in AmmoSyncCallback callback);
    oneway void put(in Uri url, in ContentValues payload, in ParcelFileDescriptor values, in AmmoSyncCallback callback);
     /**
     * get the contract description information.
     */
    oneway void getMeta(in Uri url, in AmmoSyncCallback callback);
     /**
     * Given a set of values appropriate for the the uri, encode them
     * returning the result with "returnPayload" in the callback.
     */
    oneway void encode(in Uri url, in String encoding, in ContentValues values, in AmmoSyncCallback callback);
     /**
     * Given a payload as bytes (array or file descriptor) decode into
     * a set of values returning the result with "returnContentValues" in the callback.
     */
    oneway void decodeSmall(in Uri url, in String encoding, in byte[] payload, in AmmoSyncCallback callback);
    oneway void decode(in Uri url, in String encoding, in ParcelFileDescriptor payload, in AmmoSyncCallback callback);
    /**
    * A combination of decode and encode.
    */
    oneway void transcodeSmall(in Uri url, in String decoding, in String encoding, in byte[] payload, in AmmoSyncCallback callback);
    oneway void transcode(in Uri url, in String decoding, in String encoding, in ParcelFileDescriptor payload, in AmmoSyncCallback callback);
}

