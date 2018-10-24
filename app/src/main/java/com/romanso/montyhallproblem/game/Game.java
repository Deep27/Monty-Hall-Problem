package com.romanso.montyhallproblem.game;

import java.util.ArrayList;
import java.util.List;

// @TODO implement different strategies of the game
public final class Game {

    private static final String TAG = Game.class.getSimpleName();

    private final int mDoorsAmount;
    private List<Round> mRounds;

    public Game(int doorsAmount) {
        mDoorsAmount = doorsAmount;
        mRounds = new ArrayList<>();
        mRounds.add(new Round(this, doorsAmount));
    }

    public Round getRound() {
        return mRounds.get(mRounds.size() - 1);
    }

    public boolean newRound() {
        if (getRound().getStatus() == Round.STARTED) {
            mRounds.set(mRounds.size() - 1, new Round(this, mDoorsAmount));
            return false;
        } else {
            mRounds.add(new Round(this, mDoorsAmount));
            return true;
        }
    }

    public int getDoorsAmount() {
        return mDoorsAmount;
    }

    public int getTotalRounds() {
        return mRounds.size();
    }

    public int getRounds(int roundState) {
        return (int) mRounds.stream().filter(r -> r.getStatus() == roundState).count();
    }

    public int getChangedChoices() {
        return (int) mRounds.stream().filter(Round::isChoiceChanged).count();
    }
}
