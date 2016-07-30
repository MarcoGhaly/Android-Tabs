package com.marco.tabs.tabs;

import java.io.Serializable;

public class TabItem implements Serializable {

    private int iconID;
    private String text;


    // Constructors

    public TabItem() {
    }

    public TabItem(int iconID) {
        this.iconID = iconID;
    }

    public TabItem(String text) {
        this.text = text;
    }

    public TabItem(int iconID, String text) {
        this.iconID = iconID;
        this.text = text;
    }


    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
