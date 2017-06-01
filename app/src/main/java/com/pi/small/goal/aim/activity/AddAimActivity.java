package com.pi.small.goal.aim.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.Code;

public class AddAimActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView left_image;
    private TextView right_text;

    private EditText aim_edit;
    private EditText content_edit;
    private ImageView photo_image;
    private RelativeLayout position_layout;
    private TextView position_text;
    private RelativeLayout cycle_layout;
    private TextView cycle_text;
    private RelativeLayout budget_layout;
    private TextView budget_text;


    private String position = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aim);


        init();
    }

    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);
        right_text = (TextView) findViewById(R.id.right_text);

        aim_edit = (EditText) findViewById(R.id.aim_edit);
        content_edit = (EditText) findViewById(R.id.content_edit);
        photo_image = (ImageView) findViewById(R.id.photo_image);
        position_layout = (RelativeLayout) findViewById(R.id.position_layout);
        position_text = (TextView) findViewById(R.id.position_text);
        cycle_layout = (RelativeLayout) findViewById(R.id.cycle_layout);
        cycle_text = (TextView) findViewById(R.id.cycle_text);
        budget_layout = (RelativeLayout) findViewById(R.id.budget_layout);
        budget_text = (TextView) findViewById(R.id.budget_text);


        left_image.setOnClickListener(this);
        right_text.setOnClickListener(this);

        position_layout.setOnClickListener(this);
        photo_image.setOnClickListener(this);
        cycle_layout.setOnClickListener(this);
        budget_layout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.right_text:

                break;
            case R.id.position_layout:
                Intent intent = new Intent();
                intent.setClass(this, PositionActivity.class);
                startActivityForResult(intent, Code.PositionCode);

                break;
            case R.id.photo_image:

                break;
            case R.id.cycle_layout:

                break;
            case R.id.budget_layout:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Code.PositionCode) {

            if (data.getStringExtra("position").equals("")) {
                position = "";
                position_text.setText("不显示");
            } else {
                position = data.getStringExtra("position");
                position_text.setText(position);
            }


        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void NewAim() {

    }

    private void GetPosition() {


    }

}
