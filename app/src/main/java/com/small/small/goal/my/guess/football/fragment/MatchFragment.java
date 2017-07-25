package com.small.small.goal.my.guess.football.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.guess.football.MatchEmpty;
import com.small.small.goal.my.guess.football.activity.FootBallDetailsActivity;
import com.small.small.goal.my.guess.football.adapter.MatchListAdapter;
import com.small.small.goal.my.guess.football.adapter.MatchTypeListAdapter;
import com.small.small.goal.utils.HorizontaiListView;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JS on 2017-06-26.
 */

public class MatchFragment extends Fragment {


    @InjectView(R.id.top_horizontal_list)
    HorizontaiListView topHorizontalList;
    @InjectView(R.id.match_list)
    ListView matchList;
    @InjectView(R.id.swipe)
    SwipeRefreshLayout swipe;


    private MatchTypeListAdapter matchTypeListAdapter;
    private MatchListAdapter matchListAdapter;
    private List<MatchEmpty> matchEmptyList;

    private List<List<MatchEmpty>> dataList;

    private List<String> horizontalList;

    private MatchEmpty matchEmpty;

    private int page_horizontal = 0;


    private String league = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_sport, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        horizontalList = new ArrayList<>();
        dataList = new ArrayList<>();
        matchEmptyList = new ArrayList<>();

        matchListAdapter = new MatchListAdapter(getActivity(), matchEmptyList);
        matchList.setAdapter(matchListAdapter);
        //        plv.setMode(PullToRefreshBase.Mode.BOTH);
//        matchList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        matchTypeListAdapter = new MatchTypeListAdapter(getActivity(), horizontalList);
        topHorizontalList.setAdapter(matchTypeListAdapter);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetDataList();
            }
        });

        topHorizontalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                matchTypeListAdapter.setPage(position);
                matchTypeListAdapter.notifyDataSetChanged();


                page_horizontal = position;
            }
        });

        matchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FootBallDetailsActivity.class);
                intent.putExtra("matchId", matchEmptyList.get(position - 1).getId());
                intent.putExtra("match", matchEmptyList.get(position - 1));
                startActivity(intent);
            }
        });

//        matchList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
//                GetDataList();
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
//
//            }
//        });

        GetTypeList();
    }

    private void GetTypeList() {
        horizontalList.add("全部");
        horizontalList.add("热门");
        horizontalList.add("西甲");
        horizontalList.add("英超");
        horizontalList.add("德甲");
        horizontalList.add("意甲");
        horizontalList.add("法甲");
        horizontalList.add("荷甲");
        horizontalList.add("中超");


        for (int i = 0; i < horizontalList.size(); i++) {
            matchEmptyList.clear();
            dataList.add(matchEmptyList);
        }
        matchTypeListAdapter.notifyDataSetChanged();

        GetDataList();
    }


    private void GetDataList() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Guess);
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("type", "1");
        requestParams.addBodyParameter("league", "");
        XUtil.get(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("=============足球赛事列表=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                        matchEmptyList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            matchEmpty = new MatchEmpty();

                            matchEmpty.setId(jsonArray.getJSONObject(i).getString("id"));
                            matchEmpty.setHomeTeam(jsonArray.getJSONObject(i).getString("homeTeam"));
                            matchEmpty.setVisitTeam(jsonArray.getJSONObject(i).getString("visitTeam"));
                            matchEmpty.setLeague(jsonArray.getJSONObject(i).getString("league"));
                            matchEmpty.setExpect(jsonArray.getJSONObject(i).getString("expect"));
                            matchEmpty.setVictory(jsonArray.getJSONObject(i).getString("victory"));
                            matchEmpty.setDefeat(jsonArray.getJSONObject(i).getString("defeat"));
                            matchEmpty.setDogfall(jsonArray.getJSONObject(i).getString("dogfall"));
                            matchEmpty.setType(jsonArray.getJSONObject(i).getString("type"));
                            matchEmpty.setStartTime(jsonArray.getJSONObject(i).getString("startTime"));
                            matchEmpty.setDendline(jsonArray.getJSONObject(i).getString("dendline"));
                            matchEmpty.setWager(jsonArray.getJSONObject(i).getString("wager"));
                            matchEmpty.setBean(jsonArray.getJSONObject(i).getString("bean"));
                            matchEmpty.setGameId(jsonArray.getJSONObject(i).getString("gameId"));
                            matchEmpty.setLeagueId(jsonArray.getJSONObject(i).getString("leagueId"));
                            matchEmpty.setSeasonId(jsonArray.getJSONObject(i).getString("seasonId"));
                            matchEmpty.setHomeId(jsonArray.getJSONObject(i).getString("homeId"));
                            matchEmpty.setVisitId(jsonArray.getJSONObject(i).getString("visitId"));
                            matchEmptyList.add(matchEmpty);
                        }
                        matchListAdapter.notifyDataSetChanged();
                        dataList.set(page_horizontal, matchEmptyList);
                    } else {
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                matchList.onRefreshComplete();
                swipe.setRefreshing(false);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                matchList.onRefreshComplete();
                swipe.setRefreshing(false);
                System.out.println("=============足球赛事列表=====onError====" + ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //需要放在onResume的方法放在该处执行
            if (matchListAdapter != null) {
                System.out.println("==============matchfragment===========");
                matchListAdapter.notifyDataSetChanged();
            }
        } else {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
