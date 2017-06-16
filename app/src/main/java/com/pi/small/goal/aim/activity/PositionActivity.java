package com.pi.small.goal.aim.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.R;
import com.pi.small.goal.aim.adapter.PositionAdapter;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Code;
import com.pi.small.goal.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener {

    private ImageView left_image;
    private ImageView right_image;
    private PullToRefreshListView position_list;

    private EditText search_edit;


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String latitude;
    private String longitude;

    private PositionAdapter positionAdapter;


    private PoiSearch poiSearch;

    private PoiSearch.Query query;

    private List<Map<String, String>> dataList;
    private List<Map<String, String>> dataList2;
    private Map<String, String> map;

    private int page;
    private int MaxPage = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_position);
        super.onCreate(savedInstanceState);

        page = 1;

        sharedPreferences = Utils.UserSharedPreferences(this);
        editor = sharedPreferences.edit();

        latitude = sharedPreferences.getString("latitude", "0");
        longitude = sharedPreferences.getString("longitude", "0");

        dataList = new ArrayList<Map<String, String>>();
        dataList2 = new ArrayList<Map<String, String>>();

        positionAdapter = new PositionAdapter(this, dataList);

        query = new PoiSearch.Query("", "");
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(page);//设置查询页码
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(Double.valueOf(latitude),
                Double.valueOf(longitude)), 1000));//设置周边搜索的中心点以及半径

        init();
    }


    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);
        right_image = (ImageView) findViewById(R.id.right_image);
        position_list = (PullToRefreshListView) findViewById(R.id.position_list);
        search_edit = (EditText) findViewById(R.id.search_edit);

        position_list.setAdapter(positionAdapter);
        position_list.setMode(PullToRefreshBase.Mode.BOTH);

        left_image.setOnClickListener(this);
        right_image.setOnClickListener(this);

        search_edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表

                SearchData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        position_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //      new GetDownDataTask().execute();
                Refresh();
                position_list.onRefreshComplete();
            }


            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                //     new GetUpEvaluateDataTask().execute();
                if (MaxPage == -1) {
                    page = page + 1;

                    query.setPageNum(page);
                    poiSearch.searchPOIAsyn();
                }
                position_list.onRefreshComplete();
            }
        });

        position_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 1) {
                    ImageView check_image = (ImageView) view.findViewById(R.id.check_image);
                    check_image.setVisibility(View.VISIBLE);
                    Intent intent = new Intent();
                    intent.putExtra("position", "");
                    intent.putExtra("city", "");
                    intent.putExtra("province", "");
                    setResult(Code.PositionCode, intent);
                    finish();
                } else {
                    ImageView check_image = (ImageView) view.findViewById(R.id.check_image);
                    check_image.setVisibility(View.VISIBLE);
                    Intent intent = new Intent();
                    intent.putExtra("position", dataList.get(position - 1).get("title"));
                    intent.putExtra("city", dataList.get(position - 1).get("city"));
                    intent.putExtra("province", dataList.get(position - 1).get("province"));
                    setResult(Code.PositionCode, intent);
                    finish();
                }


            }
        });

        map = new HashMap<String, String>();
        map.put("title", "不显示");
        map.put("snippet", "");
        dataList.add(map);
        dataList2.add(map);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 下拉刷新
     */
    private class GetDownDataTask extends AsyncTask<Void, Void, List<Map<String, String>>> {

        //子线程请求数据
        @Override
        protected List<Map<String, String>> doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Refresh();
            return dataList;
        }

        //主线程更新UI
        @Override
        protected void onPostExecute(List<Map<String, String>> result) {

            //通知RefreshListView 我们已经更新完成
            position_list.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    private class GetUpEvaluateDataTask extends AsyncTask<Void, Void, List<Map<String, String>>> {

        //子线程请求数据
        @Override
        protected List<Map<String, String>> doInBackground(Void... params) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (MaxPage == -1) {
                page = page + 1;

                query.setPageNum(page);
                poiSearch.searchPOIAsyn();
            }
            return dataList;
        }

        //主线程更新UI
        @Override
        protected void onPostExecute(List<Map<String, String>> result) {

            if (MaxPage != -1) {
                Utils.showToast(PositionActivity.this, "无更多数据");
            }
            //通知RefreshListView 我们已经更新完成
            position_list.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    private void SearchData(String content) {
        if (TextUtils.isEmpty(content)) {
            for (int i = 0; i < dataList2.size(); i++) {
                dataList.add(dataList2.get(i));
            }

            positionAdapter.notifyDataSetChanged();
        } else {
            dataList.clear();

            for (int i = 0; i < dataList2.size(); i++) {
                if (dataList2.get(i).get("title").indexOf(content) != -1) {
                    dataList.add(dataList2.get(i));
                }
            }
            positionAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {

        if (poiResult.getPois().size() < 10) {

            MaxPage = page;
            Utils.showToast(PositionActivity.this, "无更多数据");
        } else {
            for (int j = 0; j < poiResult.getPois().size(); j++) {

                poiResult.getPois().get(j).getTitle();
                poiResult.getPois().get(j).getSnippet();

                map = new HashMap<String, String>();
                map.put("title", poiResult.getPois().get(j).getTitle());
                map.put("snippet", poiResult.getPois().get(j).getSnippet());
                map.put("city", poiResult.getPois().get(j).getCityName());
                map.put("province", poiResult.getPois().get(j).getProvinceName());
                dataList.add(map);
                dataList2.add(map);


            }
        }


        positionAdapter.notifyDataSetChanged();

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.right_image:
                Refresh();
                break;
        }
    }

    private void Refresh() {
        dataList.clear();
        dataList2.clear();
        map = new HashMap<String, String>();
        map.put("title", "不显示");
        map.put("snippet", "");
        dataList.add(map);
        dataList2.add(map);
        page = 1;
        query.setPageNum(page);

        poiSearch.searchPOIAsyn();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            Intent intent = new Intent();
            intent.putExtra("position", "");
            intent.putExtra("city", "");
            intent.putExtra("province", "");
            setResult(Code.PositionCode, intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }
}
