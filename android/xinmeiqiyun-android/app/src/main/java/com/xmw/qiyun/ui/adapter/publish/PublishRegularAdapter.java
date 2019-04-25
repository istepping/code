package com.xmw.qiyun.ui.adapter.publish;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.net.publish.PublishBody;
import com.xmw.qiyun.ui.adapter.common.CommonAdapter;
import com.xmw.qiyun.ui.publish.PublishContract;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.view.HorizontalListView;

import java.util.List;

/**
 * Created by hongyuan on 2017/9/5.
 */

public class PublishRegularAdapter extends Adapter<PublishBody> {

    private PublishContract.Presenter mPresenter;

    public PublishRegularAdapter(Context context, PublishContract.Presenter presenter, @Nullable List<PublishBody> data) {

        super(context, data, R.layout.item_publish);

        mPresenter = presenter;
    }

    @Override
    protected void convert(AdapterHelper helper, final PublishBody item) {

        helper.getItemView().setTag(item);

        helper.setText(R.id.item_location_start, item.getLoadPlace());
        helper.setText(R.id.item_location_end, item.getUnloadPlace());

        helper.getView(R.id.item_detail).setVisibility(CommonUtil.isNullOrEmpty(item.getGoodsShortInfo()) ? View.GONE : View.VISIBLE);
        ((HorizontalListView)helper.getView(R.id.item_detail)).setAdapter(new CommonAdapter(context, CommonUtil.getStringListMulti(item.getGoodsShortInfo())));

        helper.setVisible(R.id.item_time, View.GONE);
        helper.setVisible(R.id.item_icon, View.GONE);

        helper.setVisible(R.id.item_share, View.GONE);
        helper.setVisible(R.id.item_edit, View.GONE);
        helper.setVisible(R.id.item_send, View.GONE);
        helper.setVisible(R.id.item_resend, View.GONE);
        helper.setVisible(R.id.item_close, View.GONE);
        helper.setVisible(R.id.item_delete, View.GONE);

        helper.setVisible(R.id.item_icon, View.VISIBLE);
        helper.setVisible(R.id.item_icon_resend, item.isRepeat() ? View.VISIBLE : View.GONE);
        helper.setVisible(R.id.item_icon_city, item.getNotLookCitys().size() == 0 ? View.GONE : View.VISIBLE);

        helper.setVisible(R.id.item_edit, View.VISIBLE);
        helper.setVisible(R.id.item_send, View.VISIBLE);
        helper.setVisible(R.id.item_delete, View.VISIBLE);

        /*
        * 编辑
        * */
        helper.setOnClickListener(R.id.item_edit, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.doEdit(item);
            }
        });

        /*
        * 发布
        * */
        helper.setOnClickListener(R.id.item_send, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.doSend(item);
            }
        });

        /*
        * 删除
        * */
        helper.setOnClickListener(R.id.item_delete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.doDelete(item);
            }
        });
    }
}
