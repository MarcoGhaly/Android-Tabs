package com.marco.tabs;

import com.marco.tabs.tabs.TabsAdapter;

public class CustomTabsAdapter extends TabsAdapter {

    private String[] titles;
    private int[] iconsResourceIDs;


    // Constructor
    public CustomTabsAdapter(String[] titles, int[] iconsResourceIDs) {
        this.titles = titles;
        this.iconsResourceIDs = iconsResourceIDs;
    }


    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public String getText(int position) {
        return titles[position];
    }

    @Override
    public int getIconResourceID(int position) {
        return iconsResourceIDs[position];
    }

}
