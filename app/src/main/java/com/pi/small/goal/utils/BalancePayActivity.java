package com.pi.small.goal.utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.weight.VirtualKeyboardView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BalancePayActivity extends BaseActivity {


    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.tv_hint_pass)
    TextView tvHintPass;
    @InjectView(R.id.tv_pass1)
    TextView tvPass1;
    @InjectView(R.id.img_pass1)
    ImageView imgPass1;
    @InjectView(R.id.tv_pass2)
    TextView tvPass2;
    @InjectView(R.id.img_pass2)
    ImageView imgPass2;
    @InjectView(R.id.tv_pass3)
    TextView tvPass3;
    @InjectView(R.id.img_pass3)
    ImageView imgPass3;
    @InjectView(R.id.tv_pass4)
    TextView tvPass4;
    @InjectView(R.id.img_pass4)
    ImageView imgPass4;
    @InjectView(R.id.tv_pass5)
    TextView tvPass5;
    @InjectView(R.id.img_pass5)
    ImageView imgPass5;
    @InjectView(R.id.tv_pass6)
    TextView tvPass6;
    @InjectView(R.id.img_pass6)
    ImageView imgPass6;
    @InjectView(R.id.ll_key)
    LinearLayout llKey;
    @InjectView(R.id.ll_pass)
    LinearLayout llPass;
    @InjectView(R.id.vkb_updataPass)
    VirtualKeyboardView vkbUpdataPass;
    private GridView gridView;
    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？

    private ImageView[] imgList;      //用数组保存6个TextView，为什么用数组？
    private int currentIndex = -1;    //用于记录当前输入密码格位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_balance_pay);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);


    }

    @Override
    public void initData() {
        super.initData();

        gridView = vkbUpdataPass.getGridView();
        measureListViewHeight(gridView);

        tvList = new TextView[6];
        imgList = new ImageView[6];

        tvList[0] = (TextView) findViewById(R.id.tv_pass1);
        tvList[1] = (TextView) findViewById(R.id.tv_pass2);
        tvList[2] = (TextView) findViewById(R.id.tv_pass3);
        tvList[3] = (TextView) findViewById(R.id.tv_pass4);
        tvList[4] = (TextView) findViewById(R.id.tv_pass5);
        tvList[5] = (TextView) findViewById(R.id.tv_pass6);


        imgList[0] = (ImageView) findViewById(R.id.img_pass1);
        imgList[1] = (ImageView) findViewById(R.id.img_pass2);
        imgList[2] = (ImageView) findViewById(R.id.img_pass3);
        imgList[3] = (ImageView) findViewById(R.id.img_pass4);
        imgList[4] = (ImageView) findViewById(R.id.img_pass5);
        imgList[5] = (ImageView) findViewById(R.id.img_pass6);


        int width = getWindowManager().getDefaultDisplay().getWidth() - Utils.dip2px(this, this.getResources().getDimension(R.dimen.top_left));
        float v = width / 6.0f;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llKey.getLayoutParams();
        layoutParams.height = (int) v;
        llKey.setLayoutParams(layoutParams);

    }

    @Override
    public void initWeight() {
        super.initWeight();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //点击0~9按钮
                    if (currentIndex >= -1 && currentIndex < 5) {      //判断输入位置————要小心数组越界

                        ++currentIndex;
                        tvList[currentIndex].setText(vkbUpdataPass.getValueList().get(position).get("name"));

                        tvList[currentIndex].setVisibility(View.INVISIBLE);
                        imgList[currentIndex].setVisibility(View.VISIBLE);
                    }
                } else {
                    if (position == 11) {      //点击退格键
                        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界

                            tvList[currentIndex].setText("");

                            tvList[currentIndex].setVisibility(View.VISIBLE);
                            imgList[currentIndex].setVisibility(View.INVISIBLE);

                            currentIndex--;
                        }
                    }
                }
            }
        });
        setOnFinishInput();
    }


    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput() {


        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 1) {
                    String strPassword = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
                    for (int i = 0; i < 6; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    System.out.println("strPassword :" + strPassword);

                    Intent intent = new Intent();
                    intent.putExtra("password", strPassword);
                    setResult(Code.BalancePay, intent);
                    finish();
                }
            }
        });
    }

    public void measureListViewHeight(final AbsListView listView) {
        final ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        listView.post(new Runnable() {
            @Override
            public void run() {
                int totalHeight = 0;
                int count = listAdapter.getCount();
                //TODO 这里可以去获取每一列最高的一个
                View listItem = listAdapter.getView(0, null, listView);

                listItem.measure(0, 0);
                if (listView instanceof GridView) {
                    int columns = ((GridView) listView).getNumColumns();
                    int rows = count % columns != 0 ? 1 : 0;
                    rows += count / columns;
                    totalHeight += listItem.getMeasuredHeight() * rows;
                } else if (listView instanceof ListView) {
                    for (int i = 0; i < count; i++) {
                        listItem = listAdapter.getView(i, null, listView);
                        listItem.measure(0, 0);
                        totalHeight += listItem.getMeasuredHeight() + ((ListView) listView).getDividerHeight() * (listAdapter.getCount() - 1);
                    }
                }
                handler.sendEmptyMessage(totalHeight);
            }
        });
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            llPass.setPadding(0, 0, 0, msg.what / 2);

            return false;
        }
    });

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            Intent intent = new Intent();
            intent.putExtra("password", "");
            setResult(Code.BalancePay, intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }

    @OnClick(R.id.left_image)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.putExtra("password", "");
        setResult(Code.BalancePay, intent);
        finish();
    }
}

