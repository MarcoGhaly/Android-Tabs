package com.marco.tabs.tabs;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class TabsFragment extends Fragment implements TabView.OnTabPressedListener {

    // On Tab Selected Listener
    public interface OnTabSelectedListener {
        void onTabSelected(int tabIndex);
    }


    private static final int TABS_SEPARATOR_VERTICAL_MARGIN_DP = 0;


    private static final String ARGUMENT_TAB_ITEMS = "Tab Items";
    private static final String ARGUMENT_TABS_PER_PAGE = "Tabs In Screen";
    private static final String ARGUMENT_TAB_PADDING = "Tab Padding";
    private static final String ARGUMENT_FONT_SIZE = "Font Size";
    private static final String ARGUMENT_TEXT_COLOR = "Text Selected Color";
    private static final String ARGUMENT_SELECTOR_HEIGHT_DP = "Tab Height";
    private static final String ARGUMENT_SELECTOR_COLOR = "Selector Color";
    private static final String ARGUMENT_SEPARATOR_WIDTH = "Separator Width";
    private static final String ARGUMENT_SEPARATOR_COLOR = "Separator Color";


    // New Instance

    public static TabsFragment newInstance(TabItem[] tabItems, int tabsPerPage, int tabPaddingDP, int fontSizeSP,
                                           int textColor) {
        return newInstance(tabItems, tabsPerPage, tabPaddingDP, fontSizeSP, textColor, 2, Color.WHITE);
    }

    public static TabsFragment newInstance(TabItem[] tabItems, int tabsPerPage, int tabPaddingDP, int fontSizeSP,
                                           int textColor, int selectorHeightDP, int selectorColor) {
        return newInstance(tabItems, tabsPerPage, tabPaddingDP, fontSizeSP, textColor, selectorHeightDP, selectorColor,
                0, Color.TRANSPARENT);
    }

    public static TabsFragment newInstance(TabItem[] tabItems, int tabsPerPage, int tabPaddingDP, int fontSizeSP,
                                           int textColor, int selectorHeightDP, int selectorColor,
                                           int separatorWidthDP, int separatorColor) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARGUMENT_TAB_ITEMS, tabItems);
        arguments.putInt(ARGUMENT_TABS_PER_PAGE, tabsPerPage);
        arguments.putInt(ARGUMENT_TAB_PADDING, tabPaddingDP);
        arguments.putInt(ARGUMENT_FONT_SIZE, fontSizeSP);
        arguments.putInt(ARGUMENT_TEXT_COLOR, textColor);
        arguments.putInt(ARGUMENT_SELECTOR_HEIGHT_DP, selectorHeightDP);
        arguments.putInt(ARGUMENT_SELECTOR_COLOR, selectorColor);
        arguments.putInt(ARGUMENT_SEPARATOR_COLOR, separatorColor);
        arguments.putInt(ARGUMENT_SEPARATOR_WIDTH, separatorWidthDP);

        TabsFragment tabsFragment = new TabsFragment();
        tabsFragment.setArguments(arguments);
        return tabsFragment;
    }


    private HorizontalScrollView scrollView_tabs;

    private OnTabSelectedListener onTabSelectedListener;

    private TabView[] tabViews;

    private int selectedTabIndex;

    private int tabsPerPage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView_tabs = new HorizontalScrollView(getActivity());
        scrollView_tabs.setLayoutParams(layoutParams);
        scrollView_tabs.setHorizontalScrollBarEnabled(false);
        scrollView_tabs.setVerticalScrollBarEnabled(false);

        initViews();

        return scrollView_tabs;
    }


    // Initialize Views
    private void initViews() {
        Bundle arguments = getArguments();
        final TabItem[] tabItems = (TabItem[]) arguments.getSerializable(ARGUMENT_TAB_ITEMS);
        tabsPerPage = arguments.getInt(ARGUMENT_TABS_PER_PAGE);
        final int tabPaddingDP = arguments.getInt(ARGUMENT_TAB_PADDING);
        final int fontSizeSP = arguments.getInt(ARGUMENT_FONT_SIZE);
        final int textColor = arguments.getInt(ARGUMENT_TEXT_COLOR);
        final int selectorHeightDP = arguments.getInt(ARGUMENT_SELECTOR_HEIGHT_DP);
        final int selectorColor = arguments.getInt(ARGUMENT_SELECTOR_COLOR);
        final int separatorColor = arguments.getInt(ARGUMENT_SEPARATOR_COLOR);
        final int separatorWidthDP = arguments.getInt(ARGUMENT_SEPARATOR_WIDTH);

        scrollView_tabs.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver viewTreeObserver = scrollView_tabs.getViewTreeObserver();
                if (Build.VERSION.SDK_INT >= 16) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this);
                }

                createTabs(tabItems, tabsPerPage, tabPaddingDP, fontSizeSP, textColor, selectorHeightDP,
                        selectorColor, separatorWidthDP, separatorColor);
            }
        });
    }


    // Create Tabs
    private void createTabs(TabItem[] tabItems, int tabsPerPage, int tabPaddingDP, int fontSizeSP, int textColor,
                            int selectorHeightDP, int selectorColor, int separatorWidthDP, int separatorColor) {
        int tabWidth = (scrollView_tabs.getWidth() - (tabsPerPage - 1)) / tabsPerPage;
        int separatorVerticalMargin = Utils.dpToPx(getActivity(), TABS_SEPARATOR_VERTICAL_MARGIN_DP);

        // Tabs Parent View
        FrameLayout.LayoutParams tabsLayoutParams =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout linearLayout_tabs = new LinearLayout(getActivity());
        linearLayout_tabs.setLayoutParams(tabsLayoutParams);
        linearLayout_tabs.setOrientation(LinearLayout.HORIZONTAL);

        tabViews = new TabView[tabItems.length];

        for (int i = 0; i < tabViews.length; i++) {
            if (i != 0) {
                // Create Separator
                View separatorView = createSeparator(separatorWidthDP, separatorColor);
                linearLayout_tabs.addView(separatorView);
            }

            // Create Tab
            tabViews[i] = createTab(tabItems[i], tabWidth, tabPaddingDP, fontSizeSP, textColor,
                    selectorHeightDP, selectorColor, i == selectedTabIndex);
            linearLayout_tabs.addView(tabViews[i].getView());
        }

        scrollView_tabs.addView(linearLayout_tabs);
    }

    // Create Separator
    private View createSeparator(int separatorWidthDP, int separatorColor) {
        int separatorWidth = Utils.dpToPx(getActivity(), separatorWidthDP);

        LinearLayout.LayoutParams separatorParams =
                new LinearLayout.LayoutParams(separatorWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        View separatorView = new View(getActivity());
        separatorView.setBackgroundColor(separatorColor);
        separatorView.setLayoutParams(separatorParams);

        return separatorView;
    }

    // Create Tab
    private TabView createTab(TabItem tabItem, int tabWidth, int tabPaddingDP, int fontSizeSP, int textColor,
                              int selectorHeightDP, int selectorColor, boolean selected) {
        TabView tabView = new TabView(getActivity(), tabItem, tabWidth, tabPaddingDP, fontSizeSP, textColor,
                selectorHeightDP, selectorColor);
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
            for (int i = 0; i < tabViews.length; i++) {
                tabViews[i].setSelected(i == index);
            }

            if (tabViews.length > tabsPerPage) {
                scroll(index, selectedTabIndex);
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
                onTabSelectedListener.onTabSelected(i);
            }
        }
    }


    // Scroll
    private void scroll(final int index, final int oldIndex) {
        scrollView_tabs.post(new Runnable() {
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

                scrollView_tabs.scrollTo(left, 0);
            }
        });
    }

}
