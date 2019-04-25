package com.xmw.qiyun.ui.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.chenenyu.router.annotation.Route;
import com.viewpagerindicator.CirclePageIndicator;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.local.WelcomeItem;
import com.xmw.qiyun.ui.adapter.welcome.WelcomePagerAdapter;
import com.xmw.qiyun.util.manage.RouterUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongyuan on 2017/9/19.
 */

@Route(RouterUtil.ROUTER_WELCOME)
public class WelcomeActivity extends BaseActivity implements WelcomeContract.View {

    @BindView(R.id.pager)
    ViewPager mWelcomePager;
    @BindView(R.id.indicator)
    CirclePageIndicator mWelcomeIndicator;

    WelcomeContract.Presenter mPresenter;

    @Override
    public void setPresenter(WelcomeContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ButterKnife.bind(this);

        mPresenter = new WelcomePresentImpl(this);

        init();
    }

    @Override
    public void init() {

        List<WelcomeItem> welcomeItemList = new ArrayList<>();
        welcomeItemList.add(new WelcomeItem(R.drawable.welcome1));
        welcomeItemList.add(new WelcomeItem(R.drawable.welcome2));

        mWelcomePager.setAdapter(new WelcomePagerAdapter(this, mPresenter, welcomeItemList));
        mWelcomeIndicator.setViewPager(mWelcomePager);
    }
}
