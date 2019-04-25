package com.xmw.qiyun.ui.adapter.cargo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.net.cargo.Cargo;
import com.xmw.qiyun.ui.adapter.common.CommonAdapter;
import com.xmw.qiyun.ui.cargo.cargoList.CargoListContract;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.image.ImageUtil;
import com.xmw.qiyun.view.HorizontalListView;

import java.util.List;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class CargoAdapter extends Adapter<Cargo> {
    //初始化视图,初始化事件
    private CargoListContract.View mView;
    public CargoAdapter(Context context, CargoListContract.View view, @Nullable List<Cargo> data) {
        super(context, data, R.layout.item_cargo);
        mView = view;
    }
    @Override
    protected void convert(AdapterHelper helper, final Cargo item) {
        if (!CommonUtil.isNullOrEmpty(item.getGoodsOwnerInfo_HeadPortraitId())) {
            ImageUtil.loadAvatar(context, helper.<ImageView>getView(R.id.item_avatar), item.getGoodsOwnerInfo_HeadPortraitId(), R.drawable.default_avatar);
        } else {
            helper.<ImageView>getView(R.id.item_avatar).setImageResource(R.drawable.default_avatar);
        }
        helper.setText(R.id.item_name, item.getGoodsOwnerInfo_Name());
        helper.setText(R.id.item_record, item.getRegisterTime() + " / " + item.getSourceCount());
        helper.setText(R.id.item_time, item.getReleasedTimeName());

        helper.setText(R.id.item_location_start, item.getLoadPlace());
        helper.setText(R.id.item_location_end, item.getUnloadPlace());

        helper.getView(R.id.item_detail).setVisibility(CommonUtil.isNullOrEmpty(item.getGoodsShortInfo()) ? View.GONE : View.VISIBLE);
        ((HorizontalListView) helper.getView(R.id.item_detail)).setAdapter(new CommonAdapter(context, CommonUtil.getStringListMulti(item.getGoodsShortInfo())));
        switch (item.getGoodsOwnerInfo_Status()) {
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

            case 4: {

                ((TextView) helper.getView(R.id.item_name)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_4, 0);
            }
            break;
        }

        helper.getView(R.id.item_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mView.doCallItem(item.getGoodsOwnerInfo_Mobile());
            }
        });

        helper.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mView.doClickCargoDetail(item.getId());
            }
        });

        helper.getItemView().setTag(item);
    }
}
