package com.xmw.qiyun.ui.setting.aboutAndUpdate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.net.other.VersionBean;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;
import com.xmw.qiyun.util.manage.SystemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongyuan on 2017/8/29.
 */

@Route(RouterUtil.ROUTER_ABOUT_AND_UPDATE)
public class AboutAndUpdateActivity extends BaseActivity implements AboutAndUpdateContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.version)
    TextView mVersion;
    @BindView(R.id.about_wrap)
    View mAboutWrap;
    @BindView(R.id.update_wrap)
    View mUpdateWrap;
    @BindView(R.id.update_dialog)
    View mUpdateDialog;
    @BindView(R.id.update_version)
    TextView mUpdateVersion;
    @BindView(R.id.update_description)
    TextView mUpdateDescription;

    AboutAndUpdateContract.Presenter mPresenter;

    private String mVersionPath;

    public static final String EXTRA_ABOUT_UPDATE_TYPE = "EXTRA_ABOUT_UPDATE_TYPE";

    public static final int EXTRA_ABOUT = 0;
    public static final int EXTRA_UPDATE = 1;

    @Override
    public void setPresenter(AboutAndUpdateContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_update);

        ButterKnife.bind(this);

        mPresenter = new AboutAndUpdatePresentImpl(this, this);

        init();
    }

    @Override
    public void init() {

        int type = getIntent().getExtras().getInt(EXTRA_ABOUT_UPDATE_TYPE);

        mTitle.setText(type == EXTRA_ABOUT ? getString(R.string.setting_about) : getString(R.string.setting_update));
        mAboutWrap.setVisibility((type == EXTRA_ABOUT ? View.VISIBLE : View.GONE));
        mUpdateWrap.setVisibility((type == EXTRA_ABOUT ? View.GONE : View.VISIBLE));

        mVersion.setText(SystemUtil.getVersionName(this));
    }

    @Override
    public void translateVersion(VersionBean versionBean) {

        mVersionPath = versionBean.getData().getDownloadPath();

        mUpdateVersion.setText(versionBean.getData().getVersonNum());
        mUpdateDescription.setText(versionBean.getData().getVersonDescription());

        if (versionBean.getData().getVersonNum().compareTo(SystemUtil.getVersionName(this)) > 0) {

            mUpdateDialog.setVisibility(View.VISIBLE);

        } else {

            NoteUtil.showToast(this, getString(R.string.setting_update_last));
        }
    }

    @OnClick({
            R.id.back,
            R.id.about_phone,
            R.id.about_text,
            R.id.update_check,
            R.id.update_cancel,
            R.id.update_ok
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.back: {

                onBackPressed();
            }
            break;

            case R.id.about_phone: {

                mPresenter.doCall();
            }
            break;

            case R.id.about_text: {

                mPresenter.goShowText();
            }
            break;

            case R.id.update_check: {

                mPresenter.doUpdateCheck();
            }
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
        }
    }
}
