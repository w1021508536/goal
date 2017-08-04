package com.small.small.goal.my.mall.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.dialog.DuihuanQuerenDialog;
import com.small.small.goal.my.mall.activity.AddAddressAcivity;
import com.small.small.goal.my.mall.adapter.PhoneAddmoneyAdapter;
import com.small.small.goal.search.activity.InviteActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Code;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.dialog.DialogClickListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/7 14:13
 * 修改：
 * 描述：
 **/
public class PhoneAddmoneyFragment extends Fragment implements View.OnClickListener {
    @InjectView(R.id.etv_phone)
    EditText etvPhone;
    @InjectView(R.id.img_people)
    ImageView imgPeople;
    @InjectView(R.id.gv)
    GridView gv;
    @InjectView(R.id.tv_myMoney)
    TextView tvMyMoney;
    @InjectView(R.id.tv_duihuan_ok)
    TextView tvDuihuanOk;
    @InjectView(R.id.ll_bottom)
    LinearLayout llBottom;

    //    @InjectView(R.id.etv_phone)
//    EditText etvPhone;
//    @InjectView(R.id.tv_duihuan1)
//    TextView tvDuihuan1;
//    @InjectView(R.id.tv_duihuan2)
//    TextView tvDuihuan2;
//    @InjectView(R.id.tv_duihuan3)
//    TextView tvDuihuan3;
//    @InjectView(R.id.tv_duihuan4)
//    TextView tvDuihuan4;
//
    public static final int TYPE_HUAFEI = 1;
    public static final int TYPE_LIULIANG = 2;
    @InjectView(R.id.tv_company)
    TextView tvCompany;


    private LinearLayout[] lls;
    private int clickPosition = -1;
    private int oldlickPosition;
    private DuihuanQuerenDialog duihuanQuerenDialog;
    private String[] liuliangArray = new String[]{"10M", "50M", "500M", "1G"};
    private phoneAddmoney[] huafeiArray = new phoneAddmoney[]{new phoneAddmoney(0, "20元", 20, 2400), new phoneAddmoney(0, "50元", 50, 6000), new phoneAddmoney(0, "100元", 100, 12000), new phoneAddmoney(0, "200元", 200, 24000)};
    private int type;     //区分是话费还是流量充值
    private String money = "";
    private PhoneAddmoneyAdapter addmoneyAdapter;
    private phoneAddmoney selectEntity;

    public static PhoneAddmoneyFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt(KeyCode.INTENT_FLAG, type);
        PhoneAddmoneyFragment fragment = new PhoneAddmoneyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_addmoney, null);
        ButterKnife.inject(this, view);
        initData();
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvMyMoney.setText("金豆金额：" + CacheUtil.getInstance().getUserInfo().getAccount().getBean());
    }

    private void initListener() {
        imgPeople.setOnClickListener(this);
        tvDuihuanOk.setOnClickListener(this);


        duihuanQuerenDialog.setOnClick(new DialogClickListener() {
            @Override
            public void onClick(String str) {
                if (type == TYPE_HUAFEI)
                    echargerPhone();
                else {
                    if (selectEntity != null)
                        echargerLiulinag();
                }

            }
        });
        etvPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 11) {
                    getPhoneAddress(s.toString());

                } else {
                    tvCompany.setText("");
                }
            }
        });

        addmoneyAdapter.setOnLLClickListener(new PhoneAddmoneyAdapter.OnLLClickListener() {
            @Override
            public void onClick(phoneAddmoney entity, int position) {
                PhoneAddmoneyFragment.this.selectEntity = entity;

                if (type == TYPE_HUAFEI) {
                    money = String.valueOf(huafeiArray[position].getV());
                    duihuanQuerenDialog.setMoney((int) entity.getPrice() + "");
                } else {
                    duihuanQuerenDialog.setMoney(entity.getBean() + "");
                }
                if (entity.isType()) {
                    llBottom.setVisibility(View.VISIBLE);
                } else {
                    llBottom.setVisibility(View.GONE);
                }
            }

        });
    }


    /**
     * 查询手机归属地
     * create  wjz
     **/
    private void getPhoneAddress(final String phone) {//494244960cb626aacce92ec1b43d056d

        RequestParams requestParams = new RequestParams(Url.PHONE_ADDRESS);
        requestParams.addHeader("apikey", "6409f5ac5248e7f6531dba192aa62c72");
        requestParams.addBodyParameter("shouji", phone);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("===========归属地===========" + result);

                try {
                    if (new JSONObject(result).getString("status").equals("0")) {
                        JSONObject obj = new JSONObject(result).getJSONObject("result");
                        String province = obj.getString("province");
                        String company = obj.getString("company");
                        tvCompany.setText(province + " " + company.replace("中国", ""));
                        if (type == TYPE_LIULIANG)
                            getChargeFlows(phone);
                    } else {
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                    }
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

    /**
     * 查询可充值流量套餐
     * create  wjz
     **/
    private void getChargeFlows(String phone) {
        RequestParams requestParams = new RequestParams(Url.Url + "/charge/flows");
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);

        requestParams.addBodyParameter("mobile", phone);


        XUtil.get(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=========流量套餐============" + result);

                if (Utils.callOk(result, getActivity())) {
                    ArrayList<phoneAddmoney> phoneAddmoneys = Utils.fromJsonList(Utils.getResultStr(result), phoneAddmoney.class);

                    addmoneyAdapter.setData(phoneAddmoneys);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("=========流量套餐========ex====" + ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 流量充值
     * create  wjz
     **/
    private void echargerLiulinag() {


        RequestParams requestParams = new RequestParams(Url.Url + "/charge/flow");
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("mobile", etvPhone.getText().toString());
        requestParams.addBodyParameter("fid", selectEntity.getId() + "");

        XUtil.post(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.print("========流量充值=========" + result);

                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        CacheUtil.getInstance().getUserInfo().getAccount().setBean(String.valueOf(Integer.valueOf(CacheUtil.getInstance().getUserInfo().getAccount().getBean()) - selectEntity.getBean()));
                        tvMyMoney.setText("金豆金额：" + CacheUtil.getInstance().getUserInfo().getAccount().getBean());
                        showMyToast();
                    } else {
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("===========流量充值====ex=======" + ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 手机充值
     * create  wjz
     **/
    private void echargerPhone() {
        RequestParams requestParams = new RequestParams(Url.Url + "/charge/phone");
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("mobile", etvPhone.getText().toString());
        requestParams.addBodyParameter("amount", money);
        System.out.println("===========充值手机==========" + money + "=======" + etvPhone.getText().toString());

        XUtil.post(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("========手机充值=========" + result);

                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        CacheUtil.getInstance().getUserInfo().getAccount().setBean(String.valueOf(Integer.valueOf(CacheUtil.getInstance().getUserInfo().getAccount().getBean()) - Integer.valueOf(money) * 120));
                        tvMyMoney.setText("金豆金额：" + CacheUtil.getInstance().getUserInfo().getAccount().getBean());
                        showMyToast();
                    } else {
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("===========手机充值====ex=======" + ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 显示兑换成功的toast
     * create  wjz
     **/
    private void showMyToast() {
        Toast toast = Toast.makeText(getActivity(),
                "兑换成功", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        LinearLayout toastView = (LinearLayout) toast.getView();
        TextView tv = (TextView) toastView.getChildAt(0);
        tv.setTextSize(Utils.dip2px(getActivity(), 8));
        ImageView imageCodeProject = new ImageView(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(Utils.dip2px(getActivity(), 40), Utils.dip2px(getActivity(), 10), Utils.dip2px(getActivity(), 40), Utils.dip2px(getActivity(), 10));
        imageCodeProject.setLayoutParams(layoutParams);
        imageCodeProject.setImageResource(R.mipmap.icon_success_green);
        toastView.addView(imageCodeProject, 0);
        toast.show();

    }

    private void initData() {

        type = getArguments().getInt(KeyCode.INTENT_FLAG, TYPE_HUAFEI);
        addmoneyAdapter = new PhoneAddmoneyAdapter(getActivity(), type);
        gv.setAdapter(addmoneyAdapter);

        List<phoneAddmoney> data = new ArrayList<>();
        if (type == TYPE_HUAFEI) {

            for (int i = 0; i < huafeiArray.length; i++) {
                data.add(huafeiArray[i]);
            }
            addmoneyAdapter.setData(data);
        } else {


        }
        duihuanQuerenDialog = new DuihuanQuerenDialog(getActivity(), "2000");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_duihuan_ok:
                duihuanQuerenDialog.show();
                break;
            case R.id.img_people:

//                Intent i = new Intent(Intent.ACTION_PICK);
//                i.setType("vnd.android.cursor.dir/phone");
//                startActivityForResult(i, 0);


                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS);
                    if (checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 102);
                        return;
                    } else {
                        int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CONTACTS);
                        if (checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_CONTACTS}, 103);
                            return;
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), InviteActivity.class);
                            intent.putExtra("type", "2");
                            startActivityForResult(intent, Code.CONTACTS);
                        }
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), InviteActivity.class);
                    intent.putExtra("type", "2");
                    startActivityForResult(intent, Code.CONTACTS);
                }

                break;
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        switch (requestCode) {
//            case 0:
//                if (data == null) {
//                    return;
//                }
//                Uri uri = data.getData();
//                Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
//                //  cursor.moveToFirst();
//
//                while (cursor.moveToNext()) {
//                    String usernumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    Log.d("TAG", "number" + usernumber);
//                    String userName = cursor.getString(0);
//                    etvPhone.setText(usernumber.replaceAll(" ", ""));
//                }
//
//
//                break;
//
//            default:
//                break;
//        }

        if (data != null) {
            if (requestCode == Code.CONTACTS) {
                etvPhone.setText(data.getExtras().getString("number", "").replace(" ", ""));
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 102:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CONTACTS);
                    if (checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_CONTACTS}, 103);
                        return;
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), InviteActivity.class);
                        intent.putExtra("type", "2");
                        startActivity(intent);
                    }
                } else {
                    Utils.showToast(getActivity(), "您禁止了读取权限");
                }
                break;
            case 103:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), InviteActivity.class);
                    intent.putExtra("type", "2");
                    startActivity(intent);
                } else {
                    Utils.showToast(getActivity(), "您禁止了写入权限");
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public class phoneAddmoney {

        /**
         * id : 49
         * p : 100M
         * v : 100
         * price : 9.95
         */

        private int id;
        private String p;
        private int v;
        private int price;
        private boolean type = false;
        private int bean;

        public phoneAddmoney(int id, String p, int v, int price, int bean) {
            this.id = id;
            this.p = p;
            this.v = v;
            this.price = price;
            this.bean = bean;
        }

        public phoneAddmoney(int id, String p, int v, int price) {
            this.id = id;
            this.p = p;
            this.v = v;
            this.price = price;
        }

        public phoneAddmoney(int id, String p, int v, int price, boolean type) {
            this.id = id;
            this.p = p;
            this.v = v;
            this.price = price;
            this.type = type;
        }

        public int getBean() {
            return bean;
        }

        public void setBean(int bean) {
            this.bean = bean;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public boolean isType() {
            return type;
        }

        public void setType(boolean type) {
            this.type = type;
        }
    }

}
