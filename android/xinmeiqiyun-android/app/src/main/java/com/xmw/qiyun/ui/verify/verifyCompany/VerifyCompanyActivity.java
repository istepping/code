package com.xmw.qiyun.ui.verify.verifyCompany;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.manager.PositionManager;
import com.xmw.qiyun.data.model.event.MapEvent;
import com.xmw.qiyun.data.model.event.PositionEvent;
import com.xmw.qiyun.data.model.event.VerifyCompanyEvent;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.local.PositionItem;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.user.VerifyCompanyInfo;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalAndCompany;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.adapter.common.LocationSingleAdapter;
import com.xmw.qiyun.ui.verify.VerifyActivity;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.dialog.DialogUtil;
import com.xmw.qiyun.util.image.ImageUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by hongyuan on 2017/8/29.
 */

@Route(RouterUtil.ROUTER_VERIFY_COMPANY)
public class VerifyCompanyActivity extends BaseActivity implements VerifyCompanyContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.verify_company_name_detail)
    TextView mName;
    @BindView(R.id.verify_company_alias_detail)
    TextView mAlias;
    @BindView(R.id.verify_company_license_image_detail)
    ImageView mLicense;
    @BindView(R.id.verify_company_code_detail)
    TextView mCode;
    @BindView(R.id.verify_company_address_detail)
    TextView mAddress;
    @BindView(R.id.verify_company_location_detail)
    TextView mLocation;
    @BindView(R.id.verify_company_shop_detail)
    ImageView mShop;
    @BindView(R.id.verify_company_license_true)
    CheckBox mHasLicense;
    @BindView(R.id.verify_company_license_false)
    CheckBox mNoLicense;
    @BindView(R.id.verify_company_license_image)
    View mLicenseWrap;
    @BindView(R.id.verify_company_license_divider)
    View mLicenseDivider;
    @BindView(R.id.verify_company_code)
    View mCodeWrap;
    @BindView(R.id.verify_company_code_divider)
    View mCodeDivider;
    @BindView(R.id.location_single_dialog)
    View mLocationDialog;
    @BindView(R.id.location_single_back)
    View mLocationBack;
    @BindView(R.id.location_single_list)
    GridView mLocationList;
    @BindView(R.id.confirm)
    TextView mConfirm;

    VerifyCompanyContract.Presenter mPresenter;

    private boolean hasApplied = false;
    private boolean isShowDialog = false;
    private int from;
    private int currentIndex;

    private int mDataType = 1;
    private Standard mStandard = new Standard();

    private VerifyPersonalAndCompany mVerifyPersonalAndCompany;

    public static final String EXTRA_VERIFY_FROM = "EXTRA_VERIFY_FROM";

    public static final int EXTRA_VERIFY_FROM_TOTAL = 0;
    public static final int EXTRA_VERIFY_FROM_PERSONAL = 1;

    public static final int EXTRA_VERIFY_ALL = 0;
    public static final int EXTRA_VERIFY_NAME = 1;
    public static final int EXTRA_VERIFY_ALIAS = 2;
    public static final int EXTRA_VERIFY_LICENSE = 3;
    public static final int EXTRA_VERIFY_CODE = 4;
    public static final int EXTRA_VERIFY_ADDRESS = 5;
    public static final int EXTRA_VERIFY_LOCATION = 6;
    public static final int EXTRA_VERIFY_SHOP = 7;
    public static final int EXTRA_VERIFY_HAS = 8;
    public static final int EXTRA_VERIFY_NO = 9;

    public static final int REQUEST_IMAGE = 0;

    @Override
    public void setPresenter(VerifyCompanyContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_company);

        ButterKnife.bind(this);

        mPresenter = new VerifyCompanyPresentImpl(this, this);

        init();
    }

    @Override
    public void onBackPressed() {

        if (from == EXTRA_VERIFY_FROM_TOTAL) {

            mPresenter.goVerifyHome();

        } else {

            mPresenter.goVerifyPersonal(mVerifyPersonalAndCompany);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {

            String path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT).get(0);

            mPresenter.uploadImage(mPresenter.modifyImage(new File(path)));
        }
    }

    @Override
    public void init() {

        mTitle.setText(getString(R.string.verify_company));

        from = getIntent().getExtras().getInt(EXTRA_VERIFY_FROM);

        mVerifyPersonalAndCompany = GsonImpl.init().toObject(getIntent().getExtras().getString(VerifyActivity.EXTRA_VERIFY_VALUE), VerifyPersonalAndCompany.class);

        hasApplied = getIntent().getExtras().getBoolean(VerifyActivity.EXTRA_VERIFY_HAS_APPLIED);

        mConfirm.setVisibility(hasApplied ? View.INVISIBLE : View.VISIBLE);

        currentIndex = EXTRA_VERIFY_ALL;

        if(mVerifyPersonalAndCompany.getCompanyInfo().getLatitude() == 0 & mVerifyPersonalAndCompany.getCompanyInfo().getLongitude() == 0){

            mPresenter.doPositionService();

        }else{

            refreshData(mVerifyPersonalAndCompany.getCompanyInfo());
        }

        if (hasApplied) {

            mHasLicense.setEnabled(false);
            mNoLicense.setEnabled(false);
        }

        mHasLicense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                currentIndex = EXTRA_VERIFY_HAS;

                mVerifyPersonalAndCompany.getCompanyInfo().setHaveBusinessLicense(b);
                mPresenter.save(mVerifyPersonalAndCompany.getCompanyInfo());
            }
        });

        mNoLicense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                currentIndex = EXTRA_VERIFY_NO;

                mVerifyPersonalAndCompany.getCompanyInfo().setHaveBusinessLicense(!b);
                mPresenter.save(mVerifyPersonalAndCompany.getCompanyInfo());
            }
        });
    }

    @Override
    public void getImageId(String id) {

        mHasLicense.setChecked(mVerifyPersonalAndCompany.getCompanyInfo().isHaveBusinessLicense());
        mNoLicense.setChecked(!mVerifyPersonalAndCompany.getCompanyInfo().isHaveBusinessLicense());

        switch (currentIndex) {

            default:
                break;

            case EXTRA_VERIFY_LICENSE: {

                mVerifyPersonalAndCompany.getCompanyInfo().setBusinessLicenseId(id);
            }
            break;

            case EXTRA_VERIFY_SHOP: {

                mVerifyPersonalAndCompany.getCompanyInfo().setCompanyStoreId(id);
            }
            break;
        }

        mPresenter.save(mVerifyPersonalAndCompany.getCompanyInfo());
    }

    @Override
    public void refreshData(VerifyCompanyInfo verifyCompanyInfo) {

        hideAllDialog();

        mName.setText(verifyCompanyInfo.getCompanyName());
        mAlias.setText(verifyCompanyInfo.getCompanyShortName());

        if ((currentIndex == EXTRA_VERIFY_ALL | currentIndex == EXTRA_VERIFY_LICENSE) & !CommonUtil.isNullOrEmpty(verifyCompanyInfo.getBusinessLicenseId()))
            ImageUtil.load(this, mLicense, verifyCompanyInfo.getBusinessLicenseId(), "183", "136", R.drawable.verify_upload_default);

        mCode.setText(verifyCompanyInfo.getLicenseNum());

        String province = CommonUtil.isNullOrEmpty(verifyCompanyInfo.getProvince_Value()) ? "" : verifyCompanyInfo.getProvince_Value();
        String city = CommonUtil.isNullOrEmpty(verifyCompanyInfo.getCity_Value()) ? "" : verifyCompanyInfo.getCity_Value();
        String county = CommonUtil.isNullOrEmpty(verifyCompanyInfo.getCounty_Value()) ? "" : verifyCompanyInfo.getCounty_Value();
        mAddress.setText(province + city + county);
        mLocation.setText(verifyCompanyInfo.getAddress());

        if ((currentIndex == EXTRA_VERIFY_ALL | currentIndex == EXTRA_VERIFY_SHOP) & !CommonUtil.isNullOrEmpty(verifyCompanyInfo.getCompanyStoreId()))
            ImageUtil.load(this, mShop, verifyCompanyInfo.getCompanyStoreId(), "183", "136", R.drawable.verify_upload_default);

        mHasLicense.setChecked(mVerifyPersonalAndCompany.getCompanyInfo().isHaveBusinessLicense());
        mNoLicense.setChecked(!mVerifyPersonalAndCompany.getCompanyInfo().isHaveBusinessLicense());

        mLicenseWrap.setVisibility(mVerifyPersonalAndCompany.getCompanyInfo().isHaveBusinessLicense() ? View.VISIBLE : View.GONE);
        mLicenseDivider.setVisibility(mVerifyPersonalAndCompany.getCompanyInfo().isHaveBusinessLicense() ? View.VISIBLE : View.GONE);
        mCodeWrap.setVisibility(mVerifyPersonalAndCompany.getCompanyInfo().isHaveBusinessLicense() ? View.VISIBLE : View.GONE);
        mCodeDivider.setVisibility(mVerifyPersonalAndCompany.getCompanyInfo().isHaveBusinessLicense() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void hideAllDialog() {

        isShowDialog = false;
        mLocationDialog.setVisibility(View.GONE);
    }

    @Override
    public void updateVerifyCompanyInfo(VerifyCompanyInfo verifyCompanyInfo) {

        mVerifyPersonalAndCompany.setCompanyInfo(verifyCompanyInfo);

        mPresenter.save(mVerifyPersonalAndCompany.getCompanyInfo());
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
        mLocationList.setAdapter(new LocationSingleAdapter(this, this, mPresenter, type, mVerifyPersonalAndCompany.getCompanyInfo(), locationItemList));
    }

    @OnClick({
            R.id.back,
            R.id.upload_ok,
            R.id.upload_cancel,
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

                mPresenter.getLocationData(mStandard, mDataType, true, mVerifyPersonalAndCompany.getCompanyInfo());
            }
            break;

            case R.id.location_single_cancel: {

                hideAllDialog();
            }
            break;
        }
    }

    @OnClick({
            R.id.verify_company_name,
            R.id.verify_company_alias,
            R.id.verify_company_license_image,
            R.id.verify_company_code,
            R.id.verify_company_address,
            R.id.verify_company_location_detail,
            R.id.verify_company_location_marker,
            R.id.verify_company_shop,
            R.id.confirm
    })
    public void onViewClicked(View view) {

        if (hasApplied | isShowDialog) {

            return;
        }

        switch (view.getId()) {

            default:
                break;

            case R.id.verify_company_name: {

                currentIndex = EXTRA_VERIFY_NAME;

                mPresenter.goEditName(mVerifyPersonalAndCompany.getCompanyInfo());
            }
            break;

            case R.id.verify_company_alias: {

                currentIndex = EXTRA_VERIFY_ALIAS;

                mPresenter.goEditAlias(mVerifyPersonalAndCompany.getCompanyInfo());
            }
            break;

            case R.id.verify_company_license_image: {

                currentIndex = EXTRA_VERIFY_LICENSE;

                DialogUtil.INSTANCE.initVerifyDialog(this, 3, null, mPresenter);
            }
            break;

            case R.id.verify_company_code: {

                currentIndex = EXTRA_VERIFY_CODE;

                mPresenter.goEditCode(mVerifyPersonalAndCompany.getCompanyInfo());
            }
            break;

            case R.id.verify_company_address: {

                isShowDialog = true;

                currentIndex = EXTRA_VERIFY_ADDRESS;

                mPresenter.getLocationData(null, 1, false, mVerifyPersonalAndCompany.getCompanyInfo());
            }
            break;

            case R.id.verify_company_location_detail: {

                currentIndex = EXTRA_VERIFY_LOCATION;

                mPresenter.goEditLocation(mVerifyPersonalAndCompany.getCompanyInfo());
            }
            break;

            case R.id.verify_company_location_marker: {

                currentIndex = EXTRA_VERIFY_LOCATION;

                mPresenter.goMap(mVerifyPersonalAndCompany.getCompanyInfo().getLatitude(), mVerifyPersonalAndCompany.getCompanyInfo().getLongitude());
            }
            break;

            case R.id.verify_company_shop: {

                currentIndex = EXTRA_VERIFY_SHOP;

                DialogUtil.INSTANCE.initVerifyDialog(this, 4, null, mPresenter);
            }
            break;

            case R.id.confirm: {

                mPresenter.doApply(mVerifyPersonalAndCompany);
            }
            break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPositionEvent(PositionEvent positionEvent) {

        if(!positionEvent.isSuccess()){

            NoteUtil.showToast(this, getString(R.string.position_fail));
        }

        PositionItem positionItem = GsonImpl.init().toObject(PositionManager.getPositionDetail(), PositionItem.class);
        mVerifyPersonalAndCompany.getCompanyInfo().setLatitude(positionItem.getLatitude() == 0 ? 39.904030 : positionItem.getLatitude());
        mVerifyPersonalAndCompany.getCompanyInfo().setLongitude(positionItem.getLongitude() == 0 ? 116.407526 : positionItem.getLongitude());
        mVerifyPersonalAndCompany.getCompanyInfo().setAddress(positionItem.getLocation());

        refreshData(mVerifyPersonalAndCompany.getCompanyInfo());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMapEvent(MapEvent mapEvent) {

        mVerifyPersonalAndCompany.getCompanyInfo().setLatitude(mapEvent.getLatitude());
        mVerifyPersonalAndCompany.getCompanyInfo().setLongitude(mapEvent.getLongitude());
        mVerifyPersonalAndCompany.getCompanyInfo().setAddress(mapEvent.getLocation());

        mPresenter.save(mVerifyPersonalAndCompany.getCompanyInfo());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVerifyCompanyEvent(VerifyCompanyEvent verifyCompanyEvent) {

        VerifyCompanyInfo verifyCompanyInfo = verifyCompanyEvent.getVerifyCompanyInfo();

        mVerifyPersonalAndCompany.setCompanyInfo(verifyCompanyInfo);

        mPresenter.save(mVerifyPersonalAndCompany.getCompanyInfo());
    }
}
