package com.romanso.montyhallproblem.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.romanso.montyhallproblem.model.Door;
import com.romanso.montyhallproblem.montyhallproblem.R;

import java.util.List;

public final class DoorsAdapter extends RecyclerView.Adapter<DoorsAdapter.DoorsViewHolder> {

    private List<Door> mDoorsList;

    public DoorsAdapter(List<Door> doorsList) {
        mDoorsList = doorsList;
    }

    @NonNull
    @Override
    public DoorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.door, parent, false);

        return new DoorsViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DoorsViewHolder doorViewHolder, int i) {
        Door door = mDoorsList.get(i);
        doorViewHolder.getBtn().setText(Integer.toString(door.getId() + 1));
    }

    @Override
    public int getItemCount() {
        return mDoorsList.size();
    }

    public final class DoorsViewHolder extends RecyclerView.ViewHolder {

        private Button mBtn;

        public DoorsViewHolder(@NonNull View itemView) {
            super(itemView);
            mBtn = (Button) itemView;
        }

        public Button getBtn() {
            return mBtn;
        }
    }
}
