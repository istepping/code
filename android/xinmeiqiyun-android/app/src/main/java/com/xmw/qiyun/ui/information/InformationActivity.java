package com.xmw.qiyun.ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.manager.PositionManager;
import com.xmw.qiyun.data.model.event.InformationEvent;
import com.xmw.qiyun.data.model.event.MapEvent;
import com.xmw.qiyun.data.model.event.MeEvent;
import com.xmw.qiyun.data.model.event.PositionEvent;
import com.xmw.qiyun.data.model.event.RefreshEvent;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.local.PositionItem;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.user.UserInfo;
import com.xmw.qiyun.data.model.net.user.UserInfoBody;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.adapter.common.LocationSingleAdapter;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.dialog.DialogUtil;
import com.xmw.qiyun.util.image.ImageUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by hongyuan on 2017/8/28.
 */

@Route(RouterUtil.ROUTER_INFORMATION)
public class InformationActivity extends BaseActivity implements InformationContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.back)
    View mBack;
    @BindView(R.id.information_avatar_detail)
    ImageView mAvatar;
    @BindView(R.id.information_name_detail)
    TextView mName;
    @BindView(R.id.information_company_name_detail)
    TextView mCompanyName;
    @BindView(R.id.information_company_address_detail)
    TextView mCompanyAddress;
    @BindView(R.id.information_company_location_detail)
    TextView mCompanyLocation;
    @BindView(R.id.location_single_dialog)
    View mLocationDialog;
    @BindView(R.id.location_single_back)
    View mLocationBack;
    @BindView(R.id.location_single_list)
    GridView mLocationList;
    @BindView(R.id.information_confirm)
    View mConfirm;

    InformationContract.Presenter mPresenter;

    private boolean isFromLoginOrRegister = true;
    private boolean isShowDialog = false;
    private int editItemIndex;

    private UserInfo mUserInfo = new UserInfo();

    private int mDataType = 1;
    private Standard mStandard = new Standard();

    public static final int REQUEST_IMAGE = 0;
    public static final String EXTRA_FROM_PAGE = "EXTRA_FROM_PAGE";

    public static final int EXTRA_SHOW_ALL = 0;
    public static final int EXTRA_EDIT_AVATAR = 1;
    public static final int EXTRA_EDIT_NAME = 2;
    public static final int EXTRA_EDIT_COMPANY_NAME = 3;
    public static final int EXTRA_EDIT_COMPANY_ADDRESS = 4;
    public static final int EXTRA_EDIT_COMPANY_LOCATION = 5;

    @Override
    public void setPresenter(InformationContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        ButterKnife.bind(this);

        mPresenter = new InformationPresentImpl(this, this);

        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE) {

            if (resultCode == RESULT_OK) {

                String path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT).get(0);

                mPresenter.uploadAvatar(mPresenter.modifyImage(new File(path)));
            }
        }
    }

    @Override
    public void onBackPressed() {

        EventBus.getDefault().post(new MeEvent());

        finish();
    }

    @Override
    public void init() {

        mTitle.setText(getString(R.string.information_title));

        isFromLoginOrRegister = (getIntent().getExtras().getInt(EXTRA_FROM_PAGE) == 0);

        if (isFromLoginOrRegister) {

            mBack.setVisibility(View.INVISIBLE);
        }

        mPresenter.getData();
    }

    @Override
    public void getImageId(String id) {

        mUserInfo.setHeadPortraitId(id);

        ImageUtil.loadAvatar(this, mAvatar, id, R.drawable.default_avatar);
    }

    @Override
    public void getData(UserInfo userInfo) {

        mConfirm.setVisibility(userInfo.getStatus() == 1 | !userInfo.isFull() ? View.VISIBLE : View.INVISIBLE);

        editItemIndex = EXTRA_SHOW_ALL;

        mUserInfo = userInfo;

        if (mUserInfo.getLatitude() == 0 | mUserInfo.getLongitude() == 0) {

            mPresenter.doPositionService();

        } else {

            refreshData();
        }
    }

    @Override
    public void refreshData() {

        hideAllDialog();

        if ((editItemIndex == EXTRA_SHOW_ALL | editItemIndex == EXTRA_EDIT_AVATAR) & !CommonUtil.isNullOrEmpty(mUserInfo.getHeadPortraitId()))
            ImageUtil.loadAvatar(this, mAvatar, mUserInfo.getHeadPortraitId(), R.drawable.default_avatar);

        mName.setText(mUserInfo.getName());
        mCompanyName.setText(mUserInfo.getCompanyName());

        String province = CommonUtil.isNullOrEmpty(mUserInfo.getProvince_Value()) ? "" : mUserInfo.getProvince_Value();
        String city = CommonUtil.isNullOrEmpty(mUserInfo.getCity_Value()) ? "" : mUserInfo.getCity_Value();
        String county = CommonUtil.isNullOrEmpty(mUserInfo.getCounty_Value()) ? "" : mUserInfo.getCounty_Value();
        mCompanyAddress.setText(province + city + county);
        mCompanyLocation.setText(mUserInfo.getAddress());
    }

    @Override
    public void hideAllDialog() {

        isShowDialog = false;
        mLocationDialog.setVisibility(View.GONE);
    }

    @Override
    public void updateBackButton(boolean isVisible) {

        mLocationBack.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showLocationList(List<LocationItem> locationItemList, Standard standard, int type) {

        mDataType = type;
        mStandard = standard;

        mLocationDialog.setVisibility(View.VISIBLE);
        mLocationList.setAdapter(new LocationSingleAdapter(this, mPresenter, type, mUserInfo, locationItemList));
    }

    @OnClick({
            R.id.back,
            R.id.location_single_back,
            R.id.location_single_cancel
    })
    public void doDialogClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.back: {

                onBackPressed();
            }
            break;

            case R.id.location_single_back: {

                mDataType--;

                mPresenter.getLocationData(mStandard, mDataType, true, mUserInfo);
            }
            break;

            case R.id.location_single_cancel: {

                hideAllDialog();
            }
            break;
        }
    }

    @OnClick({
            R.id.information_avatar,
            R.id.information_name,
            R.id.information_company_name,
            R.id.information_company_address,
            R.id.information_company_location_detail,
            R.id.information_company_location_marker,
            R.id.information_confirm
    })
    public void onViewClicked(View view) {

        if (mUserInfo.getStatus() != 1 & mUserInfo.isFull() | isShowDialog) {

            return;
        }

        switch (view.getId()) {

            default:
                break;

            case R.id.information_avatar: {

                editItemIndex = EXTRA_EDIT_AVATAR;

                DialogUtil.INSTANCE.initPhotoDialog(this, mPresenter);
            }
            break;

            case R.id.information_name: {

                editItemIndex = EXTRA_EDIT_NAME;

                mPresenter.goEditName(mUserInfo);
            }
            break;

            case R.id.information_company_name: {

                editItemIndex = EXTRA_EDIT_COMPANY_NAME;

                mPresenter.goEditCompanyName(mUserInfo);
            }
            break;

            case R.id.information_company_address: {

                isShowDialog = true;

                editItemIndex = EXTRA_EDIT_COMPANY_ADDRESS;

                mPresenter.getLocationData(null, 1, false, mUserInfo);
            }
            break;

            case R.id.information_company_location_detail: {

                editItemIndex = EXTRA_EDIT_COMPANY_LOCATION;

                mPresenter.goEditCompanyLocation(mUserInfo);
            }
            break;

            case R.id.information_company_location_marker: {

                editItemIndex = EXTRA_EDIT_COMPANY_LOCATION;

                mPresenter.goMap(mUserInfo.getLatitude(), mUserInfo.getLongitude());
            }
            break;

            case R.id.information_confirm: {

                UserInfoBody userInfoBody = new UserInfoBody(mUserInfo.getName(),
                        mUserInfo.getCompanyName(),
                        mUserInfo.getProvinceId(),
                        mUserInfo.getCityId(),
                        mUserInfo.getCountyId(),
                        mUserInfo.getAddress(),
                        mUserInfo.getHeadPortraitId(),
                        mUserInfo.getLatitude(),
                        mUserInfo.getLongitude());

                mPresenter.doSave(userInfoBody, isFromLoginOrRegister);
            }
            break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPositionEvent(PositionEvent positionEvent) {

        if (!positionEvent.isSuccess()) {

            NoteUtil.showToast(this, getString(R.string.position_fail));
        }

        PositionItem positionItem = GsonImpl.init().toObject(PositionManager.getPositionDetail(), PositionItem.class);
        mUserInfo.setLatitude(positionItem.getLatitude() == 0 ? 39.904030 : positionItem.getLatitude());
        mUserInfo.setLongitude(positionItem.getLongitude() == 0 ? 116.407526 : positionItem.getLongitude());
        mUserInfo.setAddress(positionItem.getLocation());

        refreshData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMapEvent(MapEvent mapEvent) {

        mUserInfo.setLatitude(mapEvent.getLatitude());
        mUserInfo.setLongitude(mapEvent.getLongitude());
        mUserInfo.setAddress(mapEvent.getLocation());

        refreshData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInformationEvent(InformationEvent informationEvent) {

        mUserInfo = informationEvent.getUserInfo();

        refreshData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent refreshEvent) {

        init();
    }
}
