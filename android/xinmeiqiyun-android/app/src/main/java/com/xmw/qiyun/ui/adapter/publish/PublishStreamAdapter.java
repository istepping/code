package com.xmw.qiyun.ui.adapter.publish;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.net.publish.PublishStream;
import com.xmw.qiyun.ui.adapter.common.CommonAdapter;
import com.xmw.qiyun.ui.publish.publishStreamBy.PublishStreamByContract;
import com.xmw.qiyun.ui.publish.publishStreamIn.PublishStreamInContract;
import com.xmw.qiyun.ui.publish.publishStreamStop.PublishStreamStopContract;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.view.HorizontalListView;

import java.util.List;

/**
 * Created by hongyuan on 2017/8/30.
 */

public class PublishStreamAdapter extends Adapter<PublishStream> {

    private int mStreamType;
    private PublishStreamByContract.Presenter mByPresenter;
    private PublishStreamInContract.Presenter mInPresenter;
    private PublishStreamStopContract.Presenter mStopPresenter;

    public PublishStreamAdapter(
            Context context,
            int streamType,
            PublishStreamByContract.Presenter byPresenter,
            PublishStreamInContract.Presenter inPresenter,
            PublishStreamStopContract.Presenter stopPresenter,
            @Nullable List<PublishStream> data) {

        super(context, data, R.layout.item_publish);

        mStreamType = streamType;
        mByPresenter = byPresenter;
        mInPresenter = inPresenter;
        mStopPresenter = stopPresenter;
    }

    @Override
    protected void convert(AdapterHelper helper, final PublishStream item) {

        helper.getItemView().setTag(item);

        helper.setText(R.id.item_location_start, item.getLoadPlace());
        helper.setText(R.id.item_location_end, item.getUnloadPlace());

        helper.getView(R.id.item_detail).setVisibility(CommonUtil.isNullOrEmpty(item.getGoodsShortInfo()) ? View.GONE : View.VISIBLE);
        ((HorizontalListView)helper.getView(R.id.item_detail)).setAdapter(new CommonAdapter(context, CommonUtil.getStringListMulti(item.getGoodsShortInfo())));

        helper.setText(R.id.item_time, CommonUtil.isNullOrEmpty(item.getReleasedTimeName()) ? context.getString(R.string.publish_stream_no_time) : item.getReleasedTimeName());

        helper.setVisible(R.id.item_icon, View.GONE);
        helper.setVisible(R.id.item_share, View.GONE);
        helper.setVisible(R.id.item_edit, View.GONE);
        helper.setVisible(R.id.item_send, View.GONE);
        helper.setVisible(R.id.item_resend, View.GONE);
        helper.setVisible(R.id.item_close, View.GONE);
        helper.setVisible(R.id.item_delete, View.GONE);

        switch (mStreamType){

            default:
                break;

            case 0 :{

                helper.setVisible(R.id.item_close, View.VISIBLE);
            }
            break;

            case 1:{

                helper.setVisible(R.id.item_share, View.VISIBLE);
                helper.setVisible(R.id.item_resend, View.VISIBLE);
                helper.setVisible(R.id.item_close, View.VISIBLE);
            }
            break;

            case 2:{

                helper.setVisible(R.id.item_resend, View.VISIBLE);
                helper.setVisible(R.id.item_delete, View.VISIBLE);
            }
            break;
        }

        helper.setOnClickListener(R.id.item_wrap, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (mStreamType){

                    default:
                        break;

                    case 0:{

                        mByPresenter.goDetail(item);
                    }
                    break;

                    case 1:{

                        mInPresenter.goDetail(item);
                    }
                    break;

                    case 2:{

                        mStopPresenter.goDetail(item);
                    }
                    break;
                }
            }
        });

        /*
        * 发布中
        * */
        helper.setOnClickListener(R.id.item_share, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mInPresenter.doShare(item);
            }
        });

        /*
        * 发布中
        * 已关闭
        * */
        helper.setOnClickListener(R.id.item_resend, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (mStreamType){

                    default:
                        break;

                    case 1:{

                        mInPresenter.doResend(item);
                    }
                    break;

                    case 2:{

                        mStopPresenter.doResend(item);
                    }
                    break;
                }
            }
        });

        /*
        * 待审核
        * 发布中
        * */
        helper.setOnClickListener(R.id.item_close, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (mStreamType){

                    default:
                        break;

                    case 0:{

                        mByPresenter.doClose(item);
                    }
                    break;

                    case 1:{

                        mInPresenter.doClose(item);
                    }
                }
            }
        });

        /*
        * 已关闭
        * */
        helper.setOnClickListener(R.id.item_delete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mStopPresenter.doDelete(item);
            }
        });
    }
}
