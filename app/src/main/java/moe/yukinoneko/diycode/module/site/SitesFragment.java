package moe.yukinoneko.diycode.module.site;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.Sites;
import moe.yukinoneko.diycode.event.ListToTopEvent;
import moe.yukinoneko.diycode.list.BaseRecyclerListAdapter;
import moe.yukinoneko.diycode.list.ListBaseFragment;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SitesFragment extends ListBaseFragment<SitesContract.View, SitesPresenter, Object> implements SitesContract.View {

    public static SitesFragment newInstance() {
        Bundle args = new Bundle();
        SitesFragment fragment = new SitesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideViewLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @NonNull
    @Override
    public BaseRecyclerListAdapter<Object, ?> createAdapter() {
        return new SitesGridAdapter();
    }

    @NonNull
    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                System.out.println(adapter.getItem(position) instanceof Sites);
                return (adapter.getItem(position) instanceof Sites.Site) ? 1 : 2;
            }
        });
        return layoutManager;
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
        if (event.id == 2) {
            smoothScrollToPosition(0);
        }
    }

    @Override
    protected void loadData() {
        presenter.fetchSites();
    }

    @Override
    protected boolean onInterceptLoadMore() {
        return true;
    }

    @Override
    public void updateSitesGrid(List<Object> sites) {
        updateList(sites);
        updateViewState();
    }
}
