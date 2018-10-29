package com.romanso.montyhallproblem.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.romanso.montyhallproblem.adapters.DoorClickListener;
import com.romanso.montyhallproblem.adapters.DoorsAdapter;
import com.romanso.montyhallproblem.game.Game;
import com.romanso.montyhallproblem.game.Round;
import com.romanso.montyhallproblem.montyhallproblem.R;
import com.romanso.montyhallproblem.pref.PrefActivity;
import com.romanso.montyhallproblem.util.Util;

public final class MainActivity extends AppCompatActivity implements DoorClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTvDoorsAmount;
    private SharedPreferences mSp;

    private Game mGame;
    private RecyclerView mDoorsRecycler;
    private DoorsAdapter mDoorsAdapter;

    private TextView mTvRoundN;
    private TextView mTvDoorChosen;
    private TextView mTvChangedChoices;
    private TextView mTvWinRate;
    private TextView mTvLoseRate;
    private TextView mTvInfo;

    private Button mNewGameBtn;
    private Button mNewRoundBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSp = PreferenceManager.getDefaultSharedPreferences(this);

        mNewGameBtn = findViewById(R.id.btnNewGame);
        mNewGameBtn.setOnClickListener((v) -> startNewGame(getDoorsAmountInt()));

        mNewRoundBtn = findViewById(R.id.btnNewRound);
        mNewRoundBtn.setOnClickListener((v) -> startNewRound());

        mTvRoundN = findViewById(R.id.tvRoundN);
        mTvDoorChosen = findViewById(R.id.tvDoorChosen);
        mTvChangedChoices = findViewById(R.id.tvChangedChoices);
        mTvWinRate = findViewById(R.id.tvWinRate);
        mTvLoseRate = findViewById(R.id.tvLoseRate);
        mTvInfo = findViewById(R.id.tvInfo);

        mTvDoorsAmount = findViewById(R.id.tvDoorsAmount);

        mDoorsAdapter = new DoorsAdapter(this, this);

        startNewGame(getDoorsAmountInt());

        mDoorsRecycler = findViewById(R.id.rvDoors);
        RecyclerView.LayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mDoorsRecycler.setLayoutManager(layoutManager);
        mDoorsRecycler.setAdapter(mDoorsAdapter);
    }

    @Override
    protected void onResume() {
        String doorsAmountString = getDoorsAmountString();
        int doorsAmount = Integer.valueOf(doorsAmountString);
        mTvDoorsAmount.setText(doorsAmountString);
        if (doorsAmount != mGame.getDoorsAmount()) {
            Log.i(TAG, "New doors amount set: " + doorsAmount + ". Starting new game. ");
            startNewGame(doorsAmount);
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi1 = menu.add(0, 1, 0, getString(R.string.preferences));
        mi1.setIntent(new Intent(this, PrefActivity.class));
        MenuItem mi2 = menu.add(0, 2, 0, getString(R.string.info));
        mi2.setIntent(new Intent(this, InfoActivity.class));
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("SetTextI18n")
    private void startNewGame(int doorsAmount) {
        mGame = new Game(doorsAmount);
        mTvRoundN.setText(Integer.toString(mGame.getTotalRounds()));
        mDoorsAdapter.setRound(mGame.getRound());
        mTvChangedChoices.setText("0");
        mTvWinRate.setText("0");
        mTvLoseRate.setText("0");
        mTvDoorChosen.setText("-");
        mTvInfo.setText(getString(R.string.which_door_has_a_prize));
    }

    private void startNewRound() {
        updateStats(mGame.newRound());
        mDoorsAdapter.setRound(mGame.getRound());
        mTvInfo.setText(getString(R.string.which_door_has_a_prize));
    }

    private int getDoorsAmountInt() {
        return Integer.valueOf(mSp.getString(PrefActivity.KEY_DOORS_AMOUNT, "3"));
    }

    private String getDoorsAmountString() {
        return mSp.getString(PrefActivity.KEY_DOORS_AMOUNT, "3");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void doorClicked(Game g) {
        mTvRoundN.setText(Integer.toString(g.getTotalRounds()));
        if (g.getRound().getStatus() != Round.STARTED) {
            startNewRound();
        } else {
            mTvDoorChosen.setText(Integer.toString(g.getRound().getChosenDoorId()));
            mTvInfo.setText(getString(R.string.choose_again));
        }
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void updateStats(boolean newRound) {

        mTvDoorChosen.setText("-");

        if (!newRound) {
            return;
        }

        int totalRounds = mGame.getTotalRounds() - 1;
        int wonGames = mGame.getRounds(Round.WON);
        int lostGames = totalRounds - wonGames;

        double winRate = (double) wonGames / totalRounds * 100;
        double loseRate = (double) lostGames / totalRounds * 100;

        mTvWinRate.setText(String.format("%d (%.3f)", wonGames, Util.round(winRate, 3)));
        mTvLoseRate.setText(String.format("%d (%.3f)", lostGames, Util.round(loseRate, 3)));

        mTvRoundN.setText(Integer.toString(mGame.getTotalRounds()));
        mTvChangedChoices.setText(Integer.toString(mGame.getChangedChoices()));
    }
}
