package com.xmw.qiyun.net.api;

import com.xmw.qiyun.App;
import com.xmw.qiyun.data.manager.UserManager;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac on 2017/7/26.
 */

public class API {

    private static API api = new API();

    //public static final String URL = "http://dev.xinmeiqiyun.com:7003/";
    public static final String URL = "http://101.201.238.192:7003/";
    public static final String BASE_URL = URL + "api/";

    public static ApiService getService() {
        return api.service;
    }

    private ApiService service;

    //构造方法私有
    private API() {
        File cacheFile = new File(App.getInstance().getCacheDir(), "[response]");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(mTokenInterceptor)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .readTimeout(1, TimeUnit.HOURS)
                .writeTimeout(1, TimeUnit.HOURS)
                .connectTimeout(1, TimeUnit.HOURS)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(MyRxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(ApiService.class);
    }

    Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

            HttpUrl url = originalRequest.url().newBuilder()
                    .build();
            Request.Builder builder = originalRequest.newBuilder()
                    .url(url)
                    .header("Authorization", UserManager.getToken());

            if (originalRequest.body() != null &&
                    originalRequest.body().contentType() != null &&
                    originalRequest.body().contentType().subtype().equals("json")) {
                Buffer buffer = new Buffer();
                originalRequest.body().writeTo(buffer);
                if (buffer.size() < 20 * 1024) {
                    RequestBody body = RequestBody.create(originalRequest.body().contentType(), buffer.readUtf8()
                            .replace("\"created_at\":", "\"created_at1\":")
                            .replace("\"date_joined\":", "\"date_joined1\":")
                            .replace("\"api_key\":", "\"api_key1\":")
                            .replace("\"updated_at\":", "\"updated_at1\":"));

                    builder.method(originalRequest.method(), body);
                }
            }
            return chain.proceed(builder.build());
        }
    };

    private Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            String cacheControl = chain.request().cacheControl().toString();

            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        }
    };
}
