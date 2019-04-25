package com.xmw.qiyun.ui.adapter.cargo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.net.cargo.CargoOwner;
import com.xmw.qiyun.ui.cargo.cargoOwnerList.CargoOwnerListContract;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.image.ImageUtil;

import java.util.List;

/**
 * Created by dell on 2017/11/1.
 */

public class CargoOwnerAdapter extends Adapter<CargoOwner> {

    private CargoOwnerListContract.Presenter mPresenter;

    public CargoOwnerAdapter(Context context, CargoOwnerListContract.Presenter presenter, @Nullable List<CargoOwner> data) {

        super(context, data, R.layout.item_cargo_owner);

        mPresenter = presenter;
    }

    @Override
    protected void convert(AdapterHelper helper, final CargoOwner item) {

        helper.setText(R.id.item_company, item.getCompanyShortName());
        helper.setText(R.id.item_address, item.getAddress());

        if (!CommonUtil.isNullOrEmpty(item.getHeadPortraitId())) {
            ImageUtil.loadAvatar(context, helper.<ImageView>getView(R.id.item_avatar), item.getHeadPortraitId(), R.drawable.default_avatar);
        } else {
            helper.<ImageView>getView(R.id.item_avatar).setImageResource(R.drawable.default_avatar);
        }

        helper.setText(R.id.item_name, item.getName());

        switch (item.getStatus()) {

            default:
                break;

            case 1: {

                ((TextView)helper.getView(R.id.item_name)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_1, 0);
            }
            break;

            case 2: {

                ((TextView)helper.getView(R.id.item_name)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_2, 0);
            }
            break;

            case 4: {

                ((TextView)helper.getView(R.id.item_name)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_4, 0);
            }
            break;
        }

        helper.getView(R.id.item_locate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.goMap(item);
            }
        });

        helper.getView(R.id.item_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.goCallOwner(item.getMobile());
            }
        });

        helper.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.goCargoOwner(item.getId());
            }
        });

        helper.getItemView().setTag(item);
    }
}
