package com.xmw.qiyun.data.model.net.user;

/**
 * Created by hongyuan on 2017/8/29.
 */

public class VerifyPersonalInfo {

    private String Name;
    private String IDCardNum;
    private String IDCardId;
    private String IdentityVerificationId;
    private String BusinessCardId;
    private boolean IsHaveBusinessCard;
    private String PersonalInfoStatisticsValue;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIDCardNum() {
        return IDCardNum;
    }

    public void setIDCardNum(String IDCardNum) {
        this.IDCardNum = IDCardNum;
    }

    public String getIDCardId() {
        return IDCardId;
    }

    public void setIDCardId(String IDCardId) {
        this.IDCardId = IDCardId;
    }

    public String getIdentityVerificationId() {
        return IdentityVerificationId;
    }

    public void setIdentityVerificationId(String identityVerificationId) {
        IdentityVerificationId = identityVerificationId;
    }

    public String getBusinessCardId() {
        return BusinessCardId;
    }

    public void setBusinessCardId(String businessCardId) {
        BusinessCardId = businessCardId;
    }

    public boolean isHaveBusinessCard() {
        return IsHaveBusinessCard;
    }

    public void setHaveBusinessCard(boolean haveBusinessCard) {
        IsHaveBusinessCard = haveBusinessCard;
    }

    public String getPersonalInfoStatisticsValue() {
        return PersonalInfoStatisticsValue;
    }

    public void setPersonalInfoStatisticsValue(String personalInfoStatisticsValue) {
        PersonalInfoStatisticsValue = personalInfoStatisticsValue;
    }
}
