/*
 * Copyright (C) 2017 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of rebase-android
 *
 * rebase-android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * rebase-android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with rebase-android. If not, see <http://www.gnu.org/licenses/>.
 */

package moe.yukinoneko.diycode.tool;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import moe.yukinoneko.diycode.R;

/**
 * @author drakeet
 */
public class SwipeRefreshDelegate {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindColor(R.color.colorAccent)
    int colorScheme;

    private OnSwipeRefreshListener providedListener;


    public interface OnSwipeRefreshListener {

        void onSwipeRefresh();
    }


    public SwipeRefreshDelegate(OnSwipeRefreshListener listener) {
        this.providedListener = listener;
    }


    public void attach(Activity activity) {
        ButterKnife.bind(this, activity);
        trySetupSwipeRefresh();
    }


    public void attach(View fragment) {
        ButterKnife.bind(this, fragment);
        trySetupSwipeRefresh();
    }


    private void trySetupSwipeRefresh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeColors(colorScheme);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    providedListener.onSwipeRefresh();
                }
            });
        }
    }


    public void setRefresh(boolean requestDataRefresh) {
        if (swipeRefreshLayout == null) {
            return;
        }
        if (!requestDataRefresh) {
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 1000);
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }
    }


    public void setEnabled(boolean enable) {
        if (swipeRefreshLayout == null) {
            throw new IllegalAccessError("The SwipeRefreshLayout has not been initialized.");
        }
        swipeRefreshLayout.setEnabled(enable);
    }


    public boolean isShowingRefresh() {
        return swipeRefreshLayout.isRefreshing();
    }


    public void setProgressViewOffset(boolean scale, int start, int end) {
        swipeRefreshLayout.setProgressViewOffset(scale, start, end);
    }
}
