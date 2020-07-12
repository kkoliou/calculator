package com.android.calculator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

    int counter;

    public PageAdapter(@NonNull FragmentManager fm, int counter) {
        super(fm);
        this.counter = counter;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                CalculatorFrag calc = new CalculatorFrag();
                return calc;
            case 1:
                CurrencyFrag curr = new CurrencyFrag();
                return curr;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return counter;
    }
}
