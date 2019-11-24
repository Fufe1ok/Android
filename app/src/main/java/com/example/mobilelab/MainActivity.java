package com.example.mobilelab;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager = findViewById(R.id.main_activity_view_pager);
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), getLifecycle());

        viewPager.setAdapter(tabsAdapter);

        final String[] tabTitles = new String[]{"Panels", "Tab 2", "Profile"};
        final TabLayout tabLayout = findViewById(R.id.main_activity_tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();
    }
}