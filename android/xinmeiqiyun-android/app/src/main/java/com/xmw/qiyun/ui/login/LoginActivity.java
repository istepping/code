package com.xmw.qiyun.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.event.LoginEvent;
import com.xmw.qiyun.data.model.net.user.CodeBody;
import com.xmw.qiyun.util.image.ImageUtil;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.dialog.DialogUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;
import com.xmw.qiyun.util.manage.SystemUtil;
import com.xmw.qiyun.view.CountDownTimerButton;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mac on 2017/7/27.
 */

@Route(RouterUtil.ROUTER_LOGIN)
public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.wrap)
    View mWrap;
    @BindView(R.id.login_phone_input)
    EditText mPhoneInput;
    @BindView(R.id.login_image_input)
    EditText mImageInput;
    @BindView(R.id.login_image)
    ImageView mImage;
    @BindView(R.id.login_code_input)
    EditText mCodeInput;
    @BindView(R.id.login_code_get)
    CountDownTimerButton mCodeGet;

    LoginContract.Presenter mPresenter;

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPresenter = new LoginPresentImpl(this, this);

        mPresenter.getImage(SystemUtil.getDeviceId());

        mWrap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                CommonUtil.hideKeyboard(mPhoneInput, LoginActivity.this);
                CommonUtil.hideKeyboard(mCodeInput, LoginActivity.this);

                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        mCodeGet.onDestroy();
    }

    @Override
    public void onBackPressed() {

        DialogUtil.INSTANCE.initLogoutDialog(this);
    }

    @Override
    public void countTime() {

        mCodeGet.startCountDownTimer(60000, 1000);
        mCodeGet.setStartCountDownText(getString(R.string.toast_code_get_again));
    }

    @Override
    public void loadImageCode(byte[] bytes) {

        mImageInput.setText("");
        ImageUtil.loadImageCode(this, mImage, bytes);
    }

    @OnClick({
            R.id.login_image,
            R.id.login_code_get,
            R.id.login_confirm,
            R.id.login_register
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.login_image: {

                mPresenter.getImage(SystemUtil.getDeviceId());
            }
            break;

            case R.id.login_code_get: {

                if (!CommonUtil.checkPhoneNumber(mPhoneInput.getText().toString())) {

                    NoteUtil.showToast(this, getString(R.string.toast_wrong_phone));
                    return;
                }

                if (CommonUtil.isNullOrEmpty(mImageInput.getText().toString())) {

                    NoteUtil.showToast(this, getString(R.string.toast_wrong_image));
                    return;
                }

                mPresenter.getCode(new CodeBody(mPhoneInput.getText().toString(), SystemUtil.getDeviceId(), mImageInput.getText().toString()));
            }
            break;

            case R.id.login_confirm: {

                mPresenter.doLogin(mPhoneInput.getText().toString(), mCodeInput.getText().toString());
            }
            break;

            case R.id.login_register: {

                mPresenter.goRegister();
            }
            break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent loginEvent) {
        finish();
    }
}
