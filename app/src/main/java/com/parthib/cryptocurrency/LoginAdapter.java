package com.parthib.cryptocurrency;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class LoginAdapter extends FragmentStatePagerAdapter {

    private final int NumberOfTabs;
    int mNoOfTabs;



    public LoginAdapter(FragmentManager supportFragmentManager, int tabCount, int numberOfTabs) {
        super(supportFragmentManager);
        NumberOfTabs = numberOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            //Slide to Tab1
            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            //Slide to tab2
            case 1:
                Tab2 tab2 = new Tab2();
                return tab2;

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
