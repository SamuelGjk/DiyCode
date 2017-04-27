package moe.yukinoneko.diycode.module.news;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.News;
import moe.yukinoneko.diycode.event.ListToTopEvent;
import moe.yukinoneko.diycode.list.BaseRecyclerListAdapter;
import moe.yukinoneko.diycode.list.ListBaseFragment;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NewsFragment extends ListBaseFragment<NewsContract.View, NewsPresenter, News> implements NewsContract.View {

    public static NewsFragment newInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideViewLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @NonNull
    @Override
    public BaseRecyclerListAdapter<News, ?> createAdapter() {
        return new NewsListAdapter();
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
        if (event.id == 1) {
            smoothScrollToPosition(0);
        }
    }

    @Override
    protected void loadData() {
        presenter.fetchNewsList(clear ? 0 : adapter.getItemCount());
    }

    @Override
    public void updateNewsList(List<News> newses) {
        updateList(newses);
        updateViewState();
    }
}
