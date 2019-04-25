package com.xmw.qiyun.ui.publish.publishShare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.util.image.ImageUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/9/5.
 */

@Route(RouterUtil.ROUTER_PUBLISH_SHARE)
public class PublishShareActivity extends BaseActivity implements PublishShareContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.share_img)
    ImageView mImage;

    PublishShareContract.Presenter mPresenter;

    private String imageUrl;

    public static final String EXTRA_IMAGE_ID = "EXTRA_IMAGE_ID";

    @Override
    public void setPresenter(PublishShareContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_share);

        ButterKnife.bind(this);

        mPresenter = new PublishSharePresentImpl(this);

        init();
    }

    @Override
    public void init() {

        mTitle.setText(getString(R.string.publish_stream_share));

        Log.d("ImageId", getIntent().getExtras().getString(EXTRA_IMAGE_ID));

        ImageUtil.loadWeChat(this, mImage, getIntent().getExtras().getString(EXTRA_IMAGE_ID));

        imageUrl = API.BASE_URL + "wechat/image/" + getIntent().getExtras().getString(EXTRA_IMAGE_ID);
    }

    @OnClick({
            R.id.back,
            R.id.share_wechat,
            R.id.share_circle
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {

            default:
                break;

            case R.id.back: {

                onBackPressed();
            }
            break;

            case R.id.share_wechat:{

                mPresenter.shareImage(imageUrl, true);
            }
            break;

            case R.id.share_circle:{

                mPresenter.shareImage(imageUrl, false);
            }
            break;
        }
    }
}
