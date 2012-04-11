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
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.util.Log;
import edu.vu.isis.ammo.launch.constants.Constants;


public class AmmoContacts {

    private static final String TAG = "AmmoContacts";

    private ContentResolver mResolver;

    public AmmoContacts() {
        mResolver = null;
    }


    private AmmoContacts(Context context) {
        this.mResolver = context.getContentResolver();
    }

    public static AmmoContacts newInstance(Context context) {
        return new AmmoContacts(context);
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

	private String userIdNum;
	public String getUserIdNumber() {
            return this.userIdNum;
        }
	public Contact setUserIdNumber(String val) {
            this.userIdNum = val;
            return this;
        }

	private String designator;
	private final int designatorLength = 3;
	public String getDesignator() {
            return this.designator;
        }
	public char[] getDesignatorAsCharArray() {
            return this.designator.toCharArray();
        }
	public Contact setDesignator(String val) {
	    // Designator is defined as exactly N characters -- enforce this
	    if (val.length() <= designatorLength) {
		this.designator = val;
	    } else {
		this.designator = val.substring(0,designatorLength - 1);
	    }
            return this;
        }

	private int rawContactId;
	public int getRawContactId() {
            return this.rawContactId;
        }
	public Contact setRawContactId(int val) {
            this.rawContactId = val;
            return this;
        }

    }

    //========================================================
    // 
    // updateContactEntry()
    // 
    // Update (save changes to) an existing contact in the 
    // contacts storage provider.
    //========================================================
    public Uri updateContactEntry(Contact lw) {
        Log.d(TAG, "Updating person: " + lw.getName() + " " 
	      + lw.getLastName() + " ... " + lw.getTigrUid() );
	
	ContentResolver cr = mResolver;

	// First find existing record so we can modify it
	Contact r = getContactByUserId(lw.getTigrUid());
	
	// If the contact isn't found, add it ("upsert" functionality)
	if (r == null) {
	    Log.d(TAG, "       no such existing user, inserting new contact ");
	    return insertContactEntry(lw);
	} else { 
	    if (Log.isLoggable(TAG, Log.VERBOSE)) {
		Log.d(TAG, "       found existing user : " + lw.getTigrUid());
	    }
	}

	int contactId = -1;
	contactId = r.getRawContactId();
	if (!(contactId > 0)) return null;

	// Then make a ContentProviderOperation to update this contact
	ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

	String callsign = lw.getCallSign();
        if (callsign == null) callsign = "";
        if (callsign.length() > 0) {
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
				   new String[] {String.valueOf(contactId), Constants.MIME_CALLSIGN,})
                    .withValue(ContactsContract.Data.DATA1, callsign)
                    .build());
	}

	// userid is a special case -- don't allow updates (but keep this snippet for future use)
	/*
	String userId = lw.getTigrUid();
        if (userId == null) userId = "";
        if (userId.length() > 0) {
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
		    .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
				   new String[] {String.valueOf(contactId), Constants.MIME_USERID,})
                    .withValue(ContactsContract.Data.DATA1, userId)
                    .build());
	}
	*/

	String userIdNum = lw.getUserIdNumber();
        if (userIdNum == null) userIdNum = "";
        if (userIdNum.length() > 0) {
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
		    .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
				   new String[] {String.valueOf(contactId), Constants.MIME_USERID_NUM,})
                    .withValue(ContactsContract.Data.DATA1, userIdNum)
                    .build());
	}
        String rank = lw.getRank();
        if (rank == null) rank = "";
        if (rank != null && rank.length() > 0) {
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
				   new String[] {String.valueOf(contactId), Constants.MIME_RANK,})
                    .withValue(ContactsContract.Data.DATA1, rank)
                    .build());
	}
	String designator = lw.getDesignator();
        if (designator == null) designator = "";
        if (designator != null && designator.length() > 0) {
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
				   new String[] {String.valueOf(contactId), Constants.MIME_DESIGNATOR,})
                    .withValue(ContactsContract.Data.DATA1, designator)
                    .build());
	}
	String branch = lw.getBranch();
        if (branch == null) branch = "";
        if (branch != null && branch.length() > 0) {
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
				   new String[] {String.valueOf(contactId), Constants.MIME_BRANCH,})
                    .withValue(ContactsContract.Data.DATA1, branch)
                    .build());
	}

	// Structured name
	ContentProviderOperation.Builder snb = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
	    .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
			   new String[] {String.valueOf(contactId), 
					 ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,});
        String firstname = lw.getName();
        if (firstname == null) firstname = "";
        if (firstname.length() > 0) {
            snb.withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, firstname);
	}
        String lastname = lw.getLastName();
        if (lastname == null) lastname = "";
        if (lastname.length() > 0) {
            snb.withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, lastname);
	}
        String middlename = lw.getMiddleName();
        if (middlename == null) middlename = "";
        if (middlename.length() > 0) {
            snb.withValue(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME, middlename);
	}
	ops.add(snb.build());

	// Unit info
	String unit = lw.getUnit();
        if (unit == null) unit = "";
        if (unit.length() > 0)
            {
                ContentProviderOperation.Builder opbld = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
		opbld.withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
			   new String[] {String.valueOf(contactId), Constants.MIME_UNIT_NAME });
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

	// Apply the content provider operations
	Uri uriToModify = null;
	try {
            ContentProviderResult[] cpres = cr.applyBatch(ContactsContract.AUTHORITY, ops);
	    uriToModify = cpres[0].uri;
        } catch (Exception ex) {
            Log.e(TAG, "Exception encoutered while updating contact: " + ex.toString());
	    return null;
        }

	return uriToModify;
    }

    //========================================================
    // 
    // insertContactEntry()
    // 
    // Add a new contact to the contacts storage provider.
    // 
    //========================================================
    public Uri insertContactEntry(Contact lw) {
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

	String userIdNum = lw.getUserIdNumber();
        if (userIdNum == null) userIdNum = "";
        if (userIdNum.length() > 0)
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_USERID_NUM)
                    .withValue(ContactsContract.Data.DATA1, userIdNum)
                    .build());

	String designator = lw.getDesignator();
        if (designator == null) designator = "";
        if (designator.length() > 0)
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_DESIGNATOR)
                    .withValue(ContactsContract.Data.DATA1, designator)
                    .build());

        String rank = lw.getRank();
        if (rank == null) rank = "";
        if (rank != null && rank.length() > 0)
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_RANK)
                    .withValue(ContactsContract.Data.DATA1, rank)
                    .build());

	String branch = lw.getBranch();
        if (branch == null) branch = "";
        if (branch.length() > 0)
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_BRANCH)
                    .withValue(ContactsContract.Data.DATA1, branch)
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
    // deleteContactEntry()
    // 
    // Delete an existing contact in the local contacts storage.
    //========================================================
    public Uri deleteContactEntry(Contact lw) {
	Log.d(TAG, "Removing contact: " + lw.getName() + " " 
	      + lw.getLastName() + " ... " + lw.getTigrUid() );
	
	ContentResolver cr = mResolver;

	// First find existing record so we can delete it
	Contact r = getContactByUserId(lw.getTigrUid());

	if (r == null) {
	    Log.d(TAG, "       no such user to delete, no further action taken");
	    return null;
	} else {
	    Log.d(TAG, "       found existing user " + lw.getTigrUid() + ", will be deleted");
	}
	
	int contactId = -1;
	contactId = r.getRawContactId();
	if (!(contactId > 0)) return null;

	Uri uriToDelete = Uri.parse("content://com.android.contacts/raw_contacts/" + String.valueOf(contactId));
	// that is: ContactsContract.RawContacts.CONTENT_URI + "/" + contactId

	// Then make a ContentProviderOperation to delete this contact
	ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

	// Entries in data table
	ops.add(ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
		.withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=?",
			       new String[] { String.valueOf(contactId) } )
		.build());

	// Entries in contacts / raw_contacts tables
	ops.add(ContentProviderOperation.newDelete(uriToDelete).build());
	
	try {
            ContentProviderResult[] cpres = cr.applyBatch(ContactsContract.AUTHORITY, ops);
	    uriToDelete = cpres[0].uri;
        } catch (Exception ex) {
            Log.e(TAG, "Exception encoutered while deleting contact: " + ex.toString() + " : " + ex.getMessage());
	    ex.printStackTrace();
	    return null;
        }

	return uriToDelete;

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
	Cursor c = null;
	String[] projection = {Contacts._ID, Contacts.DISPLAY_NAME, Contacts.LOOKUP_KEY};
	try {
	    c = mResolver.query(filterUri, projection, null,null,null);
	    if (c == null) {
		return null;
	    }
	} catch (Throwable e) {
	    Log.e(TAG, "Exception: " + e.getMessage());
	    e.printStackTrace();
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
		if (names.length > 0) {
		    if (names[0] != null) {
			lw.setName(names[0]); 
		    }
		}
		if (names.length > 1) {
		    if (names[1] != null) {
			lw.setLastName(names[1]);
		    }
		}

		lw.setLookup(lookupKey);
		lw.setRawContactId(Integer.parseInt(contactId));

		// Get "other" data for this contact, i.e. with data query
		String[] dataProjection = {"mimetype","data1","data2","data3","data4"};
		ArrayList<HashMap<String, String>> extraData = getDataForContact(contactId, dataProjection);
		if (extraData != null) {
		    // If the data query failed (null), just keep what we've 
		    // got so far (name and lookup) and go on to the next row.
		    // Otherwise get data, put in container.
		    populateContactData(extraData, lw);
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
	} catch (ArrayIndexOutOfBoundsException e) {
	    Log.e(TAG, "Array index out of bounds: " + e.getMessage());
	    e.printStackTrace();
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
    // populateContactData()
    // 
    //========================================================
    private void populateContactData(ArrayList<HashMap<String, String>> extraData, 
				     AmmoContacts.Contact lw) {
	Iterator<HashMap<String, String>> it = extraData.iterator();
	while (it.hasNext()) {
	    try {
		HashMap<String, String> f = it.next();
		if (f != null) {
		    /*
		    if (Log.isLoggable(TAG, Log.VERBOSE)) {
			Log.d(TAG, " ITERATOR: " + String.valueOf(f.size()));
			for (String d : f.keySet()) {
			    Log.d(TAG, "     " + d + "= " + f.get(d));
			}
		    }
		    */
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
		    if (Constants.MIME_USERID_NUM.equals(f.get("mimetype"))) {
			lw.setUserIdNumber(f.get("data1") );
		    }
		    if (Constants.MIME_DESIGNATOR.equals(f.get("mimetype"))) {
			lw.setDesignator(f.get("data1") );
		    }

		    if (StructuredName.CONTENT_ITEM_TYPE.equals(f.get("mimetype"))) {
			lw.setName(f.get("data2"));
			lw.setLastName(f.get("data3"));
		    }
		    
		}
	    } catch (NoSuchElementException e) {
		Log.e(TAG, "NoSuchElementException: " + e.getMessage());
		e.printStackTrace();
		continue;
	    }
	}
    }

    //========================================================
    // 
    // getDataForContact()
    // 
    //========================================================
    private ArrayList<HashMap<String, String>> getDataForContact(String contactId, 
								 String[] projection) {
	if (Log.isLoggable(TAG, Log.VERBOSE)) {
	    Log.d(TAG, "getDataForContact() ");
	}
	
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

	// Debugging
	/*
	Log.d(TAG, "   rows = " + String.valueOf(c.getCount()));
	for (String d : c.getColumnNames()) {
	    Log.d(TAG, "     got column: " + d + "  ... index = " + c.getColumnIndex(d));
	}
	*/
	
	if (projection == null) {
	    return null;
	}

	try { 
	    ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
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
	    return rows;
	} catch (IllegalArgumentException e) { 
		Log.e(TAG, "IllegalArgumentException: " + e.getMessage());
		e.printStackTrace();
		return null;
	} catch (Throwable e) {
	    Log.e(TAG, "Exception: " + e.getMessage());
	    e.printStackTrace();
	    return null;
	} finally {
	    c.close();
        }
    }

    //========================================================
    // 
    // getAllContacts()
    // 
    //========================================================
    public ArrayList<Contact> getAllContacts() {
	if (Log.isLoggable(TAG, Log.VERBOSE)) {
	    Log.d(TAG,"getAllContacts() ");
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
		if (displayName != null) {
		    String[] names = displayName.split(" ");
		    if (names.length > 0) {
			if (names[0] != null) {
			    lw.setName(names[0]); 
			}
		    }
		    if (names.length > 1) {
			if (names[1] != null) {
			    lw.setLastName(names[1]);
			}
		    }
		} else {
		    Log.e(TAG, "Error retrieving name for contact " + contactId);
		    continue;
		}
		lw.setLookup(lookupKey);
		lw.setRawContactId(Integer.parseInt(contactId)); 
		
		String[] dataProjection = {"mimetype","data1","data2","data3","data4"};
		ArrayList<HashMap<String, String>> extraData = getDataForContact(contactId, dataProjection);
		if (extraData != null) {
		    // populate other portions of Contact object
		    populateContactData(extraData, lw);
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

	return results;
    }

    //========================================================
    // 
    // getContactByLookupKey()
    // 
    //========================================================
    public Contact getContactByLookupKey(String lookupKey) {
        // Retrieve contact with provided uri
	Log.d(TAG,"getContactByLookupKey() ");

        ContentResolver cr = mResolver;

        Uri lookupUri = Uri.withAppendedPath(Contacts.CONTENT_LOOKUP_URI, lookupKey);
	
        Cursor c = cr.query(lookupUri, new String[]{Contacts.DISPLAY_NAME,Contacts._ID}, null,null,null);

	AmmoContacts.Contact lw = new AmmoContacts.Contact();
        try {
	    Log.d(TAG, "   rows = " + String.valueOf(c.getCount()));
            c.moveToFirst();
            String displayName = c.getString(0);	    
	    if (displayName != null) {
		String[] names = displayName.split(" ");
		if (names.length > 0) {
		    if (names[0] != null) {
			lw.setName(names[0]); 
		    }
		}
		if (names.length > 1) {
		    if (names[1] != null) {
			lw.setLastName(names[1]);
		    }
		}
	    } else {
		Log.e(TAG, "Error retrieving name for contact: display name is null");
	    }

	    String contactId = c.getString(1);
	    Log.d(TAG,"Found contact: " + displayName + "  id=" + contactId);
	    
	    // Get other data for this contact, i.e. with data query
	    String[] dataProjection = {"mimetype","data1","data2","data3","data4"};
	    ArrayList<HashMap<String, String>> extraData = getDataForContact(contactId, dataProjection);
	    if (extraData != null) {
		populateContactData(extraData, lw);
	    }
	    
        } finally {
            c.close();
        }
	return lw;
    }

    //========================================================
    // 
    // getContactByIdNumber()
    // 
    //========================================================
    public Contact getContactByIdNumber(long idNumber) {
	// Retrieve contact with provided unique ID number
	Log.d(TAG,"getContactByIdNumber() ");

	ContentResolver cr = mResolver;
	Uri dataUri = Data.CONTENT_URI;  
	Log.d(TAG, "data uri = " + dataUri.toString());
	String[] projection = {Data.RAW_CONTACT_ID, Data.DATA1};
	String selection=Data.MIMETYPE+"=? AND " + Data.DATA1+"=?";
	String[] selectionArgs={Constants.MIME_USERID_NUM, String.valueOf(idNumber)};

	// Query the contacts content provider
	Cursor c = null;
	try {
	    c = cr.query(dataUri, projection, selection, selectionArgs,null);
	    if (c == null) {
		Log.e(TAG, "getContactByIdNumber() -- cursor is null");
		return null;
	    }
	} catch (Throwable e) {
	    Log.e(TAG, "Exception: " + e.getMessage());
	    e.printStackTrace();
	    return null;
	}

	// Process the query's output
	try {
	    int count = c.getCount();	    
	    //Log.d(TAG, "getContactByIdNumber() -- returned " + String.valueOf(count) + " rows");
	    if (count < 1) {
		return null;
	    }

	    c.moveToFirst();
	    String contactId = c.getString(0);

	    AmmoContacts.Contact lw = new AmmoContacts.Contact();
	    lw.setUserIdNumber(c.getString(1) );

	    // Get other data for this contact
	    String[] dataProjection = {"mimetype","data1","data2","data3","data4"};
	    ArrayList<HashMap<String, String>> otherData = getDataForContact(contactId, dataProjection);
	    if (otherData != null) {
		populateContactData(otherData, lw);
	    }

	    return lw;
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
    }

    //========================================================
    // 
    // getContactByUserId()
    // 
    // Given a contact's username, retrieve other data for this
    // contact and return it in a Contact object.
    //========================================================
    public Contact getContactByUserId(String userId) {
	Log.d(TAG,"getContactByUserId() ");

	Uri f = Uri.parse("content://" + ContactsContract.AUTHORITY 
			  + "/data/userid/" + userId);
	Log.d(TAG, "  searching for uri = " + f.toString());

	@SuppressWarnings("unused")
	int contactId = -1;
	Cursor cursor = null;
	try {
	    String[] projection = {"contact_id", "lookup"};  // more...
	    cursor = mResolver.query(f, projection, null, null, null);
	    if (cursor == null) {
		Log.e(TAG, "getContactByUserId() -- cursor is null");
		return null;
	    }
	} catch (Throwable e) {
	    Log.e(TAG, "An exception occurred: " + e.getMessage());
	    e.printStackTrace();
	    return null;
	}
	
	// Process the query's output
	try {
	    int count = cursor.getCount();	    
	    if (count < 1) {
		return null;
	    }

	    cursor.moveToFirst();
	    String contactIdStr = cursor.getString(0);

	    AmmoContacts.Contact lw = new AmmoContacts.Contact();
	    lw.setUserIdNumber(cursor.getString(1) );
	    lw.setRawContactId(Integer.parseInt(contactIdStr));

	    // Get other data for this contact
	    String[] dataProjection = {"mimetype","data1","data2","data3","data4"};
	    ArrayList<HashMap<String, String>> otherData = getDataForContact(contactIdStr, dataProjection);
	    if (otherData != null) {
		populateContactData(otherData, lw);
	    }

	    return lw;
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
            cursor.close();
        }
    }

    //========================================================
    // 
    // findExistingContact()
    // 
    // Internal utility function to get the URI for an existing 
    // contact in the database.
    //========================================================
    @SuppressWarnings("unused")
	private Uri findExistingContact(Contact lw) {
	Uri rval = null;
	Log.d(TAG, "findExistingContact");
	
	// Brute force search of all existing contacts

	// First get list of all contacts
	//ArrayList<Contact> allContacts = getAllContacts();
	ArrayList<Contact> allContacts = searchForContact(lw.getTigrUid());
	if (allContacts == null) {
	    Log.d(TAG, "  Query returned no results (null)");
	    return rval;
	}
	
	// Then search the list for a match with this contact
	Log.d(TAG, "  Found " + String.valueOf(allContacts.size()) + " contacts");
	Iterator<AmmoContacts.Contact> it = allContacts.iterator();
	int contactId = -1;
	while (it.hasNext()) {
	    try {
		Contact f = it.next();
		String name = f.getName();
		String lname = f.getLastName();
		Contact g = getContactByLookupKey(f.getLookup());
		if (Log.isLoggable(TAG, Log.VERBOSE)) {
		    Log.d(TAG, "   " + name + " " + lname+ ";");
		}

		if (lw.getTigrUid().equals(g.getTigrUid())) {
		    contactId = f.getRawContactId();
		    if (Log.isLoggable(TAG, Log.VERBOSE)) {
			Log.d(TAG, "   -> MATCH:   contact id = " + String.valueOf(contactId));
		    }		    
		    break;
		}
	    } catch (NoSuchElementException e) {
		Log.e(TAG, "NoSuchElementException: " + e.getMessage());
		e.printStackTrace();
		continue;
	    }
	}

	if (contactId > 0) {
	    rval = Uri.parse("content://com.android.contacts/raw_contacts/" + String.valueOf(contactId));
	}
	if (rval != null) {
	    if (Log.isLoggable(TAG, Log.VERBOSE)) {
		Log.d(TAG, "            existing contact uri: " + rval.toString());
	    }
	}

	// TODO: re-plumb the below to do a userid lookup rather than filter
	/*
	// Not the nice way to do this...
	Uri f = Uri.parse("content://" + ContactsContract.AUTHORITY 
			  + "/data/userid/filter/" + lw.getTigrUid());
	Log.d(TAG, "  searching for uri = " + f.toString());

	int contactId = -1;
	try {
	    String[] projection = {"contact_id", "lookup"};  // more...
	    Cursor cursor = mResolver.query(f, projection, null, null, null);
	    if (cursor != null) {

		// <DEBUG>
		Log.d(TAG, "   cursor size = " + String.valueOf(cursor.getCount()) );
		Log.d(TAG, " -- examine cursor -- ");
		for (String proj : cursor.getColumnNames() ) {
		    Log.d(TAG, "       has column = " + proj + " -- index = "
			  + cursor.getColumnIndex(proj) );
		}
		int dc = cursor.getCount();
		Log.d(TAG, "       cursor rows: " + String.valueOf(dc));
		if (dc > 0) {
		    while (cursor.moveToNext() && (cursor.getPosition() < dc) ) {
			Log.d(TAG, "          row " + String.valueOf(cursor.getPosition()));
			// Thing of interest
			contactId = cursor.getInt(cursor.getColumnIndex("contact_id"));
			Log.d(TAG, "            contact id: " + String.valueOf(contactId));
		    }
		}
		// </DEBUG>
	    } else {
		Log.d(TAG, "null");
	    }
	} catch (IllegalArgumentException e) {
	    Log.e(TAG, "IllegalArgumentException caught: " + e.getMessage());
	    e.printStackTrace();
	    return null;
	} catch (Throwable e) {
	    Log.e(TAG, "An exception occurred: " + e.getMessage());
	    e.printStackTrace();
	    return null;
	}
	
	if (contactId > 0) {
	    rval = Uri.parse("content://com.android.contacts/raw_contacts/" + String.valueOf(contactId));
	}
	if (rval != null) {
	    Log.d(TAG, "            existing contact uri: " + rval.toString());
	}
	*/
	return rval;
    }

}

