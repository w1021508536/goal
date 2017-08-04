package com.small.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.MainActivity;
import com.small.small.goal.R;
import com.small.small.goal.aim.activity.AddAimActivity;
import com.small.small.goal.my.entry.UerEntity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/7 9:30
 * 修改：
 * 描述：会员等级
 **/
public class LevelActivity extends BaseActivity {

    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.img_icon_level)
    ImageView imgIconLevel;
    @InjectView(R.id.tv_expNums_level)
    TextView tvExpNumsLevel;
    @InjectView(R.id.img_share_level)
    ImageView imgShareLevel;
    @InjectView(R.id.img_help_level)
    ImageView imgHelpLevel;
    @InjectView(R.id.img_great_level)
    ImageView imgGreatLevel;
    @InjectView(R.id.img_comment_level)
    ImageView imgCommentLevel;
    @InjectView(R.id.img_invite_level)
    ImageView imgInviteLevel;
    @InjectView(R.id.img_money_level)
    ImageView imgMoneyLevel;
    @InjectView(R.id.img_level1_level)
    ImageView imgLevel1Level;
    @InjectView(R.id.img_level2_level)
    ImageView imgLevel2Level;
    @InjectView(R.id.img_level3_level)
    ImageView imgLevel3Level;
    @InjectView(R.id.img_level4_level)
    ImageView imgLevel4Level;
    @InjectView(R.id.img_level5_level)
    ImageView imgLevel5Level;
    @InjectView(R.id.img_level6_level)
    ImageView imgLevel6Level;
    @InjectView(R.id.img_level7_level)
    ImageView imgLevel7Level;
    @InjectView(R.id.img_level8_level)
    ImageView imgLevel8Level;
    @InjectView(R.id.tv_aimNums_level)
    TextView tvAimNumsLevel;
    @InjectView(R.id.tv_redNums_level)
    TextView tvRedNumsLevel;
    @InjectView(R.id.ll_aim_level)
    LinearLayout llAimLevel;
    @InjectView(R.id.ll_red_level)
    LinearLayout llRedLevel;
    @InjectView(R.id.rl_money_level)
    RelativeLayout rlMoneyLevel;
    @InjectView(R.id.view_line_red_1)
    View viewLineRed1;
    @InjectView(R.id.ll_line1)
    LinearLayout llLine1;
    @InjectView(R.id.view_line_red_3)
    View viewLineRed3;
    @InjectView(R.id.ll_line3)
    LinearLayout llLine3;
    @InjectView(R.id.view_line_red_4)
    View viewLineRed4;
    @InjectView(R.id.ll_line4)
    LinearLayout llLine4;
    @InjectView(R.id.view_line_red_5)
    View viewLineRed5;
    @InjectView(R.id.ll_line5)
    LinearLayout llLine5;
    @InjectView(R.id.view_line_red_6)
    View viewLineRed6;
    @InjectView(R.id.ll_line6)
    LinearLayout llLine6;
    @InjectView(R.id.view_line_red_7)
    View viewLineRed7;
    @InjectView(R.id.ll_line7)
    LinearLayout llLine7;
    @InjectView(R.id.view_line_red_8)
    View viewLineRed8;
    @InjectView(R.id.ll_line8)
    LinearLayout llLine8;
    private ImageView[] imageViews;
    private Integer[][] aimRed;
    private Integer[] expArray;
    private LinearLayout[] linearLayouts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_level);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("会员等级");
        rightImageInclude.setVisibility(View.GONE);

        UerEntity userInfo = CacheUtil.getInstance().getUserInfo();
        if (userInfo == null) return;
        int exp = userInfo.getAccount().getExp();
        tvExpNumsLevel.setText("经验值：" + exp);


        imageViews = new ImageView[]{imgLevel1Level, imgLevel2Level, imgLevel3Level, imgLevel4Level, imgLevel5Level, imgLevel6Level, imgLevel7Level, imgLevel8Level};

        Integer grade = Integer.valueOf(userInfo.getGrade().replace("v", ""));
//        for (int i = 0; i < imageViews.length; i++) {
//            if (i < grade) {
//                Integer integer = Integer.valueOf("R.mipmap.icon_level_" + (i + 1));
//                imageViews[i].setImageResource(integer);
//            }
//        }
        setImageView(grade);

        aimRed = new Integer[][]{{1, 0}, {1, 5}, {1, 7}, {1, 10}, {2, 15}, {5, 30}, {10, 50}, {15, 80}, {20, 100}};

        expArray = new Integer[]{0, 600, 900, 1500, 3000, 7500, 45000, 100000, 200000};
        linearLayouts = new LinearLayout[]{llLine1, llLine3, llLine4, llLine5, llLine6, llLine7, llLine8};

        Integer[] bitmapRes = new Integer[]{R.mipmap.zhuxiaobao_icon, R.mipmap.zhuxiaobao_icon, R.mipmap.icon_fendouzhu, R.mipmap.icon_chulanzhu, R.mipmap.icon_chengmingzhu, R.mipmap.icon_feitianzhu, R.mipmap.icon_zhuxiaoxian, R.mipmap.icon_zhudaxian, R.mipmap.icon_yidaizongzhu};
        Integer[] integers = aimRed[grade];
        tvAimNumsLevel.setText(integers[0] + "");
        tvRedNumsLevel.setText(integers[1] + "");
        imgIconLevel.setImageResource(bitmapRes[grade]);

        if (grade == 0) return;


        for (int y = 1; y < grade; y++) {
            LinearLayout linearLayout = linearLayouts[y - 1];
            View view = linearLayout.getChildAt(0);
            view.setBackgroundResource(R.color.line_level_red);
        }

        if (grade == 8) {
            return;
        }
        LinearLayout linearLayout = linearLayouts[grade - 1];
        float i = exp - expArray[grade];
        float v = i / (expArray[grade + 1] - expArray[grade]);
        View view1 = linearLayout.getChildAt(0);
        LinearLayout.LayoutParams l = (LinearLayout.LayoutParams) view1.getLayoutParams();
        l.weight = v;
        l.width = 0;
        l.height = Utils.dip2px(this, 1);
        view1.setBackgroundResource(R.color.line_level_red);
        view1.setLayoutParams(l);


        ImageView oval = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Utils.dip2px(this, 6), Utils.dip2px(this, 6));
        oval.setLayoutParams(layoutParams);
        oval.setImageResource(R.mipmap.small_circle);
        linearLayout.addView(oval);
        View view = new View(this);
        LinearLayout.LayoutParams layoutParamsView = new LinearLayout.LayoutParams(Utils.dip2px(this, 0), Utils.dip2px(this, 1));
        layoutParamsView.weight = (1 - v);
        view.setLayoutParams(layoutParamsView);
        view.setBackgroundColor(getResources().getColor(R.color.line_level_gray));
        linearLayout.addView(view);

    }

    @Override
    public void initWeight() {
        super.initWeight();
        llAimLevel.setOnClickListener(this);
        llRedLevel.setOnClickListener(this);
        //    rlMoneyLevel.setOnClickListener(this);
        imgLevel1Level.setOnClickListener(this);
        imgLevel2Level.setOnClickListener(this);
        imgLevel3Level.setOnClickListener(this);
        imgLevel4Level.setOnClickListener(this);
        imgLevel5Level.setOnClickListener(this);
        imgLevel6Level.setOnClickListener(this);
        imgLevel7Level.setOnClickListener(this);
        imgLevel8Level.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Integer[] integers;
        switch (v.getId()) {
            case R.id.ll_aim_level:
                startActivity(new Intent(this, AddAimActivity.class));
                break;
            case R.id.ll_red_level:
                //      startActivity(new Intent(this, RedActivity.class));
                CacheUtil.getInstance().setTaskToMainFlag(true);
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.rl_money_level:
                // startActivity(new Intent(this,MontyToActivity.class));
                break;
            case R.id.img_level1_level:
                ClearImage();
                imageViews[0].setImageResource(R.mipmap.icon_level_1);
                integers = aimRed[1];
                tvAimNumsLevel.setText(integers[0] + "");
                tvRedNumsLevel.setText(integers[1] + "");
                break;
            case R.id.img_level2_level:
                ClearImage();
                imageViews[1].setImageResource(R.mipmap.icon_level_2);
                integers = aimRed[2];
                tvAimNumsLevel.setText(integers[0] + "");
                tvRedNumsLevel.setText(integers[1] + "");
                break;
            case R.id.img_level3_level:
                ClearImage();
                imageViews[2].setImageResource(R.mipmap.icon_level_3);
                integers = aimRed[3];
                tvAimNumsLevel.setText(integers[0] + "");
                tvRedNumsLevel.setText(integers[1] + "");
                break;
            case R.id.img_level4_level:
                ClearImage();
                imageViews[3].setImageResource(R.mipmap.icon_level_4);
                integers = aimRed[4];
                tvAimNumsLevel.setText(integers[0] + "");
                tvRedNumsLevel.setText(integers[1] + "");
                break;
            case R.id.img_level5_level:
                ClearImage();
                imageViews[4].setImageResource(R.mipmap.icon_level_5);
                integers = aimRed[5];
                tvAimNumsLevel.setText(integers[0] + "");
                tvRedNumsLevel.setText(integers[1] + "");
                break;
            case R.id.img_level6_level:
                ClearImage();
                imageViews[5].setImageResource(R.mipmap.icon_level_6);
                integers = aimRed[6];
                tvAimNumsLevel.setText(integers[0] + "");
                tvRedNumsLevel.setText(integers[1] + "");
                break;
            case R.id.img_level7_level:
                ClearImage();
                imageViews[6].setImageResource(R.mipmap.icon_level_7);
                integers = aimRed[7];
                tvAimNumsLevel.setText(integers[0] + "");
                tvRedNumsLevel.setText(integers[1] + "");
                break;
            case R.id.img_level8_level:
                ClearImage();
                imageViews[7].setImageResource(R.mipmap.icon_level_8);
                integers = aimRed[8];
                tvAimNumsLevel.setText(integers[0] + "");
                tvRedNumsLevel.setText(integers[1] + "");
                break;
        }
    }


    private void ClearImage() {
        imageViews[0].setImageResource(R.mipmap.icon_level_gray_1);
        imageViews[1].setImageResource(R.mipmap.icon_level_gray_2);
        imageViews[2].setImageResource(R.mipmap.icon_level_gray_3);
        imageViews[3].setImageResource(R.mipmap.icon_level_gray_4);
        imageViews[4].setImageResource(R.mipmap.icon_level_gray_5);
        imageViews[5].setImageResource(R.mipmap.icon_level_gray_6);
        imageViews[6].setImageResource(R.mipmap.icon_level_gray_7);
        imageViews[7].setImageResource(R.mipmap.icon_level_gray_8);
    }

    private void setImageView(Integer grade) {

        if (grade != 0) {
            ViewGroup.LayoutParams layoutParams = imageViews[grade - 1].getLayoutParams();
            layoutParams.width = Utils.dip2px(this, 25);
            layoutParams.height = Utils.dip2px(this, 25);
        }
        switch (grade) {
            case 1:
                imageViews[0].setImageResource(R.mipmap.icon_level_1);
                break;
            case 2:

//                imageViews[0].setImageResource(R.mipmap.icon_level_1);
                imageViews[1].setImageResource(R.mipmap.icon_level_2);
                break;
            case 3:
//                imageViews[0].setImageResource(R.mipmap.icon_level_1);
//                imageViews[1].setImageResource(R.mipmap.icon_level_2);
                imageViews[2].setImageResource(R.mipmap.icon_level_3);
                break;
            case 4:
//                imageViews[0].setImageResource(R.mipmap.icon_level_1);
//                imageViews[1].setImageResource(R.mipmap.icon_level_2);
//                imageViews[2].setImageResource(R.mipmap.icon_level_3);
                imageViews[3].setImageResource(R.mipmap.icon_level_4);
                break;
            case 5:
//                imageViews[0].setImageResource(R.mipmap.icon_level_1);
//                imageViews[1].setImageResource(R.mipmap.icon_level_2);
//                imageViews[2].setImageResource(R.mipmap.icon_level_3);
//                imageViews[3].setImageResource(R.mipmap.icon_level_4);
                imageViews[4].setImageResource(R.mipmap.icon_level_5);
                break;
            case 6:
//                imageViews[0].setImageResource(R.mipmap.icon_level_1);
//                imageViews[1].setImageResource(R.mipmap.icon_level_2);
//                imageViews[2].setImageResource(R.mipmap.icon_level_3);
//                imageViews[3].setImageResource(R.mipmap.icon_level_4);
//                imageViews[4].setImageResource(R.mipmap.icon_level_5);
                imageViews[5].setImageResource(R.mipmap.icon_level_6);
                break;
            case 7:
//                imageViews[0].setImageResource(R.mipmap.icon_level_1);
//                imageViews[1].setImageResource(R.mipmap.icon_level_2);
//                imageViews[2].setImageResource(R.mipmap.icon_level_3);
//                imageViews[3].setImageResource(R.mipmap.icon_level_4);
//                imageViews[4].setImageResource(R.mipmap.icon_level_5);
//                imageViews[5].setImageResource(R.mipmap.icon_level_6);
                imageViews[6].setImageResource(R.mipmap.icon_level_7);
                break;
            case 8:
//                imageViews[0].setImageResource(R.mipmap.icon_level_1);
//                imageViews[1].setImageResource(R.mipmap.icon_level_2);
//                imageViews[2].setImageResource(R.mipmap.icon_level_3);
//                imageViews[3].setImageResource(R.mipmap.icon_level_4);
//                imageViews[4].setImageResource(R.mipmap.icon_level_5);
//                imageViews[5].setImageResource(R.mipmap.icon_level_6);
//                imageViews[6].setImageResource(R.mipmap.icon_level_7);
                imageViews[7].setImageResource(R.mipmap.icon_level_8);
                break;
        }
    }
}
