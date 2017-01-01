package com.marco.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.marco.tabs.tabs.TabsAdapter;
import com.marco.tabs.tabs.TabsView;

public class TabsActivity extends AppCompatActivity implements TabsView.OnTabSelectedListener {

    private String[] titles;

    private int[] iconsResourceIDs = {R.drawable.ic_tab_1,
            R.drawable.ic_tab_2,
            R.drawable.ic_tab_3,
            R.drawable.ic_tab_4,
            R.drawable.ic_tab_5,
            R.drawable.ic_tab_6,
            R.drawable.ic_tab_7, R.drawable.ic_tab_8};

    private TabsView tabsView;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        titles = getResources().getStringArray(R.array.tabs);

        initViews();
    }


    // Initialize Views
    private void initViews() {
        tabsView = (TabsView) findViewById(R.id.view_tabs);
        tabsView.setAdapter(tabsAdapter);
        tabsView.setOnTabSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), titles, iconsResourceIDs);
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
            tabsView.setSelectedTab(position);
        }
    };


    // Tabs Adapter
    private TabsAdapter tabsAdapter = new TabsAdapter() {

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

    };


    // Fragments Adapter
    private class PagerAdapter extends FragmentStatePagerAdapter {

        private String[] titles;

        private ContentFragment[] contentFragments;


        // Constructor
        public PagerAdapter(FragmentManager fragmentManager, String[] titles, int[] iconsResourceIDs) {
            super(fragmentManager);

            this.titles = titles;
            contentFragments = new ContentFragment[titles.length];
        }


        @Override
        public int getCount() {
            return titles.length;
        }


        @Override
        public Fragment getItem(int position) {
            if (contentFragments[position] == null) {
                contentFragments[position] = ContentFragment.newInstance(titles[position]);
            }

            return contentFragments[position];
        }

    }

}
