package com.xmw.qiyun.ui.adapter.common;

import android.content.Context;
import android.view.View;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.local.StandardItem;
import com.xmw.qiyun.data.model.net.cargo.CargoSearchBody;
import com.xmw.qiyun.data.model.net.map.MapSearchBody;
import com.xmw.qiyun.data.model.net.publish.PublishBody;
import com.xmw.qiyun.data.model.net.truck.TruckSearchBody;
import com.xmw.qiyun.ui.cargo.cargoList.CargoListContract;
import com.xmw.qiyun.ui.map.MapContract;
import com.xmw.qiyun.ui.publish.PublishContract;
import com.xmw.qiyun.ui.publish.PublishFragment;
import com.xmw.qiyun.ui.truck.TruckContract;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.dialog.DialogUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.view.InputDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyuan on 2017/9/1.
 */

public class OptionAdapter extends Adapter<StandardItem> {

    private PublishContract.View mPublishView;
    private PublishContract.Presenter mPublishPresenter;
    private PublishBody mPublishBody;
    private TruckContract.Presenter mTruckPresenter;
    private TruckSearchBody mTruckSearchBody;
    private CargoListContract.Presenter mCargoPresenter;
    private CargoSearchBody mCargoSearchBody;
    private MapContract.View mMapView;
    private MapContract.Presenter mMapPresenter;
    private MapSearchBody mMapSearchBody;

    private int mPageType;
    private int mUnitType;

    private static final int EXTRA_FROM_PUBLISH = 0;
    private static final int EXTRA_FROM_TRUCK = 1;
    private static final int EXTRA_FROM_CARGO = 2;
    private static final int EXTRA_FROM_MAP = 3;

    private static final int EXTRA_GOODS = 0;
    private static final int EXTRA_FREIGHT = 1;

    public OptionAdapter(
            Context context,
            PublishContract.View view,
            PublishContract.Presenter presenter,
            PublishBody publishBody,
            List<StandardItem> data) {

        super(context, data, R.layout.item_vehicle);

        mPublishView = view;
        mPublishPresenter = presenter;
        mPublishBody = publishBody;

        mPageType = EXTRA_FROM_PUBLISH;
    }

    public OptionAdapter(
            Context context,
            PublishContract.View view,
            PublishContract.Presenter presenter,
            PublishBody publishBody,
            List<StandardItem> data,
            int unitType) {

        super(context, data, R.layout.item_vehicle);

        mPublishView = view;
        mPublishPresenter = presenter;
        mPublishBody = publishBody;

        mPageType = EXTRA_FROM_PUBLISH;
        mUnitType = unitType;
    }

    public OptionAdapter(
            Context context,
            TruckContract.Presenter presenter,
            TruckSearchBody truckSearchBody,
            List<StandardItem> data) {

        super(context, data, R.layout.item_vehicle);

        mTruckPresenter = presenter;
        mTruckSearchBody = truckSearchBody;

        mPageType = EXTRA_FROM_TRUCK;
    }

    public OptionAdapter(
            Context context,
            CargoListContract.Presenter presenter,
            CargoSearchBody cargoSearchBody,
            List<StandardItem> data) {

        super(context, data, R.layout.item_vehicle);

        mCargoPresenter = presenter;
        mCargoSearchBody = cargoSearchBody;

        mPageType = EXTRA_FROM_CARGO;
    }

    public OptionAdapter(
            Context context,
            MapContract.View mapView,
            MapContract.Presenter presenter,
            MapSearchBody mapSearchBody,
            List<StandardItem> data) {

        super(context, data, R.layout.item_vehicle);

        mMapView = mapView;
        mMapPresenter = presenter;
        mMapSearchBody = mapSearchBody;

        mPageType = EXTRA_FROM_MAP;
    }

    @Override
    protected void convert(final AdapterHelper helper, final StandardItem item) {

        helper.setText(R.id.item_title, item.getStandard().getValue());
        helper.setTextColor(R.id.item_title, item.isHasSelected() ? context.getResources().getColor(R.color.white) : context.getResources().getColor(R.color.text3));
        helper.setBackgroundColor(R.id.item_wrap, item.isHasSelected() ? context.getResources().getColor(R.color.blue) : context.getResources().getColor(R.color.divider));

        helper.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (item.getStandard().getOptionCode()) {

                    default:
                        break;

                    case "GoodsType": {

                        if (helper.getPosition() == data.size() - 1) {

                            DialogUtil.INSTANCE.initAddDialog(context, mPublishPresenter, mPublishBody, 1);

                            return;
                        }

                        mPublishBody.setGoodsType(item.getStandard().getValue());
                        mPublishView.updatePublishBody(mPublishBody);
                    }
                    break;

                    case "GoodsUnit": {

                        switch (mUnitType){

                            default:
                                break;

                            case EXTRA_GOODS:{

                                mPublishBody.setGoodsUnitId(item.getStandard().getId());
                                mPublishBody.setGoodsUnit_Value(item.getStandard().getValue());
                                mPublishView.updatePublishBody(mPublishBody);
                            }
                            break;

                            case EXTRA_FREIGHT:{

                                mPublishBody.setFreightUnitId(item.getStandard().getId());
                                mPublishBody.setFreightUnit_Value(item.getStandard().getValue());
                                mPublishView.updatePublishBody(mPublishBody);
                            }
                            break;
                        }
                    }
                    break;

                    case "Assembly": {

                        mPublishBody.setAssemblyWayId(item.getStandard().getId());
                        mPublishBody.setAssemblyWay_Value(item.getStandard().getValue());
                        mPublishView.updatePublishBody(mPublishBody);
                    }
                    break;

                    case "PaymentMethod": {

                        mPublishBody.setPaymentMethodId(item.getStandard().getId());
                        mPublishBody.setPaymentMethod_Value(item.getStandard().getValue());
                        mPublishView.updatePublishBody(mPublishBody);
                    }
                    break;

                    case "Remark": {

                        mPublishBody.setOtherRemarkId(item.getStandard().getId());
                        mPublishBody.setOtherRemark_Value(item.getStandard().getValue());
                        mPublishView.updatePublishBody(mPublishBody);
                    }
                    break;

                    case "VehicleLength": {

                        switch (mPageType){

                            default:
                                break;

                            case EXTRA_FROM_PUBLISH:{

                                List<String> lengthList = new ArrayList<>(CommonUtil.getStringList(mPublishBody.getVehicleLength()));

                                if (helper.getPosition() == data.size() - 1) {

                                    DialogUtil.INSTANCE.initAddDialog(context, mPublishPresenter, mPublishBody, 0);

                                    return;
                                }

                                if(item.isHasSelected()){

                                    lengthList.remove(item.getStandard().getValue());

                                }else{

                                    if(helper.getPosition() == 0){

                                        lengthList.clear();

                                    }else{

                                        if(lengthList.size() == 5){

                                            showAlert();

                                        }else{

                                            lengthList.add(item.getStandard().getValue());
                                        }
                                    }
                                }

                                mPublishBody.setVehicleLength(CommonUtil.getString(lengthList));

                                mPublishView.updateOptionMulti(mPublishBody);
                                mPublishPresenter.getOptionMultiData(mPublishBody, PublishFragment.EXTRA_OPTION_VEHICLE);
                            }
                            break;

                            case EXTRA_FROM_TRUCK:{

                                mTruckSearchBody.setVehicleLength(helper.getPosition() == 0 ? "" : item.getStandard().getValue());
                                mTruckPresenter.getVehicleData(mTruckSearchBody);
                            }
                            break;

                            case EXTRA_FROM_CARGO:{

                                if (helper.getPosition() == data.size() - 1) {

                                    String privateLength = StandardManager.getCargoVehicleLength();

                                    new InputDialog.Builder(context)
                                            .setTitle("车长")
                                            .setInputDefaultText(CommonUtil.isNullOrEmpty(privateLength) ? privateLength : privateLength.substring(0, privateLength.indexOf("米")))
                                            .setInputHint("请输入自定义车长")
                                            .setPositiveButton("确认", new InputDialog.ButtonActionListener() {
                                                @Override
                                                public void onClick(CharSequence inputText) {

                                                    if (inputText.length() != 0) {

                                                        String text;
                                                        String value;

                                                        if (CommonUtil.checkInputInvalid(inputText.toString())) {

                                                            NoteUtil.showToast(context, context.getString(R.string.toast_invalid));

                                                            return;
                                                        }

                                                        if(inputText.toString().contains(".")){

                                                            text = inputText.toString().substring(0, inputText.toString().indexOf("."));
                                                            value = inputText.toString().substring(0, inputText.toString().indexOf(".") + 2);

                                                        }else{

                                                            text = inputText.toString();
                                                            value = text;
                                                        }

                                                        if(Integer.parseInt(text) > 22) {

                                                            NoteUtil.showToast(context, "车长不能大于22米");

                                                        }else{

                                                            mCargoSearchBody.setVehicleLength(value + "米");
                                                            mCargoPresenter.getVehicleData(mCargoSearchBody);
                                                        }

                                                    } else if (inputText.length() == 0) {

                                                        StandardManager.saveCargoVehicleLength("");
                                                        mCargoSearchBody.setVehicleLength("");
                                                        mCargoPresenter.getVehicleData(mCargoSearchBody);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("取消", null)
                                            .show();

                                } else {

                                    mCargoSearchBody.setVehicleLength(helper.getPosition() == 0 ? "" : item.getStandard().getValue());
                                    mCargoPresenter.getVehicleData(mCargoSearchBody);
                                }
                            }
                            break;

                            case EXTRA_FROM_MAP:{

                                mMapSearchBody.setVehicleLength(helper.getPosition() == 0 ? "" : item.getStandard().getValue());
                                mMapView.updateBodyOfVehicle(mMapSearchBody);
                                mMapPresenter.getVehicleData(mMapSearchBody);
                            }
                            break;
                        }
                    }
                    break;

                    case "VehicleType": {

                        switch (mPageType){

                            default:
                                break;

                            case EXTRA_FROM_PUBLISH:{

                                mPublishBody.setVehicleTypeId(item.getStandard().getId());
                                mPublishBody.setVehicleType_Value(item.getStandard().getValue());

                                mPublishView.updateOptionMulti(mPublishBody);
                                mPublishPresenter.getOptionMultiData(mPublishBody, PublishFragment.EXTRA_OPTION_VEHICLE);
                            }
                            break;

                            case EXTRA_FROM_TRUCK:{

                                mTruckSearchBody.setVehicleTypeId(item.getStandard().getId());
                                mTruckPresenter.getVehicleData(mTruckSearchBody);
                            }
                            break;

                            case EXTRA_FROM_CARGO:{

                                mCargoSearchBody.setVehicleTypeId(item.getStandard().getId());
                                mCargoPresenter.getVehicleData(mCargoSearchBody);
                            }
                            break;

                            case EXTRA_FROM_MAP:{

                                mMapSearchBody.setVehicleType(item.getStandard().getId());
                                mMapView.updateBodyOfVehicle(mMapSearchBody);
                                mMapPresenter.getVehicleData(mMapSearchBody);
                            }
                            break;
                        }
                    }
                    break;

                    case "ResendCount": {

                        mPublishBody.setRepeat(true);
                        mPublishBody.setRepeatCount(Integer.valueOf(item.getStandard().getId()));

                        if(mPublishBody.getRepeatSpacingMinute() == 0){

                            mPublishBody.setRepeatSpacingMinute(20);
                        }

                        mPublishView.updateOptionMulti(mPublishBody);
                        mPublishPresenter.getOptionMultiData(mPublishBody, PublishFragment.EXTRA_OPTION_RESEND);
                    }
                    break;

                    case "ResendTime": {

                        mPublishBody.setRepeat(true);
                        mPublishBody.setRepeatSpacingMinute(Integer.valueOf(item.getStandard().getId()));

                        if(mPublishBody.getRepeatCount() == 0){

                            mPublishBody.setRepeatCount(5);
                        }

                        mPublishView.updateOptionMulti(mPublishBody);
                        mPublishPresenter.getOptionMultiData(mPublishBody, PublishFragment.EXTRA_OPTION_RESEND);
                    }
                    break;
                }
            }
        });
    }

    private void showAlert(){

        NoteUtil.showToast(context, context.getString(R.string.toast_up));
    }
}
