package com.xmw.qiyun.ui.publish.publishStreamBy;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.net.publish.PublishStream;
import com.xmw.qiyun.data.model.net.publish.PublishStreamList;

/**
 * Created by hongyuan on 2017/8/30.
 */

public interface PublishStreamByContract {

    interface View extends BaseView<Presenter> {

        void init();
        void showList(PublishStreamList publishStreamList);

        void doRefresh();
        void stopRefresh();
    }

    interface Presenter extends BasePresenter<View> {

        void getData(int page);
        void goDetail(PublishStream publishStream);

        void doClose(PublishStream publishStream);
    }
}
