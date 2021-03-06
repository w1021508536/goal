package com.small.small.goal.my;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.small.small.goal.MainActivity;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.login.LoginActivity;
import com.small.small.goal.my.activity.AimActivity;
import com.small.small.goal.my.activity.CollectActivity;
import com.small.small.goal.my.activity.DistributionActivity;
import com.small.small.goal.my.activity.ExtensionActivity;
import com.small.small.goal.my.activity.FollowActivity;
import com.small.small.goal.my.activity.LevelActivity;
import com.small.small.goal.my.activity.RedActivity;
import com.small.small.goal.my.activity.RenameActivity;
import com.small.small.goal.my.activity.SettingActivity;
import com.small.small.goal.my.activity.SignActivity;
import com.small.small.goal.my.activity.TaskActivity;
import com.small.small.goal.my.transfer.activity.TransferActivity;
import com.small.small.goal.my.activity.UserInfoActivity;
import com.small.small.goal.my.entry.UerEntity;
import com.small.small.goal.my.gold.GoldListActivity;
import com.small.small.goal.my.guess.elevenchoosefive.activity.ChooseMainActivity;
import com.small.small.goal.my.guess.fastthree.FastThreeActivity;
import com.small.small.goal.my.guess.football.FootBallActivity;
import com.small.small.goal.my.guess.twoColorBall.TwoColorBallActivity;
import com.small.small.goal.my.mall.activity.MallActivity;
import com.small.small.goal.my.wallet.WalletActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener {


    @InjectView(R.id.icon_fragment)
    CircleImageView iconFragment;
    @InjectView(R.id.tv_level_fragment)
    TextView tvLevelFragment;
    @InjectView(R.id.img_grade_fragment)
    CircleImageView imgGradeFragment;
    @InjectView(R.id.content_tv_fragment)
    TextView contentTvFragment;
    @InjectView(R.id.tv_option_fragment)
    TextView tvOptionFragment;
    @InjectView(R.id.tv_score_fragment)
    TextView tvScoreFragment;
    @InjectView(R.id.tv_taskNums_fragment)
    TextView tvTaskNumsFragment;
    @InjectView(R.id.ll_task_fragment)
    LinearLayout llTaskFragment;
    //    ll_transfer_fragment
    @InjectView(R.id.ll_transfer_fragment)
    LinearLayout llTransferFragment;
    @InjectView(R.id.top_rl)
    RelativeLayout topRl;
    @InjectView(R.id.ll_follow_fragment)
    RelativeLayout llFollowFragment;
    @InjectView(R.id.ll_collect_framgent)
    RelativeLayout llCollectFramgent;
    @InjectView(R.id.imageView)
    ImageView imageView;
    @InjectView(R.id.ll_target_fragment)
    RelativeLayout llTargetFragment;
    @InjectView(R.id.center_ll_fragment)
    LinearLayout centerLlFragment;
    @InjectView(R.id.line_view)
    View lineView;
    @InjectView(R.id.imageView2)
    ImageView imageView2;
    @InjectView(R.id.ll_wallet_fragment)
    LinearLayout llWallectFragment;
    @InjectView(R.id.ll_red_fragment)
    LinearLayout llRedFragment;
    @InjectView(R.id.ll_extension_fragment)
    LinearLayout llExtensionFragment;
    @InjectView(R.id.ll_illness_fragment)
    LinearLayout llIllnessFragment;
    @InjectView(R.id.ll_distribution_fragment)
    LinearLayout llDistributionFragment;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.ll_shopping_fragment)
    LinearLayout llShoppingFragment;

    @InjectView(R.id.distribution_layout)
    LinearLayout distributionLayout;
    @InjectView(R.id.football_layout)
    LinearLayout footballLayout;
    @InjectView(R.id.kuai3_layout)
    LinearLayout kuai3Layout;
    @InjectView(R.id.xuan5_layout)
    LinearLayout xuan5Layout;
    @InjectView(R.id.bean_layout)
    LinearLayout beanLayout;
    @InjectView(R.id.two_ball_layout)
    LinearLayout twoBallLayout;
    @InjectView(R.id.guess_layout)
    LinearLayout guessLayout;

    private UerEntity userInfo;
    private SharedPreferences sp;
    private UerEntity.AccountBean account;

    private String version_now;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.inject(this, view);
        initData();
        initWeight();
        return view;
    }

    private void initWeight() {
        iconFragment.setOnClickListener(this);
        llTaskFragment.setOnClickListener(this);
        llRedFragment.setOnClickListener(this);
        llFollowFragment.setOnClickListener(this);
        llTargetFragment.setOnClickListener(this);
        llCollectFramgent.setOnClickListener(this);
        llWallectFragment.setOnClickListener(this);
        rightImageInclude.setOnClickListener(this);
        tvLevelFragment.setOnClickListener(this);
        leftImageInclude.setOnClickListener(this);
        contentTvFragment.setOnClickListener(this);
        llTransferFragment.setOnClickListener(this);
        llShoppingFragment.setOnClickListener(this);
        llExtensionFragment.setOnClickListener(this);
        distributionLayout.setOnClickListener(this);

        beanLayout.setOnClickListener(this);
        kuai3Layout.setOnClickListener(this);
        footballLayout.setOnClickListener(this);
        xuan5Layout.setOnClickListener(this);
        twoBallLayout.setOnClickListener(this);
    }

    private void initData() {
        version_now = Utils.getVersion(getActivity());
        sp = Utils.UserSharedPreferences(getContext());

        Drawable drawable = getResources().getDrawable(R.mipmap.exp_icon);
        drawable.setBounds(0, 0, Utils.dip2px(getContext(), 13), Utils.dip2px(getContext(), 10));
        tvLevelFragment.setCompoundDrawables(drawable, null, null, null);

        // getData();

        userInfo = CacheUtil.getInstance().getUserInfo();
        if (userInfo == null) return;
        UerEntity.TaskInfoBean taskInfo = userInfo.getTaskInfo();
        getSign(taskInfo);

        if (MainActivity.version.equals("")) {

            guessLayout.setVisibility(View.GONE);
        } else {
            long now = Long.valueOf(version_now.replace(".", ""));
            long get = Long.valueOf(MainActivity.version.replace(".", ""));
            if (now < get) {
                guessLayout.setVisibility(View.VISIBLE);
            } else {
                if (MainActivity.isLottery.equals("on")) {
                    guessLayout.setVisibility(View.VISIBLE);
                } else {
                    guessLayout.setVisibility(View.GONE);
                }

            }

        }

    }


    private void setData() {
        userInfo = CacheUtil.getInstance().getUserInfo();
        if (userInfo == null) return;
        UerEntity.UserBean user = userInfo.getUser();
        account = userInfo.getAccount();
        UerEntity.TaskInfoBean taskInfo = userInfo.getTaskInfo();

        tvOptionFragment.setText(account.getOption() + "");

        if (Integer.valueOf(CacheUtil.getInstance().getUserInfo().getAccount().getBean()) > 9999) {
            String bean = CacheUtil.getInstance().getUserInfo().getAccount().getBean();
            tvScoreFragment.setText(bean.substring(0, bean.length() - 4) + "." + bean.substring(bean.length() - 4, bean.length() - 3) + "w");
        } else
            tvScoreFragment.setText(CacheUtil.getInstance().getUserInfo().getAccount().getBean());

        int exp = userInfo.getAccount().getExp();
        tvLevelFragment.setText("Exp " + userInfo.getAccount().getExp());

        switch (Integer.valueOf(userInfo.getGrade().replace("v", ""))) {
            case 1:
                imgGradeFragment.setImageResource(R.mipmap.icon_level_1);
                break;
            case 2:
                imgGradeFragment.setImageResource(R.mipmap.icon_level_2);
                break;
            case 3:
                imgGradeFragment.setImageResource(R.mipmap.icon_level_3);
                break;
            case 4:
                imgGradeFragment.setImageResource(R.mipmap.icon_level_4);
                break;
            case 5:
                imgGradeFragment.setImageResource(R.mipmap.icon_level_5);
                break;
            case 6:
                imgGradeFragment.setImageResource(R.mipmap.icon_level_6);
                break;
            case 7:
                imgGradeFragment.setImageResource(R.mipmap.icon_level_7);
                break;
            case 8:
                imgGradeFragment.setImageResource(R.mipmap.icon_level_8);
                break;
        }

    }

    private void getSign(final UerEntity.TaskInfoBean taskInfo) {
        RequestParams requestParams = new RequestParams();
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.setUri(Url.Url + "/signin");
        XUtil.get(requestParams, getContext(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!Utils.callOk(result, getActivity())) {
                    tvTaskNumsFragment.setText(0 + "/" + 5);
                    CacheUtil.getInstance().getUserInfo().getTaskInfo().setFinishTaskCount(CacheUtil.getInstance().getUserInfo().getTaskInfo().getFinishTaskCount());
                    CacheUtil.getInstance().getUserInfo().getTaskInfo().setTotalTaskCount(CacheUtil.getInstance().getUserInfo().getTaskInfo().getTotalTaskCount() + 1);
                    return;
                }
                try {
                    long signTime = (long) ((JSONObject) new JSONObject(result).get("result")).get("lastSignInTime");
//                    tvTaskNumsFragment.setText(((int) taskInfo.getFinishTaskCount()) + "/" + (int) (taskInfo.getTotalTaskCount() + 1));
                    tvTaskNumsFragment.setText(((int) taskInfo.getFinishTaskCount()) + "/5");
                    Date date = new Date(signTime);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
                    Date nowDate = new Date();
                    if (formatter.format(date).equals(formatter.format(nowDate))) {
                        CacheUtil.getInstance().setSignFlag(true);
                        CacheUtil.getInstance().getMap().put(KeyCode.AIM_SIGN, true);
                    }
                    if (CacheUtil.getInstance().isSignFlag()) {

                        CacheUtil.getInstance().getUserInfo().getTaskInfo().setFinishTaskCount(CacheUtil.getInstance().getUserInfo().getTaskInfo().getFinishTaskCount() + 1);

                    } else {
                    }
                    CacheUtil.getInstance().getUserInfo().getTaskInfo().setTotalTaskCount(CacheUtil.getInstance().getUserInfo().getTaskInfo().getTotalTaskCount() + 1);
//                    tvTaskNumsFragment.setText(((int) taskInfo.getFinishTaskCount()) + "/" + ((int) taskInfo.getTotalTaskCount()));
                    tvTaskNumsFragment.setText(((int) taskInfo.getFinishTaskCount()) + "/5");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //个人信息
            case R.id.content_tv_fragment:
            case R.id.icon_fragment:
                if ("".equals(sp.getString(KeyCode.USER_ID, ""))) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getContext(), UserInfoActivity.class));
                }
                break;
            //任务中心
            case R.id.ll_task_fragment:
                startActivity(new Intent(getContext(), TaskActivity.class));
                break;
            //红包
            case R.id.ll_red_fragment:
                startActivity(new Intent(getContext(), RedActivity.class));
                break;
            //我的关注
            case R.id.ll_follow_fragment:
                startActivity(new Intent(getContext(), FollowActivity.class));
                break;
            //我的目标
            case R.id.ll_target_fragment:
                startActivity(new Intent(getContext(), AimActivity.class));
                break;
            //我的收藏
            case R.id.ll_collect_framgent:
                startActivity(new Intent(getContext(), CollectActivity.class));
                break;
            //我的钱包
            case R.id.ll_wallet_fragment:
                startActivity(new Intent(getContext(), WalletActivity.class));
                break;
            //设置
            case R.id.right_image_include:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            //经验值
            case R.id.tv_level_fragment:
                startActivity(new Intent(getContext(), LevelActivity.class));
                break;
            //签到
            case R.id.left_image_include:
                startActivity(new Intent(getContext(), SignActivity.class));
                break;
            //期权转让
            case R.id.ll_transfer_fragment:
                startActivity(new Intent(getContext(), TransferActivity.class));
                break;
            //商城
            case R.id.ll_shopping_fragment:
//                Utils.showToast(getActivity(), "敬请期待");
                startActivity(new Intent(getContext(), MallActivity.class));
                break;
            //推广码
            case R.id.ll_extension_fragment:
                startActivity(new Intent(getContext(), ExtensionActivity.class));
                break;
            //合作伙伴
            case R.id.distribution_layout:
                GetCode();
                break;
            //我的金豆
            case R.id.bean_layout:
                startActivity(new Intent(getContext(), GoldListActivity.class));
                break;
            //足球
            case R.id.football_layout:
                startActivity(new Intent(getContext(), FootBallActivity.class));
                break;
            //快3
            case R.id.kuai3_layout:
                Intent intent = new Intent(getContext(), FastThreeActivity.class);
                intent.putExtra("status", "");
                startActivity(intent);
                break;
            //11选5
            case R.id.xuan5_layout:
                startActivity(new Intent(getContext(), ChooseMainActivity.class));
                break;
            //双色球
            case R.id.two_ball_layout:
                startActivity(new Intent(getContext(), TwoColorBallActivity.class));
                break;
            default:
                break;
        }
    }


    //判断是否为代理商
    private void GetCode() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Agent);
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("uid", Utils.UserSharedPreferences(getActivity()).getString("id", ""));
        requestParams.addBodyParameter("p", "1");
        requestParams.addBodyParameter("r", "20");
        XUtil.get(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        Intent intent = new Intent(getActivity(), DistributionActivity.class);
                        startActivity(intent);
                    } else if (code.equals("1500001")) {
                        Utils.showToast(getActivity(), new JSONObject(result).getString("您还不是代理，请购买推广码"));
//                        startActivity(new Intent(getActivity(), ExtensionActivity.class));
                    } else if (code.equals("100000")) {
                        Intent intent = new Intent(getActivity(), DistributionActivity.class);
                        startActivity(intent);
                    } else {
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!ex.getMessage().equals("") && ex.getMessage() != null) {
                    Utils.showToast(getActivity(), ex.getMessage());
                }
            }

            @Override
            public void onFinished() {
                return;

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();


//        if (Integer.valueOf(CacheUtil.getInstance().getUserInfo().getAccount().getBean()) > 9999) {
//            String bean = CacheUtil.getInstance().getUserInfo().getAccount().getBean();
//            tvScoreFragment.setText(bean.substring(0, bean.length() - 4) + "." + bean.substring(bean.length() - 4, bean.length() - 3) + "w");
//        } else
//            tvScoreFragment.setText(CacheUtil.getInstance().getUserInfo().getAccount().getBean());

        SharedPreferences sp = Utils.UserSharedPreferences(getContext());
        String avatar = sp.getString("avatar", "");
//        x.image().bind(iconFragment, sp
//                .getString("avatar", ""), imageOptions);
        if (!"".equals(sp.getString("avatar", ""))) {
            String string = sp.getString(KeyCode.USER_AVATAR, "");
            Picasso.with(getContext()).load(Utils.GetPhotoPath(string)).into(iconFragment);
        }
        nameTextInclude.setText(sp.getString("nick", ""));
        String content = sp.getString("brief", "");

        getData();

        Map<String, Boolean> map = CacheUtil.getInstance().getMap();
        Set<String> strings = map.keySet();
        int finishNums = 0;
        for (String str : strings) {
            if (map.get(str)) {
                finishNums++;
            }
        }
//        tvTaskNumsFragment.setText(finishNums + "/" + map.size());
        tvTaskNumsFragment.setText(finishNums + "/5");
        if (!"".equals(content)) {
            contentTvFragment.setBackground(null);
            contentTvFragment.setText(content);
        } else {
            contentTvFragment.setBackgroundResource(R.drawable.background_oval_white);
            //     contentTvFragment.setText("请登录");
            if ("".equals(sp.getString(KeyCode.USER_ID, ""))) {

                contentTvFragment.setText("请登录");

            } else {
                contentTvFragment.setText("编辑");
            }
        }
    }

    private void getData() {
        RequestParams requestParams = new RequestParams();
        SharedPreferences sp = Utils.UserSharedPreferences(getContext());
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.setUri(Url.Url + "/user/my");
        requestParams.addBodyParameter("userId", sp.getString(KeyCode.USER_ID, ""));

        XUtil.get(requestParams, getContext(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!RenameActivity.callOk(result)) return;
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    userInfo = gson.fromJson(jsonObject.get("result").toString(), UerEntity.class);
                    CacheUtil.getInstance().setUserInfo(userInfo);
                    setData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void SetLottery() {

        if (MainActivity.version.equals("")) {
            guessLayout.setVisibility(View.GONE);
        } else {
            long now = Long.valueOf(version_now.replace(".", ""));
            long get = Long.valueOf(MainActivity.version.replace(".", ""));
            if (now < get) {
                guessLayout.setVisibility(View.VISIBLE);
            } else {
                if (MainActivity.isLottery.equals("on")) {
                    guessLayout.setVisibility(View.VISIBLE);
                } else {
                    guessLayout.setVisibility(View.GONE);
                }

            }

        }
    }

}
