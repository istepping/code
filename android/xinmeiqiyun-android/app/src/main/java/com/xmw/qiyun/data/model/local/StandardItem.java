package com.xmw.qiyun.data.model.local;

import com.xmw.qiyun.data.model.net.standard.Standard;

/**
 * Created by hongyuan on 2017/9/1.
 */

public class StandardItem {

    private Standard standard;
    private boolean hasSelected;

    public StandardItem() {
    }

    public StandardItem(Standard standard, boolean hasSelected) {
        this.standard = standard;
        this.hasSelected = hasSelected;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public boolean isHasSelected() {
        return hasSelected;
    }

    public void setHasSelected(boolean hasSelected) {
        this.hasSelected = hasSelected;
    }
}
