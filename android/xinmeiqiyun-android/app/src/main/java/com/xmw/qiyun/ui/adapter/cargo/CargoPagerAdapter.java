package com.xmw.qiyun.ui.adapter.cargo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/11/1.
 */

public class CargoPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();

    public CargoPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {

        super(fm);
        mFragmentList.addAll(fragmentList);
    }

    @Override
    public Fragment getItem(int position) {

        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
