package com.small.small.goal.my.guess.fastthree.empty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JS on 2017-07-04.
 */

public class FastThreeEmpty implements Serializable {
    private int status;

    private List<String> sumList;

    private List<String> twoSingleList;
    private List<String> twoSameList;
    private List<String> twoCheckList;

    private List<String> twoNotList;

    private List<String> threeNotList;
    private List<String> threeNotEvenList;//三连号通选

    private List<String> threeEvenList;//三同号通选
    private List<String> threeSingleList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getSumList() {
        return sumList;
    }

    public void setSumList(List<String> sumList) {
        this.sumList = sumList;
    }

    public List<String> getTwoSingleList() {
        return twoSingleList;
    }

    public void setTwoSingleList(List<String> twoSingleList) {
        this.twoSingleList = twoSingleList;
    }

    public List<String> getTwoSameList() {
        return twoSameList;
    }

    public void setTwoSameList(List<String> twoSameList) {
        this.twoSameList = twoSameList;
    }

    public List<String> getTwoCheckList() {
        return twoCheckList;
    }

    public void setTwoCheckList(List<String> twoCheckList) {
        this.twoCheckList = twoCheckList;
    }

    public List<String> getTwoNotList() {
        return twoNotList;
    }

    public void setTwoNotList(List<String> twoNotList) {
        this.twoNotList = twoNotList;
    }

    public List<String> getThreeNotList() {
        return threeNotList;
    }

    public void setThreeNotList(List<String> threeNotList) {
        this.threeNotList = threeNotList;
    }

    public List<String> getThreeNotEvenList() {
        return threeNotEvenList;
    }

    public void setThreeNotEvenList(List<String> threeNotEvenList) {
        this.threeNotEvenList = threeNotEvenList;
    }

    public List<String> getThreeEvenList() {
        return threeEvenList;
    }

    public void setThreeEvenList(List<String> threeEvenList) {
        this.threeEvenList = threeEvenList;
    }

    public List<String> getThreeSingleList() {
        return threeSingleList;
    }

    public void setThreeSingleList(List<String> threeSingleList) {
        this.threeSingleList = threeSingleList;
    }
}
