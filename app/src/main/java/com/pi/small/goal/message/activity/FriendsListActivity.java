package com.pi.small.goal.message.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.message.adapter.FriendsListAdapter;
import com.pi.small.goal.search.activity.UserDetitalActivity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.CharacterParser;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.utils.entity.ContactBean;
import com.pi.small.goal.utils.FirstLetterUtil;
import com.pi.small.goal.utils.PinyinComparator;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class FriendsListActivity extends BaseActivity {


    private ImageView left_image;
    private ImageView right_image;
    private RelativeLayout nullLayout;
    private ImageView imgEmpty;
    private TextView tvEmpty;
    private ListView friends_list;


    private EditText search_edit;
    private LinearLayout search_layout;


    private List<ContactBean> friendList;

    private FriendsListAdapter friendsListAdapter;

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
        setContentView(R.layout.activity_friends_list);
        super.onCreate(savedInstanceState);


        sharedPreferences = Utils.UtilsSharedPreferences(this);
        editor = sharedPreferences.edit();

        friendList = new ArrayList<ContactBean>();
        friendsListAdapter = new FriendsListAdapter(this, friendList);

        pinyinComparator = new PinyinComparator();
        characterParser = CharacterParser.getInstance();
        init();
    }

    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);
        right_image = (ImageView) findViewById(R.id.right_image);
        nullLayout = (RelativeLayout) findViewById(R.id.null_layout);
        imgEmpty = (ImageView) findViewById(R.id.img_empty);
        tvEmpty = (TextView) findViewById(R.id.tv_empty);
        search_layout = (LinearLayout) findViewById(R.id.search_layout);
        friends_list = (ListView) findViewById(R.id.friends_list);
        search_edit = (EditText) findViewById(R.id.search_edit);

        type = getIntent().getIntExtra(KEY_TYPE, TYPE_OTHER);

        friends_list.setAdapter(friendsListAdapter);

        left_image.setOnClickListener(this);
        right_image.setOnClickListener(this);
        nullLayout.setOnClickListener(this);

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
                intent.setClass(FriendsListActivity.this, UserDetitalActivity.class);
                intent.putExtra("userId", friendList.get(position).getFriendId());
                startActivity(intent);

            }
        });


        if (Utils.isNetworkConnected(FriendsListActivity.this)) {
            nullLayout.setClickable(false);
            nullLayout.setVisibility(View.GONE);
            GetFriendsListData();
        } else {
            nullLayout.setClickable(true);
            nullLayout.setVisibility(View.VISIBLE);
            imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
            tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
        }

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
            case R.id.null_layout:
                if (Utils.isNetworkConnected(FriendsListActivity.this)) {
                    nullLayout.setClickable(false);
                    nullLayout.setVisibility(View.GONE);
                    GetFriendsListData();
                } else {
                    Utils.showToast(FriendsListActivity.this, "请检查网络是否连接");
                    nullLayout.setClickable(true);
                    nullLayout.setVisibility(View.VISIBLE);
                    imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                    tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
                }
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

                System.out.println("=======GetFriendsListData======" + result);

                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

                        editor.putString("friendsList", result);
                        editor.commit();

                        List(result);

                    } else if (code.equals("100000")) {
                        nullLayout.setClickable(false);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_null_data);
                        tvEmpty.setText("暂 时 没 有 好 友");
                    } else {
//                        Utils.showToast(SearchKeyActivity.this, new JSONObject(result).getString("msg"));
                        nullLayout.setClickable(true);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_wrong);
                        tvEmpty.setText("出 错! 点 击 重 新 尝 试");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_wrong);
                tvEmpty.setText("出 错! 点 击 重 新 尝 试");
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

            if (friendList.size() > 0) {
                nullLayout.setVisibility(View.GONE);
            } else {
                nullLayout.setVisibility(View.VISIBLE);
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

        friendsListAdapter.updateListView(list);
        friends_list.setAdapter(friendsListAdapter);

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

}
