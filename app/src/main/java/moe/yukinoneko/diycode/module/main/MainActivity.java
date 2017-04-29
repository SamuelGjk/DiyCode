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

package moe.yukinoneko.diycode.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatTextView;
import android.view.MenuItem;
import android.view.View;

import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.User;
import moe.yukinoneko.diycode.event.LogoutEvent;
import moe.yukinoneko.diycode.event.OpenDrawerEvent;
import moe.yukinoneko.diycode.event.LoginEvent;
import moe.yukinoneko.diycode.misc.FragmentBackHandler;
import moe.yukinoneko.diycode.module.about.AboutActivity;
import moe.yukinoneko.diycode.module.favorites.FavoritesFragment;
import moe.yukinoneko.diycode.module.home.HomeFragment;
import moe.yukinoneko.diycode.module.myxx.MyXXFragment;
import moe.yukinoneko.diycode.module.login.LoginActivity;
import moe.yukinoneko.diycode.module.user.UserActivity;
import moe.yukinoneko.diycode.mvp.MVPBaseActivity;
import moe.yukinoneko.diycode.tool.ImageLoadHelper;
import moe.yukinoneko.diycode.tool.UserHelper;

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter>
        implements MainContract.View, NavigationView.OnNavigationItemSelectedListener {
    private static final int HOME = 0, POST = 1, FAVORITE = 2, REPLY = 3;

    @BindView(R.id.navigation_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

    private RoundedImageView imageUserAvatar;
    private AppCompatTextView textName;

    private Fragment[] fragments = {
            HomeFragment.newInstance(),
            MyXXFragment.newInstance(MyXXFragment.XX_TYPE_TOPIC),
            FavoritesFragment.newInstance(),
            MyXXFragment.newInstance(MyXXFragment.XX_TYPE_REPLY) };

    private FragmentManager fragmentManager;
    private int curFragment;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View header = navigationView.getHeaderView(0);
        imageUserAvatar = ButterKnife.findById(header, R.id.image_user_avatar);
        textName = ButterKnife.findById(header, R.id.text_name);

        imageUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                if (UserHelper.isLogin()) {
                    UserActivity.launch(MainActivity.this, UserHelper.getUser().login);
                } else {
                    LoginActivity.launch(MainActivity.this);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (Fragment f : fragments) {
            ft.add(R.id.content_container, f);
            ft.hide(f);
        }
        ft.show(fragments[HOME]);
        ft.commit();

        presenter.fetchMe();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getDelegate().onSaveInstanceState(outState);
    }

    @Override
    public void updateMe(User me) {
        ImageLoadHelper.loadAvatar(this, me.avatarUrl, imageUserAvatar);
        textName.setText(me.login);
    }

    private void switchFragment(int index) {
        if (index == curFragment) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.hide(fragments[curFragment]);
        ft.show(fragments[index]);
        ft.commit();

        curFragment = index;
    }

    @Override
    public void onBackPressed() {
        if (curFragment == HOME && fragments[HOME] instanceof FragmentBackHandler
                && ((FragmentBackHandler) fragments[HOME]).onBackPressed()) {
            // Empty
        } else if (curFragment != HOME) {
            switchFragment(HOME);
            navigationView.setCheckedItem(R.id.navigation_home);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    @Subscribe
    public void onOpenDrawerEvent(OpenDrawerEvent event) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Subscribe
    public void onLogin(LoginEvent event) {
        presenter.fetchMe();
    }

    @Subscribe
    public void onLogout(LogoutEvent event) {
        imageUserAvatar.setImageResource(R.mipmap.default_avatar);
        textName.setText(getString(R.string.click_to_login));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.navigation_home:
                switchFragment(HOME);
                return true;
            case R.id.navigation_post:
                if (UserHelper.isLogin()) {
                    switchFragment(POST);
                    return true;
                } else {
                    LoginActivity.launch(this);
                }
                break;
            case R.id.navigation_favorite:
                if (UserHelper.isLogin()) {
                    switchFragment(FAVORITE);
                    return true;
                } else {
                    LoginActivity.launch(this);
                }
                break;
            case R.id.navigation_reply:
                if (UserHelper.isLogin()) {
                    switchFragment(REPLY);
                    return true;
                } else {
                    LoginActivity.launch(this);
                }
                break;
            case R.id.navigation_about:
                startActivity(new Intent(this, AboutActivity.class));
                return false;
        }
        return false;
    }
}
