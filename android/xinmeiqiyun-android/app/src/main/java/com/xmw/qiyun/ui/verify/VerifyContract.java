package com.xmw.qiyun.ui.verify;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalAndCompany;

/**
 * Created by hongyuan on 2017/8/29.
 */

interface VerifyContract {

    interface View extends BaseView<Presenter> {

        void init();
        void getData(VerifyPersonalAndCompany verifyPersonalAndCompany);
        void updateData(VerifyPersonalAndCompany verifyPersonalAndCompany);
    }

    interface Presenter extends BasePresenter<View> {

        void getVerifyInfo();
        void goVerifyPersonal(VerifyPersonalAndCompany verifyPersonalAndCompany, boolean hasApplied);
        void goVerifyCompany(VerifyPersonalAndCompany verifyPersonalAndCompany, boolean hasApplied);

        void doApply(VerifyPersonalAndCompany verifyPersonalAndCompany);
    }
}
