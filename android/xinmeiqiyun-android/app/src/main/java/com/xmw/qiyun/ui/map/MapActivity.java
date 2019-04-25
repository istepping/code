package com.xmw.qiyun.ui.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MultiPointItem;
import com.amap.api.maps.model.MultiPointOverlay;
import com.amap.api.maps.model.MultiPointOverlayOptions;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.manager.PositionManager;
import com.xmw.qiyun.data.model.event.MapEvent;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.local.PositionItem;
import com.xmw.qiyun.data.model.local.StandardItem;
import com.xmw.qiyun.data.model.net.cargo.CargoOwner;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerList;
import com.xmw.qiyun.data.model.net.map.MapSearchBody;
import com.xmw.qiyun.data.model.net.map.TruckMarker;
import com.xmw.qiyun.data.model.net.map.TruckMarkerList;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.adapter.common.LocationSingleAdapter;
import com.xmw.qiyun.ui.adapter.common.OptionAdapter;
import com.xmw.qiyun.ui.adapter.map.MapCargoOwnerAdapter;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.image.ImageUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;
import com.xmw.qiyun.view.ScrollableGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongyuan on 2017/10/10.
 */

@Route(RouterUtil.ROUTER_MAP)
public class MapActivity extends BaseActivity implements MapContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.title_left)
    View mTitleLeft;
    @BindView(R.id.title_back)
    View mTitleBack;
    @BindView(R.id.title_location)
    TextView mTitleLocation;
    @BindView(R.id.title_button)
    View mTitleButton;
    @BindView(R.id.title_button_text)
    TextView mTitleButtonText;

    @BindView(R.id.truck_title)
    View mTruckTitle;
    @BindView(R.id.city)
    TextView mCity;
    @BindView(R.id.search)
    TextView mSearch;

    @BindView(R.id.location_single_dialog)
    View mLocationView;
    @BindView(R.id.location_single_list)
    GridView mLocationList;
    @BindView(R.id.location_single_back)
    View mLocationBack;

    @BindView(R.id.option_multi_dialog)
    View mVehicle;
    @BindView(R.id.option_multi_top_list)
    ScrollableGridView mVehicleTopList;
    @BindView(R.id.option_multi_bottom_list)
    ScrollableGridView mVehicleBottomList;

    @BindView(R.id.map)
    MapView mMap;
    @BindView(R.id.map_marker)
    View mMapMarker;
    @BindView(R.id.map_below)
    View mMapBelow;
    @BindView(R.id.map_detail)
    TextView mMapDetail;

    @BindView(R.id.truck_container)
    View mTruckContainer;
    @BindView(R.id.truck_avatar)
    ImageView mTruckAvatar;
    @BindView(R.id.truck_name)
    TextView mTruckName;
    @BindView(R.id.truck_num)
    TextView mTruckNum;
    @BindView(R.id.truck_property)
    TextView mTruckProperty;
    @BindView(R.id.truck_cities)
    TextView mTruckCities;
    @BindView(R.id.truck_time)
    TextView mTruckTime;

    @BindView(R.id.cargo_owner_container)
    View mCargoOwnerContainer;
    @BindView(R.id.cargo_owner_avatar)
    ImageView mCargoOwnerAvatar;
    @BindView(R.id.cargo_owner_name)
    TextView mCargoOwnerName;
    @BindView(R.id.cargo_owner_company)
    TextView mCargoOwnerCompany;

    MapContract.Presenter mPresenter;

    private AMap aMap;
    private Marker aMarker;
    private GeocodeSearch aGeocodeSearch;
    private TruckMarker mTruckMarker;
    private CargoOwner mCargoOwnerMarker;

    private boolean isSearchMode;
    private boolean isMarkerMode;
    private boolean isFirstLoad = true;

    private int mType = 0;
    private double mLatitude;
    private double mLongitude;

    private boolean isChangeOptions = false;

    private int mDataType = 1;
    private Standard mStandard = new Standard();

    private MapEvent mMapEvent;
    private MapSearchBody mMapSearchBody = new MapSearchBody();

    private CargoOwner mCargoOwner;
    private String mCityCode;
    private List<String> cityCodeList = new ArrayList<>();
    private List<MultiPointItem> multiPointItemList = new ArrayList<>();

    public static final String EXTRA_MAP_TYPE = "EXTRA_MAP_TYPE";
    public static final String EXTRA_MAP_TITLE = "EXTRA_MAP_TITLE";
    public static final String EXTRA_MAP_VALUE = "EXTRA_MAP_VALUE";

    public static final int EXTRA_MAP_TRUCK = 0;
    public static final int EXTRA_MAP_CARGO = 1;
    public static final int EXTRA_MAP_CARGO_OWNER = 2;
    public static final int EXTRA_MAP_OTHER = 3;

    public static final String EXTRA_MAP_LATITUDE = "EXTRA_MAP_LATITUDE";
    public static final String EXTRA_MAP_LONGITUDE = "EXTRA_MAP_LONGITUDE";

    @Override
    public void setPresenter(MapContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ButterKnife.bind(this);

        mMap.onCreate(savedInstanceState);

        init();
    }

    @Override
    protected void onResume() {

        super.onResume();

        mMap.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();

        mMap.onPause();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        mMap.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        mMap.onSaveInstanceState(outState);
    }

    @Override
    public void init() {

        mType = getIntent().getExtras().getInt(EXTRA_MAP_TYPE);

        switch (mType) {

            default:
                break;

            case EXTRA_MAP_TRUCK: {

                mTitleButtonText.setText(getIntent().getExtras().getString(EXTRA_MAP_TITLE));

                mTitle.setText(getString(R.string.map_vehicle_title));
                mTitleLeft.setVisibility(View.INVISIBLE);
                mTruckTitle.setVisibility(View.VISIBLE);
                mMapMarker.setVisibility(View.GONE);
                mMapBelow.setVisibility(View.GONE);

            }
            break;

            case EXTRA_MAP_CARGO: {

                mTitleButtonText.setText(getIntent().getExtras().getString(EXTRA_MAP_TITLE));

                mTitle.setText(getString(R.string.map_cargo_title));
                mTitleBack.setVisibility(View.GONE);
                mTitleLocation.setVisibility(View.VISIBLE);
                mTruckTitle.setVisibility(View.GONE);
                mMapMarker.setVisibility(View.GONE);
                mMapBelow.setVisibility(View.GONE);
            }
            break;

            case EXTRA_MAP_CARGO_OWNER: {

                mTitleButtonText.setText(getIntent().getExtras().getString(EXTRA_MAP_TITLE));
                mCargoOwner = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_MAP_VALUE), CargoOwner.class);

                mTitle.setText(getString(R.string.map_cargo_title));
                mTitleBack.setVisibility(View.GONE);
                mTitleLocation.setVisibility(View.VISIBLE);
                mTruckTitle.setVisibility(View.GONE);
                mMapMarker.setVisibility(View.GONE);
                mMapBelow.setVisibility(View.GONE);
            }
            break;

            case EXTRA_MAP_OTHER: {

                mLatitude = getIntent().getExtras().getDouble(EXTRA_MAP_LATITUDE);
                mLongitude = getIntent().getExtras().getDouble(EXTRA_MAP_LONGITUDE);

                mTitle.setText(getString(R.string.verify_company_location));
                mTitleBack.setVisibility(View.VISIBLE);
                mTitleLocation.setVisibility(View.GONE);
                mTitleButton.setVisibility(View.INVISIBLE);
                mTruckTitle.setVisibility(View.GONE);
            }
            break;
        }

        showMapFirst();
    }

    @Override
    public void showMapFirst() {

        //初始化数据
        mPresenter = new MapPresentImpl(this, this);

        PositionItem positionItem;

        if (mType == EXTRA_MAP_CARGO) {

            positionItem = new PositionItem(39.331605, 112.432827);

        } else if (mType == EXTRA_MAP_CARGO_OWNER) {

            positionItem = new PositionItem(mCargoOwner.getLatitude(), mCargoOwner.getLongitude());
            mMapSearchBody.setGoodsOwnerId(mCargoOwner.getId());

        } else {

            positionItem = !CommonUtil.isNullOrEmpty(PositionManager.getPositionDetail()) ?
                    GsonImpl.init().toObject(PositionManager.getPositionDetail(), PositionItem.class) :
                    new PositionItem(39.904030, 116.407526);
        }

        //初始化搜索模型
        isSearchMode = true;
        mMapSearchBody.setProvince(positionItem.getProvince());
        mMapSearchBody.setCity(positionItem.getCity());
        mMapSearchBody.setLatitude(mLatitude != 0 ? mLatitude : positionItem.getLatitude());
        mMapSearchBody.setLongitude(mLongitude != 0 ? mLongitude : positionItem.getLongitude());

        //初始化地图
        aMap = mMap.getMap();
        aMap.getUiSettings().setCompassEnabled(true);
        aMap.getUiSettings().setScaleControlsEnabled(true);
        aMap.getUiSettings().setGestureScaleByMapCenter(true);
        aMap.getUiSettings().setTiltGesturesEnabled(false);
        aMap.getUiSettings().setRotateGesturesEnabled(false);
        aMap.getUiSettings().setZoomControlsEnabled(false);

        //移动至定位地区
        switch (mType) {

            default: {

                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(mMapSearchBody.getLatitude(), mMapSearchBody.getLongitude())));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            }
            break;

            case EXTRA_MAP_CARGO: {

                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(mMapSearchBody.getLatitude(), mMapSearchBody.getLongitude())));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(7));
            }
            break;

            case EXTRA_MAP_CARGO_OWNER: {

                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(mMapSearchBody.getLatitude(), mMapSearchBody.getLongitude())));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(13));

                aMap.setInfoWindowAdapter(new MapCargoOwnerAdapter(this, mPresenter));
            }
            break;
        }

        //缩放拖拽监听
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

                if (isMarkerMode) {

                    isMarkerMode = false;

                } else {

                    hideWindowView();
                }
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {

                mMapSearchBody.setLatitude(cameraPosition.target.latitude);
                mMapSearchBody.setLongitude(cameraPosition.target.longitude);

                reGetGeo();
            }
        });

        //地图点击监听
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                hideWindowView();
            }
        });

        //标记点击监听（目标标记）
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                isMarkerMode = true;

                mTruckContainer.setVisibility(View.GONE);
                mCargoOwnerContainer.setVisibility(View.GONE);

                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(mCargoOwner.getLatitude(), mCargoOwner.getLongitude())));
                aMarker.showInfoWindow();

                return true;
            }
        });

        //标记点击监听（普通标记）
        aMap.setOnMultiPointClickListener(new AMap.OnMultiPointClickListener() {
            @Override
            public boolean onPointClick(MultiPointItem multiPointItem) {

                isMarkerMode = true;

                if (aMarker != null) aMarker.hideInfoWindow();

                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(multiPointItem.getLatLng().latitude, multiPointItem.getLatLng().longitude)));
                initWindowView(multiPointItem);

                return true;
            }
        });

        //初始化地图编码/反编码
        aGeocodeSearch = new GeocodeSearch(this);

        //地图编码/反编码监听
        aGeocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();

                mCityCode = regeocodeAddress.getCityCode();

                mMapSearchBody.setProvince(regeocodeAddress.getProvince());
                mMapSearchBody.setCity(regeocodeAddress.getCity());

                //主动搜索 & 搜索到区
                mMapSearchBody.setDistrict(isSearchMode & !CommonUtil.isNullOrEmpty(mMapSearchBody.getDistrict()) ? mMapSearchBody.getDistrict() : "");

                //更新地址选择器，定制选择器
                mDataType = 1;
                mStandard = null;
                mPresenter.getLocationData(null, mDataType, false, mMapSearchBody);
                mPresenter.getVehicleData(mMapSearchBody);
                mPresenter.getTitleData(mMapSearchBody);

                //判断若是基本资料，认证页面，则更新显示栏，更新传递模型
                if (mType == EXTRA_MAP_OTHER) {

                    mMapDetail.setText(regeocodeAddress.getFormatAddress());

                    if (mMapEvent == null) {

                        mMapEvent = new MapEvent(mMapSearchBody.getLatitude(), mMapSearchBody.getLongitude(), regeocodeAddress.getFormatAddress());

                    } else {

                        mMapEvent.setLatitude(mMapSearchBody.getLatitude());
                        mMapEvent.setLongitude(mMapSearchBody.getLongitude());
                        mMapEvent.setLocation(regeocodeAddress.getFormatAddress());
                    }

                    return;
                }

                //车主定制搜索判断
                if (isChangeOptions) {

                    isChangeOptions = false;

                    cityCodeList.clear();

                    multiPointItemList.clear();

                    aMap.clear();

                    //更新标记数据
                    mPresenter.getMarkerData(mMapSearchBody, mType);

                } else {

                    //判断是否已经加载过该城市数据，若已存在，则不执行后续方法
                    if (cityCodeList.contains(mCityCode)) return;

                    cityCodeList.add(mCityCode);

                    //更新标记数据
                    mPresenter.getMarkerData(mMapSearchBody, mType);
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                mLocationView.setVisibility(View.GONE);
                mVehicle.setVisibility(View.GONE);

                GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);

                mMapSearchBody.setLatitude(geocodeAddress.getLatLonPoint().getLatitude());
                mMapSearchBody.setLongitude(geocodeAddress.getLatLonPoint().getLongitude());

                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(mMapSearchBody.getLatitude(), mMapSearchBody.getLongitude())));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(12));

                if (mType == EXTRA_MAP_TRUCK) {

                    reGetGeo();
                }
            }
        });
    }

    @Override
    public void showMarkers(TruckMarkerList truckMarkerList) {

        isSearchMode = false;

        MultiPointOverlayOptions overlayOptions = new MultiPointOverlayOptions();
        overlayOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_red));
        MultiPointOverlay multiPointOverlay = aMap.addMultiPointOverlay(overlayOptions);

        for (int i = 0; i < truckMarkerList.getResultData().size(); i++) {

            MultiPointItem multiPointItem = new MultiPointItem(new LatLng(truckMarkerList.getResultData().get(i).getLatitude(), truckMarkerList.getResultData().get(i).getLongitude()));
            multiPointItem.setObject(truckMarkerList.getResultData().get(i));
            multiPointItemList.add(multiPointItem);
        }

        multiPointOverlay.setItems(multiPointItemList);
    }

    @Override
    public void showMarkers(CargoOwnerList cargoOwnerList) {

        isSearchMode = false;

        if (mCargoOwner != null & multiPointItemList.size() == 0) {

            aMarker = aMap.addMarker(new MarkerOptions()
                    .title("")
                    .position(new LatLng(mCargoOwner.getLatitude(), mCargoOwner.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker))
                    .draggable(false));

            aMarker.setObject(mCargoOwner);
        }

        MultiPointOverlayOptions overlayOptions = new MultiPointOverlayOptions();
        overlayOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_red));
        MultiPointOverlay multiPointOverlay = aMap.addMultiPointOverlay(overlayOptions);

        for (int i = 0; i < cargoOwnerList.getResultData().size(); i++) {

            MultiPointItem multiPointItem = new MultiPointItem(new LatLng(cargoOwnerList.getResultData().get(i).getLatitude(), cargoOwnerList.getResultData().get(i).getLongitude()));
            multiPointItem.setObject(cargoOwnerList.getResultData().get(i));
            multiPointItemList.add(multiPointItem);
        }

        multiPointOverlay.setItems(multiPointItemList);

        if (isFirstLoad) {

            isFirstLoad = false;
            mPresenter.getOriginData(mCityCode, mMapSearchBody.getGoodsOwnerId());
        }
    }

    @Override
    public void showCityTitle(String cityTitle) {

        mTitleLocation.setText(CommonUtil.isNullOrEmpty(cityTitle) ? getString(R.string.cargo_nation) : cityTitle);
        mCity.setText(CommonUtil.isNullOrEmpty(cityTitle) ? getString(R.string.cargo_location) : cityTitle);
    }

    @Override
    public void showSearchTitle(String searchTitle) {

        mSearch.setText(CommonUtil.isNullOrEmpty(searchTitle) ? getString(R.string.cargo_search) : searchTitle);
    }

    @Override
    public void updateLocationButton(boolean showOrHide) {

        mLocationBack.setVisibility(showOrHide ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showLocationList(List<LocationItem> locationItemList, Standard standard, int type) {

        mDataType = type;
        mStandard = standard;

        mLocationList.setAdapter(new LocationSingleAdapter(this, this, mPresenter, type, mMapSearchBody, locationItemList));
    }

    @Override
    public void showVehicleLengthList(List<StandardItem> standardItemList) {

        mVehicleTopList.setAdapter(new OptionAdapter(this, this, mPresenter, mMapSearchBody, standardItemList));
    }

    @Override
    public void showVehicleTypeList(List<StandardItem> standardItemList) {

        mVehicleBottomList.setAdapter(new OptionAdapter(this, this, mPresenter, mMapSearchBody, standardItemList));
    }

    @Override
    public void updateBody(MapSearchBody mapSearchBody) {

        isSearchMode = true;

        mMapSearchBody = mapSearchBody;

        getGeo();
    }

    @Override
    public void updateBodyOfVehicle(MapSearchBody mapSearchBody) {

        mMapSearchBody = mapSearchBody;
    }

    @Override
    public void getGeo() {

        aGeocodeSearch.getFromLocationNameAsyn(new GeocodeQuery(mMapSearchBody.getProvince() + mMapSearchBody.getCity() + mMapSearchBody.getDistrict(), mMapSearchBody.getCity()));
    }

    @Override
    public void reGetGeo() {

        aGeocodeSearch.getFromLocationAsyn(new RegeocodeQuery(new LatLonPoint(mMapSearchBody.getLatitude(), mMapSearchBody.getLongitude()), 200, GeocodeSearch.AMAP));
    }

    @Override
    public void initWindowView(MultiPointItem multiPointItem) {

        switch (mType) {

            default:
                break;

            case EXTRA_MAP_TRUCK: {

                mTruckContainer.setVisibility(View.VISIBLE);
                mTruckMarker = (TruckMarker) multiPointItem.getObject();

                if (!CommonUtil.isNullOrEmpty(mTruckMarker.getHeadPortraitId())) {

                    ImageUtil.loadAvatar(this, mTruckAvatar, mTruckMarker.getHeadPortraitId(), R.drawable.default_avatar);
                }

                switch (mTruckMarker.getStatus()) {

                    default:
                        break;

                    case 1: {

                        mTruckName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_1, 0);
                    }
                    break;

                    case 2: {

                        mTruckName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_2, 0);
                    }
                    break;
                }

                mTruckName.setText(mTruckMarker.getName());
                mTruckNum.setText(CommonUtil.isNullOrEmpty(mTruckMarker.getVehicleNum()) ? getString(R.string.truck_detail_no_num) : mTruckMarker.getVehicleNum());
                mTruckProperty.setText(new StringBuilder().append(getString(R.string.map_vehicle)).append(CommonUtil.isNullOrEmpty(mTruckMarker.getVehicleProperty()) ? getString(R.string.truck_detail_no_property) : mTruckMarker.getVehicleProperty()));
                mTruckCities.setText(new StringBuilder().append(getString(R.string.map_city)).append(CommonUtil.isNullOrEmpty(mTruckMarker.getOftenCity()) ? getString(R.string.truck_detail_no_city) : mTruckMarker.getOftenCity()));
                mTruckTime.setText(new StringBuilder().append(getString(R.string.map_time)).append(mTruckMarker.getLast_Position_Time()));
            }
            break;

            case EXTRA_MAP_CARGO:
            case EXTRA_MAP_CARGO_OWNER: {

                mCargoOwnerMarker = (CargoOwner) multiPointItem.getObject();
                mCargoOwnerContainer.setVisibility(View.VISIBLE);

                if (!CommonUtil.isNullOrEmpty(mCargoOwnerMarker.getHeadPortraitId())) {

                    ImageUtil.loadAvatar(this, mCargoOwnerAvatar, mCargoOwnerMarker.getHeadPortraitId(), R.drawable.default_avatar);
                }

                mCargoOwnerName.setText(mCargoOwnerMarker.getName());

                switch (mCargoOwnerMarker.getStatus()) {

                    default:
                        break;

                    case 1: {

                        mCargoOwnerName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_1, 0);
                    }
                    break;

                    case 2: {

                        mCargoOwnerName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_2, 0);
                    }
                    break;

                    case 4: {

                        mCargoOwnerName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_4, 0);
                    }
                    break;
                }

                mCargoOwnerCompany.setText(mCargoOwnerMarker.getCompanyShortName());
            }
            break;
        }
    }

    @Override
    public void hideWindowView() {

        if (aMarker != null) aMarker.hideInfoWindow();

        mTruckContainer.setVisibility(View.GONE);
        mCargoOwnerContainer.setVisibility(View.GONE);
    }

    @OnClick({
            R.id.title_button,
            R.id.title_left,
            R.id.city,
            R.id.location_single_back,
            R.id.search,
            R.id.option_multi_confirm,
            R.id.map_confirm,
            R.id.truck_close,
            R.id.truck_page,
            R.id.truck_contact,
            R.id.cargo_owner_close,
            R.id.cargo_owner_contact
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.title_button: {

                onBackPressed();
            }
            break;

            case R.id.title_left: {

                if (mType == EXTRA_MAP_OTHER) {

                    onBackPressed();

                } else {

                    mLocationView.setVisibility(mLocationView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
            }
            break;

            case R.id.city: {

                if (mLocationView.getVisibility() == View.VISIBLE) {

                    mLocationView.setVisibility(View.GONE);
                    mVehicle.setVisibility(View.GONE);

                } else {

                    mLocationView.setVisibility(View.VISIBLE);
                    mVehicle.setVisibility(View.GONE);
                }
            }
            break;

            case R.id.location_single_back: {

                mDataType--;

                mPresenter.getLocationData(mStandard, mDataType, true, mMapSearchBody);
            }
            break;

            case R.id.search: {

                if (mVehicle.getVisibility() == View.VISIBLE) {

                    mLocationView.setVisibility(View.GONE);
                    mVehicle.setVisibility(View.GONE);

                } else {

                    mLocationView.setVisibility(View.GONE);
                    mVehicle.setVisibility(View.VISIBLE);
                }
            }
            break;

            case R.id.option_multi_confirm: {

                isSearchMode = true;

                isChangeOptions = true;

                if (CommonUtil.isNullOrEmpty(mMapSearchBody.getProvince()) & CommonUtil.isNullOrEmpty(mMapSearchBody.getCity())) {

                    NoteUtil.showToast(this, getString(R.string.toast_city_empty));

                    return;
                }

                getGeo();
            }
            break;

            case R.id.map_confirm: {

                mPresenter.goBack(mMapEvent);
            }
            break;

            case R.id.truck_close: {

                hideWindowView();
            }
            break;

            case R.id.truck_page: {

                mPresenter.goTruckDetail(mTruckMarker.getVehicleOwnerInfoId());
            }
            break;

            case R.id.truck_contact: {

                mPresenter.goContact(mTruckMarker.getMobile());
            }
            break;

            case R.id.cargo_owner_close: {

                hideWindowView();
            }
            break;

            case R.id.cargo_owner_contact: {

                mPresenter.goContact(mCargoOwnerMarker.getMobile());
            }
            break;
        }
    }
}