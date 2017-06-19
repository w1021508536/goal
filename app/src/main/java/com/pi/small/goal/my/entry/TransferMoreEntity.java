package com.pi.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/16 18:14
 * 修改：
 * 描述： 转让明细的
 **/
public class TransferMoreEntity {


    /**
     * id : 4
     * amount : 1
     * fromUserId : 48
     * toUserId : 30
     * createTime : 1497601633000
     */

    private int id;
    private double amount;
    private int fromUserId;
    private int toUserId;
    private long createTime;

    private int titleType;
    private String title;
    private double addMoney;
    private double deleteMoney;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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

    public double getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(double addMoney) {
        this.addMoney = addMoney;
    }

    public double getDeleteMoney() {
        return deleteMoney;
    }

    public void setDeleteMoney(double deleteMoney) {
        this.deleteMoney = deleteMoney;
    }

    public TransferMoreEntity(int id, double amount, int fromUserId, int toUserId, long createTime, int titleType) {
        this.id = id;
        this.amount = amount;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.createTime = createTime;
        this.titleType = titleType;
    }

    public TransferMoreEntity(int titleType, String title, double addMoney, double deleteMoney) {
        this.titleType = titleType;
        this.title = title;
        this.addMoney = addMoney;
        this.deleteMoney = deleteMoney;
    }
}
