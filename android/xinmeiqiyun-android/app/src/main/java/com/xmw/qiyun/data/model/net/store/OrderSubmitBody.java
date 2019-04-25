package com.xmw.qiyun.data.model.net.store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/1/29.
 */

public class OrderSubmitBody {

    private int PaymentChannel;
    private String AppId;
    private String TimeStamp;
    private String IP;
    private String BuyerId;
    private String CurrencyType = "RMB";
    private String PaymentWay = "App";
    private List<Order> Orders = new ArrayList<>();
    private boolean IsNeedInvoice;
    private int InvoiceType;
    private String InvoiceTitle;
    private String InvoiceTax;
    private String CompanyAddress;
    private String CompanyNumber;
    private String BankName;
    private String BankAccount;
    private String DeliveryAddress;
    private String DeliveryContacts;
    private String DeliveryTel;
    private String PostNumber;

    public int getPaymentChannel() {
        return PaymentChannel;
    }

    public void setPaymentChannel(int paymentChannel) {
        PaymentChannel = paymentChannel;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getBuyerId() {
        return BuyerId;
    }

    public void setBuyerId(String buyerId) {
        BuyerId = buyerId;
    }

    public String getCurrencyType() {
        return CurrencyType;
    }

    public void setCurrencyType(String currencyType) {
        CurrencyType = currencyType;
    }

    public String getPaymentWay() {
        return PaymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        PaymentWay = paymentWay;
    }

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
        Orders = orders;
    }

    public boolean isNeedInvoice() {
        return IsNeedInvoice;
    }

    public void setNeedInvoice(boolean needInvoice) {
        IsNeedInvoice = needInvoice;
    }

    public int getInvoiceType() {
        return InvoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        InvoiceType = invoiceType;
    }

    public String getInvoiceTitle() {
        return InvoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        InvoiceTitle = invoiceTitle;
    }

    public String getInvoiceTax() {
        return InvoiceTax;
    }

    public void setInvoiceTax(String invoiceTax) {
        InvoiceTax = invoiceTax;
    }

    public String getCompanyAddress() {
        return CompanyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        CompanyAddress = companyAddress;
    }

    public String getCompanyNumber() {
        return CompanyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        CompanyNumber = companyNumber;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }

    public String getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }

    public String getDeliveryContacts() {
        return DeliveryContacts;
    }

    public void setDeliveryContacts(String deliveryContacts) {
        DeliveryContacts = deliveryContacts;
    }

    public String getDeliveryTel() {
        return DeliveryTel;
    }

    public void setDeliveryTel(String deliveryTel) {
        DeliveryTel = deliveryTel;
    }

    public String getPostNumber() {
        return PostNumber;
    }

    public void setPostNumber(String postNumber) {
        PostNumber = postNumber;
    }
}
