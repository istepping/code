package com.xmw.qiyun.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.event.NetworkEvent;
import com.xmw.qiyun.data.model.event.ReLoginEvent;
import com.xmw.qiyun.ui.home.HomeActivity;
import com.xmw.qiyun.util.manage.AppUtil;
import com.xmw.qiyun.util.dialog.DialogUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.dialog.ReLoginUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by mac on 2017/7/26.
 */

public class BaseActivity extends FragmentActivity {

    public BaseActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtil.INSTANCE.addActivity(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        AppUtil.INSTANCE.finishActivity(this);

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void finish() {

        super.finish();

        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetWorkEvent(NetworkEvent networkEvent) {

        NoteUtil.showToast(this, getString(R.string.toast_network_error));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReLoginEvent(ReLoginEvent reLoginEvent) {

        if(AppUtil.INSTANCE.getTopActivity().getClass().equals(HomeActivity.class)){

            ReLoginUtil.init(AppUtil.INSTANCE.getTopActivity()).initLoginDialog(AppUtil.INSTANCE.getTopActivity());

        }else{

            if(this.equals(AppUtil.INSTANCE.getTopActivity()))

            DialogUtil.INSTANCE.initLoginDialog(AppUtil.INSTANCE.getTopActivity());
        }
    }
}
