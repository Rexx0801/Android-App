package com.example.th2.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.th2.fragment.FragmentInfo;
import com.example.th2.fragment.FragmentList;
import com.example.th2.fragment.FragmentSearch;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentList();
            case 1: return new FragmentSearch();
            case 2: return new FragmentInfo();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
