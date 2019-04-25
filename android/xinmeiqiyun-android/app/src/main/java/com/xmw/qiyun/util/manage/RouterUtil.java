package com.xmw.qiyun.util.manage;

import android.content.Context;
import android.os.Bundle;

import com.chenenyu.router.Router;
import com.xmw.qiyun.R;

/**
 * Created by hongyuan on 2017/8/8.
 */

public class RouterUtil {
    //欢迎
    public static final String ROUTER_SPLASH = "splash";
    //广告
    public static final String ROUTER_WELCOME = "welcome";
    //登录
    public static final String ROUTER_LOGIN = "login";
    //注册
    public static final String ROUTER_REGISTER = "register";

    //单行文本
    public static final String ROUTER_INPUT_SINGLE = "inputSingle";
    //多行文本
    public static final String ROUTER_INPUT_MULTI = "inputMulti";

    //首页
    public static final String ROUTER_HOME = "home";

    //发货
    public static final String ROUTER_PUBLISH = "publish";
    //已发货-待审核
    public static final String ROUTER_PUBLISH_STREAM_BY = "publish/stream/by";
    //已发货-发布中
    public static final String ROUTER_PUBLISH_STREAM_IN = "publish/stream/in";
    //已发布-已关闭
    public static final String ROUTER_PUBLISH_STREAM_STOP = "publish/stream/stop";
    //货源详情
    public static final String ROUTER_PUBLISH_DETAIL = "publish/detail";
    //货源分享
    public static final String ROUTER_PUBLISH_SHARE = "publish/share";

    //货源
    public static final String ROUTER_CARGO = "cargo";
    //货源列表
    public static final String ROUTER_CARGO_LIST = "cargo/list";
    //货主列表
    public static final String ROUTER_CARGO_OWNER_LIST = "cargo/owner_list";
    //货源详情
    public static final String ROUTER_CARGO_DETAIL = "cargo/detail";
    //货主详情
    public static final String ROUTER_CARGO_OWNER = "cargo/owner";

    //车源
    public static final String ROUTER_TRUCK = "truck";
    //车源详情
    public static final String ROUTER_TRUCK_DETAIL = "truck/detail";

    //价格
    public static final String ROUTER_PRICE = "price";
    //价格-A
    public static final String ROUTER_PRICE_LOCAL_A = "price/local/A";
    //价格-B
    public static final String ROUTER_PRICE_LOCAL_B = "price/local/B";
    //价格-C
    public static final String ROUTER_PRICE_LOCAL_C = "price/local/C";
    //价格-D
    public static final String ROUTER_PRICE_LOCAL_D = "price/local/D";

    //我的
    public static final String ROUTER_ME ="me";

    //认证
    public static final String ROUTER_VERIFY = "verify";
    //认证-个人
    public static final String ROUTER_VERIFY_PERSONAL = "verify/personal";
    //认证-公司
    public static final String ROUTER_VERIFY_COMPANY = "verify/company";

    //信息
    public static final String ROUTER_INFORMATION = "information";

    //商城
    public static final String ROUTER_STORE = "store";

    //设置
    public static final String ROUTER_SETTING = "setting";
    //关于我们，版本升级
    public static final String ROUTER_ABOUT_AND_UPDATE = "setting/aboutAndUpdate";

    //地图
    public static final String ROUTER_MAP = "map";

    //文本
    public static final String ROUTER_SHOW_TEXT = "showText";
    public static void go(String path, Context context) {
        Router.build(path).anim(R.anim.push_left_in, R.anim.push_left_out).go(context);
    }

    public static void go(String path, Context context, Bundle bundle) {
        Router.build(path).anim(R.anim.push_left_in, R.anim.push_left_out).extras(bundle).go(context);
    }
}
