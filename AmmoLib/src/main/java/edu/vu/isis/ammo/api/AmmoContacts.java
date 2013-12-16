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
import android.database.SQLException;
import android.net.Uri;
import android.util.Log;
import edu.vu.isis.ammo.contacts.provider.ContactsContract;
import edu.vu.isis.ammo.contacts.provider.ContactsContract.CommonDataKinds.StructuredName;
import edu.vu.isis.ammo.contacts.provider.ContactsContract.Contacts;
import edu.vu.isis.ammo.contacts.provider.ContactsContract.Data;
import edu.vu.isis.ammo.launch.constants.Constants;

/**
 * An AmmoContacts instance is intended to be a singleton object which provides
 * a simple interface to the underlying contacts content provider. The intended
 * use is to instantiate the object, then use its methods to add, find, delete
 * 
 * This class also contains a public Contact class for convenient storage of 
 * TA contact info when interacting with the content provider through this interface.
 * 
 * To instantiate:
 * 
 * <pre>
 * {@code
 * Context myContext = getContext();
 * AmmoContacts ac = AmmoContacts.newInstance(myContext);
 * }
 * </pre>
 * 
 * Example use - adding a new contact:
 * 
 * <pre>
 * {@code
 * AmmoContacts.Contact contact = new AmmoContacts.Contact();
 * contact.setName("Foo");
 * contact.setLastName("Bar");
 * contact.setUserId("foo.bar");
 * Uri rval = ac.insertContactEntry(contact);
 * }
 * </pre>
 * 
 * Here the returned URI is the location of the new contact in the content provider; 
 * the insert was successful if the URI is non-null.
 */

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



    /**
     * Contact class
     *
     * Description of a TA contact: name, rank, unit, branch,
     * user ID (various), and so forth.
     *
     */
    static public class Contact {
        public Contact() { }

	private Contact(Builder b) {
            setName(b.name);
            setMiddleName(b.middle_initial );
            setLastName(b.lastname );
            setRank(b.rank );
            setCallSign(b.callsign );
            setBranch(b.branch );
            setUnit(b.unit );
            setUnitDivision(b.unitDivision );
            setUnitBrigade(b.unitBrigade );
            setUnitBattalion(b.unitBattalion );
            setUnitCompany(b.unitCompany );
            setUnitPlatoon(b.unitPlatoon );
            setUnitSquad(b.unitSquad );
            setEmail(b.email );
            setPhone(b.phone );
            setUserId(b.userid );
            setUserIdNumber(b.userIdNum );
            setDesignator(b.designator );
            setLookup(b.lookup );
            setRawContactId(b.rawContactId );
        }

        private String lookup;
        public String getLookup() {
	    if (this.lookup == null) return null;
            return new String(this.lookup);
        }
        public Contact setLookup(String val) {
	    if (val != null) {
		this.lookup = new String(val);
	    }
            return this;
        }

        private String name;
        public String getName() {
	    // return ((this.name == null) ? null : new String(this.name));
	    if (this.name == null) return null;
            return new String(this.name);
        }
        public Contact setName(String val) {
	    if (val != null) {
		this.name = new String(val);
	    }
            return this;
        }

        private String middle_initial;
        public String getMiddleName() {
	    if (this.middle_initial == null) return null;
            return new String(this.middle_initial);
        }
        public Contact setMiddleName(String val) {
	    if (val != null) {
		this.middle_initial = new String(val);
	    }
            return this;
        }

        private String lastname;
        public String getLastName() {
	    if (this.lastname == null) return null;
            return new String(this.lastname);
        }
        public Contact setLastName(String val) {
	    if (val != null) {
		this.lastname = new String(val);
	    }
            return this;
        }

        private String rank;
        public String getRank() {
	    if (this.rank == null) return null;
            return new String(this.rank);
        }
        public Contact setRank(String val) {
	    if (val != null) {
		this.rank = new String(val);
	    }
            return this;
        }

        private String callsign;
        public String getCallSign() {
	    if (this.callsign == null) return null;
            return new String(this.callsign);
        }
        public Contact setCallSign(String val) {
	    if (val != null) {
		this.callsign = new String(val);
	    }
            return this;
        }

        private String branch;
        public String getBranch() {
	    if (this.branch == null) return null;
            return new String(this.branch);
        }
        public Contact setBranch(String val) {
	    if (val != null) {
		this.branch = new String(val);
	    }
            return this;
        }

        private String unit;
        public String getUnit() {
	    if (this.unit == null) return null;
            return new String(this.unit);
        }
        public Contact setUnit(String val) {
	    if (val != null) {
		this.unit = new String(val);
	    }
            return this;
        }


        private String unitDivision;
        public String getUnitDivision() {
	    if (this.unitDivision == null) return null;
            return new String(this.unitDivision);
        }
        public Contact setUnitDivision(String val) {
	    if (val != null) {
		this.unitDivision = new String(val);
	    }
            return this;
        }
        private String unitBrigade;
        public String getUnitBrigade() {
	    if (this.unitBrigade == null) return null;
            return new String(this.unitBrigade);
        }
        public Contact setUnitBrigade(String val) {
	    if (val != null) {
		this.unitBrigade = new String(val);
	    }
            return this;
        }
        private String unitBattalion;
        public String getUnitBattalion() {
	    if (this.unitBattalion == null) return null;
            return new String(this.unitBattalion);
        }
        public Contact setUnitBattalion(String val) {
	    if (val != null) {
		this.unitBattalion = new String(val);
	    }
            return this;
        }
        private String unitCompany;
        public String getUnitCompany() {
	    if (this.unitCompany == null) return null;
            return new String(this.unitCompany);
        }
        public Contact setUnitCompany(String val) {
	    if (val != null) {
		this.unitCompany = new String(val);
	    }
            return this;
        }
        private String unitPlatoon;
        public String getUnitPlatoon() {
	    if (this.unitCompany == null) return null;
            return new String(this.unitPlatoon);
        }
        public Contact setUnitPlatoon(String val) {
	    if (val != null) {
		this.unitPlatoon = new String(val);
	    }
            return this;
        }
        private String unitSquad;
        public String getUnitSquad() {
	    if (this.unitSquad == null) return null;
            return new String(this.unitSquad);
        }
        public Contact setUnitSquad(String val) {
	    if (val != null) {
		this.unitSquad = new String(val);
	    }
            return this;
        }

        private String email;
        public String getEmail() {
	    if (this.email == null) return null;
            return new String(this.email);
        }
        public Contact setEmail(String val) {
	    if (val != null) {
		this.email = new String(val);
	    }
            return this;
        }
	
        private String phone;
        public String getPhone() {
	    if (this.phone == null) return null;
            return new String(this.phone);
        }
        public Contact setPhone(String val) {
	    if (val != null) {
		this.phone = new String(val);
	    }
            return this;
        }

        //private String tigrUid;
        private String tigruid;
        public String getTigrUid() {
	    if (this.tigruid == null) return null;
            return new String(this.tigruid);
        }
        public Contact setTigrUid(String val) {
	    if (val != null) {
		this.tigruid = new String(val);
	    }
            return this;
        }

        public String getUserId() {
            return this.getTigrUid();
        }
        public Contact setUserId(String val) {
            this.setTigrUid(val);
            return this;
        }

        private String userIdNum;
        public String getUserIdNumber() {
	    if (this.userIdNum == null) return null;
            return new String(this.userIdNum);
        }
        public Contact setUserIdNumber(String val) {
	    if (val != null) {
		this.userIdNum = new String(val);
	    }
            return this;
        }

        private String designator;
        private final int designatorLength = 3;
        public String getDesignator() {
	    if (this.designator == null) return null;
            return new String(this.designator);
        }
        public char[] getDesignatorAsCharArray() {
	    if (this.designator == null) return null;
            return new String(this.designator).toCharArray();
        }
        public Contact setDesignator(String val) {
	    if (val != null) {
		// Designator is defined as exactly N characters -- enforce this
		if (val.length() <= designatorLength) {
		    this.designator = new String(val);
		} else {
		    this.designator = new String(val.substring(0,designatorLength - 1));
		}
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

	//-------------------------------
	// Contact builder
	//-------------------------------
	/**
	 * Contact builder
	 * 
	 * Example usage:
	 * 
	 * <pre>
	 * {@code
	 * AmmoContacts.Contact c = new AmmoContacts.Contact.Builder()
	 *   .name("foo")
	 *   .lastname("bar")
	 *   .userid("fbar")
	 *   .callsign("Climber 10")
	 *   .build();
	 * }
	 * </pre>
	 */
        public static class Builder {
            private String lookup;
            private String name;
            private String middle_initial;
            private String lastname;
            private String rank;
            private String callsign;
            private String branch;
            private String unit;
            private String unitDivision;
            private String unitBrigade;
            private String unitBattalion;
            private String unitCompany;
            private String unitPlatoon;
            private String unitSquad;
            private String email;
            private String phone;
            private String userid;
            private String userIdNum;
            private String designator;
            private int rawContactId;

            public Builder name(String value) {
                name = new String(value);
                return this;
            }

            public Builder middlename(String value) {
                middle_initial = new String(value);
                return this;
            }

            public Builder lastname(String value) {
                lastname = new String(value);
                return this;
            }

            public Builder rank(String value) {
                rank = new String(value);
                return this;
            }

            public Builder callsign(String value) {
                callsign = new String(value);
                return this;
            }

            public Builder branch(String value) {
                branch = new String(value);
                return this;
            }

            public Builder unit(String value) {
                unit = new String(value);
                return this;
            }

            public Builder unitDivision(String value) {
                unitDivision = new String(value);
                return this;
            }

            public Builder unitBrigade(String value) {
                unitBrigade = new String(value);
                return this;
            }

            public Builder unitBattalion(String value) {
                unitBattalion = new String(value);
                return this;
            }

            public Builder unitCompany(String value) {
                unitCompany = new String(value);
                return this;
            }

            public Builder unitPlatoon(String value) {
                unitPlatoon = new String(value);
                return this;
            }

            public Builder unitSquad(String value) {
                unitSquad = new String(value);
                return this;
            }

            public Builder userid(String value) {
                userid = new String(value);
                return this;
            }

            public Builder useridnumber(String value) {
                userIdNum = new String(value);
                return this;
            }

            public Builder designator(String value) {
                designator = new String(value);
                return this;
            }

            public Builder lookup(String value) {
                lookup = new String(value);
                return this;
            }

            public Builder rawcontactid(int value) {
                rawContactId = value;
                return this;
            }

            public Builder email(String value) {
                email = new String(value);
                return this;
            }

            public Builder phone(String value) {
                phone = new String(value);
                return this;
            }

            public Contact build() {
                return new Contact(this);
            }
        }
    }

    //========================================================
    //
    // updateContactEntry()
    //
    //========================================================
    /** 
     * Update (save changes to) an existing contact in the contacts storage
     * provider.
     *
     * Example usage:
     * 
     * <pre>
     * {@code
     * AmmoContacts.Contact contact = new AmmoContacts.Contact();
     * contact.setName("Foo");
     * contact.setLastName("Bar");
     * contact.setUserId("foo.bar");
     * contact.setCallSign("new callsign"); // new data for existing contact
     * Uri rval = ac.updateContactEntry(contact);
     * }
     * </pre>
     * 
     * 
     * @param lw A Contact object corresponding to the contact data to update.
     * 	        The userID will be used to find the existing contact. If found,
     *		all the contacts data will be updated to the values held in the
     *		lw param. If NOT found, a new contact will be inserted.
     * @return The URI of the updated (or added) contact if successful, null otherwise.
     */
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

        // Make a list of ContentProviderOperations to update this contact
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        // Callsign
        {
            String callsign = lw.getCallSign();
            String currentCallsign = r.getCallSign();
            if (callsign == null) callsign = "";
            if (currentCallsign == null) currentCallsign = "";

            // Case 1: update
            if ((callsign.length() > 0) && (currentCallsign.length() > 0)) {
                ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
                                       + ContactsContract.Data.MIMETYPE + "=?",
                                       new String[] {String.valueOf(contactId), Constants.MIME_CALLSIGN,})
                        .withValue(ContactsContract.Data.DATA1, callsign)
                        .build());
            }
            // Case 2: remove existing
            else if ((callsign.length() <= 0) && (currentCallsign.length() > 0)) {
                Uri dataUri = hasData(Constants.MIME_CALLSIGN, contactId);
                if (dataUri != null) {
                    ops.add(ContentProviderOperation.newDelete(dataUri).build());
                }
            }
            // Case 3: add new
            else if ((callsign.length() > 0) && (currentCallsign.length() <= 0)) {
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValue(ContactsContract.Data.RAW_CONTACT_ID, contactId)
                        .withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_CALLSIGN)
                        .withValue(ContactsContract.Data.DATA1, callsign)
                        .build());

            }
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

        // User ID Number
        {
            String userIdNum = lw.getUserIdNumber();
            String currentIdNum = r.getUserIdNumber();
            if (userIdNum == null) userIdNum = "";
            if (currentIdNum == null) currentIdNum = "";

            // Case 1: update
            if ((userIdNum.length() > 0) && (currentIdNum.length() > 0)) {
                ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
                                       + ContactsContract.Data.MIMETYPE + "=?",
                                       new String[] {String.valueOf(contactId), Constants.MIME_USERID_NUM,})
                        .withValue(ContactsContract.Data.DATA1, userIdNum)
                        .build());
            }
            // Case 2: remove existing
            else if ((userIdNum.length() <= 0) && (currentIdNum.length() > 0)) {
                Uri dataUri = hasData(Constants.MIME_USERID_NUM, contactId);
                if (dataUri != null) {
                    ops.add(ContentProviderOperation.newDelete(dataUri).build());
                }
            }
            // Case 3: add new
            else if ((userIdNum.length() > 0) && (currentIdNum.length() <= 0)) {
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValue(ContactsContract.Data.RAW_CONTACT_ID, contactId)
                        .withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_USERID_NUM)
                        .withValue(ContactsContract.Data.DATA1, userIdNum)
                        .build());
            }
        }


        // Rank
        {
            String rank = lw.getRank();
            String currentRank = r.getRank();
            if (rank == null) rank = "";
            if (currentRank == null) currentRank = "";

            // Case 1: update existing value with new value (both 'new' and 'existing' have values)
            if ((rank.length() > 0) && (currentRank.length() > 0)) {
                ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
                                       + ContactsContract.Data.MIMETYPE + "=?",
                                       new String[] {String.valueOf(contactId), Constants.MIME_RANK,})
                        .withValue(ContactsContract.Data.DATA1, rank)
                        .build());
            }
            // Case 2: remove existing value ('new' value is absent)
            else if ((rank.length() <= 0) && (currentRank.length() > 0)) {
                Uri dataUri = hasData(Constants.MIME_RANK, contactId);
                if (dataUri != null) {
                    ops.add(ContentProviderOperation.newDelete(dataUri).build());
                }
            }
            // Case 3: add new value ('new' value present, 'existing' value absent)
            else if ((rank.length() > 0) && (currentRank.length() <= 0)) {
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValue(ContactsContract.Data.RAW_CONTACT_ID, contactId)
                        .withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_RANK)
                        .withValue(ContactsContract.Data.DATA1, rank)
                        .build());
            }
        }

        // PLI Designator
        {
            String designator = lw.getDesignator();
            String currentDesig = r.getDesignator();
            if (designator == null) designator = "";
            if (currentDesig == null) currentDesig = "";

            // Case 1: update
            if ((designator.length() > 0) && (currentDesig.length() > 0)) {
                ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
                                       + ContactsContract.Data.MIMETYPE + "=?",
                                       new String[] {String.valueOf(contactId), Constants.MIME_DESIGNATOR,})
                        .withValue(ContactsContract.Data.DATA1, designator)
                        .build());
            }
            // Case 2: remove existing
            else if ((designator.length() <= 0) && (currentDesig.length() > 0)) {
                Uri dataUri = hasData(Constants.MIME_DESIGNATOR, contactId);
                if (dataUri != null) {
                    ops.add(ContentProviderOperation.newDelete(dataUri).build());
                }
            }
            // Case 3: add new
            else if ((designator.length() > 0) && (currentDesig.length() <= 0)) {
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValue(ContactsContract.Data.RAW_CONTACT_ID, contactId)
                        .withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_DESIGNATOR)
                        .withValue(ContactsContract.Data.DATA1, designator)
                        .build());
            }
        }

        // Branch
        {
            String branch = lw.getBranch();
            String currentBranch = r.getBranch();
            if (branch == null) branch = "";
            if (currentBranch == null) currentBranch = "";

            // Case 1: update
            if ((branch.length() > 0) && (currentBranch.length() > 0)) {
                ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
                                       + ContactsContract.Data.MIMETYPE + "=?",
                                       new String[] {String.valueOf(contactId), Constants.MIME_BRANCH,})
                        .withValue(ContactsContract.Data.DATA1, branch)
                        .build());
            }
            // Case 2: remove existing
            else if ((branch.length() <= 0) && (currentBranch.length() > 0)) {
                Uri dataUri = hasData(Constants.MIME_BRANCH, contactId);
                if (dataUri != null) {
                    ops.add(ContentProviderOperation.newDelete(dataUri).build());
                }
            }
            // Case 3: add new
            else if ((branch.length() > 0) && (currentBranch.length() <= 0)) {
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValue(ContactsContract.Data.RAW_CONTACT_ID, contactId)
                        .withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_BRANCH)
                        .withValue(ContactsContract.Data.DATA1, branch)
                        .build());
            }
        }

        // Structured name
        {
            ContentProviderOperation.Builder snb = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
                               + ContactsContract.Data.MIMETYPE + "=?",
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

            // The display name should be the user id
            String displayName = "";
            String userId = r.getUserId();  // Existing value, because it shouldn't be changing
            if (userId != null && userId.length() > 0) {
                displayName = userId;
            }
            if (displayName.length() > 0) {
                snb.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName);
            }


            ops.add(snb.build());
        }

        // Unit info
        {
            String unit = lw.getUnit();
            String currentUnit = r.getUnit();
            if (unit == null) unit = "";
            if (currentUnit == null) currentUnit = "";

            // Case 1: update
            if ((unit.length() > 0) && (currentUnit.length() > 0)) {
                ContentProviderOperation.Builder opUnit = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);

                opUnit.withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
                                     + ContactsContract.Data.MIMETYPE + "=?",
                                     new String[] {String.valueOf(contactId), Constants.MIME_UNIT_NAME })
                    .withValue(ContactsContract.Data.DATA1, unit);

                String division = lw.getUnitDivision();
                if (division == null) division = "";
                if (division.length() > 0)
                    {
                        opUnit.withValue(ContactsContract.Data.DATA2, division);
                    }

                String brigade = lw.getUnitBrigade();
                if (brigade == null) brigade = "";
                if (brigade.length() > 0)
                    {
                        opUnit.withValue(ContactsContract.Data.DATA3, brigade);
                    }

                String battalion = lw.getUnitBattalion();
                if (battalion == null) battalion = "";
                if (battalion.length() > 0)
                    {
                        opUnit.withValue(ContactsContract.Data.DATA4, battalion);
                    }

                String company = lw.getUnitCompany();
                if (company == null) company = "";
                if (company.length() > 0)
                    {
                        opUnit.withValue(ContactsContract.Data.DATA5, company);
                    }

                String platoon = lw.getUnitPlatoon();
                if (platoon == null) platoon = "";
                if (platoon.length() > 0)
                    {
                        opUnit.withValue(ContactsContract.Data.DATA6, platoon);
                    }

                String squad = lw.getUnitSquad();
                if (squad == null) squad = "";
                if (squad.length() > 0)
                    {
                        opUnit.withValue(ContactsContract.Data.DATA7, squad);
                    }

                ops.add(opUnit.build());
            }
            // Case 2: remove existing
            else if ((unit.length() <= 0) && (currentUnit.length() > 0)) {
                Uri dataUri = hasData(Constants.MIME_UNIT_NAME, contactId);
                if (dataUri != null) {
                    ops.add(ContentProviderOperation.newDelete(dataUri).build());
                }
            }
            // Case 3: add new
            else if ((unit.length() > 0) && (currentUnit.length() <= 0)) {
                /*
                // Add unit "combined name" only to DATA1, without breakdown
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValue(ContactsContract.Data.RAW_CONTACT_ID, contactId)
                .withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_UNIT_NAME)
                .withValue(ContactsContract.Data.DATA1, unit)
                .build());
                */

                ContentProviderOperation.Builder opUnitInsert = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
                opUnitInsert.withValue(ContactsContract.Data.RAW_CONTACT_ID, contactId);
                opUnitInsert.withValue(ContactsContract.Data.MIMETYPE, Constants.MIME_UNIT_NAME);
                opUnitInsert.withValue(ContactsContract.Data.DATA1, unit);

                String division = lw.getUnitDivision();
                if (division == null) division = "";
                if (division.length() > 0)
                    {
                        opUnitInsert.withValue(ContactsContract.Data.DATA2, division);
                    }

                String brigade = lw.getUnitBrigade();
                if (brigade == null) brigade = "";
                if (brigade.length() > 0)
                    {
                        opUnitInsert.withValue(ContactsContract.Data.DATA3, brigade);
                    }

                String battalion = lw.getUnitBattalion();
                if (battalion == null) battalion = "";
                if (battalion.length() > 0)
                    {
                        opUnitInsert.withValue(ContactsContract.Data.DATA4, battalion);
                    }

                String company = lw.getUnitCompany();
                if (company == null) company = "";
                if (company.length() > 0)
                    {
                        opUnitInsert.withValue(ContactsContract.Data.DATA5, company);
                    }

                String platoon = lw.getUnitPlatoon();
                if (platoon == null) platoon = "";
                if (platoon.length() > 0)
                    {
                        opUnitInsert.withValue(ContactsContract.Data.DATA6, platoon);
                    }

                String squad = lw.getUnitSquad();
                if (squad == null) squad = "";
                if (squad.length() > 0)
                    {
                        opUnitInsert.withValue(ContactsContract.Data.DATA7, squad);
                    }
                ops.add(opUnitInsert.build());
            }

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
    //========================================================
    /**
     * Add a new contact to the contacts storage provider.
     * 
     * Example usage:
     * 
     * <pre>
     * {@code
     * AmmoContacts.Contact contact = new AmmoContacts.Contact();
     * contact.setName("Foo");
     * contact.setLastName("Bar");
     * contact.setUserId("foo.bar");
     * Uri rval = ac.insertContactEntry(contact);
     * }
     * </pre>
     * 
     * @param lw A Contact object corresponding to the contact data to insert.
     * @return The URI of the inserted contact if successful, null otherwise.
     */
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
            // if (callsign != null && callsign.length() > 0) {
            //     displayName = callsign;
            // }
            // TBD SKN: Android Contacts requires displayNames to be unique, per new requirement callsigns are not unique
            //          map userid to display name
            //          tigrUserId MUST BE UNIQUE!!!!!!
            if (userId != null && userId.length() > 0) {
                displayName = userId;
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
    //========================================================
    /**
     * Delete an existing contact in the local contacts storage.
     * 
     * @param lw A Contact object corresponding to the contact data to remove.
     * 	        The userID will be used to find the existing contact. If found,
     *		the contact and all its data will be removed from the content provider.
     *		If NOT found, return null.
     * @return The (former) URI of the deleted contact if successful, null otherwise.
     */
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

        Uri uriToDelete = Uri.withAppendedPath(ContactsContract.RawContacts.CONTENT_URI,
                                               String.valueOf(contactId));

        // Make a list of ContentProviderOperations to delete this contact
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
                    if (Constants.MIME_BRANCH.equals(f.get("mimetype"))) {
                        lw.setBranch(f.get("data1") );
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
    /**
     * Get all the TA contacts stored in the content provider.
     * 
     * @return A list of Contact objects.
     */
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
    /**
     * Retrieve a contact key using its unique internal hashcode
     *
     */
    public Contact getContactByLookupKey(String lookupKey) {
        // Retrieve contact with provided uri
        Log.d(TAG,"getContactByLookupKey() ");

        ContentResolver cr = mResolver;

        Uri lookupUri = Uri.withAppendedPath(Contacts.CONTENT_LOOKUP_URI, lookupKey);

        Cursor c = cr.query(lookupUri, new String[]{Contacts.DISPLAY_NAME,Contacts._ID}, null,null,null);

        AmmoContacts.Contact lw = new AmmoContacts.Contact();
        lw.setLookup(lookupKey);
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

            // Retrieve lookup key
            String lookup = getLookupKeyForContact(contactId);
            lw.setLookup(lookup);

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
    // Get the lookup hash key for a user, given the contact_id
    // (i.e. contact_id as defined in provider's schema)
    //========================================================
    private String getLookupKeyForContact(String contactId) {
        Log.d(TAG, "getLookupKeyForContact: id=" + contactId);
        Cursor cLookup = null;
        String[] projection = {"lookup"};
        try {
            Uri lookupUri = Uri.parse("content://" + ContactsContract.AUTHORITY
                                      + "/contacts/" + contactId + "/data");
            cLookup = mResolver.query(lookupUri, projection, null, null, null);
            if (cLookup == null) {
                Log.e(TAG, "getLookupKeyForContact() -- cursor for lookup key is null");
                return null;
            }
        } catch (Throwable e) {
            Log.e(TAG, "Exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        try {
            String lookupKey = null;
            cLookup.moveToFirst();
            lookupKey = cLookup.getString(cLookup.getColumnIndex("lookup"));
            return lookupKey;
        } catch (Throwable e) {
            return null;
        } finally {
            cLookup.close();
        }
    }

    //========================================================
    // For debugging and testing purposes... Print some information about a cursor
    // to the log.
    //========================================================
    @SuppressWarnings("unused")
        private void examineCursor(Cursor cursor) {
        if (cursor != null) {
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
                }
            }
        }
    }

    //========================================================
    //
    // getContactByUserId()
    //
    //========================================================
    /**
     * Given a contact's username, retrieve other data for this
     * contact and return it in a Contact object.
     * 
     * @param userId The TIGR ID of the user to search for.
     * @return A Contact object containing the contact's available data 
     * 		if successful, null otherwise.
     */
    public Contact getContactByUserId(String userId) {
        Log.d(TAG,"getContactByUserId() ");

        Uri f = Uri.parse("content://" + ContactsContract.AUTHORITY
                          + "/data/userid/" + userId);
        Log.d(TAG, "  searching for uri = " + f.toString());

        Cursor cursor = null;
        try {
            String[] projection = {"contact_id", "lookup"};
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
            String contactIdStr = cursor.getString(cursor.getColumnIndex("contact_id"));

            AmmoContacts.Contact lw = new AmmoContacts.Contact();
            lw.setRawContactId(Integer.parseInt(contactIdStr));
            lw.setLookup(cursor.getString(cursor.getColumnIndex("lookup")));

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
    // hasData()
    //
    // Internal utility function to find if a given user has
    // existing data for a given mimetype.
    // Returns the URI of the data if it exists, null otherwise.
    //========================================================
    private Uri hasData(String mimeType, int contactId) {
        ContentResolver cr = mResolver;

        // The data URI for this contact
        Uri dataUri = Uri.withAppendedPath(Uri.withAppendedPath(Contacts.CONTENT_URI,
                                                                String.valueOf(contactId)), "data");
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.d(TAG, "hasData()  data uri = " + dataUri.toString());
        }

        //Uri dataUri = Data.CONTENT_URI;
        String[] projection = {Data._ID, Data.DATA1};
        String selection=Data.MIMETYPE+"=? AND " + Data.RAW_CONTACT_ID +"=?";
        String[] selectionArgs={mimeType, String.valueOf(contactId)};

        // Query the contacts content provider
        Cursor c = null;
        try {
            c = cr.query(dataUri, projection, selection, selectionArgs,null);
            if (c == null) {
                Log.e(TAG, "hasData() -- cursor is null");
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
            Log.d(TAG, "hasData() -- returned " + String.valueOf(count) + " rows");
            if (count < 1) {
                return null;
            }
            c.moveToFirst();
            int dataRowId = c.getInt(c.getColumnIndex(Data._ID));

            // If data was found, return the URI for the data row
            Uri foundDataUri = Uri.withAppendedPath(Data.CONTENT_URI, String.valueOf(dataRowId));

	    if (Log.isLoggable(TAG, Log.VERBOSE)) {
		Log.d(TAG, "hasData()    uri= " + foundDataUri.toString());
	    }
            return foundDataUri;
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

}

