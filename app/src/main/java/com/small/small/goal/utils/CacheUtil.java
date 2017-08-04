package com.small.small.goal.utils;


import com.small.small.goal.my.entry.DynamicEntity;
import com.small.small.goal.my.entry.EveryTaskGsonEntity;
import com.small.small.goal.my.entry.TargetHeadEntity;
import com.small.small.goal.my.entry.UerEntity;
import com.small.small.goal.my.guess.elevenchoosefive.entity.ChooseOvalEntity;
import com.small.small.goal.my.guess.twoColorBall.OvalEntity;
import com.small.small.goal.my.mall.entity.Address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public void setClear() {
        userInfo = null;
        oldPass = "";
        newPass = "";
        setPass = "";
        forgetPassCode = "";   //重置密码时候的验证短信吗
        signFlag = false;
        supportEntityList.clear();
        commentsBeanList.clear();
        taskToMainFlag = false;
    }

    public UerEntity userInfo;

    public String oldPass = "";
    public String newPass = "";
    public String setPass = "";
    public String forgetPassCode = "";   //重置密码时候的验证短信吗

    public boolean signFlag = false;

    public List<TargetHeadEntity.SupportsBean> supportEntityList = new ArrayList<>();

    public List<DynamicEntity.CommentsBean> commentsBeanList = new ArrayList<>();
    public boolean taskToMainFlag = false;
    public boolean taskAddMoneyToMainFlag = false;
    public boolean taskQiQuan = false;

    public boolean isTaskQiQuan() {
        return taskQiQuan;
    }

    public void setTaskQiQuan(boolean taskQiQuan) {
        this.taskQiQuan = taskQiQuan;
    }

    public Map<String, Boolean> map = new HashMap<>();

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

    public boolean isTaskToMainFlag() {
        return taskToMainFlag;
    }

    public void setTaskToMainFlag(boolean taskToMainFlag) {
        this.taskToMainFlag = taskToMainFlag;
    }

    public boolean isTaskAddMoneyToMainFlag() {
        return taskAddMoneyToMainFlag;
    }

    public void setTaskAddMoneyToMainFlag(boolean taskAddMoneyToMainFlag) {
        this.taskAddMoneyToMainFlag = taskAddMoneyToMainFlag;
    }

    public Map<String, Boolean> getMap() {
        return map;
    }

    public void setMap(Map<String, Boolean> map) {
        this.map = map;
    }


    public List<Map<Integer, List<ChooseOvalEntity>>> elevenChooseFive = new ArrayList<>();  //选择的11选5数据  //Integer 对应:前一，任二，任三，任四。。。。 list 是具体号码

    public List<Map<Integer, List<ChooseOvalEntity>>> getElevenChooseFive() {
        return elevenChooseFive;
    }

    public void addElevenChooseFive(Map<Integer, List<ChooseOvalEntity>> one) {
        elevenChooseFive.add(one);
    }

    public void closeElevenChooseFive() {
        elevenChooseFive.clear();
    }


    public List<EveryTaskGsonEntity> everyTaskGsonEntityList = new ArrayList<>();

    public List<EveryTaskGsonEntity> getEveryTaskGsonEntityList() {
        return everyTaskGsonEntityList;
    }

    public void setEveryTaskGsonEntityList(List<EveryTaskGsonEntity> everyTaskGsonEntityList) {
        this.everyTaskGsonEntityList = everyTaskGsonEntityList;
    }

    public List<Map<String, List<OvalEntity>>> selectedDaLeTouData = new ArrayList<>();  //选择的大乐透的红蓝数据  string:red  blue  list:红，蓝球的数字数据
    public List<OvalEntity> selectedDaLeTouRedData = new ArrayList<>();    //选择的大乐透的红球数据
    public List<OvalEntity> selectedDaLeTouBlueData = new ArrayList<>();

    public List<Map<String, List<OvalEntity>>> selectedShuangseqiuData = new ArrayList<>();  //选择的双色球的红蓝数据
    public List<OvalEntity> selectedShuangseqiuRedData = new ArrayList<>();    //选择的双色球的红球数据
    public List<OvalEntity> selectedShuangseqiuBlueData = new ArrayList<>();

    public List<OvalEntity> getSelectedDaLeTouBlueData() {
        return selectedDaLeTouBlueData;
    }

    public void setSelectedDaLeTouBlueData(List<OvalEntity> selectedDaLeTouBlueData) {
        this.selectedDaLeTouBlueData = selectedDaLeTouBlueData;
    }

    public void closeSelectedDaLeTouBlueData() {
        selectedDaLeTouBlueData.clear();
    }

    public List<OvalEntity> getSelectedShuangseqiuBlueData() {
        return selectedShuangseqiuBlueData;
    }

    public void setSelectedShuangseqiuBlueData(List<OvalEntity> selectedShuangseqiuBlueData) {
        this.selectedShuangseqiuBlueData = selectedShuangseqiuBlueData;
    }

    public void setSelectedDaLeTouRedData(List<OvalEntity> selectedDaLeTouRedData) {
        this.selectedDaLeTouRedData = selectedDaLeTouRedData;
    }

    public List<OvalEntity> getSelectedShuangseqiuRedData() {
        return selectedShuangseqiuRedData;
    }

    public void setSelectedShuangseqiuRedData(List<OvalEntity> selectedShuangseqiuRedData) {
        this.selectedShuangseqiuRedData = selectedShuangseqiuRedData;
    }

    public List<OvalEntity> getSelectedDaLeTouRedData() {
        return selectedDaLeTouRedData;
    }

    public void addSelectedShuangseqiuData(Map<String, List<OvalEntity>> one) {
        selectedShuangseqiuData.add(one);
    }

    public void closeSelectedShuangseqiuData() {
        selectedShuangseqiuData.clear();
    }

    public void addSelectedDaLeTouData(Map<String, List<OvalEntity>> selectedDaLeTouData) {
        this.selectedDaLeTouData.add(selectedDaLeTouData);
    }

    public void closeSelectedDaLeTouData() {
        selectedDaLeTouData.clear();
    }

    public List<Map<String, List<OvalEntity>>> getSelectedShuangseqiuData() {
        return selectedShuangseqiuData;
    }

    public void closeSelectedDaLeTouRedData() {
        selectedDaLeTouRedData.clear();
    }

    public void closeSelectedShuangseqiuRedData() {
        selectedShuangseqiuBlueData.clear();
    }

    public void closeSelectedShuangseqiuBlueData() {
        selectedShuangseqiuBlueData.clear();
    }


    public List<Address> addressData = new ArrayList<>();

    public List<Address> getAddressData() {
        return addressData;
    }

    public void addAddressData(Address oneAddressData) {
        if (oneAddressData.getIsDefault() == 1) {
            for (int y = 0; y < addressData.size(); y++) {
                addressData.get(y).setIsDefault(0);
            }
        }
        this.addressData.add(oneAddressData);
        setHaveDefaultAddress(true);
    }

    public void setAddressData(List<Address> addressData) {
        this.addressData = addressData;
    }

    public void deleteOneAddressData(int position) {
        if (addressData.get(position).getIsDefault() == 1) {
            addressData.remove(position);
            if (addressData.size() > 0) {
                addressData.get(0).setIsDefault(1);
            }
        } else {
            addressData.remove(position);
        }
        if (addressData.size() == 0) {
            setHaveDefaultAddress(false);
        }
    }

    public boolean haveDefaultAddress = false;      //是否有默认地址

    public boolean isHaveDefaultAddress() {
        return haveDefaultAddress;
    }

    public void setHaveDefaultAddress(boolean haveDefaultAddress) {
        this.haveDefaultAddress = haveDefaultAddress;
    }
}
