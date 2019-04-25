package com.xmw.qiyun.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.manager.UserManager;
import com.xmw.qiyun.data.model.event.LogoutEvent;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongyuan on 2017/8/29.
 */

@Route(RouterUtil.ROUTER_SETTING)
public class SettingActivity extends BaseActivity implements SettingContract.View {

    @BindView(R.id.title)
    TextView mTitle;

    SettingContract.Presenter mPresenter;

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        mPresenter = new SettingPresentImpl(this);

        init();
    }

    @Override
    public void init() {

        mTitle.setText(getString(R.string.setting_title));
    }

    @OnClick({
            R.id.back,
            R.id.setting_cache,
            R.id.setting_about,
            R.id.setting_update,
            R.id.setting_logout
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.back:{

                onBackPressed();
            }
            break;

            case R.id.setting_cache:{

                Glide.get(this).clearMemory();

                NoteUtil.showToast(this, getString(R.string.setting_cache_clear));
            }
            break;

            case R.id.setting_about:{

                mPresenter.goAbout();
            }
            break;

            case R.id.setting_update:{

                mPresenter.goUpdate();
            }
            break;

            case R.id.setting_logout:{

                UserManager.logout();

                LogoutEvent logoutEvent = new LogoutEvent();

                logoutEvent.setHasLogout(true);

                EventBus.getDefault().post(logoutEvent);

                finish();
            }
            break;
        }
    }
}
