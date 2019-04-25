package com.xmw.qiyun.ui.truck.truckDetail;

import android.content.Context;

import com.xmw.qiyun.data.model.net.truck.TruckDetailBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.util.manage.NoteUtil;

/**
 * Created by hongyuan on 2017/8/11.
 */

class TruckDetailPresentImpl implements TruckDetailContract.Presenter {

    private Context mContext;

    private TruckDetailContract.View mView;

    TruckDetailPresentImpl(Context context, TruckDetailContract.View view){

        mContext = context;
        mView = view;
    }

    @Override
    public void bindView(TruckDetailContract.View view) {

    }

    @Override
    public void getData(String id) {

        NoteUtil.showLoading(mContext);

        API.getService().getTruckDetail(id).subscribe(new MySubscriber<TruckDetailBean>() {
            @Override
            public void onNext(TruckDetailBean truckDetailBean) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, truckDetailBean.getMessage());

                mView.refreshData(truckDetailBean.getData());
            }
        });
    }
}
