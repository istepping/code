package com.xmw.qiyun.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.event.LogoutEvent;
import com.xmw.qiyun.data.model.net.other.VersionBean;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.ui.cargo.CargoFragment;
import com.xmw.qiyun.ui.me.MeFragment;
import com.xmw.qiyun.ui.price.PriceFragment;
import com.xmw.qiyun.ui.publish.PublishFragment;
import com.xmw.qiyun.ui.truck.TruckFragment;
import com.xmw.qiyun.util.dialog.DialogUtil;
import com.xmw.qiyun.util.manage.RouterUtil;
import com.xmw.qiyun.util.manage.SystemUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mac on 2017/7/27.
 */

@Route(RouterUtil.ROUTER_HOME)
public class HomeActivity extends BaseActivity implements HomeContract.View {

    @BindView(R.id.tab_price)
    RadioButton mPrice;
    @BindView(R.id.tab_truck)
    RadioButton mTruck;
    @BindView(R.id.tab_publish)
    RadioButton mPublish;
    @BindView(R.id.tab_cargo)
    RadioButton mCargo;
    @BindView(R.id.tab_me)
    RadioButton mMe;

    @BindView(R.id.update_dialog)
    View mUpdateDialog;
    @BindView(R.id.update_version)
    TextView mUpdateVersion;
    @BindView(R.id.update_description)
    TextView mUpdateDescription;

    private String mVersionPath;

    List<Fragment> fragmentList = new ArrayList<>();

    HomeContract.Presenter mPresenter;

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        mPresenter = new HomePresentImpl(this, this);

        init();

        API.getService().getProvince().subscribe(new MySubscriber<ProvinceBean>() {
            @Override
            public void onNext(ProvinceBean provinceBean) {
                //provinceBean.getData();
                Log.d("return data:",provinceBean.getData()+"");
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (BackHandlerHelper.handleBackPress(this)) {

            DialogUtil.INSTANCE.initLogoutDialog(this);
        }
    }

    @Override
    public void init() {

        //将按钮与指定页面绑定
        mPrice.setTag(new PriceFragment());
        mTruck.setTag(new TruckFragment());
        mPublish.setTag(new PublishFragment());
        mCargo.setTag(new CargoFragment());
        mMe.setTag(new MeFragment());

        //设定初始页面
        mPublish.performClick();

        //校验版本
        mPresenter.checkUpdate();
    }

    @Override
    public void translateVersion(VersionBean versionBean) {

        mVersionPath = versionBean.getData().getDownloadPath();

        mUpdateVersion.setText(versionBean.getData().getVersonNum());
        mUpdateDescription.setText(versionBean.getData().getVersonDescription());

        if (versionBean.getData().getVersonNum().compareTo(SystemUtil.getVersionName(this)) > 0) {

            mUpdateDialog.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({
            R.id.tab_price,
            R.id.tab_truck,
            R.id.tab_publish,
            R.id.tab_cargo,
            R.id.tab_me,
            R.id.tab_plus
    })
    public void onViewClicked(View view) {

        if (view.getId() == R.id.tab_plus) {

            mPresenter.switchFragments((Fragment) mPublish.getTag(), fragmentList);

        } else {

            mPresenter.switchFragments((Fragment) view.getTag(), fragmentList);
        }
    }

    @OnClick({
            R.id.update_cancel,
            R.id.update_ok
    })
    public void onDialogClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.update_cancel: {

                mUpdateDialog.setVisibility(View.GONE);
            }
            break;

            case R.id.update_ok: {

                mUpdateDialog.setVisibility(View.GONE);

                Uri uri = Uri.parse(mVersionPath);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
            break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutEvent(LogoutEvent logoutEvent) {

        if (logoutEvent.isHasLogout()) {

            RouterUtil.go(RouterUtil.ROUTER_LOGIN, this);

            finish();
        }
    }
}
