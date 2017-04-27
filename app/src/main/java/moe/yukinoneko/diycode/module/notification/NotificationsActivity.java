package moe.yukinoneko.diycode.module.notification;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kennyc.view.MultiStateView;

import java.util.List;

import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.Notification;
import moe.yukinoneko.diycode.list.BaseRecyclerListAdapter;
import moe.yukinoneko.diycode.list.ListBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NotificationsActivity extends ListBaseActivity<NotificationsContract.View, NotificationsPresenter, Notification>
        implements NotificationsContract.View {

    private int offset;

    @Override
    protected int provideContentViewId() {
        return R.layout.layout_recyclerview_with_toolbar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @NonNull
    @Override
    public BaseRecyclerListAdapter<Notification, ?> createAdapter() {
        return new NotificationsListAdapter();
    }

    @NonNull
    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected void loadData() {
        if (adapter.getItemCount() == 0) {
            switchViewState(MultiStateView.VIEW_STATE_LOADING);
        }
        presenter.fetchNotificationsList(clear ? 0 : offset);
    }

    @Override
    public void updateNotificationsList(List<Notification> notifications) {
        if (clear) {
            offset = notifications.size();
            adapter.setData(notifications);
        } else {
            offset += notifications.size();
            adapter.addAll(notifications);
        }

        updateViewState();
    }
}
