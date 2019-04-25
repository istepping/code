package com.xmw.qiyun.ui.test;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xmw.qiyun.base.BaseActivity;

public class TestActivity extends BaseActivity implements TestContract.View {

    // 定义P层对象
    TestContract.Presenter mPresenter;

    @Override
    public void setPresenter(TestContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mPresenter = new TestPresentImpl(this);

        init();
    }

    @Override
    public void init() {

        // 调用P层方法
        // 打印运行结果

        mPresenter.getProvinceList();
    }
}
