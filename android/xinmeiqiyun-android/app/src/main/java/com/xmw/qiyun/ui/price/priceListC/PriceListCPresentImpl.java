package com.xmw.qiyun.ui.price.priceListC;

import com.xmw.qiyun.data.model.net.price.PriceListBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;

/**
 * Created by hongyuan on 2017/9/18.
 */

class PriceListCPresentImpl implements PriceListCContract.Presenter {

    private PriceListCContract.View mView;

    private static final String EXTRA_TYPE_SHAN_XI = "ShanXi";

    PriceListCPresentImpl(PriceListCContract.View view) {
        mView = view;
    }

    @Override
    public void bindView(PriceListCContract.View view) {

    }

    @Override
    public void getData(int page) {

        API.getService().getPriceList(page, EXTRA_TYPE_SHAN_XI).subscribe(new MySubscriber<PriceListBean>() {
            @Override
            public void onNext(PriceListBean priceListBean) {

                mView.showList(priceListBean.getData());
            }
        });
    }
}
