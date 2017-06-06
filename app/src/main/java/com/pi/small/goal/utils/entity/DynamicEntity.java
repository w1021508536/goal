package com.pi.small.goal.utils.entity;

import java.util.List;

/**
 * Created by JS on 2017-06-06.
 */

public class DynamicEntity {

    //dynamic
    private String id;
    private String aimId;
    private String userId;
    private String nick;
    private String avatar;
    private String content;
    private String city;
    private String money;
    private String img1;
    private String img2;
    private String img3;
    private String video;
    private String updateTime;
    private String createTime;
    private String province;
    private String isPaid;

    private String haveVote;
    private String votes;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getHaveVote() {
        return haveVote;
    }

    public void setHaveVote(String haveVote) {
        this.haveVote = haveVote;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getSupports() {
        return supports;
    }

    public void setSupports(String supports) {
        this.supports = supports;
    }

    private String supports;
    private String haveRedPacket;
    private List<List<CommentEntity>> dynamicList;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getHaveRedPacket() {
        return haveRedPacket;
    }

    public void setHaveRedPacket(String haveRedPacket) {
        this.haveRedPacket = haveRedPacket;
    }

    public List<List<CommentEntity>> getDynamicList() {
        return dynamicList;
    }

    public void setDynamicList(List<List<CommentEntity>> dynamicList) {
        this.dynamicList = dynamicList;
    }


}
