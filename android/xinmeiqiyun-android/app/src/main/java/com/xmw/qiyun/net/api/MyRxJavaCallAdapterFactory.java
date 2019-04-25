package com.xmw.qiyun.net.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mac on 2017/7/27.
 */

class MyRxJavaCallAdapterFactory extends CallAdapter.Factory {
    private RxJavaCallAdapterFactory mFactory;
    private MyRxJavaCallAdapterFactory() {
        mFactory = RxJavaCallAdapterFactory.create();
    }
    static MyRxJavaCallAdapterFactory create() {
        return new MyRxJavaCallAdapterFactory();
    }
    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        CallAdapter<?> a = mFactory.get(returnType, annotations, retrofit);
        if (a == null) {
            return null;
        } else {
            CallAdapter<Observable<?>> adapter = (CallAdapter<Observable<?>>) a;
            return new MyCallAdapter(adapter);
        }
    }

    private class MyCallAdapter implements CallAdapter<Observable<?>> {

        private CallAdapter<Observable<?>> mAdapter;

        MyCallAdapter(CallAdapter<Observable<?>> adapter) {
            this.mAdapter = adapter;
        }

        @Override
        public Type responseType() {
            return mAdapter.responseType();
        }

        @Override
        public <R> Observable<?> adapt(Call<R> call) {

            Observable<?> observable = mAdapter.adapt(call);

            return observable.lift(new Observable.Operator<Object, Object>() {
                @Override
                public Subscriber<? super Object> call(final Subscriber<? super Object> subscriber) {

                    return new Subscriber<Object>() {
                        @Override
                        public void onCompleted() {
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onError(Throwable e) {

                            subscriber.onError(e);
                        }

                        @Override
                        public void onNext(Object o) {

                            subscriber.onNext(o);
                        }
                    };
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    }
}