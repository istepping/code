package com.xmw.qiyun.ui.verify.verifyPersonal;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalInfo;

import java.io.File;

/**
 * Created by hongyuan on 2017/8/29.
 */

public interface VerifyPersonalContract {

    interface View extends BaseView<Presenter> {

        void init();
        void refreshData(VerifyPersonalInfo verifyPersonalInfo);
        void getImageId(String id);
    }

    interface Presenter extends BasePresenter<View> {

        void selectImage(int code);
        File modifyImage(File file);

        void save(VerifyPersonalInfo verifyPersonalInfo);

        void uploadImage(File file);

        void goEditName(VerifyPersonalInfo verifyPersonalInfo);
        void goEditCode(VerifyPersonalInfo verifyPersonalInfo);

        void goNext(String value);

        void goVerifyHome();
    }
}
