package com.marco.tabs;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.marco.tabs.tabs.TabItem;
import com.marco.tabs.tabs.TabsFragment;

public class TabsActivity extends AppCompatActivity implements TabsFragment.OnTabSelectedListener {

    private static final String TAG_TABS_FRAGMENT = "Tabs Fragment";


    private TabItem[] tabItems;

    private TabsFragment tabsFragment;

    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        tabItems = new TabItem[3];
        tabItems[0] = new TabItem(R.drawable.photos_selected, getString(R.string.photos));
        tabItems[1] = new TabItem(R.drawable.songs_selected, getString(R.string.songs));
        tabItems[2] = new TabItem(R.drawable.videos_selected, getString(R.string.videos));

        initTabsFragments();
        initContentFragments();
    }


    // Initialize Fragments
    private void initTabsFragments() {
        FragmentManager fragmentManager = getFragmentManager();
        tabsFragment = (TabsFragment) fragmentManager.findFragmentByTag(TAG_TABS_FRAGMENT);

        if (tabsFragment == null) {
            int textColor;
            int selectorColor;
            int separatorColor = Color.TRANSPARENT;

            if (Build.VERSION.SDK_INT >= 23) {
                textColor = getColor(R.color.tabsText);
                selectorColor = getColor(R.color.tabsSelector);
            } else {
                textColor = getResources().getColor(R.color.tabsText);
                selectorColor = getResources().getColor(R.color.tabsSelector);
            }

            tabsFragment = TabsFragment.newInstance(tabItems, 3, 5, 15, textColor, 2, selectorColor, 0, separatorColor);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.layout_tabs, tabsFragment, TAG_TABS_FRAGMENT);
            fragmentTransaction.commit();
        }

        tabsFragment.setOnTabSelectedListener(this);
    }


    // Initialize Content Fragments
    private void initContentFragments() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabItems);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(simpleOnPageChangeListener);
    }


    @Override
    public void onTabSelected(int tabIndex) {
        viewPager.setCurrentItem(tabIndex);
    }


    // On Page Change Listener
    private ViewPager.SimpleOnPageChangeListener simpleOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            tabsFragment.setSelectedTab(position);
        }
    };

}
