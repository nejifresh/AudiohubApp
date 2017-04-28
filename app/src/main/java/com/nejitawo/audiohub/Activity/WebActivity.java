package com.nejitawo.audiohub.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.nejitawo.audiohub.R;

/**
 * Created by Neji on 17/05/2015.
 */
public class WebActivity extends Activity {
    private WebView webView;
    private String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Intent me = getIntent();
        url = me.getStringExtra("data");
   //   url =   "http://audiohub.mymegalibrary.com/members/";


        webView = (WebView)findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //Toast.makeText(DaWeb.this, description, Toast.LENGTH_SHORT).show();
            }
        });

        webView.loadUrl(url);

    }
}
