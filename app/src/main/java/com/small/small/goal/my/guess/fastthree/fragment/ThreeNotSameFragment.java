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
import com.small.small.goal.my.guess.fastthree.FastThreeSumEmpty;
import com.small.small.goal.my.guess.fastthree.ThirdDHistoryEmpty;
import com.small.small.goal.my.guess.fastthree.adapter.FastThreeHistoryAdapter;
import com.small.small.goal.my.guess.fastthree.adapter.SumAdapter;
import com.small.small.goal.utils.MyListView;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.weight.MyGridView;
import com.tencent.open.utils.Util;

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
public class ThreeNotSameFragment extends Fragment {


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

    public static List<FastThreeSumEmpty> dataList;
    public static List<FastThreeSumEmpty> fastList;
    private static SumAdapter sumAdapter;
    private static SumAdapter fastAdapter;
    private FastThreeSumEmpty fastThreeSumEmpty;

    public static List<String> numberList;

    public static boolean isTongXuan ;

    private FastThreeHistoryAdapter directlyHistoryAdapter;

    private List<ThirdDHistoryEmpty> historyDataList;
    private List<String> historyNumberList;
    private ThirdDHistoryEmpty thirdDHistoryEmpty;

    public static ThreeNotSameFragment getFragment() {
        ThreeNotSameFragment fragment = new ThreeNotSameFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three_not_same, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isTongXuan = false;

        historyDataList = new ArrayList<>();
        historyNumberList = new ArrayList<>();
        thirdDHistoryEmpty = new ThirdDHistoryEmpty();
        directlyHistoryAdapter = new FastThreeHistoryAdapter(getActivity(), historyDataList, 3);
        historyList.setAdapter(directlyHistoryAdapter);



        dataList = new ArrayList<>();
        fastList = new ArrayList<>();
        numberList = new ArrayList<>();

        for (int i = 1; i < 7; i++) {
            fastThreeSumEmpty = new FastThreeSumEmpty();
            fastThreeSumEmpty.setStatus(0);
            fastThreeSumEmpty.setNumber(i + "");
            dataList.add(fastThreeSumEmpty);
        }


        for (int i = 0; i < 1; i++) {
            fastThreeSumEmpty = new FastThreeSumEmpty();
            fastThreeSumEmpty.setStatus(0);
            fastThreeSumEmpty.setNumber("三连号通选");

            fastList.add(fastThreeSumEmpty);
        }

        sumAdapter = new SumAdapter(getActivity(), dataList, "1");
        fastAdapter = new SumAdapter(getActivity(), fastList, "1");

        sumGrid.setAdapter(sumAdapter);
        fastGrid.setAdapter(fastAdapter);

        sumGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dataList.get(position).getStatus() == 0) {
                    dataList.get(position).setStatus(1);
                    sumAdapter.changeItem(position, 1);
                    numberList.add(position + 1 + "");
                } else {
                    dataList.get(position).setStatus(0);
                    sumAdapter.changeItem(position, 0);
                    for (int i = 0; i < numberList.size(); i++) {
                        if (numberList.get(i).equals(position + 1 + "")) {
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
                    isTongXuan = true;
                } else {
                    fastList.get(position).setStatus(0);
                    fastAdapter.changeItem(position, 0);
                    isTongXuan = false;
                }
                ChangeNext();
            }
        });


        init();
    }

    private void init() {

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

                System.out.print("==========GetHistory=========="+result);
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

    public static void SetRandom(int[] numbers) {
        numberList.clear();
        for (int i = 0; i < 6; i++) {
            dataList.get(i).setStatus(0);
        }
        for (int i = 0; i < numbers.length; i++) {
            dataList.get(numbers[i]).setStatus(1);
            numberList.add(dataList.get(numbers[i]).getNumber());
        }
        fastList.get(0).setStatus(0);
        fastAdapter.changeItem(0, 0);
        isTongXuan = false;

        sumAdapter.notifyDataSetChanged();
        ChangeNext();
    }

    public static void ChangeNext() {

        if (numberList.size() < 3 && !isTongXuan) {
            FastThreeActivity.instance.nextText.setText("至少选择3个号码");
        } else {
            int number = 0;
            if (numberList.size() >= 3) {
                number = (int) Utils.combination(3, numberList.size());
            }
            if (isTongXuan)
                number++;

            FastThreeActivity.instance.nextText.setText("共" + number + "注，下一步");
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
