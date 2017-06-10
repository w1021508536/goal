package com.pi.small.goal.my.entry;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/7 17:45
 * 修改：
 * 描述：
 **/
public class EveryTaskGsonEntity {


    /**
     * userTaskId : 1
     * rewardType : 1
     * action : user_login
     * taskType : 1
     * reward : 60
     * times : 1
     * status : 1
     * updateTime : 1495588840000
     * name : 用户首次登录奖励
     * upperLimit : 0
     * finish : 0
     */

    private int userTaskId;
    private int rewardType;
    private String action;
    private int taskType;
    private int reward;
    private int times;
    private int status;
    private long updateTime;
    private String name;
    private int upperLimit;
    private int finish;

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
}
