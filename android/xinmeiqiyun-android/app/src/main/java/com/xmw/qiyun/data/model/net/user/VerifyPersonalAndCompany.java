package com.xmw.qiyun.data.model.net.user;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class VerifyPersonalAndCompany {

    private VerifyPersonalInfo PersonalInfo;
    private VerifyCompanyInfo CompanyInfo;
    private int AuthenticationType;
    private String TotalStatistics;

    public VerifyPersonalInfo getPersonalInfo() {
        return PersonalInfo;
    }

    public void setPersonalInfo(VerifyPersonalInfo personalInfo) {
        PersonalInfo = personalInfo;
    }

    public VerifyCompanyInfo getCompanyInfo() {
        return CompanyInfo;
    }

    public void setCompanyInfo(VerifyCompanyInfo companyInfo) {
        CompanyInfo = companyInfo;
    }

    public int getAuthenticationType() {
        return AuthenticationType;
    }

    public void setAuthenticationType(int authenticationType) {
        AuthenticationType = authenticationType;
    }

    public String getTotalStatistics() {
        return TotalStatistics;
    }

    public void setTotalStatistics(String totalStatistics) {
        TotalStatistics = totalStatistics;
    }
}
