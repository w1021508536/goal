package com.small.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/16 18:14
 * 修改：
 * 描述： 转让明细的
 **/
public class TransferMoreGsonEntity {


    /**
     * id : 4
     * amount : 1
     * fromUserId : 48
     * toUserId : 30
     * createTime : 1497601633000
     * fromUserNick : 花重锦官城
     * fromUserAvatar : http://img.smallaim.cn/1497442967343.jpeg
     * toUserNick : 13206427314
     * toUserAvatar :
     */

    private int id;
    private double amount;
    private int fromUserId;
    private int toUserId;
    private long createTime;
    private String fromUserNick;
    private String fromUserAvatar;
    private String toUserNick;
    private String toUserAvatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public String getToUserNick() {
        return toUserNick;
    }

    public void setToUserNick(String toUserNick) {
        this.toUserNick = toUserNick;
    }

    public String getToUserAvatar() {
        return toUserAvatar;
    }

    public void setToUserAvatar(String toUserAvatar) {
        this.toUserAvatar = toUserAvatar;
    }
}
