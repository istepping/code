package com.xmw.qiyun.ui.publish.publishDetail;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.net.publish.PublishDetail;
import com.xmw.qiyun.data.model.net.publish.PublishStream;

/**
 * Created by hongyuan on 2017/8/31.
 */

interface PublishDetailContract {

    interface View extends BaseView<Presenter> {

        void init();
        void refreshData(PublishDetail publishDetail);
    }

    interface Presenter extends BasePresenter<View> {

        void getData(PublishStream publishStream);
    }
}
