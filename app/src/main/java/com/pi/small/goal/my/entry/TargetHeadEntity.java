package com.pi.small.goal.my.entry;

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
     * aim : {"id":3,"name":"我的第二个目标","budget":1000,"money":0,"cycle":6,"current":0,"userId":26,"province":"山东","city":"青岛","brief":"实现梦想","position":"卓越","longitude":0,"latitude":0,"support":0,"createTime":1495002021000,"status":1}
     * user : {"id":26,"nick":"雨滴","avatar":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2097810229,3516784541&fm=11&gp=0.jpg","brief":"","city":"青岛","sex":0}
     * count : 1
     * supports : [{"id":1,"aimId":3,"userId":26,"money":1,"message":"","createTime":1495167325000,"nick":"雨滴","avatar":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2097810229,3516784541&fm=11&gp=0.jpg"}]
     */

    private AimBean aim;
    private UserBean user;
    private int count;
    private List<SupportsBean> supports;

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

    public List<SupportsBean> getSupports() {
        return supports;
    }

    public void setSupports(List<SupportsBean> supports) {
        this.supports = supports;
    }

    public static class AimBean {
        /**
         * id : 3
         * name : 我的第二个目标
         * budget : 1000
         * money : 0
         * cycle : 6
         * current : 0
         * userId : 26
         * province : 山东
         * city : 青岛
         * brief : 实现梦想
         * position : 卓越
         * longitude : 0
         * latitude : 0
         * support : 0
         * createTime : 1495002021000
         * status : 1
         */

        private int id;
        private String name;
        private int budget;
        private int money;
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

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
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
    }

    public static class UserBean {
        /**
         * id : 26
         * nick : 雨滴
         * avatar : https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2097810229,3516784541&fm=11&gp=0.jpg
         * brief :
         * city : 青岛
         * sex : 0
         */

        private int id;
        private String nick;
        private String avatar;
        private String brief;
        private String city;
        private int sex;

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
    }

    public static class SupportsBean {
        /**
         * id : 1
         * aimId : 3
         * userId : 26
         * money : 1
         * message :
         * createTime : 1495167325000
         * nick : 雨滴
         * avatar : https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2097810229,3516784541&fm=11&gp=0.jpg
         */

        private int id;
        private int aimId;
        private int userId;
        private int money;
        private String message;
        private long createTime;
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
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
