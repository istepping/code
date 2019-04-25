package com.xmw.qiyun.ui.publish.publishDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.net.publish.PublishDetail;
import com.xmw.qiyun.data.model.net.publish.PublishStream;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.adapter.common.CommonAdapter;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.manage.RouterUtil;
import com.xmw.qiyun.view.HorizontalListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongyuan on 2017/8/31.
 */

@Route(RouterUtil.ROUTER_PUBLISH_DETAIL)
public class PublishDetailActivity extends BaseActivity implements PublishDetailContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.publish_detail_time)
    TextView mTime;
    @BindView(R.id.publish_detail_location_start)
    TextView mStart;
    @BindView(R.id.publish_detail_location_start_detail)
    TextView mStartDetail;
    @BindView(R.id.publish_detail_location_end)
    TextView mEnd;
    @BindView(R.id.publish_detail_location_end_detail)
    TextView mEndDetail;
    @BindView(R.id.publish_detail_category)
    HorizontalListView mCategory;
    @BindView(R.id.publish_detail_map)
    TextView mMap;
    @BindView(R.id.publish_detail_mark)
    TextView mMark;

    PublishDetailContract.Presenter mPresenter;

    public static final String EXTRA_PUBLISH_DETAIL = "EXTRA_PUBLISH_DETAIL";

    @Override
    public void setPresenter(PublishDetailContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_detail);

        ButterKnife.bind(this);

        mPresenter = new PublishDetailPresentImpl(this, this);

        init();
    }

    @Override
    public void init() {

        mTitle.setText(getString(R.string.cargo_detail_title));

        mPresenter.getData(GsonImpl.init().toObject(getIntent().getExtras().getString(EXTRA_PUBLISH_DETAIL), PublishStream.class));
    }

    @Override
    public void refreshData(PublishDetail publishDetail) {

        mTime.setText(publishDetail.getReleasedTimeName());

        mStart.setText(publishDetail.getLoadProvince_Value()
                + publishDetail.getLoadCity_Value()
                + (CommonUtil.isNullOrEmpty(publishDetail.getLoadCounty_Value()) ? "" : publishDetail.getLoadCounty_Value()));

        if(CommonUtil.isNullOrEmpty(publishDetail.getLoadCounty_Value())){

            int length = publishDetail.getLoadProvince_Value().length() + publishDetail.getLoadCity_Value().length();

            mStartDetail.setVisibility(publishDetail.getLoadPlace().length() == length ? View.GONE : View.VISIBLE);
            mStartDetail.setText(publishDetail.getLoadPlace().substring(length));

        }else{

            int length = publishDetail.getLoadCity_Value().length() + publishDetail.getLoadCounty_Value().length();

            mStartDetail.setVisibility(publishDetail.getLoadPlace().length() == length ? View.GONE : View.VISIBLE);
            mStartDetail.setText(publishDetail.getLoadPlace().substring(length));
        }

        mEnd.setText(publishDetail.getUnloadProvince_Value()
                + publishDetail.getUnloadCity_Value()
                + (CommonUtil.isNullOrEmpty(publishDetail.getUnloadCounty_Value()) ? "" : publishDetail.getUnloadCounty_Value()));

        if(CommonUtil.isNullOrEmpty(publishDetail.getUnloadCounty_Value())){

            int length = publishDetail.getUnloadProvince_Value().length() + publishDetail.getUnloadCity_Value().length();

            mEndDetail.setVisibility(publishDetail.getUnloadPlace().length() == length ? View.GONE : View.VISIBLE);
            mEndDetail.setText(publishDetail.getUnloadPlace().substring(length));

        }else{

            int length = publishDetail.getUnloadCity_Value().length() + publishDetail.getUnloadCounty_Value().length();

            mEndDetail.setVisibility(publishDetail.getUnloadPlace().length() == length ? View.GONE : View.VISIBLE);
            mEndDetail.setText(publishDetail.getUnloadPlace().substring(length));
        }

        mCategory.setVisibility(CommonUtil.isNullOrEmpty(publishDetail.getGoodsShortInfo()) ? View.GONE : View.VISIBLE);
        mCategory.setAdapter(new CommonAdapter(this, CommonUtil.getStringListMulti(publishDetail.getGoodsShortInfo())));

        /*mMap.setVisibility(publishDetail.getMileage() == 0 ? View.GONE : View.VISIBLE);
        mMap.setText(new StringBuilder().append(getString(R.string.publish_detail_map)).append(String.valueOf(publishDetail.getMileage())).append(getString(R.string.publish_detail_km)));*/

        mMark.setText(CommonUtil.isNullOrEmpty(publishDetail.getRemarkValue()) ? getString(R.string.cargo_detail_no_remark) : publishDetail.getRemarkValue());
    }

    @OnClick({
            R.id.back
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.back: {

                onBackPressed();
            }
            break;
        }
    }
}
