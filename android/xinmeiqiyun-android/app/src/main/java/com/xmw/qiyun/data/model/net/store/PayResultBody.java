package com.xmw.qiyun.data.model.net.store;

/**
 * Created by dell on 2018/2/6.
 */

public class PayResultBody {

    private String Id;
    private String AppId;
    private int PaymentChannel;
    private String TradeNumber;
    private String TransactionId;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public int getPaymentChannel() {
        return PaymentChannel;
    }

    public void setPaymentChannel(int paymentChannel) {
        PaymentChannel = paymentChannel;
    }

    public String getTradeNumber() {
        return TradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        TradeNumber = tradeNumber;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }
}
