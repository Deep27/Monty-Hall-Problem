package com.romanso.montyhallproblem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.romanso.montyhallproblem.montyhallproblem.R;
import com.romanso.montyhallproblem.pref.PrefActivity;

public final class MainActivity extends AppCompatActivity {

    private TextView mTvDoorsAmount;
    private SharedPreferences mSp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvDoorsAmount = findViewById(R.id.tvDoorsAmount);
        mSp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume() {
        String doorsAmount = mSp.getString(PrefActivity.KEY_DOORS_AMOUNT, "3");
        mTvDoorsAmount.setText(doorsAmount);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi = menu.add(0, 1, 0, "Preferences");
        mi.setIntent(new Intent(this, PrefActivity.class));
        return super.onCreateOptionsMenu(menu);
    }
}
