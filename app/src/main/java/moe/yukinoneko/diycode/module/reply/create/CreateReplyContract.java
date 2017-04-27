package moe.yukinoneko.diycode.module.reply.create;

import moe.yukinoneko.diycode.mvp.BasePresenter;
import moe.yukinoneko.diycode.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateReplyContract {
    interface View extends BaseView {
        String getContent();

        void contentError();

        void showProgress(boolean show);

        void createSuccessful();
    }

    interface Presenter extends BasePresenter<View> {
        void newReply(String feedType, int feedId);
    }
}
