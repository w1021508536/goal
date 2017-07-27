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
public class SumFragment extends Fragment {

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


    public static List<String> dataList_choose;

    public static List<FastThreeSumEmpty> dataList;
    private static List<FastThreeSumEmpty> fastList;
    private static SumAdapter sumAdapter;
    private static SumAdapter fastAdapter;
    private FastThreeSumEmpty fastThreeSumEmpty;

    public static SumFragment getFragment() {
        SumFragment fragment = new SumFragment();
        return fragment;
    }

    private int fastPosition = -1;

    private static int isBig;//  0  大  1 小
    private static int isSingle;//  0 单  1 双


    private FastThreeHistoryAdapter directlyHistoryAdapter;

    private List<ThirdDHistoryEmpty> historyDataList;
    private List<String> historyNumberList;
    private ThirdDHistoryEmpty thirdDHistoryEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sum, container, false);
        ButterKnife.inject(this, view);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isBig = -1;//  0  大  1 小
        isSingle = -1;//  0 单  1 双

        dataList = new ArrayList<>();
        fastList = new ArrayList<>();
        dataList_choose = new ArrayList<>();

        historyDataList = new ArrayList<>();
        historyNumberList = new ArrayList<>();
        thirdDHistoryEmpty = new ThirdDHistoryEmpty();

        directlyHistoryAdapter = new FastThreeHistoryAdapter(getActivity(), historyDataList, 0);
        historyList.setAdapter(directlyHistoryAdapter);


        for (int i = 0; i < 16; i++) {
            fastThreeSumEmpty = new FastThreeSumEmpty();
            fastThreeSumEmpty.setStatus(0);
            fastThreeSumEmpty.setNumber((i + 3) + "");
            dataList.add(fastThreeSumEmpty);
        }


        SetDataMoney();

        for (int i = 0; i < 4; i++) {
            fastThreeSumEmpty = new FastThreeSumEmpty();
            fastThreeSumEmpty.setStatus(0);
            fastList.add(fastThreeSumEmpty);
        }

        fastList.get(0).setNumber("大");
        fastList.get(1).setNumber("小");
        fastList.get(2).setNumber("单");
        fastList.get(3).setNumber("双");
        sumAdapter = new SumAdapter(getActivity(), dataList, "0");
        fastAdapter = new SumAdapter(getActivity(), fastList, "1");

        sumGrid.setAdapter(sumAdapter);
        fastGrid.setAdapter(fastAdapter);

        sumGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dataList.get(position).getStatus() == 0) {
                    dataList.get(position).setStatus(1);
                    sumAdapter.changeItem(position, 1);
                    dataList_choose.add(position + 3 + "");
                } else {
                    dataList.get(position).setStatus(0);
                    sumAdapter.changeItem(position, 0);
                    for (int i = 0; i < dataList_choose.size(); i++) {
                        if (dataList_choose.get(i).equals(position + 3 + "")) {
                            dataList_choose.remove(i);
                        }
                    }
                }
                if (isSingle == -1 && isBig == -1) {

                } else {
                    isSingle = -1;
                    isBig = -1;
                    for (int i = 0; i < 4; i++) {
                        fastList.get(i).setStatus(0);
                    }
                    fastAdapter.notifyDataSetChanged();
                }
                ChangeNext();
            }
        });
        fastGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dataList_choose.clear();
                if (position == 0) {
                    if (fastList.get(position).getStatus() == 0) {
                        isBig = 0;
                    } else {
                        isBig = -1;
                    }
                    ChangeFast();
                } else if (position == 1) {
                    if (fastList.get(position).getStatus() == 0) {
                        isBig = 1;
                    } else {
                        isBig = -1;
                    }
                    ChangeFast();

                } else if (position == 2) {

                    if (fastList.get(position).getStatus() == 0) {
                        isSingle = 0;
                    } else {
                        isSingle = -1;
                    }
                    ChangeFast();
                } else {
                    if (fastList.get(position).getStatus() == 0) {
                        isSingle = 1;
                    } else {
                        isSingle = -1;
                    }
                    ChangeFast();
                }
            }
        });

    }

    private void SetDataMoney() {
        dataList.get(0).setMoney("奖4560");
        dataList.get(1).setMoney("奖1440");
        dataList.get(2).setMoney("奖680");
        dataList.get(3).setMoney("奖400");
        dataList.get(4).setMoney("奖240");
        dataList.get(5).setMoney("奖168");
        dataList.get(6).setMoney("奖130");
        dataList.get(7).setMoney("奖108");
        dataList.get(8).setMoney("奖108");
        dataList.get(9).setMoney("奖130");
        dataList.get(10).setMoney("奖168");
        dataList.get(11).setMoney("奖240");
        dataList.get(12).setMoney("奖400");
        dataList.get(13).setMoney("奖680");
        dataList.get(14).setMoney("奖1440");
        dataList.get(15).setMoney("奖4560");
    }


    public static void ChangeNext() {

        if (dataList_choose != null) {
            if (dataList_choose.size() < 1) {
                FastThreeActivity.instance.nextText.setText("至少选择1个号码");
            } else {
                FastThreeActivity.instance.nextText.setText("共" + dataList_choose.size() + "注，下一步");
            }
        }


    }

    private void ChangeFast() {
        if (isBig == -1) {
            fastList.get(0).setStatus(0);
            fastList.get(1).setStatus(0);

            if (isSingle == -1) {
                fastList.get(2).setStatus(0);
                fastList.get(3).setStatus(0);
                for (int i = 0; i < 16; i++) {
                    dataList.get(i).setStatus(0);
                }
            } else if (isSingle == 0) {
                fastList.get(2).setStatus(1);
                fastList.get(3).setStatus(0);

                for (int i = 1; i < 16; i = i + 2) {
                    dataList.get(i).setStatus(0);
                }
                for (int i = 0; i < 16; i = i + 2) {
                    dataList.get(i).setStatus(1);
                }

            } else if (isSingle == 1) {
                fastList.get(2).setStatus(0);
                fastList.get(3).setStatus(1);
                for (int i = 1; i < 16; i = i + 2) {
                    dataList.get(i).setStatus(1);
                }
                for (int i = 0; i < 16; i = i + 2) {
                    dataList.get(i).setStatus(0);
                }
            }

        } else if (isBig == 0) {
            fastList.get(0).setStatus(1);
            fastList.get(1).setStatus(0);

            if (isSingle == -1) {
                fastList.get(2).setStatus(0);
                fastList.get(3).setStatus(0);
                for (int i = 0; i < 8; i++) {
                    dataList.get(i).setStatus(0);
                }
                for (int i = 8; i < 16; i++) {
                    dataList.get(i).setStatus(1);
                }

            } else if (isSingle == 0) {
                fastList.get(2).setStatus(1);
                fastList.get(3).setStatus(0);
                for (int i = 0; i < 16; i++) {
                    dataList.get(i).setStatus(0);
                }

                for (int i = 8; i < 16; i = i + 2) {
                    dataList.get(i).setStatus(1);
                }

            } else if (isSingle == 1) {
                fastList.get(2).setStatus(0);
                fastList.get(3).setStatus(1);

                for (int i = 0; i < 16; i++) {
                    dataList.get(i).setStatus(0);
                }

                for (int i = 9; i < 16; i = i + 2) {
                    dataList.get(i).setStatus(1);
                }
            }
        } else if (isBig == 1) {
            fastList.get(0).setStatus(0);
            fastList.get(1).setStatus(1);

            if (isSingle == -1) {
                fastList.get(2).setStatus(0);
                fastList.get(3).setStatus(0);

                for (int i = 0; i < 8; i++) {
                    dataList.get(i).setStatus(1);
                }
                for (int i = 8; i < 16; i++) {
                    dataList.get(i).setStatus(0);
                }

            } else if (isSingle == 0) {
                fastList.get(2).setStatus(1);
                fastList.get(3).setStatus(0);

                for (int i = 0; i < 16; i++) {
                    dataList.get(i).setStatus(0);
                }

                for (int i = 0; i < 8; i = i + 2) {
                    dataList.get(i).setStatus(1);
                }
            } else if (isSingle == 1) {
                fastList.get(2).setStatus(0);
                fastList.get(3).setStatus(1);

                for (int i = 0; i < 16; i++) {
                    dataList.get(i).setStatus(0);
                }

                for (int i = 1; i < 8; i = i + 2) {
                    dataList.get(i).setStatus(1);
                }
            }
        }

        for (int i = 0; i < 16; i++) {
            if (dataList.get(i).getStatus() == 1)
                dataList_choose.add(i + 3 + "");
        }

        ChangeNext();

        sumAdapter.notifyDataSetChanged();
        fastAdapter.notifyDataSetChanged();
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
//                historyOpenLayout.setVisibility(View.GONE);
//                historyCloseLayout.setVisibility(View.VISIBLE);
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

                System.out.print("==========GetHistory==========" + result);
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

        for (int i = 0; i < 16; i++) {
            dataList.get(i).setStatus(0);
        }
        dataList.get(number).setStatus(1);
        dataList_choose.clear();
        dataList_choose.add(dataList.get(number).getNumber());
        if (isSingle == -1 && isBig == -1) {

        } else {
            isSingle = -1;
            isBig = -1;
            for (int i = 0; i < 4; i++) {
                fastList.get(i).setStatus(0);
            }
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
