package com.marco.tabs.tabs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.marco.tabs.R;

public class TabsView extends HorizontalScrollView implements TabView.OnTabPressedListener {

    // On Tab Selected Listener
    public interface OnTabSelectedListener {
        void onTabSelected(int tabIndex);
    }


    private OnTabSelectedListener onTabSelectedListener;

    private TabView[] tabViews;

    private int selectedTabIndex;

    private int tabsPerPage;
    private int iconMarginLeft;
    private int iconMarginTop;
    private int iconMarginRight;
    private int iconMarginBottom;
    private int textSize;
    private int textColor;
    private int textMarginLeft;
    private int textMarginTop;
    private int textMarginRight;
    private int textMarginBottom;
    private int selectorHeight;
    private int selectorColor;
    private int selectorMarginLeft;
    private int selectorMarginTop;
    private int selectorMarginRight;
    private int selectorMarginBottom;
    private int separatorWidth;
    private int separatorColor;

    private TabsAdapter adapter;


    public TabsView(Context context) {
        super(context);
    }

    public TabsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TabsView, 0, 0);

        try {
            tabsPerPage = typedArray.getInteger(R.styleable.TabsView_tabsPerPage, 0);
//            int tabPadding = typedArray.getDimensionPixelSize(R.styleable.TabsView_tabPadding, 0);
            iconMarginLeft = typedArray.getDimensionPixelSize(R.styleable.TabsView_iconMarginLeft, 0);
            iconMarginTop = typedArray.getDimensionPixelSize(R.styleable.TabsView_iconMarginTop, 0);
            iconMarginRight = typedArray.getDimensionPixelSize(R.styleable.TabsView_iconMarginRight, 0);
            iconMarginBottom = typedArray.getDimensionPixelSize(R.styleable.TabsView_iconMarginBottom, 0);
            textSize = typedArray.getDimensionPixelSize(R.styleable.TabsView_textSize, 12);
            textColor = typedArray.getColor(R.styleable.TabsView_textColor, Color.BLACK);
            textMarginLeft = typedArray.getDimensionPixelSize(R.styleable.TabsView_textMarginLeft, 0);
            textMarginTop = typedArray.getDimensionPixelSize(R.styleable.TabsView_textMarginTop, 0);
            textMarginRight = typedArray.getDimensionPixelSize(R.styleable.TabsView_textMarginRight, 0);
            textMarginBottom = typedArray.getDimensionPixelSize(R.styleable.TabsView_textMarginBottom, 0);
            selectorHeight = typedArray.getDimensionPixelSize(R.styleable.TabsView_selectorHeight, 1);
            selectorColor = typedArray.getColor(R.styleable.TabsView_selectorColor, Color.BLACK);
            separatorWidth = typedArray.getDimensionPixelSize(R.styleable.TabsView_separatorWidth, 1);
            selectorMarginLeft = typedArray.getDimensionPixelSize(R.styleable.TabsView_selectorMarginLeft, 0);
            selectorMarginTop = typedArray.getDimensionPixelSize(R.styleable.TabsView_selectorMarginTop, 0);
            selectorMarginRight = typedArray.getDimensionPixelSize(R.styleable.TabsView_selectorMarginRight, 0);
            selectorMarginBottom = typedArray.getDimensionPixelSize(R.styleable.TabsView_selectorMarginBottom, 0);
            separatorColor = typedArray.getColor(R.styleable.TabsView_separatorColor, Color.BLACK);
        } finally {
            typedArray.recycle();
        }
    }


    public void setAdapter(TabsAdapter adapter) {
        this.adapter = adapter;
        initViews();
    }


    // Initialize Views
    private void initViews() {
        final TabItem[] tabItems = new TabItem[adapter.getCount()];
        for (int i = 0; i < tabItems.length; i++) {
            tabItems[i] = new TabItem(adapter.getIconResourceID(i), adapter.getText(i));
        }

        this.setHorizontalScrollBarEnabled(false);
        this.setVerticalScrollBarEnabled(false);

        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver viewTreeObserver = TabsView.this.getViewTreeObserver();
                if (Build.VERSION.SDK_INT >= 16) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this);
                }

                createTabs(tabItems);
            }
        });
    }


    // Create Tabs
    private void createTabs(TabItem[] tabItems) {
        int tabWidth = (this.getWidth() - (tabsPerPage - 1)) / tabsPerPage;

        // Tabs Parent View
        FrameLayout.LayoutParams tabsLayoutParams =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout linearLayout_tabs = new LinearLayout(getContext());
        linearLayout_tabs.setLayoutParams(tabsLayoutParams);
        linearLayout_tabs.setOrientation(LinearLayout.HORIZONTAL);

        tabViews = new TabView[tabItems.length];

        for (int i = 0; i < tabViews.length; i++) {
            if (i != 0) {
                // Create Separator
                View separatorView = createSeparator();
                linearLayout_tabs.addView(separatorView);
            }

            // Create Tab
            tabViews[i] = createTab(tabItems[i], tabWidth, i == selectedTabIndex);
            linearLayout_tabs.addView(tabViews[i].getView());
        }

        this.addView(linearLayout_tabs);
    }

    // Create Separator
    private View createSeparator() {
        LinearLayout.LayoutParams separatorParams =
                new LinearLayout.LayoutParams(separatorWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        View separatorView = new View(getContext());
        separatorView.setBackgroundColor(separatorColor);
        separatorView.setLayoutParams(separatorParams);

        return separatorView;
    }

    // Create Tab
    private TabView createTab(TabItem tabItem, int tabWidth, boolean selected) {
        int[] iconMargins = {iconMarginLeft, iconMarginTop, iconMarginRight, iconMarginBottom};
        int[] textMargins = {textMarginLeft, textMarginTop, textMarginRight, textMarginBottom};
        int[] selectorMargins = {selectorMarginLeft, selectorMarginTop, selectorMarginRight, selectorMarginBottom};

        TabView tabView = new TabView(getContext(), tabItem, tabWidth, textSize, textColor,
                selectorHeight, selectorColor, iconMargins, textMargins, selectorMargins);
        tabView.setSelected(selected);
        tabView.setOnTabPressedListener(this);

        return tabView;
    }


    // Set On Tab Pressed Listener
    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }


    // Set Selected Tab
    public void setSelectedTab(int index) {
        if (index != selectedTabIndex) {
            if (tabViews != null) {
                for (int i = 0; i < tabViews.length; i++) {
                    tabViews[i].setSelected(i == index);
                }

                if (tabViews.length > tabsPerPage) {
                    scroll(index, selectedTabIndex);
                }
            }

            selectedTabIndex = index;
        }
    }


    @Override
    public void onTabPressed(TabView tabView) {
        for (int i = 0; i < tabViews.length; i++) {
            boolean selected = tabView == tabViews[i];
            tabViews[i].setSelected(selected);
            if (selected && i != selectedTabIndex) {
                if (tabViews.length > tabsPerPage) {
                    scroll(i, selectedTabIndex);
                }
                selectedTabIndex = i;
                if (onTabSelectedListener != null) {
                    onTabSelectedListener.onTabSelected(i);
                }
            }
        }
    }


    // Scroll
    private void scroll(final int index, final int oldIndex) {
        this.post(new Runnable() {
            @Override
            public void run() {
                int left;

                if (index < (tabsPerPage + 1) / 2) {
                    left = 0;
                } else if (index > tabViews.length - (tabsPerPage + 1) / 2) {
                    left = tabViews[tabViews.length - 1].getView().getRight();
                } else {
                    int leftTabIndex = index - tabsPerPage / 2;
                    if (tabsPerPage % 2 == 0 && oldIndex > index) {
                        leftTabIndex++;
                    }
                    left = tabViews[leftTabIndex].getView().getLeft();
                }

                TabsView.this.scrollTo(left, 0);
            }
        });
    }

}
