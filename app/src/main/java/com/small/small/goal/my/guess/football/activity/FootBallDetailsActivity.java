package com.small.small.goal.my.guess.football.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.guess.football.FootBallMemberEmpty;
import com.small.small.goal.my.guess.football.MatchEmpty;
import com.small.small.goal.my.guess.football.adapter.TeamLeftAdapter;
import com.small.small.goal.my.guess.football.adapter.TeamRightAdapter;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.MyListView;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class FootBallDetailsActivity extends BaseActivity {


    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.league_text)
    TextView leagueText;
    @InjectView(R.id.match_status_text)
    TextView matchStatusText;
    @InjectView(R.id.time2_text)
    TextView time2Text;
    @InjectView(R.id.time_text)
    TextView timeText;
    @InjectView(R.id.zhongjian)
    LinearLayout zhongjian;
    @InjectView(R.id.sign_first_image)
    CircleImageView signFirstImage;
    @InjectView(R.id.name_first_text)
    TextView nameFirstText;
    @InjectView(R.id.sign_second_image)
    CircleImageView signSecondImage;
    @InjectView(R.id.name_second_text)
    TextView nameSecondText;
    @InjectView(R.id.notes_number_text)
    TextView notesNumberText;
    @InjectView(R.id.prize_number_text)
    TextView prizeNumberText;
    @InjectView(R.id.victory_main_text)
    TextView victoryMainText;
    @InjectView(R.id.odds_main)
    TextView oddsMain;
    @InjectView(R.id.flat_text)
    TextView flatText;
    @InjectView(R.id.odds_flat)
    TextView oddsFlat;
    @InjectView(R.id.victory_passenger_text)
    TextView victoryPassengerText;
    @InjectView(R.id.odds_guest)
    TextView oddsGuest;
    @InjectView(R.id.support_main)
    TextView supportMain;
    @InjectView(R.id.support_flat)
    TextView supportFlat;
    @InjectView(R.id.support_guest)
    TextView supportGuest;
    @InjectView(R.id.rank_first_text)
    TextView rankFirstText;
    @InjectView(R.id.win_first_text)
    TextView winFirstText;
    @InjectView(R.id.score_first_text)
    TextView scoreFirstText;
    @InjectView(R.id.rank_second_text)
    TextView rankSecondText;
    @InjectView(R.id.win_second_text)
    TextView winSecondText;
    @InjectView(R.id.score_second_text)
    TextView scoreSecondText;
    @InjectView(R.id.first_main_list)
    MyListView firstMainList;
    @InjectView(R.id.first_passenger_list)
    MyListView firstPassengerList;
    @InjectView(R.id.bench_main_list)
    MyListView benchMainList;
    @InjectView(R.id.bench_passenger_list)
    MyListView benchPassengerList;
    private TeamLeftAdapter firstMainTeamAdapter;
    private TeamLeftAdapter benchMainTeamAdapter;

    private TeamRightAdapter firstPassengerTeamAdapter;
    private TeamRightAdapter benchPassengerTeamAdapter;

    private List<FootBallMemberEmpty> homeLineupPassengerList;
    private List<FootBallMemberEmpty> awayLineupPassengerList;

    private List<FootBallMemberEmpty> homeLineupMainList;
    private List<FootBallMemberEmpty> awayLineupMainList;

    private FootBallMemberEmpty footBallMemberEmpty;

    private String matchId = "";
    private int width;

    private MatchEmpty matchEmpty;

    DecimalFormat df = new DecimalFormat("#.00");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_foot_ball_details);
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        width = getWindowManager().getDefaultDisplay().getWidth() - 130;

//        matchEmpty = (MatchEmpty) getIntent().getSerializableExtra("match");
        matchId = getIntent().getExtras().getString("matchId", "");
        homeLineupPassengerList = new ArrayList<>();
        awayLineupPassengerList = new ArrayList<>();
        homeLineupMainList = new ArrayList<>();
        awayLineupMainList = new ArrayList<>();

        nameTextInclude.setText("赛事详情");
        rightImageInclude.setVisibility(View.GONE);


        firstMainTeamAdapter = new TeamLeftAdapter(this, homeLineupMainList, 1);
        firstPassengerTeamAdapter = new TeamRightAdapter(this, awayLineupMainList, 1);

        benchMainTeamAdapter = new TeamLeftAdapter(this, homeLineupPassengerList, 2);
        benchPassengerTeamAdapter = new TeamRightAdapter(this, awayLineupPassengerList, 2);

        ViewGroup.LayoutParams layoutParams = firstMainList.getLayoutParams();
        layoutParams.width = width / 2;
        firstMainList.setLayoutParams(layoutParams);

        ViewGroup.LayoutParams layoutParams2 = benchPassengerList.getLayoutParams();
        layoutParams2.width = width / 2;
        benchPassengerList.setLayoutParams(layoutParams2);

        ViewGroup.LayoutParams layoutParams3 = firstPassengerList.getLayoutParams();
        layoutParams3.width = width / 2;
        firstPassengerList.setLayoutParams(layoutParams3);

        ViewGroup.LayoutParams layoutParams4 = benchMainList.getLayoutParams();
        layoutParams4.width = width / 2;
        benchMainList.setLayoutParams(layoutParams4);

        firstPassengerList.setAdapter(firstPassengerTeamAdapter);
        benchPassengerList.setAdapter(benchPassengerTeamAdapter);
        firstMainList.setAdapter(firstMainTeamAdapter);
        benchMainList.setAdapter(benchMainTeamAdapter);
        firstPassengerList.setFocusable(false);
        benchPassengerList.setFocusable(false);
        firstMainList.setFocusable(false);
        benchMainList.setFocusable(false);

        GetDetails();

    }

    @OnClick({R.id.left_image_include})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.left_image_include:
                finish();
                break;
        }

    }

    private void GetDetails() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.GuessDetail);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("matchId", matchId);

        XUtil.get(requestParams, FootBallDetailsActivity.this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("=============足球详情=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

                        JSONObject matchsObject = new JSONObject(result).getJSONObject("result").getJSONObject("matchs");
                        JSONObject lineupObject = new JSONObject(result).getJSONObject("result").getJSONObject("lineup");
                        JSONObject homeScoreRankObject = new JSONObject(result).getJSONObject("result").getJSONObject("homeScoreRank");
                        JSONObject visitScoreRankObject = new JSONObject(result).getJSONObject("result").getJSONObject("visitScoreRank");

                        JSONArray awayLineupArray = lineupObject.getJSONArray("awayLineup");
                        JSONArray homeLineupArray = lineupObject.getJSONArray("homeLineup");

                        matchEmpty = new MatchEmpty();

                        matchEmpty.setId(matchsObject.getString("id"));
                        matchEmpty.setHomeTeam(matchsObject.getString("homeTeam"));
                        matchEmpty.setVisitTeam(matchsObject.getString("visitTeam"));
                        matchEmpty.setLeague(matchsObject.getString("league"));
                        matchEmpty.setExpect(matchsObject.getString("expect"));
                        matchEmpty.setVictory(matchsObject.getString("victory"));
                        matchEmpty.setDefeat(matchsObject.getString("defeat"));
                        matchEmpty.setDogfall(matchsObject.getString("dogfall"));
                        matchEmpty.setType(matchsObject.getString("type"));
                        matchEmpty.setStartTime(matchsObject.getString("startTime"));
                        matchEmpty.setDendline(matchsObject.getString("dendline"));
                        matchEmpty.setWager(matchsObject.getString("wager"));
                        matchEmpty.setBean(matchsObject.getString("bean"));
                        matchEmpty.setGameId(matchsObject.getString("gameId"));
                        matchEmpty.setLeagueId(matchsObject.getString("leagueId"));
                        matchEmpty.setSeasonId(matchsObject.getString("seasonId"));
                        matchEmpty.setHomeId(matchsObject.getString("homeId"));
                        matchEmpty.setVisitId(matchsObject.getString("visitId"));


                        leagueText.setText(matchEmpty.getLeague());
                        matchStatusText.setText("未开赛");
//        signFirstImage
//        signSecondImage
                        nameFirstText.setText(matchEmpty.getHomeTeam());
                        nameSecondText.setText(matchEmpty.getVisitTeam());
                        time2Text.setText(simpleDateFormat2.format(new Date(Long.valueOf(matchEmpty.getStartTime()))));

                        timeText.setText(simpleDateFormat.format(new Date(Long.valueOf(matchEmpty.getStartTime()))));
                        notesNumberText.setText(matchEmpty.getWager());


                        double victory = Double.valueOf(matchEmpty.getVictory());
                        double defeat = Double.valueOf(matchEmpty.getDefeat());
                        double dogfall = Double.valueOf(matchEmpty.getDogfall());
                        double all = victory + defeat + dogfall;
                        if (matchEmpty.getVictory().equals("0")) {
                            if (all > 0) {
                                oddsMain.setText(String.valueOf(df.format(all / 100)));
                            } else {
                                oddsMain.setText("0.00");
                            }
                        } else {
                            if (all > 0) {
                                oddsMain.setText(String.valueOf(df.format(all / victory)));
                            } else {
                                oddsMain.setText("0.00");
                            }

                        }
                        if (matchEmpty.getDogfall().equals("0")) {
                            if (all > 0) {
                                oddsFlat.setText(String.valueOf(df.format(all / 100)));
                            } else {
                                oddsFlat.setText("0.00");
                            }

                        } else {
                            if (all > 0) {
                                oddsFlat.setText(String.valueOf(df.format(all / dogfall)));
                            } else {
                                oddsFlat.setText("0.00");
                            }

                        }

                        if (matchEmpty.getDefeat().equals("0")) {
                            if (all > 0) {
                                oddsGuest.setText(String.valueOf(df.format(all / 100)));
                            } else {
                                oddsGuest.setText("0.00");
                            }
                        } else {
                            if (all > 0) {
                                oddsGuest.setText(String.valueOf(df.format(all / defeat)));
                            } else {
                                oddsGuest.setText("0.00");
                            }

                        }

//                        TextView supportMain;
//                        TextView supportFlat;
//                        TextView supportGuest;

                        rankFirstText.setText(homeScoreRankObject.getString("rankIndex"));
                        rankSecondText.setText(visitScoreRankObject.getString("rankIndex"));
                        winFirstText.setText(homeScoreRankObject.getString("win"));
                        winSecondText.setText(visitScoreRankObject.getString("win"));
                        scoreFirstText.setText(homeScoreRankObject.getString("score"));
                        scoreSecondText.setText(visitScoreRankObject.getString("score"));


                        for (int i = 0; i < awayLineupArray.length(); i++) {
                            footBallMemberEmpty = new FootBallMemberEmpty();
                            footBallMemberEmpty.setPlayerId(awayLineupArray.getJSONObject(i).getString("playerId"));
                            footBallMemberEmpty.setPlayerName(awayLineupArray.getJSONObject(i).getString("playerName"));
                            footBallMemberEmpty.setPlayerNameEn(awayLineupArray.getJSONObject(i).getString("playerNameEn"));
                            footBallMemberEmpty.setJerseyNum(awayLineupArray.getJSONObject(i).getString("jerseyNum"));
                            footBallMemberEmpty.setFormationPlace(awayLineupArray.getJSONObject(i).getString("formationPlace"));
                            footBallMemberEmpty.setPosition(awayLineupArray.getJSONObject(i).getString("position"));
                            footBallMemberEmpty.setGdcPosition(awayLineupArray.getJSONObject(i).getString("gdcPosition"));
                            footBallMemberEmpty.setStatus(awayLineupArray.getJSONObject(i).getString("status"));
                            footBallMemberEmpty.setCaptain(awayLineupArray.getJSONObject(i).getString("captain"));

                            if (footBallMemberEmpty.getStatus().equals("1")) {
                                awayLineupMainList.add(footBallMemberEmpty);
                            } else {
                                awayLineupPassengerList.add(footBallMemberEmpty);
                            }
                        }

                        for (int i = 0; i < homeLineupArray.length(); i++) {
                            footBallMemberEmpty = new FootBallMemberEmpty();
                            footBallMemberEmpty.setPlayerId(homeLineupArray.getJSONObject(i).getString("playerId"));
                            footBallMemberEmpty.setPlayerName(homeLineupArray.getJSONObject(i).getString("playerName"));
                            footBallMemberEmpty.setPlayerNameEn(homeLineupArray.getJSONObject(i).getString("playerNameEn"));
                            footBallMemberEmpty.setJerseyNum(homeLineupArray.getJSONObject(i).getString("jerseyNum"));
                            footBallMemberEmpty.setFormationPlace(homeLineupArray.getJSONObject(i).getString("formationPlace"));
                            footBallMemberEmpty.setPosition(homeLineupArray.getJSONObject(i).getString("position"));
                            footBallMemberEmpty.setGdcPosition(homeLineupArray.getJSONObject(i).getString("gdcPosition"));
                            footBallMemberEmpty.setStatus(homeLineupArray.getJSONObject(i).getString("status"));
                            footBallMemberEmpty.setCaptain(homeLineupArray.getJSONObject(i).getString("captain"));

                            if (footBallMemberEmpty.getStatus().equals("1")) {
                                homeLineupMainList.add(footBallMemberEmpty);
                            } else {
                                homeLineupPassengerList.add(footBallMemberEmpty);
                            }
                        }

                        firstMainTeamAdapter.notifyDataSetChanged();
                        firstPassengerTeamAdapter.notifyDataSetChanged();
                        benchMainTeamAdapter.notifyDataSetChanged();
                        benchPassengerTeamAdapter.notifyDataSetChanged();


                    } else {
                        Utils.showToast(FootBallDetailsActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("=============足球详情=====onError====" + ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });

    }
}
