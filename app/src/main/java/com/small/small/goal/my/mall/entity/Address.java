package com.small.small.goal.my.mall.entity;

import java.io.Serializable;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/13 16:44
 * 修改：
 * 描述：地址的实体
 **/
public class Address implements Serializable {


    /**
     * id : 4
     * userId : 3
     * consignee : 雨滴
     * mobile : 18678989876
     * province : 山东
     * city : 青岛
     * address : 卓越世纪中心
     * isDefault : 1
     * district : 市北区
     */

    private int id;
    private String userId;
    private String consignee;
    private String mobile;
    private String province;
    private String city;
    private String address;
    private int isDefault;
    private String district;

    public Address(int id, String userId, String consignee, String mobile, String province, String city, String address, int isDefault, String district) {
        this.id = id;
        this.userId = userId;
        this.consignee = consignee;
        this.mobile = mobile;
        this.province = province;
        this.city = city;
        this.address = address;
        this.isDefault = isDefault;
        this.district = district;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
