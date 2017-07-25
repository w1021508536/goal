package com.small.small.goal.my.guess.note.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.my.guess.note.empty.SportEmpty;
import com.small.small.goal.utils.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SportDetailsActivity extends BaseActivity {


    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.status_image)
    ImageView statusImage;
    @InjectView(R.id.reward_text)
    TextView rewardText;
    @InjectView(R.id.bean_layout)
    LinearLayout beanLayout;
    @InjectView(R.id.name_text)
    TextView nameText;
    @InjectView(R.id.open_time_text)
    TextView openTimeText;
    @InjectView(R.id.content_text)
    TextView contentText;
    @InjectView(R.id.bean_text)
    TextView beanText;
    @InjectView(R.id.touzhu_text)
    TextView touzhuText;
    @InjectView(R.id.open_text)
    TextView openText;
    @InjectView(R.id.peilv_text)
    TextView peilvText;
    @InjectView(R.id.create_time_text)
    TextView createTimeText;
    private SportEmpty sportEmpty;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sport_details);
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        sportEmpty = (SportEmpty) getIntent().getSerializableExtra("sportEmpty");
        nameTextInclude.setText("开奖结果");
        rightImageInclude.setVisibility(View.GONE);

        if (sportEmpty.getIsDraw().equals("0")) {
            statusImage.setImageResource(R.mipmap.icon_waiting_for);
            beanLayout.setVisibility(View.GONE);
        } else {
            if (sportEmpty.getIsWin().equals("0")) {
                statusImage.setImageResource(R.mipmap.icon_not_winning);
                beanLayout.setVisibility(View.GONE);
            } else if (sportEmpty.getIsWin().equals("1")) {
                statusImage.setImageResource(R.mipmap.icon_winning);
                beanLayout.setVisibility(View.VISIBLE);
            }
        }

        rewardText.setText(sportEmpty.getReward());

        beanText.setText(sportEmpty.getBean());
        nameText.setText(sportEmpty.getHomeTeam() + "VS" + sportEmpty.getVisitTeam());
        contentText.setText(sportEmpty.getHomeTeam() + "与" + sportEmpty.getVisitTeam() + "比赛的胜负");
        openTimeText.setText(simpleDateFormat.format(new Date(Long.valueOf(sportEmpty.getUpdateTime()))));
        createTimeText.setText(simpleDateFormat.format(new Date(Long.valueOf(sportEmpty.getCreateTime()))));

        if (sportEmpty.getContent().equals("胜")) {
            touzhuText.setText("主胜");
        } else if (sportEmpty.getContent().equals("平")) {
            touzhuText.setText("平");
        } else {
            touzhuText.setText("客胜");
        }
        if (sportEmpty.getOpenCode().equals("胜")) {
            openText.setText("主胜");
        } else if (sportEmpty.getOpenCode().equals("平")) {
            openText.setText("平");
        } else if (sportEmpty.getOpenCode().equals("负")) {
            openText.setText("客胜");
        } else {
            openText.setText(sportEmpty.getOpenCode());
        }
    }

    @OnClick({R.id.left_image_include})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image_include:
                finish();
                break;
        }
    }
}
