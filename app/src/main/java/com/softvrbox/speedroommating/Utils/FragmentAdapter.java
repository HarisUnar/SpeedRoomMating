package com.softvrbox.speedroommating.Utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.softvrbox.speedroommating.Fragments.ArchivedFragment;
import com.softvrbox.speedroommating.Fragments.OptionsFragment;
import com.softvrbox.speedroommating.Fragments.UpcomingFragment;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    //*********************

    // Fragment Adapter for loading fragments

    //*********************

    private int TabCount;

    public FragmentAdapter(FragmentManager fragmentManager, int CountTabs) {
        super(fragmentManager);
        this.TabCount = CountTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UpcomingFragment();
            case 1:
                return new ArchivedFragment();
            case 2:
                return new OptionsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TabCount;
    }
}
