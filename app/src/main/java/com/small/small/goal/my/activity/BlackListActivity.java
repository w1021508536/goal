package com.small.small.goal.my.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.message.activity.AddFriendActivity;
import com.small.small.goal.search.activity.UserDetitalActivity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CharacterParser;
import com.small.small.goal.utils.FirstLetterUtil;
import com.small.small.goal.utils.PinyinComparator;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.entity.ContactBean;
import com.squareup.picasso.Picasso;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imlib.model.UserInfo;

public class BlackListActivity extends BaseActivity {


    private ImageView left_image;
    private ImageView right_image;

    private RelativeLayout null_layout;
    private ListView friends_list;

    private EditText search_edit;
    private LinearLayout search_layout;


    private List<ContactBean> friendList;

    private BlackListAdapter blackListAdapter;

    private boolean isFriendsList = true;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    private UserInfo userInfo;

    public static final String KEY_TYPE = "type";
    public static final int TYPE_OTHER = 1;
    public static final int TYPE_TRANSFER = 2;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_black_list);
        super.onCreate(savedInstanceState);


        sharedPreferences = Utils.UtilsSharedPreferences(this);
        editor = sharedPreferences.edit();

        friendList = new ArrayList<ContactBean>();
        blackListAdapter = new BlackListAdapter(this);

        pinyinComparator = new PinyinComparator();
        characterParser = CharacterParser.getInstance();
        init();
    }

    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);
        right_image = (ImageView) findViewById(R.id.right_image);
        null_layout= (RelativeLayout) findViewById(R.id.null_layout);
        search_layout = (LinearLayout) findViewById(R.id.search_layout);
        friends_list = (ListView) findViewById(R.id.friends_list);
        search_edit = (EditText) findViewById(R.id.search_edit);

        type = getIntent().getIntExtra(KEY_TYPE, TYPE_OTHER);

        friends_list.setAdapter(blackListAdapter);

        left_image.setOnClickListener(this);
        right_image.setOnClickListener(this);

        search_edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        friends_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                if (type == TYPE_TRANSFER) {

                    ContactBean contactBean = friendList.get(position);
                    Intent intent = new Intent();
                    intent.putExtra(KEY_TYPE, contactBean);
                    setResult(TYPE_TRANSFER, intent);
                    finish();
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(BlackListActivity.this, UserDetitalActivity.class);
                intent.putExtra("userId", friendList.get(position).getFriendId());
                startActivity(intent);

            }
        });

        GetFriendsListData();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.right_image:
                Intent intent = new Intent();
                intent.setClass(this, AddFriendActivity.class);
                startActivity(intent);
                break;
        }

    }


    private void GetFriendsListData() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.FriendList);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {


                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

                        editor.putString("friendsList", result);
                        editor.commit();

                        List(result);

                    } else {
                        Utils.showToast(BlackListActivity.this, new JSONObject(result).getString("msg"));
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

    private void List(String result) {

        ContactBean contactBean;
        try {

            JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).optString("isBlack").equals("1")) {
                    contactBean = new ContactBean();
                    contactBean.setAvatar(jsonArray.getJSONObject(i).optString("avatar"));
                    contactBean.setId(jsonArray.getJSONObject(i).optString("id"));
                    contactBean.setUserId(jsonArray.getJSONObject(i).optString("userId"));
                    contactBean.setFriendId(jsonArray.getJSONObject(i).optString("friendId"));
                    contactBean.setRemark(jsonArray.getJSONObject(i).optString("remark"));

                    contactBean.setCreateTime(jsonArray.getJSONObject(i).optString("createTime"));
                    contactBean.setNick(jsonArray.getJSONObject(i).optString("nick"));
                    contactBean.setBrief(jsonArray.getJSONObject(i).optString("brief"));
                    contactBean.setIsBlack(jsonArray.getJSONObject(i).optString("isBlack"));
                    contactBean.setContactId(i);
                    if (!jsonArray.getJSONObject(i).optString("remark").equals("")) {
                        contactBean.setDesplayName(jsonArray.getJSONObject(i).optString("remark"));
                    } else {
                        contactBean.setDesplayName(jsonArray.getJSONObject(i).optString("nick"));
                    }

                    contactBean.setSortLetters(SortString(contactBean.getDesplayName()));

                    friendList.add(contactBean);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        // 根据a-z进行排序源数据
//        Collections.sort(friendList, pinyinComparator);
//
//        friendsListAdapter.updateListView(friendList);
//        friends_list.setAdapter(friendsListAdapter);
        setAdapter(friendList);

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<ContactBean> filterDateList = new ArrayList<ContactBean>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = friendList;
        } else {
            filterDateList.clear();
            for (ContactBean contactBean : friendList) {

                String name = contactBean.getDesplayName();


                String str = filterStr.toString();
                char[] ch = str.toCharArray();
                // 将大写字母转化为小写字母，实现大小写检索查询
                for (int i = 0; i < ch.length; i++) {
                    if ((int) ch[i] >= 65 && (int) ch[i] <= 90) {
                        ch[i] += 32;
                    }
                }
                str = new String(ch);
                // 获取搜索条件的首字母，实现首字母检索查询
                String fristName = FirstLetterUtil.getFirstLetter(name);
                name.compareToIgnoreCase(characterParser.getSelling(name));
                if (name.indexOf(str) != -1 || fristName.startsWith(str)
                        || characterParser.getSelling(name).startsWith(str)
                    // 匹配手机号查询，实现手机号和姓名的双向查询
                        ) {
                    filterDateList.add(contactBean);

                }
            }
        }

        setAdapter(filterDateList);

    }

    private void setAdapter(List<ContactBean> list) {


        // 根据a-z进行排序源数据
        Collections.sort(list, pinyinComparator);

        blackListAdapter.updateListView(list);
        friends_list.setAdapter(blackListAdapter);

    }

    /**
     * 为ListView填充数据
     *
     * @param
     * @return
     */
    private String SortString(String name) {

        String pinyin = getPingYin(name);
        String sortString = pinyin.substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            return sortString;
        } else {
            return "#";
        }
    }

    /**
     * 将字符串中的中文转化为拼音,英文字符不变
     *
     * @param inputString 汉字
     * @return
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String output = "";
        if (inputString != null && inputString.length() > 0
                && !"null".equals(inputString)) {
            char[] input = inputString.trim().toCharArray();
            try {
                for (int i = 0; i < input.length; i++) {
                    if (java.lang.Character.toString(input[i]).matches(
                            "[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(
                                input[i], format);
                        output += temp[0];
                    } else
                        output += java.lang.Character.toString(input[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            return "*";
        }
        return output;
    }

    public class BlackListAdapter extends BaseAdapter {
        private Context context;

        private String[] sections;

        public BlackListAdapter(Context context) {
            this.context = context;
            this.sections = new String[friendList.size()];
            for (int i = 0; i < friendList.size(); i++) {
                sections[i] = friendList.get(i).getDesplayName();
            }
        }

        /**
         * 当ListView数据发生变化时,调用此方法来更新ListView
         *
         * @param dataList
         */
        public void updateListView(List<ContactBean> dataList) {
            notifyDataSetChanged();
        }

        public void remove(int position) {
            friendList.remove(position);
        }

        /**
         * 根据ListView的当前位置获取分类的首字母的Char ascii值
         */
        public int getSectionForPosition(int position) {
            return friendList.get(position).getSortLetters().charAt(0);
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
                String sortStr = friendList.get(i).getSortLetters();
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }

            return -1;
        }

        @Override
        public int getCount() {
            if (friendList.size()==0){
                null_layout.setVisibility(View.VISIBLE);
            }else {
                null_layout.setVisibility(View.GONE);
            }
            return friendList.size();
        }

        @Override
        public Object getItem(int position) {
            return friendList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            final ContactBean contact = friendList.get(position);
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list_black, parent, false);
                viewHolder.remove_text = (TextView) convertView.findViewById(R.id.remove_text);
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
                viewHolder.head_image.setImageDrawable(context.getDrawable(R.mipmap.icon_head));
            }

            if (friendList.size() == 0) {
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

            viewHolder.remove_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams requestParams = new RequestParams(Url.Url + Url.FriendBlackDelete);
                    requestParams.addHeader("token", Utils.GetToken(context));
                    requestParams.addHeader("deviceId", MyApplication.deviceId);
                    requestParams.addBodyParameter("friendId", contact.getFriendId());
                    XUtil.post(requestParams, context, new XUtil.XCallBackLinstener() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                String code = new JSONObject(result).getString("code");
                                if (code.equals("0")) {
                                    Utils.showToast(context, "移除成功");
                                    friendList.remove(position);
                                    notifyDataSetChanged();
                                } else {
                                    Utils.showToast(context, new JSONObject(result).getString("msg"));
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

            return convertView;
        }


        private class ViewHolder {
            private TextView remove_text;
            private TextView name_text;
            private CircleImageView head_image;
            TextView catalog;
        }
    }


}
