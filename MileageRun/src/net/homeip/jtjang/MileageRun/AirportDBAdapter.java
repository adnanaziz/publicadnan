package net.homeip.jtjang.MileageRun;
/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AirportDBAdapter {

	public static final String KEY_ORIGIN = "ORIGIN";
	public static final String KEY_DEST = "DEST";
	public static final String KEY_DISTANCE = "DISTANCE";
	
	private static final String TAG = "AirportDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	/**
	 * Database creation sql statement
	 */
	private static final String DATABASE_CREATE =
		"CREATE TABLE DISTANCE (ORIGIN TEXT NOT NULL, DEST TEXT NOT NULL, " +
		"DISTANCE INTEGER NOT NULL, " +
		"PRIMARY KEY (ORIGIN, DEST))";

	private static final String DATABASE_NAME = "FLIGHT";
	private static final String DATABASE_TABLE = "DISTANCE";
	private static final int DATABASE_VERSION = 2;

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS DISTANCE");
			onCreate(db);
		}
	}

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx the Context within which to work
	 */
	public AirportDBAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	/**
	 * Open the airport database. If it cannot be opened, try to create a new
	 * instance of the database. If it cannot be created, throw an exception to
	 * signal the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException if the database could be neither opened or created
	 */
	public AirportDBAdapter open() throws SQLException {
		System.out.println("Open");
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
		mDb.close();
	}


	/**
	 * Store a new record using the data provided. If the record is successfully
	 * created return the new rowId for that record, otherwise return a -1 to
	 * indicate failure.
	 * @param origin From which airport
	 * @param dest To which airport
	 * @param distance Distance from origin to dest
	 * 
	 * @return rowId or -1 if failed
	 */
	public long createDistance(String origin, String dest, double distance) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ORIGIN, origin);
		initialValues.put(KEY_DEST, dest);
		initialValues.put(KEY_DISTANCE, distance);

		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}

	/**
	 * Delete the record with the given origin and dest
	 * 
	 * @param origin From which airport
	 * @param dest To which airport
	 * @return true if deleted, false otherwise
	 */
	public boolean deleteDistance(String origin, String dest) {

		return mDb.delete(DATABASE_TABLE,
				KEY_ORIGIN + "=" + origin + " AND " + KEY_DEST + "=" + dest, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all records in the database
	 * 
	 * @return Cursor over all records
	 */
	public Cursor fetchAllDistances() {

		return mDb.query(DATABASE_TABLE,
				new String[] {KEY_ORIGIN, KEY_DEST, KEY_DISTANCE},
				null, null, null, null, null);
	}

	
	/**
	 * Return the distance from the given origin and dest
	 * 
	 * @param origin From which airport
	 * @param dest To which airport
	 * @return A Cursor object pointing to the first row if found (there should be exact one row if found)
	 * @throws SQLException if record could not be found/retrieved
	 */
	public Cursor fetchDistance(String origin, String dest) throws SQLException {
		String query = "SELECT " + KEY_ORIGIN + ", " + KEY_DEST + ", " + KEY_DISTANCE +
			" FROM " + DATABASE_TABLE + " WHERE " +
			KEY_ORIGIN + " = \"" + origin + "\" AND " + KEY_DEST + " = \"" + dest + "\";";
		Cursor mCursor = mDb.rawQuery(query, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	
	/**
	 * Update the record using the details provided. The record to be updated is
	 * specified using the origin and dest, and it is altered to use the values
	 * passed in
	 * @param origin value to set date to
	 * @param dest value to set note title to
	 * @param distance value to set note body to
	 * 
	 * @return true if the note was successfully updated, false otherwise
	 */
	public boolean updateDistance(String origin, String dest, double distance) {
		ContentValues args = new ContentValues();
		args.put(KEY_ORIGIN, origin);
		args.put(KEY_DEST, dest);
		args.put(KEY_DISTANCE, distance);

		return mDb.update(DATABASE_TABLE, args,
				KEY_ORIGIN + "=" + origin + " AND " + KEY_DEST + "=" + dest, null) > 0;
	}
}
