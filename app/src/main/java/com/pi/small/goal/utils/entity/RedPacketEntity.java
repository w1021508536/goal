package com.pi.small.goal.utils.entity;

/**
 * Created by JS on 2017-06-08.
 * 助力红包
 */

public class RedPacketEntity {
//
//    id":12,"aimId":18,"dynamicId":64,"money":10.00,"size":10,"
//    remainMoney":10.00,"remainSize":10,"toUserId":27,"fromUserId":26,"
//    createTime":1496885913000,"status":1,"type":1,"fromUserNick":"44","fromUserAvatar":""}

    private String id;
    private String aimId;
    private String dynamicId;
    private String money;
    private String size;
    private String remainMoney;
    private String remainSize;
    private String toUserId;
    private String fromUserId;
    private String createTime;

    private String status;
    private String type;
    private String fromUserNick;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAimId() {
        return aimId;
    }

    public void setAimId(String aimId) {
        this.aimId = aimId;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(String remainMoney) {
        this.remainMoney = remainMoney;
    }

    public String getRemainSize() {
        return remainSize;
    }

    public void setRemainSize(String remainSize) {
        this.remainSize = remainSize;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    private String fromUserAvatar;


}
