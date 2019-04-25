package com.xmw.qiyun.util;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.xmw.qiyun.data.manager.StoreManager;
import com.xmw.qiyun.data.manager.UserManager;
import com.xmw.qiyun.data.model.net.store.AliResultBean;
import com.xmw.qiyun.data.model.net.store.Order;
import com.xmw.qiyun.data.model.net.store.OrderDetail;
import com.xmw.qiyun.data.model.net.store.OrderReceiveBean;
import com.xmw.qiyun.data.model.net.store.OrderSubmitBody;
import com.xmw.qiyun.data.model.net.store.WxResultBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.manage.ConstantsUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.SystemUtil;
import com.xmw.qiyun.util.pay.PayController;
import com.xmw.qiyun.util.share.WxShareUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/1/15.
 */

public class JSUtil {

    private Activity mContext;

    public JSUtil(Activity context) {

        mContext = context;
    }

    //H5传递订单信息及支付方式的方法
    @JavascriptInterface
    public void getOrderInfo(String orderInfo) {

        //接收H5传递参数
        OrderReceiveBean orderReceiveBean = GsonImpl.init().toObject(orderInfo, OrderReceiveBean.class);

        //创建订单详情
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setGoodsId(orderReceiveBean.getGoodsId());
        orderDetail.setServiceId(orderReceiveBean.getServiceId());
        orderDetail.setCountOfDeal(orderReceiveBean.getCountOfDeal());

        //生成订单详情列表
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);

        //生成订单
        Order order = new Order();
        order.setOrderDetails(orderDetailList);

        //生成订单列表
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);

        //生成订单Body
        OrderSubmitBody orderSubmitBody = new OrderSubmitBody();
        orderSubmitBody.setPaymentChannel(TextUtils.equals("alipay", orderReceiveBean.getPaymentChannel()) ? 2 : 1);
        orderSubmitBody.setAppId(TextUtils.equals("alipay", orderReceiveBean.getPaymentChannel()) ? ConstantsUtil.ALI_APP_ID : WxShareUtil.APP_ID);
        orderSubmitBody.setTimeStamp(String.valueOf(CommonUtil.getTimeStamp()));
        orderSubmitBody.setIP(SystemUtil.getIpAddress());
        orderSubmitBody.setBuyerId(UserManager.getId());
        orderSubmitBody.setOrders(orderList);
        orderSubmitBody.setNeedInvoice(orderReceiveBean.isNeedInvoice());

        //判断是否填写发票
        if (orderSubmitBody.isNeedInvoice()) {
            orderSubmitBody.setInvoiceType(orderReceiveBean.getInvoiceType());
            orderSubmitBody.setInvoiceTitle(orderReceiveBean.getInvoiceTitle());
            orderSubmitBody.setInvoiceTax(orderReceiveBean.getInvoiceTax());
            orderSubmitBody.setCompanyAddress(orderReceiveBean.getCompanyAddress());
            orderSubmitBody.setCompanyNumber(orderReceiveBean.getCompanyNumber());
            orderSubmitBody.setBankName(orderReceiveBean.getBankName());
            orderSubmitBody.setBankAccount(orderReceiveBean.getBankAccount());
        }

        NoteUtil.showLoading(mContext);

        //调用提交订单接口
        switch (orderSubmitBody.getPaymentChannel()) {

            default:
                break;

            case 1: {

                API.getService().submitWxOrder(orderSubmitBody).subscribe(new MySubscriber<WxResultBean>() {
                    @Override
                    public void onNext(WxResultBean wxResultBean) {

                        NoteUtil.hideLoading();

                        StoreManager.setOrderId(wxResultBean.getData().getTradeNumber());

                        PayController.wxPay(mContext, wxResultBean.getData());
                    }
                });
            }
            break;

            case 2: {

                API.getService().submitAliOrder(orderSubmitBody).subscribe(new MySubscriber<AliResultBean>() {
                    @Override
                    public void onNext(AliResultBean aliResultBean) {

                        NoteUtil.hideLoading();

                        StoreManager.setOrderId(aliResultBean.getData().getOrderNumber());

                        PayController.aliPay(mContext, aliResultBean.getData().getOrderInfo());
                    }
                });
            }
            break;
        }
    }
}