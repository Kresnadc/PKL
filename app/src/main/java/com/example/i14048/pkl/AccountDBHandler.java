package com.example.i14048.pkl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by kresn on 2/19/2017.
 * Handler for Account Database
 */

public class AccountDBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AccountPKL.db";
    public static final String TABLE_NAME = "account";
    public static final String COLUMN_NAME_EMAIL = "email";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_ADDRESS = "address";
    public static final String COLUMN_NAME_PHONE = "phone";
    public static final String COLUMN_NAME_BIRTHDAY = "birth";
    public static final String COLUMN_NAME_PRODUCT = "product";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_EMAIL + " TEXT PRIMARY KEY," +
                    COLUMN_NAME_NAME + " TEXT," +
                    COLUMN_NAME_ADDRESS + " TEXT," +
                    COLUMN_NAME_PHONE + " TEXT," +
                    COLUMN_NAME_BIRTHDAY + " TEXT," +
                    COLUMN_NAME_PRODUCT + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public AccountDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insertNewAccoount(String email, String name, String address, String phone, String birthday, String product) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_EMAIL, email);
        values.put(COLUMN_NAME_NAME, name);
        values.put(COLUMN_NAME_ADDRESS, address);
        values.put(COLUMN_NAME_PHONE, phone);
        values.put(COLUMN_NAME_BIRTHDAY, birthday);
        values.put(COLUMN_NAME_PRODUCT, product);
        long newRowId = db.insert(TABLE_NAME, null, values);
        if(newRowId>0){
            return true;
        }else {
            return false;
        }
    }

    public List<ContentValues> showAllAccount(){
        return null;
    }

    public ContentValues getAccount(String email, String name){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues AccountInfo = new ContentValues();
        String[] projection = {
                COLUMN_NAME_EMAIL,
                COLUMN_NAME_NAME,
                COLUMN_NAME_ADDRESS,
                COLUMN_NAME_PHONE,
                COLUMN_NAME_BIRTHDAY,
                COLUMN_NAME_PRODUCT
        };
        String selection = this.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = { email };

        // How you want the results sorted in the resulting Cursor
        //String sortOrder = FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                this.TABLE_NAME,                     // The table to query
                projection,                          // The columns to return
                selection,                           // The columns for the WHERE clause
                selectionArgs,                       // The values for the WHERE clause
                null,                                // don't group the rows
                null,                                // don't filter by row groups
                null                                 // The sort order
        );

        if(cursor.moveToNext()){
            AccountInfo.put(COLUMN_NAME_EMAIL, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_EMAIL)));
            AccountInfo.put(COLUMN_NAME_NAME, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)));
            AccountInfo.put(COLUMN_NAME_ADDRESS, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ADDRESS)));
            AccountInfo.put(COLUMN_NAME_PHONE, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PHONE)));
            AccountInfo.put(COLUMN_NAME_BIRTHDAY, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_BIRTHDAY)));
            AccountInfo.put(COLUMN_NAME_PRODUCT, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCT)));
        }
        cursor.close();
        return AccountInfo;
    }

    public void updatePkl(String email, String name, String address, String phone, String birthday, String product) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_NAME, name);
        values.put(COLUMN_NAME_ADDRESS, address);
        values.put(COLUMN_NAME_PHONE, phone);
        values.put(COLUMN_NAME_BIRTHDAY, birthday);
        values.put(COLUMN_NAME_PRODUCT, product);

        String selection = COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = {email};

        db.update(TABLE_NAME, values, selection, selectionArgs);
    }

    private Cursor getAccountCursor(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String[] emailConstraint = {email};
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_EMAIL + " = ?", emailConstraint);
    }
}
