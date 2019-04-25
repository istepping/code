package com.xmw.qiyun.ui.cargo.cargoOwnerList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseFragment;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerList;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerSearchBody;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.ui.adapter.cargo.CargoOwnerAdapter;
import com.xmw.qiyun.ui.adapter.common.LocationSingleAdapter;
import com.xmw.qiyun.util.manage.RouterUtil;
import com.xmw.qiyun.view.ClearEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/11/1.
 */

@Route(RouterUtil.ROUTER_CARGO_OWNER_LIST)
public class CargoOwnerListFragment extends BaseFragment implements CargoOwnerListContract.View {

    @BindView(R.id.wrap)
    View mWrap;
    @BindView(R.id.location)
    TextView mLocation;
    @BindView(R.id.company)
    ClearEditText mCompany;

    @BindView(R.id.location_single_dialog)
    View mLocationView;
    @BindView(R.id.location_single_back)
    View mLocationBack;
    @BindView(R.id.location_single_list)
    GridView mLocationList;

    @BindView(R.id.cargo_owner_list)
    SuperListview mList;

    CargoOwnerListContract.Presenter mPresenter;

    private int mPage;
    private int mTotalSize;

    private int mDataType = 1;
    private Standard mStandard = new Standard();

    private CargoOwnerSearchBody mCargoOwnerSearchBody = new CargoOwnerSearchBody();

    @Override
    public void setPresenter(CargoOwnerListContract.Presenter presenter) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cargo_owner_list, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        init();
    }

    @Override
    public void init() {

        mCargoOwnerSearchBody.setRegionType(1);

        mPresenter = new CargoOwnerListPresentImpl(getContext(), this);
        mPresenter.getTitleData(mCargoOwnerSearchBody);
        mPresenter.getLocationData(null, mDataType, false, mCargoOwnerSearchBody);

        mPage = 1;
        mPresenter.getData(mPage, mCargoOwnerSearchBody);

        //隐藏软键盘
        mWrap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                hideKeyboard(mCompany);

                return false;
            }
        });

        //隐藏软键盘
        mList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                hideKeyboard(mCompany);

                return false;
            }
        });

        //输入框输入监听
        mCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                mCargoOwnerSearchBody.setCompanyShortName(mCompany.getText().toString());
            }
        });

        //输入框焦点监听
        mCompany.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b) {

                    mLocationView.setVisibility(View.GONE);

                } else {

                    hideKeyboard(view);
                }
            }
        });

        //输入框键盘监听
        mCompany.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    hideKeyboard(view);

                    mCargoOwnerSearchBody.setCompanyShortName(mCompany.getText().toString());

                    refreshData();
                }

                return false;
            }
        });

        //下拉刷新
        mList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPage = 1;
                mPresenter.getData(mPage, mCargoOwnerSearchBody);
            }
        });

        //上拉加载
        mList.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {

                if (mTotalSize != mList.getAdapter().getCount()) {

                    mPage++;
                    mPresenter.getData(mPage, mCargoOwnerSearchBody);

                } else {

                    stopRefresh();
                }
            }
        });
    }

    @Override
    public void showTitle(String title) {

        mLocation.setText(title);
    }

    @Override
    public void showList(CargoOwnerList cargoOwnerList) {

        mTotalSize = cargoOwnerList.getTotal();

        if (mPage == 1) {

            mList.setAdapter(new CargoOwnerAdapter(getContext(), mPresenter, cargoOwnerList.getResultData()));

        } else {

            ((CargoOwnerAdapter) mList.getAdapter()).addAll(cargoOwnerList.getResultData());
            ((CargoOwnerAdapter) mList.getAdapter()).notifyDataSetChanged();
        }

        stopRefresh();
    }

    @Override
    public void refreshData() {

        mPage = 1;
        mPresenter.getData(mPage, mCargoOwnerSearchBody);
        mPresenter.getTitleData(mCargoOwnerSearchBody);
    }

    @Override
    public void stopRefresh() {

        mList.hideProgress();
        mList.hideMoreProgress();
        mList.setLoadingMore(false);
    }

    @Override
    public void updateLocationButton(boolean showOrHide) {

        mLocationBack.setVisibility(showOrHide ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showLocationList(List<LocationItem> locationItemList, Standard standard, int type) {

        mDataType = type;
        mStandard = standard;

        mLocationList.setAdapter(new LocationSingleAdapter(getContext(), this, mPresenter, type, mCargoOwnerSearchBody, locationItemList));
    }

    @Override
    public void updateBody(CargoOwnerSearchBody cargoOwnerSearchBody) {

        mCargoOwnerSearchBody = cargoOwnerSearchBody;

        mLocationView.setVisibility(View.GONE);

        refreshData();
    }

    @Override
    public void hideKeyboard(View view) {

        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        view.clearFocus();
    }

    @OnClick({
            R.id.location,
            R.id.location_single_back,
            R.id.cargo_owner_list
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.location: {

                hideKeyboard(mCompany);

                mLocationView.setVisibility(mLocationView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
            break;

            case R.id.location_single_back: {

                mDataType--;

                mPresenter.getLocationData(mStandard, mDataType, true, mCargoOwnerSearchBody);
            }
            break;
        }
    }
}
