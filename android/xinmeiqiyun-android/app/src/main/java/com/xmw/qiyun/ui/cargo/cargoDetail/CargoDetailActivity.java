package com.xmw.qiyun.ui.cargo.cargoDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.net.cargo.CargoDetail;
import com.xmw.qiyun.ui.adapter.common.CommonAdapter;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.image.ImageUtil;
import com.xmw.qiyun.util.manage.RouterUtil;
import com.xmw.qiyun.view.HorizontalListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongyuan on 2017/8/11.
 */

@Route(RouterUtil.ROUTER_CARGO_DETAIL)
public class CargoDetailActivity extends BaseActivity implements CargoDetailContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.cargo_detail_time)
    TextView mTime;
    @BindView(R.id.cargo_detail_location_start)
    TextView mStart;
    @BindView(R.id.cargo_detail_location_start_detail)
    TextView mStartDetail;
    @BindView(R.id.cargo_detail_location_end)
    TextView mEnd;
    @BindView(R.id.cargo_detail_location_end_detail)
    TextView mEndDetail;
    @BindView(R.id.cargo_detail_category)
    HorizontalListView mCategory;
    @BindView(R.id.cargo_detail_map)
    TextView mMap;
    @BindView(R.id.cargo_detail_mark)
    TextView mMark;
    @BindView(R.id.cargo_detail_region)
    TextView mRegion;
    @BindView(R.id.cargo_detail_avatar)
    ImageView mAvatar;
    @BindView(R.id.cargo_detail_name)
    TextView mName;
    @BindView(R.id.cargo_detail_verify)
    ImageView mVerify;
    @BindView(R.id.cargo_detail_record)
    TextView mRecord;

    CargoDetailContract.Presenter mPresenter;

    private CargoDetail mCargoDetail = new CargoDetail();

    public static final String EXTRA_CARGO_DETAIL = "EXTRA_CARGO_DETAIL";

    @Override
    public void setPresenter(CargoDetailContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_detail);

        ButterKnife.bind(this);

        mPresenter = new CargoDetailPresentImpl(this, this);

        init();
    }

    @Override
    public void init() {

        mTitle.setText(getString(R.string.cargo_detail_title));

        mPresenter.getData(getIntent().getExtras().getString(EXTRA_CARGO_DETAIL));
    }

    @Override
    public void refreshData(CargoDetail cargoDetail) {

        mCargoDetail = cargoDetail;

        mTime.setText(cargoDetail.getReleasedTimeName());

        mStart.setText(cargoDetail.getLoadProvince_Value()
                + cargoDetail.getLoadCity_Value()
                + (CommonUtil.isNullOrEmpty(cargoDetail.getLoadCounty_Value()) ? "" : cargoDetail.getLoadCounty_Value()));

        if (CommonUtil.isNullOrEmpty(cargoDetail.getLoadCounty_Value())) {

            int length = cargoDetail.getLoadProvince_Value().length() + cargoDetail.getLoadCity_Value().length();

            mStartDetail.setVisibility(cargoDetail.getLoadPlace().length() == length ? View.GONE : View.VISIBLE);
            mStartDetail.setText(cargoDetail.getLoadPlace().substring(length));

        } else {

            int length = cargoDetail.getLoadCity_Value().length() + cargoDetail.getLoadCounty_Value().length();

            mStartDetail.setVisibility(cargoDetail.getLoadPlace().length() == length ? View.GONE : View.VISIBLE);
            mStartDetail.setText(cargoDetail.getLoadPlace().substring(length));
        }

        mEnd.setText(cargoDetail.getUnloadProvince_Value()
                + cargoDetail.getUnloadCity_Value()
                + (CommonUtil.isNullOrEmpty(cargoDetail.getUnloadCounty_Value()) ? "" : cargoDetail.getUnloadCounty_Value()));

        if (CommonUtil.isNullOrEmpty(cargoDetail.getUnloadCounty_Value())) {

            int length = cargoDetail.getUnloadProvince_Value().length() + cargoDetail.getUnloadCity_Value().length();

            mEndDetail.setVisibility(cargoDetail.getUnloadPlace().length() == length ? View.GONE : View.VISIBLE);
            mEndDetail.setText(cargoDetail.getUnloadPlace().substring(length));

        } else {

            int length = cargoDetail.getUnloadCity_Value().length() + cargoDetail.getUnloadCounty_Value().length();

            mEndDetail.setVisibility(cargoDetail.getUnloadPlace().length() == length ? View.GONE : View.VISIBLE);
            mEndDetail.setText(cargoDetail.getUnloadPlace().substring(length));
        }

        mCategory.setVisibility(CommonUtil.isNullOrEmpty(cargoDetail.getGoodsShortInfo()) ? View.GONE : View.VISIBLE);
        mCategory.setAdapter(new CommonAdapter(this, CommonUtil.getStringListMulti(cargoDetail.getGoodsShortInfo())));

        /*mMap.setVisibility(cargoDetail.getMileage() == 0 ? View.GONE : View.VISIBLE);
        mMap.setText(new StringBuilder().append(getString(R.string.publish_detail_map)).append(String.valueOf(cargoDetail.getMileage())).append(getString(R.string.publish_detail_km)));*/

        mMark.setText(CommonUtil.isNullOrEmpty(cargoDetail.getRemarkValue()) ? getString(R.string.cargo_detail_no_remark) : cargoDetail.getRemarkValue());

        mRegion.setText(new StringBuilder().append(getString(R.string.publish_detail_from)).append("  ").append(cargoDetail.getGoodsOwnerInfo_Region()));

        ImageUtil.loadAvatar(this, mAvatar, cargoDetail.getGoodsOwnerInfo_HeadPortraitId(), R.drawable.default_avatar);

        mName.setText(cargoDetail.getGoodsOwnerInfo_Name());

        switch (cargoDetail.getGoodsOwnerInfo_Status()) {

            default:
                break;

            case 1: {

                mVerify.setImageResource(R.drawable.verify_type_1);
            }
            break;

            case 2: {

                mVerify.setImageResource(R.drawable.verify_type_2);
            }
            break;

            case 4: {

                mVerify.setImageResource(R.drawable.verify_type_4);
            }
            break;
        }

        mRecord.setText(cargoDetail.getRegisterTime() + " / " + cargoDetail.getSourceCount());
    }

    @OnClick({
            R.id.back,
            R.id.cargo_detail_page,
            R.id.cargo_detail_contact
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.back: {

                onBackPressed();
            }
            break;

            case R.id.cargo_detail_page: {

                mPresenter.doGoClickCargoMaster(mCargoDetail.getGoodsOwnerInfoId());
            }
            break;

            case R.id.cargo_detail_contact: {

                mPresenter.doCallItem(mCargoDetail.getGoodsOwnerInfo_Mobile());
            }
            break;
        }
    }
}
