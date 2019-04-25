package com.xmw.qiyun.ui.adapter.welcome;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.pacific.adapter.PagerAdapterHelper;
import com.pacific.adapter.ViewPagerAdapter;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.local.WelcomeItem;
import com.xmw.qiyun.ui.welcome.WelcomeContract;
import com.xmw.qiyun.util.image.ImageUtil;

import java.util.List;

/**
 * Created by hongyuan on 2017/9/19.
 */

public class WelcomePagerAdapter extends ViewPagerAdapter<WelcomeItem> {

    private Context mContext;
    private WelcomeContract.Presenter mPresenter;

    public WelcomePagerAdapter(Context context, WelcomeContract.Presenter presenter, @Nullable List<WelcomeItem> data) {

        super(context, data, R.layout.item_welcome);

        mContext = context;
        mPresenter = presenter;
    }

    @Override
    protected void convert(PagerAdapterHelper helper, WelcomeItem item) {

        ImageUtil.loadLocal(mContext, helper.<ImageView>getView(R.id.item_image), item.getImageUrl());
        helper.getView(R.id.item_button).setVisibility(helper.getPosition() == data.size() - 1 ? View.VISIBLE : View.GONE);
        helper.getView(R.id.item_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.checkHasLogin();
            }
        });
    }
}
