package com.xmw.qiyun.ui.publish.publishStreamBy;

import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.data.model.event.PublishStreamStopEvent;
import com.xmw.qiyun.data.model.net.CommonResponse;
import com.xmw.qiyun.data.model.net.publish.PublishStream;
import com.xmw.qiyun.data.model.net.publish.PublishStreamListBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.publish.publishDetail.PublishDetailActivity;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hongyuan on 2017/8/30.
 */

class PublishStreamByPresentImpl implements PublishStreamByContract.Presenter {

    private Context mContext;

    private PublishStreamByContract.View mView;

    PublishStreamByPresentImpl(Context context, PublishStreamByContract.View view){

        mContext = context;
        mView = view;
    }

    @Override
    public void bindView(PublishStreamByContract.View view) {

    }

    @Override
    public void getData(int page) {

        API.getService().getPublishStreamByList(page).subscribe(new MySubscriber<PublishStreamListBean>() {
            @Override
            public void onNext(PublishStreamListBean publishStreamListBean) {

                mView.showList(publishStreamListBean.getData());
            }
        });
    }

    @Override
    public void goDetail(PublishStream publishStream) {

        Bundle bundle = new Bundle();
        bundle.putString(PublishDetailActivity.EXTRA_PUBLISH_DETAIL, GsonImpl.init().toJson(publishStream));

        RouterUtil.go(RouterUtil.ROUTER_PUBLISH_DETAIL, mContext, bundle);
    }

    @Override
    public void doClose(PublishStream publishStream) {

        NoteUtil.showLoading(mContext);

        API.getService().doCloseCargo(publishStream.getId()).subscribe(new MySubscriber<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, commonResponse.getMessage());

                if(commonResponse.getStatusCode() == 1){

                    mView.doRefresh();

                    EventBus.getDefault().post(new PublishStreamStopEvent());
                }
            }
        });
    }
}
