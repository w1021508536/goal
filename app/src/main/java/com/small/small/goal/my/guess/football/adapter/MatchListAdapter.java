package com.small.small.goal.my.guess.football.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.my.guess.football.FootBallActivity;
import com.small.small.goal.my.guess.football.empty.MatchEmpty;
import com.squareup.picasso.Picasso;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by JS on 2017-06-26.
 */

public class MatchListAdapter extends BaseAdapter {

    private List<MatchEmpty> matchEmptyList;
    private Context context;
    DecimalFormat df = new DecimalFormat("#.00");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public MatchListAdapter(Context context, List<MatchEmpty> matchEmptyList) {
        this.matchEmptyList = matchEmptyList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return matchEmptyList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_match, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.victoryMainLayout.setBackgroundResource(R.drawable.background_gray_corner_left_5);
        viewHolder.flatLayout.setBackgroundResource(R.color.bg_hui_botton);
        viewHolder.victoryPassengerLayout.setBackgroundResource(R.drawable.background_gray_corner_right_5);

        viewHolder.numberText.setText("已有" + matchEmptyList.get(position).getWager() + "人参与");
        viewHolder.matchText.setText(matchEmptyList.get(position).getLeague());
        Picasso.with(context).load("http://gdc.hupucdn.com/gdc/soccer/team/logo/87x87/3148.png").into(viewHolder.signFirstImage);
        viewHolder.nameFirstText.setText(matchEmptyList.get(position).getHomeTeam());
        viewHolder.nameSecondText.setText(matchEmptyList.get(position).getVisitTeam());
        viewHolder.timeText.setText(simpleDateFormat.format(new Date(Long.valueOf(matchEmptyList.get(position).getDendline()))) + " 前截止");
        for (int i = 0; i < FootBallActivity.chooseList.size(); i++) {
            if (FootBallActivity.chooseList.get(i).get("id").equals(matchEmptyList.get(position).getId())) {
                if (FootBallActivity.chooseList.get(i).get("status").equals("0")) {
                    viewHolder.victoryMainLayout.setBackgroundResource(R.drawable.background_yellow_corner_left_5);
                } else if (FootBallActivity.chooseList.get(i).get("status").equals("1")) {
                    viewHolder.flatLayout.setBackgroundResource(R.color.text_yellow);
                } else {
                    viewHolder.victoryPassengerLayout.setBackgroundResource(R.drawable.background_yellow_corner_right_5);
                }
            }
        }


        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.victoryMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "-1";
                for (int i = 0; i < FootBallActivity.chooseList.size(); i++) {
                    if (FootBallActivity.chooseList.get(i).get("id").equals(matchEmptyList.get(position).getId())) {
                        status = FootBallActivity.chooseList.get(i).get("status");
                    }
                }

                FootBallActivity.chooseList.clear();
                notifyDataSetChanged();

                if (status.equals("-1")) {
                    finalViewHolder.victoryMainLayout.setBackgroundResource(R.drawable.background_yellow_corner_left_5);
                    FootBallActivity.ChangeNotesInfo(matchEmptyList.get(position).getId(), "0", matchEmptyList.get(position).getLeague());
                } else if (status.equals("0")) {
                    finalViewHolder.victoryMainLayout.setBackgroundResource(R.drawable.background_gray_corner_left_5);
                    FootBallActivity.ChangeNotesInfo(matchEmptyList.get(position).getId(), "-1", matchEmptyList.get(position).getLeague());
                } else if (status.equals("1")) {
                    finalViewHolder.victoryMainLayout.setBackgroundResource(R.drawable.background_yellow_corner_left_5);
                    finalViewHolder.flatLayout.setBackgroundResource(R.color.bg_hui_botton);
                    FootBallActivity.ChangeNotesInfo(matchEmptyList.get(position).getId(), "0", matchEmptyList.get(position).getLeague());
                } else {
                    finalViewHolder.victoryMainLayout.setBackgroundResource(R.drawable.background_yellow_corner_left_5);
                    finalViewHolder.victoryPassengerLayout.setBackgroundResource(R.drawable.background_gray_corner_right_5);
                    FootBallActivity.ChangeNotesInfo(matchEmptyList.get(position).getId(), "0", matchEmptyList.get(position).getLeague());
                }
            }
        });
        viewHolder.flatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "-1";
                for (int i = 0; i < FootBallActivity.chooseList.size(); i++) {
                    if (FootBallActivity.chooseList.get(i).get("id").equals(matchEmptyList.get(position).getId())) {
                        status = FootBallActivity.chooseList.get(i).get("status");
                    }
                }

                FootBallActivity.chooseList.clear();
                notifyDataSetChanged();

                if (status.equals("-1")) {
                    finalViewHolder.flatLayout.setBackgroundResource(R.color.text_yellow);
                    FootBallActivity.ChangeNotesInfo(matchEmptyList.get(position).getId(), "1", matchEmptyList.get(position).getLeague());
                } else if (status.equals("0")) {
                    finalViewHolder.flatLayout.setBackgroundResource(R.color.text_yellow);
                    finalViewHolder.victoryMainLayout.setBackgroundResource(R.drawable.background_gray_corner_left_5);
                    FootBallActivity.ChangeNotesInfo(matchEmptyList.get(position).getId(), "1", matchEmptyList.get(position).getLeague());
                } else if (status.equals("1")) {
                    finalViewHolder.flatLayout.setBackgroundResource(R.color.bg_hui_botton);
                    FootBallActivity.ChangeNotesInfo(matchEmptyList.get(position).getId(), "-1", matchEmptyList.get(position).getLeague());
                } else {
                    finalViewHolder.flatLayout.setBackgroundResource(R.color.text_yellow);
                    finalViewHolder.victoryPassengerLayout.setBackgroundResource(R.drawable.background_gray_corner_right_5);
                    FootBallActivity.ChangeNotesInfo(matchEmptyList.get(position).getId(), "1", matchEmptyList.get(position).getLeague());
                }

            }
        });
        viewHolder.victoryPassengerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "-1";
                for (int i = 0; i < FootBallActivity.chooseList.size(); i++) {
                    if (FootBallActivity.chooseList.get(i).get("id").equals(matchEmptyList.get(position).getId())) {
                        status = FootBallActivity.chooseList.get(i).get("status");
                    }
                }

                FootBallActivity.chooseList.clear();
                notifyDataSetChanged();

                if (status.equals("-1")) {
                    finalViewHolder.victoryPassengerLayout.setBackgroundResource(R.drawable.background_yellow_corner_right_5);
                    FootBallActivity.ChangeNotesInfo(matchEmptyList.get(position).getId(), "2", matchEmptyList.get(position).getLeague());
                } else if (status.equals("0")) {
                    finalViewHolder.victoryPassengerLayout.setBackgroundResource(R.drawable.background_yellow_corner_right_5);
                    finalViewHolder.victoryMainLayout.setBackgroundResource(R.drawable.background_gray_corner_left_5);
                    FootBallActivity.ChangeNotesInfo(matchEmptyList.get(position).getId(), "2", matchEmptyList.get(position).getLeague());
                } else if (status.equals("1")) {
                    finalViewHolder.victoryPassengerLayout.setBackgroundResource(R.drawable.background_yellow_corner_right_5);
                    finalViewHolder.flatLayout.setBackgroundResource(R.color.bg_hui_botton);
                    FootBallActivity.ChangeNotesInfo(matchEmptyList.get(position).getId(), "2", matchEmptyList.get(position).getLeague());
                } else {
                    finalViewHolder.victoryPassengerLayout.setBackgroundResource(R.drawable.background_gray_corner_right_5);
                    FootBallActivity.ChangeNotesInfo(matchEmptyList.get(position).getId(), "-1", matchEmptyList.get(position).getLeague());
                }

            }
        });

//        "victory": 0,
//                "defeat": 0,
//                "dogfall": 0,

        double victory = Double.valueOf(matchEmptyList.get(position).getVictory());
        double defeat = Double.valueOf(matchEmptyList.get(position).getDefeat());
        double dogfall = Double.valueOf(matchEmptyList.get(position).getDogfall());
        double all = victory + defeat + dogfall;
        if (matchEmptyList.get(position).getVictory().equals("0")) {
            if (all > 0) {
                viewHolder.oddsMain.setText(String.valueOf(df.format(all / 100)));
            } else {
                viewHolder.oddsMain.setText("0.00");
            }
        } else {
            if (all > 0) {
                viewHolder.oddsMain.setText(String.valueOf(df.format(all / victory)));
            } else {
                viewHolder.oddsMain.setText("0.00");
            }

        }
        if (matchEmptyList.get(position).getDogfall().equals("0")) {
            if (all > 0) {
                viewHolder.oddsFlat.setText(String.valueOf(df.format(all / 100)));
            } else {
                viewHolder.oddsFlat.setText("0.00");
            }

        } else {
            if (all > 0) {
                viewHolder.oddsFlat.setText(String.valueOf(df.format(all / dogfall)));
            } else {
                viewHolder.oddsFlat.setText("0.00");
            }

        }

        if (matchEmptyList.get(position).getDefeat().equals("0")) {
            if (all > 0) {
                viewHolder.oddsGuest.setText(String.valueOf(df.format(all / 100)));
            } else {
                viewHolder.oddsGuest.setText("0.00");
            }
        } else {
            if (all > 0) {
                viewHolder.oddsGuest.setText(String.valueOf(df.format(all / defeat)));
            } else {
                viewHolder.oddsGuest.setText("0.00");
            }

        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.number_text)
        TextView numberText;
        @InjectView(R.id.sign_first_image)
        CircleImageView signFirstImage;
        @InjectView(R.id.name_first_text)
        TextView nameFirstText;
        @InjectView(R.id.match_text)
        TextView matchText;
        @InjectView(R.id.time_text)
        TextView timeText;
        @InjectView(R.id.sign_second_image)
        CircleImageView signSecondImage;
        @InjectView(R.id.name_second_text)
        TextView nameSecondText;
        @InjectView(R.id.victory_main_text)
        TextView victoryMainText;
        @InjectView(R.id.victory_main_layout)
        RelativeLayout victoryMainLayout;
        @InjectView(R.id.flat_text)
        TextView flatText;
        @InjectView(R.id.flat_layout)
        RelativeLayout flatLayout;
        @InjectView(R.id.victory_passenger_text)
        TextView victoryPassengerText;
        @InjectView(R.id.victory_passenger_layout)
        RelativeLayout victoryPassengerLayout;
        @InjectView(R.id.odds_main)
        TextView oddsMain;
        @InjectView(R.id.odds_flat)
        TextView oddsFlat;
        @InjectView(R.id.odds_guest)
        TextView oddsGuest;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
