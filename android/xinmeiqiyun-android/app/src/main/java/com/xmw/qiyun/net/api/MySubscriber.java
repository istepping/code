package com.xmw.qiyun.net.api;

import com.xmw.qiyun.data.model.event.NetworkEvent;
import com.xmw.qiyun.data.model.event.ReLoginEvent;
import com.xmw.qiyun.util.check.NetworkUtil;
import com.xmw.qiyun.util.manage.NoteUtil;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;

/**
 * Created by mac on 2017/7/27.
 */

public abstract class MySubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

        NoteUtil.hideLoading();
    }

    @Override
    public void onError(Throwable e) {

        NoteUtil.hideLoading();

        if (!NetworkUtil.isConnected()) {

            EventBus.getDefault().post(new NetworkEvent());

            return;
        }

        if (e.getMessage().substring(5, 8).equals("401")) {

            EventBus.getDefault().post(new ReLoginEvent());
        }
    }
}