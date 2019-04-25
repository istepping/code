package com.xmw.qiyun.ui.store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseActivity;
import com.xmw.qiyun.data.model.event.ResultEvent;
import com.xmw.qiyun.util.JSUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/1/10.
 */

@Route(RouterUtil.ROUTER_STORE)
public class StoreActivity extends BaseActivity implements StoreContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    public void setPresenter(StoreContract.Presenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        ButterKnife.bind(this);

        init();
    }

    @Override
    public void init() {

        mTitle.setText(getString(R.string.store_title));
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.addJavascriptInterface(new JSUtil(StoreActivity.this), "JS");
        mWebView.loadUrl("file:///android_asset/store/index.html");

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                return false;
            }
        });
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultEvent(ResultEvent resultEvent) {

        mWebView.evaluateJavascript(resultEvent.isSuccess() ? "javascript:paySuccess()" : "javascript:payFail()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {

            }
        });
    }
}
