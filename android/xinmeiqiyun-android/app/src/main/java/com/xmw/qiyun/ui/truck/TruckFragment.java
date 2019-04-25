package com.xmw.qiyun.ui.truck;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseFragment;
import com.xmw.qiyun.data.model.event.RefreshEvent;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.local.StandardItem;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.truck.TruckList;
import com.xmw.qiyun.data.model.net.truck.TruckSearchBody;
import com.xmw.qiyun.ui.adapter.truck.TruckAdapter;
import com.xmw.qiyun.ui.adapter.TruckCityAdapter;
import com.xmw.qiyun.ui.adapter.TruckCityTopAdapter;
import com.xmw.qiyun.ui.adapter.common.OptionAdapter;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.manage.RouterUtil;
import com.xmw.qiyun.view.ScrollableGridView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mac on 2017/8/2.
 */

@Route(RouterUtil.ROUTER_TRUCK)
public class TruckFragment extends BaseFragment implements TruckContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.city)
    TextView mCity;
    @BindView(R.id.search)
    TextView mSearch;
    @BindView(R.id.truck_list)
    SuperListview mList;

    @BindView(R.id.truck_city)
    View mTruckCity;
    @BindView(R.id.city_top_list)
    GridView mCityTopList;
    @BindView(R.id.city_bottom_list)
    GridView mCityBottomList;
    @BindView(R.id.city_back)
    View mCityBack;

    @BindView(R.id.option_multi_dialog)
    View mTruckVehicle;
    @BindView(R.id.option_multi_top_list)
    ScrollableGridView mVehicleTopList;
    @BindView(R.id.option_multi_bottom_list)
    ScrollableGridView mVehicleBottomList;

    TruckContract.Presenter mPresenter;

    private int mPage;
    private int mTotalSize;

    private int mDataType = 1;
    private Standard mStandard = new Standard();

    private TruckSearchBody mTruckSearchBody = new TruckSearchBody();

    @Override
    public void setPresenter(TruckContract.Presenter presenter) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_truck, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        mPresenter = new TruckPresentImpl(getContext(), this);

        init();
    }

    @Override
    public void init() {

        mTitle.setText(getString(R.string.truck_title));

        mPresenter.getLocationData(null, mDataType, false, mTruckSearchBody);
        mPresenter.getVehicleData(mTruckSearchBody);

        mPage = 1;
        mPresenter.getData(mPage, mTruckSearchBody);

        //下拉刷新
        mList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPage = 1;
                mPresenter.getData(mPage, mTruckSearchBody);
            }
        });

        //上拉加载
        mList.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {

                if (mTotalSize != mList.getAdapter().getCount()) {

                    mPage++;
                    mPresenter.getData(mPage, mTruckSearchBody);

                } else {

                    stopRefresh();
                }
            }
        });
    }

    @Override
    public void showList(TruckList truckList) {

        mTotalSize = truckList.getTotal();

        if (mPage == 1) {

            mList.setAdapter(new TruckAdapter(getContext(), mPresenter, truckList.getResultData()));

        } else {

            ((TruckAdapter) mList.getAdapter()).addAll(truckList.getResultData());
            ((TruckAdapter) mList.getAdapter()).notifyDataSetChanged();
        }

        stopRefresh();
    }

    @Override
    public void stopRefresh() {

        mList.hideProgress();
        mList.hideMoreProgress();
        mList.setLoadingMore(false);
    }

    @Override
    public void showCityTitle(String cityTitle) {

        mCity.setText(CommonUtil.isNullOrEmpty(cityTitle) ? getString(R.string.cargo_nation) : cityTitle);
    }

    @Override
    public void showSearchTitle(String searchTitle) {

        mSearch.setText(CommonUtil.isNullOrEmpty(searchTitle) ? getString(R.string.cargo_search) : searchTitle);
    }

    @Override
    public void updateCityButton(boolean showOrHide) {

        mCityBack.setVisibility(showOrHide ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showCityList(List<LocationItem> locationItemList, Standard standard, int type) {

        mDataType = type;
        mStandard = standard;

        mCityBottomList.setAdapter(new TruckCityAdapter(getContext(), this, mPresenter, type, mTruckSearchBody, locationItemList));
    }

    @Override
    public void showVehicleLengthList(List<StandardItem> standardItemList) {

        mVehicleTopList.setAdapter(new OptionAdapter(getContext(), mPresenter, mTruckSearchBody, standardItemList));
    }

    @Override
    public void showVehicleTypeList(List<StandardItem> standardItemList) {

        mVehicleBottomList.setAdapter(new OptionAdapter(getContext(), mPresenter, mTruckSearchBody, standardItemList));
    }

    @Override
    public void refreshTop(TruckSearchBody truckSearchBody) {

        mTruckSearchBody = truckSearchBody;

        mCityTopList.setAdapter(new TruckCityTopAdapter(getContext(), this, mTruckSearchBody));
    }

    @Override
    public void refreshBottom(TruckSearchBody truckSearchBody) {

        mTruckSearchBody = truckSearchBody;

        mPresenter.getLocationData(mStandard, mDataType, false, mTruckSearchBody);
    }

    @Override
    public void hideAllDialog(TruckSearchBody truckSearchBody) {

        mTruckCity.setVisibility(View.GONE);
        mTruckVehicle.setVisibility(View.GONE);

        mTruckSearchBody = truckSearchBody;

        mPage = 1;
        mPresenter.getData(mPage, mTruckSearchBody);
        mPresenter.getTitleData(mTruckSearchBody);
    }

    @OnClick({
            R.id.title_button,
            R.id.city,
            R.id.city_back,
            R.id.city_confirm,
            R.id.search,
            R.id.option_multi_confirm
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.title_button: {

                mPresenter.goMap();
            }
            break;

            case R.id.city: {

                if (mTruckCity.getVisibility() == View.VISIBLE) {

                    mTruckCity.setVisibility(View.GONE);
                    mTruckVehicle.setVisibility(View.GONE);

                } else {

                    mTruckCity.setVisibility(View.VISIBLE);
                    mTruckVehicle.setVisibility(View.GONE);
                }
            }
            break;

            case R.id.city_back: {

                mDataType--;

                mPresenter.getLocationData(mStandard, mDataType, true, mTruckSearchBody);
            }
            break;

            case R.id.city_confirm: {

                hideAllDialog(mTruckSearchBody);
            }
            break;

            case R.id.search: {

                if (mTruckVehicle.getVisibility() == View.VISIBLE) {

                    mTruckCity.setVisibility(View.GONE);
                    mTruckVehicle.setVisibility(View.GONE);

                } else {

                    mTruckCity.setVisibility(View.GONE);
                    mTruckVehicle.setVisibility(View.VISIBLE);
                }
            }
            break;

            case R.id.option_multi_confirm: {

                hideAllDialog(mTruckSearchBody);
            }
            break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent refreshEvent) {

        mPage = 1;
        mPresenter.getData(mPage, mTruckSearchBody);
        mPresenter.getTitleData(mTruckSearchBody);
    }
}
