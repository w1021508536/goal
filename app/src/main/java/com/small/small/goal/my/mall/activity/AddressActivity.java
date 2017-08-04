package com.small.small.goal.my.mall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.mall.adapter.AddressAdapter;
import com.small.small.goal.my.mall.entity.Address;
import com.small.small.goal.my.mall.entity.JsonBean;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/11 13:14
 * 修改：
 * 描述：地址
 **/
public class AddressActivity extends BaseActivity {

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
    @InjectView(R.id.lv)
    ListView lv;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private AddressAdapter adapter;

    private List<Address> addressList;
    private Address address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_address);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();

        addressList = new ArrayList<>();

        nameTextInclude.setText("收货地址");
        tvOkInclude.setText("添加地址");
        tvOkInclude.setVisibility(View.VISIBLE);
        rightImageInclude.setVisibility(View.GONE);
        llTopInclude.setBackgroundColor(getResources().getColor(R.color.bg_include));

//        CacheUtil.getInstance().addAddressData(new AddressEntity("1","2","3","4","wang","123",true));
//        CacheUtil.getInstance().addAddressData(new AddressEntity("1","2","3","4","wan g","12 3",false));
        adapter = new AddressAdapter(this);
        adapter.setData(CacheUtil.getInstance().getAddressData());
        lv.setAdapter(adapter);

        tvOkInclude.setOnClickListener(this);


        adapter.setOnAddressAdapterClickListener(new AddressAdapter.OnAddressAdapterClickListener() {
            @Override
            public void OnDeleteClick(String id, final int position) {
                RequestParams requestParams = new RequestParams(Url.Url + Url.AddressDelete);
                requestParams.addHeader("token", Utils.GetToken(AddressActivity.this));
                requestParams.addHeader("deviceId", MyApplication.deviceId);
                requestParams.addBodyParameter("addressId", id);
                requestParams.addBodyParameter("isDefault", "1");
                XUtil.get(requestParams, AddressActivity.this, new XUtil.XCallBackLinstener() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            if (new JSONObject(result).getString("code").equals("0")) {
                                Utils.showToast(AddressActivity.this, "删除成功");
                                CacheUtil.getInstance().deleteOneAddressData(position);
                                adapter.setData(CacheUtil.getInstance().getAddressData());
                            } else {
                                Utils.showToast(AddressActivity.this, new JSONObject(result).getString("msg"));
                            }
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
        });

        GetData();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_ok_include:
                startActivity(new Intent(this, AddAddressAcivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


    private void GetData() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Address);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("===========address=========" + result);

                try {
                    if (new JSONObject(result).getString("code").equals("0")) {

                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            address = new Address(Integer.valueOf(jsonArray.getJSONObject(i).getString("id")), jsonArray.getJSONObject(i).getString("userId"), jsonArray.getJSONObject(i).getString("consignee"), jsonArray.getJSONObject(i).getString("mobile"), jsonArray.getJSONObject(i).getString("province"), jsonArray.getJSONObject(i).getString("city"), jsonArray.getJSONObject(i).getString("address"), Integer.valueOf(jsonArray.getJSONObject(i).getString("isDefault")), jsonArray.getJSONObject(i).getString("district"));
                            addressList.add(address);
                        }
                        CacheUtil.getInstance().setAddressData(addressList);
                        adapter.setData(CacheUtil.getInstance().getAddressData());

                    } else {
                        Utils.showToast(AddressActivity.this, new JSONObject(result).getString("msg"));
                    }
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
