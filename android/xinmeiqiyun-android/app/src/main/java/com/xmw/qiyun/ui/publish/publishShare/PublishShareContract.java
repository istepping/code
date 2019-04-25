package com.xmw.qiyun.ui.publish.publishShare;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;

/**
 * Created by admin on 2017/9/5.
 */

interface PublishShareContract {

    interface View extends BaseView<Presenter> {

        void init();
    }

    interface Presenter extends BasePresenter<View> {

        void shareImage(String imageUrl, boolean isToChat);
    }
}
