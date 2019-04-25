package com.xmw.qiyun.ui.adapter;

import android.content.Context;
import android.view.View;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.net.standard.CityBean;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.truck.TruckSearch;
import com.xmw.qiyun.data.model.net.truck.TruckSearchBody;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.truck.TruckContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/8/30.
 */

public class TruckCityTopAdapter extends Adapter<TruckSearch> {

    private TruckContract.View mView;
    private TruckSearchBody mTruckSearchBody;

    private List<Standard> provinceList = new ArrayList<>();
    private List<Standard> cityList = new ArrayList<>();

    public TruckCityTopAdapter(Context context, TruckContract.View view, TruckSearchBody truckSearchBody) {

        super(context, truckSearchBody.getUnloadSearch(), R.layout.item_location_select);

        mView = view;
        mTruckSearchBody = truckSearchBody;

        provinceList.addAll(GsonImpl.init().toObject(StandardManager.getProvince(), ProvinceBean.class).getData());
        cityList.addAll(GsonImpl.init().toObject(StandardManager.getCity(), CityBean.class).getData());
    }

    @Override
    protected void convert(final AdapterHelper helper, final TruckSearch item) {

        helper.getItemView().setTag(item);

        switch (item.getRegionType()){

            default:
                break;

            case 1:{

                for(Standard standard : provinceList){

                    if(item.getRegionId().equals(standard.getId())){

                        helper.setText(R.id.item_title, standard.getValue());

                        break;
                    }
                }
            }
            break;

            case 2:{

                for(Standard standard : cityList){

                    if(item.getRegionId().equals(standard.getId())){

                        helper.setText(R.id.item_title, standard.getValue());

                        break;
                    }
                }
            }
            break;
        }



        helper.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTruckSearchBody.getUnloadSearch().remove(item);

                mView.refreshTop(mTruckSearchBody);
                mView.refreshBottom(mTruckSearchBody);
            }
        });
    }
}
