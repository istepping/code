package com.xmw.qiyun.ui.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.net.user.CodeBody;
import com.xmw.qiyun.util.image.ImageUtil;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;
import com.xmw.qiyun.util.ShowTextUtil;
import com.xmw.qiyun.util.manage.SystemUtil;
import com.xmw.qiyun.view.CountDownTimerButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mac on 2017/7/27.
 */

@Route(RouterUtil.ROUTER_REGISTER)
public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    @BindView(R.id.wrap)
    View mWrap;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.register_phone_input)
    EditText mPhoneInput;
    @BindView(R.id.register_image_input)
    EditText mImageInput;
    @BindView(R.id.register_image)
    ImageView mImage;
    @BindView(R.id.register_code_input)
    EditText mCodeInput;
    @BindView(R.id.register_code_get)
    CountDownTimerButton mCodeGet;
    @BindView(R.id.showText)
    TextView mShowText;

    RegisterContract.Presenter mPresenter;

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        mPresenter = new RegisterPresentImpl(this, this);

        mPresenter.getImage(SystemUtil.getDeviceId());

        mTitle.setText(getString(R.string.register_title));

        String string = getString(R.string.agreement_total);

        SpannableStringBuilder spannable = new SpannableStringBuilder(string);
        spannable.setSpan(new ShowTextUtil(this, ShowTextUtil.EXTRA_SERVICE), 15, 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ShowTextUtil(this, ShowTextUtil.EXTRA_SECRET), 28, string.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mShowText.setMovementMethod(LinkMovementMethod.getInstance());
        mShowText.setText(spannable);

        mWrap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                CommonUtil.hideKeyboard(mPhoneInput, RegisterActivity.this);
                CommonUtil.hideKeyboard(mCodeInput, RegisterActivity.this);

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
            R.id.back,
            R.id.register_image,
            R.id.register_code_get,
            R.id.register_confirm
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.back: {

                onBackPressed();
            }
            break;

            case R.id.register_image: {

                mPresenter.getImage(SystemUtil.getDeviceId());
            }
            break;

            case R.id.register_code_get: {

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

            case R.id.register_confirm: {

                mPresenter.doRegister(mPhoneInput.getText().toString(), mCodeInput.getText().toString());
            }
            break;
        }
    }
}
