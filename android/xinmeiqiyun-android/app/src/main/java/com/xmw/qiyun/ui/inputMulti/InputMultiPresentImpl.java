package com.xmw.qiyun.ui.inputMulti;

import android.app.Activity;
import android.content.Context;

import com.xmw.qiyun.data.model.event.PublishBodyEvent;
import com.xmw.qiyun.data.model.net.publish.PublishBody;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hongyuan on 2017/8/28.
 */

class InputMultiPresentImpl implements InputMultiContract.Presenter {

    private Context mContext;

    InputMultiPresentImpl(Context context){
        mContext = context;
    }

    @Override
    public void bindView(InputMultiContract.View view) {

    }

    @Override
    public void save(Object object) {

        if(object instanceof PublishBody){

            PublishBodyEvent publishBodyEvent = new PublishBodyEvent();
            publishBodyEvent.setPublishBody((PublishBody) object);

            EventBus.getDefault().post(publishBodyEvent);
        }

        ((Activity) mContext).finish();
    }
}
