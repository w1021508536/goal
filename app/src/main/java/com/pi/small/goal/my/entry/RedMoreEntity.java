package com.pi.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/5 21:37
 * 修改：
 * 描述：
 **/
public class RedMoreEntity {


    /**
     * redPacketRecordId : 1
     * userId : 11
     * money : 8
     * packetId : 1
     * createTime : 1491300885000
     * type : 1                type: 1:公共红包(可抢)  2:助力红包（用户收取的别人的助力） 3:收益红包
     * fromUserId : 0
     */

    private int redPacketRecordId;
    private int userId;
    private int money;
    private int packetId;
    private long createTime;
    private int type;
    private int fromUserId;

    public RedMoreEntity(int redPacketRecordId, int userId, int money, int packetId, long createTime, int type, int fromUserId) {
        this.redPacketRecordId = redPacketRecordId;
        this.userId = userId;
        this.money = money;
        this.packetId = packetId;
        this.createTime = createTime;
        this.type = type;
        this.fromUserId = fromUserId;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }
}
