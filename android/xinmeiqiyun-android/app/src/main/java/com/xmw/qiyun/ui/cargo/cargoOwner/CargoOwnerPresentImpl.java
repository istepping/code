package com.xmw.qiyun.ui.cargo.cargoOwner;

import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.net.cargo.CargoOwner;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.map.MapActivity;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

/**
 * Created by hongyuan on 2017/8/29.
 */

class CargoOwnerPresentImpl implements CargoOwnerContract.Presenter {

    private Context mContext;

    private CargoOwnerContract.View mView;

    CargoOwnerPresentImpl(Context context, CargoOwnerContract.View view) {

        mContext = context;
        mView = view;
    }

    @Override
    public void bindView(CargoOwnerContract.View view) {

    }

    @Override
    public void getData(String id) {

        NoteUtil.showLoading(mContext);

        API.getService().getCargoMaster(id).subscribe(new MySubscriber<CargoOwnerBean>() {
            @Override
            public void onNext(CargoOwnerBean cargoOwnerBean) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, cargoOwnerBean.getMessage());

                mView.refreshData(cargoOwnerBean);
            }
        });
    }

    @Override
    public void goMap(CargoOwner cargoOwner) {

        Bundle bundle = new Bundle();
        bundle.putInt(MapActivity.EXTRA_MAP_TYPE, MapActivity.EXTRA_MAP_CARGO_OWNER);
        bundle.putString(MapActivity.EXTRA_MAP_TITLE, mContext.getString(R.string.cargo_master_title));
        bundle.putString(MapActivity.EXTRA_MAP_VALUE, GsonImpl.init().toJson(cargoOwner));

        RouterUtil.go(RouterUtil.ROUTER_MAP, mContext, bundle);
    }
}
