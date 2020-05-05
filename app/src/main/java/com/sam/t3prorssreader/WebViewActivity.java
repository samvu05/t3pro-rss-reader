package com.sam.t3prorssreader;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);

        initWidget();
        getLinkAndLoadURL();
    }

    private void getLinkAndLoadURL() {
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient()); //Để khi người dùng click vào link trong webview thì vẫn mở trong webview

    }

    private void initWidget() {
        webView = findViewById(R.id.news_layout);
    }
}
