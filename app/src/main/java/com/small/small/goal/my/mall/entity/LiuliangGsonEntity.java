package com.small.small.goal.my.mall.entity;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/14 10:36
 * 修改：
 * 描述：
 **/
public class LiuliangGsonEntity extends RecordEntity {

    /**
     * id : 7
     * userId : 1
     * orderNo : 2017010204
     * jid :
     * mobile : 18678989876
     * cardName : 20M流量
     * orderCash : 5
     * amount : 20
     * status : 1
     * createTime : 2017-07-14 10:14:40
     * updateTime : 2017-07-14 10:21:36
     * bean : 400
     */

    private int id;
    private int userId;
    private String orderNo;
    private String jid;
    private String mobile;
    private String cardName;
    private int orderCash;
    private int amount;
    private int status;
    private String createTime;
    private String updateTime;
    private int bean;

    public LiuliangGsonEntity() {
    }

    public LiuliangGsonEntity(int id, int userId, String orderNo, String jid, String mobile, String cardName, int orderCash, int amount, int status, String createTime, String updateTime, int bean) {
        this.id = id;
        this.userId = userId;
        this.orderNo = orderNo;
        this.jid = jid;
        this.mobile = mobile;
        this.cardName = cardName;
        this.orderCash = orderCash;
        this.amount = amount;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.bean = bean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getOrderCash() {
        return orderCash;
    }

    public void setOrderCash(int orderCash) {
        this.orderCash = orderCash;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getBean() {
        return bean;
    }

    public void setBean(int bean) {
        this.bean = bean;
    }
}
