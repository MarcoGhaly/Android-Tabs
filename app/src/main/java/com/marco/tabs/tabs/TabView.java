package com.marco.tabs.tabs;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

class TabView implements View.OnClickListener {

    // On Tab Pressed Listener
    interface OnTabPressedListener {
        void onTabPressed(TabView tabView);
    }


    private AtomicInteger atomicInteger = new AtomicInteger(1);

    private Context context;
    private TabItem tabItem;
    private int tabWidth;
    private int textSize;
    private int textColor;
    private float selectorWeight;
    private int selectorHeight;
    private int selectorColor;

    private int[] iconMargins;
    private int[] textMargins;
    private int[] selectorMargins;

    private TabView.OnTabPressedListener onTabPressedListener;

    private RelativeLayout relativeLayout_tab;
    private ImageView imageView_icon;
    private TextView textView_title;
    private View selector;


    // Constructor
    public TabView(Context context, TabItem tabItem, int tabWidth, int textSize, int textColor,
                   float selectorWeight, int selectorHeight, int selectorColor, int[] iconMargins,
                   int[] textMargins, int[] selectorMargins) {
        this.context = context;
        this.tabItem = tabItem;
        this.tabWidth = tabWidth;
        this.textSize = textSize;
        this.textColor = textColor;
        this.selectorWeight = selectorWeight;
        this.selectorHeight = selectorHeight;
        this.selectorColor = selectorColor;
        this.iconMargins = iconMargins;
        this.textMargins = textMargins;
        this.selectorMargins = selectorMargins;

        initViews();
    }

    // Initialize Views
    private void initViews() {
        // Parent Layout
        relativeLayout_tab = new RelativeLayout(context);
        relativeLayout_tab.setLayoutParams(new LinearLayout.LayoutParams(tabWidth, LinearLayout.LayoutParams.MATCH_PARENT));
//        relativeLayout_tab.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        relativeLayout_tab.setOnClickListener(this);

        // Selector
        int selectorWidth = (int) (tabWidth * selectorWeight);
        RelativeLayout.LayoutParams selectorParams = new RelativeLayout.LayoutParams(selectorWidth, selectorHeight);
        selectorParams.setMargins(selectorMargins[0], selectorMargins[1], selectorMargins[2], selectorMargins[3]);
        selectorParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        selectorParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        selector = new View(context);
        selector.setId(generateViewID());
        selector.setLayoutParams(selectorParams);
        selector.setBackgroundColor(selectorColor);
        selector.setVisibility(View.INVISIBLE);

        // Text
        int buttonHeight = tabItem.getIconID() == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT;
        RelativeLayout.LayoutParams titleParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, buttonHeight);
        titleParams.setMargins(textMargins[0], textMargins[1], textMargins[2], textMargins[3]);
        titleParams.addRule(RelativeLayout.ABOVE, selector.getId());
        if (tabItem.getIconID() == 0) {
            titleParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        }

        textView_title = new TextView(context);
        textView_title.setId(generateViewID());
        textView_title.setLayoutParams(titleParams);
        textView_title.setGravity(Gravity.CENTER);
        textView_title.setMaxLines(1);
        textView_title.setText(tabItem.getText());
        textView_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView_title.setTextColor(textColor);
        textView_title.setAlpha(0.5F);

        // Icon
        if (tabItem.getIconID() != 0) {
            RelativeLayout.LayoutParams iconParams =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            iconParams.setMargins(iconMargins[0], iconMargins[1], iconMargins[2], iconMargins[3]);
            iconParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            iconParams.addRule(RelativeLayout.ABOVE, textView_title.getId());

            imageView_icon = new ImageView(context);
            imageView_icon.setLayoutParams(iconParams);
            imageView_icon.setImageResource(tabItem.getIconID());
            imageView_icon.setAlpha(0.5F);
        }

        // Add Views
        relativeLayout_tab.addView(selector);
        relativeLayout_tab.addView(textView_title);
        if (tabItem.getIconID() != 0) {
            relativeLayout_tab.addView(imageView_icon);
        }
    }


    // Get View
    public View getView() {
        return relativeLayout_tab;
    }


    // Set On Tab Pressed Listener
    public void setOnTabPressedListener(OnTabPressedListener onTabPressedListener) {
        this.onTabPressedListener = onTabPressedListener;
    }


    // Set Selected
    public void setSelected(boolean selected) {
        selector.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
        if (imageView_icon != null) {
            imageView_icon.setAlpha(selected ? 1 : 0.5F);
        }
        textView_title.setAlpha(selected ? 1 : 0.5F);
    }


    @Override
    public void onClick(View view) {
        onTabPressedListener.onTabPressed(this);
    }


    // Generate View ID
    private int generateViewID() {
        while (true) {
            int id = atomicInteger.get();
            int newID = id + 1;

            // ID number larger than 0x00FFFFFF is reserved for static views defined in the /res xml files
            if (newID > 0x00FFFFFF) {
                newID = 1;
            }

            if (atomicInteger.compareAndSet(id, newID)) {
                return id;
            }
        }
    }

}
