package com.xmw.qiyun.ui.adapter.common;

import android.content.Context;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;

import java.util.List;

/**
 * Created by hongyuan on 2017/9/25.
 */

public class CommonAdapter extends Adapter<String> {

    public CommonAdapter(Context context, List<String> data) {

        super(context, data, R.layout.item_common);
    }

    @Override
    protected void convert(AdapterHelper helper, String item) {

        helper.setText(R.id.item_title, item);
    }
}
