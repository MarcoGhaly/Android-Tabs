package com.marco.tabs.tabs;

public interface TabsAdapter {

    int getCount();

    String getTitle(int position);

    int getIconResourceID(int position);

}
