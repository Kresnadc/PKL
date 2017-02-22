package com.example.i14048.pkl.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public KatalogDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
