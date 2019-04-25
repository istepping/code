package com.xmw.qiyun.util.check;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.xmw.qiyun.App;

/**
 * Created by dell on 2017/11/13.
 */

public class NetworkUtil {

    private static NetworkInfo getActiveNetworkInfo() {
        return ((ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
