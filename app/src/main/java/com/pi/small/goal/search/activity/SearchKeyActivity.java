package com.pi.small.goal.search.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SearchKeyActivity extends BaseActivity {

    @InjectView(R.id.cancel_text)
    TextView cancelText;
    @InjectView(R.id.search_first_image)
    ImageView searchFirstImage;
    @InjectView(R.id.search_edit)
    EditText searchEdit;
    @InjectView(R.id.search_layout)
    RelativeLayout searchLayout;
    @InjectView(R.id.search_list)
    PullToRefreshListView searchList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_key);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!searchEdit.getText().toString().trim().equals("")) {
                        RequestParams requestParams = new RequestParams(Url.Url + Url.SearchAim);
                        requestParams.addHeader("token", Utils.GetToken(SearchKeyActivity.this));
                        requestParams.addHeader("deviceId", MyApplication.deviceId);
                        requestParams.addBodyParameter("keyword", searchEdit.getText().toString().trim());
                        requestParams.addBodyParameter("p", "1");
                        requestParams.addBodyParameter("r", "10");
                        XUtil.get(requestParams, SearchKeyActivity.this, new XUtil.XCallBackLinstener() {
                            @Override
                            public void onSuccess(String result) {

                                System.out.println("=====getaim=======" + result);
                                try {
                                    String code = new JSONObject(result).getString("code");
                                    if (code.equals("0")) {

                                    } else {
                                        Utils.showToast(SearchKeyActivity.this, new JSONObject(result).getString("msg"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                System.out.println("=====getaim=======" + ex.getMessage());
                                Utils.showToast(SearchKeyActivity.this, ex.getMessage());
                            }

                            @Override
                            public void onFinished() {

                            }
                        });
                    }


                }
                return false;
            }
        });
    }

    @OnClick(R.id.cancel_text)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_text:
                finish();
                break;
        }
    }
}
