package com.small.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/7 18:35
 * 修改：
 * 描述：  每日任务包含listview 的多布局
 **/
public class EveryTaskAdapterEntity {

    private int userTaskId;
    private int rewardType;
    private String action= "";
    private int taskType;
    private int reward;
    private int times;
    private int status;
    private long updateTime;
    private String name= "";
    private int upperLimit;
    private int finish;
    private int type;  //是否是title adater中
    private String titleName= "";

    public EveryTaskAdapterEntity(int userTaskId, int rewardType, String action, int taskType, int reward, int times, int status, long updateTime, String name, int upperLimit, int finish, int type, String titleName) {
        this.userTaskId = userTaskId;
        this.rewardType = rewardType;
        this.action = action;
        this.taskType = taskType;
        this.reward = reward;
        this.times = times;
        this.status = status;
        this.updateTime = updateTime;
        this.name = name;
        this.upperLimit = upperLimit;
        this.finish = finish;
        this.type = type;
        this.titleName = titleName;
    }

    public EveryTaskAdapterEntity(int type, String titleName) {
        this.type = type;
        this.titleName = titleName;
    }

    public EveryTaskAdapterEntity(int userTaskId, int rewardType, String action, int taskType, int reward, int times, int status, long updateTime, String name, int upperLimit, int finish, int type) {
        this.userTaskId = userTaskId;
        this.rewardType = rewardType;
        this.action = action;
        this.taskType = taskType;
        this.reward = reward;
        this.times = times;
        this.status = status;
        this.updateTime = updateTime;
        this.name = name;
        this.upperLimit = upperLimit;
        this.finish = finish;
        this.type = type;
    }

    public int getUserTaskId() {
        return userTaskId;
    }

    public void setUserTaskId(int userTaskId) {
        this.userTaskId = userTaskId;
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}
