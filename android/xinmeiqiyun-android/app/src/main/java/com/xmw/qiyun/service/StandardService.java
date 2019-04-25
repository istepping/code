package com.xmw.qiyun.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.xmw.qiyun.data.manager.StandardManager;
import com.xmw.qiyun.data.model.event.StandardEvent;
import com.xmw.qiyun.data.model.net.standard.AssemblyBean;
import com.xmw.qiyun.data.model.net.standard.CityBean;
import com.xmw.qiyun.data.model.net.standard.CountyBean;
import com.xmw.qiyun.data.model.net.standard.GoodsTypeBean;
import com.xmw.qiyun.data.model.net.standard.GoodsUnitBean;
import com.xmw.qiyun.data.model.net.standard.PaymentMethodBean;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.data.model.net.standard.RemarkBean;
import com.xmw.qiyun.data.model.net.standard.VehicleLengthBean;
import com.xmw.qiyun.data.model.net.standard.VehicleTypeBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class StandardService extends Service {

    public StandardService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        API.getService().getVehicleLength().subscribe(new MySubscriber<VehicleLengthBean>() {
            @Override
            public void onNext(VehicleLengthBean vehicleLengthBean) {

                StandardManager.saveVehicleLength(GsonImpl.init().toJson(vehicleLengthBean));

                API.getService().getVehicleType().subscribe(new MySubscriber<VehicleTypeBean>() {
                    @Override
                    public void onNext(VehicleTypeBean vehicleTypeBean) {

                        StandardManager.saveVehicleType(GsonImpl.init().toJson(vehicleTypeBean));

                        API.getService().getGoodsUnit().subscribe(new MySubscriber<GoodsUnitBean>() {
                            @Override
                            public void onNext(GoodsUnitBean goodsUnitBean) {

                                StandardManager.saveGoodsUnit(GsonImpl.init().toJson(goodsUnitBean));

                                API.getService().getGoodsType().subscribe(new MySubscriber<GoodsTypeBean>() {
                                    @Override
                                    public void onNext(GoodsTypeBean goodsTypeBean) {

                                        StandardManager.saveGoodsType(GsonImpl.init().toJson(goodsTypeBean));

                                        API.getService().getAssembly().subscribe(new MySubscriber<AssemblyBean>() {
                                            @Override
                                            public void onNext(AssemblyBean assemblyBean) {

                                                StandardManager.saveAssembly(GsonImpl.init().toJson(assemblyBean));

                                                API.getService().getPaymentMethod().subscribe(new MySubscriber<PaymentMethodBean>() {
                                                    @Override
                                                    public void onNext(PaymentMethodBean paymentMethodBean) {

                                                        StandardManager.savePayment(GsonImpl.init().toJson(paymentMethodBean));

                                                        API.getService().getRemark().subscribe(new MySubscriber<RemarkBean>() {
                                                            @Override
                                                            public void onNext(RemarkBean remarkBean) {

                                                                StandardManager.saveRemark(GsonImpl.init().toJson(remarkBean));

                                                                API.getService().getProvince().subscribe(new MySubscriber<ProvinceBean>() {
                                                                    @Override
                                                                    public void onNext(ProvinceBean provinceBean) {

                                                                        StandardManager.saveProvince(GsonImpl.init().toJson(provinceBean));

                                                                        API.getService().getCity().subscribe(new MySubscriber<CityBean>() {
                                                                            @Override
                                                                            public void onNext(CityBean cityBean) {

                                                                                StandardManager.saveCity(GsonImpl.init().toJson(cityBean));

                                                                                API.getService().getCounty().subscribe(new MySubscriber<CountyBean>() {
                                                                                    @Override
                                                                                    public void onNext(CountyBean countyBean) {

                                                                                        StandardManager.saveCounty(GsonImpl.init().toJson(countyBean));
                                                                                        StandardManager.saveStatus(true);

                                                                                        StandardEvent standardEvent = new StandardEvent();

                                                                                        EventBus.getDefault().post(standardEvent);

                                                                                        stopSelf();
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }
}
