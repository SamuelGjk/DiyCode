package moe.yukinoneko.diycode.module.news.create;

import java.util.List;

import moe.yukinoneko.diycode.bean.NewsNode;
import moe.yukinoneko.diycode.mvp.BasePresenter;
import moe.yukinoneko.diycode.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateNewsContract {
    interface View extends BaseView {
        void updateNodes(List<NewsNode> nodes);

        String getNewsTitle();

        String getLink();

        void showProgress(boolean show);

        void titleError();

        void linkError();

        void createSuccessful();
    }

    interface Presenter extends BasePresenter<View> {
        void fetchNewsNodes();

        void newNews(int nodeId);
    }
}
