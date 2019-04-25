package com.xmw.qiyun.ui.verify.verifyPersonal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.event.VerifyApplyEvent;
import com.xmw.qiyun.data.model.event.VerifyBackEvent;
import com.xmw.qiyun.data.model.event.VerifyPersonalEvent;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalAndCompany;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalInfo;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.verify.VerifyActivity;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.dialog.DialogUtil;
import com.xmw.qiyun.util.image.ImageUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by hongyuan on 2017/8/29.
 */

@Route(RouterUtil.ROUTER_VERIFY_PERSONAL)
public class VerifyPersonalActivity extends BaseActivity implements VerifyPersonalContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.verify_personal_name_detail)
    TextView mName;
    @BindView(R.id.verify_personal_code_detail)
    TextView mCode;
    @BindView(R.id.verify_personal_auth_detail)
    ImageView mAuth;
    @BindView(R.id.verify_personal_image_detail)
    ImageView mImage;
    @BindView(R.id.verify_personal_card_detail)
    ImageView mCard;
    @BindView(R.id.verify_personal_card_true)
    CheckBox mHasCard;
    @BindView(R.id.verify_personal_card_false)
    CheckBox mNoCard;
    @BindView(R.id.verify_personal_card)
    View mCardWrap;
    @BindView(R.id.verify_personal_card_divider)
    View mCardDivider;
    @BindView(R.id.confirm)
    TextView mConfirm;

    VerifyPersonalContract.Presenter mPresenter;

    private boolean hasApplied = false;
    private int currentIndex;

    private VerifyPersonalAndCompany mVerifyPersonalAndCompany;

    public static final int EXTRA_VERIFY_ALL = 0;
    public static final int EXTRA_VERIFY_NAME = 1;
    public static final int EXTRA_VERIFY_CODE = 2;
    public static final int EXTRA_VERIFY_AUTH = 3;
    public static final int EXTRA_VERIFY_IMAGE= 4;
    public static final int EXTRA_VERIFY_CARD = 5;
    public static final int EXTRA_VERIFY_HAS = 6;
    public static final int EXTRA_VERIFY_NO = 7;

    public static final int REQUEST_IMAGE = 0;

    @Override
    public void setPresenter(VerifyPersonalContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_personal);

        ButterKnife.bind(this);

        mPresenter = new VerifyPersonalPresentImpl(this, this);

        init();
    }

    @Override
    public void onBackPressed(){

        mPresenter.goVerifyHome();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE && resultCode == RESULT_OK){

            String path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT).get(0);

            mPresenter.uploadImage(mPresenter.modifyImage(new File(path)));
        }
    }

    @Override
    public void init() {

        mTitle.setText(getString(R.string.verify_personal));

        mVerifyPersonalAndCompany = GsonImpl.init().toObject(getIntent().getExtras().getString(VerifyActivity.EXTRA_VERIFY_VALUE), VerifyPersonalAndCompany.class);

        hasApplied = getIntent().getExtras().getBoolean(VerifyActivity.EXTRA_VERIFY_HAS_APPLIED);

        mConfirm.setVisibility(hasApplied ? View.INVISIBLE : View.VISIBLE);

        currentIndex = EXTRA_VERIFY_ALL;

        refreshData(mVerifyPersonalAndCompany.getPersonalInfo());

        if(hasApplied){

            mHasCard.setEnabled(false);
            mNoCard.setEnabled(false);
        }

        mHasCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                currentIndex = EXTRA_VERIFY_HAS;

                mVerifyPersonalAndCompany.getPersonalInfo().setHaveBusinessCard(b);
                mPresenter.save(mVerifyPersonalAndCompany.getPersonalInfo());
            }
        });

        mNoCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                currentIndex = EXTRA_VERIFY_NO;

                mVerifyPersonalAndCompany.getPersonalInfo().setHaveBusinessCard(!b);
                mPresenter.save(mVerifyPersonalAndCompany.getPersonalInfo());
            }
        });
    }

    @Override
    public void refreshData(VerifyPersonalInfo verifyPersonalInfo) {

        if(currentIndex == EXTRA_VERIFY_ALL) {

            mName.setText(verifyPersonalInfo.getName());
            mCode.setText(verifyPersonalInfo.getIDCardNum());
            if (!CommonUtil.isNullOrEmpty(verifyPersonalInfo.getIdentityVerificationId()))
                ImageUtil.load(this, mAuth, verifyPersonalInfo.getIdentityVerificationId(), "183", "136", R.drawable.verify_upload_default);
            if (!CommonUtil.isNullOrEmpty(verifyPersonalInfo.getIDCardId()))
                ImageUtil.load(this, mImage, verifyPersonalInfo.getIDCardId(), "183", "136", R.drawable.verify_upload_default);
            if (!CommonUtil.isNullOrEmpty(verifyPersonalInfo.getBusinessCardId()))
                ImageUtil.load(this, mCard, verifyPersonalInfo.getBusinessCardId(), "183", "136", R.drawable.verify_upload_default);

            mHasCard.setChecked(mVerifyPersonalAndCompany.getPersonalInfo().isHaveBusinessCard());
            mNoCard.setChecked(!mVerifyPersonalAndCompany.getPersonalInfo().isHaveBusinessCard());

            mCardWrap.setVisibility(mVerifyPersonalAndCompany.getPersonalInfo().isHaveBusinessCard() ? View.VISIBLE : View.GONE);
            mCardDivider.setVisibility(mVerifyPersonalAndCompany.getPersonalInfo().isHaveBusinessCard() ? View.VISIBLE : View.GONE);

        }else{

            switch (currentIndex){

                default:
                    break;

                case EXTRA_VERIFY_NAME:{

                    mName.setText(verifyPersonalInfo.getName());
                }
                break;

                case EXTRA_VERIFY_CODE:{

                    mCode.setText(verifyPersonalInfo.getIDCardNum());
                }
                break;

                case EXTRA_VERIFY_AUTH:{

                    if (!CommonUtil.isNullOrEmpty(verifyPersonalInfo.getIdentityVerificationId()))
                        ImageUtil.load(this, mAuth, verifyPersonalInfo.getIdentityVerificationId(), "183", "136", R.drawable.verify_upload_default);
                }
                break;

                case EXTRA_VERIFY_IMAGE:{

                    if (!CommonUtil.isNullOrEmpty(verifyPersonalInfo.getIDCardId()))
                        ImageUtil.load(this, mImage, verifyPersonalInfo.getIDCardId(), "183", "136", R.drawable.verify_upload_default);
                }
                break;

                case EXTRA_VERIFY_CARD:{

                    if (!CommonUtil.isNullOrEmpty(verifyPersonalInfo.getBusinessCardId()))
                        ImageUtil.load(this, mCard, verifyPersonalInfo.getBusinessCardId(), "183", "136", R.drawable.verify_upload_default);
                }
                break;

                case EXTRA_VERIFY_HAS:{

                    mNoCard.setChecked(!mVerifyPersonalAndCompany.getPersonalInfo().isHaveBusinessCard());

                    mCardWrap.setVisibility(mVerifyPersonalAndCompany.getPersonalInfo().isHaveBusinessCard() ? View.VISIBLE : View.GONE);
                    mCardDivider.setVisibility(mVerifyPersonalAndCompany.getPersonalInfo().isHaveBusinessCard() ? View.VISIBLE : View.GONE);
                }
                break;

                case EXTRA_VERIFY_NO:{

                    mHasCard.setChecked(mVerifyPersonalAndCompany.getPersonalInfo().isHaveBusinessCard());

                    mCardWrap.setVisibility(mVerifyPersonalAndCompany.getPersonalInfo().isHaveBusinessCard() ? View.VISIBLE : View.GONE);
                    mCardDivider.setVisibility(mVerifyPersonalAndCompany.getPersonalInfo().isHaveBusinessCard() ? View.VISIBLE : View.GONE);
                }
                break;
            }
        }
    }

    @Override
    public void getImageId(String id) {

        switch (currentIndex){

            default:
                break;

            case EXTRA_VERIFY_AUTH:{

                mVerifyPersonalAndCompany.getPersonalInfo().setIdentityVerificationId(id);
            }
            break;

            case EXTRA_VERIFY_IMAGE:{

                mVerifyPersonalAndCompany.getPersonalInfo().setIDCardId(id);
            }
            break;

            case EXTRA_VERIFY_CARD:{

                mVerifyPersonalAndCompany.getPersonalInfo().setBusinessCardId(id);
            }
            break;
        }

        mPresenter.save(mVerifyPersonalAndCompany.getPersonalInfo());
    }

    @OnClick({
            R.id.back
    })
    public void doDialogClicked(View view){

        switch (view.getId()){

            default:
                break;

            case R.id.back:{

                onBackPressed();
            }
            break;
        }
    }

    @OnClick({
            R.id.verify_personal_name,
            R.id.verify_personal_code,
            R.id.verify_personal_auth,
            R.id.verify_personal_image,
            R.id.verify_personal_card,
            R.id.confirm
    })
    public void onViewClicked(View view) {

        if(hasApplied){

            return;
        }

        switch (view.getId()) {

            default:
                break;

            case R.id.verify_personal_name:{

                currentIndex = EXTRA_VERIFY_NAME;

                mPresenter.goEditName(mVerifyPersonalAndCompany.getPersonalInfo());
            }
            break;

            case R.id.verify_personal_code:{

                currentIndex = EXTRA_VERIFY_CODE;

                mPresenter.goEditCode(mVerifyPersonalAndCompany.getPersonalInfo());
            }
            break;

            case R.id.verify_personal_auth:{

                currentIndex = EXTRA_VERIFY_AUTH;

                DialogUtil.INSTANCE.initVerifyDialog(this, 0, mPresenter, null);
            }
            break;

            case R.id.verify_personal_image:{

                currentIndex = EXTRA_VERIFY_IMAGE;

                DialogUtil.INSTANCE.initVerifyDialog(this, 1, mPresenter, null);
            }
            break;

            case R.id.verify_personal_card:{

                currentIndex = EXTRA_VERIFY_CARD;

                DialogUtil.INSTANCE.initVerifyDialog(this, 2, mPresenter, null);
            }
            break;

            case R.id.confirm:{

                if(CommonUtil.isNullOrEmpty(mVerifyPersonalAndCompany.getPersonalInfo().getName())
                        | CommonUtil.isNullOrEmpty(mVerifyPersonalAndCompany.getPersonalInfo().getIDCardNum())
                        | CommonUtil.isNullOrEmpty(mVerifyPersonalAndCompany.getPersonalInfo().getIdentityVerificationId())
                        | CommonUtil.isNullOrEmpty(mVerifyPersonalAndCompany.getPersonalInfo().getIDCardId())){

                    NoteUtil.showToast(this, getString(R.string.verify_apply_driver_error));

                    return;
                }

                mPresenter.goNext(GsonImpl.init().toJson(mVerifyPersonalAndCompany));
            }
            break;
        }
    }

    //当修改该页面条目成功时，该页面接收消息并更新认证模型，同时刷新页面信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVerifyPersonalEvent(VerifyPersonalEvent verifyPersonalEvent) {

        VerifyPersonalInfo verifyPersonalInfo = verifyPersonalEvent.getVerifyPersonalInfo();

        mVerifyPersonalAndCompany.setPersonalInfo(verifyPersonalInfo);

        mPresenter.save(mVerifyPersonalAndCompany.getPersonalInfo());
    }

    //当从该页面跳转至车辆信息页面，并从车辆信息页面返回时，该页面接收消息并更新认证模型
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVerifyBackEvent(VerifyBackEvent verifyBackEvent) {

        mVerifyPersonalAndCompany = verifyBackEvent.getVerifyPersonalAndCompany();
    }

    //当从车辆信息页面申请认证成功后，该页面接收消息并关闭
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVerifyApplyEvent(VerifyApplyEvent verifyApplyEvent) {

        if(verifyApplyEvent.isHasApplied()){
            finish();
        }
    }
}

