package com.xmw.qiyun.ui.publish.publishShare;

import android.content.Context;

import com.xmw.qiyun.util.share.WxShareUtil;

/**
 * Created by admin on 2017/9/5.
 */

class PublishSharePresentImpl implements PublishShareContract.Presenter {

    private Context mContext;

    PublishSharePresentImpl(Context context){
        mContext = context;
    }

    @Override
    public void bindView(PublishShareContract.View view) {

    }

    @Override
    public void shareImage(String imageUrl, boolean isToChat) {

        WxShareUtil.sendImage(mContext, imageUrl, isToChat);
    }
}
