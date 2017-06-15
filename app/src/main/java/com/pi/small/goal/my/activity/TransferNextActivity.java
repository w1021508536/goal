package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.message.activity.FriendsListActivity;
import com.pi.small.goal.my.dialog.HuiFuDialog;
import com.pi.small.goal.my.dialog.KeyBoardDialog;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.utils.entity.ContactBean;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.pi.small.goal.message.activity.FriendsListActivity.KEY_TYPE;
import static com.pi.small.goal.message.activity.FriendsListActivity.TYPE_TRANSFER;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/12 16:40
 * 修改：
 * 描述： 期权转让给别人
 **/
public class TransferNextActivity extends BaseActivity {


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
    @InjectView(R.id.etv_money)
    EditText etvMoney;
    @InjectView(R.id.img_icon)
    ImageView imgIcon;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.ll_user)
    LinearLayout llUser;
    @InjectView(R.id.img_right)
    ImageView imgRight;
    @InjectView(R.id.rl_select_friends)
    RelativeLayout rlSelectFriends;
    @InjectView(R.id.tv_ok)
    TextView tvOk;
    @InjectView(R.id.tv_hint)
    TextView tvHint;

    private ContactBean contactBean;
    private HuiFuDialog dialog;
    private KeyBoardDialog keyBoardDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_transfer_next);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_warn);
        drawable.setBounds(0, 0, Utils.dip2px(this, 10), Utils.dip2px(this, 10));
        tvHint.setCompoundDrawables(drawable, null, null, null);

        nameTextInclude.setText("转让");
        rightImageInclude.setVisibility(View.GONE);


        dialog = new HuiFuDialog(this, "期权转出成功");

        keyBoardDialog = new KeyBoardDialog(this);

    }

    @Override
    public void initWeight() {
        super.initWeight();
        rlSelectFriends.setOnClickListener(this);
        tvOk.setOnClickListener(this);

        keyBoardDialog.setOnKeyBoardLinener(new KeyBoardDialog.OnKeyBoardLinener() {
            @Override
            public void onKey(String key) {
                verifyPass(key);
            }
        });

    }


    /**
     * 验证密码
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
                    transfer(strPassword);
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
     * 期权转出
     * create  wjz
     **/
    private void transfer(String strPassword) {
        requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/account/option/transfer");
        requestParams.addBodyParameter("toUserId", contactBean.getId());
        requestParams.addBodyParameter("amount", etvMoney.getText().toString());
        requestParams.addBodyParameter("password", strPassword);

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!Utils.callOk(result)) return;
                if (dialog != null) {
                    dialog.show();
                    keyBoardDialog.dismiss();
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.rl_select_friends:

                Intent intent = new Intent(this, FriendsListActivity.class);
                intent.putExtra(KEY_TYPE, TYPE_TRANSFER);

                startActivityForResult(intent, TYPE_TRANSFER);

                break;
            case R.id.tv_ok:
                String money = etvMoney.getText().toString();
                if ("".equals(money) || "0".equals(money)) {
                    return;
                }

                if (contactBean != null) {

                    keyBoardDialog.show();

                }

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;
        if (requestCode == TYPE_TRANSFER) {
            contactBean = (ContactBean) data.getSerializableExtra(KEY_TYPE);

            if (Utils.photoEmpty(contactBean.getAvatar())) {
                Picasso.with(this).load(Utils.GetPhotoPath(contactBean.getAvatar())).into(imgIcon);
            }
            tvName.setText(contactBean.getNick());
            llUser.setVisibility(View.VISIBLE);
        }
    }
}
