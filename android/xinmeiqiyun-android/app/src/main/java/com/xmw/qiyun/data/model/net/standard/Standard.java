package com.xmw.qiyun.data.model.net.standard;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class Standard {

    private String Id;
    private String ParentId;
    private String OptionCode;
    private String Value;
    private String ShowOrder;

    public Standard() {
    }

    public Standard(String value) {
        Value = value;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getOptionCode() {
        return OptionCode;
    }

    public void setOptionCode(String optionCode) {
        OptionCode = optionCode;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getShowOrder() {
        return ShowOrder;
    }

    public void setShowOrder(String showOrder) {
        ShowOrder = showOrder;
    }
}
