package com.small.small.goal.aim;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
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

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.aim.activity.AddAimActivity;
import com.small.small.goal.aim.activity.SupportAimActivity;
import com.small.small.goal.my.activity.AimMoreActivity;
import com.small.small.goal.my.activity.RedActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.ChoosePhotoActivity;
import com.small.small.goal.utils.Code;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.entity.AimEntity;

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

    private ViewPager viewPager;
    private ViewGroup viewGroup;
    private RelativeLayout null_layout;
    private TextView aim_text;

    private ImageView money_gift_image;

    private List<AimEntity> dataList;
    private Map<String, String> map;
    private AimEntity aimEntity;

    private ImageView[] imageViews;

    private ViewPagerAdapter viewPagerAdapter;
    private CustomTransformer customTransformer;

    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    final public static int REQUEST_CODE_ASK_CALL_STORGE = 124;

    private int position_now = 0;


    private ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setFailureDrawableId(R.drawable.image2)
            .setLoadingDrawableId(R.drawable.image1)
            .setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
            .build();
    private View currentView;

    private String img;
    private String path;

    private int photoFrom = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_aim, container, false);
        View topView = currentView.findViewById(R.id.view);

        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion >= 19) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            topView.setVisibility(View.GONE);
        }

        dataList = new ArrayList<AimEntity>();
//        viewList = new ArrayList<View>();
        customTransformer = new CustomTransformer();
        right_image = (ImageView) currentView.findViewById(R.id.right_image);
        viewPager = (ViewPager) currentView.findViewById(R.id.view_pager);
        viewGroup = (ViewGroup) currentView.findViewById(R.id.viewGroup);
        money_gift_image = (ImageView) currentView.findViewById(R.id.money_gift_image);
        null_layout = (RelativeLayout) currentView.findViewById(R.id.null_layout);
        aim_text = (TextView) currentView.findViewById(R.id.aim_text);

        right_image.setOnClickListener(this);
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
                if (grade.equals("v0")) {
                    if (size < 1) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getResources().getString(R.string.aim_upgrade));
                    }
                } else if (grade.equals("v1")) {
                    if (size < 1) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getResources().getString(R.string.aim_upgrade));
                    }
                } else if (grade.equals("v2")) {
                    if (size < 1) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getResources().getString(R.string.aim_upgrade));
                    }
                } else if (grade.equals("v3")) {
                    if (size < 1) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getResources().getString(R.string.aim_upgrade));
                    }
                } else if (grade.equals("v4")) {
                    if (size < 2) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getResources().getString(R.string.aim_upgrade));
                    }

                } else if (grade.equals("v5")) {
                    if (size < 5) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getResources().getString(R.string.aim_upgrade));
                    }
                } else if (grade.equals("v6")) {
                    if (size < 10) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getResources().getString(R.string.aim_upgrade));
                    }
                } else if (grade.equals("v7")) {
                    if (size < 15) {
                        intent.setClass(getActivity(), AddAimActivity.class);
                        startActivityForResult(intent, Code.AddAimCode);
                    } else {
                        Utils.showToast(getActivity(), getResources().getString(R.string.aim_upgrade));
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
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Code.AddAimCode) {
            List<AimEntity> list = (List<AimEntity>) data.getSerializableExtra("aim");
            if (list.size() == 0) {

            } else {
                if (dataList.size() == 0) {

                    AimEntity aimEntity = list.get(0);
                    dataList.add(aimEntity);
                    SetViewPager();
                } else {
                    AimEntity aimEntity = list.get(0);
                    dataList.add(aimEntity);
                    addViewPager();
                    viewPagerAdapter.setViewList(dataList);
                }


            }
            if (dataList.size() == 0) {
                null_layout.setVisibility(View.VISIBLE);
            } else {
                null_layout.setVisibility(View.GONE);
            }

        } else if (resultCode == Code.RESULT_CAMERA_CODE) {
            path = data.getStringExtra("path");
            photoFrom = 0;
            UpPicture();
        } else if (resultCode == Code.RESULT_GALLERY_CODE) {
            path = data.getStringExtra("path");
            photoFrom = 0;
            UpPicture();
        } else if (resultCode == Code.SupportAim) {
            String money = data.getStringExtra("money");

            Float totalMoney = Float.valueOf(dataList.get(position_now).getMoney());
            totalMoney = totalMoney + Float.valueOf(money);
            dataList.get(position_now).setMoney(totalMoney + "");
            if (viewPager.getChildAt(position_now) == null) {
                System.out.println("================null==============");
            }
            View currentView = viewPagerAdapter.getPrimaryItem();

            TextView tv_money = (TextView) currentView.findViewById(R.id.money_text);
            ImageView line_right_image = (ImageView) currentView.findViewById(R.id.line_right_image);
            TextView weight_text = (TextView) currentView.findViewById(R.id.weight_text);
            ImageView line_left_image = (ImageView) currentView.findViewById(R.id.line_left_image);

            tv_money.setText(dataList.get(position_now).getMoney());
            if ((Float.valueOf(dataList.get(position_now).getMoney()) / Float.valueOf(dataList.get(position_now).getBudget())) > 0.98) {
                String weight = String.valueOf(Float.valueOf(dataList.get(position_now).getMoney()) / Float.valueOf(dataList.get(position_now).getBudget()) * 100);
                weight_text.setText(weight.substring(0, weight.indexOf(".")) + "%");
                line_right_image.setVisibility(View.GONE);
            } else {
                line_left_image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 8, (Float.valueOf(dataList.get(position_now).getBudget()) - Float.valueOf(dataList.get(position_now).getMoney())) / Float.valueOf(dataList.get(position_now).getBudget())));
                line_right_image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 8, Float.valueOf(dataList.get(position_now).getMoney()) / Float.valueOf(dataList.get(position_now).getBudget())));
                String weight = String.valueOf(Float.valueOf(dataList.get(position_now).getMoney()) / Float.valueOf(dataList.get(position_now).getBudget()) * 100);
                weight_text.setText(weight.substring(0, weight.indexOf(".")) + "%");
                line_right_image.setVisibility(View.VISIBLE);
            }
            viewPagerAdapter.notifyDataSetChanged();
        } else if (resultCode == Code.RESULT_OWM_CODE) {

            if (!data.getStringExtra("path").equals("")) {
                photoFrom = 1;
                img = data.getStringExtra("path");
                ChangeAimPhoto();
            }

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
        XUtil.post(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
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
            public void onFinished() {

            }
        });

    }

    private void ChangeAimPhoto() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Aim);
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("aimId", dataList.get(position_now).getId());
        requestParams.addBodyParameter("img", img);
        XUtil.post(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        dataList.get(position_now).setImg(img);
                        x.image().bind((ImageView) viewPagerAdapter.getPrimaryItem().findViewById(R.id.aim_image), Utils.GetPhotoPath(img), imageOptions, new Callback.CommonCallback<Drawable>() {
                            @Override
                            public void onSuccess(Drawable result) {
                                result.setCallback(null);

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
        XUtil.get(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

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
            public void onFinished() {

            }
        });
    }

    private void SetViewPager() {
        //添加小圆点的图片
        addViewPager();
        viewPagerAdapter = new ViewPagerAdapter(getActivity(), dataList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());

    }

    private void addViewPager() {
        viewGroup.removeAllViews();
        setPoint();
    }

    private void setPoint() {
        imageViews = new ImageView[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            //设置小圆点imageview的参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
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
            position_now = position;
            System.out.println("==========position_now========" + position_now);
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


    public class ViewPagerAdapter extends PagerAdapter {

        private List<AimEntity> dataList;
        private Context context;

        List<View> mViewList = new ArrayList<View>();
        private View mCurrentView;

        public ViewPagerAdapter(Context context, List<AimEntity> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        public void setViewList(List<AimEntity> dataList) {
            this.dataList = dataList;
            notifyDataSetChanged();
        }


        public void addData(AimEntity aimEntity) {
            this.dataList.add(aimEntity);
            notifyDataSetChanged();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mCurrentView = (View) object;
        }

        public View getPrimaryItem() {
            return mCurrentView;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
            mViewList.add(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = null;
            ViewHolder viewHolder = null;
            if (mViewList.isEmpty()) {
                itemView = LayoutInflater.from(context).inflate(
                        R.layout.item_viewpager_aim, null);
                viewHolder = new ViewHolder();
                viewHolder.line_left_image = (ImageView) itemView.findViewById(R.id.line_left_image);
                viewHolder.line_right_image = (ImageView) itemView.findViewById(R.id.line_right_image);
                viewHolder.weight_text = (TextView) itemView.findViewById(R.id.weight_text);
                viewHolder.money_text = (TextView) itemView.findViewById(R.id.money_text);
                viewHolder.budget_text = (TextView) itemView.findViewById(R.id.budget_text);
                viewHolder.aim_text = (TextView) itemView.findViewById(R.id.aim_text);
                viewHolder.support_text = (TextView) itemView.findViewById(R.id.support_text);
                viewHolder.day_text = (TextView) itemView.findViewById(R.id.day_text);
                viewHolder.process_text = (TextView) itemView.findViewById(R.id.process_text);
                viewHolder.set_text = (TextView) itemView.findViewById(R.id.set_text);
                viewHolder.aim_image = (ImageView) itemView.findViewById(R.id.aim_image);

                itemView.setTag(viewHolder);
            } else {
                itemView = mViewList.remove(0);
                viewHolder = (ViewHolder) itemView.getTag();
            }
            if (!dataList.get(position).getImg().equals("")) {
                x.image().bind(viewHolder.aim_image, Utils.GetPhotoPath(dataList.get(position).getImg()) + "?x-oss-process=image/resize,m_lfit,h_400,w_400", imageOptions, new Callback.CommonCallback<Drawable>() {
                    @Override
                    public void onSuccess(Drawable result) {
                        result.setCallback(null);
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
            } else {
                viewHolder.aim_image.setImageDrawable(getResources().getDrawable(R.drawable.image1));
            }

            viewHolder.aim_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position_now = position;
                    Intent intent = new Intent(context, AimMoreActivity.class);
                    intent.putExtra(AimMoreActivity.KEY_AIMID, dataList.get(position).getId());
                    context.startActivity(intent);
                }
            });

            viewHolder.aim_text.setText(dataList.get(position).getName());
            viewHolder.money_text.setText(dataList.get(position).getMoney());
            viewHolder.budget_text.setText(dataList.get(position).getBudget());

            viewHolder.support_text.setText(dataList.get(position).getSupport());


            long createTime = Long.valueOf(dataList.get(position).getCreateTime());
            long currentTime = System.currentTimeMillis();
            long day = (currentTime - createTime) / 86400000;
            Long.valueOf(dataList.get(position).getCycle());
            if (day > Long.valueOf(dataList.get(position).getCycle()) * 30) {
                viewHolder.day_text.setText("0");
            } else {
                viewHolder.day_text.setText(Long.valueOf(dataList.get(position).getCycle()) * 30 - day + "");
            }

            if ((Float.valueOf(dataList.get(position).getMoney()) / Float.valueOf(dataList.get(position).getBudget())) > 0.98) {
                String weight = String.valueOf(Float.valueOf(dataList.get(position).getMoney()) / Float.valueOf(dataList.get(position).getBudget()) * 100);
                viewHolder.weight_text.setText(weight.substring(0, weight.indexOf(".")) + "%");
                viewHolder.line_right_image.setVisibility(View.GONE);
            } else {
                viewHolder.line_left_image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 8, (Float.valueOf(dataList.get(position).getBudget()) - Float.valueOf(dataList.get(position).getMoney())) / Float.valueOf(dataList.get(position).getBudget())));
                viewHolder.line_right_image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 8, Float.valueOf(dataList.get(position).getMoney()) / Float.valueOf(dataList.get(position).getBudget())));
                String weight = String.valueOf(Float.valueOf(dataList.get(position).getMoney()) / Float.valueOf(dataList.get(position).getBudget()) * 100);
                viewHolder.weight_text.setText(weight.substring(0, weight.indexOf(".")) + "%");
                viewHolder.line_right_image.setVisibility(View.VISIBLE);
            }
            if ((Float.valueOf(dataList.get(position).getMoney()) / Float.valueOf(dataList.get(position).getBudget())) > 0.98) {
                String weight = String.valueOf(Float.valueOf(dataList.get(position).getMoney()) / Float.valueOf(dataList.get(position).getBudget()) * 100);
                viewHolder.weight_text.setText(weight.substring(0, weight.indexOf(".")) + "%");
                viewHolder.line_right_image.setVisibility(View.GONE);
            } else {
                viewHolder.line_left_image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 8, (Float.valueOf(dataList.get(position).getBudget()) - Float.valueOf(dataList.get(position).getMoney())) / Float.valueOf(dataList.get(position).getBudget())));
                viewHolder.line_right_image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 8, Float.valueOf(dataList.get(position).getMoney()) / Float.valueOf(dataList.get(position).getBudget())));
                String weight = String.valueOf(Float.valueOf(dataList.get(position).getMoney()) / Float.valueOf(dataList.get(position).getBudget()) * 100);
                viewHolder.weight_text.setText(weight.substring(0, weight.indexOf(".")) + "%");
                viewHolder.line_right_image.setVisibility(View.VISIBLE);
            }

            if (Float.valueOf(dataList.get(position).getMoney()) < Float.valueOf(dataList.get(position).getBudget())) {
                viewHolder.process_text.setText("向小目标更进一步");
                viewHolder.process_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        position_now = position;
                        Intent intent = new Intent();
                        intent.setClass(context, SupportAimActivity.class);
                        intent.putExtra("aimId", dataList.get(position).getId());
                        intent.putExtra("budget", dataList.get(position).getBudget());
                        intent.putExtra("money", dataList.get(position).getMoney());
                        startActivityForResult(intent, Code.SupportAim);
                    }
                });
            } else {

                viewHolder.process_text.setText("目标达成 可提现");
                Drawable drawable = getActivity().getResources().getDrawable(R.mipmap.icon_aim_finish);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                viewHolder.process_text.setCompoundDrawables(drawable, null, null, null);
                viewHolder.process_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        position_now = position;
                        TransferAim();
                    }
                });
            }


            viewHolder.set_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position_now = position;
                    View windowView = LayoutInflater.from(getActivity()).inflate(
                            R.layout.window_aim_set, null);
                    final PopupWindow popupWindow = new PopupWindow(windowView,
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

                    TextView withdrawals_text = (TextView) windowView.findViewById(R.id.withdrawals_text);
                    TextView photo_text = (TextView) windowView.findViewById(R.id.photo_text);
                    TextView cancel_text = (TextView) windowView.findViewById(R.id.cancel_text);
                    withdrawals_text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (Float.valueOf(dataList.get(position).getMoney()) < Float.valueOf(dataList.get(position).getBudget())) {
                                popupWindow.dismiss();
                                View windowView2 = LayoutInflater.from(getActivity()).inflate(
                                        R.layout.window_withdrawals_aim, null);
                                final PopupWindow popupWindow2 = new PopupWindow(windowView2,
                                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);

                                TextView cancel_text = (TextView) windowView2.findViewById(R.id.cancel_text);
                                TextView ok_text = (TextView) windowView2.findViewById(R.id.ok_text);
                                ImageView delete_image = (ImageView) windowView2.findViewById(R.id.delete_image);

                                delete_image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        popupWindow2.dismiss();
                                    }
                                });
                                cancel_text.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //仍要取出  扣除3%手续费
                                        TransferAim();
                                        popupWindow2.dismiss();
                                    }
                                });
                                ok_text.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        popupWindow2.dismiss();
                                    }
                                });

                                popupWindow2.setAnimationStyle(R.style.MyDialogStyle);
                                popupWindow2.setTouchable(true);
                                popupWindow2.setOutsideTouchable(true);
                                popupWindow2.setFocusable(false);

                                // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
                                // 我觉得这里是API的一个bug
                                popupWindow2.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_empty));
                                // 设置好参数之后再show
                                popupWindow2.showAtLocation(view, Gravity.CENTER, 0, 0);
                            } else {
                                popupWindow.dismiss();
                                TransferAim();
                            }
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

            container.addView(itemView);
            return itemView;
        }

        //提现
        private void Withdrawals() {
            Utils.showToast(getActivity(), "提现");
        }

        private void TransferAim() {
            RequestParams requestParams = new RequestParams(Url.Url + Url.AimTransfer);
            requestParams.addHeader("token", Utils.GetToken(getActivity()));
            requestParams.addHeader("deviceId", MyApplication.deviceId);
            requestParams.addBodyParameter("aimId", dataList.get(position_now).getId());
            XUtil.post(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
                @Override
                public void onSuccess(String result) {
                    try {
                        if (new JSONObject(result).getString("code").equals("0")) {
                            Utils.showToast(getActivity(), "目标成功提现至余额");
                            dataList.remove(position_now);
//                            addViewPager(dataList.size() - 1);
//                            viewPagerAdapter.setViewList(dataList);



                            if (dataList.size() == 0) {
                                null_layout.setVisibility(View.VISIBLE);
                            } else {
                                null_layout.setVisibility(View.GONE);
                                SetViewPager();
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

                }

                @Override
                public void onFinished() {

                }
            });
        }


        private class ViewHolder {
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
        }
    }
}
