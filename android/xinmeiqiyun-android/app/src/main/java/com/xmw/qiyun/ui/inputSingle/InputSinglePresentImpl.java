package com.xmw.qiyun.ui.inputSingle;

import android.app.Activity;
import android.content.Context;

import com.xmw.qiyun.data.model.event.InformationEvent;
import com.xmw.qiyun.data.model.event.PublishBodyEvent;
import com.xmw.qiyun.data.model.event.VerifyCompanyEvent;
import com.xmw.qiyun.data.model.event.VerifyPersonalEvent;
import com.xmw.qiyun.data.model.net.publish.PublishBody;
import com.xmw.qiyun.data.model.net.user.UserInfo;
import com.xmw.qiyun.data.model.net.user.VerifyCompanyInfo;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hongyuan on 2017/8/28.
 */

class InputSinglePresentImpl implements InputSingleContract.Presenter {

    private Context mContext;

    InputSinglePresentImpl(Context context) {

        mContext = context;
    }

    @Override
    public void bindView(InputSingleContract.View view) {

    }

    @Override
    public void save(final Object object) {

        if (object instanceof UserInfo) {

            InformationEvent informationEvent = new InformationEvent((UserInfo) object);
            EventBus.getDefault().post(informationEvent);
        }

        if (object instanceof VerifyPersonalInfo) {

            VerifyPersonalEvent verifyPersonalEvent = new VerifyPersonalEvent();
            verifyPersonalEvent.setVerifyPersonalInfo((VerifyPersonalInfo) object);

            EventBus.getDefault().post(verifyPersonalEvent);
        }

        if (object instanceof VerifyCompanyInfo) {

            VerifyCompanyEvent verifyCompanyEvent = new VerifyCompanyEvent();
            verifyCompanyEvent.setVerifyCompanyInfo((VerifyCompanyInfo) object);

            EventBus.getDefault().post(verifyCompanyEvent);
        }

        if(object instanceof PublishBody){

            PublishBodyEvent publishBodyEvent = new PublishBodyEvent();
            publishBodyEvent.setPublishBody((PublishBody) object);

            EventBus.getDefault().post(publishBodyEvent);
        }

        ((Activity) mContext).finish();
    }
}
