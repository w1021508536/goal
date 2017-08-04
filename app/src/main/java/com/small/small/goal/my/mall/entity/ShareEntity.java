package com.small.small.goal.my.mall.entity;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/15 9:56
 * 修改：
 * 描述：晒图分享的
 **/
public class ShareEntity {

    /**
     * id : 4
     * orderNo : 102017071409493606251851
     * img1 : http://img2.imgtn.bdimg.com/it/u=3309760069,2909302417&fm=214&gp=0.jpg
     * img2 :
     * img3 :
     * img4 :
     * img5 :
     * img6 :
     * userId : 1
     * nick : 哈哈
     * avatar : http://img2.imgtn.bdimg.com/it/u=3309760069,2909302417&fm=214&gp=0.jpg
     * createTime : 2017-07-14 16:33:56
     * content : 好一只狗
     * vote : 2
     * comment : 3
     */

    private int id;
    private String orderNo;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private String img6;
    private int userId;
    private String nick;
    private String avatar;
    private String createTime;
    private String content;
    private int vote;
    private int comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getImg5() {
        return img5;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
    }

    public String getImg6() {
        return img6;
    }

    public void setImg6(String img6) {
        this.img6 = img6;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
