package com.xmw.qiyun.ui.adapter.common;

import android.content.Context;
import android.view.View;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.event.InformationEvent;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerSearchBody;
import com.xmw.qiyun.data.model.net.cargo.CargoSearchBody;
import com.xmw.qiyun.data.model.net.map.MapSearchBody;
import com.xmw.qiyun.data.model.net.publish.PublishBody;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.user.UserInfo;
import com.xmw.qiyun.data.model.net.user.VerifyCompanyInfo;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.cargo.cargoList.CargoListContract;
import com.xmw.qiyun.ui.cargo.cargoOwnerList.CargoOwnerListContract;
import com.xmw.qiyun.ui.information.InformationContract;
import com.xmw.qiyun.ui.map.MapContract;
import com.xmw.qiyun.ui.publish.PublishContract;
import com.xmw.qiyun.ui.verify.verifyCompany.VerifyCompanyContract;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/9/1.
 */

public class LocationSingleAdapter extends Adapter<LocationItem> {

    private Context mContext;

    private int mLocationType;
    private PublishContract.View mPublishView;
    private PublishContract.Presenter mPublishPresenter;
    private PublishBody mPublishBody;

    private CargoListContract.View mCargoListView;
    private CargoListContract.Presenter mCargoListPresenter;
    private CargoSearchBody mCargoSearchBody;

    private CargoOwnerListContract.View mCargoOwnerListView;
    private CargoOwnerListContract.Presenter mCargoOwnerListPresenter;
    private CargoOwnerSearchBody mCargoOwnerSearchBody;

    private VerifyCompanyContract.View mVerifyCompanyView;
    private VerifyCompanyContract.Presenter mVerifyCompanyPresenter;
    private VerifyCompanyInfo mVerifyCompanyInfo;

    private InformationContract.Presenter mInformationPresenter;
    private UserInfo mUserInfo;

    private MapContract.View mMapView;
    private MapContract.Presenter mMapPresenter;
    private MapSearchBody mMapSearchBody;

    private int mDataType;
    private int mPageType;

    private static final int EXTRA_FROM_PUBLISH = 0;
    private static final int EXTRA_FROM_CARGO = 1;
    private static final int EXTRA_FROM_CARGO_OWNER = 2;
    private static final int EXTRA_FROM_MAP = 3;
    private static final int EXTRA_FROM_VERIFY = 4;
    private static final int EXTRA_FROM_INFO = 5;

    private List<Standard> provinceList = new ArrayList<>(GsonImpl.init().toObject(StandardManager.getProvince(), ProvinceBean.class).getData());

    public LocationSingleAdapter(Context context,
                                 PublishContract.View view,
                                 PublishContract.Presenter presenter,
                                 int dataType,
                                 int locationType,
                                 PublishBody publishBody,
                                 List<LocationItem> data) {

        super(context, data, R.layout.item_location);

        mContext = context;
        mPublishView = view;
        mPublishPresenter = presenter;
        mDataType = dataType;
        mLocationType = locationType;
        mPublishBody = publishBody;

        mPageType = EXTRA_FROM_PUBLISH;
    }

    public LocationSingleAdapter(Context context,
                                 CargoListContract.View view,
                                 CargoListContract.Presenter presenter,
                                 int dataType,
                                 CargoSearchBody cargoSearchBody,
                                 List<LocationItem> data) {

        super(context, data, R.layout.item_location);

        mContext = context;
        mCargoListView = view;
        mCargoListPresenter = presenter;
        mDataType = dataType;
        mCargoSearchBody = cargoSearchBody;

        mPageType = EXTRA_FROM_CARGO;
    }

    public LocationSingleAdapter(Context context,
                                 CargoOwnerListContract.View view,
                                 CargoOwnerListContract.Presenter presenter,
                                 int dataType,
                                 CargoOwnerSearchBody cargoOwnerSearchBody,
                                 List<LocationItem> data) {

        super(context, data, R.layout.item_location);

        mContext = context;
        mCargoOwnerListView = view;
        mCargoOwnerListPresenter = presenter;
        mDataType = dataType;
        mCargoOwnerSearchBody = cargoOwnerSearchBody;

        mPageType = EXTRA_FROM_CARGO_OWNER;
    }

    public LocationSingleAdapter(Context context,
                                 MapContract.View view,
                                 MapContract.Presenter presenter,
                                 int dataType,
                                 MapSearchBody mapSearchBody,
                                 List<LocationItem> data) {

        super(context, data, R.layout.item_location);

        mContext = context;
        mMapView = view;
        mMapPresenter = presenter;
        mDataType = dataType;
        mMapSearchBody = mapSearchBody;

        mPageType = EXTRA_FROM_MAP;
    }

    public LocationSingleAdapter(Context context,
                                 VerifyCompanyContract.View view,
                                 VerifyCompanyContract.Presenter presenter,
                                 int dataType,
                                 VerifyCompanyInfo verifyCompanyInfo,
                                 List<LocationItem> data) {

        super(context, data, R.layout.item_location);

        mContext = context;
        mVerifyCompanyView = view;
        mVerifyCompanyPresenter = presenter;
        mDataType = dataType;
        mVerifyCompanyInfo = verifyCompanyInfo;

        mPageType = EXTRA_FROM_VERIFY;
    }

    public LocationSingleAdapter(Context context,
                                 InformationContract.Presenter presenter,
                                 int dataType,
                                 UserInfo userInfo,
                                 List<LocationItem> data) {

        super(context, data, R.layout.item_location);

        mContext = context;
        mInformationPresenter = presenter;
        mDataType = dataType;
        mUserInfo = userInfo;

        mPageType = EXTRA_FROM_INFO;
    }

    @Override
    protected void convert(final AdapterHelper helper, final LocationItem item) {

        if (mPageType == EXTRA_FROM_CARGO | mPageType == EXTRA_FROM_CARGO_OWNER) {

            helper.setText(R.id.item_title, helper.getPosition() == 0 && mDataType != 1 ? "全" + item.getStandard().getValue() : item.getStandard().getValue());

        } else {

            helper.setText(R.id.item_title, helper.getPosition() == 0 && mDataType == 3 ? "全" + item.getStandard().getValue() : item.getStandard().getValue());
        }

        helper.setTextColor(R.id.item_title, item.isHasSelected() ? mContext.getResources().getColor(R.color.blue) : mContext.getResources().getColor(R.color.text3));
        helper.setBackgroundColor(R.id.item_wrap, item.isHasSelected() ? mContext.getResources().getColor(R.color.blue) : mContext.getResources().getColor(R.color.divider));

        helper.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (mDataType) {

                    default:
                        break;

                    //处于省列表中
                    case 1: {

                        switch (mPageType) {

                            default:
                                break;

                            case EXTRA_FROM_PUBLISH: {

                                mPublishPresenter.getLocationSingleData(item.getStandard(), mDataType + 1, false, mLocationType == 0, mPublishBody);
                            }
                            break;

                            case EXTRA_FROM_CARGO: {

                                if (helper.getPosition() == 0) {

                                    mCargoSearchBody.setLoadRegionType(mDataType);
                                    mCargoSearchBody.setLoadRegionId("");

                                    mCargoListView.updateBodyFromLoad(mCargoSearchBody, mContext.getString(R.string.cargo_nation));
                                    mCargoListPresenter.getLocationData(item.getStandard(), mDataType, false, true, mCargoSearchBody);

                                } else {

                                    mCargoListPresenter.getLocationData(item.getStandard(), mDataType + 1, false, true, mCargoSearchBody);
                                }
                            }
                            break;

                            case EXTRA_FROM_CARGO_OWNER: {

                                if (helper.getPosition() == 0) {

                                    mCargoOwnerSearchBody.setRegionType(mDataType);
                                    mCargoOwnerSearchBody.setRegionId("");

                                    mCargoOwnerListView.updateBody(mCargoOwnerSearchBody);
                                    mCargoOwnerListPresenter.getLocationData(item.getStandard(), mDataType, false, mCargoOwnerSearchBody);

                                } else {

                                    mCargoOwnerListPresenter.getLocationData(item.getStandard(), mDataType + 1, false, mCargoOwnerSearchBody);
                                }
                            }
                            break;

                            case EXTRA_FROM_MAP: {

                                mMapPresenter.getLocationData(item.getStandard(), mDataType + 1, false, mMapSearchBody);
                            }
                            break;

                            case EXTRA_FROM_VERIFY: {

                                mVerifyCompanyPresenter.getLocationData(item.getStandard(), mDataType + 1, false, mVerifyCompanyInfo);
                            }
                            break;

                            case EXTRA_FROM_INFO: {

                                mInformationPresenter.getLocationData(item.getStandard(), mDataType + 1, false, mUserInfo);
                            }
                            break;
                        }
                    }
                    break;

                    //处于市列表中
                    case 2: {

                        switch (mPageType) {

                            default:
                                break;

                            case EXTRA_FROM_PUBLISH: {

                                mPublishPresenter.getLocationSingleData(item.getStandard(), mDataType + 1, false, mLocationType == 0, mPublishBody);
                            }
                            break;

                            case EXTRA_FROM_CARGO: {

                                if (helper.getPosition() == 0) {

                                    mCargoSearchBody.setLoadRegionType(mDataType - 1);
                                    mCargoSearchBody.setLoadRegionId(item.getStandard().getId());

                                    mCargoListView.updateBodyFromLoad(mCargoSearchBody, item.getStandard().getValue());
                                    mCargoListPresenter.getLocationData(item.getStandard(), mDataType, false, true, mCargoSearchBody);

                                } else {

                                    mCargoListPresenter.getLocationData(item.getStandard(), mDataType + 1, false, true, mCargoSearchBody);
                                }
                            }
                            break;

                            case EXTRA_FROM_CARGO_OWNER: {

                                if (helper.getPosition() == 0) {

                                    mCargoOwnerSearchBody.setRegionType(mDataType - 1);
                                    mCargoOwnerSearchBody.setRegionId(item.getStandard().getId());

                                    mCargoOwnerListView.updateBody(mCargoOwnerSearchBody);
                                    mCargoOwnerListPresenter.getLocationData(item.getStandard(), mDataType, false, mCargoOwnerSearchBody);

                                } else {

                                    mCargoOwnerListPresenter.getLocationData(item.getStandard(), mDataType + 1, false, mCargoOwnerSearchBody);
                                }
                            }
                            break;

                            case EXTRA_FROM_MAP: {

                                mMapPresenter.getLocationData(item.getStandard(), mDataType + 1, false, mMapSearchBody);
                            }
                            break;

                            case EXTRA_FROM_VERIFY: {

                                mVerifyCompanyPresenter.getLocationData(item.getStandard(), mDataType + 1, false, mVerifyCompanyInfo);
                            }
                            break;

                            case EXTRA_FROM_INFO: {

                                mInformationPresenter.getLocationData(item.getStandard(), mDataType + 1, false, mUserInfo);
                            }
                            break;
                        }
                    }
                    break;

                    //处于区列表中
                    case 3: {

                        for (Standard standard : provinceList) {

                            if (standard.getId().equals(item.getProvinceId())) {

                                item.setProvinceValue(standard.getValue());

                                break;
                            }
                        }

                        switch (mPageType) {

                            default:
                                break;

                            case EXTRA_FROM_PUBLISH: {

                                if (mLocationType == 0) {

                                    mPublishBody.setLoadProvinceId(item.getProvinceId());
                                    mPublishBody.setLoadProvince_Value(item.getProvinceValue());
                                    mPublishBody.setLoadCityId(item.getCityId());
                                    mPublishBody.setLoadCity_Value(item.getCityValue());
                                    mPublishBody.setLoadCountyId(helper.getPosition() == 0 ? null : item.getCountyId());
                                    mPublishBody.setLoadCounty_Value(helper.getPosition() == 0 ? null : item.getCountyValue());
                                    mPublishBody.setLoadPlace(item.getProvinceValue() + item.getCityValue() + (helper.getPosition() == 0 ? "" : item.getCountyValue()));
                                }

                                if (mLocationType == 1) {

                                    mPublishBody.setUnloadProvinceId(item.getProvinceId());
                                    mPublishBody.setUnloadProvince_Value(item.getProvinceValue());
                                    mPublishBody.setUnloadCityId(item.getCityId());
                                    mPublishBody.setUnloadCity_Value(item.getCityValue());
                                    mPublishBody.setUnloadCountyId(helper.getPosition() == 0 ? null : item.getCountyId());
                                    mPublishBody.setUnloadCounty_Value(helper.getPosition() == 0 ? null : item.getCountyValue());
                                    mPublishBody.setUnloadPlace(item.getProvinceValue() + item.getCityValue() + (helper.getPosition() == 0 ? "" : item.getCountyValue()));
                                }

                                mPublishView.updatePublishBody(mPublishBody);
                            }
                            break;

                            case EXTRA_FROM_CARGO: {

                                if (helper.getPosition() == 0) {

                                    mCargoSearchBody.setLoadRegionType(mDataType - 1);
                                    mCargoSearchBody.setLoadRegionId(item.getStandard().getId());

                                } else {

                                    mCargoSearchBody.setLoadRegionType(mDataType);
                                    mCargoSearchBody.setLoadRegionId(item.getStandard().getId());
                                }

                                mCargoListView.updateBodyFromLoad(mCargoSearchBody, item.getStandard().getValue());
                                mCargoListPresenter.getLocationData(data.get(0).getStandard(), 3, false, true, mCargoSearchBody);
                            }
                            break;

                            case EXTRA_FROM_CARGO_OWNER: {

                                if (helper.getPosition() == 0) {

                                    mCargoOwnerSearchBody.setRegionType(mDataType - 1);
                                    mCargoOwnerSearchBody.setRegionId(item.getStandard().getId());

                                } else {

                                    mCargoOwnerSearchBody.setRegionType(mDataType);
                                    mCargoOwnerSearchBody.setRegionId(item.getStandard().getId());
                                }

                                mCargoOwnerListView.updateBody(mCargoOwnerSearchBody);
                                mCargoOwnerListPresenter.getLocationData(data.get(0).getStandard(), 3, false, mCargoOwnerSearchBody);
                            }
                            break;

                            case EXTRA_FROM_MAP: {

                                if (helper.getPosition() == 0) {

                                    mMapSearchBody.setProvince(item.getProvinceValue());
                                    mMapSearchBody.setCity(item.getStandard().getValue());
                                    mMapSearchBody.setDistrict("");

                                } else {

                                    mMapSearchBody.setProvince(item.getProvinceValue());
                                    mMapSearchBody.setCity(item.getCityValue());
                                    mMapSearchBody.setDistrict(item.getStandard().getValue());
                                }

                                mMapView.updateBody(mMapSearchBody);
                                mMapPresenter.getLocationData(data.get(0).getStandard(), 3, false, mMapSearchBody);
                            }
                            break;

                            case EXTRA_FROM_VERIFY: {

                                mVerifyCompanyInfo.setProvinceId(item.getProvinceId());
                                mVerifyCompanyInfo.setProvince_Value(item.getProvinceValue());
                                mVerifyCompanyInfo.setCityId(item.getCityId());
                                mVerifyCompanyInfo.setCity_Value(item.getCityValue());
                                mVerifyCompanyInfo.setCountyId(helper.getPosition() == 0 ? null : item.getCountyId());
                                mVerifyCompanyInfo.setCounty_Value(helper.getPosition() == 0 ? null : item.getCountyValue());

                                mVerifyCompanyView.updateVerifyCompanyInfo(mVerifyCompanyInfo);
                            }
                            break;

                            case EXTRA_FROM_INFO: {

                                mUserInfo.setProvinceId(item.getProvinceId());
                                mUserInfo.setProvince_Value(item.getProvinceValue());
                                mUserInfo.setCityId(item.getCityId());
                                mUserInfo.setCity_Value(item.getCityValue());
                                mUserInfo.setCountyId(helper.getPosition() == 0 ? null : item.getCountyId());
                                mUserInfo.setCounty_Value(helper.getPosition() == 0 ? null : item.getCountyValue());

                                EventBus.getDefault().post(new InformationEvent(mUserInfo));
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        });
    }
}
