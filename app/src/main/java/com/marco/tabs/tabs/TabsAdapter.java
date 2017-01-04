package com.marco.tabs.tabs;

public abstract class TabsAdapter {

    public abstract int getCount();

    public abstract String getText(int position);

    public int getIconResourceID(int position) {
        return 0;
    }

}
