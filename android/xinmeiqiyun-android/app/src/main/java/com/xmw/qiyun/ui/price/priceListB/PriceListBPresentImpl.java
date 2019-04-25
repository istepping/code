package com.xmw.qiyun.ui.price.priceListB;

import com.xmw.qiyun.data.model.net.price.PriceListBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;

/**
 * Created by hongyuan on 2017/9/18.
 */

class PriceListBPresentImpl implements PriceListBContract.Presenter {

    private PriceListBContract.View mView;

    private static final String EXTRA_TYPE_INNER_MONGOLIA = "InnerMongolia";

    PriceListBPresentImpl(PriceListBContract.View view) {
        mView = view;
    }

    @Override
    public void bindView(PriceListBContract.View view) {

    }

    @Override
    public void getData(int page) {

        API.getService().getPriceList(page, EXTRA_TYPE_INNER_MONGOLIA).subscribe(new MySubscriber<PriceListBean>() {
            @Override
            public void onNext(PriceListBean priceListBean) {

                mView.showList(priceListBean.getData());
            }
        });
    }
}
