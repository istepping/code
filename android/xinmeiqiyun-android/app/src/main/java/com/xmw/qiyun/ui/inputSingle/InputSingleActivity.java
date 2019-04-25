package com.xmw.qiyun.ui.inputSingle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.net.publish.PublishBody;
import com.xmw.qiyun.data.model.net.user.UserInfo;
import com.xmw.qiyun.data.model.net.user.VerifyCompanyInfo;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalInfo;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.check.IdCardUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongyuan on 2017/8/28.
 */

@Route(RouterUtil.ROUTER_INPUT_SINGLE)
public class InputSingleActivity extends BaseActivity implements InputSingleContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.input)
    EditText mInput;

    InputSingleContract.Presenter mPresenter;

    private int inputType;
    private boolean isNullable = false;

    private UserInfo mUserInfo;
    private VerifyPersonalInfo mVerifyPersonalInfo;
    private VerifyCompanyInfo mVerifyCompanyInfo;
    private PublishBody mPublishBody;

    public static final String EXTRA_INPUT_TYPE = "EXTRA_INPUT_TYPE";
    public static final String EXTRA_INPUT_VALUE = "EXTRA_INPUT_VALUE";

    public static final int EXTRA_INPUT_TYPE_INFO_NAME = 0;
    public static final int EXTRA_INPUT_TYPE_INFO_COMPANY_NAME = 1;
    public static final int EXTRA_INPUT_TYPE_INFO_COMPANY_LOCATION = 2;
    public static final int EXTRA_INPUT_TYPE_VERIFY_PERSONAL_NAME = 4;
    public static final int EXTRA_INPUT_TYPE_VERIFY_PERSONAL_CODE = 5;
    public static final int EXTRA_INPUT_TYPE_VERIFY_COMPANY_NAME = 6;
    public static final int EXTRA_INPUT_TYPE_VERIFY_COMPANY_ALIAS = 7;
    public static final int EXTRA_INPUT_TYPE_VERIFY_COMPANY_CODE = 8;
    public static final int EXTRA_INPUT_TYPE_VERIFY_COMPANY_LOCATION = 9;
    public static final int EXTRA_INPUT_TYPE_PUBLISH_START_DETAIL = 10;
    public static final int EXTRA_INPUT_TYPE_PUBLISH_END_DETAIL = 11;
    public static final int EXTRA_INPUT_TYPE_PUBLISH_NUM = 12;
    public static final int EXTRA_INPUT_TYPE_PUBLISH_FEE = 13;
    public static final int EXTRA_INPUT_TYPE_PUBLISH_LOAD_FEE = 14;
    public static final int EXTRA_INPUT_TYPE_PUBLISH_UNLOAD_FEE = 15;

    @Override
    public void setPresenter(InputSingleContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_single);

        ButterKnife.bind(this);

        mPresenter = new InputSinglePresentImpl(this);

        init();
    }

    @Override
    public void init() {

        inputType = getIntent().getExtras().getInt(EXTRA_INPUT_TYPE);

        switch (inputType) {

            default:
                break;

            case EXTRA_INPUT_TYPE_INFO_NAME: {

                mUserInfo = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), UserInfo.class);
                mTitle.setText(getString(R.string.information_name));
                mInput.setText(mUserInfo.getName());
            }
            break;

            case EXTRA_INPUT_TYPE_INFO_COMPANY_NAME: {

                mUserInfo = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), UserInfo.class);
                mTitle.setText(getString(R.string.information_company_name));
                mInput.setText(mUserInfo.getCompanyName());
            }
            break;

            case EXTRA_INPUT_TYPE_INFO_COMPANY_LOCATION: {

                mUserInfo = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), UserInfo.class);
                mTitle.setText(getString(R.string.information_company_location));
                mInput.setText(mUserInfo.getAddress());
            }
            break;

            case EXTRA_INPUT_TYPE_VERIFY_PERSONAL_NAME: {

                mVerifyPersonalInfo = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), VerifyPersonalInfo.class);
                mTitle.setText(getString(R.string.verify_personal_name));
                mInput.setText(mVerifyPersonalInfo.getName());
            }
            break;

            case EXTRA_INPUT_TYPE_VERIFY_PERSONAL_CODE: {

                mVerifyPersonalInfo = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), VerifyPersonalInfo.class);
                mTitle.setText(getString(R.string.verify_personal_code));
                mInput.setText(mVerifyPersonalInfo.getIDCardNum());
            }
            break;

            case EXTRA_INPUT_TYPE_VERIFY_COMPANY_NAME: {

                mVerifyCompanyInfo = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), VerifyCompanyInfo.class);
                mTitle.setText(getString(R.string.verify_company_name));
                mInput.setText(mVerifyCompanyInfo.getCompanyName());
            }
            break;

            case EXTRA_INPUT_TYPE_VERIFY_COMPANY_ALIAS: {

                mVerifyCompanyInfo = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), VerifyCompanyInfo.class);
                mTitle.setText(getString(R.string.verify_company_alias));
                mInput.setText(mVerifyCompanyInfo.getCompanyShortName());
            }
            break;

            case EXTRA_INPUT_TYPE_VERIFY_COMPANY_CODE: {

                mVerifyCompanyInfo = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), VerifyCompanyInfo.class);
                mTitle.setText(getString(R.string.verify_company_code));
                mInput.setText(mVerifyCompanyInfo.getLicenseNum());
            }
            break;

            case EXTRA_INPUT_TYPE_VERIFY_COMPANY_LOCATION: {

                mVerifyCompanyInfo = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), VerifyCompanyInfo.class);
                mTitle.setText(getString(R.string.verify_company_location));
                mInput.setText(mVerifyCompanyInfo.getAddress());
            }
            break;

            case EXTRA_INPUT_TYPE_PUBLISH_START_DETAIL:{

                mPublishBody = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), PublishBody.class);
                mTitle.setText(getString(R.string.publish_cargo_start_detail_title));
                mInput.setText(mPublishBody.getLoadAddress());

                isNullable = true;
            }
            break;

            case EXTRA_INPUT_TYPE_PUBLISH_END_DETAIL:{

                mPublishBody = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), PublishBody.class);
                mTitle.setText(getString(R.string.publish_cargo_end_detail_title));
                mInput.setText(mPublishBody.getUnloadAddress());

                isNullable = true;
            }
            break;

            case EXTRA_INPUT_TYPE_PUBLISH_NUM:{

                mInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                mPublishBody = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), PublishBody.class);
                mTitle.setText(getString(R.string.publish_cargo_num_title));
                mInput.setText(mPublishBody.getGoodsNumber() == 0 ? "" : String.valueOf(mPublishBody.getGoodsNumber()));
                mInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

                isNullable = true;
            }
            break;

            case EXTRA_INPUT_TYPE_PUBLISH_FEE:{

                mInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                mPublishBody = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), PublishBody.class);
                mTitle.setText(getString(R.string.publish_cargo_fee_title));
                mInput.setText(mPublishBody.getFreight() == 0 ? "" : String.valueOf(mPublishBody.getFreight()));
                mInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

                isNullable = true;
            }
            break;

            case EXTRA_INPUT_TYPE_PUBLISH_LOAD_FEE:{

                mInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                mPublishBody = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), PublishBody.class);
                mTitle.setText(getString(R.string.publish_cargo_load_fee_title));
                mInput.setText(mPublishBody.getLoadVehicleCost() == 0 ? "" : String.valueOf(mPublishBody.getLoadVehicleCost()));
                mInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

                isNullable = true;
            }
            break;

            case EXTRA_INPUT_TYPE_PUBLISH_UNLOAD_FEE:{

                mInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                mPublishBody = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), PublishBody.class);
                mTitle.setText(getString(R.string.publish_cargo_unload_fee_title));
                mInput.setText(mPublishBody.getUnloadVehicleCost() == 0 ? "" : String.valueOf(mPublishBody.getUnloadVehicleCost()));
                mInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

                isNullable = true;
            }
            break;
        }
    }

    @OnClick({
            R.id.back,
            R.id.save
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.back: {

                onBackPressed();
            }
            break;

            case R.id.save: {

                if(CommonUtil.isNullOrEmpty(mInput.getText().toString()) & !isNullable){

                    NoteUtil.showToast(this, getString(R.string.toast_empty));

                    return;
                }

                switch (inputType) {

                    default:
                        break;

                    case EXTRA_INPUT_TYPE_INFO_NAME: {

                        mUserInfo.setName(mInput.getText().toString());
                        mPresenter.save(mUserInfo);
                    }
                    break;

                    case EXTRA_INPUT_TYPE_INFO_COMPANY_NAME: {

                        mUserInfo.setCompanyName(mInput.getText().toString());
                        mPresenter.save(mUserInfo);
                    }
                    break;

                    case EXTRA_INPUT_TYPE_INFO_COMPANY_LOCATION: {

                        mUserInfo.setAddress(mInput.getText().toString());
                        mPresenter.save(mUserInfo);
                    }
                    break;

                    case EXTRA_INPUT_TYPE_VERIFY_PERSONAL_NAME: {

                        mVerifyPersonalInfo.setName(mInput.getText().toString());
                        mPresenter.save(mVerifyPersonalInfo);
                    }
                    break;

                    case EXTRA_INPUT_TYPE_VERIFY_PERSONAL_CODE: {

                        if(IdCardUtil.isIdCard(mInput.getText().toString())){

                            mVerifyPersonalInfo.setIDCardNum(mInput.getText().toString());
                            mPresenter.save(mVerifyPersonalInfo);

                        }else{

                            NoteUtil.showToast(this, getString(R.string.verify_personal_code_error));
                        }
                    }
                    break;

                    case EXTRA_INPUT_TYPE_VERIFY_COMPANY_NAME: {

                        mVerifyCompanyInfo.setCompanyName(mInput.getText().toString());
                        mPresenter.save(mVerifyCompanyInfo);
                    }
                    break;

                    case EXTRA_INPUT_TYPE_VERIFY_COMPANY_ALIAS: {

                        mVerifyCompanyInfo.setCompanyShortName(mInput.getText().toString());
                        mPresenter.save(mVerifyCompanyInfo);
                    }
                    break;

                    case EXTRA_INPUT_TYPE_VERIFY_COMPANY_CODE: {

                        mVerifyCompanyInfo.setLicenseNum(mInput.getText().toString());
                        mPresenter.save(mVerifyCompanyInfo);
                    }
                    break;

                    case EXTRA_INPUT_TYPE_VERIFY_COMPANY_LOCATION: {

                        mVerifyCompanyInfo.setAddress(mInput.getText().toString());
                        mPresenter.save(mVerifyCompanyInfo);
                    }
                    break;

                    case EXTRA_INPUT_TYPE_PUBLISH_START_DETAIL:{

                        mPublishBody.setLoadAddress(mInput.getText().toString());
                        mPresenter.save(mPublishBody);
                    }
                    break;

                    case EXTRA_INPUT_TYPE_PUBLISH_END_DETAIL:{

                        mPublishBody.setUnloadAddress(mInput.getText().toString());
                        mPresenter.save(mPublishBody);
                    }
                    break;

                    case EXTRA_INPUT_TYPE_PUBLISH_NUM:{

                        String args = mInput.getText().toString();

                        if (CommonUtil.checkInputInvalid(args)) {

                            NoteUtil.showToast(this, getString(R.string.toast_invalid));

                            return;
                        }

                        if((args.contains(".") && args.substring(args.indexOf(".")).length() > 2)){

                            args = args.substring(0, args.indexOf(".") + 3);
                        }

                        mPublishBody.setGoodsNumber(CommonUtil.isNullOrEmpty(args) ? 0 : Float.parseFloat(args));
                        mPresenter.save(mPublishBody);
                    }
                    break;

                    case EXTRA_INPUT_TYPE_PUBLISH_FEE:{

                        String args = mInput.getText().toString();

                        if (CommonUtil.checkInputInvalid(args)) {

                            NoteUtil.showToast(this, getString(R.string.toast_invalid));

                            return;
                        }

                        if((args.contains(".") && args.substring(args.indexOf(".")).length() > 2)){

                            args = args.substring(0, args.indexOf(".") + 3);
                        }

                        mPublishBody.setFreight(CommonUtil.isNullOrEmpty(args) ? 0 : Float.parseFloat(args));
                        mPresenter.save(mPublishBody);
                    }
                    break;

                    case EXTRA_INPUT_TYPE_PUBLISH_LOAD_FEE:{

                        String args = mInput.getText().toString();

                        if (CommonUtil.checkInputInvalid(args)) {

                            NoteUtil.showToast(this, getString(R.string.toast_invalid));

                            return;
                        }

                        if((args.contains(".") && args.substring(args.indexOf(".")).length() > 2)){

                            args = args.substring(0, args.indexOf(".") + 3);
                        }

                        mPublishBody.setLoadVehicleCost(CommonUtil.isNullOrEmpty(args) ? 0 : Float.parseFloat(args));
                        mPresenter.save(mPublishBody);
                    }
                    break;

                    case EXTRA_INPUT_TYPE_PUBLISH_UNLOAD_FEE:{

                        String args = mInput.getText().toString();

                        if (CommonUtil.checkInputInvalid(args)) {

                            NoteUtil.showToast(this, getString(R.string.toast_invalid));

                            return;
                        }

                        if((args.contains(".") && args.substring(args.indexOf(".")).length() > 2)){

                            args = args.substring(0, args.indexOf(".") + 3);
                        }

                        mPublishBody.setUnloadVehicleCost(CommonUtil.isNullOrEmpty(args) ? 0 : Float.parseFloat(args));
                        mPresenter.save(mPublishBody);
                    }
                    break;
                }
            }
            break;
        }
    }
}