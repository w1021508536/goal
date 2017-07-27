package com.small.small.goal.my.guess;

import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.small.small.goal.R;
import com.small.small.goal.utils.BaseActivity;

public class LotteryExplainActivity extends BaseActivity {

    private ImageView left_image;

    private WebView webView;

    private String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lottery_explain);
        super.onCreate(savedInstanceState);
        Url = getIntent().getStringExtra("url");

        init();
    }

    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);
        webView = (WebView) findViewById(R.id.web);


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
                handler.proceed(); // 接受所有网站的证书

            }
        });
        webView.loadUrl(Url);
        left_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
                finish();
                break;
        }
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
