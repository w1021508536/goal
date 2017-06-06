package com.pi.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 11:09
 * 描述：
 * 修改：未领取的红包的实体数据
 **/
public class WalletEntry {
    private int id;
    private int aimId;
    private int dynamicId;
    private int money;
    private int size;
    private int remainMoney;
    private int remainSize;
    private int toUserId;
    private int fromUserId;
    private long createTime;
    private int status;
    private int type;

    public WalletEntry(int id, int aimId, int dynamicId, int money, int size, int remainMoney, int remainSize, int toUserId, int fromUserId, long createTime, int status, int type) {
        this.id = id;
        this.aimId = aimId;
        this.dynamicId = dynamicId;
        this.money = money;
        this.size = size;
        this.remainMoney = remainMoney;
        this.remainSize = remainSize;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.createTime = createTime;
        this.status = status;
        this.type = type;
    }

    public WalletEntry(int id, int aimId, int dynamicId, int money, int size, int remainMoney, int remainSize, int toUserId, int fromUserId, long createTime, int status) {
        this.id = id;
        this.aimId = aimId;
        this.dynamicId = dynamicId;
        this.money = money;
        this.size = size;
        this.remainMoney = remainMoney;
        this.remainSize = remainSize;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.createTime = createTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAimId() {
        return aimId;
    }

    public void setAimId(int aimId) {
        this.aimId = aimId;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(int remainMoney) {
        this.remainMoney = remainMoney;
    }

    public int getRemainSize() {
        return remainSize;
    }

    public void setRemainSize(int remainSize) {
        this.remainSize = remainSize;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
