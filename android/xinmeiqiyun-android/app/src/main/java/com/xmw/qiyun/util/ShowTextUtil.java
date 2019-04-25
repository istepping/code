package com.xmw.qiyun.util;

import android.content.Context;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.xmw.qiyun.R;
import com.xmw.qiyun.ui.showText.ShowTextActivity;
import com.xmw.qiyun.util.manage.RouterUtil;

/**
 * Created by hongyuan on 2017/9/22.
 */

public class ShowTextUtil extends ClickableSpan {

    public static final int EXTRA_SERVICE = 0;
    public static final int EXTRA_SECRET = 1;

    private Context mContext;
    private int mType;

    public ShowTextUtil(Context context, int type){

        mContext = context;
        mType = type;
    }

    @Override
    public void onClick(View widget) {

        Bundle bundle = new Bundle();

        switch (mType){

            default:
                break;

            case EXTRA_SERVICE:{

                bundle.putString(ShowTextActivity.EXTRA_TITLE, mContext.getString(R.string.agreement_service));
                bundle.putString(ShowTextActivity.EXTRA_TXT, "file:///android_asset/service_agreement.html");

                goShowText(bundle);
            }
            break;

            case EXTRA_SECRET:{

                bundle.putString(ShowTextActivity.EXTRA_TITLE, mContext.getString(R.string.agreement_secret));
                bundle.putString(ShowTextActivity.EXTRA_TXT, "file:///android_asset/secret_agreement.html");

                goShowText(bundle);
            }
            break;
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mContext.getResources().getColor(R.color.blue));
    }

    private void goShowText(Bundle bundle){

        RouterUtil.go(RouterUtil.ROUTER_SHOW_TEXT, mContext, bundle);
    }
}
