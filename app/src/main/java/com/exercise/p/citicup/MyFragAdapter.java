package com.exercise.p.citicup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        public MyFragAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments=fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }