package com.xmw.qiyun.ui.adapter;

import android.content.Context;
import android.view.View;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.local.LocationItem;
import com.xmw.qiyun.data.model.net.standard.CityBean;
import com.xmw.qiyun.data.model.net.standard.Standard;
import com.xmw.qiyun.data.model.net.truck.TruckSearch;
import com.xmw.qiyun.data.model.net.truck.TruckSearchBody;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.truck.TruckContract;
import com.xmw.qiyun.util.manage.NoteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/8/30.
 */

public class TruckCityAdapter extends Adapter<LocationItem> {

    private Context mContext;
    private TruckContract.View mView;
    private TruckContract.Presenter mPresenter;
    private int mDataType;
    private TruckSearchBody mTruckSearchBody;

    private List<Standard> cityList = new ArrayList<>();

    public TruckCityAdapter(Context context, TruckContract.View view, TruckContract.Presenter presenter, int dataType, TruckSearchBody truckSearchBody, List<LocationItem> data) {

        super(context, data, R.layout.item_location);

        mContext = context;
        mView = view;
        mPresenter = presenter;
        mDataType = dataType;
        mTruckSearchBody = truckSearchBody;

        cityList.addAll(GsonImpl.init().toObject(StandardManager.getCity(), CityBean.class).getData());
    }

    @Override
    protected void convert(final AdapterHelper helper, final LocationItem item) {

        helper.getItemView().setTag(item);
        helper.setText(R.id.item_title, mDataType == 2 && helper.getPosition() == 0 ? new StringBuilder().append("全").append(item.getStandard().getValue()) : item.getStandard().getValue());
        helper.setTextColor(R.id.item_title, item.isHasSelected() ? mContext.getResources().getColor(R.color.blue) : mContext.getResources().getColor(R.color.text3));
        helper.setBackgroundColor(R.id.item_wrap, item.isHasSelected() ? mContext.getResources().getColor(R.color.blue) : mContext.getResources().getColor(R.color.divider));

        helper.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (mDataType) {

                    default:
                        break;

                    //处于省列表中
                    case 1: {

                        mPresenter.getLocationData(item.getStandard(), 2, false, mTruckSearchBody);
                    }
                    break;

                    //处于市列表中
                    case 2: {

                        if(item.isHasSelected()){

                            for(TruckSearch truckSearch : mTruckSearchBody.getUnloadSearch()){

                                if(truckSearch.getRegionId().equals(item.getStandard().getId())){

                                    mTruckSearchBody.getUnloadSearch().remove(truckSearch);

                                    break;
                                }
                            }

                        }else{

                            if(mTruckSearchBody.getUnloadSearch().size() == 5){

                                showAlert();

                                return;
                            }

                            if(helper.getPosition() == 0){

                                List<String> cityIds = new ArrayList<>();

                                //循环市列表
                                for(Standard standard : cityList){

                                    if(standard.getParentId().equals(item.getStandard().getId())){

                                        cityIds.add(standard.getId());
                                    }
                                }

                                List<TruckSearch> truckSearchList = new ArrayList<>();
                                truckSearchList.addAll(mTruckSearchBody.getUnloadSearch());

                                for(TruckSearch truckSearchItem : truckSearchList){

                                    if(cityIds.contains(truckSearchItem.getRegionId())){

                                        mTruckSearchBody.getUnloadSearch().remove(truckSearchItem);
                                    }
                                }

                                TruckSearch truckSearch = new TruckSearch();
                                truckSearch.setRegionId(item.getStandard().getId());
                                truckSearch.setRegionType(mDataType - 1);

                                mTruckSearchBody.getUnloadSearch().add(truckSearch);

                                mPresenter.getLocationData(item.getStandard(), 2, false, mTruckSearchBody);

                            }else{

                                TruckSearch truckSearch = new TruckSearch();
                                truckSearch.setRegionId(item.getStandard().getId());
                                truckSearch.setRegionType(mDataType);

                                mTruckSearchBody.getUnloadSearch().add(truckSearch);
                            }
                        }

                        mView.refreshTop(mTruckSearchBody);

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
