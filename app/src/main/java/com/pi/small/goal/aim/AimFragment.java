package com.pi.small.goal.aim;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.aim.activity.AddAimActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.entity.AimEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AimFragment extends Fragment implements View.OnClickListener {


    private ImageView right_image;

    private List<AimEntity> dataList;
    private Map<String, String> map;

    private AimEntity aimEntity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_aim, container, false);
        View topView = view.findViewById(R.id.view);

        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion >= 19) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            topView.setVisibility(View.GONE);
        }

        dataList = new ArrayList<AimEntity>();


        right_image = (ImageView) view.findViewById(R.id.right_image);

        right_image.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_image:
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddAimActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void GetAim() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Aim);
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("userId", Utils.UserSharedPreferences(getActivity()).getString("id", ""));
        requestParams.addBodyParameter("aimId", "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=============GetAim=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            aimEntity = new AimEntity();
                            aimEntity.setId(jsonArray.getJSONObject(i).getString("id"));
                            aimEntity.setName(jsonArray.getJSONObject(i).getString("name"));
                            aimEntity.setBudget(jsonArray.getJSONObject(i).getString("budget"));
                            aimEntity.setMoney(jsonArray.getJSONObject(i).getString("money"));
                            aimEntity.setCycle(jsonArray.getJSONObject(i).getString("cycle"));
                            aimEntity.setCurrent(jsonArray.getJSONObject(i).getString("current"));
                            aimEntity.setUserId(jsonArray.getJSONObject(i).getString("userId"));

                            aimEntity.setProvince(jsonArray.getJSONObject(i).getString("province"));
                            aimEntity.setCity(jsonArray.getJSONObject(i).getString("city"));
                            aimEntity.setBrief(jsonArray.getJSONObject(i).getString("brief"));
                            aimEntity.setPosition(jsonArray.getJSONObject(i).getString("position"));
                            aimEntity.setLongitude(jsonArray.getJSONObject(i).getString("longitude"));
                            aimEntity.setLatitude(jsonArray.getJSONObject(i).getString("latitude"));
                            aimEntity.setSupport(jsonArray.getJSONObject(i).getString("support"));
                            aimEntity.setCreateTime(jsonArray.getJSONObject(i).getString("createTime"));
                            aimEntity.setStatus(jsonArray.getJSONObject(i).getString("status"));
                            aimEntity.setImg(jsonArray.getJSONObject(i).getString("img"));

                            dataList.add(aimEntity);
                        }


                    } else {
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex.getMessage() != null) {
                    Utils.showToast(getActivity(), ex.getMessage());
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
}
