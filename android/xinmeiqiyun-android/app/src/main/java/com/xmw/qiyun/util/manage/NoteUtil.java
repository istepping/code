package com.xmw.qiyun.util.manage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.xmw.qiyun.view.LoadingDialog;

/**
 * Created by hongyuan on 2017/8/11.
 */

public class NoteUtil {

    private static ProgressDialog progressDialog;
    private static LoadingDialog mLoadingDialog;

    public static void showCommonLoading(Context context, String msg) {

        progressDialog = ProgressDialog.show(context, "", msg);
    }

    public static void dismissCommonLoading() {

        if (progressDialog != null) {

            progressDialog.dismiss();
        }
    }

    public static void showLoading(Context context) {

        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            return;
        }

        if (mLoadingDialog != null) {

            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }

        try {

            mLoadingDialog = LoadingDialog.show(context);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void hideLoading() {

        if (mLoadingDialog != null) {

            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    public static void showToast(Context context, String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
