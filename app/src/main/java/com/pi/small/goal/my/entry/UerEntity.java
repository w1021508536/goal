package com.pi.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/7 10:58
 * 修改：
 * 描述：个人的实体
 **/
public class UerEntity {
    /**
     * user : {"id":48,"nick":"花重锦官城","avatar":"1496478918741.jpeg","brief":"加油","wechatId":"","qqId":"9238*****59EC","mobile":"","city":"","sex":0,"createTime":1496284728000,"updateTime":1496478942000}
     * account : {"accountId":4,"userId":48,"exp":16,"balance":0,"aim":0,"option":0,"score":0}
     * taskInfo : {"totalTaskCount":4,"finishTaskCount":0}
     * grade : v0
     */

    private UserBean user;
    private AccountBean account;
    private TaskInfoBean taskInfo;
    private String grade;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public TaskInfoBean getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfoBean taskInfo) {
        this.taskInfo = taskInfo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public static class UserBean {
        /**
         * id : 48
         * nick : 花重锦官城
         * avatar : 1496478918741.jpeg
         * brief : 加油
         * wechatId :
         * qqId : 9238*****59EC
         * mobile :
         * city :
         * sex : 0
         * createTime : 1496284728000
         * updateTime : 1496478942000
         */

        private int id;
        private String nick;
        private String avatar;
        private String brief;
        private String wechatId;
        private String qqId;
        private String mobile;
        private String city;
        private int sex;
        private long createTime;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public String getWechatId() {
            return wechatId;
        }

        public void setWechatId(String wechatId) {
            this.wechatId = wechatId;
        }

        public String getQqId() {
            return qqId;
        }

        public void setQqId(String qqId) {
            this.qqId = qqId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
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
    }

    public static class AccountBean {
        /**
         * accountId : 4
         * userId : 48
         * exp : 16
         * balance : 0
         * aim : 0
         * option : 0
         * score : 0
         */

        private int accountId;
        private int userId;
        private int exp;
        private float balance;
        private int aim;
        private int option;
        private int score;

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getExp() {
            return exp;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }

        public float getBalance() {
            return balance;
        }

        public void setBalance(float balance) {
            this.balance = balance;
        }

        public int getAim() {
            return aim;
        }

        public void setAim(int aim) {
            this.aim = aim;
        }

        public int getOption() {
            return option;
        }

        public void setOption(int option) {
            this.option = option;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

    public static class TaskInfoBean {
        /**
         * totalTaskCount : 4
         * finishTaskCount : 0
         */

        private int totalTaskCount;
        private int finishTaskCount;

        public int getTotalTaskCount() {
            return totalTaskCount;
        }

        public void setTotalTaskCount(int totalTaskCount) {
            this.totalTaskCount = totalTaskCount;
        }

        public int getFinishTaskCount() {
            return finishTaskCount;
        }

        public void setFinishTaskCount(int finishTaskCount) {
            this.finishTaskCount = finishTaskCount;
        }
    }

    //   {"msg":"success","code":0,"result":{"user":{"id":48,"nick":"花重锦官城","avatar":"1496478918741.jpeg","brief":"加油","wechatId":"","qqId":"9238*****59EC","mobile":"","city":"","sex":0,"createTime":1496284728000,"updateTime":1496478942000},"account":{"accountId":4,"userId":48,"exp":16,"balance":0.00,"aim":0,"option":0,"score":0},"taskInfo":{"totalTaskCount":4,"finishTaskCount":0},"grade":"v0"},"pageNum":0,"pageSize":0,"pageTotal":0,"total":0}

}
