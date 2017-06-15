package com.pi.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 11:09
 * 描述：
 * 修改：未领取的红包的实体数据
 **/
public class WalletEntry {

    /**
     * id : 1
     * aimId : 3
     * dynamicId : 4
     * money : 10
     * size : 5
     * remainMoney : 10
     * remainSize : 5
     * toUserId : 11
     * fromUserId : 26
     * createTime : 1495793276000
     * updateTime : 1495793766000
     * status : 1
     * type : 1
     * supportId : 0
     * fromUserNick : 44
     * fromUserAvatar :
     */

    private int id;
    private int aimId;
    private int dynamicId;
    private float money;
    private int size;
    private double remainMoney;
    private int remainSize;
    private int toUserId;
    private int fromUserId;
    private long createTime;
    private long updateTime;
    private int status;
    private int type;
    private int supportId;
    private String fromUserNick= "";
    private String fromUserAvatar= "";
    private int titleType;

    public WalletEntry(int id, int aimId, int dynamicId, float money, int size, double remainMoney, int remainSize, int toUserId, int fromUserId, long createTime, long updateTime, int status, int type, int supportId, String fromUserNick, String fromUserAvatar, int titleType) {
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
        this.updateTime = updateTime;
        this.status = status;
        this.type = type;
        this.supportId = supportId;
        this.fromUserNick = fromUserNick;
        this.fromUserAvatar = fromUserAvatar;
        this.titleType = titleType;
    }

    public WalletEntry(int titleType) {
        this.titleType = titleType;
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

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(double remainMoney) {
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

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
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

    public int getSupportId() {
        return supportId;
    }

    public void setSupportId(int supportId) {
        this.supportId = supportId;
    }

    public String getFromUserNick() {
        return fromUserNick;
    }

    public void setFromUserNick(String fromUserNick) {
        this.fromUserNick = fromUserNick;
    }

    public String getFromUserAvatar() {
        return fromUserAvatar;
    }

    public void setFromUserAvatar(String fromUserAvatar) {
        this.fromUserAvatar = fromUserAvatar;
    }
}
