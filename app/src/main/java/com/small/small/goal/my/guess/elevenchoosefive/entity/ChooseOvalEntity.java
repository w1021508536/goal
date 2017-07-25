package com.small.small.goal.my.guess.elevenchoosefive.entity;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/28 17:14
 * 修改：
 * 描述： 11选5的数字
 **/
public class ChooseOvalEntity {

    public String content = "";
    public boolean selected = false;  //是否选中
    public int missNums = 0;    //遗漏次数

    public ChooseOvalEntity(String content, boolean selected, int missNums) {
        this.content = content;
        this.selected = selected;
        this.missNums = missNums;
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

    public int getMissNums() {
        return missNums;
    }

    public void setMissNums(int missNums) {
        this.missNums = missNums;
    }
}
