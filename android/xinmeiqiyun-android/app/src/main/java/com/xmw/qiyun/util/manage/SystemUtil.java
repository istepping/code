package com.xmw.qiyun.util.manage;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.xmw.qiyun.App;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by dell on 2017/10/30.
 */

public class SystemUtil {

    //获取网关
    public static String getIpAddress() {

        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces(); enNetI.hasMoreElements(); ) {

                NetworkInterface netI = enNetI.nextElement();

                for (Enumeration<InetAddress> enumIpAddress = netI.getInetAddresses(); enumIpAddress.hasMoreElements(); ) {

                    InetAddress inetAddress = enumIpAddress.nextElement();

                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {

                        return inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {

            e.printStackTrace();
        }

        return "";
    }

    //获取设备号
    public static String getDeviceId() {

        return ((TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    //获取包名
    public static String getPackageName(Context context) {

        return context.getPackageName();
    }

    //获取版本名
    public static String getVersionName(Context context) {

        try {

            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

            return info.versionName;

        } catch (Exception e) {

            e.printStackTrace();

            return "0";
        }
    }

    //获取版本号
    public static int getVersionCode(Context context) {

        try {

            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

            return info.versionCode;

        } catch (Exception e) {

            e.printStackTrace();

            return 0;
        }
    }

    //获取SDK版本
    public static int getSDKVersion() {

        return Build.VERSION.SDK_INT;
    }

    //获取安装版本
    public static String getReleaseVersion() {

        return Build.VERSION.RELEASE;
    }
}
