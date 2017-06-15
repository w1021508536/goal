package com.pi.small.goal.my.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.TrajectoryAdapter;
import com.pi.small.goal.my.entry.DynamicEntity;
import com.pi.small.goal.my.entry.TargetHeadEntity;
import com.pi.small.goal.search.activity.SupportMoneyActivity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.CacheUtil;
import com.pi.small.goal.utils.Code;
import com.pi.small.goal.utils.EditTextHeightUtil;
import com.pi.small.goal.utils.ImageUtils;
import com.pi.small.goal.utils.KeyCode;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.weight.PinchImageView;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pi.small.goal.my.activity.AimOldActivity.KEY_IMG;
import static com.pi.small.goal.my.activity.AimOldActivity.REQUEST_CHANGE_PHOTO;
import static com.pi.small.goal.my.activity.UserInfoActivity.REQUEST_CODE_CAMERA;
import static com.pi.small.goal.my.activity.UserInfoActivity.REQUEST_CODE_PHOTO;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/5 14:50
 * 修改：
 * 描述： 目标详情
 **/
public class AimMoreActivity extends BaseActivity {


    @InjectView(R.id.lv_targetMore)
    ListView lvTargetMore;
    @InjectView(R.id.srfl_targetMore)
    SwipeRefreshLayout srflTargetMore;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.collect_image_include)
    ImageView collectImageInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.tl_top)
    LinearLayout tlTop;
    @InjectView(R.id.pimg)
    PinchImageView pimg;
    @InjectView(R.id.rl_image)
    RelativeLayout rlImage;
    @InjectView(R.id.etv_targetMore)
    EditText etvTargetMore;
    @InjectView(R.id.ll_bottom)
    LinearLayout llBottom;
    @InjectView(R.id.rl_top)
    RelativeLayout rlTop;
    @InjectView(R.id.rl_empty)
    RelativeLayout rlEmpty;
    private int allDy;
    private View header;
    private TrajectoryAdapter adapter;
    private String aimId = "";
    private String userId = "";

    public static final String KEY_AIMID = "aimId";
    private DonutProgress progress;            //圆形的百分比加载框
    private boolean myAim;
    private String commentId;
    private TargetHeadEntity targetHeadEntity;      //头部视图布局的数据
    private int position;
    private ImageView img_bg_head;   //头部布局的背景图片

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_target_more);
        ButterKnife.inject(this);
        EditTextHeightUtil.assistActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        nameTextInclude.setText("目标详情");

        adapter = new TrajectoryAdapter(this);
        lvTargetMore.setAdapter(adapter);
        aimId = getIntent().getStringExtra(KEY_AIMID);

//        plvTargetMore.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
//        View header = getLayoutInflater().inflate(R.layout.view_head_target, plvTargetMore, false);
//        header.setLayoutParams(layoutParams);
//        ListView lv = plvTargetMore.getRefreshableView();
//        lv.addHeaderView(header);

        setHeadView();

        super.initData();
    }

    private void setHeadView() {
        header = getLayoutInflater().inflate(R.layout.view_head_target, null);
        lvTargetMore.addHeaderView(header);
        srflTargetMore.setColorSchemeColors(getResources().getColor(R.color.chat_top));
        progress = (DonutProgress) header.findViewById(R.id.progress);
        progress.setFinishedStrokeWidth(12);
        progress.setUnfinishedStrokeWidth(12);
        progress.setTextColor(getResources().getColor(R.color.white));
        progress.setFinishedStrokeColor(getResources().getColor(R.color.chat_top));
        progress.setUnfinishedStrokeColor(getResources().getColor(R.color.progress_unfinish_color));
        progress.setProgress(0.0f);
    }

    @Override
    public void getData() {
        super.getData();
        requestParams.setUri(Url.Url + "/aim");
        requestParams.addBodyParameter("aimId", aimId);
        requestParams.addBodyParameter("userId", userId);

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!RenameActivity.callOk(result)) return;

                try {
                    JSONObject jsonObj = (JSONObject) ((JSONObject) new JSONObject(result).get("result"));

                    Gson gson = new Gson();
                    targetHeadEntity = gson.fromJson(jsonObj.toString(), TargetHeadEntity.class);

                    setHeadViewData(targetHeadEntity);
                } catch (Exception e) {
                    Log.v("TAG", "'");
//com.google.gson.JsonSyntaxException: java.lang.NumberFormatException: Expected an int but was 0.9 at line 1 column 800 path $.supports[1].remainMoney
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
        getTargetDynamic();

    }

    private void getTargetDynamic() {

        RequestParams requestParams = new RequestParams();
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.setUri(Url.Url + "/aim/dynamic");
        requestParams.addBodyParameter("aimId", aimId);
        //      requestParams.addBodyParameter("userId", userId);

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!RenameActivity.callOk(result)) {
                    rlEmpty.setVisibility(View.VISIBLE);
                    return;
                }

                try {
                    String jsonData = new JSONObject(result).getString("result");
                    Gson gson = new Gson();
                    List<DynamicEntity> data = gson.fromJson(jsonData, new TypeToken<List<DynamicEntity>>() {
                    }.getType());

                    if (new JSONObject(result).getString("pageNum").equals("1")) {
                        adapter.setData(data);
                    } else {
                        adapter.addData(data);
                    }
                    if (data.size() == 0) {
                        rlEmpty.setVisibility(View.VISIBLE);
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

    /**
     * 解析json并绑定视图上
     * create  wjz
     **/

    private void setHeadViewData(TargetHeadEntity targetHeadEntity) {

        try {
            nameTextInclude.setText(targetHeadEntity.getAim().getName());
            if (header == null) return;
            TextView tv_budget = (TextView) header.findViewById(R.id.tv_budget_head);   //预算额度
            TextView tv_money = (TextView) header.findViewById(R.id.tv_money_head);   //已存金额
            TextView tv_cycle = (TextView) header.findViewById(R.id.tv_cycle_head);   //周期
            TextView tv_supports = (TextView) header.findViewById(R.id.tv_goodNums_targetMOre);   //周期
            LinearLayout ll_images = (LinearLayout) header.findViewById(R.id.ll_imags_head);
            RelativeLayout rl_supports = (RelativeLayout) header.findViewById(R.id.rl_goodPeople_targetMore);
            img_bg_head = (ImageView) header.findViewById(R.id.img_bg_head);

            if (Utils.photoEmpty(targetHeadEntity.getAim().getImg())) {
                Picasso.with(this).load(Utils.GetPhotoPath(targetHeadEntity.getAim().getImg())).into(img_bg_head);
            }

            if (targetHeadEntity.getSupports().size() == 0) {
                rl_supports.setVisibility(View.GONE);
            }

//            JSONObject aimJsonObj = (JSONObject) jsonObj.get("aim");
//            JSONObject userJsonObj = (JSONObject) jsonObj.get("user");
//            int userId = (int) userJsonObj.get("id");
            if ((targetHeadEntity.getUser().getId() + "").equals(sp.getString(KeyCode.USER_ID, "26"))) {
                myAim = true;
                collectImageInclude.setImageResource(R.mipmap.goals_setting_btn);
                adapter.setOperationShowFlag(false);
            } else {
                myAim = false;
                if (targetHeadEntity.getHaveCollect() == 1) {
                    collectImageInclude.setImageResource(R.mipmap.collection_btn_pressed);
                } else {
                    collectImageInclude.setImageResource(R.mipmap.collection_btn);
                }
                adapter.setOperationShowFlag(true);
            }


            tv_budget.setText(targetHeadEntity.getAim().getBudget() + "");
            tv_money.setText(targetHeadEntity.getAim().getMoney() + "");
            tv_cycle.setText((targetHeadEntity.getAim().getCycle() * 30) + "");
            String percentOne = Utils.getPercentOne((float) targetHeadEntity.getAim().getMoney() / targetHeadEntity.getAim().getBudget() * 100);

            progress.setProgress(Float.parseFloat(percentOne));

            List<TargetHeadEntity.SupportsBean> supports = targetHeadEntity.getSupports();
            CacheUtil.getInstance().setSupportEntityList(supports);

            tv_supports.setText(supports.size() + "助力");
            for (int i = 0; i < 5; i++) {   //最多显示5个助力的人的头像
                if (supports.size() > i) {
                    CircleImageView circleImageView = new CircleImageView(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Utils.dip2px(this, 30), Utils.dip2px(this, 30));
                    layoutParams.setMargins(Utils.dip2px(this, 5), Utils.dip2px(this, 10), Utils.dip2px(this, 5), Utils.dip2px(this, 10));
                    circleImageView.setLayoutParams(layoutParams);
                    ll_images.addView(circleImageView);
                    circleImageView.setImageResource(R.mipmap.icon_user);
                    TargetHeadEntity.SupportsBean supportsBean = supports.get(i);
                    if (Utils.photoEmpty(supportsBean.getAvatar())) {   //1496802195021.jpg
                        Picasso.with(this).load(Utils.GetPhotoPath(supportsBean.getAvatar())).into(circleImageView);
                    }
                }
            }


            rl_supports.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AimMoreActivity.this, SupportActivity.class));
                }
            });

        } catch (NullPointerException e) {
        }
    }

    @Override
    public void initWeight() {
        super.initWeight();
        rlImage.setOnClickListener(this);
        pimg.setOnClickListener(this);
        collectImageInclude.setOnClickListener(this);
        srflTargetMore.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srflTargetMore.setRefreshing(false);
            }
        });
        rightImageInclude.setOnClickListener(this);
        rlTop.setOnClickListener(this);
        lvTargetMore.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                    // 滚动到最后一行了
                }

                //顶部渐变色
                if (adapter == null)
                    return;
                int heigh = header.getHeight();
                if (heigh < getScrollY()) {

                    //Color.argb(255,0,178,238)
                    tlTop.setBackgroundColor(Color.argb(255, 239, 120, 52));
                } else {
                    Log.i("scl", getScrollY() + "");
                    float a = 255.0f / (float) heigh;
                    a = a * (float) getScrollY();
                    if (a > 255)
                        a = 255;
                    else if (a < 0)
                        a = 0;

                    tlTop.setBackgroundColor(Color.argb((int) a, 239, 120, 52));
                }
            }

        });

        adapter.setOnMyAdapterClickListener(new TrajectoryAdapter.myAdapterClickListener() {

            @Override
            public void great(String id) {
                goGreat(id);
            }

            @Override
            public void comment(String id, int position) {
                commentId = id;
                AimMoreActivity.this.position = position;
                etvTargetMore.setVisibility(View.VISIBLE);
                rlTop.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void help(String id, String nick, String avatar) {

                Intent intent = new Intent(AimMoreActivity.this, SupportMoneyActivity.class);
                intent.putExtra("dynamicId", id);
                intent.putExtra("aimId", aimId);
                intent.putExtra("nick", nick);
                intent.putExtra("avatar", avatar);
                startActivity(intent);

            }


        });

        etvTargetMore.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:

                        rlTop.setVisibility(View.GONE);
                        etvTargetMore.setVisibility(View.GONE);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        comment(commentId, etvTargetMore.getText().toString());

                        List<DynamicEntity.CommentsBean> comments = adapter.getData().get(position).getComments();
                        if (comments.size() > 0) {
                            comments.add(0, new DynamicEntity.CommentsBean(etvTargetMore.getText().toString(), sp.getString(KeyCode.USER_NICK, "")));
                            adapter.getData().get(position).setComments(comments);
                            adapter.notifyDataSetChanged();
                        }

                        break;
                }

                return true;
            }
        });

    }


    /**
     * 点赞
     * create  wjz
     **/
    private void goGreat(String id) {

        RequestParams requestParams = Utils.getRequestParams(this);

        requestParams.setUri(Url.Url + "/vote/vote");
        requestParams.addBodyParameter("objectId", id);

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 去评论
     * create  wjz
     **/
    private void comment(String id, String content) {

        RequestParams requestParams = Utils.getRequestParams(this);

        requestParams.setUri(Url.Url + "/aim/dynamic/comment");
        requestParams.addBodyParameter("dynamicId", id);
        requestParams.addBodyParameter("content", content);

        XUtil.put(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!Utils.callOk(result)) return;

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    public int getScrollY() {
        View c = lvTargetMore.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = lvTargetMore.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }

    private int listViewScorllY(ListView lv) {
        View view = lv.getChildAt(0);
        if (view == null) {
            return 0;
        }
        int firstVisiblePosition = lv.getFirstVisiblePosition();
        int top = view.getTop();
        return -top + firstVisiblePosition * view.getHeight();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_image:
                rlImage.setVisibility(View.GONE);
                break;
            case R.id.pimg:
                rlImage.setVisibility(View.GONE);
                break;
            case R.id.collect_image_include:
                if (targetHeadEntity == null) return;
                if (myAim) {
                    showSetPop(v);
                } else {

                    if (targetHeadEntity.getHaveCollect() == 0) {
                        collectAim(aimId, "1");
                    } else {
                        collectAim(aimId, "0");
                    }

                }
                break;
            case R.id.rl_top:
                rlTop.setVisibility(View.GONE);
                etvTargetMore.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.right_image_include:
                share();
                break;
        }
    }

    private void showSetPop(View v) {

        View windowView = LayoutInflater.from(this).inflate(
                R.layout.window_aim_set, null);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        TextView withdrawals_text = (TextView) windowView.findViewById(R.id.withdrawals_text);
        TextView photo_text = (TextView) windowView.findViewById(R.id.photo_text);
        TextView cancel_text = (TextView) windowView.findViewById(R.id.cancel_text);
        withdrawals_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                System.out.println("======finalI===========" + finalI);
////                            popupWindow.dismiss();
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
                    int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(AimMoreActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AimMoreActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PHOTO);
                        return;
                    } else {
                        goGallery();
                    }
                } else
                    goGallery();
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

    /**
     * 获取相册
     */
    private void goGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Code.RESULT_GALLERY_CODE);
    }

    /**
     * 拍照获取照片
     */
    private void goCamera() {
        Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(getImageByCamera, Code.RESULT_CAMERA_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goCamera();
                } else {
                    // Permission Denied
                    Toast.makeText(AimMoreActivity.this, "您禁止了相机权限", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case REQUEST_CODE_PHOTO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goCamera();
                } else {
                    // Permission Denied
                    Toast.makeText(AimMoreActivity.this, "您禁止了写入权限", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 分享
     * create  wjz
     **/
    private void share() {

        new ShareAction(this).withText("hello")
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener).open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(AimMoreActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(AimMoreActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(AimMoreActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == Code.RESULT_GALLERY_CODE) {

                Uri uri = data.getData();

                Bitmap bitmap = ImageUtils.getBitmapFormUri(this, uri);
//                File file = ImageUtils.getSmallImageFile(this, bitmap, 1080, 1080, true);
//                iconUser.setImageBitmap(bitmap);
//                uploadFile(file);
                afterUploadVitmap(bitmap);

            } else if (requestCode == Code.RESULT_CAMERA_CODE) {   //获取拍照的
                Uri uri = data.getData();
                if (uri == null) {
                    //use bundle to get data
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap

//                        String xmb = ImageUtils.saveMyBitmap("xmb", photo);
//                        Uri imageUri = Uri.fromFile(new File(xmb));
//                        if (Integer.parseInt(Build.VERSION.SDK) >= 24) {
//                            imageUri = FileProvider.getUriForFile(this, "com.pi.small.goal.FileProvider", new File(xmb));
//                        }
                        afterUploadVitmap(photo);
//                        File file = ImageUtils.getSmallImageFile(this, photo, 1080, 1080, true);
//                        iconUser.setImageBitmap(photo);
//                        uploadFile(file);
                    } else {
                        Toast.makeText(getApplicationContext(), "err****", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    //to do find the path of pic by uri
                    Bitmap bitmap = ImageUtils.getBitmapFormUri(this, uri);
//                    File file = ImageUtils.getSmallImageFile(this, bitmap, 1080, 1080, true);
//                    iconUser.setImageBitmap(bitmap);
//                    uploadFile(file);
                    //    doCrop(uri);

                    afterUploadVitmap(bitmap);
                }

            }
            //       else if (requestCode == REQUESTCODE_DROP_IMAGE) {
//                Bitmap bitmap = data.getParcelableExtra("data");
//                //    File smallImageFile = ImageUtils.getSmallImageFile(this, bitmap, 1080, 1080, true);
//                File file = ImageUtils.getSmallImageFile(this, bitmap, 1080, 1080, true);
//                uploadFile(file);
//                img_bg_head.setImageBitmap(bitmap);
//            }
        }
    }

    private void afterUploadVitmap(Bitmap bitmap) {
        if (bitmap == null) return;
        int scW = (int) (bitmap.getWidth() / (bitmap.getHeight() / 640f));

        File file = ImageUtils.getSmallImageFile(this, bitmap, scW, 640, true);
        img_bg_head.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
        uploadFile(file);
        bitmap.recycle();
    }

    /**
     * 上传文件
     * create  wjz
     **/
    public void uploadFile(File file) {
        requestParams.setUri(Url.Url + "/file/picture");
        requestParams.addHeader(KeyCode.USER_TOKEN, sp.getString(KeyCode.USER_TOKEN, ""));
        requestParams.addHeader(KeyCode.USER_DEVICEID, MyApplication.deviceId);
        requestParams.addBodyParameter("token", Utils.GetToken(this));
        requestParams.addBodyParameter("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("picture", file);


        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject obj = (JSONObject) new JSONObject(result).get("result");
                    String path = (String) obj.get("path");
                    ChangeAimPhoto(path);
                    //        updataIcon(path);

                    Intent intent = new Intent();
                    intent.putExtra(KEY_IMG, path);
                    setResult(REQUEST_CHANGE_PHOTO, intent);
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

    private void ChangeAimPhoto(String img) {
        requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + Url.Aim);
        requestParams.addBodyParameter("aimId", aimId);
        requestParams.addBodyParameter("img", img);
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("=========photo=========" + result);

                if (!Utils.callOk(result)) return;

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {

            }
        });
    }


    private void collectAim(String aimId, String status) {
        requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/aim/collect");
        requestParams.addBodyParameter("aimId", aimId + "");
        requestParams.addBodyParameter("status", status);
        if (status.equals("1")) {
            collectImageInclude.setImageResource(R.mipmap.collection_btn_pressed);
            targetHeadEntity.setHaveCollect(1);
        } else {
            collectImageInclude.setImageResource(R.mipmap.collection_btn);
            targetHeadEntity.setHaveCollect(0);
        }

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!RenameActivity.callOk(result)) return;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Utils.showToast(AimMoreActivity.this, (String) jsonObject.get("msg"));
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (rlImage.getVisibility() == View.VISIBLE) {
                    rlImage.setVisibility(View.GONE);
                    return false;
                } else {
                    break;
                }
        }

        return super.onKeyDown(keyCode, event);
    }
}
