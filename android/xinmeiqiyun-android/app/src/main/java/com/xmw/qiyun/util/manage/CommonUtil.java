package com.xmw.qiyun.util.manage;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.xmw.qiyun.App;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mac on 2017/7/26.
 */

public class CommonUtil {

    //非空验证
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    //获取时间戳
    public static long getTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    //验证手机号码
    public static boolean checkPhoneNumber(String phoneNumber) {

        Pattern pattern = Pattern.compile("^1[0-9]{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

    //验证输入非法
    public static boolean checkInputInvalid(String input) {

        return (input.contains(".") & input.length() == 1)
                | (input.contains(".") & input.indexOf(".") == 0
                | (input.contains(".") & input.indexOf(".") == input.length() - 1));
    }

    //隐藏软键盘
    public static void hideKeyboard(View view, Context context) {

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        view.clearFocus();
    }

    //字符转字符数组
    public static List<String> getStringList(String string) {

        List<String> lengthList = new ArrayList<>();

        if (!CommonUtil.isNullOrEmpty(string)) {

            if (string.contains(",")) {

                String[] args = string.split(",");

                for (String arg : args) {

                    lengthList.add(arg);
                }

            } else {

                lengthList.add(string);
            }
        }

        return lengthList;
    }

    //字符转字符数组
    public static List<String> getStringListMulti(String string) {

        List<String> lengthList = new ArrayList<>();

        if (!CommonUtil.isNullOrEmpty(string)) {

            if (string.contains("/")) {

                String[] args1 = string.split("/");

                for (String arg1 : args1) {

                    if (arg1.contains(",")) {

                        String[] args2 = arg1.split(",");

                        for (String arg2 : args2) {

                            lengthList.add(arg2);
                        }

                    } else {

                        lengthList.add(arg1);
                    }
                }

            } else {

                lengthList.add(string);
            }
        }

        return lengthList;
    }

    //字符数组转字符
    public static String getString(List<String> stringList) {

        if (stringList.size() == 0) {

            return "";

        } else if (stringList.size() == 1) {

            return stringList.get(0);

        } else {

            String string = "";

            for (int i = 0; i < stringList.size(); i++) {

                string = string + (i == 0 ? stringList.get(i) : "," + stringList.get(i));
            }

            return string;
        }
    }

    public static int getPxFromDp(Context context, int dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (dp * metrics.density);
    }

    public static int getPxFromDp(int dp) {
        DisplayMetrics metrics = App.getInstance().getResources().getDisplayMetrics();
        return (int) (dp * metrics.density);
    }
}
