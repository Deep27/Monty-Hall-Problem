package com.romanso.montyhallproblem.model;

public class Door {

    private int mId;
    private boolean mOpened;

    public Door(int id) {
        mId = id;
        mOpened = false;
    }

    public int getId() {
        return mId;
    }

    public boolean isOpened() {
        return mOpened;
    }

    public void setOpened(boolean mOpened) {
        this.mOpened = mOpened;
    }
}
