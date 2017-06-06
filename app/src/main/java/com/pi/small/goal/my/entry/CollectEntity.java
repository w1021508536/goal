package com.pi.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/3 10:17
 * 修改：
 * 描述： 收藏的实体o
 **/
public class CollectEntity {

    // {"id":5,"name":"我的第二个目标","budget":1000,"money":0,"cycle":6,"current":0,"userId":11,"province":"山东","city":"青岛","brief":"实现梦想","position":"卓越","longitude":0.000000,"latitude":0.000000,"support":0,"createTime":1494974232000,"status":1,"img":""}

    private int id;
    private String name;
    private float budget;   //预算
    private int money;
    private int cycle;   //周期
    private int current;  //现在的
    private int userId;
    private String province;  //省
    private String city;
    private String brief;                                                              //1496458648242
    //"position":"卓越","longitude":0.000000,"latitude":0.000000,"support":0,"createTime":1494974232000,"status":1,"img":""
    private String position;
    private float longitude;
    private float latitude;
    private int support;   //支持人数
    private long createTime;
    private int status;
    private String img;

    public CollectEntity() {
    }

    public CollectEntity(int id, String name, float budget, int money, int cycle, int current, int userId, String province, String city, String brief, String position, float longitude, float latitude, int support, long createTime, int status, String img) {
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.money = money;
        this.cycle = cycle;
        this.current = current;
        this.userId = userId;
        this.province = province;
        this.city = city;
        this.brief = brief;
        this.position = position;
        this.longitude = longitude;
        this.latitude = latitude;
        this.support = support;
        this.createTime = createTime;
        this.status = status;
        this.img = img;
    }

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

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
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

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
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
}
