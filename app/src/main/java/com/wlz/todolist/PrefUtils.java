package com.wlz.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by WaiLynnZaw on 2/13/15.
 */
public class PrefUtils {
    private static final String EMAIL = "email";
    private static final String AUTH = "auth";
    private static final String USER = "username";
    private static final String PREF_FIRST_TIME_CHECK = "pref_first_time_check";
    private static final String IS_LOGGED_IN = "is_member_flag";
    private static final String DATA = "data_loaded";
    private static final String ID = "id";
    private static final String SLUG_ARRAY = "slug_array";
    private static final String DB_SYNC = "sync_db";

    private static final String LAST_MESSAGE_SYNC_TIME = "last_message_time";
    private static final String SEARCH_QUERY = "search_query";
    private static final String SAVED_CAR = "saved_car";

    private static PrefUtils pref;
    protected SharedPreferences mSharedPreferences;
    protected SharedPreferences.Editor mEditor;
    protected Context mContext;

    public PrefUtils(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();
        this.mContext = context;
    }

    public static synchronized PrefUtils getInstance(Context mContext) {
        if (pref == null) {
            pref = new PrefUtils(mContext);
        }
        return pref;
    }

    public boolean isDbSync(){
        return mSharedPreferences.getBoolean(DB_SYNC,false);
    }
    public void setDbSync(){
        mEditor.putBoolean(DB_SYNC,true).commit();
    }
    public boolean isFirstTime() {
        return mSharedPreferences.getBoolean(PREF_FIRST_TIME_CHECK, true);
    }

    public void setNoLongerFirstTime() {
        mEditor.putBoolean(PREF_FIRST_TIME_CHECK, false).commit();
    }
    public void saveID(int id){mEditor.putInt(ID, id).commit();}
    public int getID(){return mSharedPreferences.getInt(ID, 0);}

    public boolean isDataLoaded(){
        return mSharedPreferences.getBoolean(DATA, false);
    }
    public void setDataAsLoaded(){
        mEditor.putBoolean(DATA, true)
                .commit();
    }
    public void saveEmail(String value) {
        mEditor.putString(EMAIL, value).commit();
    }
    public void saveAuth(String value){
        mEditor.putString(AUTH,value).commit();
    }
    public String getEmail() {
        return mSharedPreferences.getString(EMAIL, null);
    }
    public String getAuth(){
        return mSharedPreferences.getString(AUTH, null);
    }

    public boolean isLoggedIn(){
        return mSharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void setIsLoggedIn(boolean val){
        mEditor.putBoolean(IS_LOGGED_IN, val).commit();
    }


}
