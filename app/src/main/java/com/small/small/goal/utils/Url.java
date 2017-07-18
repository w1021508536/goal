package com.small.small.goal.utils;

/**
 * Created by Administrator on 2017/5/23.
 */

public class Url {


    //    public static String Url = "http://47.92.79.222:8081";
    public static String Url = "http://api1.smallaim.cn";
    public static String PhotoUrl = "http://img.smallaim.cn";
    public static final String SMALL_PHOTO_URL = "?x-oss-process=image/resize,m_lfit,h_200,w_200";
    public static final String SMALL_PHOTO_URL2 = "?x-oss-process=image/resize,m_lfit,h_400,w_400";
    public static final String SMALL_PHOTO_URL_600 = "?x-oss-process=image/resize,m_lfit,h_600,w_600";

    public static String GetCode = "/sms/verifycode/send";
    public static String Register = "/user/register";
    public static String Login = "/user/login";
    public static String ForgetPassword = "/user/password/reset";//重置密码

    public static String UpPicture = "/file/picture";//上传图片
    public static String UserMy = "/user/my";//用户个人信息

    //user
    public static String UserRecommend = "/user/recommend";// 推荐的用户

    //aim
    public static String Aim = "/aim";// put 访问  新建目标   get 访问  aimId参数为详情  userId参数为列表
    public static String AimDynamic = "/aim/dynamic";//新建动态或创建目标
    public static String AimTransfer = "/aim/transfer";

    public static String AimDynamicComment = "/aim/dynamic/comment";//评论动态

    //search
    public static String HotSearch = "/aim/discover";//获取热门发现
    public static String CitySearch = "/aim/city";//获取同城发现
    public static String FollowedSearch = "/aim/followed";//获取关注发现

    public static String SearchAim = "/aim/search";//发现----搜索目标


    public static String AimSupport = "/aim/support";//支持目标
    public static String PayBalance = "/pay/balance";//余额支付


    //Message
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


    //关注
    public static String Follow = "/user/follow";//关注
    public static String FollowedList = "/user/followed";//我的关注列表
    public static String BeFollowedList = "/user/befollowed"; //关注我的列表

    public static String Vote = "/vote/vote";//点赞


    //红包
    public static String TestRedSet = "/test/red/set";//发红包
    public static String RedpacketDraw = "/redpacket/draw";//抢红包
    public static String RedpacketRecord = "/redpacket/record";//获取指定红包领取记录

    public static String Redpacket = "/redpacket";//用户的红包
    public static String RedpacketUnraw = "/redpacket/undraw";//用户未领取的红包
    public static String RedpacketDynamic = "/redpacket/dynamic";//动态/助力红包
    public static String TestRedThanksSet = "/test/red/thanks/set";//设置感谢红包
    public static String RedpacketThanksDraw = "/redpacket/thanks/draw";//领取感谢红包

    public static String RedGet = "/redpacket/income/draw";//获取收益红包


    public static String AimDynamicUser = "/aim/dynamic/user";//用户的动态
    public static String UserBase = "/user/base";//用户基础信息

    public static String AgentDistribution = "/agent/distribution";//分销收益
    public static String Agent = "/agent";//下级代理

    public static String AdList = "/ad";//广告列表

    public static String AccountCash = "/account/cash";//提现

}
