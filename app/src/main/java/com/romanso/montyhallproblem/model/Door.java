package com.romanso.montyhallproblem.model;

public final class Door {

    private final int mId;
    private boolean mHasPrize;

    public Door(int id) {
        mId = id;
        mHasPrize = false;
    }

    @Override
    public int hashCode() {
        return mId;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Door) && ((Door) obj).mId == mId;
    }

    public int getId() {
        return mId;
    }

    public boolean hasPrize() {
        return mHasPrize;
    }

    public void setHasPrize(boolean hasPrize) {
        mHasPrize = hasPrize;
    }
}
