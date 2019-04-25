package com.xmw.qiyun.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.xmw.qiyun.data.model.event.PositionEvent;
import com.xmw.qiyun.data.manager.PositionManager;
import com.xmw.qiyun.data.model.local.PositionItem;
import com.xmw.qiyun.net.json.GsonImpl;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hongyuan on 2017/8/10.
 */

public class PositionService extends Service {

    public AMapLocationClient mPositionClient = null;
    public AMapLocationClientOption mPositionClientOption = new AMapLocationClientOption();

    public PositionService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        initPosition();
        startPosition();

        return super.onStartCommand(intent, flags, startId);
    }

    public void initPosition() {

        mPositionClientOption.setOnceLocationLatest(true);

        mPositionClient = new AMapLocationClient(getApplicationContext());
        mPositionClient.setLocationOption(mPositionClientOption);
        mPositionClient.setLocationListener(mPositionListener);
    }

    public void startPosition() {
        mPositionClient.startLocation();
    }

    public void stopPosition() {

        mPositionClient.stopLocation();
        stopSelf();
    }

    public AMapLocationListener mPositionListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {

            if (aMapLocation != null) {

                if (aMapLocation.getErrorCode() == 0) {

                    PositionManager.setPositionDetail(GsonImpl.init().toJson(new PositionItem(
                            aMapLocation.getLatitude(),
                            aMapLocation.getLongitude(),
                            aMapLocation.getAddress(),
                            aMapLocation.getProvince(),
                            aMapLocation.getCity(),
                            aMapLocation.getDistrict())));

                    EventBus.getDefault().post(new PositionEvent(true));

                    stopPosition();

                    return;
                }
            }

            EventBus.getDefault().post(new PositionEvent(false));

            stopPosition();
        }
    };
}
