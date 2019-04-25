package com.xmw.qiyun.ui.publish.publishStreamIn;

import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.data.model.event.PublishStreamByEvent;
import com.xmw.qiyun.data.model.event.PublishStreamStopEvent;
import com.xmw.qiyun.data.model.net.CommonResponse;
import com.xmw.qiyun.data.model.net.publish.PublishStream;
import com.xmw.qiyun.data.model.net.publish.PublishStreamListBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.publish.publishDetail.PublishDetailActivity;
import com.xmw.qiyun.ui.publish.publishShare.PublishShareActivity;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hongyuan on 2017/8/30.
 */

class PublishStreamInPresentImpl implements PublishStreamInContract.Presenter {

    private Context mContext;

    private PublishStreamInContract.View mView;

    PublishStreamInPresentImpl(Context context, PublishStreamInContract.View view){

        mContext = context;
        mView = view;
    }

    @Override
    public void bindView(PublishStreamInContract.View view) {

    }

    @Override
    public void getData(int page) {

        API.getService().getPublishStreamInList(page).subscribe(new MySubscriber<PublishStreamListBean>() {
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
    public void doShare(PublishStream publishStream) {

        Bundle bundle = new Bundle();
        bundle.putString(PublishShareActivity.EXTRA_IMAGE_ID, publishStream.getId());

        RouterUtil.go(RouterUtil.ROUTER_PUBLISH_SHARE, mContext, bundle);
    }

    @Override
    public void doResend(final PublishStream publishStream) {

        NoteUtil.showLoading(mContext);

        API.getService().doResendCargo(publishStream.getId()).subscribe(new MySubscriber<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, commonResponse.getMessage());

                if(commonResponse.getStatusCode() == 1){

                    mView.doRefresh();

                    if(publishStream.getGoodsOwnerInfo_Status() == 1){

                        EventBus.getDefault().post(new PublishStreamByEvent());
                    }
                }
            }
        });
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
