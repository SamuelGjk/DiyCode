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

package moe.yukinoneko.diycode.module.reply;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.Reply;
import moe.yukinoneko.diycode.list.BaseRecyclerListAdapter;
import moe.yukinoneko.diycode.list.ListBaseFragment;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RepliesFragment extends ListBaseFragment<RepliesContract.View, RepliesPresenter, Reply> implements RepliesContract.View {

    private static final String FEED_TYPE = "feed_type";
    private static final String FEED_ID = "feed_id";

    private String feedType;
    private int feedId;

    public static RepliesFragment newInstance() {
        Bundle args = new Bundle();
        RepliesFragment fragment = new RepliesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static RepliesFragment newInstance(String feedType, int feedId) {
        Bundle args = new Bundle();
        args.putString(FEED_TYPE, feedType);
        args.putInt(FEED_ID, feedId);
        RepliesFragment fragment = new RepliesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideViewLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        feedType = arguments.getString(FEED_TYPE);
        feedId = arguments.getInt(FEED_ID);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @NonNull
    @Override
    public BaseRecyclerListAdapter<Reply, ?> createAdapter() {
        return feedType != null ? new RepliesListAdapter(feedType) : new MyRepliesListAdapter();
    }

    @NonNull
    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected void loadData() {
        if (feedType != null) {
            presenter.fetchRepliesList(feedType, feedId, clear ? 0 : adapter.getItemCount());
        } else {
            presenter.fetchMyRepliesList(clear ? 0 : adapter.getItemCount());
        }
    }

    @Override
    public void updateRepliesList(List<Reply> replies) {
        updateList(replies);
        updateViewState();
    }
}
