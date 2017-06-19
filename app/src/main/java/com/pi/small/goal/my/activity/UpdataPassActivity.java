package com.pi.small.goal.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.pi.small.goal.my.dialog.HuiFuDialog;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.CacheUtil;
import com.pi.small.goal.utils.KeyCode;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.weight.VirtualKeyboardView;

import org.xutils.http.RequestParams;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.pi.small.goal.R.id.ll_key;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/5 9:26
 * 修改：
 * 描述： 修改密码
 **/
public class UpdataPassActivity extends BaseActivity {

    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.rl_top)
    LinearLayout rlTop;
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
    @InjectView(R.id.vkb_updataPass)
    VirtualKeyboardView vkbUpdataPass;
    @InjectView(R.id.ll_pass)
    LinearLayout llPass;
    @InjectView(R.id.tv_hint_pass)
    TextView tvHintPass;
    @InjectView(R.id.view)
    View view;
    @InjectView(ll_key)
    LinearLayout llKey;

    private GridView gridView;
    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？

    private ImageView[] imgList;      //用数组保存6个TextView，为什么用数组？
    private int currentIndex = -1;    //用于记录当前输入密码格位置


    public static final String KEY_FLAG = "flag";
    public static final String KEY_PASS = "pass";
    public static final int FLAG_OLD = 1;
    public static final int FLAG_NEW = 2;
    public static final int FLAG_NEW_AGIN = 3;
    public static final int FLAG_SET_PASS = 4;
    public static final int FLAG_SET_PASS_AGIN = 5;
    public static final int FLAG_FORGET_PASS = 6;
    public static final int FLAG_FORGET_PASS_AGIN = 7;
    private int flag;            //用户与区分是新的密码输入，还是旧的
    private HuiFuDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_updata_pass);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("支付密码");
        rightImageInclude.setVisibility(View.GONE);

        flag = getIntent().getIntExtra(KEY_FLAG, FLAG_OLD);
        switch (flag) {
            case FLAG_OLD:
                tvHintPass.setText("请输入原支付密码，用于支付验证");
                break;
            case FLAG_NEW:
                tvHintPass.setText("请输入新的支付密码");
                break;
            case FLAG_NEW_AGIN:
            case FLAG_SET_PASS_AGIN:
            case FLAG_FORGET_PASS_AGIN:
                tvHintPass.setText("请再次输入以确认");
                break;
            case FLAG_SET_PASS:
            case FLAG_FORGET_PASS:
                tvHintPass.setText("请设置支付密码，用于支付验证");
                break;
        }

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

        dialog = new HuiFuDialog(this, "你的支付密码已设置成功");

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

                    Intent intent = new Intent(UpdataPassActivity.this, UpdataPassActivity.class);

                    switch (flag) {
                        case FLAG_OLD:

                            verifyPass(strPassword);

                            break;
                        case FLAG_NEW:
                            intent.putExtra(KEY_FLAG, FLAG_NEW_AGIN);
                            startActivity(intent);
                            CacheUtil.getInstance().setNewPass(strPassword);
                            finish();
                            break;
                        case FLAG_NEW_AGIN:
                            if (!CacheUtil.getInstance().getNewPass().equals(strPassword)) {
                                Utils.showToast(UpdataPassActivity.this, "两次密码不一致");
                                return;
                            }
                            updataPass();
                            break;
                        case FLAG_SET_PASS:
                            intent.putExtra(KEY_FLAG, FLAG_SET_PASS_AGIN);
                            startActivity(intent);
                            finish();
                            CacheUtil.getInstance().setNewPass(strPassword);
                            break;
                        case FLAG_SET_PASS_AGIN:

                            if (!CacheUtil.getInstance().getNewPass().equals(strPassword)) {
                                Utils.showToast(UpdataPassActivity.this, "两次密码不一致");
                                return;
                            }
                            setPass();
                            break;
                        case FLAG_FORGET_PASS:
                            intent.putExtra(KEY_FLAG, FLAG_FORGET_PASS_AGIN);
                            startActivity(intent);
                            finish();
                            CacheUtil.getInstance().setNewPass(strPassword);
                            break;
                        case FLAG_FORGET_PASS_AGIN:
                            if (!CacheUtil.getInstance().getNewPass().equals(strPassword)) {
                                Utils.showToast(UpdataPassActivity.this, "两次密码不一致");
                                return;
                            }
                            forgetPass();
                            break;
                    }

                }
            }
        });
    }

    /**
     * 重置密码
     * create  wjz
     **/

    private void forgetPass() {
        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/pay/password/reset");
        requestParams.addBodyParameter("password", CacheUtil.getInstance().getNewPass());
        requestParams.addBodyParameter("verifyCode", CacheUtil.getInstance().getForgetPassCode());
        requestParams.addBodyParameter("mobile", sp.getString("mobile", ""));

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (Utils.callOk(result)) {
                    dialog.show();
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

    /**
     * 验证支付密码是否正确
     * create  wjz
     **/

    private void verifyPass(final String strPassword) {

        requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/pay/password/verify");
        requestParams.addBodyParameter("password", strPassword);

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (Utils.callOk(result)) {
                    Intent intent = new Intent(UpdataPassActivity.this, UpdataPassActivity.class);
                    intent.putExtra(KEY_FLAG, FLAG_NEW);
                    startActivity(intent);
                    CacheUtil.getInstance().setOldPass(strPassword);
                    finish();
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

    private void setPass() {

        requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/pay/password");
        requestParams.addBodyParameter("password", CacheUtil.getInstance().getNewPass());
        XUtil.put(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (Utils.callOk(result)) {
                    dialog.show();
                    sp.edit().putInt(KeyCode.USER_PAY_PASS, 1).commit();

                    List<Activity> activityList = app.getActivityList();
                    for (Activity activity : activityList) {
                        if (activity instanceof BindPhoneNextActivity) {
                            activity.finish();
                        }
                    }
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

    /**
     * 修改支付密码
     * create  wjz
     **/

    private void updataPass() {
        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/pay/password");
        requestParams.addBodyParameter("password", CacheUtil.getInstance().getNewPass());
        requestParams.addBodyParameter("oldPassword", CacheUtil.getInstance().getOldPass());

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (RenameActivity.callOk(result)) {
                    dialog.show();
                } else {
                    Utils.showToast(UpdataPassActivity.this, Utils.getMsg(result));
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
}
