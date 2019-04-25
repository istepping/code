package com.xmw.qiyun.ui.adapter.common;

import android.content.Context;
import android.view.View;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.net.publish.PublishBody;
import com.xmw.qiyun.data.model.net.publish.PublishNotCity;
import com.xmw.qiyun.ui.publish.PublishContract;
import com.xmw.qiyun.util.manage.NoteUtil;

import java.util.List;

/**
 * Created by hongyuan on 2017/9/2.
 */

public class LocationMultiAdapter extends Adapter<LocationItem> {

    private Context mContext;
    private PublishContract.View mView;
    private PublishContract.Presenter mPresenter;
    private PublishBody mPublishBody;
    private int mDataType;

    public LocationMultiAdapter(Context context, PublishContract.View view, PublishContract.Presenter presenter, PublishBody publishBody, int dataType, List<LocationItem> data) {

        super(context, data, R.layout.item_location);

        mContext = context;
        mView = view;
        mPresenter = presenter;
        mPublishBody = publishBody;
        mDataType = dataType;
    }

    @Override
    protected void convert(final AdapterHelper helper, final LocationItem item) {

        helper.setText(R.id.item_title, item.getStandard().getValue());
        helper.setTextColor(R.id.item_title, item.isHasSelected() ? mContext.getResources().getColor(R.color.blue) : mContext.getResources().getColor(R.color.text3));
        helper.setBackgroundColor(R.id.item_wrap, item.isHasSelected() ? mContext.getResources().getColor(R.color.blue) : mContext.getResources().getColor(R.color.divider));

        helper.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (mDataType) {

                    default:
                        break;

                    case 1: {

                        mPresenter.getLocationMultiData(item.getStandard(), mDataType + 1, false, mPublishBody);
                    }
                    break;

                    case 2: {

                        if(item.isHasSelected()){

                            for(PublishNotCity publishNotCity : mPublishBody.getNotLookCitys()){

                                if(publishNotCity.getCityId().equals(item.getStandard().getId())){

                                    mPublishBody.getNotLookCitys().remove(publishNotCity);

                                    break;
                                }
                            }

                        }else{

                            if(mPublishBody.getNotLookCitys().size() == 3){

                                showAlert();

                                return;
                            }

                            PublishNotCity publishNotCity = new PublishNotCity();
                            publishNotCity.setCityId(item.getStandard().getId());

                            mPublishBody.getNotLookCitys().add(publishNotCity);
                        }

                        changeColor(helper, item);

                        mView.refreshLocationTop(mPublishBody);
                    }
                    break;
                }
            }
        });
    }

    private void changeColor(AdapterHelper helper, LocationItem item) {

        boolean b = item.isHasSelected();

        helper.setTextColor(R.id.item_title, item.isHasSelected() ? mContext.getResources().getColor(R.color.text3) : mContext.getResources().getColor(R.color.blue));
        helper.setBackgroundColor(R.id.item_wrap, item.isHasSelected() ? mContext.getResources().getColor(R.color.divider) : mContext.getResources().getColor(R.color.blue));

        item.setHasSelected(!b);
    }

    private void showAlert() {

        NoteUtil.showToast(mContext, mContext.getString(R.string.toast_up));
    }
}
