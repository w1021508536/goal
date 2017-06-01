package com.pi.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 19:19
 * 描述：
 * 修改： 我的关注的实体类
 **/
public class FollowEntry {
    // "followId":2,"userId":26,"followUserId":8,"nick":"ee","avatar":"jpg"
    private int followId;
    private int userId;
    private int followUserId;
    private String nick;
    private String avatar;

    public FollowEntry() {
    }

    public FollowEntry(int followId, int userId, int followUserId, String nick, String avatar) {
        this.followId = followId;
        this.userId = userId;
        this.followUserId = followUserId;
        this.nick = nick;
        this.avatar = avatar;
    }

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(int followUserId) {
        this.followUserId = followUserId;
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
