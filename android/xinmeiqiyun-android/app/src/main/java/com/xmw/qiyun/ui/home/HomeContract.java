package com.xmw.qiyun.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.net.other.VersionBean;

import java.util.List;

/**
 * Created by mac on 2017/7/27.
 */

interface HomeContract {

    interface View extends BaseView<Presenter> {

        void init();
        void translateVersion(VersionBean versionBean);
    }

    interface Presenter extends BasePresenter<View> {

        void switchFragments(Fragment fragment, List<Fragment> fragmentList);
        void hideFragments(FragmentTransaction transaction, List<Fragment> fragmentList);
        void checkUpdate();
    }
}
