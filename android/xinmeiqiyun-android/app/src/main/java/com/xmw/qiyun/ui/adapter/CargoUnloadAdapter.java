package com.xmw.qiyun.ui.adapter;

import android.content.Context;
import android.view.View;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.net.cargo.CargoSearch;
import com.xmw.qiyun.data.model.net.cargo.CargoSearchBody;
import com.xmw.qiyun.data.model.net.standard.CityBean;
import com.xmw.qiyun.data.model.net.standard.CountyBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.cargo.cargoList.CargoListContract;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.manage.NoteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class CargoUnloadAdapter extends Adapter<LocationItem> {

    private Context mContext;
    private CargoListContract.View mView;
    private CargoListContract.Presenter mPresenter;
    private CargoSearchBody mCargoSearchBody;
    private int mDataType;

    private List<Standard> cityList = new ArrayList<>();
    private List<Standard> countyList = new ArrayList<>();

    public CargoUnloadAdapter(Context context, CargoListContract.View view, CargoListContract.Presenter presenter, CargoSearchBody cargoSearchBody, int dataType, List<LocationItem> data) {

        super(context, data, R.layout.item_location);

        mContext = context;
        mView = view;
        mPresenter = presenter;
        mCargoSearchBody = cargoSearchBody;
        mDataType = dataType;

        cityList.addAll(GsonImpl.init().toObject(StandardManager.getCity(), CityBean.class).getData());
        countyList.addAll(GsonImpl.init().toObject(StandardManager.getCounty(), CountyBean.class).getData());
    }

    @Override
    protected void convert(final AdapterHelper helper, final LocationItem item) {

        helper.getItemView().setTag(item);
        helper.setText(R.id.item_title, helper.getPosition() == 0 && mDataType != 1 ? "全" + item.getStandard().getValue() : item.getStandard().getValue());
        helper.setTextColor(R.id.item_title, item.isHasSelected() ? mContext.getResources().getColor(R.color.blue) : mContext.getResources().getColor(R.color.text3));
        helper.setBackgroundColor(R.id.item_wrap, item.isHasSelected() ? mContext.getResources().getColor(R.color.blue) : mContext.getResources().getColor(R.color.divider));

        helper.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (mDataType){

                    default:
                        break;

                    //处于省列表中
                    case 1:{

                        if(helper.getPosition() == 0){

                            CargoSearch cargoSearch = new CargoSearch();
                            cargoSearch.setRegionType(mDataType);
                            cargoSearch.setRegionId("");

                            mCargoSearchBody.getUnloadSearch().clear();
                            mCargoSearchBody.getUnloadSearch().add(cargoSearch);

                            mView.refreshTop(mCargoSearchBody);

                            mPresenter.getLocationData(item.getStandard(), 1, false, false, mCargoSearchBody);

                        }else{

                            mPresenter.getLocationData(item.getStandard(), 2, false, false, mCargoSearchBody);
                        }
                    }
                    break;

                    //处于市列表中
                    case 2:{

                        if(helper.getPosition() == 0){

                            if(item.isHasSelected()){

                                for(CargoSearch cargoSearch : mCargoSearchBody.getUnloadSearch()){

                                    if(cargoSearch.getRegionId().equals(item.getStandard().getId())){

                                        mCargoSearchBody.getUnloadSearch().remove(cargoSearch);

                                        break;
                                    }
                                }

                                if(mCargoSearchBody.getUnloadSearch().size() == 0){

                                    CargoSearch cargoSearch = new CargoSearch();
                                    cargoSearch.setRegionType(1);
                                    cargoSearch.setRegionId("");

                                    mCargoSearchBody.getUnloadSearch().clear();
                                    mCargoSearchBody.getUnloadSearch().add(cargoSearch);
                                }

                            }else{

                                if(mCargoSearchBody.getUnloadSearch().size() == 1 && CommonUtil.isNullOrEmpty(mCargoSearchBody.getUnloadSearch().get(0).getRegionId())){

                                    mCargoSearchBody.getUnloadSearch().clear();
                                }

                                if(mCargoSearchBody.getUnloadSearch().size() == 5){

                                    showAlert();

                                    return;
                                }

                                List<String> cityIds = new ArrayList<>();
                                List<String> countyIds = new ArrayList<>();
                                List<String> totalIds = new ArrayList<>();

                                //循环市列表
                                for(Standard standard : cityList){

                                    if(standard.getParentId().equals(item.getStandard().getId())){

                                        cityIds.add(standard.getId());
                                    }
                                }

                                //循环区列表
                                for(Standard standard : countyList){

                                    if(cityIds.contains(standard.getParentId())){

                                        countyIds.add(standard.getId());
                                    }
                                }

                                totalIds.addAll(cityIds);
                                totalIds.addAll(countyIds);

                                List<CargoSearch> cargoSearchList = new ArrayList<>();
                                cargoSearchList.addAll(mCargoSearchBody.getUnloadSearch());

                                for(CargoSearch cargoSearchItem : cargoSearchList){

                                    if(totalIds.contains(cargoSearchItem.getRegionId())){

                                        mCargoSearchBody.getUnloadSearch().remove(cargoSearchItem);
                                    }
                                }

                                CargoSearch cargoSearch = new CargoSearch();
                                cargoSearch.setRegionType(mDataType - 1);
                                cargoSearch.setRegionId(item.getStandard().getId());

                                mCargoSearchBody.getUnloadSearch().add(cargoSearch);
                            }

                            mView.refreshTop(mCargoSearchBody);

                            mPresenter.getLocationData(item.getStandard(), 2, false, false, mCargoSearchBody);

                        }else{

                            mPresenter.getLocationData(item.getStandard(), 3, false, false, mCargoSearchBody);
                        }
                    }
                    break;

                    //处于区列表中
                    case 3:{

                        if(item.isHasSelected()){

                            for(CargoSearch cargoSearch : mCargoSearchBody.getUnloadSearch()){

                                if(cargoSearch.getRegionId().equals(item.getStandard().getId())){

                                    mCargoSearchBody.getUnloadSearch().remove(cargoSearch);

                                    break;
                                }
                            }

                            if(mCargoSearchBody.getUnloadSearch().size() == 0){

                                CargoSearch cargoSearch = new CargoSearch();
                                cargoSearch.setRegionType(1);
                                cargoSearch.setRegionId("");

                                mCargoSearchBody.getUnloadSearch().clear();
                                mCargoSearchBody.getUnloadSearch().add(cargoSearch);
                            }

                        }else {

                            if(mCargoSearchBody.getUnloadSearch().size() == 1 && CommonUtil.isNullOrEmpty(mCargoSearchBody.getUnloadSearch().get(0).getRegionId())){

                                mCargoSearchBody.getUnloadSearch().clear();
                            }

                            if (mCargoSearchBody.getUnloadSearch().size() == 5) {

                                showAlert();

                                return;
                            }

                            if (helper.getPosition() == 0) {

                                List<String> countyIds = new ArrayList<>();

                                //循环区列表
                                for(Standard standard : countyList){

                                    if(standard.getParentId().equals(item.getStandard().getId())){

                                        countyIds.add(standard.getId());
                                    }
                                }

                                List<CargoSearch> cargoSearchList = new ArrayList<>();
                                cargoSearchList.addAll(mCargoSearchBody.getUnloadSearch());

                                for(CargoSearch cargoSearchItem : cargoSearchList){

                                    if(countyIds.contains(cargoSearchItem.getRegionId())){

                                        mCargoSearchBody.getUnloadSearch().remove(cargoSearchItem);
                                    }
                                }

                                CargoSearch cargoSearch = new CargoSearch();
                                cargoSearch.setRegionType(mDataType - 1);
                                cargoSearch.setRegionId(item.getStandard().getId());

                                mCargoSearchBody.getUnloadSearch().add(cargoSearch);

                                mPresenter.getLocationData(item.getStandard(), 3, false, false, mCargoSearchBody);

                            } else {

                                CargoSearch cargoSearch = new CargoSearch();
                                cargoSearch.setRegionType(mDataType);
                                cargoSearch.setRegionId(item.getStandard().getId());

                                mCargoSearchBody.getUnloadSearch().add(cargoSearch);
                            }
                        }

                        mView.refreshTop(mCargoSearchBody);

                        changeColor(helper, item);
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
