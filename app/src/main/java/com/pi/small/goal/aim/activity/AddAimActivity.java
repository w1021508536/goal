package com.pi.small.goal.aim.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.pi.small.goal.MainActivity;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.ChoosePhotoActivity;
import com.pi.small.goal.utils.Code;
import com.pi.small.goal.utils.ScrollerNumberPicker;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.utils.entity.AimEntity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;

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

    private ArrayList<AimEntity> aimList;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;

    public AMapLocationClientOption mLocationOption;

    private SharedPreferences userSharedPreferences;
    private SharedPreferences.Editor userEditor;

    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    final public static int REQUEST_CODE_ASK_CALL_STORGE = 124;
    final public static int REQUEST_CODE_ASK_CALL_POSITION = 125;

    private int photoFrom = 0;
    private ImageOptions imageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_aim);
        super.onCreate(savedInstanceState);
        userSharedPreferences = Utils.UserSharedPreferences(this);
        userEditor = userSharedPreferences.edit();
        imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.image2)
                .setFailureDrawableId(R.drawable.image1)
                .build();
        aimList = new ArrayList<AimEntity>();

        cycle_window = "6";
        cycleList = new ArrayList<String>();
        for (int i = 1; i < 25; i++) {
            cycleList.add(String.valueOf(i));
        }

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        mLocationClient = new AMapLocationClient(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//        //启动定位
//        mLocationClient.startLocation();

        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {


                if (aMapLocation != null) {
                    //解析定位结果
                    if (aMapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        aMapLocation.getLatitude();//获取纬度
                        aMapLocation.getLongitude();//获取经度
                        aMapLocation.getAccuracy();//获取精度信息

                        userEditor.putString("latitude", String.valueOf(aMapLocation.getLatitude()));
                        userEditor.putString("longitude", String.valueOf(aMapLocation.getLongitude()));
                        if (aMapLocation.getCity().substring(aMapLocation.getCity().length() - 1, aMapLocation.getCity().length()).equals("市")) {
                            userEditor.putString("city", aMapLocation.getCity().substring(0, aMapLocation.getCity().length() - 1));
                        } else {
                            userEditor.putString("city", aMapLocation.getCity());
                        }
                        userEditor.commit();
                        Intent intent = new Intent();
                        intent.setClass(AddAimActivity.this, PositionActivity.class);
                        startActivityForResult(intent, Code.PositionCode);

                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                        Log.e("AmapError", "location Error, ErrCode:"
//                                + aMapLocation.getErrorCode() + ", errInfo:"
//                                + aMapLocation.getErrorInfo());

                        Utils.showToast(AddAimActivity.this, "定位失败");

                    }
                }
                position_layout.setClickable(true);
            }
        };

        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

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
                intent.putExtra("aim", aimList);
                setResult(Code.AddAimCode, intent);
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
                            right_text.setClickable(false);
                            if (imgLoad.equals("")) {
                                NewAim(v);
                            } else {
                                UpPicture(v);
                            }
                        }
                    }
                }


                break;
            case R.id.position_layout:
                position_layout.setClickable(false);
                if (Utils.UserSharedPreferences(this).getString("longitude", "").equals("")) {
                    if (Build.VERSION.SDK_INT >= 23) {

                        int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
                        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_CALL_POSITION);
                            return;
                        } else {
                            mLocationClient.startLocation();
                        }
                    } else {
                        mLocationClient.startLocation();
                    }
                } else {
                    intent.setClass(this, PositionActivity.class);
                    startActivityForResult(intent, Code.PositionCode);
                }


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
                    Utils.showToast(AddAimActivity.this, "您禁止了相机权限");
                }
                break;
            case REQUEST_CODE_ASK_CALL_STORGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Intent intent = new Intent();
                    intent.setClass(this, ChoosePhotoActivity.class);
                    startActivityForResult(intent, Code.REQUEST_HEAD_CODE);
                } else {
                    Utils.showToast(AddAimActivity.this, "您禁止了写入权限");
                }
                break;
            case REQUEST_CODE_ASK_CALL_POSITION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationClient.startLocation();
                } else {
                    position_layout.setClickable(true);
                    Utils.showToast(AddAimActivity.this, "您禁止了定位权限");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {
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
                    if (city.substring(city.length() - 1, city.length()).equals("市")) {
                        city = city.substring(0, city.length() - 1);
                    }
                    position_text.setText(position);
                }
            } else if (resultCode == Code.SupportAim) {
                aimList.get(0).setMoney(data.getStringExtra("money"));
                Intent intent = new Intent();
                intent.putExtra("aim", aimList);
                setResult(Code.AddAimCode, intent);
                finish();
            } else if (resultCode == Code.FailCode) {
                Intent intent = new Intent();
                intent.putExtra("aim", aimList);
                setResult(Code.AddAimCode, intent);
                finish();
            } else if (resultCode == Code.RESULT_CAMERA_CODE) {
                photoFrom = 0;
                imgLoad = data.getStringExtra("path");
                System.out.println("===============CAMERA======imgLoad======" + imgLoad);
                photo_image.setImageBitmap(BitmapFactory.decodeFile(imgLoad));
            } else if (resultCode == Code.RESULT_GALLERY_CODE) {
                photoFrom = 0;
                imgLoad = data.getStringExtra("path");
                System.out.println("===============imgLoad============" + imgLoad);
                photo_image.setImageBitmap(BitmapFactory.decodeFile(imgLoad));
            } else if (resultCode == Code.RESULT_OWM_CODE) {
                if (!data.getStringExtra("path").equals("")) {
                    photoFrom = 1;
                    img = data.getStringExtra("path");
                    x.image().bind(photo_image, Utils.GetPhotoPath(img), imageOptions);
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void UpPicture(final View view) {
        RequestParams requestParams = new RequestParams(Url.Url + Url.UpPicture);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("token", Utils.GetToken(this));
        requestParams.addBodyParameter("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("picture", new File(imgLoad));
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=========photo=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        img = new JSONObject(result).getJSONObject("result").getString("path");
                        NewAim(view);
                    } else {
                        right_text.setClickable(true);

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
                right_text.setClickable(true);
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void NewAim(final View view) {
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
        XUtil.put(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("==============aim=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

                        JSONObject jsonObject = new JSONObject(result).getJSONObject("result");
                        AimEntity aimEntity = new AimEntity();


                        aimEntity.setId(jsonObject.getString("id"));
                        aimEntity.setName(jsonObject.getString("name"));
                        aimEntity.setBudget(jsonObject.getString("budget"));
                        aimEntity.setMoney("0");
                        aimEntity.setCycle(jsonObject.getString("cycle"));
                        aimEntity.setCurrent("0");//
                        aimEntity.setUserId(jsonObject.getString("userId"));

                        aimEntity.setProvince(jsonObject.getString("province"));
                        aimEntity.setCity(jsonObject.getString("city"));
                        aimEntity.setBrief(jsonObject.getString("brief"));
                        aimEntity.setPosition(jsonObject.getString("position"));
                        aimEntity.setLongitude(jsonObject.optString("longitude"));
                        aimEntity.setLatitude(jsonObject.optString("latitude"));
                        aimEntity.setSupport("0");
                        aimEntity.setCreateTime(jsonObject.getString("createTime"));
                        aimEntity.setStatus("1");
                        aimEntity.setImg(jsonObject.getString("img"));

                        aimList.add(aimEntity);

                        right_text.setClickable(true);
                        PutAimMoneyWindow(view);
                    } else {
                        Utils.showToast(AddAimActivity.this, new JSONObject(result).getString("msg"));
                        right_text.setClickable(true);
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
                right_text.setClickable(true);
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


    //弹出框
    private void PutAimMoneyWindow(View view) {

        View windowView = LayoutInflater.from(this).inflate(
                R.layout.window_aim_money, null);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);

        Button cancel_text = (Button) windowView.findViewById(R.id.cancel_text);
        TextView ok_text = (TextView) windowView.findViewById(R.id.ok_text);
        ImageView delete_image = (ImageView) windowView.findViewById(R.id.delete_image);
        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent();
                intent.putExtra("aim", aimList);
                setResult(Code.AddAimCode, intent);
                finish();
            }
        });
        delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent();
                intent.putExtra("aim", aimList);
                setResult(Code.AddAimCode, intent);
                finish();
            }
        });
        ok_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
//                Intent intent = new Intent();
//                setResult(Code.AddAimCode, intent);
                Intent intent = new Intent();
                intent.setClass(AddAimActivity.this, SaveMoneyActivity.class);
                intent.putExtra("content", aimList.get(0).getBrief());
                intent.putExtra("aimId", aimList.get(0).getId());
                intent.putExtra("money", "0");
                intent.putExtra("budget", aimList.get(0).getBudget());
                intent.putExtra("img1", aimList.get(0).getImg());
                intent.putExtra("img2", "");
                intent.putExtra("img3", "");
                startActivityForResult(intent, Code.SupportAim);


            }
        });
        right_text.setOnClickListener(this);
        left_image.setOnClickListener(this);


//        windowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
        popupWindow.setAnimationStyle(R.style.MyDialogStyle);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(false);

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
            intent.putExtra("aim", aimList);
            setResult(Code.AddAimCode, intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }
}
