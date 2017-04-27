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

import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import moe.yukinoneko.diycode.module.user.UserActivity;
import moe.yukinoneko.diycode.module.web.WebActivity;

/**
 * Created by SamuelGjk on 2017/3/27.
 */

public class ReplySpan extends ClickableSpan {

    private static final String TAG = "ReplySpan";

    private String url;

    public ReplySpan(String url) {
        this.url = url;
    }

    @Override
    public void onClick(View widget) {
        if (url.startsWith("/")) {
            // url: "/plusend"
            Log.d(TAG, "username: " + url.substring(1));
            UserActivity.launch(widget.getContext(), url.substring(1));
        } else if (url.startsWith("#")) {
            // url: "#reply1"
        } else {
            WebActivity.launch(widget.getContext(), url);
        }
    }
}
