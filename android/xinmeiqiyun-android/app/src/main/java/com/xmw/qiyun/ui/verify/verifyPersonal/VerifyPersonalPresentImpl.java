package com.xmw.qiyun.ui.verify.verifyPersonal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.nanchen.compresshelper.CompressHelper;
import com.xmw.qiyun.data.model.event.VerifyRefreshEvent;
import com.xmw.qiyun.data.model.net.CommonResponse;
import com.xmw.qiyun.data.model.net.user.ImageUploadBean;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalInfo;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.inputSingle.InputSingleActivity;
import com.xmw.qiyun.ui.verify.VerifyActivity;
import com.xmw.qiyun.ui.verify.verifyCompany.VerifyCompanyActivity;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by hongyuan on 2017/8/29.
 */

class VerifyPersonalPresentImpl implements VerifyPersonalContract.Presenter {

    private Context mContext;

    private VerifyPersonalContract.View mView;

    VerifyPersonalPresentImpl(Context context, VerifyPersonalContract.View view){

        mContext = context;
        mView = view;
    }

    @Override
    public void bindView(VerifyPersonalContract.View view) {

    }

    @Override
    public void selectImage(int code) {

        MultiImageSelector.create(mContext)
                .showCamera(true)
                .single()
                .start((VerifyPersonalActivity)mContext, code);
    }

    @Override
    public File modifyImage(File file) {

        return new CompressHelper.Builder(mContext).setQuality(80).build().compressToFile(file);
    }

    @Override
    public void save(final VerifyPersonalInfo verifyPersonalInfo) {

        API.getService().editPersonalInfo(verifyPersonalInfo).subscribe(new MySubscriber<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, commonResponse.getMessage());

                mView.refreshData(verifyPersonalInfo);
            }
        });
    }

    @Override
    public void uploadImage(File file) {

        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (file.length() > 0) {

            requestBodyBuilder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));

            NoteUtil.showLoading(mContext);

            API.getService().upload(requestBodyBuilder.build()).subscribe(new MySubscriber<ImageUploadBean>() {
                @Override
                public void onNext(ImageUploadBean imageUploadBean) {

                    NoteUtil.hideLoading();
                    NoteUtil.showToast(mContext, imageUploadBean.getMessage());

                    mView.getImageId(imageUploadBean.getData());
                }
            });
        }
    }

    @Override
    public void goEditName(VerifyPersonalInfo verifyPersonalInfo) {

        Bundle bundle = new Bundle();
        bundle.putInt(InputSingleActivity.EXTRA_INPUT_TYPE, InputSingleActivity.EXTRA_INPUT_TYPE_VERIFY_PERSONAL_NAME);
        bundle.putString(InputSingleActivity.EXTRA_INPUT_VALUE, GsonImpl.init().toJson(verifyPersonalInfo));

        RouterUtil.go(RouterUtil.ROUTER_INPUT_SINGLE, mContext, bundle);
    }

    @Override
    public void goEditCode(VerifyPersonalInfo verifyPersonalInfo) {

        Bundle bundle = new Bundle();
        bundle.putInt(InputSingleActivity.EXTRA_INPUT_TYPE, InputSingleActivity.EXTRA_INPUT_TYPE_VERIFY_PERSONAL_CODE);
        bundle.putString(InputSingleActivity.EXTRA_INPUT_VALUE, GsonImpl.init().toJson(verifyPersonalInfo));

        RouterUtil.go(RouterUtil.ROUTER_INPUT_SINGLE, mContext, bundle);
    }

    @Override
    public void goNext(String value) {

        Bundle bundle = new Bundle();
        bundle.putString(VerifyActivity.EXTRA_VERIFY_VALUE, value);
        bundle.putInt(VerifyCompanyActivity.EXTRA_VERIFY_FROM, VerifyCompanyActivity.EXTRA_VERIFY_FROM_PERSONAL);

        RouterUtil.go(RouterUtil.ROUTER_VERIFY_COMPANY, mContext, bundle);
    }

    @Override
    public void goVerifyHome() {

        EventBus.getDefault().post(new VerifyRefreshEvent());
        ((Activity)mContext).finish();
    }
}
