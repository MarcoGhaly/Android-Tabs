package com.marco.tabs;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.marco.tabs.tabs.TabsAdapter;
import com.marco.tabs.tabs.TabsView;

public class TabsActivity extends AppCompatActivity implements TabsView.OnTabSelectedListener {

    private String[] titles;
    private int[] iconsResourceIDs;

    private TabsView tabsView;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        titles = new String[3];
        titles[0] = getString(R.string.photos);
        titles[1] = getString(R.string.songs);
        titles[2] = getString(R.string.videos);

        iconsResourceIDs = new int[3];
        iconsResourceIDs[0] = R.drawable.photos_selected;
        iconsResourceIDs[1] = R.drawable.songs_selected;
        iconsResourceIDs[2] = R.drawable.videos_selected;

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
            return 3;
        }

        @Override
        public String getTitle(int position) {
            return titles[position];
        }

        @Override
        public int getIconResourceID(int position) {
            return iconsResourceIDs[position];
        }

    };

}
