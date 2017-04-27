/*
 * Copyright (c) 2017 SamuelGjk <samuel.alva@outlook.com>
 *
 * This file is part of DiyCode
 *
 * DiyCode is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DiyCode is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DiyCode. If not, see <http://www.gnu.org/licenses/>.
 */

package moe.yukinoneko.diycode.module.web;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.daimajia.numberprogressbar.NumberProgressBar;

import butterknife.BindView;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WebActivity extends MVPBaseActivity<WebContract.View, WebPresenter> implements WebContract.View {

    private static final String EXTRA_URL = "extra_url";

    @BindView(R.id.web_view) WebView webView;
    @BindView(R.id.progressbar) NumberProgressBar progressbar;

    public static void launch(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebClient());
        webView.setWebChromeClient(new ChromeClient());

        webView.loadUrl(getIntent().getStringExtra(EXTRA_URL));
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(false);
        webView.destroy();

        super.onDestroy();
    }

    private static class WebClient extends WebViewClient {

        @SuppressWarnings("deprecation")
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) {
                view.loadUrl(url);
            }
            return true;
        }
    }

    private class ChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressbar.setProgress(newProgress);
            if (newProgress == 100) {
                progressbar.setVisibility(View.INVISIBLE);
            } else {
                progressbar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setToolBarTitle(title);
        }
    }
}
