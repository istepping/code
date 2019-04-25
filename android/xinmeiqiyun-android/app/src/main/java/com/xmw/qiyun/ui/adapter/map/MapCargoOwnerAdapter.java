package com.xmw.qiyun.ui.adapter.map;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.net.cargo.CargoOwner;
import com.xmw.qiyun.ui.map.MapContract;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.image.ImageUtil;

/**
 * Created by dell on 2017/11/2.
 */

public class MapCargoOwnerAdapter implements AMap.InfoWindowAdapter, View.OnClickListener {

    private Context mContext;
    private MapContract.Presenter mPresenter;
    private Marker mMarker;

    private String mMobile;

    public MapCargoOwnerAdapter(Context context, MapContract.Presenter presenter) {

        mContext = context;
        mPresenter = presenter;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        mMarker = marker;

        return initView();
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }

    @NonNull
    private View initView() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_map_cargo_owner, null);

        CargoOwner cargoOwner = (CargoOwner) mMarker.getObject();

        mMobile = cargoOwner.getMobile();

        if (!CommonUtil.isNullOrEmpty(cargoOwner.getHeadPortraitId())) {

            ImageUtil.loadAvatar(mContext, ((ImageView) view.findViewById(R.id.item_avatar)), cargoOwner.getHeadPortraitId(), R.drawable.default_avatar);
        }

        ((TextView) view.findViewById(R.id.item_name)).setText(cargoOwner.getName());

        switch (cargoOwner.getStatus()) {

            default:
                break;

            case 1: {

                ((TextView) view.findViewById(R.id.item_name)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_1, 0);
            }
            break;

            case 2: {

                ((TextView) view.findViewById(R.id.item_name)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_2, 0);
            }
            break;

            case 4: {

                ((TextView) view.findViewById(R.id.item_name)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verify_type_4, 0);
            }
            break;
        }

        ((TextView) view.findViewById(R.id.item_company)).setText(cargoOwner.getCompanyShortName());

        view.findViewById(R.id.item_close).setOnClickListener(this);
        view.findViewById(R.id.item_contact).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {

            case R.id.item_contact: {

                mPresenter.goContact(mMobile);
            }
            break;

            case R.id.item_close: {

                mMarker.hideInfoWindow();
            }
            break;
        }
    }
}