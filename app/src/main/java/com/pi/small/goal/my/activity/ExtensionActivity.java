package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pi.small.goal.R;
import com.pi.small.goal.my.dialog.ExtensionDialog;
import com.pi.small.goal.utils.BaseActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/14 16:10
 * 修改：
 * 描述：推广码
 **/
public class ExtensionActivity extends BaseActivity {

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
    @InjectView(R.id.img_icon)
    CircleImageView imgIcon;
    @InjectView(R.id.tv_userName)
    TextView tvUserName;
    @InjectView(R.id.tv_agent)
    TextView tvAgent;
    @InjectView(R.id.tv_extension)
    TextView tvExtension;
    private ExtensionDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_extension);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        dialog = new ExtensionDialog(this);
    }

    @Override
    public void initWeight() {
        super.initWeight();
        tvAgent.setOnClickListener(this);
        tvExtension.setOnClickListener(this);
        dialog.setOnClickGoListener(new ExtensionDialog.onClickGoListener() {
            @Override
            public void onclick() {
                Intent intent = new Intent(ExtensionActivity.this, ExtensionPayActivity.class);
                intent.putExtra("money", "698");
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_agent:
//                View view = LayoutInflater.from(this).inflate(R.layout.dialog_hint, null);
//                new AlertDialog.Builder(this).setView(view)
//                        .setTitle("成为代理商")
//                        //   .setMessage("1.成为代理商需要支付698元;" + "\n" + "2.成为代理商后可以开展小目标分销业务，参与全民业绩分享;")
//                        .setPositiveButton("去支付", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //     unBindWx();
//                                dialog.dismiss();
//                                Intent intent = new Intent(ExtensionActivity.this, ExtensionPayActivity.class);
//                                intent.putExtra("money", "698");
//                                startActivity(intent);
//                            }
//                        }).show();
                dialog.show();
                break;
            case R.id.tv_extension:
                share();
                break;
        }
    }

    /**
     * 分享
     * create  wjz
     **/
    private void share() {

        new ShareAction(this).withText("hello")
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener).open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(ExtensionActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ExtensionActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ExtensionActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
