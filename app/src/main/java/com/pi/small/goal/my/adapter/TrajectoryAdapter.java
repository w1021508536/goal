package com.pi.small.goal.my.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.my.entry.DynamicEntity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.weight.PinchImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/5 15:45
 * 修改：
 * 描述： 轨迹的适配器
 **/
public class TrajectoryAdapter extends BaseAdapter {


    private final Context context;
    private boolean operationShowFlag;
    private List<DynamicEntity> data;
    private myAdapterClickListener listener;


    private ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setLoadingDrawableId(R.mipmap.background_load)
            .setFailureDrawableId(R.mipmap.background_fail)

            .build();

    public TrajectoryAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();

    }

    public List<DynamicEntity> getData() {
        return data;
    }


    //    dynamicId = getIntent().getStringExtra("dynamicId");
//    aimId = getIntent().getStringExtra("aimId");
//    nick = getIntent().getStringExtra("nick");
//    avatar = getIntent().getStringExtra("avatar");
    public interface myAdapterClickListener {
        void great(String id);  //点赞

        void comment(String id, int position); //评论

        void help(String id, String nick, String avatar);    //助力
    }

    public void setOnMyAdapterClickListener(myAdapterClickListener listener) {
        this.listener = listener;
    }


    /**
     * 是否显示下面的点赞 评论 主力的图标  自己看自己的不显示
     * create  wjz
     **/

    public void setOperationShowFlag(boolean operationShowFlag) {
        this.operationShowFlag = operationShowFlag;
        notifyDataSetChanged();
    }

    public void setData(List<DynamicEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<DynamicEntity> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_trajectory, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
            if (!operationShowFlag) {
                vh.rlOperation.setVisibility(View.GONE);
            }
            Drawable drawable = context.getResources().getDrawable(R.mipmap.local_icon);
            drawable.setBounds(0, 0, Utils.dip2px(context, 11), Utils.dip2px(context, 13));
            vh.tvCityItem.setCompoundDrawables(drawable, null, null, null);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final DynamicEntity dynamicEntity = data.get(position);

        String timeStr = Utils.GetTime2(dynamicEntity.getDynamic().getUpdateTime());
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(timeStr);
        String time = m.replaceAll("").trim();
        vh.tvTimeItem.setText(time);
        vh.tvTimeDayItem.setText(timeStr.replace(time, ""));
        if (dynamicEntity.getDynamic().getContent().equals("")) {
            vh.tvTitleItem.setVisibility(View.GONE);
        } else {
            vh.tvTitleItem.setVisibility(View.VISIBLE);
        }
        vh.tvTitleItem.setText(dynamicEntity.getDynamic().getContent());
        vh.tvAddMoneyItem.setText(dynamicEntity.getDynamic().getMoney() + "");
        vh.tvSupportsItem.setText(dynamicEntity.getVotes() + "赞" + "  " + dynamicEntity.getSupports() + "助力");
        vh.imgItem.setImageResource(R.drawable.image2);
        vh.tvCityItem.setText(dynamicEntity.getDynamic().getProvince() + " " + dynamicEntity.getDynamic().getCity());

        if (position == data.size() - 1) {
            vh.imgFlag.setImageResource(R.mipmap.icon_start_dream);
            if (dynamicEntity.getDynamic().getMoney() == 0) {
                vh.rlBottom.setVisibility(View.GONE);
                vh.rlOperation.setVisibility(View.GONE);
            }
        } else {
            vh.imgFlag.setImageResource(R.mipmap.timeline_all_icon);
        }

        if (data.get(position).getHaveVote() == 1) {
            vh.imgGreat.setImageResource(R.mipmap.icon_vote_on);
        } else {
            vh.imgGreat.setImageResource(R.mipmap.icon_vote_off);
        }
//1496736200354.jpg  1495527714097.jpg   1496737409748.jpg

//        if (Utils.photoEmpty(dynamicEntity.getDynamic().getImg1())) { //1496802195021.jpg
//            Picasso.with(context).load(Utils.GetPhotoPath(dynamicEntity.getDynamic().getImg1())).into(vh.imgItem);
//        }
//        if (Utils.photoEmpty(dynamicEntity.getDynamic().getImg2())) { //1496802195021.jpg
//            Picasso.with(context).load(Utils.GetPhotoPath(dynamicEntity.getDynamic().getImg2())).into(vh.img2Item);
//        }
//        if (Utils.photoEmpty(dynamicEntity.getDynamic().getImg3())) { //1496802195021.jpg
//            Picasso.with(context).load(Utils.GetPhotoPath(dynamicEntity.getDynamic().getImg3())).into(vh.img3Item);
//        }
        String addSmallPhoto = Url.SMALL_PHOTO_URL;
        if ("".equals(dynamicEntity.getDynamic().getImg2())) {
            addSmallPhoto = Url.SMALL_PHOTO_URL2;
        }
        x.image().bind(vh.imgItem, Utils.GetPhotoPath(dynamicEntity.getDynamic().getImg1()) + addSmallPhoto, imageOptions);
        x.image().bind(vh.img2Item, Utils.GetPhotoPath(dynamicEntity.getDynamic().getImg2()) + addSmallPhoto, imageOptions);
        x.image().bind(vh.img3Item, Utils.GetPhotoPath(dynamicEntity.getDynamic().getImg3()) + addSmallPhoto, imageOptions);


        if (dynamicEntity.getDynamic().getImg1().length() == 0) {
            vh.imgItem.setVisibility(View.GONE);
        } else {
            vh.imgItem.setVisibility(View.VISIBLE);
        }
        if (dynamicEntity.getDynamic().getImg2().length() == 0) {
            vh.img2Item.setVisibility(View.GONE);
        } else {
            vh.img2Item.setVisibility(View.VISIBLE);
        }
        if (dynamicEntity.getDynamic().getImg3().length() == 0) {
            vh.img3Item.setVisibility(View.GONE);
        } else {
            vh.img3Item.setVisibility(View.VISIBLE);
        }

        if (position == 0) {
            vh.viewLineTop.setVisibility(View.INVISIBLE);
        }
        List<DynamicEntity.CommentsBean> comments = dynamicEntity.getComments();
        final CommentAdapter adapter = new CommentAdapter(context, comments);
        vh.lvItem.setAdapter(adapter);
        Utils.setListViewHeightBasedOnChildren(vh.lvItem);

//        if (comments.size() > 1) {
//            vh.tvUserSupport1Item.setText(comments.get(0).getNick() + ":");
//            vh.tvContentSupport1Item.setText(comments.get(0).getContent());
//            vh.llSuppor1Item.setVisibility(View.VISIBLE);
//        }
        if (comments.size() > 2) {
            vh.tvSupportsNumsItem.setText("查看全部" + comments.size() + "条评论");
//            vh.tvUserSupport2Item.setText(comments.get(1).getNick() + ": ");
//            vh.tvContentSupport2Item.setText(comments.get(1).getContent());
//            vh.llSupport2Item.setVisibility(View.VISIBLE);
            vh.tvSupportsNumsItem.setVisibility(View.VISIBLE);
        }

        vh.imgItem.setOnClickListener(new myClick(data, position, vh));
        vh.img2Item.setOnClickListener(new myClick(data, position, vh));
        vh.img3Item.setOnClickListener(new myClick(data, position, vh));

        vh.tvSupportsNumsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setShowMore(true);
                Utils.setListViewHeightBasedOnChildren(vh.lvItem);
                vh.tvSupportsNumsItem.setVisibility(View.GONE);
            }
        });
        vh.imgMore.setOnClickListener(new myClick(data, position, vh));
        vh.imgCommit.setOnClickListener(new myClick(data, position, vh));
        vh.imgGreat.setOnClickListener(new myClick(data, position, vh));
        vh.imgHelp.setOnClickListener(new myClick(data, position, vh));

        return convertView;
    }

    class myClick implements View.OnClickListener {

        private final int position;
        private final ViewHolder viewHolder;
        private List<DynamicEntity> data;

        public myClick(List<DynamicEntity> data, int position, ViewHolder viewHolder) {
            this.data = data;
            this.viewHolder = viewHolder;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_great:
                    if (listener != null) {
                        listener.great(data.get(position).getDynamic().getId() + "");
                        if (data.get(position).getHaveVote() == 0) {
                            data.get(position).setHaveVote(1);
                            viewHolder.imgGreat.setImageResource(R.mipmap.icon_vote_on);
                            data.get(position).setVotes(data.get(position).getVotes() + 1);
                            notifyDataSetChanged();
                        } else {
                            data.get(position).setHaveVote(0);
                            viewHolder.imgGreat.setImageResource(R.mipmap.icon_vote_off);
                            data.get(position).setVotes(data.get(position).getVotes() - 1);
                            notifyDataSetChanged();
                        }
                    }
                    break;
                case R.id.img_commit:
                    if (listener != null) {
                        listener.comment(data.get(position).getDynamic().getId() + "", position);

                    }
                    break;
                case R.id.img_help:
                    if (listener != null) {
                        listener.help(data.get(position).getDynamic().getId() + "", data.get(position).getDynamic().getNick(), data.get(position).getDynamic().getAvatar());
                    }
                    break;
                case R.id.img_more:
                    report(data.get(position).getDynamic().getId() + "", v);
                    break;
                case R.id.img_item:
                case R.id.img2_item:
                case R.id.img3_item:
                    RelativeLayout rl_image = (RelativeLayout) ((Activity) context).findViewById(R.id.rl_image);
                    String s;
                    if (v.getId() == R.id.img_item) {
                        s = Utils.GetPhotoPath(data.get(position).getDynamic().getImg1());
                    } else if (v.getId() == R.id.img2_item) {
                        s = Utils.GetPhotoPath(data.get(position).getDynamic().getImg2());
                    } else {
                        s = Utils.GetPhotoPath(data.get(position).getDynamic().getImg3());
                    }
                    PinchImageView pimg = (PinchImageView) rl_image.getChildAt(0);

                    if (Utils.photoEmpty(s)) {
                        Picasso.with(context).load(Utils.GetPhotoPath(s)).into(pimg);
                    } else {
                        pimg.setImageResource(R.drawable.image2);
                    }

                    rl_image.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private void report(final String id, View v) {
        View windowView = LayoutInflater.from(context).inflate(
                R.layout.window_search_report, null);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        final TextView report_text = (TextView) windowView.findViewById(R.id.report_text);
        TextView cancel_text = (TextView) windowView.findViewById(R.id.cancel_text);

        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        report_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_text.setClickable(false);

                RequestParams requestParams = Utils.getRequestParams(context);
                requestParams.setUri(Url.Url + Url.ReportUser);
                requestParams.addBodyParameter("uid", id + "");
                XUtil.put(requestParams, context, new XUtil.XCallBackLinstener() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            String code = new JSONObject(result).getString("code");
                            if (code.equals("0")) {
                                Utils.showToast(context, "举报成功");
                                report_text.setClickable(true);
                                popupWindow.dismiss();
                            } else {
                                Utils.showToast(context, new JSONObject(result).getString("msg"));
                                report_text.setClickable(true);
                                popupWindow.dismiss();
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
        });
        popupWindow.setAnimationStyle(R.style.MyDialogStyle);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_empty));
        // 设置好参数之后再show
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }


    static class ViewHolder {
        @InjectView(R.id.view_line_top)
        View viewLineTop;
        @InjectView(R.id.img_flag)
        ImageView imgFlag;
        @InjectView(R.id.tv_time_item)
        TextView tvTimeItem;
        @InjectView(R.id.tv_timeDay_item)
        TextView tvTimeDayItem;
        @InjectView(R.id.tv_title_item)
        TextView tvTitleItem;
        @InjectView(R.id.img_item)
        ImageView imgItem;
        @InjectView(R.id.img2_item)
        ImageView img2Item;
        @InjectView(R.id.img3_item)
        ImageView img3Item;
        @InjectView(R.id.ll_imgs)
        LinearLayout llImgs;
        @InjectView(R.id.tv_addMoney_item)
        TextView tvAddMoneyItem;
        @InjectView(R.id.rl_money_item)
        LinearLayout rlMoneyItem;
        @InjectView(R.id.img_great)
        ImageView imgGreat;
        @InjectView(R.id.img_commit)
        ImageView imgCommit;
        @InjectView(R.id.img_help)
        ImageView imgHelp;
        @InjectView(R.id.img_more)
        ImageView imgMore;
        @InjectView(R.id.rl_operation)
        RelativeLayout rlOperation;
        @InjectView(R.id.tv_supports_item)
        TextView tvSupportsItem;
        @InjectView(R.id.tv_city_item)
        TextView tvCityItem;
        @InjectView(R.id.lv_item)
        ListView lvItem;
        @InjectView(R.id.tv_supportsNums_item)
        TextView tvSupportsNumsItem;
        @InjectView(R.id.rl_bottom)
        RelativeLayout rlBottom;
        @InjectView(R.id.rl_operation_item)
        LinearLayout rlOperationItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
