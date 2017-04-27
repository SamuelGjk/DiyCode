package moe.yukinoneko.diycode.module.topic;

import java.util.List;

import moe.yukinoneko.diycode.list.ListBaseView;
import moe.yukinoneko.diycode.bean.Topic;
import moe.yukinoneko.diycode.mvp.BasePresenter;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TopicsContract {
    interface View extends ListBaseView {
        void updateTopicsList(List<Topic> topics);
    }

    interface Presenter extends BasePresenter<View> {
        void fetchTopicsList(int offset);

        void fetchUserTopicsList(String userLogin, int offset);
    }
}
