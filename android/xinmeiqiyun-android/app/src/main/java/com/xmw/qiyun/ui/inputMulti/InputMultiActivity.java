package com.xmw.qiyun.ui.inputMulti;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.net.publish.PublishBody;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongyuan on 2017/8/28.
 */

@Route(RouterUtil.ROUTER_INPUT_MULTI)
public class
InputMultiActivity extends BaseActivity implements InputMultiContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.input_min)
    EditText mInputMin;
    @BindView(R.id.input_max)
    EditText mInputMax;

    InputMultiContract.Presenter mPresenter;

    private PublishBody mPublishBody;

    public static final String EXTRA_INPUT_VALUE = "EXTRA_INPUT_VALUE";

    @Override
    public void setPresenter(InputMultiContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_multi);

        ButterKnife.bind(this);

        mPresenter = new InputMultiPresentImpl(this);

        init();
    }

    @Override
    public void init() {

        mPublishBody = GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_INPUT_VALUE), PublishBody.class);
        mTitle.setText(getString(R.string.publish_cargo_num_title));

        mInputMin.setText(mPublishBody.getGoodsNumberMin() == 0 ? "" : String.valueOf(mPublishBody.getGoodsNumberMin()));
        mInputMin.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        mInputMax.setText(mPublishBody.getGoodsNumberMax() == 0 ? "" : String.valueOf(mPublishBody.getGoodsNumberMax()));
        mInputMax.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
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

                if (CommonUtil.isNullOrEmpty(mInputMin.getText().toString()) | CommonUtil.isNullOrEmpty(mInputMax.getText().toString())) {

                    NoteUtil.showToast(this, getString(R.string.toast_empty));

                    return;
                }

                if (CommonUtil.checkInputInvalid(mInputMax.getText().toString()) | CommonUtil.checkInputInvalid(mInputMax.getText().toString())) {

                    NoteUtil.showToast(this, getString(R.string.toast_invalid));

                    return;
                }

                if (!CommonUtil.isNullOrEmpty(mInputMin.getText().toString()) & !CommonUtil.isNullOrEmpty(mInputMax.getText().toString())) {

                    if (Float.valueOf(mInputMin.getText().toString()) >= Float.valueOf(mInputMax.getText().toString())) {

                        NoteUtil.showToast(this, getString(R.string.toast_invalid));

                        return;
                    }
                }

                String minInput = mInputMin.getText().toString();

                if ((minInput.contains(".") && minInput.substring(minInput.indexOf(".")).length() > 2)) {

                    minInput = minInput.substring(0, minInput.indexOf(".") + 3);
                }

                String maxInput = mInputMax.getText().toString();

                if ((maxInput.contains(".") && maxInput.substring(maxInput.indexOf(".")).length() > 2)) {

                    maxInput = maxInput.substring(0, maxInput.indexOf(".") + 3);
                }

                mPublishBody.setGoodsNumberMin(CommonUtil.isNullOrEmpty(minInput) ? 0 : Float.valueOf(minInput));
                mPublishBody.setGoodsNumberMax(CommonUtil.isNullOrEmpty(maxInput) ? 0 : Float.valueOf(maxInput));

                mPresenter.save(mPublishBody);
            }
            break;
        }
    }
}
