package moe.yukinoneko.diycode.module.notification;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import moe.yukinoneko.diycode.api.DiyCodeRetrofit;
import moe.yukinoneko.diycode.bean.Notification;
import moe.yukinoneko.diycode.list.ListBasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NotificationsPresenter extends ListBasePresenterImpl<NotificationsContract.View, Notification> implements NotificationsContract.Presenter {

    @Override
    public void fetchNotificationsList(int offset) {
        DiyCodeRetrofit.api().fetchNotificationsList(offset, null)
                       .compose(view.<List<Notification>>bindToLife())
                       .compose(applyCommonOperators())
                       .subscribe(new Consumer<List<Notification>>() {
                           @Override
                           public void accept(@NonNull List<Notification> notifications) throws Exception {
                               view.updateNotificationsList(notifications);
                           }
                       });
    }
}
