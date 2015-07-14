package com.example.aaswaas;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "aasWaas";

	// Contacts table name
	private static final String TABLE_CONTACTS = "contacts";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_PH_NO = "phone_number";

	// User table name
	private static final String TABLE_USER = "user";
	
	// User Table Columns names
	private static final String KEY_UID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_UPH_NO = "phone_number";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_BLOODGRP = "bloodgrp";
	private static final String KEY_IMAGE = "image";
	

	// User table message
	private static final String TABLE_MSG = "message";
	
	// User Table Columns names
	// User Table Columns names
	private static final String KEY_MID = "id";
	private static final String KEY_MSG1 = "msg1";
	private static final String KEY_MSG2 = "msg2";
	

	public DbHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_PH_NO + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
		
		String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_UID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_UPH_NO + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_BLOODGRP + " TEXT" + KEY_IMAGE + " BLOB" +")";
		db.execSQL(CREATE_USER_TABLE);
		
		String CREATE_MSG_TABLE = "CREATE TABLE " + TABLE_MSG + "("
				+ KEY_MID + " INTEGER PRIMARY KEY," + KEY_MSG1 + " TEXT," + KEY_MSG2 + " TEXT" + ")";
		db.execSQL(CREATE_MSG_TABLE);
		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MSG);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	Contact getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				 KEY_PH_NO }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Contact contact = new Contact(Integer.parseInt(cursor.getString(0))
				, cursor.getString(1));
		// return contact
		return contact;
	}
	
	// Getting All Contacts
	public List<Contact> getAllContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				
				contact.setPhoneNumber(cursor.getString(1));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}
	
	
	// Getting All Contacts return cursor
		public Cursor getContacts() {
			
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			 
			return cursor; 
		}

	// Updating single contact
	public int updateContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(KEY_PH_NO, contact.getPhoneNumber());

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
	}

	// Deleting single contact
	public void deleteContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
		db.close();
	}

	// Deleting single contact a/c to id
	public void deleteCon(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
	
	//userinfo ko lagi
	
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new user
	void addUserInfo(UserInfo user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, user.getName());
		values.put(KEY_UPH_NO, user.getPhoneNumber()); // Contact Phone
		values.put(KEY_ADDRESS, user.getAddress());
		values.put(KEY_BLOODGRP, user.getBloodGrp());
		

		// Inserting Row
		db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection
	}

	
	// Updating User
	public int updateUser(UserInfo user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, user.getName());
		values.put(KEY_UPH_NO, user.getPhoneNumber()); // Contact Phone
		values.put(KEY_ADDRESS, user.getAddress());
		values.put(KEY_BLOODGRP, user.getBloodGrp());

		// updating row
		return db.update(TABLE_USER, values, KEY_ID + " = ?",
				new String[] { String.valueOf(1) });
	}


	// Getting All User
		public List<UserInfo> getAllUser() {
			List<UserInfo> UserList = new ArrayList<UserInfo>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_USER;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					UserInfo user = new UserInfo();
					user.setID(Integer.parseInt(cursor.getString(0)));
					user.setName(cursor.getString(1));
					user.setPhoneNumber(cursor.getString(2));
					user.setAddress(cursor.getString(3));
					user.setBloodGrp(cursor.getString(4));
					//user.setImage(cursor.getBlob(5));
					// Adding contact to list
					UserList.add(user);
				} while (cursor.moveToNext());
			}

			// return contact list
			return UserList;
		}
		
		

		
		// Getting user Count
		public int getuserCount() {
			String countQuery = "SELECT  * FROM " + TABLE_USER;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(countQuery, null);
			cursor.close();

			// return count
			return cursor.getCount();
		}

		
		// Updating Image
		public int updateImage(UserInfo user) {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(KEY_IMAGE, user.getImage());
			
			// updating row
			return db.update(TABLE_USER, values, KEY_ID + " = ?",
					new String[] { String.valueOf(1) });
		}
		
		// Getting Image
		public List<UserInfo> getImage() {
			List<UserInfo> UserList = new ArrayList<UserInfo>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_USER;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					UserInfo user = new UserInfo();
					user.setImage(cursor.getBlob(5));
					// Adding contact to list
					UserList.add(user);
				} while (cursor.moveToNext());
			}

			// return contact list
			return UserList;
		}

		
		//message ko lagi
		
		/**
		 * All CRUD(Create, Read, Update, Delete) Operations
		 */

		// Adding new user
		void addMessage(message msg) {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(KEY_MSG1, msg.getMessage1());
			values.put(KEY_MSG2, msg.getMessage2());

			// Inserting Row
			db.insert(TABLE_MSG, null, values);
			db.close(); // Closing database connection
		}

		
		// Updating User
		public int updateMsg(message msg) {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(KEY_MSG1, msg.getMessage1());
			values.put(KEY_MSG2, msg.getMessage2());
	

			// updating row
			return db.update(TABLE_MSG, values, KEY_ID + " = ?",
					new String[] { String.valueOf(1) });
		}


		// Getting All Msg
			public List<message> getAllMsg() {
				List<message> MsgList = new ArrayList<message>();
				// Select All Query
				String selectQuery = "SELECT  * FROM " + TABLE_MSG;

				SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						message msg = new message();
						msg.setID(Integer.parseInt(cursor.getString(0)));
						msg.setMessage1(cursor.getString(1));
						msg.setMessage2(cursor.getString(2));
						
						//user.setImage(cursor.getBlob(5));
						// Adding contact to list
						MsgList.add(msg);
					} while (cursor.moveToNext());
				}

				// return contact list
				return MsgList;
			}
}

