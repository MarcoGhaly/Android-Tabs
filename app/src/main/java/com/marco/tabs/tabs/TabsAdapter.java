package com.marco.tabs.tabs;

public interface TabsAdapter {

    int getCount();

    String getText(int position);

    int getIconResourceID(int position);

}
