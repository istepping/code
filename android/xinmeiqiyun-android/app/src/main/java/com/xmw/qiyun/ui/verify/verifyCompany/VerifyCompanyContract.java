package com.xmw.qiyun.ui.verify.verifyCompany;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.user.VerifyCompanyInfo;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalAndCompany;

import java.io.File;
import java.util.List;

/**
 * Created by hongyuan on 2017/8/29.
 */

public interface VerifyCompanyContract {

    interface View extends BaseView<Presenter> {

        void init();
        void getImageId(String id);
        void refreshData(VerifyCompanyInfo verifyCompanyInfo);
        void hideAllDialog();

        void updateVerifyCompanyInfo(VerifyCompanyInfo verifyCompanyInfo);
        void updateBackButton(boolean isVisible);
        void showLocationList(List<LocationItem> locationItemList, Standard standard, int type);
    }

    interface Presenter extends BasePresenter<View> {

        void selectImage(int code);
        File modifyImage(File file);

        void save(VerifyCompanyInfo verifyCompanyInfo);

        void uploadImage(File file);

        void goEditName(VerifyCompanyInfo verifyCompanyInfo);
        void goEditAlias(VerifyCompanyInfo verifyCompanyInfo);
        void goEditCode(VerifyCompanyInfo verifyCompanyInfo);
        void goEditLocation(VerifyCompanyInfo verifyCompanyInfo);

        void doPositionService();
        void doApply(VerifyPersonalAndCompany verifyPersonalAndCompany);

        void goVerifyPersonal(VerifyPersonalAndCompany verifyPersonalAndCompany);
        void goVerifyPersonal();
        void goVerifyHome();
        void goMap(double latitude, double longitude);

        void getLocationData(Standard standard, int type, boolean isBack, VerifyCompanyInfo verifyCompanyInfo);
    }
}
