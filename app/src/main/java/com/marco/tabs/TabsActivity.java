package com.marco.tabs;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.marco.tabs.tabs.FragmentsPagerAdapter;
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
        tabsView.setAdapter(new CustomTabsAdapter(titles, iconsResourceIDs));
        tabsView.setOnTabSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        FragmentsPagerAdapter fragmentsPagerAdapter = new FragmentsPagerAdapter(getSupportFragmentManager(), titles);
        viewPager.setAdapter(fragmentsPagerAdapter);
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

}
