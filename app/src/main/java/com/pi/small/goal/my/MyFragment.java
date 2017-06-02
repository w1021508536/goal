package com.pi.small.goal.my;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.my.activity.CollectActivity;
import com.pi.small.goal.my.activity.FollowActivity;
import com.pi.small.goal.my.activity.RedActivity;
import com.pi.small.goal.my.activity.TargetActivity;
import com.pi.small.goal.my.activity.TaskActivity;
import com.pi.small.goal.my.activity.UserInfoActivity;
import com.pi.small.goal.utils.Utils;
import com.squareup.picasso.Picasso;

import org.xutils.image.ImageOptions;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener {


    @InjectView(R.id.icon_fragment)
    CircleImageView iconFragment;
    @InjectView(R.id.content_tv_fragment)
    TextView contentTvFragment;
    @InjectView(R.id.ll_task_fragment)
    LinearLayout llTaskFragment;
    @InjectView(R.id.linearLayout)
    LinearLayout linearLayout;
    @InjectView(R.id.top_rl)
    RelativeLayout topRl;
    @InjectView(R.id.ll_follow_fragment)
    LinearLayout llFollowFragment;
    @InjectView(R.id.ll_collect_framgent)
    LinearLayout llCollectFramgent;
    @InjectView(R.id.imageView)
    ImageView imageView;
    @InjectView(R.id.ll_target_fragment)
    LinearLayout llTargetFragment;
    @InjectView(R.id.center_ll_fragment)
    LinearLayout centerLlFragment;
    @InjectView(R.id.line_view)
    View lineView;
    @InjectView(R.id.imageView2)
    ImageView imageView2;
    @InjectView(R.id.ll_wallect_fragment)
    LinearLayout llWallectFragment;
    @InjectView(R.id.ll_red_fragment)
    LinearLayout llRedFragment;
    @InjectView(R.id.ll_extension_fragment)
    LinearLayout llExtensionFragment;
    @InjectView(R.id.ll_illness_fragment)
    LinearLayout llIllnessFragment;
    @InjectView(R.id.ll_distribution_fragment)
    LinearLayout llDistributionFragment;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    private ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setLoadingDrawableId(R.mipmap.ic_launcher)
            .setFailureDrawableId(R.mipmap.icon_message_system)
            .build();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.inject(this, view);
        initData();
        initWeight();
        return view;
    }

    private void initWeight() {
        iconFragment.setOnClickListener(this);
        llTaskFragment.setOnClickListener(this);
        llRedFragment.setOnClickListener(this);
        llFollowFragment.setOnClickListener(this);
        llTargetFragment.setOnClickListener(this);
        llCollectFramgent.setOnClickListener(this);

    }

    private void initData() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_fragment:
                startActivity(new Intent(getContext(), UserInfoActivity.class));
                break;
            case R.id.ll_task_fragment:
                startActivity(new Intent(getContext(), TaskActivity.class));
                break;
            case R.id.ll_red_fragment:
                startActivity(new Intent(getContext(), RedActivity.class));
                break;
            case R.id.ll_follow_fragment:
                startActivity(new Intent(getContext(), FollowActivity.class));
                break;
            case R.id.ll_target_fragment:
                startActivity(new Intent(getContext(), TargetActivity.class));
                break;
            case R.id.ll_collect_framgent:
                startActivity(new Intent(getContext(), CollectActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = Utils.UserSharedPreferences(getContext());
        String avatar = sp.getString("avatar", "");
//        x.image().bind(iconFragment, sp
//                .getString("avatar", ""), imageOptions);
        if (!"".equals(sp.getString("avatar", ""))) {
            Picasso.with(getContext()).load(sp.getString("avatar", "")).into(iconFragment);
        }
        nameTextInclude.setText(sp.getString("nick", ""));
        String content = sp.getString("brief", "");

        if (!"".equals(content)) {
            contentTvFragment.setBackground(null);
            contentTvFragment.setText(content);
        } else {
            contentTvFragment.setBackgroundResource(R.drawable.background_oval_white);
            contentTvFragment.setText("请登录");
        }
    }
}
