package com.example.mobilelab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabsAdapter extends FragmentStateAdapter {

    private static final List<Fragment> FRAGMENTS = new ArrayList<Fragment>() {
        {
            add(new DataListFragment());
            add(new Sample());
            add(new ProfileFragment());
        }
    };

    TabsAdapter(final FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(final int position) {
        return FRAGMENTS.get(position);
    }

    @Override
    public int getItemCount() {
        return FRAGMENTS.size();
    }
}