package com.xmw.qiyun.ui.adapter;

import android.content.Context;
import android.view.View;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.net.cargo.CargoSearch;
import com.xmw.qiyun.data.model.net.cargo.CargoSearchBody;
import com.xmw.qiyun.data.model.net.standard.CityBean;
import com.xmw.qiyun.data.model.net.standard.CountyBean;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.cargo.cargoList.CargoListContract;
import com.xmw.qiyun.util.manage.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class CargoUnloadTopAdapter extends Adapter<CargoSearch> {

    private CargoListContract.View mView;
    private CargoSearchBody mCargoSearchBody;

    private List<Standard> provinceList = new ArrayList<>();
    private List<Standard> cityList = new ArrayList<>();
    private List<Standard> countyList = new ArrayList<>();

    public CargoUnloadTopAdapter(Context context, CargoListContract.View view, CargoSearchBody cargoSearchBody) {

        super(context, cargoSearchBody.getUnloadSearch(), R.layout.item_location_select);

        mView = view;
        mCargoSearchBody = cargoSearchBody;

        provinceList.addAll(GsonImpl.init().toObject(StandardManager.getProvince(), ProvinceBean.class).getData());
        cityList.addAll(GsonImpl.init().toObject(StandardManager.getCity(), CityBean.class).getData());
        countyList.addAll(GsonImpl.init().toObject(StandardManager.getCounty(), CountyBean.class).getData());
    }

    @Override
    protected void convert(final AdapterHelper helper, final CargoSearch item) {

        helper.getItemView().setTag(item);

        switch (item.getRegionType()){

            case 1:{

                if(CommonUtil.isNullOrEmpty(item.getRegionId())){

                    helper.setText(R.id.item_title, "全国");
                }

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

            case 3:{

                for(Standard standard : countyList){

                    if(item.getRegionId().equals(standard.getId())){

                        helper.setText(R.id.item_title, standard.getValue());

                        break;
                    }
                }
            }
        }

        helper.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mCargoSearchBody.getUnloadSearch().size() == 1 && CommonUtil.isNullOrEmpty(mCargoSearchBody.getUnloadSearch().get(0).getRegionId())){
                    return;
                }

                mCargoSearchBody.getUnloadSearch().remove(item);

                if(mCargoSearchBody.getUnloadSearch().size() == 0){

                    CargoSearch cargoSearch = new CargoSearch();
                    cargoSearch.setRegionType(1);
                    cargoSearch.setRegionId("");

                    mCargoSearchBody.getUnloadSearch().add(cargoSearch);
                }

                mView.refreshTop(mCargoSearchBody);
                mView.refreshBottom(mCargoSearchBody);
            }
        });
    }
}
