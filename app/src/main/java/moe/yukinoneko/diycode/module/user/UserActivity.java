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

package moe.yukinoneko.diycode.module.user;


import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.github.florent37.expectanim.ExpectAnim;
import com.kennyc.view.MultiStateView;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.User;
import moe.yukinoneko.diycode.event.LogoutEvent;
import moe.yukinoneko.diycode.module.topic.TopicsFragment;
import moe.yukinoneko.diycode.mvp.MVPBaseActivity;
import moe.yukinoneko.diycode.tool.ImageLoadHelper;
import moe.yukinoneko.diycode.tool.UserHelper;

import static com.github.florent37.expectanim.core.Expectations.alpha;
import static com.github.florent37.expectanim.core.Expectations.leftOfParent;
import static com.github.florent37.expectanim.core.Expectations.sameCenterVerticalAs;
import static com.github.florent37.expectanim.core.Expectations.scale;
import static com.github.florent37.expectanim.core.Expectations.toRightOf;
import static com.github.florent37.expectanim.core.Expectations.topOfParent;
import static com.kennyc.view.MultiStateView.VIEW_STATE_ERROR;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class UserActivity extends MVPBaseActivity<UserContract.View, UserPresenter> implements UserContract.View, AppBarLayout.OnOffsetChangedListener {
    private static final String EXTRA_USER_LOGIN = "extra_user_login";

    @BindView(R.id.multi_state_view) MultiStateView multiStateView;
    @BindView(R.id.image_user_avatar) RoundedImageView imageUserAvatar;
    @BindView(R.id.text_name) AppCompatTextView textName;
    @BindView(R.id.user_options) LinearLayout userOptions;
    @BindView(R.id.appbar) AppBarLayout appbar;
    @BindView(R.id.text_post_number) AppCompatTextView textPostNumber;
    @BindView(R.id.text_follow_number) AppCompatTextView textFollowNumber;
    @BindView(R.id.text_follower_number) AppCompatTextView textFollowerNumber;

    private String loginName;

    private ExpectAnim expectAnimMove;
    private ArgbEvaluator argbEvaluator;

    public static void launch(Context context, String userLogin) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(EXTRA_USER_LOGIN, userLogin);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        expectAnimMove = new ExpectAnim()
                .expect(textName)
                .toBe(
                        toRightOf(imageUserAvatar).withMarginDp(10),
                        sameCenterVerticalAs(imageUserAvatar)
                )
                .expect(imageUserAvatar)
                .toBe(
                        topOfParent().withMarginDp(10.5f),
                        leftOfParent().withMarginDp(72),
                        scale(0.5f, 0.5f)
                )
                .expect(userOptions)
                .toBe(
                        alpha(0)
                )
                .toAnimation();

        argbEvaluator = new ArgbEvaluator();

        setDisplayShowTitleEnabled(false);
        appbar.addOnOffsetChangedListener(this);

        View errorView = multiStateView.getView(VIEW_STATE_ERROR);
        if (errorView != null) {
            ButterKnife.findById(errorView, R.id.text_error)
                       .setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                               presenter.fetchUser(loginName);
                           }
                       });
        }

        loginName = getIntent().getStringExtra(EXTRA_USER_LOGIN);
        presenter.fetchUser(loginName);
        getSupportFragmentManager().beginTransaction()
                                   .replace(
                                           R.id.topics_container,
                                           TopicsFragment.newInstance(loginName)
                                   )
                                   .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        User user = UserHelper.getUser();
        if (user != null && loginName.equals(user.login)) {
            getMenuInflater().inflate(R.menu.user, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            UserHelper.logout();
            EventBus.getDefault().post(new LogoutEvent());
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        final float percent = Math.abs(verticalOffset * 1f / appBarLayout.getTotalScrollRange());
        Integer color = (Integer) argbEvaluator.evaluate(percent, Color.WHITE, navigationColor);
        setNavigationIconColor(color);
        textName.setTextColor(color);
        imageUserAvatar.post(new Runnable() {
            @Override
            public void run() {
                expectAnimMove.setPercent(percent);
            }
        });
    }

    @Override
    public void updateUser(User user) {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

        ImageLoadHelper.loadAvatar(
                this,
                user.avatarUrl,
                imageUserAvatar
        );
        textName.setText(user.login);
        textPostNumber.setText(String.valueOf(user.topicsCount));
        textFollowNumber.setText(String.valueOf(user.followingCount));
        textFollowerNumber.setText(String.valueOf(user.followersCount));
    }

    @Override
    public void error() {
        multiStateView.setViewState(VIEW_STATE_ERROR);
    }
}
