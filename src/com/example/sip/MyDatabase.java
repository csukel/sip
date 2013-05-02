package com.example.sip;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * This class makes use of a third party library which uses the SQLite classes and supports
 * reading databases bigger than the maximum file size for the assets folder.
 * @author Loucas Stylianou
 *
 */
public class MyDatabase extends SQLiteAssetHelper {
	/**
	 * The name of the database file
	 */
	private static final String DATABASE_NAME = "db";
	/**
	 * The database version
	 */
	private static final int DATABASE_VERSION = 1;

	/**
	 * The class constructor
	 * @param context
	 */
	public MyDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}
	
	/**
	 * This method queries the sql database and gets the boundaries for a specific borough given its id
	 * @param id The borough's id
	 * @return A cursor containing the matched results
	 */
	public Cursor getBoundaries(int id){
		
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT _id,latitude,longitude from boundaries_table where borough_id ="+id
				,new String[]{});
		cur.moveToFirst();   
		return cur;
		
	}
	
	/**
	 * This method queries the database and gets all the boroughs data from the table
	 * @return A cursor with all the boroughs table data
	 */
	public Cursor getAllBoroughs() {
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT * from borough_table",new String[]{});
		cur.moveToFirst();   
		return cur;
	}
	
	/**
	 * This method queries the database and gets all data stored in the schools table in the database
	 * @return A cursor with the data of all schools
	 */
	public Cursor getAllSchools() {
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT * from school_table",new String[]{});
		cur.moveToFirst();   
		return cur;
	}
}
