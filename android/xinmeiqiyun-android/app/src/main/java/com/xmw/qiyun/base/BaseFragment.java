package com.xmw.qiyun.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;
import com.xmw.qiyun.data.model.event.ReLoginEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by mac on 2017/7/26.
 */

public class BaseFragment extends Fragment implements FragmentBackHandler {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onBackPressed() {

        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReLoginEvent(ReLoginEvent reLoginEvent){

        // TODO: 2017/9/11 处理重新登录
    }
}
