package com.romanso.montyhallproblem.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.romanso.montyhallproblem.game.Round;
import com.romanso.montyhallproblem.model.Door;
import com.romanso.montyhallproblem.montyhallproblem.R;

public final class DoorsAdapter extends RecyclerView.Adapter<DoorsAdapter.DoorsViewHolder> {

    private final Context mContext;
    private final DoorClickListener mDoorClickListener;

    private Round mRound;

    public DoorsAdapter(Context context, DoorClickListener doorClickListener) {
        mContext = context;
        mDoorClickListener = doorClickListener;
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
        Door door = mRound.getDoors().get(i);
        Button btn = doorViewHolder.getBtn();
        btn.setText(Integer.toString(door.getId()));
    }

    @Override
    public int getItemCount() {
        return mRound.getDoors().size();
    }

    public void setRound(Round r) {
        mRound = r;
        notifyDataSetChanged();
    }

    final class DoorsViewHolder extends RecyclerView.ViewHolder {

        private Button mBtn;

        public DoorsViewHolder(@NonNull View itemView) {
            super(itemView);
            mBtn = (Button) itemView;
            mBtn.setOnClickListener(mDoorClickHandler);
        }

        public Button getBtn() {
            return mBtn;
        }

        private View.OnClickListener mDoorClickHandler = (v) -> {
            Button b = (Button) v;
            mRound.chooseDoor(Integer.parseInt(b.getText().toString()));
            mDoorClickListener.doorClicked(DoorsAdapter.this.mRound.getGame());
            if (mRound.getStatus() != Round.STARTED) {

            } else {
                b.setText(mContext.getResources().getString(R.string.app_name));
                notifyDataSetChanged();
            }
        };
    }
}
