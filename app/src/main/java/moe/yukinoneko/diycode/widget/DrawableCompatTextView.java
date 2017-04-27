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

package moe.yukinoneko.diycode.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import moe.yukinoneko.diycode.R;

/**
 * Created by SamuelGjk on 2017/3/30.
 * <p>
 * See http://stackoverflow.com/questions/35761636/is-it-possible-to-use-vectordrawable-in-buttons-and-textviews-using-androiddraw
 */

public class DrawableCompatTextView extends AppCompatTextView {

    public DrawableCompatTextView(Context context) {
        this(context, null);
    }

    public DrawableCompatTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public DrawableCompatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawableCompatTextView);

            Drawable drawableLeft = null;
            Drawable drawableRight = null;
            Drawable drawableBottom = null;
            Drawable drawableTop = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawableLeft = a.getDrawable(R.styleable.DrawableCompatTextView_drawableLeftCompat);
                drawableRight = a.getDrawable(R.styleable.DrawableCompatTextView_drawableRightCompat);
                drawableBottom = a.getDrawable(R.styleable.DrawableCompatTextView_drawableBottomCompat);
                drawableTop = a.getDrawable(R.styleable.DrawableCompatTextView_drawableTopCompat);
            } else {
                final int drawableLeftId = a.getResourceId(R.styleable.DrawableCompatTextView_drawableLeftCompat, -1);
                final int drawableRightId = a.getResourceId(R.styleable.DrawableCompatTextView_drawableRightCompat, -1);
                final int drawableBottomId = a.getResourceId(R.styleable.DrawableCompatTextView_drawableBottomCompat, -1);
                final int drawableTopId = a.getResourceId(R.styleable.DrawableCompatTextView_drawableTopCompat, -1);

                if (drawableLeftId != -1)
                    drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId);
                if (drawableRightId != -1)
                    drawableRight = AppCompatResources.getDrawable(context, drawableRightId);
                if (drawableBottomId != -1)
                    drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId);
                if (drawableTopId != -1)
                    drawableTop = AppCompatResources.getDrawable(context, drawableTopId);
            }
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);

            a.recycle();
        }
    }
}
