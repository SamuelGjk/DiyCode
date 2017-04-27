package moe.yukinoneko.diycode.module.news;

import java.util.List;

import moe.yukinoneko.diycode.list.ListBaseView;
import moe.yukinoneko.diycode.bean.News;
import moe.yukinoneko.diycode.mvp.BasePresenter;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NewsContract {
    interface View extends ListBaseView {
        void updateNewsList(List<News> newses);
    }

    interface  Presenter extends BasePresenter<View> {
        void fetchNewsList(int offset);
    }
}
