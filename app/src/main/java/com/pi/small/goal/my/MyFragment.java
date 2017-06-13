package com.pi.small.goal.my;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.login.LoginActivity;
import com.pi.small.goal.my.activity.AimActivity;
import com.pi.small.goal.my.activity.CollectActivity;
import com.pi.small.goal.my.activity.FollowActivity;
import com.pi.small.goal.my.activity.LevelActivity;
import com.pi.small.goal.my.activity.RedActivity;
import com.pi.small.goal.my.activity.RenameActivity;
import com.pi.small.goal.my.activity.SettingActivity;
import com.pi.small.goal.my.activity.SignActivity;
import com.pi.small.goal.my.activity.TaskActivity;
import com.pi.small.goal.my.activity.TransferActivity;
import com.pi.small.goal.my.activity.UserInfoActivity;
import com.pi.small.goal.my.activity.WalletActivity;
import com.pi.small.goal.my.entry.UerEntity;
import com.pi.small.goal.utils.CacheUtil;
import com.pi.small.goal.utils.KeyCode;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    @InjectView(R.id.ll_wallect_fragment)
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
    private ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setLoadingDrawableId(R.mipmap.icon_user)
            .setFailureDrawableId(R.mipmap.icon_user)
            .build();
    private UerEntity userInfo;
    private SharedPreferences sp;

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
    }

    private void initData() {

        sp = Utils.UserSharedPreferences(getContext());

        Drawable drawable = getResources().getDrawable(R.mipmap.exp_icon);
        drawable.setBounds(0, 0, Utils.dip2px(getContext(), 13), Utils.dip2px(getContext(), 10));
        tvLevelFragment.setCompoundDrawables(drawable, null, null, null);

        getData();

    }

    private void getData() {
        RequestParams requestParams = new RequestParams();
        SharedPreferences sp = Utils.UserSharedPreferences(getContext());
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.setUri(Url.Url + "/user/my");
        requestParams.addBodyParameter("userId", sp.getString(KeyCode.USER_ID, "26"));

        XUtil.get(requestParams, getContext(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!RenameActivity.callOk(result)) return;
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    userInfo = gson.fromJson(jsonObject.get("result").toString(), UerEntity.class);

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

    private void setData() {
        if (userInfo == null) return;
        CacheUtil.getInstance().setUserInfo(userInfo);
        UerEntity.UserBean user = userInfo.getUser();
        UerEntity.AccountBean account = userInfo.getAccount();
        UerEntity.TaskInfoBean taskInfo = userInfo.getTaskInfo();
        getSign(taskInfo);
        tvOptionFragment.setText(account.getOption() + "");
        tvScoreFragment.setText(account.getScore() + "");

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

                Log.v("TAG", result);


                if (!Utils.callOk(result)) {
                    tvTaskNumsFragment.setText(0 + "/" + 5);
                    CacheUtil.getInstance().getUserInfo().getTaskInfo().setFinishTaskCount(CacheUtil.getInstance().getUserInfo().getTaskInfo().getFinishTaskCount());
                    CacheUtil.getInstance().getUserInfo().getTaskInfo().setTotalTaskCount(CacheUtil.getInstance().getUserInfo().getTaskInfo().getTotalTaskCount() + 1);
                    return;
                }
                try {
                    long signTime = (long) ((JSONObject) new JSONObject(result).get("result")).get("lastSignInTime");
                    tvTaskNumsFragment.setText((taskInfo.getFinishTaskCount()) + "/" + (taskInfo.getTotalTaskCount() + 1));
                    Date date = new Date(signTime);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
                    Date nowDate = new Date();
                    if (formatter.format(date).equals(formatter.format(nowDate))) {
                        CacheUtil.getInstance().setSignFlag(true);
                    }
                    if (CacheUtil.getInstance().isSignFlag()) {

                        CacheUtil.getInstance().getUserInfo().getTaskInfo().setFinishTaskCount(CacheUtil.getInstance().getUserInfo().getTaskInfo().getFinishTaskCount() + 1);

                    } else {
                    }
                    CacheUtil.getInstance().getUserInfo().getTaskInfo().setTotalTaskCount(CacheUtil.getInstance().getUserInfo().getTaskInfo().getTotalTaskCount() + 1);
                    tvTaskNumsFragment.setText((taskInfo.getFinishTaskCount()) + "/" + (taskInfo.getTotalTaskCount()));

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
            case R.id.content_tv_fragment:
            case R.id.icon_fragment:
                if ("".equals(sp.getString(KeyCode.USER_ID, ""))) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getContext(), UserInfoActivity.class));
                }
                break;
            case R.id.ll_task_fragment:
                startActivity(new Intent(getContext(), TaskActivity.class));
                break;
            case R.id.ll_red_fragment:
                startActivity(new Intent(getContext(), RedActivity.class));
                break;
            case R.id.ll_follow_fragment:
                startActivity(new Intent(getContext(), FollowActivity.class));
                break;
            case R.id.ll_target_fragment:
                startActivity(new Intent(getContext(), AimActivity.class));
                break;
            case R.id.ll_collect_framgent:
                startActivity(new Intent(getContext(), CollectActivity.class));
                break;
            case R.id.ll_wallect_fragment:
                startActivity(new Intent(getContext(), WalletActivity.class));
                break;
            case R.id.right_image_include:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.tv_level_fragment:
                startActivity(new Intent(getContext(), LevelActivity.class));
                break;
            case R.id.left_image_include:
                startActivity(new Intent(getContext(), SignActivity.class));
                break;
            case R.id.ll_transfer_fragment:
                startActivity(new Intent(getContext(), TransferActivity.class));
                break;
            case R.id.ll_shopping_fragment:
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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


        if (CacheUtil.getInstance().getUserInfo() != null) {
            tvTaskNumsFragment.setText(CacheUtil.getInstance().getUserInfo().getTaskInfo().getFinishTaskCount() + "/" + CacheUtil.getInstance().getUserInfo().getTaskInfo().getTotalTaskCount());

        }


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
}
