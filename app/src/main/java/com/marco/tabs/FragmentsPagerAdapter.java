package com.marco.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentsPagerAdapter extends FragmentStatePagerAdapter {

    private String[] titles;

    private ContentFragment[] contentFragments;


    // Constructor
    public FragmentsPagerAdapter(FragmentManager fragmentManager, String[] titles) {
        super(fragmentManager);

        this.titles = titles;
        contentFragments = new ContentFragment[titles.length];
    }


    @Override
    public int getCount() {
        return titles.length;
    }


    @Override
    public Fragment getItem(int position) {
        if (contentFragments[position] == null) {
            contentFragments[position] = ContentFragment.newInstance(titles[position]);
        }

        return contentFragments[position];
    }

}
