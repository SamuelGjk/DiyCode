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

package moe.yukinoneko.diycode.module.topic;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.Topic;
import moe.yukinoneko.diycode.event.ListToTopEvent;
import moe.yukinoneko.diycode.list.BaseRecyclerListAdapter;
import moe.yukinoneko.diycode.list.ListBaseFragment;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TopicsFragment extends ListBaseFragment<TopicsContract.View, TopicsPresenter, Topic> implements TopicsContract.View {

    private static final String USER_LOGIN = "user_login";

    private String userLogin;

    public static TopicsFragment newInstance() {
        Bundle args = new Bundle();
        TopicsFragment fragment = new TopicsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TopicsFragment newInstance(String userLogin) {
        Bundle args = new Bundle();
        args.putString(USER_LOGIN, userLogin);
        TopicsFragment fragment = new TopicsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideViewLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        userLogin = getArguments().getString(USER_LOGIN);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (userLogin != null) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        }
    }

    @NonNull
    @Override
    public BaseRecyclerListAdapter<Topic, ?> createAdapter() {
        return userLogin == null ? new TopicsListAdapter() : new UserTopicsListAdapter();
    }

    @NonNull
    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onListToTopEvent(ListToTopEvent event) {
        if (event.id == 0) {
            smoothScrollToPosition(0);
        }
    }

    @Override
    protected void loadData() {
        if (userLogin == null) {
            presenter.fetchTopicsList(clear ? 0 : adapter.getItemCount());
        } else {
            presenter.fetchUserTopicsList(userLogin, clear ? 0 : adapter.getItemCount());
        }
    }

    @Override
    public void updateTopicsList(List<Topic> topics) {
        updateList(topics);
        updateViewState();
    }
}
