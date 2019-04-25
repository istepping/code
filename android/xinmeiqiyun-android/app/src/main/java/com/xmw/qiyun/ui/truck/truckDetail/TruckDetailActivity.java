package com.xmw.qiyun.ui.truck.truckDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.net.truck.TruckDetail;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.image.ImageUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongyuan on 2017/8/11.
 */

@Route(RouterUtil.ROUTER_TRUCK_DETAIL)
public class TruckDetailActivity extends BaseActivity implements TruckDetailContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.truck_detail_avatar)
    ImageView mAvatar;
    @BindView(R.id.truck_detail_icon)
    ImageView mIcon;
    @BindView(R.id.truck_detail_name)
    TextView mName;
    @BindView(R.id.truck_detail_num)
    TextView mNum;
    @BindView(R.id.truck_detail_phone)
    TextView mPhone;
    @BindView(R.id.truck_detail_property)
    TextView mProperty;
    @BindView(R.id.truck_detail_city)
    TextView mCity;
    @BindView(R.id.truck_detail_time)
    TextView mTime;

    TruckDetailContract.Presenter mPresenter;

    public static final String EXTRA_TRUCK_DETAIL = "EXTRA_TRUCK_DETAIL";

    @Override
    public void setPresenter(TruckDetailContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_detail);

        ButterKnife.bind(this);

        mPresenter = new TruckDetailPresentImpl(this, this);

        init();
    }

    @Override
    public void init() {

        mTitle.setText(getString(R.string.truck_detail_title));

        mPresenter.getData(getIntent().getExtras().getString(EXTRA_TRUCK_DETAIL));
    }

    @Override
    public void refreshData(TruckDetail truckDetail) {

        ImageUtil.loadAvatar(this, mAvatar, truckDetail.getHeadPortraitId(), R.drawable.default_avatar);

        switch (truckDetail.getStatus()){

            default:
                break;

            case 1:{

                mIcon.setImageResource(R.drawable.verify_type_1);
            }
            break;

            case 2:{

                mIcon.setImageResource(R.drawable.verify_type_2);
            }
            break;
        }

        mName.setText(truckDetail.getName());
        mNum.setText(CommonUtil.isNullOrEmpty(truckDetail.getVehicleNum()) ? getString(R.string.truck_detail_no_num) : truckDetail.getVehicleNum());
        mPhone.setText(truckDetail.getMobile());
        mProperty.setText(CommonUtil.isNullOrEmpty(truckDetail.getVehicleProperty()) ? getString(R.string.truck_detail_no_property) : truckDetail.getVehicleProperty());
        mCity.setText(CommonUtil.isNullOrEmpty(truckDetail.getOftenCityName()) ? getString(R.string.truck_detail_no_city) : truckDetail.getOftenCityName());
        mTime.setText(truckDetail.getRegisterTime());
    }

    @OnClick({
            R.id.back
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.back: {

                onBackPressed();
            }
            break;
        }
    }
}
