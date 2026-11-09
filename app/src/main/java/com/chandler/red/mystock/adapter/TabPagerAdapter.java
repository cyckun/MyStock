package com.chandler.red.mystock.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author zcq
 * @date 2018/4/5.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> tabIndicators;

    public TabPagerAdapter(FragmentManager fm, List<String> tabs, List<Fragment> fragments){
        super(fm);
        tabIndicators = tabs;
        fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return tabIndicators.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = tabIndicators.get(position);
        return title;
    }
}
