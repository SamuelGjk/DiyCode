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
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.HashSet;
import java.util.Set;

import moe.yukinoneko.diycode.R;

/**
 * Implements {@link android.text.Html.ImageGetter}
 * in order to show images in the TextView.
 * <p>
 * Uses {@link com.bumptech.glide.request.target.ViewTarget}
 * to make an asynchronous HTTP GET to load the image.
 * <p>
 * Forked from https://github.com/goofyz/testGlide/pull/1
 * See https://github.com/bumptech/glide/issues/550
 */
public final class GlideImageGetter implements Html.ImageGetter, View.OnAttachStateChangeListener, Drawable.Callback {

    private final Context mContext;

    private final TextView mTextView;

    private final Set<ImageGetterViewTarget> mTargets;

    public GlideImageGetter(Context context, TextView textView) {
        this.mContext = context;
        this.mTextView = textView;

        mTargets = new HashSet<>();

        // save Drawable.Callback in TextView
        // and get back when finish fetching image
        // see https://github.com/goofyz/testGlide/pull/1 for more details
        mTextView.setTag(R.id.tag_drawable_callback, this);
        // add this listener in order to clean any pending images loading
        // and set drawable callback tag to null when detached from window
        mTextView.addOnAttachStateChangeListener(this);
    }

    /**
     * We display image depends on settings and Wi-Fi status,
     * but display emoticons at any time.
     */
    @Override
    public Drawable getDrawable(String url) {
        final UrlDrawable urlDrawable = new UrlDrawable();

        ImageGetterViewTarget target = new ImageGetterViewTarget(mTextView, urlDrawable);

        Glide.with(mContext)
             .load(url)
             .diskCacheStrategy(DiskCacheStrategy.ALL)
             .into(target);

        mTargets.add(target);

        return urlDrawable;
    }

    @Override
    public void onViewAttachedToWindow(View v) {
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        // cancels any pending images loading
        for (ImageGetterViewTarget target : mTargets) {
            Glide.clear(target);
        }
        mTargets.clear();
        v.removeOnAttachStateChangeListener(this);

        v.setTag(R.id.tag_drawable_callback, null);
    }

    /**
     * Implements {@link Drawable.Callback} in order to
     * redraw the TextView which contains the animated GIFs.
     */
    @Override
    public void invalidateDrawable(@NonNull Drawable who) {
        mTextView.invalidate();
    }

    @Override
    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
    }

    @Override
    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
    }

    private static final class ImageGetterViewTarget extends ViewTarget<TextView, GlideDrawable> {

        private final UrlDrawable mDrawable;

        private Request mRequest;

        private ImageGetterViewTarget(TextView view, UrlDrawable drawable) {
            super(view);

            this.mDrawable = drawable;
        }

        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            // resize this drawable's width & height to fit its container
            final int resWidth = resource.getIntrinsicWidth();
            final int resHeight = resource.getIntrinsicHeight();
            int width, height;
            TextView textView = getView();
            if (textView.getWidth() >= resWidth) {
                width = resWidth;
                height = resHeight;
            } else {
                width = textView.getWidth();
                height = (int) (resHeight / ((float) resWidth / width));
            }

            Rect rect = new Rect(0, 0, width, height);
            resource.setBounds(rect);
            mDrawable.setBounds(rect);
            mDrawable.setDrawable(resource);

            if (resource.isAnimated()) {
                Drawable.Callback callback = (Drawable.Callback) textView.getTag(R.id.tag_drawable_callback);
                // note: not sure whether callback would be null sometimes
                // when this Drawable' host view is detached from View
                if (callback != null) {
                    // set callback to drawable in order to
                    // signal its container to be redrawn
                    // to show the animated GIF
                    mDrawable.setCallback(callback);
                    resource.setLoopCount(GlideDrawable.LOOP_FOREVER);
                    resource.start();
                }
            } else {
                textView.setTag(R.id.tag_drawable_callback, null);
            }

            // see http://stackoverflow.com/questions/7870312/android-imagegetter-images-overlapping-text#comment-22289166
            textView.setText(textView.getText());
        }

        /**
         * See https://github.com/bumptech/glide/issues/550#issuecomment-123693051
         *
         * @see com.bumptech.glide.GenericRequestBuilder#into(com.bumptech.glide.request.target.Target)
         */
        @Override
        public Request getRequest() {
            return mRequest;
        }

        @Override
        public void setRequest(Request request) {
            this.mRequest = request;
        }
    }
}
