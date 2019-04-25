package com.xmw.qiyun.ui.information;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.user.UserInfo;
import com.xmw.qiyun.data.model.net.user.UserInfoBody;

import java.io.File;
import java.util.List;

/**
 * Created by hongyuan on 2017/8/28.
 */

public interface InformationContract {

    interface View extends BaseView<Presenter> {

        void init();
        void getImageId(String id);
        void getData(UserInfo userInfo);
        void refreshData();
        void hideAllDialog();

        void updateBackButton(boolean isVisible);
        void showLocationList(List<LocationItem> locationItemList, Standard standard, int type);
    }

    interface Presenter extends BasePresenter<View> {

        void getData();

        void selectImage();
        File modifyImage(File file);

        void uploadAvatar(File file);
        void goEditName(UserInfo userInfo);
        void goEditCompanyName(UserInfo userInfo);
        void goEditCompanyLocation(UserInfo userInfo);

        void getLocationData(Standard standard, int type, boolean isBack, UserInfo userInfo);

        void doPositionService();
        void doSave(UserInfoBody userInfoBody, boolean isFromLoginAndRegister);

        void goMap(double latitude, double longitude);
    }
}
