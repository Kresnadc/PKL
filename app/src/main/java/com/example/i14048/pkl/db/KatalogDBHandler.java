package com.example.i14048.pkl.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by kresn on 2/22/2017.
 */

public class KatalogDBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "KatalogPKL.db";
    public static final String TABLE_NAME = "account";
    public static final String COLUMN_NAME_PRODUCTID = "product_id";
    public static final String COLUMN_NAME_ACCOUNTNAME = "account_name";
    public static final String COLUMN_NAME_PRODUCTNAME = "product_name";
    public static final String COLUMN_NAME_BASEPRICE = "base_price";
    public static final String COLUMN_NAME_SELLPRICE = "sell_price";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_PRODUCTID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_ACCOUNTNAME + " TEXT," +
                    COLUMN_NAME_PRODUCTNAME + " TEXT," +
                    COLUMN_NAME_BASEPRICE + " INTEGER," +
                    COLUMN_NAME_SELLPRICE +" INTEGER)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public KatalogDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.testInsert(); // DELETE JIKA TIDAK PERLU
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insertKatalog(String accountName, String productName, int basePrice, int sellPrice) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ACCOUNTNAME, accountName);
        values.put(COLUMN_NAME_PRODUCTNAME, productName);
        values.put(COLUMN_NAME_BASEPRICE, basePrice);
        values.put(COLUMN_NAME_SELLPRICE, sellPrice);
        long newRowId = db.insert(TABLE_NAME, null, values);
        if(newRowId>0){
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<ContentValues> getKatalog(String accountName){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ContentValues> katalogsInfo = new ArrayList<ContentValues>();
        String[] projection = {
                COLUMN_NAME_PRODUCTID,
                COLUMN_NAME_ACCOUNTNAME,
                COLUMN_NAME_PRODUCTNAME,
                COLUMN_NAME_BASEPRICE,
                COLUMN_NAME_SELLPRICE,
        };
        
        String selection = this.COLUMN_NAME_ACCOUNTNAME + " = ?";
        String[] selectionArgs = { accountName };

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

        while(cursor.moveToNext()){
            ContentValues katalogInfo = new ContentValues();
            katalogInfo.put(COLUMN_NAME_PRODUCTID, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCTID)));
            katalogInfo.put(COLUMN_NAME_ACCOUNTNAME, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ACCOUNTNAME)));
            katalogInfo.put(COLUMN_NAME_PRODUCTNAME, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCTNAME)));
            katalogInfo.put(COLUMN_NAME_BASEPRICE, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_BASEPRICE)));
            katalogInfo.put(COLUMN_NAME_SELLPRICE, cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SELLPRICE)));
            katalogsInfo.add(katalogInfo);
        }
        cursor.close();
        return katalogsInfo;
    }

    public void updateKatalog(int productId, String accountName, String productName, int basePrice, int sellPrice) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_PRODUCTID, productId);
        values.put(COLUMN_NAME_ACCOUNTNAME, accountName);
        values.put(COLUMN_NAME_PRODUCTNAME, productName);
        values.put(COLUMN_NAME_BASEPRICE, basePrice);
        values.put(COLUMN_NAME_SELLPRICE, sellPrice);

        String selection = COLUMN_NAME_PRODUCTNAME + " = ?";
        // UPDATE FAILURE INTEGER
        String[] selectionArgs = {productName};

        db.update(TABLE_NAME, values, selection, selectionArgs);
    }

    /**
     * Tester Insert
     */
    public void testInsert(){
//        this.insertKatalog("kresna","Kopikap", 3000, 5000);
//        this.insertKatalog("kresna","Coca-Cola", 3000, 5000);
    }
}
