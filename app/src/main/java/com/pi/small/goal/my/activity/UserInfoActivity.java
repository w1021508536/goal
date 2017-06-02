package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Code;
import com.pi.small.goal.utils.ImageUtils;
import com.pi.small.goal.utils.KeyCode;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pi.small.goal.my.activity.RenameActivity.TYPE_NICK;
import static com.pi.small.goal.my.activity.RenameActivity.callOk;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/5/31 17:48
 * 修改：
 * 描述：个人中心
 **/
public class UserInfoActivity extends BaseActivity {

    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_username_user)
    TextView tvUsernameUser;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.icon_user)
    CircleImageView iconUser;
    @InjectView(R.id.rl_icon_user)
    RelativeLayout rlIconUser;
    @InjectView(R.id.rl_username_user)
    RelativeLayout rlUsernameUser;
    @InjectView(R.id.rl_content_user)
    RelativeLayout rlContentUser;
    @InjectView(R.id.rl_denji_user)
    RelativeLayout rlDenjiUser;
    @InjectView(R.id.rl_wx_user)
    RelativeLayout rlWxUser;
    @InjectView(R.id.rl_phone_user)
    RelativeLayout rlPhoneUser;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.tv_brief_user)
    TextView tvBriefUser;
    @InjectView(R.id.ll_user)
    LinearLayout llUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_user);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override

    public void initData() {

        nameTextInclude.setText(getResources().getString(R.string.title_user_activity));

        view = findViewById(R.id.view);
        super.initData();
        //  x.image().bind(sp.getString("avatar", ""), iconUser);
    }

    @Override
    public void initWeight() {
        super.initWeight();
        leftImageInclude.setOnClickListener(this);
        rlUsernameUser.setOnClickListener(this);
        rlContentUser.setOnClickListener(this);
        rlIconUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        switch (v.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.rl_content_user:
                intent = new Intent(this, RenameActivity.class);
                intent.putExtra(RenameActivity.KEY_TYPE, RenameActivity.TYPE_CONTENT);
                startActivity(intent);
                break;
            case R.id.rl_username_user:
                intent = new Intent(this, RenameActivity.class);
                intent.putExtra(RenameActivity.KEY_TYPE, TYPE_NICK);
                startActivity(intent);
                break;
            case R.id.rl_icon_user:

                showPop();
                break;
        }
    }

    /**
     * 显示popuwindow
     * create  wjz
     **/

    private void showPop() {
        final PopupWindow popupWindow = new PopupWindow(llUser,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        View view = LayoutInflater.from(this).inflate(R.layout.popu_choose_photo, null);
        popupWindow.setContentView(view);

        popupWindow.setHeight(getWindowManager().getDefaultDisplay().getHeight());
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true); //点击其他地方dismiss
        popupWindow.showAsDropDown(llUser);

        TextView tv_album = (TextView) view.findViewById(R.id.tv_album_pop);  //选择相册
        TextView tv_camera = (TextView) view.findViewById(R.id.tv_camera_pop); //拍照

        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCamera();
                popupWindow.dismiss();
            }
        });

        tv_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goGallery();
                popupWindow.dismiss();
            }
        });

        view.findViewById(R.id.tv_cancel_pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
//        view.findViewById(R.id.rl_pop).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
    }

    /**
     * 获取相册
     */
    private void goGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Code.RESULT_GALLERY_CODE);
    }

    /**
     * 拍照获取照片
     */
    private void goCamera() {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
//        cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
//        startActivityForResult(cameraIntent, Code.RESULT_CAMERA_CODE);
        Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(getImageByCamera, Code.RESULT_CAMERA_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = Utils.UserSharedPreferences(this);
        tvUsernameUser.setText(sp.getString(KeyCode.USER_NICK, ""));
        if (sp.getString(KeyCode.USER_AVATAR, "").length() != 0) {
            Picasso.with(this).load(sp.getString(KeyCode.USER_AVATAR, "")).into(iconUser);
        }
        String brief = sp.getString(KeyCode.USER_BRIEF, "");
        tvBriefUser.setText(brief);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == Code.RESULT_GALLERY_CODE) {

                Uri uri = data.getData();
                Bitmap bitmap = ImageUtils.getBitmapFromUri(uri, this);
                File file = ImageUtils.getSmallImageFile(this, bitmap, 1080, 1080, true);
                iconUser.setImageBitmap(bitmap);
                uploadFile(file);
            } else if (requestCode == Code.RESULT_CAMERA_CODE) {   //获取拍照的
                Uri uri = data.getData();
                if (uri == null) {
                    //use bundle to get data
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                        //spath :生成图片取个名字和路径包含类型
                        File file = ImageUtils.getSmallImageFile(this, photo, 1080, 1080, true);
                        iconUser.setImageBitmap(photo);
                        uploadFile(file);
                    } else {
                        Toast.makeText(getApplicationContext(), "err****", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    //to do find the path of pic by uri
                    Bitmap bitmap = ImageUtils.getBitmapFromUri(uri, this);
                    File file = ImageUtils.getSmallImageFile(this, bitmap, 1080, 1080, true);
                    iconUser.setImageBitmap(bitmap);
                    uploadFile(file);
                }

            }
        }

    }

    /**
     * 上传头像
     * create  wjz
     **/

    private void updataIcon(final String iconPath) {
        final SharedPreferences sp = Utils.UserSharedPreferences(this);
        RequestParams requestParams = new RequestParams(Url.Url + "/user");
        requestParams.addHeader(KeyCode.USER_TOKEN, sp.getString(KeyCode.USER_TOKEN, ""));
        requestParams.addHeader(KeyCode.USER_DEVICEID, MyApplication.deviceId);
        requestParams.addBodyParameter("avatar", iconPath);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (callOk(result)) {
                    Utils.showToast(UserInfoActivity.this, getResources().getString(R.string.updata_ok));

                    SharedPreferences.Editor editor = sp.edit();

                    if (!RenameActivity.callOk(result)) return;
                    editor.putString(KeyCode.USER_AVATAR, iconPath);
                    editor.commit();

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 上传文件
     * create  wjz
     **/
    public void uploadFile(File file) {
        final SharedPreferences sp = Utils.UserSharedPreferences(this);
        RequestParams requestParams = new RequestParams(Url.Url + "/file/picture");
        requestParams.addHeader(KeyCode.USER_TOKEN, sp.getString(KeyCode.USER_TOKEN, ""));
        requestParams.addHeader(KeyCode.USER_DEVICEID, MyApplication.deviceId);
        requestParams.addBodyParameter("picture", file);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject obj = (JSONObject) new JSONObject(result).get("result");
                    String path = (String) obj.get("path");
                    updataIcon(path);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
