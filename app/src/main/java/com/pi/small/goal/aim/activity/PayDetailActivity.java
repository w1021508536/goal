package com.pi.small.goal.aim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Code;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PayDetailActivity extends BaseActivity {


    @InjectView(R.id.left_image)
    ImageView left_image;
    @InjectView(R.id.right_image)
    ImageView right_image;
    @InjectView(R.id.pay_mode_text)
    TextView pay_mode_text;
    @InjectView(R.id.card_text)
    TextView card_text;
    @InjectView(R.id.money_text)
    TextView money_text;
    @InjectView(R.id.finish_text)
    TextView finish_text;

    private String money;
    private String channel;
    private String card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        ButterKnife.inject(this);

        money = getIntent().getExtras().getString("money", "0");
        channel = getIntent().getExtras().getString("channel", "");
        card = getIntent().getExtras().getString("card");


        init();
    }

    @OnClick({R.id.left_image, R.id.finish_text})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.finish_text:
                setResult(Code.Pay, intent);
                finish();
                break;
            case R.id.left_image:
                setResult(Code.Pay, intent);
                finish();
                break;
        }

    }

    private void init() {

        money_text.setText(getResources().getText(R.string.money_sign) + money);
        pay_mode_text.setText(channel);
        if (card.equals("")) {
            card_text.setVisibility(View.GONE);
        } else {
            card_text.setVisibility(View.VISIBLE);
            card_text.setText(card);
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            Intent intent = new Intent();
            setResult(Code.Pay, intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }

}
