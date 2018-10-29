package com.romanso.montyhallproblem.pref;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.romanso.montyhallproblem.montyhallproblem.R;


/**
 * @TODO:
 * - add toolbar with back button
 * - fix pref.xml to show min and max values of doors in android:summary
 */
public final class PrefActivity extends PreferenceActivity {

    private static final String TAG = PrefActivity.class.getSimpleName();

    public static final String KEY_DOORS_AMOUNT = "key_doors_amount";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }
}
