package com.android.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.getTabAt(0).setIcon(R.drawable.cal_selected);
        tabLayout.getTabAt(1).setIcon(R.drawable.curr_tran);

        final ViewPager viewPager = findViewById(R.id.view_pager);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    tab.setIcon(R.drawable.cal_selected);
                } else {
                    tab.setIcon(R.drawable.curr_selected);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tab.setIcon(R.drawable.cal_tran);
                } else {
                    tab.setIcon(R.drawable.curr_tran);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}