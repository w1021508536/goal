package com.pi.small.goal.utils;

/**
 * Created by Administrator on 2017/5/23.
 */

public class Url {


    public static String Url = "http://47.92.79.222:8081";
    public static String PhotoUrl = "http://img.smallaim.cn";

    public static String GetCode = "/sms/verifycode/send";
    public static String Register = "/user/register";
    public static String Login = "/user/login";


    public static String LastMessage = "/push/message/last";

    public static String FriendsMessageList = "/push/message/contact";
    public static String FriendsMessageListClear = "/push/message/clear";
    public static String FriendsMessageListDelete = "/push/message/delete";
    public static String SystemMessageList = "/push/message/system";

    //搜索用户
    public static String SearchUser = "/user/search";
    public static String ReportUser = "/user/report";//投诉用户

    public static String FriendApply = "/friend/apply";//申请添加好友
    public static String FriendAgree = "/friend/agree";//同意好友申请
    public static String FriendList = "/friend/list";//好友列表
    public static String FriendRefuse = "/friend/refuse";//拒绝好友申请
    public static String FriendDelete = "/friend/delete";//删除好友
    public static String FriendBlackAdd = "/friend/black/add";//添加黑名单
    public static String FriendBlackDelete = "/friend/black/remove";// 移出黑名单
    public static String FriendEdit = "/friend";//编辑好友
}
