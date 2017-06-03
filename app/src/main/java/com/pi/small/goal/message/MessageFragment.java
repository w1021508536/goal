package com.pi.small.goal.message;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.message.activity.FriendsListActivity;
import com.pi.small.goal.message.activity.FriendsMessageListActivity;
import com.pi.small.goal.message.activity.SystemMessageListActivity;
import com.pi.small.goal.message.adapter.MessageListAdapter;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2017/5/25.
 */

public class MessageFragment extends ConversationListFragment implements View.OnClickListener {
    private ImageView right_image;
    private RelativeLayout system_layout;
    private TextView system_context_text;
    private TextView system_time_text;
    private ImageView system_image;
    private RelativeLayout friends_layout;
    private TextView friends_context_text;
    private TextView friends_time_text;
    private ImageView friends_image;
    private MessageListAdapter messageListAdapter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String token;


    private List<Conversation> LastMessage_List;
    private Conversation conversation;

    private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

    public MessageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = Utils.UserSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        token = sharedPreferences.getString("token", "");
        LastMessage_List = new ArrayList<Conversation>();

        right_image = (ImageView) view.findViewById(R.id.right_image);
        system_image = (ImageView) view.findViewById(R.id.system_image);
        friends_image = (ImageView) view.findViewById(R.id.friends_image);

        system_layout = (RelativeLayout) view.findViewById(R.id.system_layout);
        system_context_text = (TextView) view.findViewById(R.id.system_context_text);
        system_time_text = (TextView) view.findViewById(R.id.system_time_text);

        friends_layout = (RelativeLayout) view.findViewById(R.id.friends_layout);
        friends_context_text = (TextView) view.findViewById(R.id.friends_context_text);
        friends_time_text = (TextView) view.findViewById(R.id.friends_time_text);

        View topView = view.findViewById(R.id.view);
        if (topView == null)
            return;
        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion >= 19) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            topView.setVisibility(View.GONE);
        }

        init();
    }

    private void init() {
        right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_chat_set));

        system_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_message_system));
        friends_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_message_friend));

        right_image.setOnClickListener(this);
        system_layout.setOnClickListener(this);
        friends_layout.setOnClickListener(this);
        GetLastMessage();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.right_image:
                intent.setClass(getActivity(), FriendsListActivity.class);
                startActivity(intent);
                break;
            case R.id.friends_layout:
                intent.setClass(getActivity(), FriendsMessageListActivity.class);
                startActivity(intent);
                break;
            case R.id.system_layout:
                intent.setClass(getActivity(), SystemMessageListActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);


    }


    @Override
    public ConversationListAdapter onResolveAdapter(Context context) {
        messageListAdapter = new MessageListAdapter(context);
        return messageListAdapter;
    }


    private void GetLastMessage() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.LastMessage);
        requestParams.addHeader("token", token);
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        conversation = new Conversation();

                        JSONObject jsonObject_system = new JSONObject(result).getJSONObject("result").optJSONObject("system");
                        JSONObject jsonObject_contact = new JSONObject(result).getJSONObject("result").optJSONObject("contact");

                        if (jsonObject_contact != null) {

                            String content = jsonObject_contact.getString("content");
                            JSONObject jsonObject = new JSONObject(content);
                            friends_context_text.setText(jsonObject.getJSONObject("user").getString("nick") + "请求加你为好友");
                            friends_time_text.setText(simpleDateFormat1.format(new Date(Long.valueOf(jsonObject_contact.optString("createTime")))));

                        } else {
                            friends_context_text.setText("");
                            friends_time_text.setText("");
                        }

                        if (jsonObject_system != null) {
                            String content = jsonObject_system.getString("content");
                            JSONObject jsonObject = new JSONObject(content);

                            system_context_text.setText(jsonObject.optString("brief"));
                            system_time_text.setText(simpleDateFormat1.format(new Date(Long.valueOf(jsonObject_system.optString("createTime")))));
                        } else {
                            system_context_text.setText("");
                            system_time_text.setText("");
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
