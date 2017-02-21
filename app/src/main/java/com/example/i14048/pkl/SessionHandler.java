package com.example.i14048.pkl;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kresn on 2/20/2017.
 */

public class SessionHandler {
    private static final String SESSION_NAME = "AccountSession";
    private static final String IS_LOGIN = "isLogin";

    public static void loginSession(Context context, ContentValues account){
        SharedPreferences sharedPref = context.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(AccountDBHandler.COLUMN_NAME_EMAIL, account.getAsString(AccountDBHandler.COLUMN_NAME_EMAIL));
        editor.putString(AccountDBHandler.COLUMN_NAME_NAME, account.getAsString(AccountDBHandler.COLUMN_NAME_NAME));
        editor.putString(AccountDBHandler.COLUMN_NAME_ADDRESS, account.getAsString(AccountDBHandler.COLUMN_NAME_ADDRESS));
        editor.putString(AccountDBHandler.COLUMN_NAME_PHONE, account.getAsString(AccountDBHandler.COLUMN_NAME_PHONE));
        editor.putString(AccountDBHandler.COLUMN_NAME_BIRTHDAY, account.getAsString(AccountDBHandler.COLUMN_NAME_BIRTHDAY));
        editor.putString(AccountDBHandler.COLUMN_NAME_PRODUCT, account.getAsString(AccountDBHandler.COLUMN_NAME_PRODUCT));
        editor.commit();
    }

    public static ContentValues getActiveSession(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean(IS_LOGIN, false);
        if (isLoggedIn) {
            ContentValues accountSession = new ContentValues();
            accountSession.put(AccountDBHandler.COLUMN_NAME_EMAIL, sharedPref.getString(AccountDBHandler.COLUMN_NAME_EMAIL,""));
            accountSession.put(AccountDBHandler.COLUMN_NAME_NAME, sharedPref.getString(AccountDBHandler.COLUMN_NAME_NAME, ""));
            accountSession.put(AccountDBHandler.COLUMN_NAME_ADDRESS,  sharedPref.getString(AccountDBHandler.COLUMN_NAME_ADDRESS, ""));
            accountSession.put(AccountDBHandler.COLUMN_NAME_PHONE,  sharedPref.getString(AccountDBHandler.COLUMN_NAME_PHONE, ""));
            accountSession.put(AccountDBHandler.COLUMN_NAME_BIRTHDAY,  sharedPref.getString(AccountDBHandler.COLUMN_NAME_BIRTHDAY, ""));
            accountSession.put(AccountDBHandler.COLUMN_NAME_PRODUCT,  sharedPref.getString(AccountDBHandler.COLUMN_NAME_PRODUCT, ""));
            return accountSession;
        } else {
            return null;
        }
    }

    public static boolean checkSession(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean(IS_LOGIN, false);
        if(isLoggedIn){
            return true;
        }else{
            return false;
        }
    }

    public static void logout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
