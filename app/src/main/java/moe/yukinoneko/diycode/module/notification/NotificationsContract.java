package moe.yukinoneko.diycode.module.notification;

import java.util.List;

import moe.yukinoneko.diycode.list.ListBaseView;
import moe.yukinoneko.diycode.bean.Notification;
import moe.yukinoneko.diycode.mvp.BasePresenter;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NotificationsContract {
    interface View extends ListBaseView {
        void updateNotificationsList(List<Notification> notifications);
    }

    interface Presenter extends BasePresenter<View> {
        void fetchNotificationsList(int offset);
    }
}
