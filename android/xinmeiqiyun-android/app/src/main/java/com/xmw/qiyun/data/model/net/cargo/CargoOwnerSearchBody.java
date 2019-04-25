package com.xmw.qiyun.data.model.net.cargo;

/**
 * Created by dell on 2017/11/1.
 */

public class CargoOwnerSearchBody {

    private int RegionType;
    private String RegionId;
    private String CompanyShortName;

    public int getRegionType() {
        return RegionType;
    }

    public void setRegionType(int regionType) {
        RegionType = regionType;
    }

    public String getRegionId() {
        return RegionId;
    }

    public void setRegionId(String regionId) {
        RegionId = regionId;
    }

    public String getCompanyShortName() {
        return CompanyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        CompanyShortName = companyShortName;
    }
}
