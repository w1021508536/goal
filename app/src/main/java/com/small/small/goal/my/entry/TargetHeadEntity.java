package com.small.small.goal.my.entry;

import java.util.List;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/6 18:06
 * 修改：
 * 描述：
 **/
public class TargetHeadEntity {

    /**
     * aim : {"id":18,"name":"买双鞋","budget":6000,"money":151.09,"cycle":1,"current":20,"userId":27,"province":"山东省","city":"青岛市","brief":"买双鞋子","position":"万锦律师","longitude":0,"latitude":0,"support":0,"createTime":1495527450000,"status":1,"img":"1497234921125.jpg","transfer":0,"isFinish":0}
     * user : {"id":27,"nick":"1234","avatar":"1496802195021.jpg","brief":"12345678","city":"","sex":0,"status":1,"aim":0,"login":0,"follow":2,"beFollowed":7}
     * count : 5
     * supports : [{"id":124,"aimId":18,"dynamicId":65,"fromUserId":63,"money":30,"message":"","createTime":1497342023000,"isPaid":1,"toUserId":27,"remainMoney":27,"drew":1,"nick":"曈","avatar":"1497344401703.jpg"},{"id":123,"aimId":18,"dynamicId":65,"fromUserId":59,"money":1,"message":"","createTime":1497341655000,"isPaid":1,"toUserId":27,"remainMoney":0.9,"drew":1,"nick":"云鹏","avatar":"http://wx.qlogo.cn/mmopen/DibTKUUzk82tX46rsqOodMiciadWSqtVW040sdjPiaMQoyo2PbEy24QcEia1nIH4zSYd8dqmHaWPhDEcgRxTVEGJRUlGPWzVhPgE6/0"},{"id":79,"aimId":18,"dynamicId":65,"fromUserId":43,"money":1,"message":"","createTime":1497246001000,"isPaid":1,"toUserId":27,"remainMoney":1,"drew":1,"nick":"13693361208","avatar":""},{"id":78,"aimId":18,"dynamicId":65,"fromUserId":43,"money":1,"message":"","createTime":1497245884000,"isPaid":1,"toUserId":27,"remainMoney":1,"drew":1,"nick":"13693361208","avatar":""},{"id":75,"aimId":18,"dynamicId":65,"fromUserId":43,"money":1,"message":"","createTime":1497244830000,"isPaid":1,"toUserId":27,"remainMoney":1,"drew":1,"nick":"13693361208","avatar":""}]
     * haveCollect : 0
     */

    private AimBean aim;
    private UserBean user;
    private int count;
    private List<SupportsBean> supports;
    private int haveCollect;

    public AimBean getAim() {
        return aim;
    }

    public void setAim(AimBean aim) {
        this.aim = aim;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getHaveCollect() {
        return haveCollect;
    }

    public void setHaveCollect(int haveCollect) {
        this.haveCollect = haveCollect;
    }

    public List<SupportsBean> getSupports() {
        return supports;
    }

    public void setSupports(List<SupportsBean> supports) {
        this.supports = supports;
    }

    public static class AimBean {
        /**
         * id : 18
         * name : 买双鞋
         * budget : 6000
         * money : 151.09
         * cycle : 1
         * current : 20
         * userId : 27
         * province : 山东省
         * city : 青岛市
         * brief : 买双鞋子
         * position : 万锦律师
         * longitude : 0
         * latitude : 0
         * support : 0
         * createTime : 1495527450000
         * status : 1
         * img : 1497234921125.jpg
         * transfer : 0
         * isFinish : 0
         */

        private int id;
        private String name;
        private int budget;
        private double money;
        private int cycle;
        private int current;
        private int userId;
        private String province;
        private String city;
        private String brief;
        private String position;
        private int longitude;
        private int latitude;
        private int support;
        private long createTime;
        private int status;
        private String img;
        private int transfer;
        private int isFinish;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBudget() {
            return budget;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getCycle() {
            return cycle;
        }

        public void setCycle(int cycle) {
            this.cycle = cycle;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getLongitude() {
            return longitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }

        public int getLatitude() {
            return latitude;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
        }

        public int getSupport() {
            return support;
        }

        public void setSupport(int support) {
            this.support = support;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getTransfer() {
            return transfer;
        }

        public void setTransfer(int transfer) {
            this.transfer = transfer;
        }

        public int getIsFinish() {
            return isFinish;
        }

        public void setIsFinish(int isFinish) {
            this.isFinish = isFinish;
        }
    }

    public static class UserBean {
        /**
         * id : 27
         * nick : 1234
         * avatar : 1496802195021.jpg
         * brief : 12345678
         * city :
         * sex : 0
         * status : 1
         * aim : 0
         * login : 0
         * follow : 2
         * beFollowed : 7
         */

        private int id;
        private String nick;
        private String avatar;
        private String brief;
        private String city;
        private int sex;
        private int status;
        private int aim;
        private int login;
        private int follow;
        private int beFollowed;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAim() {
            return aim;
        }

        public void setAim(int aim) {
            this.aim = aim;
        }

        public int getLogin() {
            return login;
        }

        public void setLogin(int login) {
            this.login = login;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public int getBeFollowed() {
            return beFollowed;
        }

        public void setBeFollowed(int beFollowed) {
            this.beFollowed = beFollowed;
        }
    }

    public static class SupportsBean {
        /**
         * id : 124
         * aimId : 18
         * dynamicId : 65
         * fromUserId : 63
         * money : 30
         * message :
         * createTime : 1497342023000
         * isPaid : 1
         * toUserId : 27
         * remainMoney : 27
         * drew : 1
         * nick : 曈
         * avatar : 1497344401703.jpg
         */

        private int id;
        private int aimId;
        private int dynamicId;
        private int fromUserId;
        private double money;
        private String message;
        private long createTime;
        private int isPaid;
        private int toUserId;
        private double remainMoney;
        private int drew;
        private String nick;
        private String avatar;

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

        public int getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(int dynamicId) {
            this.dynamicId = dynamicId;
        }

        public int getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(int fromUserId) {
            this.fromUserId = fromUserId;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getIsPaid() {
            return isPaid;
        }

        public void setIsPaid(int isPaid) {
            this.isPaid = isPaid;
        }

        public int getToUserId() {
            return toUserId;
        }

        public void setToUserId(int toUserId) {
            this.toUserId = toUserId;
        }

        public double getRemainMoney() {
            return remainMoney;
        }

        public void setRemainMoney(double remainMoney) {
            this.remainMoney = remainMoney;
        }

        public int getDrew() {
            return drew;
        }

        public void setDrew(int drew) {
            this.drew = drew;
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
}
