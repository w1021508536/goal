package com.small.small.goal.my.guess.elevenchoosefive.entity;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/6 11:37
 * 修改：
 * 描述： 最新开奖的实体
 **/
public class NewsResultEntity {


    /**
     * expect : 2017070658
     * openCode : 11,08,02,10,07
     * openTime : 2017-07-06 18:07:06
     * openTimestamp : 1499335626
     * expireTime : 2017-07-06 18:16:06
     * code : sd11x5
     */

    private String expect;
    private String openCode;
    private String openTime;
    private double openTimestamp;
    private String expireTime;
    private String code;

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getOpenCode() {
        return openCode;
    }

    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public double getOpenTimestamp() {
        return openTimestamp;
    }

    public void setOpenTimestamp(double openTimestamp) {
        this.openTimestamp = openTimestamp;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
