package com.small.small.goal.my.mall.entity;

import java.io.Serializable;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/7 10:30
 * 修改：
 * 描述：
 **/
public class LuckEntity implements Serializable {


    /**
     * id : 1
     * name : 苹果iphone6 32G
     * img : http://a.hiphotos.baidu.com/baike/s%3D220/sign=c959067fdf2a60595610e6181835342d/3801213fb80e7becccb1e957272eb9389b506b19.jpg
     * type : 1
     * price : 600
     * status : 1
     * content : 这款iPhone6设计图出自莱恩-安妮（Ran Avni）和西蒙-伊恩文吉斯塔（Simone Evangelista）之手。该机所采用的是4.7寸屏幕、搭载A8处理器、内置1GB内存，并配备了1810mAh电池。而且，该设备的整机厚度仅为6.5毫米。[5]
     * 三屏
     * 由设计师ISKlander Utebayev创作的这款iPhone6设计图完全取消了原本的实体按键设计，并以数字虚拟按钮的形式进行了替代，科技感非常足，而且整体上看效果也是
     * iphone 6
     * iphone 6(4张)
     * 非常酷炫的。被列入果粉最爱的iPhone6概念机设计列单中也是十分够格的。
     * stores : 0
     * sales : 0
     * isNew : 0
     * isHot : 0
     * createTime : 2017-07-10 13:08:57
     * updateTime : 2017-07-10 15:37:58
     */

    private int id;
    private String name;
    private String img;
    private int type;
    private int price;
    private int status;
    private String content;
    private int stores;     //剩余个数
    private int sales;
    private int isNew;
    private int isHot;
    private String createTime;
    private String updateTime;

    public LuckEntity(int id, String name, String img, int type, int price, int status, String content, int stores, int sales, int isNew, int isHot, String createTime, String updateTime) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.type = type;
        this.price = price;
        this.status = status;
        this.content = content;
        this.stores = stores;
        this.sales = sales;
        this.isNew = isNew;
        this.isHot = isHot;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStores() {
        return stores;
    }

    public void setStores(int stores) {
        this.stores = stores;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
