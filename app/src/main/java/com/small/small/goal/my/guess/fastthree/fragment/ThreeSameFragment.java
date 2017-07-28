package com.small.small.goal.my.guess.fastthree.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.guess.fastthree.FastThreeActivity;
import com.small.small.goal.my.guess.fastthree.empty.FastThreeSumEmpty;
import com.small.small.goal.my.guess.fastthree.empty.ThirdDHistoryEmpty;
import com.small.small.goal.my.guess.fastthree.adapter.FastThreeHistoryAdapter;
import com.small.small.goal.my.guess.fastthree.adapter.SumAdapter;
import com.small.small.goal.utils.Code;
import com.small.small.goal.utils.MyListView;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.weight.MyGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * JS
 */
public class ThreeSameFragment extends Fragment {


    public static List<FastThreeSumEmpty> dataList;
    public static List<FastThreeSumEmpty> fastList;
    private static SumAdapter sumAdapter;
    private static SumAdapter fastAdapter;
    @InjectView(R.id.history_open_layout)
    LinearLayout historyOpenLayout;
    @InjectView(R.id.history_list)
    MyListView historyList;
    @InjectView(R.id.history_close_layout)
    LinearLayout historyCloseLayout;
    @InjectView(R.id.sum_grid)
    MyGridView sumGrid;
    @InjectView(R.id.fast_grid)
    MyGridView fastGrid;
    private FastThreeSumEmpty fastThreeSumEmpty;

    public static List<String> numberList;

    public static int isTongXuan;

    private FastThreeHistoryAdapter directlyHistoryAdapter;

    private List<ThirdDHistoryEmpty> historyDataList;
    private List<String> historyNumberList;
    private ThirdDHistoryEmpty thirdDHistoryEmpty;

    public static ThreeSameFragment getFragment() {
        ThreeSameFragment fragment = new ThreeSameFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three_same, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isTongXuan = 0;

        historyDataList = new ArrayList<>();
        historyNumberList = new ArrayList<>();
        thirdDHistoryEmpty = new ThirdDHistoryEmpty();
        directlyHistoryAdapter = new FastThreeHistoryAdapter(getActivity(), historyDataList, 1);
        historyList.setAdapter(directlyHistoryAdapter);


        dataList = new ArrayList<>();
        fastList = new ArrayList<>();
        numberList = new ArrayList<>();

        for (int i = 1; i < 7; i++) {
            fastThreeSumEmpty = new FastThreeSumEmpty();
            fastThreeSumEmpty.setStatus(0);
            fastThreeSumEmpty.setNumber(String.valueOf(i) + String.valueOf(i) + String.valueOf(i));
            fastThreeSumEmpty.setMoney("奖4560");
            dataList.add(fastThreeSumEmpty);
        }


        for (int i = 0; i < 1; i++) {
            fastThreeSumEmpty = new FastThreeSumEmpty();
            fastThreeSumEmpty.setStatus(0);
            fastThreeSumEmpty.setNumber("三同号通选");
            fastThreeSumEmpty.setMoney("任意一个豹子开出即中");
            fastThreeSumEmpty.setMoney("奖680");
            fastList.add(fastThreeSumEmpty);
        }

        sumAdapter = new SumAdapter(getActivity(), dataList, "0");
        fastAdapter = new SumAdapter(getActivity(), fastList, "0");

        sumGrid.setAdapter(sumAdapter);
        fastGrid.setAdapter(fastAdapter);

        sumGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dataList.get(position).getStatus() == 0) {
                    dataList.get(position).setStatus(1);
                    sumAdapter.changeItem(position, 1);
                    numberList.add(dataList.get(position).getNumber());
                } else {
                    dataList.get(position).setStatus(0);
                    sumAdapter.changeItem(position, 0);
                    for (int i = 0; i < numberList.size(); i++) {
                        if (numberList.get(i).equals(dataList.get(position).getNumber())) {
                            numberList.remove(i);
                        }
                    }
                }
                ChangeNext();
            }
        });

        fastGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (fastList.get(position).getStatus() == 0) {
                    fastList.get(position).setStatus(1);
                    fastAdapter.changeItem(position, 1);
                    isTongXuan = 1;
                } else {
                    fastList.get(position).setStatus(0);
                    fastAdapter.changeItem(position, 0);
                    isTongXuan = 0;
                }
                ChangeNext();
            }
        });


    }


    public static void ChangeNext() {

        if (numberList.size() < 1 && isTongXuan == 0) {
            FastThreeActivity.instance.nextText.setText("至少选择1组号码");
        } else {
            int number = numberList.size();
            if (isTongXuan == 1) {
                number++;
            }
            FastThreeActivity.instance.nextText.setText("共" + number + "注，下一步");
        }

    }


    @OnClick({R.id.history_open_layout, R.id.history_close_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.history_open_layout:
                if (historyDataList.size() == 0) {
                    GetHistory();
                } else {
                    directlyHistoryAdapter.SetNumber(historyDataList.size());
                    historyOpenLayout.setVisibility(View.GONE);
                    historyCloseLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.history_close_layout:
                directlyHistoryAdapter.SetNumber(0);
                historyOpenLayout.setVisibility(View.VISIBLE);
                historyCloseLayout.setVisibility(View.GONE);
                break;
        }
    }

    private void GetHistory() {

        RequestParams requestParams = new RequestParams(Url.Url + Url.LotteryNewest);
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("code", "jlk3");
        XUtil.get(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        historyDataList.clear();
                        JSONArray array = new JSONObject(result).getJSONArray("result");
                        for (int i = 0; i < array.length(); i++) {
                            thirdDHistoryEmpty = new ThirdDHistoryEmpty();
                            thirdDHistoryEmpty.setTime(array.getJSONObject(i).getString("expect"));
                            historyNumberList = new ArrayList<String>();
                            array.getJSONObject(i).getString("openCode");
                            historyNumberList.add(array.getJSONObject(i).getString("openCode").substring(0, 1));
                            historyNumberList.add(array.getJSONObject(i).getString("openCode").substring(2, 3));
                            historyNumberList.add(array.getJSONObject(i).getString("openCode").substring(4, 5));
                            thirdDHistoryEmpty.setNumber_list(historyNumberList);
                            historyDataList.add(thirdDHistoryEmpty);
                        }
//                        directlyHistoryAdapter.notifyDataSetChanged();
                        directlyHistoryAdapter.SetNumber(historyDataList.size());
                        historyOpenLayout.setVisibility(View.GONE);
                        historyCloseLayout.setVisibility(View.VISIBLE);
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

    public static void SetRandom(int number) {

        for (int i = 0; i < 6; i++) {
            dataList.get(i).setStatus(0);
        }
        dataList.get(number).setStatus(1);
        numberList.clear();
        numberList.add(dataList.get(number).getNumber());

        if (isTongXuan == 1) {
            fastList.get(0).setStatus(0);
            isTongXuan = 0;
            fastAdapter.notifyDataSetChanged();
        }
        sumAdapter.notifyDataSetChanged();
        ChangeNext();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
