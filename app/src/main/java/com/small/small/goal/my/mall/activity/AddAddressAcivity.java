package com.small.small.goal.my.mall.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.mall.entity.Address;
import com.small.small.goal.my.mall.entity.JsonBean;
import com.small.small.goal.my.mall.entity.JsonFileReader;
import com.small.small.goal.search.activity.InviteActivity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Code;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.weight.SwitchView;

import org.json.JSONArray;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/7 13:19
 * 修改：
 * 描述：添加地址
 **/
public class AddAddressAcivity extends BaseActivity {

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
    @InjectView(R.id.ll_top_include)
    LinearLayout llTopInclude;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.rl_address)
    RelativeLayout rlAddress;
    @InjectView(R.id.img_people)
    ImageView imgPeople;
    @InjectView(R.id.etv_endPeople)
    EditText etvEndPeople;
    @InjectView(R.id.etv_phone)
    EditText etvPhone;
    @InjectView(R.id.etv_address)
    EditText etvAddress;
    @InjectView(R.id.sw)
    SwitchView sw;
    @InjectView(R.id.tv_ok)
    TextView tvOk;
    public String province = "";  //省
    public String city = "";      //市
    public String area = "";      //区
    @InjectView(R.id.rl_sw)
    RelativeLayout rlSw;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    //获取库Data表字段
    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};

    private Address addressEntity;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_address);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        init();
    }

    public void init() {
        nameTextInclude.setText("添加地址");
        rightImageInclude.setVisibility(View.GONE);

        addressEntity = (Address) getIntent().getSerializableExtra(KeyCode.INTENT_ADDRESSENTITY);
        position = getIntent().getIntExtra(KeyCode.INTENT_POSITION, 0);
        setAddressMore();

        initJsonData();

        rlAddress.setOnClickListener(this);
        imgPeople.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        sw.setOnSwitchChangeListener(new SwitchView.OnSwitchChangeListener() {
            @Override
            public void onSwitchChanged(boolean open) {
                if (addressEntity != null && addressEntity.getIsDefault() == 1 && !open) {
                    sw.setSwitchStatus(true);
                    Utils.showToast(AddAddressAcivity.this
                            , "您必须拥有一个默认地址");
                }
            }
        });
    }

    private void setAddressMore() {
        if (addressEntity == null) {
            rlSw.setVisibility(View.VISIBLE);
            return;
        }
        etvEndPeople.setText(addressEntity.getConsignee());
        etvPhone.setText(addressEntity.getMobile());
        tvAddress.setText(addressEntity.getProvince() + addressEntity.getCity() + addressEntity.getDistrict());
        etvAddress.setText(addressEntity.getAddress());
        sw.setSwitchStatus(addressEntity.getIsDefault() == 1 ? true : false);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_address:
                showPickerView();
                break;
            case R.id.img_people:
//                Intent i = new Intent(Intent.ACTION_PICK);
//                i.setType("vnd.android.cursor.dir/phone");
//                startActivityForResult(i, 0);
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                    if (checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 102);
                        return;
                    } else {
                        int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS);
                        if (checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, 103);
                            return;
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(this, InviteActivity.class);
                            intent.putExtra("type", "2");
                            startActivityForResult(intent, Code.CONTACTS);
                        }
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setClass(this, InviteActivity.class);
                    intent.putExtra("type", "2");
                    startActivityForResult(intent, Code.CONTACTS);
                }

                break;
            case R.id.tv_ok:

                if ("".equals(etvAddress.getText().toString()) || "".equals(etvEndPeople.getText().toString()) || "".equals(etvPhone.getText().toString()) || "请选择".equals(tvAddress.getText().toString())) {
                    return;
                }
                if (addressEntity == null) {

                    addAddress();


                } else {

                    setAddress();
                }

                break;
        }
    }

    /**
     * 修改已存在的地址
     * create  wjz
     **/
    private void setAddress() {

        if (sw.getSwitchStatus()) {
            for (int z = 0; z < CacheUtil.getInstance().getAddressData().size(); z++) {
                CacheUtil.getInstance().getAddressData().get(z).setIsDefault(0);
            }
        }

        addressEntity.setIsDefault(sw.getSwitchStatus() ? 1 : 0);
        addressEntity.setProvince(province);
        addressEntity.setCity(city);
        addressEntity.setDistrict(area);
        addressEntity.setAddress(etvAddress.getText().toString());
        addressEntity.setMobile(etvPhone.getText().toString());
        addressEntity.setConsignee(etvEndPeople.getText().toString());
        CacheUtil.getInstance().getAddressData().set(position, addressEntity);

        RequestParams requestParams = new RequestParams(Url.Url + Url.Address);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);

        requestParams.addBodyParameter("consignee", addressEntity.getConsignee());
        requestParams.addBodyParameter("province", addressEntity.getProvince());
        requestParams.addBodyParameter("city", addressEntity.getCity());
        requestParams.addBodyParameter("district", addressEntity.getDistrict());
        requestParams.addBodyParameter("address", addressEntity.getAddress());
        requestParams.addBodyParameter("mobile", addressEntity.getMobile());
        requestParams.addBodyParameter("isDefault", addressEntity.getIsDefault() + "");
        requestParams.addBodyParameter("addressId", addressEntity.getId() + "");
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                finish();
            }

            @Override
            public void onFinished() {
                finish();
            }
        });
    }

    /**
     * 添加地址
     * create  wjz
     **/
    private void addAddress() {

        final List<Address> addressData = CacheUtil.getInstance().getAddressData();
        final Address addressEntity = new Address(1, Utils.UserSharedPreferences(this).getString("id", ""), etvEndPeople.getText().toString(), etvPhone.getText().toString(), province, city, etvAddress.getText().toString(), sw.getSwitchStatus() ? 1 : 0, area);
        if (addressData.size() == 0) {
            addressEntity.setIsDefault(1);
        }
        CacheUtil.getInstance().setHaveDefaultAddress(true);
        //    int id, int userId, String consignee, String mobile, String province, String city, String address, int isDefault, String district
        //     CacheUtil.getInstance().addAddressData(addressEntity);

        RequestParams requestParams = new RequestParams(Url.Url + Url.Address);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);

        requestParams.addBodyParameter("consignee", addressEntity.getConsignee());
        requestParams.addBodyParameter("province", addressEntity.getProvince());
        requestParams.addBodyParameter("city", addressEntity.getCity());
        requestParams.addBodyParameter("district", addressEntity.getDistrict());
        requestParams.addBodyParameter("address", addressEntity.getAddress());
        requestParams.addBodyParameter("mobile", addressEntity.getMobile());
        requestParams.addBodyParameter("isDefault", addressEntity.getIsDefault() + "");

        XUtil.put(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (Utils.callOk(result, AddAddressAcivity.this)) {
                    CacheUtil.getInstance().setHaveDefaultAddress(true);
                    //    int id, int userId, String consignee, String mobile, String province, String city, String address, int isDefault, String district
                    CacheUtil.getInstance().addAddressData(addressEntity);
                }
                finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                finish();
            }

            @Override
            public void onFinished() {
                finish();
            }
        });

    }

    /**
     * 显示三级联动的选择城市
     * create  wjz
     **/
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String text = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(options2);
                area = options3Items.get(options1).get(options2).get(options3);
                tvAddress.setText(text);
            }
        }).setTitleText("")
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.GRAY)
                .setContentTextSize(20)
                .setOutSideCancelable(false)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {   //解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        //  获取json数据
        String JsonData = JsonFileReader.getJson(this, "province_data.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        switch (requestCode) {
//            case 0:   //选完联系人的回调
//                if (data == null) {
//                    return;
//                }
//                Uri uri = data.getData();
//                Cursor cursor = getContentResolver().query(uri, PHONES_PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
//                //  cursor.moveToFirst();
//
//                while (cursor.moveToNext()) {
//                    String usernumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    Log.d("TAG", "number" + usernumber);
//                    String userName = cursor.getString(0);
//                    //         String number = cursor.getString(cursor.getColumnIndexOrThrow(Phones.NUMBER));
//                    etvPhone.setText(usernumber.replace(" ", ""));
//                    etvEndPeople.setText(userName);
//
//                }
//
//
////                mContactText.setText(number);
////                mContactText.setSelection(number.length());
//                break;
//
//            default:
//                break;
//        }

        if (data != null) {
            if (requestCode == Code.CONTACTS) {
                etvEndPeople.setText(data.getExtras().getString("name", ""));
                etvPhone.setText(data.getExtras().getString("number", ""));
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 102:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS);
                    if (checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, 103);
                        return;
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(this, InviteActivity.class);
                        intent.putExtra("type", "2");
                        startActivity(intent);
                    }
                } else {
                    Utils.showToast(AddAddressAcivity.this, "您禁止了读取权限");
                }
                break;
            case 103:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setClass(this, InviteActivity.class);
                    intent.putExtra("type", "2");
                    startActivity(intent);
                } else {
                    Utils.showToast(AddAddressAcivity.this, "您禁止了写入权限");
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
