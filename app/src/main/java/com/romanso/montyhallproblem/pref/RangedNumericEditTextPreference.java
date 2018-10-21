package com.romanso.montyhallproblem.pref;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import com.romanso.montyhallproblem.montyhallproblem.R;

public final class RangedNumericEditTextPreference extends EditTextPreference {

    private static final String TAG = RangedNumericEditTextPreference.class.getSimpleName();

    private final int mMinValue;
    private final int mMaxValue;

    public RangedNumericEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.com_romanso_montyhallproblem_pref_RangedNumericEditTextPreference);

        mMinValue = a.getInteger(
                R.styleable.com_romanso_montyhallproblem_pref_RangedNumericEditTextPreference_minValue,
                getContext().getResources().getInteger(R.integer.minDoorsAmount));
        mMaxValue = a.getInteger(
                R.styleable.com_romanso_montyhallproblem_pref_RangedNumericEditTextPreference_maxValue,
                getContext().getResources().getInteger(R.integer.maxDoorsAmount)
        );
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        RangedNumericEditTextPreference editText =
                ((RangedNumericEditTextPreference) findPreferenceInHierarchy("key_doors_amount"));

        int doorsAmount = Integer.valueOf(editText.getText());
        if (!isDoorsAmountCorrect(doorsAmount)) {
            Toast.makeText(getContext(),
                    String.format("Doors amount should be in range [%d:%d]", mMinValue, mMaxValue),
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG,"Wrong amount of doors was chosen: " + doorsAmount);

            editText.setText(Integer.toString(mMinValue));
        }
    }

    private boolean isDoorsAmountCorrect(int doorsAmount) {
        return doorsAmount >= mMinValue && doorsAmount <= mMaxValue;
    }
}
