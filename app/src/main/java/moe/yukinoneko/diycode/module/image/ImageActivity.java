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

package moe.yukinoneko.diycode.module.image;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.mvp.MVPBaseActivity;
import moe.yukinoneko.diycode.tool.ImageLoadHelper;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ImageActivity extends MVPBaseActivity<ImageContract.View, ImagePresenter> implements ImageContract.View {
    private static final String EXTRA_IMAGE_URL = "extra_image_url";

    @BindView(R.id.photo_view) PhotoView photoView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    public static void launch(Context context, String imageUrl) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(EXTRA_IMAGE_URL, imageUrl);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageLoadHelper.loadImage(this, getIntent().getStringExtra(EXTRA_IMAGE_URL), photoView, new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        });
    }
}
