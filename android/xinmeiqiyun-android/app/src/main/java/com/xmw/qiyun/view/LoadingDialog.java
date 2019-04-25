package com.xmw.qiyun.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.xmw.qiyun.R;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class LoadingDialog extends Dialog {

    public static LoadingDialog show(Context context) {

        return LoadingDialog.show(context, false, null);
    }

    private LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private static LoadingDialog show(Context context, boolean cancelable, OnCancelListener cancelListener) {

        LoadingDialog dialog = new LoadingDialog(context, R.style.LoadingDialog);
        dialog.setContentView(R.layout.loading_layout);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(0);
        dialog.show();
        return dialog;
    }
}
