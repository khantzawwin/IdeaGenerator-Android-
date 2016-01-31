package com.khantzawwin.ideagenerator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by khantzawwin on 30/1/16.
 */
public class PageAdpater extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdpater(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TodayIdeasTabFragement tab1 = new TodayIdeasTabFragement();
                return tab1;
            case 1:
                MyIdeasTabFragement tab2 = new MyIdeasTabFragement();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
