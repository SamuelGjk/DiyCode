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

package moe.yukinoneko.diycode.tool;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;

import moe.yukinoneko.diycode.R;

/**
 * Created by SamuelGjk on 2017/4/25.
 */

public class ImageLoadHelper {

    public static void loadImage(Context context, String url, ImageView imageView, @Nullable RequestListener<String, GlideDrawable> listener) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(listener).into(imageView);
    }

    public static void loadAvatar(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.mipmap.default_avatar).dontAnimate().into(imageView);
    }
}
