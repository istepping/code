package com.xmw.qiyun.ui.adapter.common;

import android.content.Context;
import android.view.View;
import android.widget.GridView;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.manager.UserManager;
import com.xmw.qiyun.net.json.GsonImpl;

import java.util.List;

/**
 * Created by hongyuan on 2017/9/6.
 */

public class OptionTopAdapter extends Adapter<String> {

    private int mType;
    private GridView mGridView;

    public OptionTopAdapter(Context context, int type, GridView gridView, List<String> data) {

        super(context, data, R.layout.item_location_select);

        mType = type;
        mGridView = gridView;
    }

    public List<String> getData(){

        return data;
    }

    @Override
    protected void convert(AdapterHelper helper, final String item) {

        helper.setText(R.id.item_title, item);

        helper.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data.remove(item);

                switch (mType){

                    default:
                        break;

                    case 0:{

                        UserManager.saveVehicle(GsonImpl.init().toJson(data));

                        mGridView.setAdapter(new OptionTopAdapter(context, mType, mGridView, data));
                    }
                    break;

                    case 1:{

                        UserManager.saveType(GsonImpl.init().toJson(data));

                        mGridView.setAdapter(new OptionTopAdapter(context, mType, mGridView, data));
                    }
                    break;
                }
            }
        });
    }
}
