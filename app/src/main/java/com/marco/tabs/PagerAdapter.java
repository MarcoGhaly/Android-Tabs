package com.marco.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private String[] titles;
    private int[] iconsResourceIDs;

    private ContentFragment[] contentFragments;


    // Constructor
    public PagerAdapter(FragmentManager fragmentManager, String[] titles, int[] iconsResourceIDs) {
        super(fragmentManager);

        this.titles = titles;
        this.iconsResourceIDs = iconsResourceIDs;
        contentFragments = new ContentFragment[titles.length];
    }


    @Override
    public int getCount() {
        return titles.length;
    }


    @Override
    public Fragment getItem(int position) {
        if (contentFragments[position] == null) {
            contentFragments[position] = ContentFragment.newInstance(iconsResourceIDs[position], titles[position]);
        }

        return contentFragments[position];
    }

}
