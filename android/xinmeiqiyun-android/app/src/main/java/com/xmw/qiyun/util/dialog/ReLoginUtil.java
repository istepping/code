package com.xmw.qiyun.util.dialog;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.xmw.qiyun.App;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.manager.UserManager;
import com.xmw.qiyun.data.model.event.ReLoginUpdateEvent;
import com.xmw.qiyun.data.model.event.RefreshEvent;
import com.xmw.qiyun.data.model.net.user.LoginAndRegisterBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.util.manage.AppUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

import org.greenrobot.eventbus.EventBus;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by dell on 2017/11/13.
 */

public class ReLoginUtil extends MaterialDialog {

    private volatile static ReLoginUtil instance;

    private ReLoginUtil(Context context) {
        super(context);
    }

    public static ReLoginUtil init(Context context) {

        if (instance == null) {

            synchronized (ReLoginUtil.class) {

                if (instance == null) {

                    instance = new ReLoginUtil(context);
                }
            }
        }
        return instance;
    }

    public void initLoginDialog(final Context context) {

        instance.setTitle(context.getString(R.string.dialog_login_title))
                .setMessage(context.getString(R.string.dialog_login_message))
                .setPositiveButton(context.getString(R.string.dialog_login_positive), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dismiss();

                        UserManager.logout();

                        API.getService().reLogin(UserManager.getPhone()).subscribe(new MySubscriber<LoginAndRegisterBean>() {
                            @Override
                            public void onNext(LoginAndRegisterBean loginAndRegisterBean) {

                                if (loginAndRegisterBean.getStatusCode() == 1) {

                                    UserManager.login(loginAndRegisterBean.getData());

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            EventBus.getDefault().post(new RefreshEvent());
                                            EventBus.getDefault().post(new ReLoginUpdateEvent());

                                        }
                                    }, 1000);

                                } else {

                                    AppUtil.INSTANCE.finishAllActivity();

                                    RouterUtil.go(RouterUtil.ROUTER_LOGIN, App.getInstance());
                                }
                            }
                        });
                    }
                })
                .setNegativeButton(context.getString(R.string.dialog_login_negative), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dismiss();

                        UserManager.logout();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                AppUtil.INSTANCE.finishAllActivity();

                                RouterUtil.go(RouterUtil.ROUTER_LOGIN, App.getInstance());

                            }
                        }, 1000);
                    }
                })
                .show();
    }
}
