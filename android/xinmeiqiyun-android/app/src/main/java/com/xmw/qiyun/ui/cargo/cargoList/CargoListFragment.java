package com.xmw.qiyun.ui.cargo.cargoList;

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
import com.xmw.qiyun.data.model.net.cargo.CargoList;
import com.xmw.qiyun.data.model.net.cargo.CargoSearch;
import com.xmw.qiyun.data.model.net.cargo.CargoSearchBody;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.ui.adapter.cargo.CargoAdapter;
import com.xmw.qiyun.ui.adapter.CargoUnloadAdapter;
import com.xmw.qiyun.ui.adapter.CargoUnloadTopAdapter;
import com.xmw.qiyun.ui.adapter.common.LocationSingleAdapter;
import com.xmw.qiyun.ui.adapter.common.OptionAdapter;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.manage.RouterUtil;
import com.xmw.qiyun.view.ScrollableGridView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mac on 2017/8/2.
 */

@Route(RouterUtil.ROUTER_CARGO_LIST)
public class CargoListFragment extends BaseFragment implements CargoListContract.View {

    @BindView(R.id.start_location)
    TextView mStart;
    @BindView(R.id.end_location)
    TextView mEnd;
    @BindView(R.id.search)
    TextView mSearch;
    @BindView(R.id.cargo_list)
    SuperListview mList;

    @BindView(R.id.location_single_dialog)
    View mCargoStart;
    @BindView(R.id.location_single_list)
    GridView mLoadList;
    @BindView(R.id.location_single_back)
    View mLoadBack;

    @BindView(R.id.cargo_end)
    View mCargoEnd;
    @BindView(R.id.unload_top_list)
    GridView mUnloadTopList;
    @BindView(R.id.unload_bottom_list)
    GridView mUnloadBottomList;
    @BindView(R.id.unload_back)
    View mUnloadBack;

    @BindView(R.id.option_multi_dialog)
    View mCargoVehicle;
    @BindView(R.id.option_multi_top_list)
    ScrollableGridView mVehicleTopList;
    @BindView(R.id.option_multi_bottom_list)
    ScrollableGridView mVehicleBottomList;

    CargoListContract.Presenter mPresenter;

    private int mPage;
    private int mTotalSize;

    private int mStartType = 1;
    private Standard mStartStandard = new Standard();

    private int mEndType = 1;
    private Standard mEndStandard = new Standard();

    private CargoSearchBody mCargoSearchBody = new CargoSearchBody();

    @Override
    public void setPresenter(CargoListContract.Presenter presenter) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cargo_list, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        mPresenter = new CargoListPresentImpl(getContext(), this);

        init();
    }

    @Override
    public void init() {

        //初始化搜索模型
        mCargoSearchBody.setLoadRegionType(1);

        CargoSearch cargoSearch = new CargoSearch();
        cargoSearch.setRegionId("");
        cargoSearch.setRegionType(1);

        List<CargoSearch> cargoSearchList = new ArrayList<>();
        cargoSearchList.add(cargoSearch);
        mCargoSearchBody.setUnloadSearch(cargoSearchList);

        mPresenter.getTitleData(mCargoSearchBody);
        mPresenter.getLocationData(null, mStartType, false, true, mCargoSearchBody);
        mPresenter.getLocationData(null, mEndType, false, false, mCargoSearchBody);
        mPresenter.getVehicleData(mCargoSearchBody);
        refreshTop(mCargoSearchBody);

        mPage = 1;
        mPresenter.getData(mPage, mCargoSearchBody);

        //下拉刷新
        mList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mPresenter.getData(mPage, mCargoSearchBody);
            }
        });

        //上拉加载
        mList.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {

                if (mTotalSize != mList.getAdapter().getCount()) {

                    mPage++;
                    mPresenter.getData(mPage, mCargoSearchBody);

                } else {

                    stopRefresh();
                }
            }
        });
    }

    @Override
    public void showTitle(String startTitle, String endTitle, String searchTitle) {

        mStart.setText(CommonUtil.isNullOrEmpty(startTitle) ? getString(R.string.cargo_start) : startTitle);
        mEnd.setText(CommonUtil.isNullOrEmpty(endTitle) ? getString(R.string.cargo_end) : endTitle);
        mSearch.setText(CommonUtil.isNullOrEmpty(searchTitle) ? getString(R.string.cargo_search) : searchTitle);
    }

    @Override
    public void showList(CargoList cargoList) {

        mTotalSize = cargoList.getTotal();

        if (mPage == 1) {

            mList.setAdapter(new CargoAdapter(getContext(), this, cargoList.getResultData()));

        } else {

            ((CargoAdapter) mList.getAdapter()).addAll(cargoList.getResultData());
            ((CargoAdapter) mList.getAdapter()).notifyDataSetChanged();
        }

        stopRefresh();
    }

    @Override
    public void refreshData() {

        mPage = 1;
        mPresenter.getData(mPage, mCargoSearchBody);
        mPresenter.getTitleData(mCargoSearchBody);
    }

    @Override
    public void stopRefresh() {

        mList.hideProgress();
        mList.hideMoreProgress();
        mList.setLoadingMore(false);
    }

    @Override
    public void updateStartButton(boolean showOrHide) {

        mLoadBack.setVisibility(showOrHide ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showStartList(List<LocationItem> locationItemList, Standard standard, int type) {

        mStartType = type;
        mStartStandard = standard;

        mLoadList.setAdapter(new LocationSingleAdapter(getContext(), this, mPresenter, type, mCargoSearchBody, locationItemList));
    }

    @Override
    public void updateEndButton(boolean showOrHide) {

        mUnloadBack.setVisibility(showOrHide ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showEndList(List<LocationItem> locationItemList, Standard standard, int type) {

        mEndType = type;
        mEndStandard = standard;

        mUnloadBottomList.setAdapter(new CargoUnloadAdapter(getContext(), this, mPresenter, mCargoSearchBody, type, locationItemList));
    }

    @Override
    public void showVehicleLengthList(List<StandardItem> standardItemList) {

        mVehicleTopList.setAdapter(new OptionAdapter(getContext(), mPresenter, mCargoSearchBody, standardItemList));
    }

    @Override
    public void showVehicleTypeList(List<StandardItem> standardItemList) {

        mVehicleBottomList.setAdapter(new OptionAdapter(getContext(), mPresenter, mCargoSearchBody, standardItemList));
    }

    @Override
    public void updateBodyFromLoad(CargoSearchBody cargoSearchBody, String value) {

        mCargoSearchBody = cargoSearchBody;

        hideAllDialog();

        refreshData();
    }

    @Override
    public void refreshTop(CargoSearchBody cargoSearchBody) {

        mCargoSearchBody = cargoSearchBody;

        mUnloadTopList.setAdapter(new CargoUnloadTopAdapter(getContext(), this, cargoSearchBody));
    }

    @Override
    public void refreshBottom(CargoSearchBody cargoSearchBody) {

        mCargoSearchBody = cargoSearchBody;

        mPresenter.getLocationData(mEndStandard, mEndType, false, false, mCargoSearchBody);
    }

    @Override
    public void hideAllDialog() {

        mCargoStart.setVisibility(View.GONE);
        mCargoEnd.setVisibility(View.GONE);
        mCargoVehicle.setVisibility(View.GONE);
    }

    @Override
    public void doCallItem(String mobile) {

        mPresenter.goCallOwner(mobile);
    }

    @Override
    public void doClickCargoDetail(String id) {

        mPresenter.goCargoDetail(id);
    }

    @OnClick({
            R.id.start_location,
            R.id.location_single_back,
            R.id.end_location,
            R.id.unload_back,
            R.id.unload_confirm,
            R.id.search,
            R.id.option_multi_confirm
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.start_location: {

                if (mCargoStart.getVisibility() == View.GONE) {

                    mCargoStart.setVisibility(View.VISIBLE);
                    mCargoEnd.setVisibility(View.GONE);
                    mCargoVehicle.setVisibility(View.GONE);

                } else if (mCargoStart.getVisibility() == View.VISIBLE) {

                    hideAllDialog();
                }
            }
            break;

            case R.id.location_single_back: {

                mStartType--;

                mPresenter.getLocationData(mStartStandard, mStartType, true, true, mCargoSearchBody);
            }
            break;

            case R.id.end_location: {

                if (mCargoEnd.getVisibility() == View.GONE) {

                    mCargoStart.setVisibility(View.GONE);
                    mCargoEnd.setVisibility(View.VISIBLE);
                    mCargoVehicle.setVisibility(View.GONE);

                } else if (mCargoEnd.getVisibility() == View.VISIBLE) {

                    hideAllDialog();
                }
            }
            break;

            case R.id.unload_back: {

                mEndType--;

                mPresenter.getLocationData(mEndStandard, mEndType, true, false, mCargoSearchBody);
            }
            break;

            case R.id.unload_confirm: {

                hideAllDialog();

                refreshData();
            }
            break;

            case R.id.search: {

                if (mCargoVehicle.getVisibility() == View.GONE) {

                    mCargoStart.setVisibility(View.GONE);
                    mCargoEnd.setVisibility(View.GONE);
                    mCargoVehicle.setVisibility(View.VISIBLE);

                } else if (mCargoVehicle.getVisibility() == View.VISIBLE) {

                    hideAllDialog();
                }
            }
            break;

            case R.id.option_multi_confirm: {

                hideAllDialog();

                refreshData();
            }
            break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent refreshEvent) {

        refreshData();
    }
}
