package com.xmw.qiyun.ui.cargo.cargoList;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.local.StandardItem;
import com.xmw.qiyun.data.model.net.cargo.CargoList;
import com.xmw.qiyun.data.model.net.cargo.CargoSearchBody;
import com.xmw.qiyun.data.model.net.standard.Standard;

import java.util.List;

/**
 * Created by mac on 2017/8/2.
 */

public interface CargoListContract {

    interface View extends BaseView<Presenter> {

        void init();

        void showTitle(String startTitle, String endTitle, String searchTitle);

        void showList(CargoList cargoList);

        void refreshData();

        void stopRefresh();

        void updateStartButton(boolean showOrHide);

        void showStartList(List<LocationItem> locationItemList, Standard standard, int type);

        void updateEndButton(boolean showOrHide);

        void showEndList(List<LocationItem> locationItemList, Standard standard, int type);

        void showVehicleLengthList(List<StandardItem> standardItemList);

        void showVehicleTypeList(List<StandardItem> standardItemList);

        void updateBodyFromLoad(CargoSearchBody cargoSearchBody, String value);

        void refreshTop(CargoSearchBody cargoSearchBody);

        void refreshBottom(CargoSearchBody cargoSearchBody);

        void hideAllDialog();

        void doCallItem(String mobile);

        void doClickCargoDetail(String id);
    }

    interface Presenter extends BasePresenter<View> {

        void getData(int page, CargoSearchBody cargoSearchBody);

        void getTitleData(CargoSearchBody cargoSearchBody);

        void getLocationData(Standard standard, int type, boolean isBack, boolean isLoad, CargoSearchBody cargoSearchBody);

        void getVehicleData(CargoSearchBody cargoSearchBody);

        void goCallOwner(String mobile);

        void goCargoDetail(String id);
    }
}
