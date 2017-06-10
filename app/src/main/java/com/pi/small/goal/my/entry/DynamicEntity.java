package com.pi.small.goal.my.entry;

import java.util.List;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/7 8:41
 * 修改：
 * 描述：
 **/
public class DynamicEntity {
    /**
     * dynamic : {"id":57,"aimId":17,"userId":27,"nick":"1234","avatar":"1496802195021.jpg","content":"","city":"","money":100,"img1":"","img2":"","img3":"","video":"","createTime":1496661462000,"updateTime":1496661467000,"province":"","isPaid":1}
     * votes : 0
     * haveVote : 0
     * comments : [{"commentId":35,"pid":0,"userId":32,"content":"effective","aimId":17,"dynamicId":57,"createTime":1496993506000,"nick":"杨晶晶","avatar":"https://q.qlogo.cn/qqapp/1106004131/E174B73EF43BC3C4D2D8891B79E237AF/100"},{"commentId":34,"pid":0,"userId":32,"content":"ofo呃呃呃","aimId":17,"dynamicId":57,"createTime":1496993485000,"nick":"杨晶晶","avatar":"https://q.qlogo.cn/qqapp/1106004131/E174B73EF43BC3C4D2D8891B79E237AF/100"},{"commentId":31,"pid":0,"userId":43,"content":"啦啦啦啦啦啦啦","aimId":17,"dynamicId":57,"createTime":1496992360000,"nick":"13693361208","avatar":""}]
     * supports : 0
     * haveRedPacket : 0
     */

    private DynamicBean dynamic;
    private int votes;
    private int haveVote;
    private int supports;
    private int haveRedPacket;
    private List<CommentsBean> comments;

    public DynamicBean getDynamic() {
        return dynamic;
    }

    public void setDynamic(DynamicBean dynamic) {
        this.dynamic = dynamic;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getHaveVote() {
        return haveVote;
    }

    public void setHaveVote(int haveVote) {
        this.haveVote = haveVote;
    }

    public int getSupports() {
        return supports;
    }

    public void setSupports(int supports) {
        this.supports = supports;
    }

    public int getHaveRedPacket() {
        return haveRedPacket;
    }

    public void setHaveRedPacket(int haveRedPacket) {
        this.haveRedPacket = haveRedPacket;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class DynamicBean {
        /**
         * id : 57
         * aimId : 17
         * userId : 27
         * nick : 1234
         * avatar : 1496802195021.jpg
         * content :
         * city :
         * money : 100
         * img1 :
         * img2 :
         * img3 :
         * video :
         * createTime : 1496661462000
         * updateTime : 1496661467000
         * province :
         * isPaid : 1
         */

        private int id;
        private int aimId;
        private int userId;
        private String nick;
        private String avatar;
        private String content;
        private String city;
        private int money;
        private String img1;
        private String img2;
        private String img3;
        private String video;
        private long createTime;
        private long updateTime;
        private String province;
        private int isPaid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAimId() {
            return aimId;
        }

        public void setAimId(int aimId) {
            this.aimId = aimId;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
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

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getIsPaid() {
            return isPaid;
        }

        public void setIsPaid(int isPaid) {
            this.isPaid = isPaid;
        }
    }

    public static class CommentsBean {
        /**
         * commentId : 35
         * pid : 0
         * userId : 32
         * content : effective
         * aimId : 17
         * dynamicId : 57
         * createTime : 1496993506000
         * nick : 杨晶晶
         * avatar : https://q.qlogo.cn/qqapp/1106004131/E174B73EF43BC3C4D2D8891B79E237AF/100
         */

        private int commentId;
        private int pid;
        private int userId;
        private String content;
        private int aimId;
        private int dynamicId;
        private long createTime;
        private String nick;
        private String avatar;

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getAimId() {
            return aimId;
        }

        public void setAimId(int aimId) {
            this.aimId = aimId;
        }

        public int getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(int dynamicId) {
            this.dynamicId = dynamicId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
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
    /**
     * dynamic : {"id":57,"aimId":17,"userId":27,"nick":"123","avatar":"","content":"","city":"","money":100,"img1":"","img2":"","img3":"","video":"","createTime":1496661462000,"updateTime":1496661467000,"province":"","isPaid":1}
     * votes : 0
     * haveVote : 0
     * comments : []
     * supports : 0
     * haveRedPacket : 0
     */
//    dynamic：动态
//    votes：点赞数量
//    haveVote：是否已点赞
//    comments：评论
//    supports：支持数量
//    haveRedPacket：是否有红包


    //{"msg":"success","code":0,"result":[{"dynamic":{"id":57,"aimId":17,"userId":27,"nick":"123","avatar":"","content":"","city":"","money":100,"img1":"","img2":"","img3":"","video":"","createTime":1496661462000,"updateTime":1496661467000,"province":"","isPaid":1},"votes":0,"haveVote":0,"comments":[],"supports":0,"haveRedPacket":0},{"dynamic":{"id":55,"aimId":17,"userId":27,"nick":"123","avatar":"","content":"","city":"","money":200,"img1":"","img2":"","img3":"","video":"","createTime":1496661324000,"updateTime":1496661358000,"province":"","isPaid":1},"votes":0,"haveVote":0,"comments":[],"supports":0,"haveRedPacket":0},{"dynamic":{"id":7,"aimId":17,"userId":48,"nick":"花重锦官城","avatar":"1496478918741.jpeg","content":"我的第一次存钱","city":"","money":10,"img1":"1494912134294.jpg","img2":"","img3":"","video":"","createTime":1496398591000,"updateTime":1496717044000,"province":"","isPaid":1},"votes":0,"haveVote":0,"comments":[],"supports":2,"haveRedPacket":1}],"pageNum":1,"pageSize":9,"pageTotal":1,"total":3}


}
