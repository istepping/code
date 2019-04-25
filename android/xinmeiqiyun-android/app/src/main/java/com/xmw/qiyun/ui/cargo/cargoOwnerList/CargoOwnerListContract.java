package com.xmw.qiyun.ui.cargo.cargoOwnerList;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.net.cargo.CargoOwner;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerList;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerSearchBody;
import com.xmw.qiyun.data.model.net.standard.Standard;

import java.util.List;

/**
 * Created by dell on 2017/11/1.
 */

public interface CargoOwnerListContract {

    interface View extends BaseView<Presenter> {

        void init();

        void showTitle(String title);

        void showList(CargoOwnerList cargoOwnerList);

        void refreshData();

        void stopRefresh();

        void updateLocationButton(boolean showOrHide);

        void showLocationList(List<LocationItem> locationItemList, Standard standard, int type);

        void updateBody(CargoOwnerSearchBody cargoOwnerSearchBody);

        void hideKeyboard(android.view.View view);
    }

    interface Presenter extends BasePresenter<View> {

        void getData(int page, CargoOwnerSearchBody cargoOwnerSearchBody);

        void getTitleData(CargoOwnerSearchBody cargoOwnerSearchBody);

        void getLocationData(Standard standard, int type, boolean isBack, CargoOwnerSearchBody cargoOwnerSearchBody);

        void goMap(CargoOwner cargoOwner);

        void goCallOwner(String mobile);

        void goCargoOwner(String id);
    }
}
