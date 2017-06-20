package com.pi.small.goal.aim.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.ChoosePhotoActivity;
import com.pi.small.goal.utils.Code;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.utils.entity.AimEntity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static me.nereo.multi_image_selector.MultiImageSelectorActivity.EXTRA_RESULT;

public class SupportAimActivity extends BaseActivity {

    private ImageView left_image;
    private TextView right_text;

    private EditText content_edit;
    private ImageView photo_image;
    private RelativeLayout position_layout;
    private TextView position_text;

    private RelativeLayout money_layout;
    private TextView money_text;

    private String aimId;
    private String money;
    private String budget;

    private String position = "";
    private String city = "";
    private String province = "";


    private String brief = "";
    private String img = "";
    private String imgLoad = "";


    private ArrayList<AimEntity> aimList;

    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    final public static int REQUEST_CODE_ASK_CALL_STORGE = 124;
    private ImageView photo2_image;
    private ImageView photo3_image;

    private List<String> selectPhotoPaths = new ArrayList<String>();

    private String img1 = "";
    private String img2 = "";
    private String img3 = "";

    private String payMoney = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_support_aim);
        super.onCreate(savedInstanceState);
        aimId = getIntent().getStringExtra("aimId");
        budget = getIntent().getStringExtra("budget");
        money = getIntent().getStringExtra("money");
        aimList = new ArrayList<AimEntity>();
        init();
    }

    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);
        right_text = (TextView) findViewById(R.id.right_text);

        content_edit = (EditText) findViewById(R.id.content_edit);
        photo_image = (ImageView) findViewById(R.id.photo1_image);
        photo2_image = (ImageView) findViewById(R.id.photo2_image);
        photo3_image = (ImageView) findViewById(R.id.photo3_image);
        position_layout = (RelativeLayout) findViewById(R.id.position_layout);
        position_text = (TextView) findViewById(R.id.position_text);
        money_layout = (RelativeLayout) findViewById(R.id.money_layout);
        money_text = (TextView) findViewById(R.id.money_text);

        left_image.setOnClickListener(this);
        right_text.setOnClickListener(this);

        position_layout.setOnClickListener(this);
        photo_image.setOnClickListener(this);
        photo2_image.setOnClickListener(this);
        photo3_image.setOnClickListener(this);
        money_layout.setOnClickListener(this);
        initImageView();

    }

    private void initImageView() {

        int WindowWidth = getWindowManager().getDefaultDisplay().getWidth();
        int i = WindowWidth - Utils.dip2px(this, 80);
        int width = i / 3;
        photo_image.getLayoutParams().width = width;
        photo_image.getLayoutParams().height = width;
        photo2_image.getLayoutParams().width = width;
        photo2_image.getLayoutParams().height = width;
        photo3_image.getLayoutParams().width = width;
        photo3_image.getLayoutParams().height = width;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.left_image:
                intent.putExtra("money", "0");
                setResult(Code.SupportAim, intent);
                finish();
                break;
            case R.id.right_text:
                System.out.println("=========dianji=========");

                if (payMoney.equals("")) {
                    Utils.showToast(this, "请填写存入金额");

                } else {
                    if (selectPhotoPaths.size() > 0) {
                        UpPicture1();
                    } else {

                        intent.setClass(this, PayActivity.class);
                        intent.putExtra("aimId", aimId);
                        intent.putExtra("money", payMoney);
                        intent.putExtra("img1", img1);
                        intent.putExtra("img2", img2);
                        intent.putExtra("img3", img3);
                        intent.putExtra("content", content_edit.getText().toString().trim());
                        startActivityForResult(intent, Code.SupportAim);
                    }
                }


                break;
            case R.id.position_layout:

                intent.setClass(this, PositionActivity.class);
                startActivityForResult(intent, Code.PositionCode);

                break;
            case R.id.money_layout:
                GetBudget(v);
                break;

            case R.id.photo1_image:
            case R.id.photo2_image:
            case R.id.photo3_image:
                myStartActivity();

                break;
        }
    }

    private void myStartActivity() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 23) {

            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_CALL_STORGE);
                    return;
                } else {

                    intent.putExtra(ChoosePhotoActivity.KEY_TYPE, ChoosePhotoActivity.TYPE_MORE);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 3 - selectPhotoPaths.size());
                    intent.setClass(this, ChoosePhotoActivity.class);
                    startActivityForResult(intent, Code.REQUEST_HEAD_CODE);
                }
            }
        } else {
            intent.putExtra(ChoosePhotoActivity.KEY_TYPE, ChoosePhotoActivity.TYPE_MORE);
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 3 - selectPhotoPaths.size());
            intent.setClass(this, ChoosePhotoActivity.class);
            startActivityForResult(intent, Code.REQUEST_HEAD_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_CALL_STORGE);
                        return;
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra(ChoosePhotoActivity.KEY_TYPE, ChoosePhotoActivity.TYPE_MORE);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 3 - selectPhotoPaths.size());
                        intent.setClass(this, ChoosePhotoActivity.class);
                        startActivityForResult(intent, Code.REQUEST_HEAD_CODE);
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(SupportAimActivity.this, "您禁止了相机权限", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case REQUEST_CODE_ASK_CALL_STORGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Intent intent = new Intent();
                    intent.putExtra(ChoosePhotoActivity.KEY_TYPE, ChoosePhotoActivity.TYPE_MORE);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 3 - selectPhotoPaths.size());
                    intent.setClass(this, ChoosePhotoActivity.class);
                    startActivityForResult(intent, Code.REQUEST_HEAD_CODE);
                } else {
                    // Permission Denied
                    Toast.makeText(SupportAimActivity.this, "您禁止了写入权限", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Code.RESULT_CAMERA_CODE) {
            imgLoad = data.getStringExtra("path");
            System.out.println("===============CAMERA======imgLoad======" + imgLoad);
            photo_image.setImageBitmap(BitmapFactory.decodeFile(imgLoad));
            selectPhotoPaths.add(imgLoad);
            setPhoto(selectPhotoPaths);
        } else if (resultCode == Code.RESULT_GALLERY_CODE) {
            imgLoad = data.getStringExtra("path");
            System.out.println("===============imgLoad============" + imgLoad);
            if ("".equals(imgLoad)) {
                ArrayList<String> morePhotoDatas = data.getStringArrayListExtra(EXTRA_RESULT);
                selectPhotoPaths.addAll(morePhotoDatas);
                setPhoto(selectPhotoPaths);
            } else {
                photo_image.setImageBitmap(BitmapFactory.decodeFile(imgLoad));
                selectPhotoPaths.add(imgLoad);
                setPhoto(selectPhotoPaths);
            }
        } else if (resultCode == Code.SupportAim) {
            Intent intent = new Intent();
            intent.putExtra("money", payMoney);
            setResult(Code.SupportAim, intent);
            finish();
        } else if (resultCode == Code.FailCode) {
            Intent intent = new Intent();
            intent.putExtra("money", "0");
            setResult(Code.SupportAim, intent);
            finish();
        } else if (resultCode == Code.PositionCode) {
            if (data.getStringExtra("position").equals("")) {
                position = "";
                province = "";
                city = "";
                position_text.setText("不显示");
            } else {
                position = data.getStringExtra("position");
                province = data.getStringExtra("province");
                city = data.getStringExtra("city");
                position_text.setText(position);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setPhoto(List<String> morePhotoDatas) {
        if (morePhotoDatas.size() > 0) {
            photo_image.setImageBitmap(BitmapFactory.decodeFile(morePhotoDatas.get(0)));
            photo_image.setClickable(false);
            photo2_image.setVisibility(View.VISIBLE);
        }
        if (morePhotoDatas.size() > 1) {
            photo2_image.setImageBitmap(BitmapFactory.decodeFile(morePhotoDatas.get(1)));
            photo2_image.setClickable(false);
            photo3_image.setVisibility(View.VISIBLE);
        }
        if (morePhotoDatas.size() > 2) {
            photo3_image.setImageBitmap(BitmapFactory.decodeFile(morePhotoDatas.get(2)));
            photo3_image.setClickable(false);
        }
    }

    private void UpPicture1() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.UpPicture);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("token", Utils.GetToken(this));
        requestParams.addBodyParameter("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("picture", new File(selectPhotoPaths.get(0)));
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=========UpPicture1=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        img1 = new JSONObject(result).getJSONObject("result").getString("path");

                        if (selectPhotoPaths.size() > 1) {
                            UpPicture2();
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(SupportAimActivity.this, PayActivity.class);
                            intent.putExtra("aimId", aimId);
                            intent.putExtra("money", payMoney);
                            intent.putExtra("img1", img1);
                            intent.putExtra("img2", img2);
                            intent.putExtra("img3", img3);
                            intent.putExtra("content", content_edit.getText().toString().trim());
                            startActivityForResult(intent, Code.SupportAim);
                        }


                    } else {
                        Utils.showToast(SupportAimActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!ex.getMessage().equals("")) {
                    Utils.showToast(SupportAimActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void UpPicture2() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.UpPicture);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("token", Utils.GetToken(this));
        requestParams.addBodyParameter("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("picture", new File(selectPhotoPaths.get(1)));
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=========photo=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

                        img2 = new JSONObject(result).getJSONObject("result").getString("path");
                        if (selectPhotoPaths.size() > 2) {
                            UpPicture3();
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(SupportAimActivity.this, PayActivity.class);
                            intent.putExtra("aimId", aimId);
                            intent.putExtra("money", payMoney);
                            intent.putExtra("img1", img1);
                            intent.putExtra("img2", img2);
                            intent.putExtra("img3", img3);
                            intent.putExtra("content", content_edit.getText().toString().trim());
                            startActivityForResult(intent, Code.SupportAim);
                        }
                    } else {
                        Utils.showToast(SupportAimActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!ex.getMessage().equals("")) {
                    Utils.showToast(SupportAimActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void UpPicture3() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.UpPicture);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("token", Utils.GetToken(this));
        requestParams.addBodyParameter("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("picture", new File(selectPhotoPaths.get(2)));
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=========photo=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

                        img3 = new JSONObject(result).getJSONObject("result").getString("path");
                        Intent intent = new Intent();
                        intent.setClass(SupportAimActivity.this, PayActivity.class);
                        intent.putExtra("aimId", aimId);
                        intent.putExtra("money", payMoney);
                        intent.putExtra("img1", img1);
                        intent.putExtra("img2", img2);
                        intent.putExtra("img3", img3);
                        intent.putExtra("content", content_edit.getText().toString().trim());
                        startActivityForResult(intent, Code.SupportAim);
                    } else {
                        Utils.showToast(SupportAimActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!ex.getMessage().equals("")) {
                    Utils.showToast(SupportAimActivity.this, ex.getMessage());
                }
            }


            @Override
            public void onFinished() {

            }
        });
    }

    private void GetBudget(View view) {


        View windowView = LayoutInflater.from(this).inflate(
                R.layout.window_aim_budget, null);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        TextView title_text = (TextView) windowView.findViewById(R.id.title_text);
        TextView cancel_text = (TextView) windowView.findViewById(R.id.cancel_text);
        TextView ok_text = (TextView) windowView.findViewById(R.id.ok_text);
        final EditText money_edit = (EditText) windowView.findViewById(R.id.money_edit);
        title_text.setText("存入金额");
        money_edit.setHint("最少存入10元");
        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ok_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (money_edit.getText().toString().trim().equals("")) {
                    popupWindow.dismiss();
                } else {
                    if (Float.valueOf(money_edit.getText().toString().trim()) < (Float.valueOf(10))) {
                        Utils.showToast(SupportAimActivity.this, "存入金额不得小于10元");
                    } else {
                        payMoney = money_edit.getText().toString().trim();
                        money_text.setText(payMoney);
                        popupWindow.dismiss();
                    }
                }
            }
        });

        windowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setAnimationStyle(R.style.MyDialogStyle);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_empty));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            Intent intent = new Intent();
            intent.putExtra("money", "0");
            setResult(Code.SupportAim, intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }
}
