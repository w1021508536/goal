package com.pi.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 11:09
 * 描述：
 * 修改：未领取的红包的尸体数据
 **/
public class WalletEntry {

    private int type;

    public WalletEntry(int type) {
        this.type = type;
    }

    public WalletEntry() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
