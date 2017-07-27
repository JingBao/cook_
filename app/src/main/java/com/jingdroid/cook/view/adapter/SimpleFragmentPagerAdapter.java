package com.jingdroid.cook.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Jing on 2017/7/25.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[]{"  推荐  ","个人成就","面包糕点","海鲜料理", "川粤风味"};
//    private Context context;
    private List<Fragment> listFragments;

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<Fragment> al) {
        super(fm);
        this.listFragments = al;
    }

    @Override
    public Fragment getItem(int position) {
//        return BaseFragment.newInstance(position + 1);
        return listFragments.get(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}