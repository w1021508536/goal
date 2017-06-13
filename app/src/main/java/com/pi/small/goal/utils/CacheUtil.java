package com.pi.small.goal.utils;


import com.pi.small.goal.my.entry.DynamicEntity;
import com.pi.small.goal.my.entry.TargetHeadEntity;
import com.pi.small.goal.my.entry.UerEntity;

import java.util.List;

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

    public UerEntity userInfo;

    public String oldPass = "";
    public String newPass = "";
    public String setPass = "";
    public String forgetPassCode = "";   //重置密码时候的验证短信吗

    public boolean signFlag = false;

    public List<TargetHeadEntity.SupportsBean> supportEntityList;

    public List<DynamicEntity.CommentsBean> commentsBeanList;

    public boolean isSignFlag() {
        return signFlag;
    }

    public void setSignFlag(boolean signFlag) {
        this.signFlag = signFlag;
    }

    public UerEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UerEntity userInfo) {
        this.userInfo = userInfo;
    }


    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public List<TargetHeadEntity.SupportsBean> getSupportEntityList() {
        return supportEntityList;
    }

    public void setSupportEntityList(List<TargetHeadEntity.SupportsBean> supportEntityList) {
        this.supportEntityList = supportEntityList;
    }

    public List<DynamicEntity.CommentsBean> getCommentsBeanList() {
        return commentsBeanList;
    }

    public void setCommentsBeanList(List<DynamicEntity.CommentsBean> commentsBeanList) {
        this.commentsBeanList = commentsBeanList;
    }

    public String getSetPass() {
        return setPass;
    }

    public void setSetPass(String setPass) {
        this.setPass = setPass;
    }

    public String getForgetPassCode() {
        return forgetPassCode;
    }

    public void setForgetPassCode(String forgetPassCode) {
        this.forgetPassCode = forgetPassCode;
    }
}
