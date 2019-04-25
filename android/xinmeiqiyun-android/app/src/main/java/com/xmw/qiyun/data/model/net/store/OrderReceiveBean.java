package com.xmw.qiyun.data.model.net.store;

/**
 * Created by dell on 2018/1/29.
 */

public class OrderReceiveBean {

    private String PaymentChannel;
    private String GoodsId;
    private int CountOfDeal;
    private boolean IsNeedInvoice;
    private int InvoiceType;
    private String InvoiceTitle;
    private String InvoiceTax;
    private String CompanyAddress;
    private String CompanyNumber;
    private String BankName;
    private String BankAccount;
    private String ServiceId;

    public String getPaymentChannel() {
        return PaymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        PaymentChannel = paymentChannel;
    }

    public String getGoodsId() {
        return GoodsId;
    }

    public void setGoodsId(String goodsId) {
        GoodsId = goodsId;
    }

    public int getCountOfDeal() {
        return CountOfDeal;
    }

    public void setCountOfDeal(int countOfDeal) {
        CountOfDeal = countOfDeal;
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

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }
}
