package com.xmw.qiyun.ui.adapter.common;

import android.content.Context;
import android.view.View;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.net.publish.PublishBody;
import com.xmw.qiyun.ui.publish.PublishContract;

import java.util.List;

/**
 * Created by hongyuan on 2017/9/2.
 */

public class LocationTopAdapter extends Adapter<LocationItem> {

    private PublishContract.View mView;
    private PublishBody mPublishBody;

    public LocationTopAdapter(Context context, PublishContract.View view, PublishBody publishBody, List<LocationItem> data) {

        super(context, data, R.layout.item_location_select);

        mView = view;
        mPublishBody = publishBody;
    }

    @Override
    protected void convert(AdapterHelper helper, final LocationItem item) {

        helper.setText(R.id.item_title, item.getStandard().getValue());

        helper.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = 0; i < mPublishBody.getNotLookCitys().size(); i++){

                    if(mPublishBody.getNotLookCitys().get(i).getCityId().equals(item.getStandard().getId())){

                        mPublishBody.getNotLookCitys().remove(i);

                        break;
                    }
                }

                mView.refreshLocationTop(mPublishBody);
                mView.refreshLocationBottom(mPublishBody);
            }
        });
    }
}
