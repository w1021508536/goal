package com.pi.small.goal.utils;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/5 10:42
 * 修改：
 * 描述：换村   单例
 **/
public class CacheUtil {


    private static CacheUtil cacheUtil = null;

    private CacheUtil() {
        // Exists only to defeat instantiation.
    }

    public static CacheUtil getInstance() {
        if (cacheUtil == null) {
            cacheUtil = new CacheUtil();
        }
        return cacheUtil;
    }

    public String newPassWord = "";

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }
}
