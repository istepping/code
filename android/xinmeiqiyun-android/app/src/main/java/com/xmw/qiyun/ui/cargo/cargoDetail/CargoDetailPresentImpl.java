package com.xmw.qiyun.ui.cargo.cargoDetail;

import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.data.model.net.cargo.CargoDetailBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.ui.cargo.cargoOwner.CargoOwnerActivity;
import com.xmw.qiyun.util.dialog.DialogUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

/**
 * Created by hongyuan on 2017/8/11.
 */

class CargoDetailPresentImpl implements CargoDetailContract.Presenter {

    private Context mContext;

    private CargoDetailContract.View mView;

    CargoDetailPresentImpl(Context context, CargoDetailContract.View view) {

        mContext = context;
        mView = view;
    }

    @Override
    public void bindView(CargoDetailContract.View view) {

    }

    @Override
    public void getData(String id) {

        NoteUtil.showLoading(mContext);

        API.getService().getCargoDetail(id).subscribe(new MySubscriber<CargoDetailBean>() {
            @Override
            public void onNext(CargoDetailBean cargoDetailBean) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, cargoDetailBean.getMessage());

                mView.refreshData(cargoDetailBean.getData());
            }
        });
    }

    @Override
    public void doGoClickCargoMaster(String id) {

        Bundle bundle = new Bundle();
        bundle.putString(CargoOwnerActivity.EXTRA_CARGO_OWNER, id);

        RouterUtil.go(RouterUtil.ROUTER_CARGO_OWNER, mContext, bundle);
    }

    @Override
    public void doCallItem(String mobile) {

        DialogUtil.INSTANCE.initPhoneDialog(mContext, mobile);
    }
}
