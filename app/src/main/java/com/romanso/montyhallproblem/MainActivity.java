package com.romanso.montyhallproblem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.romanso.montyhallproblem.adapters.DoorsAdapter;
import com.romanso.montyhallproblem.model.Door;
import com.romanso.montyhallproblem.montyhallproblem.R;
import com.romanso.montyhallproblem.pref.PrefActivity;

import java.util.ArrayList;
import java.util.List;

public final class MainActivity extends AppCompatActivity {

    private TextView mTvDoorsAmount;
    private SharedPreferences mSp;

    private List<Door> mDoorsList;
    private RecyclerView mDoorsRecycler;
    private DoorsAdapter mDoorsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvDoorsAmount = findViewById(R.id.tvDoorsAmount);
        mSp = PreferenceManager.getDefaultSharedPreferences(this);

        mDoorsList = new ArrayList<>(getDoorsAmountInt());
        init(getDoorsAmountInt());

        mDoorsRecycler = findViewById(R.id.rvDoors);
        mDoorsAdapter = new DoorsAdapter(mDoorsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mDoorsRecycler.setLayoutManager(layoutManager);
        mDoorsRecycler.setAdapter(mDoorsAdapter);
    }

    @Override
    protected void onResume() {
        String doorsAmountString = mSp.getString(PrefActivity.KEY_DOORS_AMOUNT, "3");
        int doorsAmount = Integer.valueOf(doorsAmountString);
        mTvDoorsAmount.setText(doorsAmountString);
        if (doorsAmount != mDoorsList.size()) {
            init(doorsAmount);
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi = menu.add(0, 1, 0, "Preferences");
        mi.setIntent(new Intent(this, PrefActivity.class));
        return super.onCreateOptionsMenu(menu);
    }

    private void init(int doorsAmount) {
        mDoorsList.clear();
        for (int i = 0; i < doorsAmount; i++) {
            mDoorsList.add(new Door(i));
        }
    }

    private int getDoorsAmountInt() {
        return Integer.valueOf(mSp.getString(PrefActivity.KEY_DOORS_AMOUNT, "3"));
    }

    private String getDoorsAmountString() {
        return mSp.getString(PrefActivity.KEY_DOORS_AMOUNT, "3");
    }
}
