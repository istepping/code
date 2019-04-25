package com.xmw.qiyun.ui.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.net.other.VersionBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.util.manage.NoteUtil;

import java.util.List;

/**
 * Created by mac on 2017/7/27.
 */

class HomePresentImpl implements HomeContract.Presenter {

    private Context mContext;
    private HomeContract.View mView;

    HomePresentImpl(Context context, HomeContract.View view){

        mContext = context;
        mView = view;
    }

    @Override
    public void bindView(HomeContract.View view) {

    }

    @Override
    public void switchFragments(Fragment fragment, List<Fragment> fragmentList) {

        FragmentTransaction transaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
        hideFragments(transaction, fragmentList);

        if (!fragment.isAdded()) {

            transaction.add(R.id.container, fragment, fragment.getClass().getName()).commit();
            fragmentList.add(fragment);

        } else {

            transaction.show(fragment).commit();
        }
    }

    @Override
    public void hideFragments(FragmentTransaction transaction, List<Fragment> fragmentList) {

        if (fragmentList.size() == 0) {
            return;
        }

        for (Fragment fragment : fragmentList) {
            transaction.hide(fragment);
        }
    }

    @Override
    public void checkUpdate() {

        NoteUtil.showLoading(mContext);

        API.getService().checkVersion().subscribe(new MySubscriber<VersionBean>() {
            @Override
            public void onNext(VersionBean versionBean) {

                NoteUtil.hideLoading();

                mView.translateVersion(versionBean);
            }
        });
    }
}
