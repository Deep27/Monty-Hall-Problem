package com.romanso.montyhallproblem.game;

import android.util.Log;

import com.romanso.montyhallproblem.model.Door;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Round {

    private static final String TAG = Round.class.getSimpleName();

    public static final int STARTED = 0;
    public static final int WON = 1;
    public static final int LOST = 2;

    private Game mGame;

    private final List<Door> mDoors;
    private final int mPrizeDoorId;
    private int mChosenDoorId = -1;
    private int mAnotherDoor = -1;
    private boolean mChangedChoice;
    private int mStatus = STARTED;

    public Round(Game game, int doorsAmount) {
        mGame = game;
        int prizeDoorId = new Random().nextInt(doorsAmount);
        mDoors = new ArrayList<>(doorsAmount);
        for (int i = 0; i < doorsAmount; i++) {
            mDoors.add(new Door(i));
        }
        mPrizeDoorId = prizeDoorId;
        mDoors.get(mPrizeDoorId).setHasPrize(true);
        Log.i(TAG, "Prize door set: #" + mPrizeDoorId);
    }

    public int getChosenDoorId() {
        return mChosenDoorId;
    }

    public int getPrizeDoorId() {
        return mPrizeDoorId;
    }

    public int getStatus() {
        return mStatus;
    }

    public boolean isChoiceChanged() {
        return mChangedChoice;
    }

    public List<Door> getDoors() {
        return mDoors;
    }

    public Game getGame() {
        return mGame;
    }

    public int chooseDoor(int n) {
        if (mChosenDoorId == -1) {
            Log.i(TAG, "Player has chosen door #" + n + " for the first time.");
            mChosenDoorId = n;
            findAnotherDoor();
            removeExcessDoors();
        } else {
            if (n == mChosenDoorId) {
                Log.i(TAG, "Player has chosen the same door! #" + n + " (probably, someone has to get acquainted with probability theory)");
            } else {
                mChangedChoice = true;
                Log.i(TAG, "Player has changed the choice. Good move!");
            }

            if (n == mPrizeDoorId) {
                mStatus = WON;
                Log.i(TAG, "Player has won the round! Door #" + mChosenDoorId);
            } else {
                mStatus = LOST;
                Log.i(TAG, "Player has lost the round! Chosen door: " + n + ", door with prize: " + mPrizeDoorId);
            }
        }
        return mStatus;
    }

    private void findAnotherDoor() {

        if (mChosenDoorId != mPrizeDoorId) {
            Log.i(TAG, "Player has chosen door without prize (" + mChosenDoorId + "). Another door: " + mPrizeDoorId);
            mAnotherDoor = mPrizeDoorId;
        } else {
            Random r = new Random();
            do {
                mAnotherDoor = r.nextInt(mDoors.size());
            } while (mAnotherDoor == mPrizeDoorId);
            Log.i(TAG,"Player has chosen door with prize: " + mChosenDoorId + ". Another door: " + mAnotherDoor);
        }
    }

    private void removeExcessDoors() {
        mDoors.removeIf(door -> door.getId() != mAnotherDoor && door.getId() != mChosenDoorId);
        StringBuilder doorsLeft = new StringBuilder();
        for (Door d : mDoors) {
            doorsLeft.append(d.getId()).append(", ");
        }
        Log.i(TAG, "Removed all doors except 2: " + doorsLeft.toString());
    }
}
