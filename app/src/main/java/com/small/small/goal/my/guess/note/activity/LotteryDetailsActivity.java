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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        timeText.setText(simpleDateFormat.format(new Date(Long.valueOf(lotteryEmpty.getOpenTime()))));

        beanText.setText(lotteryEmpty.getBean());
        infoText.setText("共" + lotteryEmpty.getNumber() + "注，倍投" + lotteryEmpty.getMultifold() + "倍");
        createTimeText.setText(simpleDateFormat.format(new Date(Long.valueOf(lotteryEmpty.getCreateTime()))));
        openCodeText.setText(lotteryEmpty.getOpenCode());

        String contentString = "";
        String lotteryContent = lotteryEmpty.getContent();

        List<String> listContent = new ArrayList<>();


        while (lotteryContent.indexOf(";") > 0) {
            listContent.add(lotteryContent.substring(0, lotteryContent.indexOf(";")));
            lotteryContent = lotteryContent.substring(lotteryContent.indexOf(";") + 1, lotteryContent.length());
        }
        if (listContent.size() == 0) {
            listContent.add(lotteryEmpty.getContent());
        }

        for (int i = 0; i < listContent.size(); i++) {
            String code = listContent.get(i).substring(0, listContent.get(i).indexOf(":"));
            String content = listContent.get(i).substring(listContent.get(i).indexOf(":") + 1, listContent.get(i).length());
            if (code.equals("1")) {
                contentString = contentString + "[直选]";
            } else if (code.equals("3")) {
                contentString = contentString + "[组三]";
            } else if (code.equals("6")) {
                contentString = contentString + "[组六]";
            } else if (code.equals("22")) {
                contentString = contentString + "[任2]";
            } else if (code.equals("23")) {
                contentString = contentString + "[任3]";
            } else if (code.equals("24")) {
                contentString = contentString + "[任4]";
            } else if (code.equals("25")) {
                contentString = contentString + "[任5]";
            } else if (code.equals("26")) {
                contentString = contentString + "[任6]";
            } else if (code.equals("27")) {
                contentString = contentString + "[任7]";
            } else if (code.equals("28")) {
                contentString = contentString + "[任8]";
            } else if (code.equals("31")) {
                contentString = contentString + "[前一直选]";
            } else if (code.equals("32")) {
                contentString = contentString + "[前二直选]";
            } else if (code.equals("33")) {
                contentString = contentString + "[前三直选]";
            } else if (code.equals("301")) {
                contentString = contentString + "[前一组选]";
            } else if (code.equals("302")) {
                contentString = contentString + "[前二组选]";
            } else if (code.equals("303")) {
                contentString = contentString + "[前三组选]";
            } else if (code.equals("10")) {
                contentString = contentString + "[和值]";
            } else if (code.equals("43")) {
                contentString = contentString + "[三连号通选]";
            } else if (code.equals("431")) {
                contentString = contentString + "[三同号单选]";
            } else if (code.equals("436")) {
                contentString = contentString + "[三同号通选]";
            } else if (code.equals("430")) {
                contentString = contentString + "[三不同号]";
            } else if (code.equals("421")) {
                contentString = contentString + "[二同号单选]";
            } else if (code.equals("426")) {
                contentString = contentString + "[二同号复选]";
            } else if (code.equals("420")) {
                contentString = contentString + "[二不同号]";
            }
            contentString = contentString + content + ";";
        }
        contentText.setText(contentString);


    }

    @OnClick({R.id.left_image_include, R.id.next_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.next_text:
                LotteryNoteActivity.instance.finish();
                finish();
                break;
        }
    }
}
