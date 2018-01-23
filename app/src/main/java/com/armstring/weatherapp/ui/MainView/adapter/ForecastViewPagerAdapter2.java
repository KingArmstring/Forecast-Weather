package com.armstring.weatherapp.ui.MainView.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



import java.util.List;



public class ForecastViewPagerAdapter2 extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    public ForecastViewPagerAdapter2(FragmentManager fm, List<Fragment> fragments) {
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
