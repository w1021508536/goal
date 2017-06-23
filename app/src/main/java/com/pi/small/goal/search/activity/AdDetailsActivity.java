package com.pi.small.goal.search.activity;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AdDetailsActivity extends BaseActivity {

    @InjectView(R.id.left_image)
    ImageView left_image;
    @InjectView(R.id.title_text)
    TextView title_text;
    @InjectView(R.id.web)
    WebView webView;

    private String Url;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ad_details);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        Url = getIntent().getStringExtra("url");
        title= getIntent().getExtras().getString("title","");
        init();
    }


    private void init() {
        title_text.setText(title);


        //设置可自由缩放网页
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(false);


        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();// 接受所有网站的证书

            }
        });
        webView.loadUrl(Url);
    }

    @OnClick(R.id.left_image)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.pauseTimers();
    }

}
