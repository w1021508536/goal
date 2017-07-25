package com.small.small.goal.my.guess.elevenchoosefive.entity;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/28 16:23
 * 修改：
 * 描述：
 **/
public class HlvEntity {

    public String content = "";
    public boolean selected = false;
    public int min = 0;  //最小的选择数量
    public long winNums = 0;  //赢得的奖励数;

    public HlvEntity(String content, boolean selected, int min, long winNums) {
        this.content = content;
        this.selected = selected;
        this.min = min;
        this.winNums = winNums;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public long getWinNums() {
        return winNums;
    }

    public void setWinNums(long winNums) {
        this.winNums = winNums;
    }
}
