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
     * user : {"id":96,"nick":"央金wechat","avatar":"1497858658890.jpg","brief":"乖哦，毕竟已经是个100多斤的大人了！","wechatId":"oYvM*****-l3I","qqId":"","mobile":"156****2161","city":"","sex":0,"password":"","createTime":1497858605000,"updateTime":1497941452000,"status":1,"lastLoginTime":1498210118000,"aim":1,"login":10,"follow":0,"beFollowed":4,"companyId":0,"registerIp":"","lastLoginIp":"119.167.117.194"}
     * account : {"accountId":45,"userId":96,"exp":2893,"balance":169.21,"aim":1514.8,"option":6.5,"score":0}
     * token :
     * taskInfo : {"totalTaskCount":4,"finishTaskCount":0}
     * grade : v3
     * payPassword :
     * lastSignInTime : 1497955231000
     * imtoken :
     */

    private UserBean user;
    private AccountBean account;
    private String token;
    private TaskInfoBean taskInfo;
    private String grade;
    private String payPassword;
    private String imtoken;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }


    public String getImtoken() {
        return imtoken;
    }

    public void setImtoken(String imtoken) {
        this.imtoken = imtoken;
    }

    public static class UserBean {
        /**
         * id : 96
         * nick : 央金wechat
         * avatar : 1497858658890.jpg
         * brief : 乖哦，毕竟已经是个100多斤的大人了！
         * wechatId : oYvM*****-l3I
         * qqId :
         * mobile : 156****2161
         * city :
         * sex : 0
         * password :
         * createTime : 1497858605000
         * updateTime : 1497941452000
         * status : 1
         * lastLoginTime : 1498210118000
         * aim : 1
         * login : 10
         * follow : 0
         * beFollowed : 4
         * companyId : 0
         * registerIp :
         * lastLoginIp : 119.167.117.194
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
        private String password;
        private long createTime;
        private long updateTime;
        private double status;
        private long lastLoginTime;
        private double aim;
        private double login;
        private double follow;
        private double beFollowed;
        private double companyId;
        private String registerIp;
        private String lastLoginIp;

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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public double getStatus() {
            return status;
        }

        public void setStatus(double status) {
            this.status = status;
        }

        public long getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public double getAim() {
            return aim;
        }

        public void setAim(double aim) {
            this.aim = aim;
        }

        public double getLogin() {
            return login;
        }

        public void setLogin(double login) {
            this.login = login;
        }

        public double getFollow() {
            return follow;
        }

        public void setFollow(double follow) {
            this.follow = follow;
        }

        public double getBeFollowed() {
            return beFollowed;
        }

        public void setBeFollowed(double beFollowed) {
            this.beFollowed = beFollowed;
        }

        public double getCompanyId() {
            return companyId;
        }

        public void setCompanyId(double companyId) {
            this.companyId = companyId;
        }

        public String getRegisterIp() {
            return registerIp;
        }

        public void setRegisterIp(String registerIp) {
            this.registerIp = registerIp;
        }

        public String getLastLoginIp() {
            return lastLoginIp;
        }

        public void setLastLoginIp(String lastLoginIp) {
            this.lastLoginIp = lastLoginIp;
        }
    }

    public static class AccountBean {
        /**
         * accountId : 45
         * userId : 96
         * exp : 2893
         * balance : 169.21
         * aim : 1514.8
         * option : 6.5
         * score : 0
         */

        private int accountId;
        private int userId;
        private int exp;
        private double balance;
        private double aim;
        private double option;
        private double score;
        private String lastExecuteTime = "";

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

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getAim() {
            return aim;
        }

        public void setAim(double aim) {
            this.aim = aim;
        }

        public double getOption() {
            return option;
        }

        public void setOption(double option) {
            this.option = option;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getLastExecuteTime() {
            return lastExecuteTime;
        }

        public void setLastExecuteTime(String lastExecuteTime) {
            this.lastExecuteTime = lastExecuteTime;
        }
    }

    public static class TaskInfoBean {
        /**
         * totalTaskCount : 4
         * finishTaskCount : 0
         */

        private double totalTaskCount;
        private double finishTaskCount;

        public double getTotalTaskCount() {
            return totalTaskCount;
        }

        public void setTotalTaskCount(double totalTaskCount) {
            this.totalTaskCount = totalTaskCount;
        }

        public double getFinishTaskCount() {
            return finishTaskCount;
        }

        public void setFinishTaskCount(double finishTaskCount) {
            this.finishTaskCount = finishTaskCount;
        }
    }
}
