package com.small.small.goal.my.guess.twoColorBall;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/28 11:49
 * 修改：
 * 描述：中奖的大乐透的数字
 **/
public class WinDaletouNumberEntity {

    public String qiNums = "";
    public List<Integer> reds = new ArrayList<>();
    public List<Integer> blues = new ArrayList<>();


    public WinDaletouNumberEntity(String qiNums, List<Integer> reds, List<Integer> blues) {
        this.qiNums = qiNums;
        this.reds = reds;
        this.blues = blues;
    }

    public String getQiNums() {
        return qiNums;
    }

    public void setQiNums(String qiNums) {
        this.qiNums = qiNums;
    }

    public List<Integer> getReds() {
        return reds;
    }

    public void setReds(List<Integer> reds) {
        this.reds = reds;
    }

    public List<Integer> getBlues() {
        return blues;
    }

    public void setBlues(List<Integer> blues) {
        this.blues = blues;
    }
}
