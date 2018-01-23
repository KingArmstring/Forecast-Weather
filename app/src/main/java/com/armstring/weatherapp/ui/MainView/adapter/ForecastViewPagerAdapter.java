package com.armstring.weatherapp.ui.MainView.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Never fukenly use this shit except in case of showing data that needs to b update.
 */

public class ForecastViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    public ForecastViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }


}
