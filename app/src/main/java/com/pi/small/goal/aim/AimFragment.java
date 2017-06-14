package com.pi.small.goal.aim;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.aim.activity.AddAimActivity;
import com.pi.small.goal.aim.activity.SupportAimActivity;
import com.pi.small.goal.aim.adapter.ViewPagerAdapter;
import com.pi.small.goal.my.activity.AimMoreActivity;
import com.pi.small.goal.my.activity.RedActivity;
import com.pi.small.goal.utils.CacheUtil;
import com.pi.small.goal.utils.ChoosePhotoActivity;
import com.pi.small.goal.utils.Code;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.utils.entity.AimEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AimFragment extends Fragment implements View.OnClickListener {


    private ImageView right_image;
//    private ImageView left_image;

    private ViewPager viewPager;
    private ViewGroup viewGroup;
    private RelativeLayout null_layout;
    private TextView aim_text;

    private ImageView money_gift_image;

    private List<AimEntity> dataList;
    private Map<String, String> map;
    private AimEntity aimEntity;

    private List<View> viewList;
    private View itemView;
    private ImageView[] imageViews;

    private ViewPagerAdapter viewPagerAdapter;
    private CustomTransformer customTransformer;

    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    final public static int REQUEST_CODE_ASK_CALL_STORGE = 124;

    private int position = 0;


    private ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            .setLoadingDrawableId(R.drawable.image1)
            .setFailureDrawableId(R.drawable.image1)
            .build();
    private View currentView;

    private String img;
    private String path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        currentView = inflater.inflate(R.layout.fragment_aim, container, false);
        View topView = currentView.findViewById(R.id.view);

        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion >= 19) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            topView.setVisibility(View.GONE);
        }

        dataList = new ArrayList<AimEntity>();
        viewList = new ArrayList<View>();
        customTransformer = new CustomTransformer();

        right_image = (ImageView) currentView.findViewById(R.id.right_image);
//        left_image = (ImageView) currentView.findViewById(R.id.left_image);
        viewPager = (ViewPager) currentView.findViewById(R.id.view_pager);
        viewGroup = (ViewGroup) currentView.findViewById(R.id.viewGroup);
        money_gift_image = (ImageView) currentView.findViewById(R.id.money_gift_image);
        null_layout = (RelativeLayout) currentView.findViewById(R.id.null_layout);
        aim_text = (TextView) currentView.findViewById(R.id.aim_text);

        right_image.setOnClickListener(this);
//        left_image.setOnClickListener(this);
        money_gift_image.setOnClickListener(this);
        aim_text.setOnClickListener(this);

        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(true, new CustomTransformer());
        GetAim();
        return currentView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.aim_text:
                intent.setClass(getActivity(), AddAimActivity.class);
                startActivityForResult(intent, Code.AddAimCode);
                break;
            case R.id.right_image:
                int size = dataList.size();
                String grade = CacheUtil.getInstance().getUserInfo().getGrade();
//                String grade = Utils.UserSharedPreferences(getActivity()).getString("grade", "");
                if (grade.equals("v0")) {
                    if (size < 1) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getString(R.string.aim_upgrade));
                    }
                } else if (grade.equals("v1")) {
                    if (size < 1) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getString(R.string.aim_upgrade));
                    }
                } else if (grade.equals("v2")) {
                    if (size < 1) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getString(R.string.aim_upgrade));
                    }
                } else if (grade.equals("v3")) {
                    if (size < 1) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getString(R.string.aim_upgrade));
                    }
                } else if (grade.equals("v4")) {
                    if (size < 2) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getString(R.string.aim_upgrade));
                    }

                } else if (grade.equals("v5")) {
                    if (size < 5) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getString(R.string.aim_upgrade));
                    }
                } else if (grade.equals("v6")) {
                    if (size < 10) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getString(R.string.aim_upgrade));
                    }
                } else if (grade.equals("v7")) {
                    if (size < 15) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getString(R.string.aim_upgrade));
                    }

                } else if (grade.equals("v8")) {
                    if (size < 20) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), "目标已达最大个数");
                    }
                }

                break;
            case R.id.money_gift_image:
                intent.setClass(getActivity(), RedActivity.class);
                startActivity(intent);
                break;
//            case R.id.left_image:
//                startActivity(new Intent(getContext(), SignActivity.class));
//                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Code.AddAimCode) {
            List<AimEntity> list = (List<AimEntity>) data.getSerializableExtra("aim");
            if (list.size() == 0) {

            } else {
                AimEntity aimEntity = list.get(0);
                dataList.add(aimEntity);
                addViewPager(dataList.size() - 1);
                // viewPagerAdapter.notifyDataSetChanged();
                viewPagerAdapter.setViewList(viewList);

            }
            if (dataList.size() == 0) {
                null_layout.setVisibility(View.VISIBLE);
            } else {
                null_layout.setVisibility(View.GONE);
            }

        } else if (resultCode == Code.RESULT_CAMERA_CODE) {
            path = data.getStringExtra("path");
            UpPicture();
        } else if (resultCode == Code.RESULT_GALLERY_CODE) {
            path = data.getStringExtra("path");
            UpPicture();
        } else if (resultCode == Code.SupportAim) {
            String money = data.getStringExtra("money");

            double totalMoney = Double.valueOf(dataList.get(position).getMoney());
            totalMoney = totalMoney + Double.valueOf(money);
            dataList.get(position).setMoney(totalMoney + "");

            TextView tv_money = (TextView) viewList.get(position).findViewById(R.id.money_text);
            ImageView line_left_image = (ImageView) viewList.get(position).findViewById(R.id.line_left_image);
            ImageView line_right_image = (ImageView) viewList.get(position).findViewById(R.id.line_right_image);
            TextView weight_text = (TextView) viewList.get(position).findViewById(R.id.weight_text);


            tv_money.setText(dataList.get(position).getMoney());
            line_left_image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2, Float.valueOf(dataList.get(position).getBudget()) - Float.valueOf(dataList.get(position).getMoney())));
            line_right_image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2, Float.valueOf(dataList.get(position).getMoney())));
//            weight_text.setText(Float.valueOf(dataList.get(position).getMoney()) / Float.valueOf(dataList.get(position).getBudget()) * 100 + "%");

            String weight = String.valueOf(Float.valueOf(dataList.get(position).getMoney()) / Float.valueOf(dataList.get(position).getBudget()) * 100);
            weight_text.setText(weight.substring(0, weight.indexOf(".")) + "%");
            viewPagerAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void UpPicture() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.UpPicture);
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("token", Utils.GetToken(getActivity()));
        requestParams.addBodyParameter("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("picture", new File(path));
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=========photo=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        img = new JSONObject(result).getJSONObject("result").getString("path");
                        ChangeAimPhoto();
                    } else {
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!ex.getMessage().equals("")) {
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

    private void ChangeAimPhoto() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Aim);
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("aimId", dataList.get(position).getId());
        requestParams.addBodyParameter("img", img);
        XUtil.post(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("=========photo=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        ImageView aim_image = (ImageView) viewList.get(position).findViewById(R.id.aim_image);
                        aim_image.setImageBitmap(BitmapFactory.decodeFile(path));
                        dataList.get(position).setImg(img);
                        viewPagerAdapter.notifyDataSetChanged();
                    } else {
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!ex.getMessage().equals("")) {
                    Utils.showToast(getActivity(), ex.getMessage());
                }
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void GetAim() {
        right_image.setClickable(false);

        RequestParams requestParams = new RequestParams(Url.Url + Url.Aim);
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("userId", Utils.UserSharedPreferences(getActivity()).getString("id", ""));
        requestParams.addBodyParameter("aimId", "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("============GetAim==========" + result);
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
                        System.out.println("=====================" + dataList.size());
                        if (dataList.size() == 0) {
                            null_layout.setVisibility(View.VISIBLE);
                        } else {
                            null_layout.setVisibility(View.GONE);
                            SetViewPager();
                        }


                    } else if (code.equals("100000")) {
                        null_layout.setVisibility(View.VISIBLE);
                    } else {
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                    }

                    right_image.setClickable(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex.getMessage() != null) {
                    Utils.showToast(getActivity(), ex.getMessage());
                }
                right_image.setClickable(true);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void SetViewPager() {


        for (int i = 0; i < dataList.size(); i++) {
            setView(i);
        }


        //添加小圆点的图片
        setPoint();
        viewPagerAdapter = new ViewPagerAdapter(getActivity(), viewList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());

    }

    private void addViewPager(int i) {
        setView(i);
        viewGroup.removeAllViews();
        setPoint();
    }

    private void setPoint() {
        imageViews = new ImageView[viewList.size()];
        for (int i = 0; i < viewList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            //设置小圆点imageview的参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            params.setMargins(10, 0, 10, 0);
            imageView.setLayoutParams(params);//创建一个宽高均为20 的布局
            //将小圆点layout添加到数组中
            imageViews[i] = imageView;

            //默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.mipmap.icon_dian_yellow);
            } else {
                imageViews[i].setBackgroundResource(R.mipmap.icon_dian_white);
            }

            //将imageviews添加到小圆点视图组
            viewGroup.addView(imageViews[i]);
        }
    }


    private void setView(int i) {
        ImageView line_left_image;
        ImageView line_right_image;
        TextView weight_text;
        TextView money_text;
        TextView budget_text;
        TextView aim_text;
        TextView support_text;
        TextView day_text;
        TextView process_text;
        TextView set_text;
        ImageView aim_image;

        itemView = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_viewpager_aim, null);
        money_text = (TextView) itemView.findViewById(R.id.money_text);
        budget_text = (TextView) itemView.findViewById(R.id.budget_text);
        aim_text = (TextView) itemView.findViewById(R.id.aim_text);
        support_text = (TextView) itemView.findViewById(R.id.support_text);
        day_text = (TextView) itemView.findViewById(R.id.day_text);
        set_text = (TextView) itemView.findViewById(R.id.set_text);
        aim_image = (ImageView) itemView.findViewById(R.id.aim_image);
        process_text = (TextView) itemView.findViewById(R.id.process_text);

        line_left_image = (ImageView) itemView.findViewById(R.id.line_left_image);
        line_right_image = (ImageView) itemView.findViewById(R.id.line_right_image);
        weight_text = (TextView) itemView.findViewById(R.id.weight_text);


        if (!dataList.get(i).getImg().equals("")) {
            x.image().bind(aim_image, Utils.GetPhotoPath(dataList.get(i).getImg()), imageOptions);
        }

        aim_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AimMoreActivity.class);
                intent.putExtra(AimMoreActivity.KEY_AIMID, dataList.get(position).getId());
                getActivity().startActivity(intent);
            }
        });

        aim_text.setText(dataList.get(i).getName());
        money_text.setText(dataList.get(i).getMoney());
        budget_text.setText(dataList.get(i).getBudget());

        support_text.setText(dataList.get(i).getSupport());


        long createTime = Long.valueOf(dataList.get(i).getCreateTime());
        long currentTime = System.currentTimeMillis();
        long day = (currentTime - createTime) / 86400000;
        Long.valueOf(dataList.get(i).getCycle());
        if (day > Long.valueOf(dataList.get(i).getCycle()) * 30) {
            day_text.setText("0");
        } else {
            day_text.setText(Long.valueOf(dataList.get(i).getCycle()) * 30 - day + "");
        }
//            Float.valueOf(dataList.get(i).getBudget())-Float.valueOf(dataList.get(i).getMoney())
        line_left_image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 4, Float.valueOf(dataList.get(i).getBudget()) - Float.valueOf(dataList.get(i).getMoney())));
        line_right_image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 4, Float.valueOf(dataList.get(i).getMoney())));
//        weight_text.setText(Float.valueOf(dataList.get(i).getMoney()) / Float.valueOf(dataList.get(i).getBudget()) * 100 + "%");
        String weight = String.valueOf(Float.valueOf(dataList.get(i).getMoney()) / Float.valueOf(dataList.get(i).getBudget()) * 100);
        weight_text.setText(weight.substring(0, weight.indexOf(".")) + "%");
        if (!dataList.get(i).getMoney().equals(dataList.get(i).getBudget())) {
            process_text.setText("向小目标更进一步");
            process_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SupportAimActivity.class);
                    intent.putExtra("aimId", dataList.get(position).getId());
                    intent.putExtra("budget", dataList.get(position).getBudget());
                    intent.putExtra("money", dataList.get(position).getMoney());
                    startActivityForResult(intent, Code.SupportAim);
                }
            });
        } else {
            process_text.setText("目标达成 可提现");
            Drawable drawable = getActivity().getResources().getDrawable(R.mipmap.icon_aim_finish);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
            process_text.setCompoundDrawables(drawable, null, null, null);
        }

        final int finalI = i;
        set_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     position = finalI;
                View windowView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.window_aim_set, null);
                final PopupWindow popupWindow = new PopupWindow(windowView,
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

                TextView withdrawals_text = (TextView) windowView.findViewById(R.id.withdrawals_text);
                TextView photo_text = (TextView) windowView.findViewById(R.id.photo_text);
                TextView cancel_text = (TextView) windowView.findViewById(R.id.cancel_text);
                withdrawals_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        System.out.println("======finalI===========" + finalI);
//                            popupWindow.dismiss();
                    }
                });

                cancel_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                photo_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= 23) {

                            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_CALL_PHONE);
                                return;
                            } else {
                                int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                if (checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_CALL_STORGE);
                                    return;
                                } else {
                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), ChoosePhotoActivity.class);
                                    startActivityForResult(intent, Code.REQUEST_HEAD_CODE);
                                }
                            }
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), ChoosePhotoActivity.class);
                            startActivityForResult(intent, Code.REQUEST_HEAD_CODE);
                        }
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
                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);


            }
        });


        viewList.add(itemView);

    }

    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            AimFragment.this.position = position;
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[position].setBackgroundResource(R.mipmap.icon_dian_yellow);
                //不是当前选中的page，其小圆点设置为未选中的状态
                if (position != i) {
                    imageViews[i].setBackgroundResource(R.mipmap.icon_dian_white);
                }
            }

        }
    }


    public class CustomTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.9F;

        @Override
        public void transformPage(View view, float position) {

            if (position < -1) {
                view.setScaleY(MIN_SCALE);
            } else if (position <= 1) {
                float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
                view.setScaleY(scale);
            } else {
                view.setScaleY(MIN_SCALE);
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_CALL_STORGE);
                        return;
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), ChoosePhotoActivity.class);
                        startActivityForResult(intent, Code.REQUEST_HEAD_CODE);
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "您禁止了相机权限", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case REQUEST_CODE_ASK_CALL_STORGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ChoosePhotoActivity.class);
                    startActivityForResult(intent, Code.REQUEST_HEAD_CODE);
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "您禁止了写入权限", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
