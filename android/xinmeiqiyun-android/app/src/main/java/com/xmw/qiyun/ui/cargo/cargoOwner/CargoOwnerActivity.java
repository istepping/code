package com.xmw.qiyun.ui.cargo.cargoOwner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.net.cargo.CargoOwner;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerBean;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.image.ImageUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongyuan on 2017/8/29.
 */

@Route(RouterUtil.ROUTER_CARGO_OWNER)
public class CargoOwnerActivity extends BaseActivity implements CargoOwnerContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.cargo_master_avatar)
    ImageView mAvatar;
    @BindView(R.id.cargo_master_icon)
    ImageView mIcon;
    @BindView(R.id.cargo_master_name)
    TextView mName;
    @BindView(R.id.cargo_master_time)
    TextView mTime;
    @BindView(R.id.cargo_master_number)
    TextView mNumber;
    @BindView(R.id.cargo_master_phone)
    TextView mPhone;
    @BindView(R.id.cargo_master_company)
    TextView mCompany;
    @BindView(R.id.cargo_master_position)
    TextView mPosition;

    CargoOwnerContract.Presenter mPresenter;

    private CargoOwner mCargoOwner;

    public static final String EXTRA_CARGO_OWNER = "EXTRA_CARGO_OWNER";

    @Override
    public void setPresenter(CargoOwnerContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_owner);

        ButterKnife.bind(this);

        mPresenter = new CargoOwnerPresentImpl(this, this);

        init();
    }

    @Override
    public void init() {

        mTitle.setText(getString(R.string.cargo_master_title));

        mPresenter.getData(getIntent().getExtras().getString(EXTRA_CARGO_OWNER));
    }

    @Override
    public void refreshData(CargoOwnerBean cargoOwnerBean) {

        mCargoOwner = cargoOwnerBean.getData();

        ImageUtil.loadAvatar(this, mAvatar, mCargoOwner.getHeadPortraitId(), R.drawable.default_avatar);

        switch (mCargoOwner.getStatus()){

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

            case 4:{

                mIcon.setImageResource(R.drawable.verify_type_4);
            }
            break;
        }

        mName.setText(mCargoOwner.getName());
        mTime.setText(mCargoOwner.getRegisterTime());
        mNumber.setText(mCargoOwner.getSourceCount());
        mPhone.setText(mCargoOwner.getMobile());
        mCompany.setText(CommonUtil.isNullOrEmpty(mCargoOwner.getCompanyName()) ? getString(R.string.cargo_master_no_company) : mCargoOwner.getCompanyName());
        mPosition.setText(CommonUtil.isNullOrEmpty(mCargoOwner.getAddress()) ? getString(R.string.cargo_master_no_position) : mCargoOwner.getAddress());
    }

    @OnClick({
            R.id.back,
            R.id.title_button
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.back: {

                onBackPressed();
            }
            break;

            case R.id.title_button:{

                mPresenter.goMap(mCargoOwner);
            }
            break;
        }
    }
}
