package com.pi.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 13:21
 * 描述：
 * 修改：红包明细里的实体
 **/
public class RedMoreAdapterEntry {

    private int redPacketRecordId;
    private int userId;
    private int money;
    private int packetId;
    private int createTime;
    private int type;
    private int titleType;

    public RedMoreAdapterEntry(int redPacketRecordId, int userId, int money, int packetId, int createTime, int type, int titleType) {
        this.redPacketRecordId = redPacketRecordId;
        this.userId = userId;
        this.money = money;
        this.packetId = packetId;
        this.createTime = createTime;
        this.type = type;
        this.titleType = titleType;
    }

    public RedMoreAdapterEntry(int redPacketRecordId, int userId, int money, int packetId, int createTime, int type) {
        this.redPacketRecordId = redPacketRecordId;
        this.userId = userId;
        this.money = money;
        this.packetId = packetId;
        this.createTime = createTime;
        this.type = type;
    }

    public int getRedPacketRecordId() {
        return redPacketRecordId;
    }

    public void setRedPacketRecordId(int redPacketRecordId) {
        this.redPacketRecordId = redPacketRecordId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPacketId() {
        return packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTitleType() {
        return titleType;
    }

    public void setTitleType(int titleType) {
        this.titleType = titleType;
    }
}
