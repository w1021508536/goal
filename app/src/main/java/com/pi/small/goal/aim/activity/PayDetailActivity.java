package com.pi.small.goal.aim.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;

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
    private String pay_mode;
    private String card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        ButterKnife.inject(this);

        money = getIntent().getExtras().getString("money", "0");
        pay_mode = getIntent().getExtras().getString("pay_mode", "");
        card = getIntent().getExtras().getString("card");


        init();
    }

    @OnClick({R.id.left_image, R.id.finish_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finish_text:
                finish();

                break;
            case R.id.left_image:
                finish();

                break;
        }

    }

    private void init() {

        money_text.setText(getResources().getText(R.string.aim) + money);
        pay_mode_text.setText(pay_mode);
        if (card.equals("")) {
            card_text.setVisibility(View.GONE);
        } else {
            card_text.setVisibility(View.VISIBLE);
            card_text.setText(card);
        }


    }

}
