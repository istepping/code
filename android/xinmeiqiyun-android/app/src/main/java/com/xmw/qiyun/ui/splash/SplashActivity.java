package com.xmw.qiyun.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.event.PositionEvent;
import com.xmw.qiyun.data.model.event.StandardEvent;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by mac on 2017/7/26.
 */

@Route(RouterUtil.ROUTER_SPLASH)
public class SplashActivity extends BaseActivity implements SplashContract.View {

    SplashContract.Presenter mPresenter;

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        mPresenter = new SplashPresentImpl(this);

        init();
    }

    @Override
    public void init() {

        if(!StandardManager.getStatus()) {

            mPresenter.doStandardService();

        }else{

            mPresenter.doPositionService();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStandardEvent(StandardEvent standardEvent) {

        mPresenter.doPositionService();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPositionEvent(PositionEvent positionEvent) {

        mPresenter.checkHasLogin();

        /*if(UserManager.getIsFirst()){

            mPresenter.goWelcome();

        }else{

            mPresenter.checkHasLogin();
        }*/
    }
}