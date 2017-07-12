package com.small.small.goal.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.entity.ContactBean;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/5/26.
 */

public class FriendsListAdapter extends BaseAdapter {
    private Context context;
    private List<ContactBean> dataList;

    private String[] sections;

    public FriendsListAdapter(Context context, List<ContactBean> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.sections = new String[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            sections[i] = dataList.get(i).getDesplayName();
        }
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param dataList
     */
    public void updateListView(List<ContactBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void remove(int position) {
        dataList.remove(position);
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return dataList.get(position).getSortLetters().charAt(0);
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        if (str.equals(" ")) {
            return " ";
        }
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = dataList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
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
        final ContactBean contact = dataList.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_friends, parent, false);

            viewHolder.name_text = (TextView) convertView.findViewById(R.id.name_text);
            viewHolder.head_image = (CircleImageView) convertView.findViewById(R.id.head_image);
            viewHolder.catalog = (TextView) convertView.findViewById(R.id.catalog);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name_text.setText(contact.getDesplayName());

        if (!contact.getAvatar().equals("")) {
            Picasso.with(context).load(Utils.GetPhotoPath(contact.getAvatar())).into(viewHolder.head_image);
        } else {
            viewHolder.head_image.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_head));
        }

        if (dataList.size() == 0) {
            viewHolder.catalog.setVisibility(View.VISIBLE);
            viewHolder.catalog.setText("没有联系人");
        }
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.catalog.setVisibility(View.VISIBLE);
            viewHolder.catalog.setText(contact.getSortLetters());
        } else {
            viewHolder.catalog.setVisibility(View.GONE);
        }


        return convertView;
    }


    private class ViewHolder {

        private TextView name_text;
        private CircleImageView head_image;
        TextView catalog;

    }
}