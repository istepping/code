package com.xmw.qiyun.ui.showText;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.util.manage.RouterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongyuan on 2017/9/22.
 */

@Route(RouterUtil.ROUTER_SHOW_TEXT)
public class ShowTextActivity extends BaseActivity implements ShowTextContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.webView)
    WebView mWebView;

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_TXT = "EXTRA_TXT";

    @Override
    public void setPresenter(ShowTextContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text);

        ButterKnife.bind(this);

        init();
    }

    @Override
    public void init() {

        mTitle.setText(getIntent().getExtras().getString(EXTRA_TITLE));
        mWebView.loadUrl(getIntent().getExtras().getString(EXTRA_TXT));
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
