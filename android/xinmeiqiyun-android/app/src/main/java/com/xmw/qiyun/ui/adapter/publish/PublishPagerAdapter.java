package com.xmw.qiyun.ui.adapter.publish;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xmw.qiyun.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/8/30.
 */

public class PublishPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> fragmentList = new ArrayList<>();

    public PublishPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList) {

        super(fm);
        this.context = context;
        this.fragmentList.addAll(fragmentList);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(fragmentList.size() == 2){

            switch (position) {
                case 0:
                    return context.getString(R.string.publish_stream_tab_2);
                case 1:
                    return context.getString(R.string.publish_stream_tab_3);
            }
            return null;

        }else{

            switch (position) {
                case 0:
                    return context.getString(R.string.publish_stream_tab_1);
                case 1:
                    return context.getString(R.string.publish_stream_tab_2);
                case 2:
                    return context.getString(R.string.publish_stream_tab_3);
            }
            return null;
        }
    }
}
