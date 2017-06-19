package com.pi.small.goal.my.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.dialog.HuiFuDialog;
import com.pi.small.goal.my.entry.UerEntity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.CacheUtil;
import com.pi.small.goal.utils.Code;
import com.pi.small.goal.utils.ImageUtils;
import com.pi.small.goal.utils.KeyCode;
import com.pi.small.goal.utils.ThirdUtils;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pi.small.goal.R.id.tv_wxBind_user;
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
    @InjectView(R.id.img_level_user)
    ImageView imgLevelUser;
    @InjectView(R.id.tv_phoneBind_user)
    TextView tvPhoneBindUser;
    @InjectView(R.id.tv_level_user)
    TextView tvLevelUser;
    @InjectView(tv_wxBind_user)
    TextView tvWxBindUser;
    @InjectView(R.id.black_layout)
    RelativeLayout blackLayout;

    private IWXAPI wx_api;

    public static final String BIND_WX = "bind_wx";
    final public static int REQUEST_CODE_CAMERA = 123;
    final public static int REQUEST_CODE_PHOTO = 124;
    private HuiFuDialog dialog;

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

        dialog = new HuiFuDialog(this, "微信解绑成功");

        UerEntity userInfo = CacheUtil.getInstance().getUserInfo();
        //微信
        wx_api = WXAPIFactory.createWXAPI(this, ThirdUtils.WX_APP_ID, true);
        wx_api.registerApp(ThirdUtils.WX_APP_ID);
        if (userInfo == null) return;
        String grade = userInfo.getGrade().replace("v", "");

        switch (Integer.valueOf(grade)) {
            case 0:
                imgLevelUser.setVisibility(View.GONE);
                tvLevelUser.setVisibility(View.VISIBLE);
                break;
            case 1:
                imgLevelUser.setImageResource(R.mipmap.icon_level_1);
                break;
            case 2:
                imgLevelUser.setImageResource(R.mipmap.icon_level_2);
                break;
            case 3:
                imgLevelUser.setImageResource(R.mipmap.icon_level_3);
                break;
            case 4:
                imgLevelUser.setImageResource(R.mipmap.icon_level_4);
                break;
            case 5:
                imgLevelUser.setImageResource(R.mipmap.icon_level_5);
                break;
            case 6:
                imgLevelUser.setImageResource(R.mipmap.icon_level_6);
                break;
            case 7:
                imgLevelUser.setImageResource(R.mipmap.icon_level_7);
                break;
            case 8:
                imgLevelUser.setImageResource(R.mipmap.icon_level_8);
                break;
        }
    }

    @Override
    public void initWeight() {
        super.initWeight();
        leftImageInclude.setOnClickListener(this);
        rlUsernameUser.setOnClickListener(this);
        rlContentUser.setOnClickListener(this);
        rlIconUser.setOnClickListener(this);
        rlDenjiUser.setOnClickListener(this);
        rlPhoneUser.setOnClickListener(this);
        rlWxUser.setOnClickListener(this);
        blackLayout.setOnClickListener(this);
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
            case R.id.rl_denji_user:
                startActivity(new Intent(this, LevelActivity.class));
                break;
            case R.id.rl_phone_user:
                if ("".equals(CacheUtil.getInstance().getUserInfo().getUser().getMobile())) {
                    startActivity(new Intent(this, BindPhoneActivity.class));
                } else {
                    startActivity(new Intent(this, UpdataPhoneActivity.class));
                }
                break;
            case R.id.rl_wx_user:

                String wx = CacheUtil.getInstance().getUserInfo().getUser().getWechatId();
                if ("".equals(wx)) {
                    wxLogin();
                } else {

                    new AlertDialog.Builder(this)
                            .setTitle("解绑微信")
                            .setMessage("确认解绑？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    unBindWx();
                                    dialog.dismiss();
                                }
                            }).show();
                }
                break;
            case R.id.black_layout:

                startActivity(new Intent(this, BlackListActivity.class));
                break;
        }
    }

    private void unBindWx() {

        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/user/unbind");
        requestParams.addBodyParameter("bindWay", "wechat");
        requestParams.addBodyParameter("verifyCode", "");
        requestParams.addBodyParameter("mobile", "");

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (Utils.callOk(result)) {
                    dialog.show();
                    tvWxBindUser.setText("解绑");
                    CacheUtil.getInstance().getUserInfo().getUser().setWechatId("");
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
     * 微信登陆
     * create  wjz
     **/
    private void wxLogin() {
        boolean isHaveWeixin = wx_api.isWXAppInstalled()
                && wx_api.isWXAppSupportAPI();
        if (isHaveWeixin) {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = BIND_WX;
            wx_api.sendReq(req);
        } else {
            Utils.showToast(this, "请先安装微信应用");
        }
    }


    /**
     * 显示popuwindow
     * create  wjz
     **/

    private void showPop() {
        final PopupWindow popupWindow = new PopupWindow(llUser,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.popu_choose_photo, null);
        popupWindow.setContentView(view);

        popupWindow.setWidth(getWindowManager().getDefaultDisplay().getWidth());
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true); //点击其他地方dismiss
        popupWindow.update();


        popupWindow.showAsDropDown(llUser, 0, -llUser.getHeight());

        TextView tv_album = (TextView) view.findViewById(R.id.tv_album_pop);  //选择相册
        TextView tv_camera = (TextView) view.findViewById(R.id.tv_camera_pop); //拍照

        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
                        return;
                    } else {
                        goCamera();
                    }
                } else {
                    goCamera();
                }

                popupWindow.dismiss();
            }
        });

        tv_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PHOTO);
                        return;
                    } else {
                        goGallery();
                    }
                } else
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
        UerEntity userInfo = CacheUtil.getInstance().getUserInfo();
        String mobile = CacheUtil.getInstance().getUserInfo().getUser().getMobile();
        String wechatId = CacheUtil.getInstance().getUserInfo().getUser().getWechatId();
        Log.v("TAG", wechatId + "");
        if (mobile == null || "".equals(mobile)) {
            tvPhoneBindUser.setText("未绑定");
        } else {
            tvPhoneBindUser.setText("已绑定");
            //     rlPhoneUser.setClickable(false);
        }
        if (wechatId == null || "".equals(wechatId)) {
            tvWxBindUser.setText("未绑定");
        } else {
            tvWxBindUser.setText("已绑定");
            //  rlWxUser.setClickable(false);
        }
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
                        Uri imageUri = Uri.fromFile(new File(xmb));
                        if (Integer.parseInt(Build.VERSION.SDK) >= 24) {
                            imageUri = FileProvider.getUriForFile(this, "com.pi.small.goal.FileProvider", new File(xmb));
                        }
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
                int scW = (int) (bitmap.getWidth() / (bitmap.getHeight() / 1080f));
                File file = ImageUtils.getSmallImageFile(this, bitmap, 640, 640, true);
                uploadFile(file);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goCamera();
                } else {
                    // Permission Denied
                    Toast.makeText(UserInfoActivity.this, "您禁止了相机权限", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case REQUEST_CODE_PHOTO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goCamera();
                } else {
                    // Permission Denied
                    Toast.makeText(UserInfoActivity.this, "您禁止了写入权限", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 上传头像
     * create  wjz
     **/

    private void updataIcon(final String iconPath) {
        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/user");
        requestParams.addBodyParameter("avatar", Url.PhotoUrl + "/" + iconPath);
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (callOk(result)) {
                    Utils.showToast(UserInfoActivity.this, getResources().getString(R.string.updata_ok));
                    SharedPreferences.Editor editor = sp.edit();
                    if (!RenameActivity.callOk(result)) return;
                    editor.putString(KeyCode.USER_AVATAR, Utils.GetPhotoPath(iconPath));
                    editor.commit();
                    Picasso.with(UserInfoActivity.this).load(Utils.GetPhotoPath(iconPath)).into(iconUser);
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
        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/file/picture");
        requestParams.addBodyParameter("token", Utils.GetToken(this));
        requestParams.addBodyParameter("deviceId", MyApplication.deviceId);
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
