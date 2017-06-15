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
    private double money;
    private int packetId;
    private long createTime;
    private int type;   //type: 1:公共红包(可抢)  2:助力红包（用户收取的别人的助力） 3:收益红包
    private int fromUserId;
    private String fromUserNick= "";
    private String fromUserAvatar= "";
    private int titleType;
    private String title= "";
    private float addMoney;     //标题里的总收益
    private float deletteMoney; //标题里的总支出

    public RedMoreAdapterEntry(int titleType, String title, float addMoney, float deletteMoney) {
        this.titleType = titleType;
        this.title = title;
        this.addMoney = addMoney;
        this.deletteMoney = deletteMoney;
    }

    public RedMoreAdapterEntry(int redPacketRecordId, int userId, double money, int packetId, long createTime, int type, int fromUserId, String fromUserNick, String fromUserAvatar, int titleType) {
        this.redPacketRecordId = redPacketRecordId;
        this.userId = userId;
        this.money = money;
        this.packetId = packetId;
        this.createTime = createTime;
        this.type = type;
        this.fromUserId = fromUserId;
        this.fromUserNick = fromUserNick;
        this.fromUserAvatar = fromUserAvatar;
        this.titleType = titleType;
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

    public int getTitleType() {
        return titleType;
    }

    public void setTitleType(int titleType) {
        this.titleType = titleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public float getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(float addMoney) {
        this.addMoney = addMoney;
    }

    public float getDeletteMoney() {
        return deletteMoney;
    }

    public void setDeletteMoney(float deletteMoney) {
        this.deletteMoney = deletteMoney;
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
