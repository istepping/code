package com.xmw.qiyun.ui.publish.publishDetail;

import android.content.Context;

import com.xmw.qiyun.data.model.net.publish.PublishDetailBean;
import com.xmw.qiyun.data.model.net.publish.PublishStream;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.util.manage.NoteUtil;

/**
 * Created by hongyuan on 2017/8/31.
 */

class PublishDetailPresentImpl implements PublishDetailContract.Presenter {

    private Context mContext;

    private PublishDetailContract.View mView;

    PublishDetailPresentImpl(Context context, PublishDetailContract.View view){

        mContext = context;
        mView = view;
    }

    @Override
    public void bindView(PublishDetailContract.View view) {

    }

    @Override
    public void getData(PublishStream publishStream) {

        NoteUtil.showLoading(mContext);

        API.getService().getPublishDetail(publishStream.getId()).subscribe(new MySubscriber<PublishDetailBean>() {
            @Override
            public void onNext(PublishDetailBean publishDetailBean) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, publishDetailBean.getMessage());

                mView.refreshData(publishDetailBean.getData());
            }
        });
    }
}
