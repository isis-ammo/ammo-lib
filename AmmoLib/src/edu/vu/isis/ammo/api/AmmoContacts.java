package edu.vu.isis.ammo.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import edu.vu.isis.ammo.launch.constants.Constants;


public class AmmoContacts {

    private static final String TAG = "AmmoContacts";

    private Context mContext;
    private ContentResolver mResolver;

    public void AmmoContacts() {
        mContext = null;
        mResolver = null;
    }

    public void setContext(Context m) {
        if (m != null) {
            mContext = m;
        } else {
            Log.e(TAG,"Attempt to set context with a null reference");
        }
    }

    public void setContentResolver(ContentResolver cr) {
        if (cr != null) {
            mResolver = cr;
        } else {
            Log.e(TAG,"Attempt to set content resolver with a null reference");
        }
    }

    //========================================================
    //
    // Contact class
    //
    //========================================================
    static public class Contact {
        public Contact() { }

        private String lookup;
        public String getLookup() {
            return this.lookup;
        }
        public Contact setLookup(String val) {
            this.lookup = val;
            return this;
        }

        private String name;
        public String getName() {
            return this.name;
        }
        public Contact setName(String val) {
            this.name = val;
            return this;
        }

        private String middle_initial;
        public String getMiddleName() {
            return this.middle_initial;
        }
        public Contact setMiddleName(String val) {
            this.middle_initial = val;
            return this;
        }

        private String lastname;
        public String getLastName() {
            return this.lastname;
        }
        public Contact setLastName(String val) {
            this.lastname = val;
            return this;
        }

        private String rank;
        public String getRank() {
            return this.rank;
        }
        public Contact setRank(String val) {
            this.rank = val;
            return this;
        }

        private String callsign;
        public String getCallSign() {
            return this.callsign;
        }
        public Contact setCallSign(String val) {
            this.callsign = val;
            return this;
        }

        private String branch;
        public String getBranch() {
            return this.branch;
        }
        public Contact setBranch(String val) {
            this.branch = val;
            return this;
        }

        private String unit;
        public String getUnit() {
            return this.unit;
        }
        public Contact setUnit(String val) {
            this.unit = val;
            return this;
        }


        private String unitDivision;
        public String getUnitDivision() {
            return this.unitDivision;
        }
        public Contact setUnitDivision(String val) {
            this.unitDivision = val;
            return this;
        }
        private String unitBrigade;
        public String getUnitBrigade() {
            return this.unitBrigade;
        }
        public Contact setUnitBrigade(String val) {
            this.unitBrigade = val;
            return this;
        }
        private String unitBattalion;
        public String getUnitBattalion() {
            return this.unitBattalion;
        }
        public Contact setUnitBattalion(String val) {
            this.unitBattalion = val;
            return this;
        }
        private String unitCompany;
        public String getUnitCompany() {
            return this.unitCompany;
        }
        public Contact setUnitCompany(String val) {
            this.unitCompany = val;
            return this;
        }
        private String unitPlatoon;
        public String getUnitPlatoon() {
            return this.unitPlatoon;
        }
        public Contact setUnitPlatoon(String val) {
            this.unitPlatoon = val;
            return this;
        }
        private String unitSquad;
        public String getUnitSquad() {
            return this.unitSquad;
        }
        public Contact setUnitSquad(String val) {
            this.unitSquad = val;
            return this;
        }


        private String email;
        public String getEmail() {
            return this.email;
        }
        public Contact setEmail(String val) {
            this.email = val;
            return this;
        }

        private String phone;
        public String getPhone() {
            return this.phone;
        }
        public Contact setPhone(String val) {
            this.phone = val;
            return this;
        }

        //private String tigrUid;
        private String tigruid;
        public String getTigrUid() {
            return this.tigruid;
        }
        public Contact setTigrUid(String val) {
            this.tigruid = val;
            return this;
        }

        // The following items will arrive as blobs.
        //
        // std::vector<unsigned char> photo;
        // std::vector<unsigned char> insignia;

    }

    //========================================================
    // 
    // updateContactEntry()
    // 
    //========================================================
    public Uri updateContactEntry(Contact lw) {

        Log.d(TAG,"updateContactEntry() ");
        Log.d(TAG, "Adding person: " + lw.getName() + " " + lw.getLastName() + " ... " + lw.getTigrUid() );

        ContentResolver cr = mResolver;

        /**
         *  Prepare contact creation request
         *  Note: We use RawContacts because this data must be associated with a particular account.
         *       The system will aggregate this with any other data for this contact and create a
         *       corresponding entry in the ContactsContract.Contacts provider for us.
         */
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, Constants.AMMO_ACCOUNT_TYPE)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, Constants.AMMO_DEFAULT_ACCOUNT_NAME)
                .build());

        String callsign = lw.getCallSign();
        if (callsign == null) callsign = "";
        if (callsign.length() > 0)
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_CALLSIGN)
                    .withValue(ContactsContract.Data.DATA1, callsign)
                    .build());


        String phone = lw.getPhone();
        if (phone == null) phone = "";

        String email = lw.getEmail();
        if (email == null) email = "";
        if (email.length() > 0)
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                               ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
                               ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());

        String unit = lw.getUnit();
        if (unit == null) unit = "";
        if (unit.length() > 0)
            {
                ContentProviderOperation.Builder opbld = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
                opbld.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);
                opbld.withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_UNIT_NAME);
                opbld.withValue(ContactsContract.Data.DATA1, unit);

                String division = lw.getUnitDivision();
                if (division == null) division = "";
                if (division.length() > 0)
                    {
                        opbld.withValue(ContactsContract.Data.DATA2, division);
                    }

                String brigade = lw.getUnitBrigade();
                if (brigade == null) brigade = "";
                if (brigade.length() > 0)
                    {
                        opbld.withValue(ContactsContract.Data.DATA3, brigade);
                    }

                String battalion = lw.getUnitBattalion();
                if (battalion == null) battalion = "";
                if (battalion.length() > 0)
                    {
                        opbld.withValue(ContactsContract.Data.DATA4, battalion);
                    }

                String company = lw.getUnitCompany();
                if (company == null) company = "";
                if (company.length() > 0)
                    {
                        opbld.withValue(ContactsContract.Data.DATA5, company);
                    }

                String platoon = lw.getUnitPlatoon();
                if (platoon == null) platoon = "";
                if (platoon.length() > 0)
                    {
                        opbld.withValue(ContactsContract.Data.DATA6, platoon);
                    }

                String squad = lw.getUnitSquad();
                if (squad == null) squad = "";
                if (squad.length() > 0)
                    {
                        opbld.withValue(ContactsContract.Data.DATA7, squad);
                    }
                ops.add(opbld.build());

            }

        String userId = lw.getTigrUid();
        if (userId == null) userId = "";
        if (userId.length() > 0)
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_USERID)
                    .withValue(ContactsContract.Data.DATA1, userId)
                    .build());

        String rank = lw.getRank();
        if (rank == null) rank = "";
        if (rank != null && rank.length() > 0)
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_RANK)
                    .withValue(ContactsContract.Data.DATA1, rank)
                    .build());


        ContentProviderOperation.Builder snb = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(ContactsContract.Data.MIMETYPE,
                       ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);

        String firstname = lw.getName();
        if (firstname == null) firstname = "";
        if (firstname.length() > 0)
            snb.withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, firstname);

        String lastname = lw.getLastName();
        if (lastname == null) lastname = "";
        if (lastname.length() > 0)
            snb.withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, lastname);

        String middlename = lw.getMiddleName();
        if (middlename == null) middlename = "";
        if (middlename.length() > 0)
            snb.withValue(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME, middlename);

        // Set the display name. Use, in order of preference, either the callsign or the full name.
        // (In future we should prob. change this to include rank, role, etc.)
        String displayName = "";
        if (displayName.length() < 1) {
            if (callsign != null && callsign.length() > 0) {
                displayName = callsign;
            }
        }
        if (displayName.length() < 1) {
            if (firstname.length() > 0) {
                displayName += firstname;
            }
            if (lastname != null && lastname.length() > 0) {
                displayName += (displayName.length() > 0 ? " ":"") + lastname;
            }
        }
        if (displayName.length() > 0) {
            snb.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName);
        }
        ops.add(snb.build());

        // Ask the Contact provider to create a new contact
        Log.d(TAG,"Selected account: " + "ammo" + " (" + "ammo" + ")");
        Log.d(TAG,"Creating contact: " + displayName);
        try {
            if (cr != null) {
                ContentProviderResult[] cpres = cr.applyBatch(ContactsContract.AUTHORITY, ops);
                return cpres[0].uri;
            } else {
                Log.w(TAG, "Content resolver is null -- will not add contact");
            }
        } catch (Exception ex) {
            Log.e(TAG,"Exception encoutered while inserting contact: " + ex);
        }

        return null;

    }

    //========================================================
    //
    // searchForContact()
    // 
    //========================================================
    public ArrayList<Contact> searchForContact(String searchTerm) {
	if (Log.isLoggable(TAG, Log.VERBOSE)) {
	    Log.d(TAG,"searchForContact() ");
	}

	Uri filterUri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, searchTerm);

	String contactId = "";
	String displayName = "";
	String lookupKey =  "";

	// Container to store search results
        ArrayList<Contact> results = new ArrayList<Contact>();

        // Perform a query
	String[] projection = {Contacts._ID, Contacts.DISPLAY_NAME, Contacts.LOOKUP_KEY};
        Cursor c = mResolver.query(filterUri, projection, null,null,null);
	if (c == null) {
            return null;
        }
	
	// Get data from the returned cursor
	try {
	    int count = c.getCount();
	    if (count <= 0) {
		return null;
	    }
	    
	    for (int i = 0; i < count; i++) {
		c.moveToNext();
		contactId = c.getString(0);
		displayName = c.getString(1);
		lookupKey =  c.getString(2);
		Log.d(TAG,"Found contact: [" + contactId + "] " + displayName + "  " + lookupKey);
		
		// Populate results container 
		AmmoContacts.Contact lw = new AmmoContacts.Contact();
		String[] names = displayName.split(" ");
		lw.setName(names[0]); 
		lw.setLastName(names[1]);
		lw.setLookup(lookupKey);

		// Get "other" data for this contact, i.e. with data query
		String[] dataProjection = {"mimetype","data1","data2","data3","data4"};
		ArrayList<HashMap<String, String>> extraData = getDataForContact(contactId, dataProjection);
		if (extraData != null) {
		    // If the data query failed (null), just keep what we've 
		    // got so far (name and lookup) and go on to the next row.
		    // Otherwise get data, put in container.
		    
		    Iterator<HashMap<String, String>> it = extraData.iterator();
		    while (it.hasNext()) {
			try {
			    HashMap<String, String> f = it.next();
			    if (f != null) {
				if (Log.isLoggable(TAG, Log.VERBOSE)) {
				    Log.d(TAG, " ITERATOR: " + String.valueOf(f.size()));
				    for (String d : f.keySet()) {
					Log.d(TAG, "     " + d + "= " + f.get(d));
				    }
				}
				
				if (Constants.MIME_CALLSIGN.equals(f.get("mimetype"))) {
				    lw.setCallSign(f.get("data1") );
				}
				if (Constants.MIME_RANK.equals(f.get("mimetype"))) {
				    lw.setRank(f.get("data1") );
				}
				if (Constants.MIME_UNIT_NAME.equals(f.get("mimetype"))) {
				    lw.setUnit(f.get("data1") );
				}
				if (Constants.MIME_USERID.equals(f.get("mimetype"))) {
				    lw.setTigrUid(f.get("data1") );
				}

			    }
			} catch (NoSuchElementException e) {
			    Log.e(TAG, "NoSuchElementException: " + e.getMessage());
			    e.printStackTrace();
			    continue;
			}
		    }
		}
		results.add(lw);
	    }
        } catch (IllegalArgumentException e) { 
	    Log.e(TAG, "IllegalArgumentException: " + e.getMessage());
	    e.printStackTrace();
	    return null;
	} catch (CursorIndexOutOfBoundsException e) {
	    Log.e(TAG, "Cursor out of bounds: " + e.getMessage());
	    e.printStackTrace();
	    return null;
	} catch (Throwable e) {
	    Log.e(TAG, "Exception: " + e.getMessage());
	    e.printStackTrace();
	    return null;
	} finally {
	    c.close();
        }

        // Return the container of results
        return results;
    }

    //========================================================
    // 
    // getDataForContact()
    // 
    //========================================================
    private ArrayList<HashMap<String, String>> getDataForContact(String contactId, String[] projection) {
	if (Log.isLoggable(TAG, Log.VERBOSE)) {
	    Log.d(TAG, "getDataForContact() ");
	}
	ContentResolver cr = mResolver;

	// Form the data URI for this contact
	Uri dataUri = Uri.withAppendedPath(Uri.withAppendedPath(Contacts.CONTENT_URI, contactId), "data");
	if (Log.isLoggable(TAG, Log.VERBOSE)) {
	    Log.d(TAG, "data uri = " + dataUri.toString());
	}

	// Perform the query
        Cursor c = mResolver.query(dataUri, projection, null,null,null);
	if (c == null) {
	    Log.e(TAG, "getDataForContact() -- cursor is null");
            return null;
        }

	// Debuggery
	/*
	Log.d(TAG, "   rows = " + String.valueOf(c.getCount()));
	for (String d : c.getColumnNames()) {
	    Log.d(TAG, "     got column: " + d + "  ... index = " + c.getColumnIndex(d));
	}
	*/
	
	if (projection != null) {
	    ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
	    try {
		int count = c.getCount();
		for (int i = 0; i < count; i++) {
		    c.moveToNext();		    
		    HashMap<String, String> queryData = new HashMap<String,String>();
		    for (String proj : projection) {
			int idx = c.getColumnIndexOrThrow(proj);
			//Log.d(TAG, "     storing " + proj + "     idx=" + String.valueOf(idx));
			queryData.put(proj, c.getString(idx));
		    }
		    rows.add(queryData);
		}
	    } catch (IllegalArgumentException e) { 
		Log.e(TAG, "IllegalArgumentException: " + e.getMessage());
		e.printStackTrace();
		return null;
	    }
	    return rows;
	} else {
	    return null;
	}
    }

    //========================================================
    // 
    // getAllContacts()
    // 
    //========================================================
    public ArrayList<Contact> getAllContacts() {
	if (Log.isLoggable(TAG, Log.VERBOSE)) {
	    Log.d(TAG,"searchForContact() ");
	}
	Uri contactsUri = Contacts.CONTENT_URI;

	// Container to store found contacts
        ArrayList<Contact> results = new ArrayList<Contact>();

        // Perform a query
	String[] projection = {Contacts._ID, Contacts.DISPLAY_NAME, Contacts.LOOKUP_KEY};
        Cursor c = mResolver.query(contactsUri, projection, null,null,null);
	if (c == null) {
            return null;
        }

	// Populate container with stuff from query
	try {
	    int count = c.getCount();
	    if (count <= 0) {
		return null;
	    }
	    
	    String contactId = "";
	    String displayName = "";
	    String lookupKey =  "";

	    for (int i = 0; i < count; i++) {
		c.moveToNext();
		contactId = c.getString(0);
		displayName = c.getString(1);
		lookupKey =  c.getString(2);
		//Log.d(TAG,"Found contact: [" + contactId + "] " + displayName + "  " + lookupKey);
		
		// Populate results container 
		AmmoContacts.Contact lw = new AmmoContacts.Contact();
		String[] names = displayName.split(" ");
		lw.setName(names[0]); 
		lw.setLastName(names[1]);
		lw.setLookup(lookupKey);

		results.add(lw);
	    }

	} catch (IllegalArgumentException e) { 
	    Log.e(TAG, "IllegalArgumentException: " + e.getMessage());
	    e.printStackTrace();
	    return null;
	} catch (CursorIndexOutOfBoundsException e) {
	    Log.e(TAG, "Cursor out of bounds: " + e.getMessage());
	    e.printStackTrace();
	    return null;
	} catch (Throwable e) {
	    Log.e(TAG, "Exception: " + e.getMessage());
	    e.printStackTrace();
	    return null;
	} finally {
	    c.close();
        }

	return results;
    }

    //========================================================
    // 
    // lookupContact()
    // 
    //========================================================
    public Contact lookupContact(String lookupKey) {
        // Retrieve contact with provided uri
	Log.d(TAG,"lookupContact() ");

        ContentResolver cr = mResolver;

        Uri lookupUri = Uri.withAppendedPath(Contacts.CONTENT_LOOKUP_URI, lookupKey);

        Cursor c = cr.query(lookupUri, new String[]{Contacts.DISPLAY_NAME}, null,null,null);

	AmmoContacts.Contact lw = new AmmoContacts.Contact();
        try {
	    Log.d(TAG, "   rows = " + String.valueOf(c.getCount()));
            c.moveToFirst();
            String displayName = c.getString(0);
	    String[] names = displayName.split(" ");
	    lw.setName(names[0]); 
	    lw.setLastName(names[1]);
	    Log.d(TAG,"Found contact: " + displayName);
	    
	    // TODO: Get "other" data for this contact, i.e. with data query
	    /*
	    String[] dataProjection = {"mimetype","data1","data2","data3","data4"};
	    ArrayList<HashMap<String, String>> extraData = getDataForContact(contactId, dataProjection);
	    if (extraData != null) {
		// populate other portions of Contact object
	    }
	    */
        } finally {
            c.close();
        }
	return lw;
    }

    /*
    public void deleteContact(Uri contactUri) {
        // Retrieve contact with provided uri
        ContentResolver cr = mResolver;
        cr.delete(contactUri, null, null);
    }
    */
}

