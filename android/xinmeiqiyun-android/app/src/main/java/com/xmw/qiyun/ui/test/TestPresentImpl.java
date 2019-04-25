package com.xmw.qiyun.ui.test;

import android.content.Context;
import android.util.Log;

import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;

class TestPresentImpl implements TestContract.Presenter {

    private Context mContext;

    TestPresentImpl(Context context){
        mContext = context;
    }

    @Override
    public void bindView(TestContract.View view) {

    }

    @Override
    public void getProvinceList() {

        //调用接口，api工具已封装在net.api文件夹
        API.getService().getProvince().subscribe(new MySubscriber<ProvinceBean>() {
            @Override
            public void onNext(ProvinceBean provinceBean) {

                //打印结果，json工具已封装在net.json文件夹
                Log.d("result", GsonImpl.init().toJson(provinceBean));
            }
        });
    }
}
