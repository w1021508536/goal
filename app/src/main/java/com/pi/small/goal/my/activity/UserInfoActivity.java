package com.pi.small.goal.my.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
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
import com.pi.small.goal.utils.XUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

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
    public final static int REQUESTCODE_DROP_IMAGE = 7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_user);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override

    public void initData() {

        nameTextInclude.setText(getResources().getString(R.string.title_user_activity));
        rightImageInclude.setVisibility(View.GONE);

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


    /**
     * 调用底层的图片裁剪成正方形
     * create  wjz
     **/

    private void doCrop(Uri mUri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);

        int size = list.size();

        if (size == 0) {
            Utils.showToast(this, "找不到图像");
            return;
        } else {

            //     intent.setClassName("com.android.camera", "com.android.camera.CropImage");
            intent.setData(mUri);
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);   // 裁剪框比例
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, REQUESTCODE_DROP_IMAGE);       //调用 onActivityResult      这个  方法
            } else {
                Utils.showToast(this, "调取图像失败");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = Utils.UserSharedPreferences(this);
        tvUsernameUser.setText(sp.getString(KeyCode.USER_NICK, ""));
        if (sp.getString(KeyCode.USER_AVATAR, "").length() != 0) {
            Picasso.with(this).load(Utils.GetPhotoPath(sp.getString(KeyCode.USER_AVATAR, ""))).into(iconUser);
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
//                Bitmap bitmap = ImageUtils.getBitmapFromUri(uri, this);
//                File file = ImageUtils.getSmallImageFile(this, bitmap, 1080, 1080, true);
//                iconUser.setImageBitmap(bitmap);
//                uploadFile(file);
                doCrop(uri);
            } else if (requestCode == Code.RESULT_CAMERA_CODE) {   //获取拍照的
                Uri uri = data.getData();
                if (uri == null) {
                    //use bundle to get data
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap

                        String xmb = ImageUtils.saveMyBitmap("xmb", photo);
                        Uri imageUri = FileProvider.getUriForFile(this, "com.pi.small.goal.FileProvider", new File(xmb));
                        doCrop(imageUri);
//                        File file = ImageUtils.getSmallImageFile(this, photo, 1080, 1080, true);
//                        iconUser.setImageBitmap(photo);
//                        uploadFile(file);
                    } else {
                        Toast.makeText(getApplicationContext(), "err****", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    //to do find the path of pic by uri
//                    Bitmap bitmap = ImageUtils.getBitmapFromUri(uri, this);
//                    File file = ImageUtils.getSmallImageFile(this, bitmap, 1080, 1080, true);
//                    iconUser.setImageBitmap(bitmap);
//                    uploadFile(file);
                    doCrop(uri);
                }

            } else if (requestCode == REQUESTCODE_DROP_IMAGE) {
                Bitmap bitmap = data.getParcelableExtra("data");
                //    File smallImageFile = ImageUtils.getSmallImageFile(this, bitmap, 1080, 1080, true);
                File file = ImageUtils.getSmallImageFile(this, bitmap, 1080, 1080, true);
                uploadFile(file);
            }
        }

    }

    /**
     * 上传头像
     * create  wjz
     **/

    private void updataIcon(final String iconPath) {
        requestParams.setUri(Url.Url + "/user");
        requestParams.addHeader(KeyCode.USER_TOKEN, sp.getString(KeyCode.USER_TOKEN, ""));
        requestParams.addHeader(KeyCode.USER_DEVICEID, MyApplication.deviceId);
        requestParams.addBodyParameter("avatar", iconPath);
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (callOk(result)) {
                    Utils.showToast(UserInfoActivity.this, getResources().getString(R.string.updata_ok));
                    SharedPreferences.Editor editor = sp.edit();
                    if (!RenameActivity.callOk(result)) return;
                    editor.putString(KeyCode.USER_AVATAR, iconPath);
                    editor.commit();
                    Picasso.with(UserInfoActivity.this).load(iconPath).into(iconUser);
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
     * 上传文件
     * create  wjz
     **/
    public void uploadFile(File file) {
        requestParams.setUri(Url.Url + "/file/picture");
        requestParams.addHeader(KeyCode.USER_TOKEN, sp.getString(KeyCode.USER_TOKEN, ""));
        requestParams.addHeader(KeyCode.USER_DEVICEID, MyApplication.deviceId);
        requestParams.addBodyParameter("picture", file);
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
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
            public void onFinished() {

            }
        });
    }
}
