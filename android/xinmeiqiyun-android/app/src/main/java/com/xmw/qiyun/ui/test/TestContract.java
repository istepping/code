package com.xmw.qiyun.ui.test;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;

/*
* 此接口为 V 层与 P 层的中继层，用于集中分发 V 层 与 P 层的方法
*/
interface TestContract {

    /* V 层的方法 */
    interface View extends BaseView<Presenter> {

        void init();
    }

    /* P 层的方法 */
    interface Presenter extends BasePresenter<View> {

        //接口测试方法（以获取省份数据为例）
        void getProvinceList();

        /*
        * 封装方法总结
        *
        * 1.util.manage.AppUtil 处理Activity堆栈问题
        * 2.util.manage.CommonUtil 处理常规的验证问题
        * 3.util.manage.NoteUtil 处理Loading与Toast相关问题
        * 4.util.manage.RouterUtil 处理组件路由问题（Activity 与 Fragment 的组件定义与跳转）（important）
        * 5.util.manage.SystemUtil 处理手机相关问题
        *
        * 6.util.image.ImageUtil 处理图片的压缩与显示问题
        *
        * 7.util.dialog.DialogUtil 处理Dialog相关问题
        *
        * 8.util.pay文件夹存放支付相关的封装
        *
        * 9.view文件夹存放自定义view控件
        *
        * 10.net.api文件夹存放api相关的封装（important）
        *
        * 11.net.json文件夹存放json相关的封装
        *
        * 12.data.manager文件夹存放持久化数据的封装
        *
        * 13.data.model文件夹存放所有的模型类
        *
        * 13.base文件夹存放所有基类（Activity, Fragment, V 层， P 层）
        * */

        /*
        * 第三方库总结
        *
        * 1.控件：ButterKnife
        * 2.接口：Retrofit + RxJava
        * 3.广播：EventBus
        * 4.图片：Glide
        * 5.序列化反序列化：Gson
        *
        * 6.地图：地图相关方法请参考ui.map.MapActivity, 拥有完整的流程处理与方法封装
        *
        * */
    }
}