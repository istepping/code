package com.xmw.qiyun.ui.price;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseFragment;
import com.xmw.qiyun.ui.adapter.price.PricePagerAdapter;
import com.xmw.qiyun.ui.price.priceListA.PriceListAFragment;
import com.xmw.qiyun.ui.price.priceListB.PriceListBFragment;
import com.xmw.qiyun.ui.price.priceListC.PriceListCFragment;
import com.xmw.qiyun.ui.price.priceListD.PriceListDFragment;
import com.xmw.qiyun.util.manage.RouterUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongyuan on 2017/9/18.
 */

@Route(RouterUtil.ROUTER_PRICE)
public class PriceFragment extends BaseFragment implements PriceContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.price_tab)
    TabLayout mPriceTab;
    @BindView(R.id.price_pager)
    ViewPager mPricePager;

    @Override
    public void setPresenter(PriceContract.Presenter presenter) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_price, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        init();
    }

    @Override
    public void init() {

        mTitle.setText(getString(R.string.price_title));

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(new PriceListAFragment());
        fragmentList.add(new PriceListBFragment());
        fragmentList.add(new PriceListCFragment());
        fragmentList.add(new PriceListDFragment());

        mPricePager.setAdapter(new PricePagerAdapter(this.getChildFragmentManager(), getContext(), fragmentList));
        mPriceTab.setupWithViewPager(mPricePager);
    }
}
