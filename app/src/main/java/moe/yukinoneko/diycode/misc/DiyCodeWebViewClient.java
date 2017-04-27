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

package moe.yukinoneko.diycode.misc;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static android.text.TextUtils.isEmpty;
import static moe.yukinoneko.diycode.tool.UrlHelper.getMimeType;
import static moe.yukinoneko.diycode.tool.UrlHelper.isGifSuffix;
import static moe.yukinoneko.diycode.tool.UrlHelper.isImageSuffix;

/**
 * Created by SamuelGjk on 2017/4/24.
 */

public class DiyCodeWebViewClient extends WebViewClient {

    private Context context;
    private ACache aCache;

    public DiyCodeWebViewClient(Context context) {
        this.context = context;
        aCache = ACache.get(context);
    }

    // 监听加载了哪些资源
    @Override
    public void onLoadResource(WebView view, final String url) {
        // 是图片就缓存
        if (isImageSuffix(url) || isGifSuffix(url)) {
            Glide.with(context)
                 .load(url)
                 .downloadOnly(new SimpleTarget<File>() {
                     @Override
                     public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                         aCache.put(url, resource.getAbsolutePath());
                     }
                 });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        WebResourceResponse response = getWebResourceResponse(url);
        if (response != null) return response;
        return super.shouldInterceptRequest(view, request);
    }

    @SuppressWarnings("deprecation")
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        WebResourceResponse response = getWebResourceResponse(url);
        if (response != null) return response;
        return super.shouldInterceptRequest(view, url);
    }

    /**
     * 获取本地资源
     */
    @Nullable
    private WebResourceResponse getWebResourceResponse(String url) {
        try {
            // 如果是图片且本地有缓存
            if (isImageSuffix(url) || isGifSuffix(url)) {
                String cachePath = aCache.getAsString(url);
                if (!isEmpty(cachePath)) {
                    File cacheFile = new File(cachePath);
                    if (cacheFile.exists()) {
                        return new WebResourceResponse(getMimeType(url), "base64", new FileInputStream(cacheFile));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
