package com.pi.small.goal.utils.entity;

import java.util.List;

/**
 * Created by JS on 2017-06-10.
 */

public class UserSearchEntity {
    //    {"id":1,"nick":"aa","avatar":"jpg","brief":"",
//            "city":"","sex":0,"updateTime":1496979123000,"status":1,"aim":0,"login":0,"follow":0,"beFollowed":0}
    private String id;
    private String nick;
    private String avatar;
    private String brief;
    private String city;
    private String sex;
    private String updateTime;
    private String status;
    private String aim;
    private String login;
    private String follow;
    private String beFollowed;
    private List<AimEntity> aimEntityList;

    public String getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(String isFollowed) {
        this.isFollowed = isFollowed;
    }

    private String isFollowed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getBeFollowed() {
        return beFollowed;
    }

    public void setBeFollowed(String beFollowed) {
        this.beFollowed = beFollowed;
    }

    public List<AimEntity> getAimEntityList() {
        return aimEntityList;
    }

    public void setAimEntityList(List<AimEntity> aimEntityList) {
        this.aimEntityList = aimEntityList;
    }




}
