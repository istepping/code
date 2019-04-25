package com.xmw.qiyun.ui.adapter.truck;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.net.truck.Truck;
import com.xmw.qiyun.ui.truck.TruckContract;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.image.ImageUtil;

import java.util.List;

/**
 * Created by hongyuan on 2017/8/30.
 */

public class TruckAdapter extends Adapter<Truck> {

    private TruckContract.Presenter mPresenter;

    public TruckAdapter(Context context, TruckContract.Presenter presenter, @Nullable List<Truck> data) {

        super(context, data, R.layout.item_truck);

        mPresenter = presenter;
    }

    @Override
    protected void convert(AdapterHelper helper, final Truck item) {

        if (!CommonUtil.isNullOrEmpty(item.getHeadPortraitId())) {
            ImageUtil.loadAvatar(context, helper.<ImageView>getView(R.id.item_avatar), item.getHeadPortraitId(), R.drawable.default_avatar);
        } else {
            helper.<ImageView>getView(R.id.item_avatar).setImageResource(R.drawable.default_avatar);
        }
        helper.setText(R.id.item_name, item.getName());
        helper.setText(R.id.item_vehicle_num, CommonUtil.isNullOrEmpty(item.getVehicleNum()) ? context.getString(R.string.truck_detail_no_num) : item.getVehicleNum());
        helper.setText(R.id.item_vehicle_detail, item.getVehicleProperty());
        helper.setText(R.id.item_time, item.getRegisterTime());

        switch (item.getStatus()) {

            default:
                break;

            case 1: {

                ((TextView) helper.getView(R.id.item_name)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_1, 0);
            }
            break;

            case 2: {

                ((TextView) helper.getView(R.id.item_name)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_2, 0);
            }
            break;
        }

        helper.getView(R.id.item_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.goCallOwner(item.getMobile());
            }
        });

        helper.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.goTruckDetail(item.getId());
            }
        });

        helper.getItemView().setTag(item);
    }
}
