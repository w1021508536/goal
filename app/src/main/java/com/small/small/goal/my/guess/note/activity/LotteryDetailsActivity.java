package com.small.small.goal.my.guess.note.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.my.guess.note.empty.LotteryEmpty;
import com.small.small.goal.utils.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LotteryDetailsActivity extends BaseActivity {


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
    @InjectView(R.id.time_text)
    TextView timeText;
    @InjectView(R.id.open_status_layout)
    RelativeLayout openStatusLayout;
    @InjectView(R.id.name_text)
    TextView nameText;
    @InjectView(R.id.bean_text)
    TextView beanText;
    @InjectView(R.id.content_text)
    TextView contentText;
    @InjectView(R.id.info_text)
    TextView infoText;
    @InjectView(R.id.create_time_text)
    TextView createTimeText;
    @InjectView(R.id.open_code_text)
    TextView openCodeText;
    @InjectView(R.id.open_layout)
    LinearLayout openLayout;
    @InjectView(R.id.next_text)
    TextView nextText;
    private LotteryEmpty lotteryEmpty;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lottery_details);
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        lotteryEmpty = (LotteryEmpty) getIntent().getSerializableExtra("lotteryEmpty");
        init();
    }

    private void init() {
        nameTextInclude.setText("开奖结果");
        rightImageInclude.setVisibility(View.GONE);

        if (lotteryEmpty.getIsDraw().equals("0")) {
            openStatusLayout.setBackgroundResource(R.mipmap.image_waiting);
            timeText.setVisibility(View.VISIBLE);
            openLayout.setVisibility(View.GONE);
        } else {
            openLayout.setVisibility(View.VISIBLE);
            if (lotteryEmpty.getIsWin().equals("0")) {
                openStatusLayout.setBackgroundResource(R.mipmap.image_not_winning);
                timeText.setVisibility(View.GONE);
            } else if (lotteryEmpty.getIsWin().equals("1")) {
                openStatusLayout.setBackgroundResource(R.mipmap.image_winning);
                timeText.setVisibility(View.GONE);
            }
        }

        if (lotteryEmpty.getTid().equals("9")) {
            nameText.setText("新快3-" + lotteryEmpty.getExpect() + "期");
        } else if (lotteryEmpty.getTid().equals("8")) {
            nameText.setText("11选5-" + lotteryEmpty.getExpect() + "期");
        }

        timeText.setText(simpleDateFormat.format(new Date(Long.valueOf(lotteryEmpty.getUpdateTime()))));

        beanText.setText(lotteryEmpty.getBean());
        infoText.setText("共" + lotteryEmpty.getNumber() + "注，倍投" + lotteryEmpty.getMultifold() + "倍");
        createTimeText.setText(simpleDateFormat.format(new Date(Long.valueOf(lotteryEmpty.getCreateTime()))));
        openCodeText.setText(lotteryEmpty.getOpenCode());

        String code = lotteryEmpty.getContent().substring(0, lotteryEmpty.getContent().indexOf(":"));
        String conent = lotteryEmpty.getContent().substring(lotteryEmpty.getContent().indexOf(":") + 1, lotteryEmpty.getContent().length());


        if (code.equals("1")) {
            contentText.setText("[直选]" + conent);
        } else if (code.equals("3")) {
            contentText.setText("[组三]" + conent);
        } else if (code.equals("6")) {
            contentText.setText("[组六]" + conent);
        } else if (code.equals("22")) {
            contentText.setText("[任2]" + conent);
        } else if (code.equals("23")) {
            contentText.setText("[任3]" + conent);
        } else if (code.equals("24")) {
            contentText.setText("[任4]" + conent);
        } else if (code.equals("25")) {
            contentText.setText("[任5]" + conent);
        } else if (code.equals("26")) {
            contentText.setText("[任6]" + conent);
        } else if (code.equals("27")) {
            contentText.setText("[任7]" + conent);
        } else if (code.equals("28")) {
            contentText.setText("[任8]" + conent);
        } else if (code.equals("31")) {
            contentText.setText("[前一直选]" + conent);
        } else if (code.equals("32")) {
            contentText.setText("[前二直选]" + conent);
        } else if (code.equals("33")) {
            contentText.setText("[前三直选]" + conent);
        } else if (code.equals("301")) {
            contentText.setText("[前一组选]" + conent);
        } else if (code.equals("302")) {
            contentText.setText("[前二组选]" + conent);
        } else if (code.equals("303")) {
            contentText.setText("[前三组选]" + conent);
        } else if (code.equals("10")) {
            contentText.setText("[和值]" + conent);
        } else if (code.equals("43")) {
            contentText.setText("[三连号通选]" + conent);
        } else if (code.equals("431")) {
            contentText.setText("[三同号单选]" + conent);
        } else if (code.equals("436")) {
            contentText.setText("[三同号通选]" + conent);
        } else if (code.equals("430")) {
            contentText.setText("[三不同号]" + conent);
        } else if (code.equals("421")) {
            contentText.setText("[二同号单选]" + conent);
        } else if (code.equals("426")) {
            contentText.setText("[二同号复选]" + conent);
        } else if (code.equals("420")) {
            contentText.setText("[二不同号]" + conent);
        }
    }

    @OnClick({R.id.left_image_include, R.id.next_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.next_text:
                finish();
                break;
        }
    }
}
