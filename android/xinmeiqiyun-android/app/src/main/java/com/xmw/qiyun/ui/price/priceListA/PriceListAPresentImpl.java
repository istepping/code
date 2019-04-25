package com.xmw.qiyun.ui.price.priceListA;

import com.xmw.qiyun.data.model.net.price.PriceListBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;

/**
 * Created by hongyuan on 2017/9/18.
 */

class PriceListAPresentImpl implements PriceListAContract.Presenter {

    private PriceListAContract.View mView;

    private static final String EXTRA_TYPE_SAN_XI = "SanXi";

    PriceListAPresentImpl(PriceListAContract.View view) {
        mView = view;
    }

    @Override
    public void bindView(PriceListAContract.View view) {

    }

    @Override
    public void getData(int page) {

        API.getService().getPriceList(page, EXTRA_TYPE_SAN_XI).subscribe(new MySubscriber<PriceListBean>() {
            @Override
            public void onNext(PriceListBean priceListBean) {

                mView.showList(priceListBean.getData());
            }
        });
    }
}
