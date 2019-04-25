package com.xmw.qiyun.ui.cargo;

import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.R;
import com.xmw.qiyun.ui.map.MapActivity;
import com.xmw.qiyun.util.manage.RouterUtil;

/**
 * Created by dell on 2017/11/1.
 */

class CargoPresentImpl implements CargoContract.Presenter {

    private Context mContext;

    CargoPresentImpl(Context context) {
        mContext = context;
    }

    @Override
    public void bindView(CargoContract.View view) {

    }

    @Override
    public void goMap() {

        Bundle bundle = new Bundle();
        bundle.putInt(MapActivity.EXTRA_MAP_TYPE, MapActivity.EXTRA_MAP_CARGO);
        bundle.putString(MapActivity.EXTRA_MAP_TITLE, mContext.getString(R.string.cargo_owner_title));

        RouterUtil.go(RouterUtil.ROUTER_MAP, mContext, bundle);
    }
}