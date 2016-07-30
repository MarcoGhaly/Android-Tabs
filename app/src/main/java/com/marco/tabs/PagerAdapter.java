package com.marco.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.marco.tabs.tabs.TabItem;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private TabItem[] tabItems;
    private ContentFragment[] contentFragments;


    // Constructor
    public PagerAdapter(FragmentManager fragmentManager, TabItem[] tabItems) {
        super(fragmentManager);

        this.tabItems = tabItems;
        contentFragments = new ContentFragment[tabItems.length];
    }


    @Override
    public Fragment getItem(int position) {
        if (contentFragments[position] == null) {
            TabItem tabItem = tabItems[position];
            contentFragments[position] = ContentFragment.newInstance(tabItem.getIconID(), tabItem.getText());
        }

        return contentFragments[position];
    }

    @Override
    public int getCount() {
        return tabItems.length;
    }

}
