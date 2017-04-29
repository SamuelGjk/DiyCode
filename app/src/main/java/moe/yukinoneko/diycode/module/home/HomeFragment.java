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

package moe.yukinoneko.diycode.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.base.BaseFragment;
import moe.yukinoneko.diycode.event.ListToTopEvent;
import moe.yukinoneko.diycode.event.OpenDrawerEvent;
import moe.yukinoneko.diycode.misc.FragmentBackHandler;
import moe.yukinoneko.diycode.module.login.LoginActivity;
import moe.yukinoneko.diycode.module.news.NewsFragment;
import moe.yukinoneko.diycode.module.news.create.CreateNewsActivity;
import moe.yukinoneko.diycode.module.notification.NotificationsActivity;
import moe.yukinoneko.diycode.module.site.SitesFragment;
import moe.yukinoneko.diycode.module.topic.TopicsFragment;
import moe.yukinoneko.diycode.module.topic.create.CreateTopicActivity;
import moe.yukinoneko.diycode.tool.ToastHelper;
import moe.yukinoneko.diycode.tool.UserHelper;

/**
 * Created by SamuelGjk on 2017/3/22.
 */

public class HomeFragment extends BaseFragment implements Toolbar.OnMenuItemClickListener, FragmentBackHandler {

    @BindView(R.id.search_view) MaterialSearchView searchView;
    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.pager) ViewPager pager;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideViewLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (toolbar != null) {
            toolbar.inflateMenu(R.menu.home);
            toolbar.setOnMenuItemClickListener(this);
            searchView.setMenuItem(toolbar.getMenu().findItem(R.id.action_search));
        }

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ToastHelper.showShort(getContext(), "搜索？不存在的");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        pager.setAdapter(new HomePagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    protected void onNavigationClick() {
        EventBus.getDefault().post(new OpenDrawerEvent());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_notification) {
            if (UserHelper.isLogin()) {
                startActivity(new Intent(getContext(), NotificationsActivity.class));
            } else {
                LoginActivity.launch(getContext());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            return true;
        }
        return false;
    }

    @OnClick({ R.id.fab, R.id.toolbar })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                switch (pager.getCurrentItem()) {
                    case 0:
                        if (UserHelper.isLogin()) {
                            startActivity(new Intent(getContext(), CreateTopicActivity.class));
                        } else {
                            LoginActivity.launch(getContext());
                        }
                        break;
                    case 1:
                        if (UserHelper.isLogin()) {
                            startActivity(new Intent(getContext(), CreateNewsActivity.class));
                        } else {
                            LoginActivity.launch(getContext());
                        }
                        break;
                }
                break;
            case R.id.toolbar:
                EventBus.getDefault().post(new ListToTopEvent(pager.getCurrentItem()));
                break;
        }
    }

    private class HomePagerAdapter extends FragmentPagerAdapter {

        private String[] categoryNames;

        HomePagerAdapter(FragmentManager manager) {
            super(manager);
            categoryNames = getResources().getStringArray(R.array.category_names);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return TopicsFragment.newInstance();
                case 1:
                    return NewsFragment.newInstance();
                case 2:
                    return SitesFragment.newInstance();
            }
            return null;
        }


        @Override
        public int getCount() {
            return categoryNames.length;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return categoryNames[position];
        }
    }
}
