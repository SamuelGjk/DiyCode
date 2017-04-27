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
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by SamuelGjk on 2017/3/27.
 */

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;


    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
    }


    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child, final View directTargetChild, final View target, final int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }


    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child, final View target, final int dxConsumed, final int dyConsumed, final int dxUnconsumed, final int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            animateOut(child);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            animateIn(child);
        }
    }


    // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
    private void animateOut(final FloatingActionButton button) {
        ViewCompat.animate(button)
                  .scaleX(0.0F)
                  .scaleY(0.0F)
                  .alpha(0.0F)
                  .setInterpolator(INTERPOLATOR)
                  .withLayer()
                  .setListener(new ViewPropertyAnimatorListener() {
                      public void onAnimationStart(View view) {
                          ScrollAwareFABBehavior.this.mIsAnimatingOut = true;
                      }


                      public void onAnimationCancel(View view) {
                          ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                      }


                      public void onAnimationEnd(View view) {
                          ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                          view.setVisibility(View.INVISIBLE);
                      }
                  })
                  .start();
    }


    // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
    private void animateIn(FloatingActionButton button) {
        button.setVisibility(View.VISIBLE);
        ViewCompat.animate(button)
                  .scaleX(1.0F)
                  .scaleY(1.0F)
                  .alpha(1.0F)
                  .setInterpolator(INTERPOLATOR)
                  .withLayer()
                  .setListener(null)
                  .start();
    }
}
