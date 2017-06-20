package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.entity.MemberEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DistributionMemberActivity extends BaseActivity {

    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.member_list)
    ExpandableListView memberList;
    @InjectView(R.id.null_layout)
    RelativeLayout nullLayout;
    private List<MemberEntity> memberEntityList;
    private MemberEntity memberEntity;

    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_distribution_member);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        json = getIntent().getExtras().getString("json", "");

        memberEntityList = new ArrayList<>();
        init();
    }

    private void init() {
        if (!json.equals("")) {
            try {
                if (new JSONObject(json).getString("code").equals("100000")) {
                    nullLayout.setVisibility(View.VISIBLE);
                } else {
                    nullLayout.setVisibility(View.GONE);
                    JSONArray jsonArray = new JSONObject(json).getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        memberEntity = new MemberEntity();

                        memberEntity.setId(jsonArray.getJSONObject(i).getString("id"));
                        memberEntity.setUserId(jsonArray.getJSONObject(i).getString("userId"));
                        memberEntity.setPid(jsonArray.getJSONObject(i).getString("pid"));
                        memberEntity.setLevel(jsonArray.getJSONObject(i).getString("level"));
                        memberEntity.setSubCompanyId(jsonArray.getJSONObject(i).getString("subCompanyId"));
                        memberEntity.setVolume(jsonArray.getJSONObject(i).getString("volume"));
                        memberEntity.setNick(jsonArray.getJSONObject(i).getString("nick"));
                        memberEntity.setAvatar(jsonArray.getJSONObject(i).getString("avatar"));

                        memberEntityList.add(memberEntity);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @OnClick(R.id.left_image)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image:
                finish();
                break;
        }
    }
}
