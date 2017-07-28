package com.small.small.goal.my.guess.football.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;


import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.guess.football.empty.MatchEmpty;
import com.small.small.goal.my.guess.football.activity.FootBallDetailsActivity;
import com.small.small.goal.my.guess.football.adapter.MatchListAdapter;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.mzbanner.MZBannerView;
import com.small.small.goal.utils.mzbanner.holder.MZHolderCreator;
import com.small.small.goal.utils.mzbanner.holder.MZViewHolder;

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
public class HotFragment extends Fragment {


    @InjectView(R.id.match_list)
    ListView matchList;
    @InjectView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private ListView listView;

    private List<MatchEmpty> matchEmptyList;
    private MatchEmpty matchEmpty;


    private MatchListAdapter matchListAdapter;
    private MZBannerView bannerView;
    private View headView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot_sport, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        matchEmptyList = new ArrayList<>();

        matchListAdapter = new MatchListAdapter(getActivity(), matchEmptyList);
        matchList.setAdapter(matchListAdapter);


        headView = LayoutInflater.from(getActivity()).inflate(R.layout.view_head_guess_foot_hot, null);
        bannerView = (MZBannerView) headView.findViewById(R.id.banner_view);

        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.banner_football);
        images.add(R.mipmap.banner_football);
        images.add(R.mipmap.banner_football);
        bannerView.setmIndicatorsBottom(Utils.dip2px(getActivity(), 10));
        bannerView.setPages(images, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        matchList.addHeaderView(headView);


        matchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FootBallDetailsActivity.class);
                intent.putExtra("matchId", matchEmptyList.get(position - 1).getId());
                intent.putExtra("match", matchEmptyList.get(position - 1));
                startActivity(intent);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetDataList();
            }
        });
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
                    } else {
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipe.setRefreshing(false);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                swipe.setRefreshing(false);
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
        bannerView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        bannerView.pause();
    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
            mImageView = (ImageView) view.findViewById(R.id.img_banner);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }

}
