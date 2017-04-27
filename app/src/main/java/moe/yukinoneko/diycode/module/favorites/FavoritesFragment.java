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

package moe.yukinoneko.diycode.module.favorites;


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
import moe.yukinoneko.diycode.event.OpenDrawerEvent;
import moe.yukinoneko.diycode.event.SaveUserEvent;
import moe.yukinoneko.diycode.list.BaseRecyclerListAdapter;
import moe.yukinoneko.diycode.list.ListBaseFragment;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FavoritesFragment extends ListBaseFragment<FavoritesContract.View, FavoritesPresenter, Topic> implements FavoritesContract.View {

    public static FavoritesFragment newInstance() {
        Bundle args = new Bundle();
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideViewLayoutId() {
        return R.layout.layout_recyclerview_with_toolbar;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolBarTitle(R.string.my_favorites);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
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
    public void onSaveUserEvent(SaveUserEvent event) {
        if (event.isNew) {
            loadData();
        }
    }

    @NonNull
    @Override
    public BaseRecyclerListAdapter<Topic, ?> createAdapter() {
        return new FavoritesListAdapter();
    }

    @NonNull
    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected void onNavigationClick() {
        EventBus.getDefault().post(new OpenDrawerEvent());
    }

    @Override
    protected void loadData() {
        presenter.fetchFavoritesList(clear ? 0 : adapter.getItemCount());
    }

    @Override
    public void updateFavoritesList(List<Topic> topics) {
        updateList(topics);
        updateViewState();
    }
}
