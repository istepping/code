package com.xmw.qiyun.ui.publish;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseFragment;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.event.PublishBodyEvent;
import com.xmw.qiyun.data.model.event.PublishMileageEvent;
import com.xmw.qiyun.data.model.event.PublishStatusEvent;
import com.xmw.qiyun.data.model.event.ReLoginUpdateEvent;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.local.StandardItem;
import com.xmw.qiyun.data.model.net.publish.PublishBody;
import com.xmw.qiyun.data.model.net.publish.PublishBodyList;
import com.xmw.qiyun.data.model.net.standard.GoodsUnitBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.adapter.publish.PublishPagerAdapter;
import com.xmw.qiyun.ui.adapter.publish.PublishRegularAdapter;
import com.xmw.qiyun.ui.adapter.common.LocationMultiAdapter;
import com.xmw.qiyun.ui.adapter.common.LocationSingleAdapter;
import com.xmw.qiyun.ui.adapter.common.LocationTopAdapter;
import com.xmw.qiyun.ui.adapter.common.OptionAdapter;
import com.xmw.qiyun.ui.publish.publishStreamBy.PublishStreamByFragment;
import com.xmw.qiyun.ui.publish.publishStreamIn.PublishStreamInFragment;
import com.xmw.qiyun.ui.publish.publishStreamStop.PublishStreamStopFragment;
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
 * Created by mac on 2017/8/3.
 */

@Route(RouterUtil.ROUTER_PUBLISH)
public class PublishFragment extends BaseFragment implements PublishContract.View {

    @BindView(R.id.publish_cargo)
    TextView mPublishCargo;
    @BindView(R.id.publish_stream)
    TextView mPublishStream;
    @BindView(R.id.publish_regular)
    TextView mPublishRegular;
    @BindView(R.id.publish_cargo_wrap)
    View mPublishCargoWrap;
    @BindView(R.id.publish_stream_wrap)
    View mPublishStreamWrap;
    @BindView(R.id.publish_regular_wrap)
    View mPublishRegularWrap;

    /*
    PublishCargo
    */

    @BindView(R.id.publish_cargo_start_input)
    TextView mStart;
    @BindView(R.id.publish_cargo_start_detail_input)
    TextView mStartDetail;
    @BindView(R.id.publish_cargo_end_input)
    TextView mEnd;
    @BindView(R.id.publish_cargo_end_detail_input)
    TextView mEndDetail;
    @BindView(R.id.publish_cargo_map_input)
    TextView mMap;
    @BindView(R.id.publish_cargo_vehicle_input)
    TextView mVehicle;
    @BindView(R.id.publish_cargo_type_input)
    TextView mType;
    @BindView(R.id.publish_cargo_num_input)
    TextView mNum;
    @BindView(R.id.publish_cargo_num_type_input)
    TextView mNumType;
    @BindView(R.id.publish_cargo_num_switch)
    Switch mSwitch;
    @BindView(R.id.publish_cargo_num_switch_value)
    TextView mSwitchValue;
    @BindView(R.id.publish_cargo_fee_input)
    TextView mFee;
    @BindView(R.id.publish_cargo_fee_type_input)
    TextView mFeeType;
    @BindView(R.id.publish_cargo_load_fee_input)
    TextView mLoadFee;
    @BindView(R.id.publish_cargo_unload_fee_input)
    TextView mUnloadFee;
    @BindView(R.id.publish_cargo_load_input)
    TextView mLoad;
    @BindView(R.id.publish_cargo_pay_input)
    TextView mPay;
    @BindView(R.id.publish_cargo_remark_input)
    TextView mRemark;
    @BindView(R.id.publish_cargo_other_input)
    TextView mOther;
    @BindView(R.id.publish_cargo_resend_input)
    TextView mResend;
    @BindView(R.id.publish_cargo_regular_input)
    TextView mRegular;
    @BindView(R.id.publish_cargo_city_input)
    TextView mCity;

    @BindView(R.id.location_multi_dialog)
    View mLocationMultiDialog;
    @BindView(R.id.location_multi_limit)
    TextView mLocationMultiLimit;
    @BindView(R.id.location_multi_back)
    View mLocationMultiBack;
    @BindView(R.id.location_multi_top_list)
    GridView mLocationMultiTopList;
    @BindView(R.id.location_multi_bottom_list)
    GridView mLocationMultiBottomList;

    @BindView(R.id.location_single_dialog)
    View mLocationSingleDialog;
    @BindView(R.id.location_single_back)
    View mLocationSingleBack;
    @BindView(R.id.location_single_list)
    GridView mLocationSingleList;

    @BindView(R.id.option_multi_dialog)
    View mOptionMultiDialog;
    @BindView(R.id.option_multi_top_title)
    TextView mOptionMultiTopTitle;
    @BindView(R.id.option_multi_top_list)
    ScrollableGridView mOptionMultiTopList;
    @BindView(R.id.option_multi_bottom_title)
    TextView mOptionMultiBottomTitle;
    @BindView(R.id.option_multi_bottom_list)
    ScrollableGridView mOptionMultiBottomList;
    @BindView(R.id.option_multi_cancel)
    View mOptionMultiBottomCancel;
    @BindView(R.id.option_single_dialog)
    View mOptionSingleDialog;
    @BindView(R.id.option_single_list)
    GridView mOptionSingleList;

    /*
    PublishStream
    */

    @BindView(R.id.publish_stream_tab)
    TabLayout mPublishStreamTab;
    @BindView(R.id.publish_stream_pager)
    ViewPager mPublishStreamPager;

    /*PublishRegular*/
    @BindView(R.id.publish_regular_list)
    SuperListview mList;

    PublishContract.Presenter mPresenter;

    /*
    publishCargo
    */

    private int mCurrentIndex;
    private boolean isShowDialog = false;

    private int mDataType = 1;
    private Standard mStandard = new Standard();

    private PublishBody mPublishBody = new PublishBody();
    private PublishBody mPublishBodyCache = new PublishBody();

    public static final int EXTRA_EDIT_START = 0;
    public static final int EXTRA_EDIT_START_DETAIL = 1;
    public static final int EXTRA_EDIT_END = 2;
    public static final int EXTRA_EDIT_END_DETAIL = 3;
    public static final int EXTRA_EDIT_VEHICLE = 4;
    public static final int EXTRA_EDIT_TYPE = 5;
    public static final int EXTRA_EDIT_NUM = 6;
    public static final int EXTRA_EDIT_NUM_TYPE = 7;
    public static final int EXTRA_EDIT_FEE = 8;
    public static final int EXTRA_EDIT_FEE_TYPE = 9;
    public static final int EXTRA_EDIT_LOAD_FEE = 10;
    public static final int EXTRA_EDIT_UNLOAD_FEE = 11;
    public static final int EXTRA_EDIT_LOAD = 12;
    public static final int EXTRA_EDIT_PAY = 13;
    public static final int EXTRA_EDIT_REMARK = 14;
    public static final int EXTRA_EDIT_RESEND = 15;
    public static final int EXTRA_EDIT_REGULAR = 16;
    public static final int EXTRA_EDIT_CITY = 17;

    public static final int EXTRA_LOCATION_PROVINCE = 1;
    public static final int EXTRA_LOCATION_CITY = 2;
    public static final int EXTRA_LOCATION_COUNTY = 3;

    public static final int EXTRA_OPTION_VEHICLE = 0;
    public static final int EXTRA_OPTION_RESEND = 1;

    public static final int EXTRA_OPTION_GOODS_TYPE = 0;
    public static final int EXTRA_OPTION_GOODS_UNIT = 1;
    public static final int EXTRA_OPTION_ASSEMBLY = 2;
    public static final int EXTRA_OPTION_PAYMENT_METHOD = 3;
    public static final int EXTRA_OPTION_REMARK = 4;

    /*
    publishRegular
    */

    private int mPage;
    private int mTotalSize;

    @Override
    public void setPresenter(PublishContract.Presenter presenter) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_publish, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        init();

        initPublishCargo();

        initPublishStream();

        initPublishRegular();
    }

    @Override
    public boolean onBackPressed() {

        if (isShowDialog) {

            hideAllDialog();

            return false;

        } else {

            return true;
        }
    }

    @Override
    public void init() {

        mPresenter = new PublishPresentImpl(getContext(), this);

        changePage(0);
    }

    @Override
    public void initPublishCargo() {

        mPublishBody.setGoodsUnitId(GsonImpl.init().toObject(StandardManager.getGoodsUnit(), GoodsUnitBean.class).getData().get(0).getId());
        mPublishBody.setGoodsUnit_Value(GsonImpl.init().toObject(StandardManager.getGoodsUnit(), GoodsUnitBean.class).getData().get(0).getValue());
        mPublishBody.setFreightUnitId(GsonImpl.init().toObject(StandardManager.getGoodsUnit(), GoodsUnitBean.class).getData().get(0).getId());
        mPublishBody.setFreightUnit_Value(GsonImpl.init().toObject(StandardManager.getGoodsUnit(), GoodsUnitBean.class).getData().get(0).getValue());

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                mSwitchValue.setText(b ? getString(R.string.publish_cargo_num_multi) : getString(R.string.publish_cargo_num_single));

                mPublishBody.setRange(b);

                if (mPublishBody.isRange()) {

                    mNum.setText(
                            mPublishBody.getGoodsNumberMin() == 0 | mPublishBody.getGoodsNumberMax() == 0 ?
                                    "" :
                                    new StringBuilder(String.valueOf(mPublishBody.getGoodsNumberMin())).append(" - ").append(String.valueOf(mPublishBody.getGoodsNumberMax())));

                } else {

                    mNum.setText(mPublishBody.getGoodsNumber() == 0 ? "" : String.valueOf(mPublishBody.getGoodsNumber()));
                }

                createOtherRemark(mPublishBody);
            }
        });

        mLocationMultiLimit.setText(getString(R.string.location_has_select_three));
    }

    @Override
    public void initPublishStream() {

        mPresenter.getStatus();
    }

    @Override
    public void initPublishRegular() {

        doRegularRefresh();

        //下拉刷新
        mList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                doRegularRefresh();
            }
        });

        //上拉加载
        mList.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {

                if (mTotalSize != mList.getAdapter().getCount()) {

                    mPage++;
                    mPresenter.getRegularData(mPage);

                } else {

                    stopRegularRefresh();
                }
            }
        });
    }

    @Override
    public void changePage(int index) {

        mPublishCargo.setTextColor(getResources().getColor(R.color.white));
        mPublishStream.setTextColor(getResources().getColor(R.color.white));
        mPublishRegular.setTextColor(getResources().getColor(R.color.white));

        mPublishCargo.setBackground(getResources().getDrawable(R.drawable.shape_left));
        mPublishStream.setBackgroundColor(getResources().getColor(R.color.blue));
        mPublishRegular.setBackground(getResources().getDrawable(R.drawable.shape_right));

        mPublishCargoWrap.setVisibility(View.GONE);
        mPublishStreamWrap.setVisibility(View.GONE);
        mPublishRegularWrap.setVisibility(View.GONE);

        switch (index) {

            default:
                break;

            case 0: {

                mPublishCargo.setTextColor(getResources().getColor(R.color.blue));
                mPublishCargo.setBackground(getResources().getDrawable(R.drawable.shape_left_select));
                mPublishCargoWrap.setVisibility(View.VISIBLE);
            }
            break;

            case 1: {

                mPublishStream.setTextColor(getResources().getColor(R.color.blue));
                mPublishStream.setBackgroundColor(getResources().getColor(R.color.white));
                mPublishStreamWrap.setVisibility(View.VISIBLE);
            }
            break;

            case 2: {

                mPublishRegular.setTextColor(getResources().getColor(R.color.blue));
                mPublishRegular.setBackground(getResources().getDrawable(R.drawable.shape_right_select));
                mPublishRegularWrap.setVisibility(View.VISIBLE);
            }
            break;
        }
    }

    @Override
    public void editRegularData(PublishBody publishBody) {

        changePage(0);

        mPublishBody = publishBody;

        mStart.setText(
                new StringBuilder(publishBody.getLoadProvince_Value()).
                        append(publishBody.getLoadCity_Value()).
                        append(CommonUtil.isNullOrEmpty(publishBody.getLoadCounty_Value()) ? "" : publishBody.getLoadCounty_Value()));

        mStartDetail.setText(publishBody.getLoadAddress());

        mEnd.setText(
                new StringBuilder(publishBody.getUnloadProvince_Value()).
                        append(publishBody.getUnloadCity_Value()).
                        append(CommonUtil.isNullOrEmpty(publishBody.getUnloadCounty_Value()) ? "" : publishBody.getUnloadCounty_Value()));

        mEndDetail.setText(publishBody.getUnloadAddress());

        mMap.setText(new StringBuilder().append(String.valueOf(publishBody.getMileage())).append(getString(R.string.publish_cargo_map_km)));

        mVehicle.setText(
                new StringBuilder(CommonUtil.isNullOrEmpty(publishBody.getVehicleLength()) ? "不限" : publishBody.getVehicleLength()).
                        append(",").
                        append(CommonUtil.isNullOrEmpty(publishBody.getVehicleType_Value()) ? "不限" : publishBody.getVehicleType_Value()));

        mType.setText(publishBody.getGoodsType());

        if (publishBody.isRange()) {

            mSwitch.setChecked(true);
            mSwitchValue.setText(getString(R.string.publish_cargo_num_multi));
            mNum.setText(
                    mPublishBody.getGoodsNumberMin() == 0 | mPublishBody.getGoodsNumberMax() == 0 ?
                            "" :
                            new StringBuilder(String.valueOf(mPublishBody.getGoodsNumberMin())).append(" - ").append(String.valueOf(mPublishBody.getGoodsNumberMax())));

        } else {

            mSwitch.setChecked(false);
            mSwitchValue.setText(getString(R.string.publish_cargo_num_single));
            mNum.setText(publishBody.getGoodsNumber() == 0 ? "" : String.valueOf(publishBody.getGoodsNumber()));
        }

        mNumType.setText(publishBody.getGoodsUnit_Value());
        mFeeType.setText(publishBody.getFreightUnit_Value());
        mFee.setText(publishBody.getFreight() == 0 ? "" : String.valueOf(publishBody.getFreight()));
        mLoadFee.setText(publishBody.getLoadVehicleCost() == 0 ? "" : String.valueOf(publishBody.getLoadVehicleCost()));
        mUnloadFee.setText(publishBody.getUnloadVehicleCost() == 0 ? "" : String.valueOf(publishBody.getUnloadVehicleCost()));
        mLoad.setText(publishBody.getAssemblyWay_Value());
        mPay.setText(publishBody.getPaymentMethod_Value());
        mRemark.setText(publishBody.getOtherRemark_Value());

        mResend.setText(publishBody.isRepeat() ?
                new StringBuilder(String.valueOf(publishBody.getRepeatCount())).
                        append(getString(R.string.publish_cargo_resend_count)).
                        append(String.valueOf(publishBody.getRepeatSpacingMinute())).
                        append(getString(R.string.publish_cargo_resend_time)) : getString(R.string.publish_cargo_resend));

        mResend.setTextColor(publishBody.isRepeat() ? getResources().getColor(R.color.white) : getResources().getColor(R.color.blue));
        mResend.setBackgroundColor(publishBody.isRepeat() ? getResources().getColor(R.color.blue) : getResources().getColor(R.color.white));

        mRegular.setTextColor(publishBody.isSaveOften() ? getResources().getColor(R.color.white) : getResources().getColor(R.color.blue));
        mRegular.setBackgroundColor(publishBody.isSaveOften() ? getResources().getColor(R.color.blue) : getResources().getColor(R.color.white));

        mCity.setTextColor(publishBody.getNotLookCitys().size() != 0 ? getResources().getColor(R.color.white) : getResources().getColor(R.color.blue));
        mCity.setBackgroundColor(publishBody.getNotLookCitys().size() != 0 ? getResources().getColor(R.color.blue) : getResources().getColor(R.color.white));

        createOtherRemark(publishBody);
    }

    @Override
    public void updatePublishBody(PublishBody publishBody) {

        hideAllDialog();

        createOtherRemark(publishBody);

        switch (mCurrentIndex) {

            default:
                break;

            case EXTRA_EDIT_START: {

                mStart.setText(
                        new StringBuilder(publishBody.getLoadProvince_Value()).
                                append(publishBody.getLoadCity_Value()).
                                append(CommonUtil.isNullOrEmpty(publishBody.getLoadCounty_Value()) ? "" : publishBody.getLoadCounty_Value()));

                if (!CommonUtil.isNullOrEmpty(mEnd.getText().toString())) {

                    //mPresenter.getMileage(publishBody);
                }
            }
            break;

            case EXTRA_EDIT_START_DETAIL: {

                mStartDetail.setText(publishBody.getLoadAddress());
            }
            break;

            case EXTRA_EDIT_END: {

                mEnd.setText(
                        new StringBuilder(publishBody.getUnloadProvince_Value()).
                                append(publishBody.getUnloadCity_Value()).
                                append(CommonUtil.isNullOrEmpty(publishBody.getUnloadCounty_Value()) ? "" : publishBody.getUnloadCounty_Value()));

                if (!CommonUtil.isNullOrEmpty(mStart.getText().toString())) {

                    //mPresenter.getMileage(publishBody);
                }
            }
            break;

            case EXTRA_EDIT_END_DETAIL: {

                mEndDetail.setText(publishBody.getUnloadAddress());
            }
            break;

            case EXTRA_EDIT_VEHICLE: {

                mVehicle.setText(
                        new StringBuilder(CommonUtil.isNullOrEmpty(publishBody.getVehicleLength()) ? "不限" : publishBody.getVehicleLength()).
                                append(",").
                                append(CommonUtil.isNullOrEmpty(publishBody.getVehicleType_Value()) ? "不限" : publishBody.getVehicleType_Value()));
            }
            break;

            case EXTRA_EDIT_TYPE: {

                mType.setText(publishBody.getGoodsType());
            }
            break;

            case EXTRA_EDIT_NUM: {

                if (publishBody.isRange()) {

                    mPublishBody.setGoodsNumber(0);

                    mNum.setText(
                            mPublishBody.getGoodsNumberMin() == 0 | mPublishBody.getGoodsNumberMax() == 0 ?
                                    "" :
                                    new StringBuilder(String.valueOf(mPublishBody.getGoodsNumberMin())).append(" - ").append(String.valueOf(mPublishBody.getGoodsNumberMax())));

                } else {

                    mPublishBody.setGoodsNumberMin(0);
                    mPublishBody.setGoodsNumberMax(0);

                    mNum.setText(publishBody.getGoodsNumber() == 0 ? "" : String.valueOf(publishBody.getGoodsNumber()));
                }
            }
            break;

            case EXTRA_EDIT_NUM_TYPE: {

                mNumType.setText(publishBody.getGoodsUnit_Value());
                mFeeType.setText(publishBody.getFreightUnit_Value());
            }
            break;

            case EXTRA_EDIT_FEE: {

                mFee.setText(publishBody.getFreight() == 0 ? "" : String.valueOf(publishBody.getFreight()));
            }
            break;

            case EXTRA_EDIT_FEE_TYPE: {

                mNumType.setText(publishBody.getGoodsUnit_Value());
                mFeeType.setText(publishBody.getFreightUnit_Value());
            }
            break;

            case EXTRA_EDIT_LOAD_FEE: {

                mLoadFee.setText(publishBody.getLoadVehicleCost() == 0 ? "" : String.valueOf(publishBody.getLoadVehicleCost()));
            }
            break;

            case EXTRA_EDIT_UNLOAD_FEE: {

                mUnloadFee.setText(publishBody.getUnloadVehicleCost() == 0 ? "" : String.valueOf(publishBody.getUnloadVehicleCost()));
            }
            break;

            case EXTRA_EDIT_LOAD: {

                mLoad.setText(publishBody.getAssemblyWay_Value());
            }
            break;

            case EXTRA_EDIT_PAY: {

                mPay.setText(publishBody.getPaymentMethod_Value());
            }
            break;

            case EXTRA_EDIT_REMARK: {

                mRemark.setText(publishBody.getOtherRemark_Value());
            }
            break;

            case EXTRA_EDIT_RESEND: {

                mResend.setText(publishBody.isRepeat() ?
                        new StringBuilder(String.valueOf(publishBody.getRepeatCount())).
                                append(getString(R.string.publish_cargo_resend_count)).
                                append(String.valueOf(publishBody.getRepeatSpacingMinute())).
                                append(getString(R.string.publish_cargo_resend_time)) : getString(R.string.publish_cargo_resend));

                mResend.setTextColor(publishBody.isRepeat() ? getResources().getColor(R.color.white) : getResources().getColor(R.color.blue));
                mResend.setBackgroundColor(publishBody.isRepeat() ? getResources().getColor(R.color.blue) : getResources().getColor(R.color.white));
            }
            break;

            case EXTRA_EDIT_REGULAR: {

                mRegular.setTextColor(publishBody.isSaveOften() ? getResources().getColor(R.color.white) : getResources().getColor(R.color.blue));
                mRegular.setBackgroundColor(publishBody.isSaveOften() ? getResources().getColor(R.color.blue) : getResources().getColor(R.color.white));
            }
            break;

            case EXTRA_EDIT_CITY: {

                mCity.setTextColor(publishBody.getNotLookCitys().size() != 0 ? getResources().getColor(R.color.white) : getResources().getColor(R.color.blue));
                mCity.setBackgroundColor(publishBody.getNotLookCitys().size() != 0 ? getResources().getColor(R.color.blue) : getResources().getColor(R.color.white));
            }
            break;
        }
    }

    @Override
    public void updateOptionMulti(PublishBody publishBody) {

        mPublishBodyCache = publishBody;
    }

    @Override
    public void updateMultiBackButton(boolean isVisible) {

        mLocationMultiBack.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void updateSingleBackButton(boolean isVisible) {

        mLocationSingleBack.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void refreshLocationTop(PublishBody publishBody) {

        mPublishBody = publishBody;

        mPresenter.getLocationMultiData(null, EXTRA_LOCATION_CITY, true, publishBody);
    }

    @Override
    public void refreshLocationBottom(PublishBody publishBody) {

        mPublishBody = publishBody;

        mPresenter.getLocationMultiData(mStandard, mDataType, false, publishBody);
    }

    @Override
    public void showLocationSelectList(List<LocationItem> locationItemList) {

        mLocationMultiTopList.setAdapter(new LocationTopAdapter(getContext(), this, mPublishBody, locationItemList));
    }

    @Override
    public void showLocationMultiList(List<LocationItem> locationItemList, Standard standard, int type) {

        mDataType = type;
        mStandard = standard;

        mLocationMultiDialog.setVisibility(View.VISIBLE);
        mLocationMultiBottomList.setAdapter(new LocationMultiAdapter(getContext(), this, mPresenter, mPublishBody, type, locationItemList));
    }

    @Override
    public void showLocationSingleList(List<LocationItem> locationItemList, Standard standard, int type) {

        mDataType = type;
        mStandard = standard;

        mLocationSingleDialog.setVisibility(View.VISIBLE);
        mLocationSingleList.setAdapter(new LocationSingleAdapter(getContext(), this, mPresenter, type, mCurrentIndex == EXTRA_EDIT_START ? 0 : 1, mPublishBody, locationItemList));
    }

    @Override
    public void showOptionMultiList(List<StandardItem> topList, List<StandardItem> bottomList) {

        mOptionMultiDialog.setVisibility(View.VISIBLE);
        mOptionMultiBottomCancel.setVisibility(mCurrentIndex == EXTRA_EDIT_VEHICLE ? View.GONE : View.VISIBLE);

        mOptionMultiTopTitle.setText(mCurrentIndex == EXTRA_EDIT_VEHICLE ? getString(R.string.dialog_vehicle_length) : getString(R.string.dialog_resend_count));
        mOptionMultiBottomTitle.setText(mCurrentIndex == EXTRA_EDIT_VEHICLE ? getString(R.string.dialog_vehicle_type) : getString(R.string.dialog_resend_time));

        mOptionMultiTopList.setAdapter(new OptionAdapter(getContext(), this, mPresenter, mPublishBody, topList));
        mOptionMultiBottomList.setAdapter(new OptionAdapter(getContext(), this, mPresenter, mPublishBody, bottomList));
    }

    @Override
    public void showOptionSingleList(List<StandardItem> standardItemList) {

        mOptionSingleDialog.setVisibility(View.VISIBLE);
        mOptionSingleList.setAdapter(new OptionAdapter(getContext(), this, mPresenter, mPublishBody, standardItemList));
    }

    @Override
    public void showOptionSingleList(List<StandardItem> standardItemList, int unitType) {

        mOptionSingleDialog.setVisibility(View.VISIBLE);
        mOptionSingleList.setAdapter(new OptionAdapter(getContext(), this, mPresenter, mPublishBody, standardItemList, unitType));
    }

    @Override
    public void createOtherRemark(PublishBody publishBody) {

        StringBuilder otherRemark = new StringBuilder();

        List<String> otherRemarkList = new ArrayList<>();

        otherRemarkList.add(
                (CommonUtil.isNullOrEmpty(publishBody.getLoadProvince_Value()) ? "" : publishBody.getLoadProvince_Value())
                        + (CommonUtil.isNullOrEmpty(publishBody.getLoadCity_Value()) ? "" : publishBody.getLoadCity_Value())
                        + (CommonUtil.isNullOrEmpty(publishBody.getLoadCounty_Value()) ? "" : publishBody.getLoadCounty_Value())
                        + (CommonUtil.isNullOrEmpty(publishBody.getLoadAddress()) ? "" : publishBody.getLoadAddress())
                        + (!CommonUtil.isNullOrEmpty(publishBody.getLoadProvince_Value()) & !CommonUtil.isNullOrEmpty(publishBody.getUnloadProvince_Value()) ? " --> " : "")
                        + (CommonUtil.isNullOrEmpty(publishBody.getUnloadProvince_Value()) ? "" : publishBody.getUnloadProvince_Value())
                        + (CommonUtil.isNullOrEmpty(publishBody.getUnloadCity_Value()) ? "" : publishBody.getUnloadCity_Value())
                        + (CommonUtil.isNullOrEmpty(publishBody.getUnloadCounty_Value()) ? "" : publishBody.getUnloadCounty_Value())
                        + (CommonUtil.isNullOrEmpty(publishBody.getUnloadAddress()) ? "" : publishBody.getUnloadAddress()));

        if (!CommonUtil.isNullOrEmpty(publishBody.getGoodsType()))
            otherRemarkList.add(publishBody.getGoodsType());

        if (publishBody.isRange() & publishBody.getGoodsNumberMin() != 0 & publishBody.getGoodsNumberMax() != 0)

            otherRemarkList.add(getString(R.string.remark_goods_num) + publishBody.getGoodsNumberMin() + "-" + publishBody.getGoodsNumberMax() + publishBody.getGoodsUnit_Value());

        if (!publishBody.isRange() & publishBody.getGoodsNumber() != 0)

            otherRemarkList.add(getString(R.string.remark_goods_num) + publishBody.getGoodsNumber() + publishBody.getGoodsUnit_Value());

        if (!CommonUtil.isNullOrEmpty(publishBody.getVehicleLength()))

            otherRemarkList.add(getString(R.string.remark_vehicle_length) + publishBody.getVehicleLength());

        if (!CommonUtil.isNullOrEmpty(publishBody.getVehicleType_Value()))

            otherRemarkList.add(getString(R.string.remark_vehicle_type) + publishBody.getVehicleType_Value());

        if (publishBody.getFreight() != 0)

            otherRemarkList.add(getString(R.string.remark_goods_fee) + publishBody.getFreight() + "元/" + publishBody.getFreightUnit_Value());

        if (publishBody.getLoadVehicleCost() != 0)

            otherRemarkList.add(getString(R.string.remark_load_fee) + publishBody.getLoadVehicleCost() + "元");

        if (publishBody.getUnloadVehicleCost() != 0)

            otherRemarkList.add(getString(R.string.remark_unload_fee) + publishBody.getUnloadVehicleCost() + "元");

        if (!CommonUtil.isNullOrEmpty(publishBody.getAssemblyWay_Value()))

            otherRemarkList.add(getString(R.string.remark_assembly) + publishBody.getAssemblyWay_Value());

        if (!CommonUtil.isNullOrEmpty(publishBody.getPaymentMethod_Value()))

            otherRemarkList.add(getString(R.string.remark_payment) + publishBody.getPaymentMethod_Value());

        if (!CommonUtil.isNullOrEmpty(publishBody.getOtherRemark_Value()))

            otherRemarkList.add(getString(R.string.remark_remark) + publishBody.getOtherRemark_Value());

        if (otherRemarkList.get(0).length() == 0) {

            otherRemarkList.remove(0);
        }

        for (int i = 0; i < otherRemarkList.size(); i++) {

            otherRemark.append(i == 0 ? otherRemarkList.get(i) : ", " + otherRemarkList.get(i));
        }

        mOther.setText(otherRemark);
    }

    @Override
    public void hideAllDialog() {

        isShowDialog = false;

        mLocationMultiDialog.setVisibility(View.GONE);
        mLocationSingleDialog.setVisibility(View.GONE);
        mOptionMultiDialog.setVisibility(View.GONE);
        mOptionSingleDialog.setVisibility(View.GONE);
    }

    @Override
    public void clear() {

        mPublishBody = new PublishBody();
        mPublishBody.setGoodsUnitId(GsonImpl.init().toObject(StandardManager.getGoodsUnit(), GoodsUnitBean.class).getData().get(0).getId());
        mPublishBody.setGoodsUnit_Value(GsonImpl.init().toObject(StandardManager.getGoodsUnit(), GoodsUnitBean.class).getData().get(0).getValue());
        mPublishBody.setFreightUnitId(GsonImpl.init().toObject(StandardManager.getGoodsUnit(), GoodsUnitBean.class).getData().get(0).getId());
        mPublishBody.setFreightUnit_Value(GsonImpl.init().toObject(StandardManager.getGoodsUnit(), GoodsUnitBean.class).getData().get(0).getValue());

        mSwitch.setChecked(false);

        mStart.setText("");
        mStartDetail.setText("");
        mEnd.setText("");
        mEndDetail.setText("");
        mMap.setText("");
        mVehicle.setText("");
        mType.setText("");
        mNum.setText("");
        mNumType.setText(mPublishBody.getGoodsUnit_Value());
        mFeeType.setText(mPublishBody.getFreightUnit_Value());
        mFee.setText("");
        mLoadFee.setText("");
        mUnloadFee.setText("");
        mLoad.setText("");
        mPay.setText("");
        mRemark.setText("");
        mResend.setText(getString(R.string.publish_cargo_resend));
        mResend.setTextColor(getResources().getColor(R.color.blue));
        mResend.setBackgroundColor(getResources().getColor(R.color.white));
        mRegular.setTextColor(getResources().getColor(R.color.blue));
        mRegular.setBackgroundColor(getResources().getColor(R.color.white));
        mCity.setTextColor(getResources().getColor(R.color.blue));
        mCity.setBackgroundColor(getResources().getColor(R.color.white));
        mOther.setText("");
    }

    @Override
    public void showRegularList(PublishBodyList publishBodyList) {

        mTotalSize = publishBodyList.getTotal();

        if (mPage == 1) {

            mList.setAdapter(new PublishRegularAdapter(getContext(), mPresenter, publishBodyList.getResultData()));

        } else {

            ((PublishRegularAdapter) mList.getAdapter()).addAll(publishBodyList.getResultData());
            ((PublishRegularAdapter) mList.getAdapter()).notifyDataSetChanged();
        }

        stopRegularRefresh();
    }

    @Override
    public void doRegularRefresh() {

        mPage = 1;
        mPresenter.getRegularData(mPage);
    }

    @Override
    public void stopRegularRefresh() {

        mList.hideProgress();
        mList.hideMoreProgress();
        mList.setLoadingMore(false);
    }

    @OnClick({
            R.id.publish_cargo_start_input,
            R.id.publish_cargo_start_detail_input,
            R.id.publish_cargo_end_input,
            R.id.publish_cargo_end_detail_input,
            R.id.publish_cargo_vehicle_input,
            R.id.publish_cargo_type_input,
            R.id.publish_cargo_num_input,
            R.id.publish_cargo_num_type_input,
            R.id.publish_cargo_fee_input,
            R.id.publish_cargo_fee_type_input,
            R.id.publish_cargo_load_fee_input,
            R.id.publish_cargo_unload_fee_input,
            R.id.publish_cargo_load_input,
            R.id.publish_cargo_pay_input,
            R.id.publish_cargo_remark_input,
            R.id.publish_cargo_resend_input,
            R.id.publish_cargo_regular_input,
            R.id.publish_cargo_city_input,
            R.id.publish_cargo_confirm
    })
    public void onViewClicked(View view) {

        if (isShowDialog) return;

        switch (view.getId()) {

            default:
                break;

            case R.id.publish_cargo_start_input: {

                isShowDialog = true;

                mCurrentIndex = EXTRA_EDIT_START;
                mPresenter.getLocationSingleData(null, EXTRA_LOCATION_PROVINCE, false, true, mPublishBody);
            }
            break;

            case R.id.publish_cargo_start_detail_input: {

                mCurrentIndex = EXTRA_EDIT_START_DETAIL;
                mPresenter.goEditStartDetail(mPublishBody);
            }
            break;

            case R.id.publish_cargo_end_input: {

                isShowDialog = true;

                mCurrentIndex = EXTRA_EDIT_END;
                mPresenter.getLocationSingleData(null, EXTRA_LOCATION_PROVINCE, false, false, mPublishBody);
            }
            break;

            case R.id.publish_cargo_end_detail_input: {

                mCurrentIndex = EXTRA_EDIT_END_DETAIL;
                mPresenter.goEditEndDetail(mPublishBody);
            }
            break;

            case R.id.publish_cargo_vehicle_input: {

                isShowDialog = true;

                mCurrentIndex = EXTRA_EDIT_VEHICLE;
                mPresenter.getOptionMultiData(mPublishBody, EXTRA_OPTION_VEHICLE);
            }
            break;

            case R.id.publish_cargo_type_input: {

                isShowDialog = true;

                mCurrentIndex = EXTRA_EDIT_TYPE;
                mPresenter.getOptionSingleData(mPublishBody, EXTRA_OPTION_GOODS_TYPE);
            }
            break;

            case R.id.publish_cargo_num_input: {

                mCurrentIndex = EXTRA_EDIT_NUM;
                mPresenter.goEditNum(mPublishBody);
            }
            break;

            case R.id.publish_cargo_num_type_input: {

                isShowDialog = true;

                mCurrentIndex = EXTRA_EDIT_NUM_TYPE;
                mPresenter.getOptionSingleData(mPublishBody, EXTRA_OPTION_GOODS_UNIT, 0);
            }
            break;

            case R.id.publish_cargo_fee_input: {

                mCurrentIndex = EXTRA_EDIT_FEE;
                mPresenter.goEditFee(mPublishBody);
            }
            break;

            case R.id.publish_cargo_fee_type_input: {

                isShowDialog = true;

                mCurrentIndex = EXTRA_EDIT_FEE_TYPE;
                mPresenter.getOptionSingleData(mPublishBody, EXTRA_OPTION_GOODS_UNIT, 1);
            }
            break;

            case R.id.publish_cargo_load_fee_input: {

                mCurrentIndex = EXTRA_EDIT_LOAD_FEE;
                mPresenter.goEditLoadFee(mPublishBody);
            }
            break;

            case R.id.publish_cargo_unload_fee_input: {

                mCurrentIndex = EXTRA_EDIT_UNLOAD_FEE;
                mPresenter.goEditUnLoadFee(mPublishBody);
            }
            break;

            case R.id.publish_cargo_load_input: {

                isShowDialog = true;

                mCurrentIndex = EXTRA_EDIT_LOAD;
                mPresenter.getOptionSingleData(mPublishBody, EXTRA_OPTION_ASSEMBLY);
            }
            break;

            case R.id.publish_cargo_pay_input: {

                isShowDialog = true;

                mCurrentIndex = EXTRA_EDIT_PAY;
                mPresenter.getOptionSingleData(mPublishBody, EXTRA_OPTION_PAYMENT_METHOD);
            }
            break;

            case R.id.publish_cargo_remark_input: {

                isShowDialog = true;

                mCurrentIndex = EXTRA_EDIT_REMARK;
                mPresenter.getOptionSingleData(mPublishBody, EXTRA_OPTION_REMARK);
            }
            break;

            case R.id.publish_cargo_resend_input: {

                isShowDialog = true;

                mCurrentIndex = EXTRA_EDIT_RESEND;
                mPresenter.getOptionMultiData(mPublishBody, EXTRA_OPTION_RESEND);
            }
            break;

            case R.id.publish_cargo_regular_input: {

                mCurrentIndex = EXTRA_EDIT_REGULAR;

                mPublishBody.setSaveOften(!mPublishBody.isSaveOften());
                updatePublishBody(mPublishBody);
            }
            break;

            case R.id.publish_cargo_city_input: {

                isShowDialog = true;

                mCurrentIndex = EXTRA_EDIT_CITY;
                mPresenter.getLocationMultiData(null, EXTRA_LOCATION_PROVINCE, false, mPublishBody);
                mPresenter.getLocationMultiData(null, EXTRA_LOCATION_CITY, true, mPublishBody);
            }
            break;

            case R.id.publish_cargo_confirm: {

                mPresenter.doPublish(mPublishBody);
            }
            break;
        }
    }

    @OnClick({
            R.id.publish_cargo,
            R.id.publish_stream,
            R.id.publish_regular,
            R.id.location_multi_back,
            R.id.location_multi_ok,
            R.id.option_multi_cancel,
            R.id.option_multi_ok,
            R.id.location_single_back,
            R.id.location_single_cancel,
            R.id.option_single_cancel
    })
    public void onDialogClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.publish_cargo: {

                changePage(0);
            }
            break;

            case R.id.publish_stream: {

                changePage(1);
            }
            break;

            case R.id.publish_regular: {

                changePage(2);
            }
            break;

            case R.id.location_multi_back: {

                mDataType--;
                mPresenter.getLocationMultiData(null, mDataType, false, mPublishBody);
            }
            break;

            case R.id.location_multi_ok: {

                updatePublishBody(mPublishBody);
            }
            break;

            case R.id.option_multi_cancel: {

                mPublishBody.setRepeat(false);
                mPublishBody.setRepeatCount(0);
                mPublishBody.setRepeatSpacingMinute(0);

                updatePublishBody(mPublishBody);

                hideAllDialog();
            }
            break;

            case R.id.option_multi_ok: {

                switch (mCurrentIndex) {

                    default:
                        break;

                    case EXTRA_EDIT_VEHICLE: {

                        mPublishBody.setVehicleLength(mPublishBodyCache.getVehicleLength());
                        mPublishBody.setVehicleTypeId(mPublishBodyCache.getVehicleTypeId());
                        mPublishBody.setVehicleType_Value(mPublishBodyCache.getVehicleType_Value());
                    }
                    break;

                    case EXTRA_EDIT_RESEND: {

                        mPublishBody.setRepeat(mPublishBodyCache.isRepeat());
                        mPublishBody.setRepeatCount(mPublishBodyCache.isRepeat() ? mPublishBodyCache.getRepeatCount() : 0);
                        mPublishBody.setRepeatSpacingMinute(mPublishBodyCache.isRepeat() ? mPublishBodyCache.getRepeatSpacingMinute() : 0);
                    }
                    break;
                }

                updatePublishBody(mPublishBody);
            }
            break;

            case R.id.location_single_back: {

                mDataType--;
                mPresenter.getLocationSingleData(mStandard, mDataType, true, mCurrentIndex == EXTRA_EDIT_START, mPublishBody);
            }
            break;

            case R.id.location_single_cancel:
            case R.id.option_single_cancel: {

                hideAllDialog();
            }
            break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReLoginUpdateEvent(ReLoginUpdateEvent reLoginUpdateEvent) {

        initPublishStream();

        initPublishRegular();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPublishStatusEvent(PublishStatusEvent publishStatusEvent) {

        List<Fragment> fragmentList = new ArrayList<>();

        if (publishStatusEvent.isShow()) {
            fragmentList.add(new PublishStreamByFragment());
        }

        fragmentList.add(new PublishStreamInFragment());
        fragmentList.add(new PublishStreamStopFragment());

        mPublishStreamPager.setAdapter(new PublishPagerAdapter(this.getChildFragmentManager(), getContext(), fragmentList));
        mPublishStreamTab.setupWithViewPager(mPublishStreamPager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPublishMileageEvent(PublishMileageEvent publishMileageEvent) {

        mPublishBody.setMileage(publishMileageEvent.getMileage());

        mMap.setText(new StringBuilder().append(String.valueOf(publishMileageEvent.getMileage())).append(getString(R.string.publish_cargo_map_km)));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPublishBodyEvent(PublishBodyEvent publishBodyEvent) {

        mPublishBody = publishBodyEvent.getPublishBody();

        updatePublishBody(mPublishBody);
    }
}
