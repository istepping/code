package com.xmw.qiyun.ui.verify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.event.MeEvent;
import com.xmw.qiyun.data.model.event.RefreshEvent;
import com.xmw.qiyun.data.model.event.VerifyRefreshEvent;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalAndCompany;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalInfo;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongyuan on 2017/8/29.
 */

@Route(RouterUtil.ROUTER_VERIFY)
public class VerifyActivity extends BaseActivity implements VerifyContract.View {

    @BindView(R.id.verify_progress)
    TextView mProgress;
    @BindView(R.id.personal_progress)
    TextView mPersonalProgress;
    @BindView(R.id.company_progress)
    TextView mCompanyProgress;
    @BindView(R.id.verify_apply)
    TextView mConfirm;

    VerifyContract.Presenter mPresenter;

    private boolean hasApplied = false;

    private VerifyPersonalAndCompany mVerifyPersonalAndCompany;
    private VerifyPersonalInfo mVerifyPersonalInfo;

    public static final String EXTRA_VERIFY_VALUE = "EXTRA_VERIFY_VALUE";
    public static final String EXTRA_VERIFY_HAS_APPLIED = "EXTRA_VERIFY_HAS_APPLIED";

    @Override
    public void setPresenter(VerifyContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        ButterKnife.bind(this);

        mPresenter = new VerifyPresentImpl(this, this);

        init();
    }

    @Override
    public void onBackPressed(){

        EventBus.getDefault().post(new MeEvent());

        finish();
    }

    @Override
    public void init() {

        mPresenter.getVerifyInfo();
    }

    @Override
    public void getData(VerifyPersonalAndCompany verifyPersonalAndCompany) {

        mVerifyPersonalAndCompany = verifyPersonalAndCompany;
        mVerifyPersonalInfo = verifyPersonalAndCompany.getPersonalInfo();

        int type = verifyPersonalAndCompany.getAuthenticationType();

        if(type == 0 | type == 4){

            mConfirm.setVisibility(View.VISIBLE);

        }else {

            mConfirm.setVisibility(View.INVISIBLE);

            hasApplied = true;
        }

        updateData(mVerifyPersonalAndCompany);
    }

    @Override
    public void updateData(VerifyPersonalAndCompany verifyPersonalAndCompany) {

        StringBuilder driverSb = new StringBuilder(getString(R.string.verify_complete_title));
        StringBuilder vehicleSb = new StringBuilder(getString(R.string.verify_complete_title));

        mProgress.setText(verifyPersonalAndCompany.getTotalStatistics().substring(0, verifyPersonalAndCompany.getTotalStatistics().length() - 1));
        mPersonalProgress.setText(driverSb.append(verifyPersonalAndCompany.getPersonalInfo().getPersonalInfoStatisticsValue()));
        mCompanyProgress.setText(vehicleSb.append(verifyPersonalAndCompany.getCompanyInfo().getCompanyInfoStatisticsValue()));
    }

    @OnClick({
            R.id.back,
            R.id.verify_apply,
            R.id.personal_wrap,
            R.id.company_wrap
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.back:{

                onBackPressed();
            }
            break;

            case R.id.verify_apply:{

                mPresenter.doApply(mVerifyPersonalAndCompany);
            }
            break;

            case R.id.personal_wrap:{

                mPresenter.goVerifyPersonal(mVerifyPersonalAndCompany, hasApplied);
            }
            break;

            case R.id.company_wrap:{

                if (mVerifyPersonalAndCompany == null) {

                    NoteUtil.showToast(this, getString(R.string.toast_verify_fail));

                    return;
                }

                if(mVerifyPersonalInfo.getPersonalInfoStatisticsValue().length() == 4) {

                    mPresenter.goVerifyCompany(mVerifyPersonalAndCompany, hasApplied);

                }else{

                    NoteUtil.showToast(this, getString(R.string.verify_apply_driver_error));
                }
            }
            break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVerifyDriverAndVehicleEvent(VerifyRefreshEvent verifyRefreshEvent) {

        init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent refreshEvent) {

        init();
    }
}