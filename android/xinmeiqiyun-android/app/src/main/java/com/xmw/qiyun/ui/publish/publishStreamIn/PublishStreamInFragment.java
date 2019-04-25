package com.xmw.qiyun.ui.publish.publishStreamIn;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenenyu.router.annotation.Route;
import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;
import com.xmw.qiyun.R;
import com.xmw.qiyun.base.BaseFragment;
import com.xmw.qiyun.data.model.event.PublishStreamInEvent;
import com.xmw.qiyun.data.model.net.publish.PublishStreamList;
import com.xmw.qiyun.ui.adapter.publish.PublishStreamAdapter;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongyuan on 2017/8/30.
 */

@Route(RouterUtil.ROUTER_PUBLISH_STREAM_IN)
public class PublishStreamInFragment extends BaseFragment implements PublishStreamInContract.View {

    @BindView(R.id.publish_stream_list)
    SuperListview mList;

    PublishStreamInContract.Presenter mPresenter;

    private int mPage;
    private int mTotalSize;

    @Override
    public void setPresenter(PublishStreamInContract.Presenter presenter) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_publish_stream, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        mPresenter = new PublishStreamInPresentImpl(getContext(), this);

        init();
    }

    @Override
    public void init() {

        doRefresh();

        //下拉刷新
        mList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                doRefresh();
            }
        });

        //上拉加载
        mList.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {

                if (mTotalSize != mList.getAdapter().getCount()) {

                    mPage++;
                    mPresenter.getData(mPage);

                } else {

                    stopRefresh();
                }
            }
        });
    }

    @Override
    public void showList(PublishStreamList publishStreamList) {

        mTotalSize = publishStreamList.getTotal();

        if (mPage == 1) {

            mList.setAdapter(new PublishStreamAdapter(getContext(), 1, null, mPresenter, null, publishStreamList.getResultData()));

        } else {

            ((PublishStreamAdapter) mList.getAdapter()).addAll(publishStreamList.getResultData());
            ((PublishStreamAdapter) mList.getAdapter()).notifyDataSetChanged();
        }

        stopRefresh();
    }

    @Override
    public void doRefresh() {

        mPage = 1;
        mPresenter.getData(mPage);
    }

    @Override
    public void stopRefresh() {

        mList.hideProgress();
        mList.hideMoreProgress();
        mList.setLoadingMore(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPublishStreamInEvent(PublishStreamInEvent publishStreamInEvent) {

        doRefresh();
    }
}
