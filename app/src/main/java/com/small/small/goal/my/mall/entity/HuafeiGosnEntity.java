package com.small.small.goal.my.mall.entity;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/14 10:30
 * 修改：
 * 描述：gson用的话费充值记录
 **/
public class HuafeiGosnEntity extends RecordEntity {

    /**
     * cardId : 5001
     * cardNum : 1
     * orderCash : 20
     * amount : 0
     * cardName : 20元充值
     * id : 3
     * jid : 1212
     * mobile : 18678989876
     * status : 1
     * userId : 1
     * orderNo : 20170102
     * createTime : 2017-07-14 10:12:45
     * updateTime : 2017-07-14 10:22:03
     * bean : 200
     */

    private String cardId;
    private int cardNum;
    private double orderCash;
    private int amount;
    private String cardName;
    private int id;
    private String jid;
    private String mobile;
    private int status;
    private int userId;
    private String orderNo;
    private String createTime;
    private String updateTime;
    private int bean;

    public HuafeiGosnEntity() {
    }

    public HuafeiGosnEntity(String cardId, int cardNum, double orderCash, int amount, String cardName, int id, String jid, String mobile, int status, int userId, String orderNo, String createTime, String updateTime, int bean) {
        this.cardId = cardId;
        this.cardNum = cardNum;
        this.orderCash = orderCash;
        this.amount = amount;
        this.cardName = cardName;
        this.id = id;
        this.jid = jid;
        this.mobile = mobile;
        this.status = status;
        this.userId = userId;
        this.orderNo = orderNo;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.bean = bean;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public double getOrderCash() {
        return orderCash;
    }

    public void setOrderCash(double orderCash) {
        this.orderCash = orderCash;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
