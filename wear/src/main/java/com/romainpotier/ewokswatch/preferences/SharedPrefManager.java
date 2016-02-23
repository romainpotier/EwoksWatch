package com.romainpotier.ewokswatch.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.romainpotier.ewokswatch.R;

public class SharedPrefManager {

    private static final String PREF_FILE = "ewoks_watch";

    private static final String REFRESH_TIME = "refresh_time";
    private static final String TIME_FORMAT_24 = "time_format_24";
    private static final String DATE_FORMAT = "date_format";
    private static final String BURN_MODE = "burn_mode";
    private static final String EWOK_FIXED = "ewok_fixed";

    private static final long DEFAULT_REFRESH_TIME = 1000 * 60 * 5; // each 3 hours

    private static SharedPrefManager sInstance;

    private SharedPreferences mSharedPreferences;

    private SharedPrefManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
    }

    public static SharedPrefManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPrefManager(context.getApplicationContext());
        }
        return sInstance;
    }

    public void setRefreshTime(long refreshTime) {
        final SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putLong(REFRESH_TIME, refreshTime);
        edit.commit();
    }

    public long getRefreshTime() {
        return mSharedPreferences.getLong(REFRESH_TIME, DEFAULT_REFRESH_TIME);
    }

    public void setTimeFormat24(boolean timeFormat24) {
        final SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean(TIME_FORMAT_24, timeFormat24);
        edit.commit();
    }

    public boolean getTimeFormat24() {
        return mSharedPreferences.getBoolean(TIME_FORMAT_24, true);
    }

    public void setBurnMode(boolean burnMode) {
        final SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean(BURN_MODE, burnMode);
        edit.commit();
    }

    public boolean getBurnMode() {
        return mSharedPreferences.getBoolean(BURN_MODE, false);
    }

    public void setDateFormat(String dateFormat) {
        final SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(DATE_FORMAT, dateFormat);
        edit.commit();
    }

    public String getDateFormat() {
        return mSharedPreferences.getString(DATE_FORMAT, "");
    }

    public int getEwokFixed() {
        return mSharedPreferences.getInt(EWOK_FIXED, -1);
    }

    public void setEwokFixed(int ewokFixed) {
        final SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putInt(EWOK_FIXED, ewokFixed);
        edit.commit();
    }

}
