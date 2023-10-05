package com.example.bai2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

public class BrowserActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        // Lấy tham chiếu đến WebView
        webView = findViewById(R.id.webView1);

        // Lấy URL từ Intent (như đã thảo luận trong trước đó)
        String url = getIntent().getStringExtra("url");

        // Kiểm tra xem URL có tồn tại không
        if (url != null && !url.isEmpty()) {
            // Kích hoạt JavaScript trong WebView (tuỳ chọn)
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            // Thiết lập WebViewClient để không mở URL bằng trình duyệt bên ngoài
            webView.setWebViewClient(new WebViewClient());

            // Tải URL trong WebView
            webView.loadUrl(url);
        }
    }
}