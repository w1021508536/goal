package com.pi.small.goal.utils.entity;

public class ContactBean {

//	       map.put("id", jsonArray.getJSONObject(i).optString("id"));
//                            map.put("userId", jsonArray.getJSONObject(i).optString("userId"));
//                            map.put("avatar", jsonArray.getJSONObject(i).optString("avatar"));
//                            map.put("friendId", jsonArray.getJSONObject(i).optString("friendId"));
//                            map.put("remark", jsonArray.getJSONObject(i).optString("remark"));
//                            map.put("createTime", jsonArray.getJSONObject(i).optString("createTime"));
//                            map.put("nick", jsonArray.getJSONObject(i).optString("nick"));
//                            map.put("brief", jsonArray.getJSONObject(i).optString("brief"));
//                            map.put("isBlack", jsonArray.getJSONObject(i).optString("isBlack"));

    private int contactId;
    private String desplayName;
    private String sortLetters; // 显示数据拼音的首字母

    private String id;
    private String userId;
    private String avatar;
    private String friendId;
    private String remark;
    private String createTime;
    private String nick;
    private String brief;
    private String isBlack;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(String isBlack) {
        this.isBlack = isBlack;
    }




    public ContactBean() {
        super();
    }

    public ContactBean(int contactId, String desplayName) {
        this.contactId = contactId;
        this.desplayName = desplayName;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getDesplayName() {
        return desplayName;
    }

    public void setDesplayName(String desplayName) {
        this.desplayName = desplayName;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
