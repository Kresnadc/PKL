package com.example.i14048.pkl.db;

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
    public static final String TABLE_ACCOUNT = "account";
    public static final String TABLE_TRANSACTION = "transaksi";
    public static final String COLUMN_NAME_EMAIL = "email";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_ADDRESS = "address";
    public static final String COLUMN_NAME_PHONE = "phone";
    public static final String COLUMN_NAME_BIRTHDAY = "birth";
    public static final String COLUMN_NAME_PRODUCT = "product";
    public static final String COLUMN_NAME_PRODUCTID = "product_id";
    public static final String COLUMN_NAME_QUANTITY = "kuantitas";


    private static final String CREATE_TABLE_ACCOUNT =
            "CREATE TABLE " + TABLE_ACCOUNT + " (" +
                    COLUMN_NAME_EMAIL + " TEXT PRIMARY KEY," +
                    COLUMN_NAME_NAME + " TEXT," +
                    COLUMN_NAME_ADDRESS + " TEXT," +
                    COLUMN_NAME_PHONE + " TEXT," +
                    COLUMN_NAME_BIRTHDAY + " TEXT," +
                    COLUMN_NAME_PRODUCT + " TEXT);";

    private static final String CREATE_TABLE_TRANSACTION =
            "CREATE TABLE " + TABLE_TRANSACTION + "(" +
                    COLUMN_NAME_PRODUCTID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_QUANTITY + " INTEGER)";

    private static final String SQL_DELETE_ACCOUNT =
            "DROP TABLE IF EXISTS " + TABLE_ACCOUNT;

    private static final String SQL_DELETE_TRANSACTION =
            "DROP TABLE IF EXISTS " + TABLE_TRANSACTION;

    public AccountDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_TRANSACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ACCOUNT);
        db.execSQL(SQL_DELETE_TRANSACTION);
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
        long newRowId = db.insert(TABLE_ACCOUNT, null, values);
        if(newRowId>0){
            return true;
        }else {
            return false;
        }
    }

    public List<ContentValues> showAllAccount(){
        // to do
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
                this.TABLE_ACCOUNT,                     // The table to query
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

        db.update(TABLE_ACCOUNT, values, selection, selectionArgs);
    }

    public boolean insertTransaction(String idProduct, int quantity) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_PRODUCTID, idProduct);
        values.put(COLUMN_NAME_QUANTITY, quantity);
        long newRowId = db.insert(TABLE_TRANSACTION, null, values);
        if(newRowId>0){
            return true;
        }else {
            return false;
        }
    }

    public ContentValues getTransactionById(String productId){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues TransactionInfo = new ContentValues();
        String[] projection = {
                COLUMN_NAME_PRODUCTID,
                COLUMN_NAME_QUANTITY
        };
        String selection = this.COLUMN_NAME_PRODUCTID + " = ?";
        String[] selectionArgs = { productId };

        Cursor cursor = db.query(
                this.TABLE_TRANSACTION,                     // The table to query
                projection,                          // The columns to return
                selection,                           // The columns for the WHERE clause
                selectionArgs,                       // The values for the WHERE clause
                null,                                // don't group the rows
                null,                                // don't filter by row groups
                null                                 // The sort order
        );

        if(cursor.moveToNext()){
            TransactionInfo.put(COLUMN_NAME_PRODUCTID, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCTID)));
            TransactionInfo.put(COLUMN_NAME_QUANTITY, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_QUANTITY)));
        }else{
            TransactionInfo = null;
        }
        cursor.close();
        return TransactionInfo;
    }

    public int updateTransactionById(String productId, int quantity){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_PRODUCTID, productId);
        values.put(COLUMN_NAME_QUANTITY, quantity);

        String selection = COLUMN_NAME_PRODUCTID + " = ?";
        String[] selectionArgs = {productId+""};

        int count = db.update(TABLE_TRANSACTION, values, selection, selectionArgs);
        return count;
    }

//    private Cursor getAccountCursor(String email) {
//        SQLiteDatabase db = getReadableDatabase();
//        String[] emailConstraint = {email};
//        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_EMAIL + " = ?", emailConstraint);
//    }
}
