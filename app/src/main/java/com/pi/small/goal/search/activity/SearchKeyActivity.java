package com.pi.small.goal.search.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.activity.AimMoreActivity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.utils.entity.AimEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SearchKeyActivity extends BaseActivity {

    @InjectView(R.id.cancel_text)
    TextView cancelText;
    @InjectView(R.id.search_first_image)
    ImageView searchFirstImage;
    @InjectView(R.id.search_edit)
    EditText searchEdit;
    @InjectView(R.id.search_layout)
    RelativeLayout searchLayout;
    @InjectView(R.id.search_list)
    PullToRefreshListView searchList;

    private String key;

    private int page = 1;
    private int total = -1;

    private List<AimEntity> dataList;
    private SearchKeyAdapter searchKeyAdapter;
    private AimEntity aimEntity;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_key);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
        dataList = new ArrayList<AimEntity>();
        searchKeyAdapter = new SearchKeyAdapter(this, dataList);
        searchList.setAdapter(searchKeyAdapter);

        width = (getWindowManager().getDefaultDisplay().getWidth() - 130);

        init();
    }

    private void init() {

        searchList.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        /**
         * 实现 接口  OnRefreshListener2<ListView>  以便与监听  滚动条到顶部和到底部
         */
        searchList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetUpDataTask().execute();
            }
        });

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchKeyActivity.this, AimMoreActivity.class);
                intent.putExtra(AimMoreActivity.KEY_AIMID, dataList.get(position - 1).getId());
                startActivity(intent);
            }
        });

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!searchEdit.getText().toString().trim().equals("")) {
                        dataList.clear();
                        searchKeyAdapter.notifyDataSetChanged();
                        page = 1;
                        total = -1;
                        GetData();
                    }


                }
                return false;
            }
        });
        searchEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                if (s.toString().equals("")) {
                    dataList.clear();
                    searchKeyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 模拟网络加载数据的   异步请求类
     * 上拉加载
     */
    private class GetUpDataTask extends AsyncTask<Void, Void, List<AimEntity>> {

        //子线程请求数据
        @Override
        protected List<AimEntity> doInBackground(Void... params) {
//            isDown = false;

            if (page * 10 >= total) {

            } else {
                page = page + 1;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GetData();
            }


            return dataList;
        }

        //主线程更新UI
        @Override
        protected void onPostExecute(List<AimEntity> result) {
//            hotAdapter.notifyDataSetChanged();
            searchList.onRefreshComplete();
            if (page * 10 >= total) {
                Utils.showToast(SearchKeyActivity.this, "没有更多数据了");
            }
            super.onPostExecute(result);
        }
    }

    @OnClick(R.id.cancel_text)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_text:
                finish();
                break;
        }
    }


    private void GetData() {

        RequestParams requestParams = new RequestParams(Url.Url + Url.SearchAim);
        requestParams.addHeader("token", Utils.GetToken(SearchKeyActivity.this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("keyword", searchEdit.getText().toString().trim());
        requestParams.addBodyParameter("p", page + "");
        requestParams.addBodyParameter("r", "10");
        XUtil.get(requestParams, SearchKeyActivity.this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=====getaim=======" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        total = Integer.valueOf(new JSONObject(result).getString("total"));
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            aimEntity = new AimEntity();
//                            {"id":18,"name":"买双鞋","userId":27,"province":"山东省","city":"青岛市","brief":"买双鞋子","position":"万锦律师",
//                                    "longitude":0.000000,"latitude":0.000000,"createTime":1495527450000,"status":1,"img":"","transfer":0}
                            aimEntity.setId(jsonArray.getJSONObject(i).getString("id"));
                            aimEntity.setName(jsonArray.getJSONObject(i).getString("name"));
                            aimEntity.setUserId(jsonArray.getJSONObject(i).getString("userId"));
                            aimEntity.setPosition(jsonArray.getJSONObject(i).getString("province"));
                            aimEntity.setCity(jsonArray.getJSONObject(i).getString("city"));
                            aimEntity.setBrief(jsonArray.getJSONObject(i).getString("brief"));
                            aimEntity.setPosition(jsonArray.getJSONObject(i).getString("position"));
                            aimEntity.setLongitude(jsonArray.getJSONObject(i).getString("longitude"));
                            aimEntity.setLatitude(jsonArray.getJSONObject(i).getString("latitude"));
                            aimEntity.setCreateTime(jsonArray.getJSONObject(i).getString("createTime"));
                            aimEntity.setStatus(jsonArray.getJSONObject(i).getString("status"));
                            aimEntity.setImg(jsonArray.getJSONObject(i).getString("img"));

                            dataList.add(aimEntity);
                        }
                        searchKeyAdapter.notifyDataSetChanged();

                    } else {
                        Utils.showToast(SearchKeyActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("=====getaim=======" + ex.getMessage());
                Utils.showToast(SearchKeyActivity.this, ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }

    public class SearchKeyAdapter extends BaseAdapter {

        private List<AimEntity> dataList;
        private Context context;
        private ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setLoadingDrawableId(R.mipmap.background_load)
                .setFailureDrawableId(R.mipmap.background_fail)
                .build();

        SpannableStringBuilder spannable;

        public SearchKeyAdapter(Context context, List<AimEntity> dataList) {
            this.dataList = dataList;
            this.context = context;

        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list_search_key, parent, false);
                viewHolder.name_text = (TextView) convertView.findViewById(R.id.name_text);
                viewHolder.aim_iamge = (ImageView) convertView.findViewById(R.id.aim_image);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            key = searchEdit.getText().toString().trim();
            spannable = new SpannableStringBuilder(dataList.get(position).getName());
            spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.yellow_light)), dataList.get(position).getName().indexOf(key), dataList.get(position).getName().indexOf(key) + key.length()
                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.name_text.setText(spannable);

            ViewGroup.LayoutParams layoutParams = viewHolder.aim_iamge.getLayoutParams();
            layoutParams.height = width / 3;
            layoutParams.width = width / 3;
            viewHolder.aim_iamge.setLayoutParams(layoutParams);

            if (dataList.get(position).getImg().equals("")) {
                viewHolder.aim_iamge.setImageDrawable(context.getResources().getDrawable(R.drawable.image1));
            } else {
                x.image().bind(viewHolder.aim_iamge, Utils.GetPhotoPath(dataList.get(position).getImg()), imageOptions);
            }

            return convertView;
        }

        private class ViewHolder {
            ImageView aim_iamge;
            TextView name_text;
        }
    }

}
