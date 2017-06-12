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
     * redPacketRecordId : 185
     * userId : 48
     * money : 8.03
     * packetId : 30
     * createTime : 1497231587000
     * type : 1
     * fromUserId : 26
     * fromUserNick : 44
     * fromUserAvatar :
     * nick : 花重锦官城
     * avatar : http://img.smallaim.cn/1496900672239.jpeg
     */

    private int redPacketRecordId;
    private int userId;
    private double money;
    private int packetId;
    private long createTime;
    private int type;
    private int fromUserId;
    private String fromUserNick;
    private String fromUserAvatar;
    private String nick;
    private String avatar;

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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
