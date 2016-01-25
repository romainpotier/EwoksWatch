package fr.romainpotier.ewokswatch.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String PREF_FILE = "ewoks_watch";

    private static final String REFRESH_TIME = "refresh_time";

    private static final long DEFAULT_REFRESH_TIME = 1000 * 3600 * 3; // each 3 hours

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

}
