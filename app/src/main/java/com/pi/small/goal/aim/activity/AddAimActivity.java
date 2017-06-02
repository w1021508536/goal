package com.pi.small.goal.aim.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
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
import com.pi.small.goal.utils.ScrollerNumberPicker;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * JS
 */

public class AddAimActivity extends BaseActivity {

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

    private ArrayList<String> cycleList;

    private String position = "";
    private String city = "";
    private String province = "";


    private String name = "";
    private String budget = "";//预算
    private String cycle = "";//周期
    private String brief = "";
    private String img = "";
    private String imgLoad = "";

    private String cycle_window;

    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    final public static int REQUEST_CODE_ASK_CALL_STORGE = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aim);

        cycle_window = "6";
        cycleList = new ArrayList<String>();
        for (int i = 1; i < 25; i++) {
            cycleList.add(String.valueOf(i));

        }

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
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.right_text:
                System.out.println("=========dianji=========");
                name = aim_edit.getText().toString().trim();
                if (name.equals("")) {
                    Utils.showToast(this, "目标名称不可为空");
                } else {
                    if (cycle.equals("")) {
                        Utils.showToast(this, "周期不可为空");
                    } else {
                        if (budget.equals("")) {
                            Utils.showToast(this, "预算不可为空");
                        } else {
                            brief = content_edit.getText().toString().trim();
                            if (imgLoad.equals("")) {
                                NewAim();
                            } else {
                                UpPicture();
                            }
                        }
                    }

                }

                break;
            case R.id.position_layout:

                intent.setClass(this, PositionActivity.class);
                startActivityForResult(intent, Code.PositionCode);

                break;
            case R.id.photo_image:
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
                            intent.setClass(this, ChoosePhotoActivity.class);
                            startActivityForResult(intent, Code.REQUEST_HEAD_CODE);
                        }
                    }
                } else {
                    intent.setClass(this, ChoosePhotoActivity.class);
                    startActivityForResult(intent, Code.REQUEST_HEAD_CODE);
                }

                break;
            case R.id.cycle_layout:
                GetCycle(v);
                break;
            case R.id.budget_layout:
                GetBudget(v);
                break;
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
                        intent.setClass(this, ChoosePhotoActivity.class);
                        startActivityForResult(intent, Code.REQUEST_HEAD_CODE);
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(AddAimActivity.this, "您禁止了相机权限", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case REQUEST_CODE_ASK_CALL_STORGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Intent intent = new Intent();
                    intent.setClass(this, ChoosePhotoActivity.class);
                    startActivityForResult(intent, Code.REQUEST_HEAD_CODE);
                } else {
                    // Permission Denied
                    Toast.makeText(AddAimActivity.this, "您禁止了写入权限", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Code.PositionCode) {

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
        } else if (resultCode == Code.RESULT_CAMERA_CODE) {
            imgLoad = data.getStringExtra("path");
            System.out.println("===============CAMERA======imgLoad======" + imgLoad);
            photo_image.setImageBitmap(BitmapFactory.decodeFile(imgLoad));
        } else if (resultCode == Code.RESULT_GALLERY_CODE) {
            imgLoad = data.getStringExtra("path");
            System.out.println("===============imgLoad============" + imgLoad);
            photo_image.setImageBitmap(BitmapFactory.decodeFile(imgLoad));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void UpPicture() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.UpPicture);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("token", Utils.GetToken(this));
        requestParams.addBodyParameter("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("picture", new File(imgLoad));
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=========photo=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

                        img = new JSONObject(result).getJSONObject("result").getString("path");
                        NewAim();
                    } else {
                        Utils.showToast(AddAimActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!ex.getMessage().equals("")) {
                    Utils.showToast(AddAimActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void NewAim() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Aim);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("name", name);
        requestParams.addBodyParameter("budget", budget);
        requestParams.addBodyParameter("cycle", cycle);
        requestParams.addBodyParameter("city", city);
        requestParams.addBodyParameter("position", position);
        requestParams.addBodyParameter("brief", brief);
        requestParams.addBodyParameter("province", province);
        requestParams.addBodyParameter("img", img);
        x.http().request(HttpMethod.PUT, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println("==============aim=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {


                    } else {
                        Utils.showToast(AddAimActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex.getMessage() != null) {
                    Utils.showToast(AddAimActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

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

        TextView cancel_text = (TextView) windowView.findViewById(R.id.cancel_text);
        TextView ok_text = (TextView) windowView.findViewById(R.id.ok_text);
        final EditText money_edit = (EditText) windowView.findViewById(R.id.money_edit);

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
                    if (Integer.valueOf(money_edit.getText().toString().trim()) < 1000) {
                        Utils.showToast(AddAimActivity.this, "目标预算不得小于1000");
                    } else if (Integer.valueOf(money_edit.getText().toString().trim()) > 100000) {
                        Utils.showToast(AddAimActivity.this, "目标预算不得大于100000");
                    } else {
                        budget = money_edit.getText().toString().trim();
                        budget_text.setText(budget);
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

    private void GetCycle(View view) {

        View windowView = LayoutInflater.from(this).inflate(
                R.layout.window_aim_cycle, null);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        TextView cancel_text = (TextView) windowView.findViewById(R.id.cancel_text);
        TextView ok_text = (TextView) windowView.findViewById(R.id.ok_text);
        ScrollerNumberPicker cycle_picker = (ScrollerNumberPicker) windowView.findViewById(R.id.cycle_picker);
        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ok_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("=============cycle_window=====" + cycle_window);
                cycle = cycle_window;
                cycle_text.setText(cycle);
                popupWindow.dismiss();
            }
        });
        cycle_picker.setData(cycleList);
        cycle_picker.setDefault(5);
        right_text.setOnClickListener(this);
        left_image.setOnClickListener(this);

        cycle_picker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                cycle_window = text;
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

//        windowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
        popupWindow.setAnimationStyle(R.style.MyDialogStyle);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_empty));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);


    }

}