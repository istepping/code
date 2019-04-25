package com.xmw.qiyun.ui.price.priceListB;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenenyu.router.annotation.Route;
import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseFragment;
import com.xmw.qiyun.data.model.net.price.PriceList;
import com.xmw.qiyun.ui.adapter.price.PriceListAdapter;
import com.xmw.qiyun.util.manage.RouterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongyuan on 2017/9/18.
 */

@Route(RouterUtil.ROUTER_PRICE_LOCAL_B)
public class PriceListBFragment extends BaseFragment implements PriceListBContract.View {

    @BindView(R.id.price_local_list)
    SuperListview mList;

    PriceListBContract.Presenter mPresenter;

    private int mPage;
    private int mTotalSize;

    @Override
    public void setPresenter(PriceListBContract.Presenter presenter) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_price_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        mPresenter = new PriceListBPresentImpl(this);

        init();
    }

    @Override
    public void init() {

        doRefresh();

        //下拉刷新
        mList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                doRefresh();
            }
        });

        //上拉加载
        mList.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {

                if (mTotalSize != mList.getAdapter().getCount()) {

                    doLoadMore();

                } else {

                    stopLoading();
                }
            }
        });
    }

    @Override
    public void showList(PriceList priceList) {

        mTotalSize = priceList.getTotal();

        if (mPage == 1) {

            mList.setAdapter(new PriceListAdapter(getContext(), priceList.getResultData()));

        } else {

            ((PriceListAdapter) mList.getAdapter()).addAll(priceList.getResultData());
            ((PriceListAdapter) mList.getAdapter()).notifyDataSetChanged();
        }

        stopLoading();
    }

    @Override
    public void doRefresh() {

        mPage = 1;
        mPresenter.getData(mPage);
    }

    @Override
    public void doLoadMore() {

        mPage++;
        mPresenter.getData(mPage);
    }

    @Override
    public void stopLoading() {

        mList.hideProgress();
        mList.hideMoreProgress();
        mList.setLoadingMore(false);
    }
}
