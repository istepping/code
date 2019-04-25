package com.xmw.qiyun.ui.publish;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.local.StandardItem;
import com.xmw.qiyun.data.model.net.publish.PublishBody;
import com.xmw.qiyun.data.model.net.publish.PublishBodyList;
import com.xmw.qiyun.data.model.net.standard.Standard;

import java.util.List;

/**
 * Created by mac on 2017/8/3.
 */

public interface PublishContract {

    interface View extends BaseView<Presenter> {

        void init();
        void initPublishCargo();
        void initPublishStream();
        void initPublishRegular();
        void changePage(int index);

        void editRegularData(PublishBody publishBody);
        void updatePublishBody(PublishBody publishBody);
        void updateOptionMulti(PublishBody publishBody);
        void updateMultiBackButton(boolean isVisible);
        void updateSingleBackButton(boolean isVisible);
        void refreshLocationTop(PublishBody publishBody);
        void refreshLocationBottom(PublishBody publishBody);
        void showLocationSelectList(List<LocationItem> locationItemList);
        void showLocationMultiList(List<LocationItem> locationItemList, Standard standard, int type);
        void showLocationSingleList(List<LocationItem> locationItemList, Standard standard, int type);
        void showOptionMultiList(List<StandardItem> topList, List<StandardItem> bottomList);
        void showOptionSingleList(List<StandardItem> standardItemList);
        void showOptionSingleList(List<StandardItem> standardItemList, int unitType);
        void createOtherRemark(PublishBody publishBody);
        void hideAllDialog();
        void clear();

        void showRegularList(PublishBodyList publishBodyList);
        void doRegularRefresh();
        void stopRegularRefresh();
    }

    interface Presenter extends BasePresenter<View> {

        void getStatus();
        void getMileage(PublishBody publishBody);
        void goEditStartDetail(PublishBody publishBody);
        void goEditEndDetail(PublishBody publishBody);
        void goEditNum(PublishBody publishBody);
        void goEditFee(PublishBody publishBody);
        void goEditLoadFee(PublishBody publishBody);
        void goEditUnLoadFee(PublishBody publishBody);

        void getLocationMultiData(Standard standard, int type, boolean isTop, PublishBody publishBody);
        void getLocationSingleData(Standard standard, int type, boolean isBack, boolean isLoad, PublishBody publishBody);
        void getOptionMultiData(PublishBody publishBody, int type);
        void getOptionSingleData(PublishBody publishBody, int type);
        void getOptionSingleData(PublishBody publishBody, int type, int unitType);
        void doPublish(PublishBody publishBody);

        void getRegularData(int page);
        void doEdit(PublishBody publishBody);
        void doSend(PublishBody publishBody);
        void doDelete(PublishBody publishBody);
    }
}
