package io.paymenthighway.phroadshowdemo.Service;

import android.content.Intent;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyAppWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        Log.d("PHDEMO", url);
        if (url.startsWith("ph://")) {
            if (url.startsWith("ph://")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                view.getContext().startActivity(intent);
                return true;
            }
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, request.getUrl()));
            return true;
        } else {
            return false;
        }
    }
}
