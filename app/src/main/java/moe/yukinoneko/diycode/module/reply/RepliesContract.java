package moe.yukinoneko.diycode.module.reply;

import java.util.List;

import moe.yukinoneko.diycode.list.ListBaseView;
import moe.yukinoneko.diycode.bean.Reply;
import moe.yukinoneko.diycode.mvp.BasePresenter;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RepliesContract {
    interface View extends ListBaseView {
        void updateRepliesList(List<Reply> replies);
    }

    interface Presenter extends BasePresenter<View> {
        void fetchRepliesList(String feedType, int feedId, int offset);

        void fetchMyRepliesList(int offset);
    }
}
