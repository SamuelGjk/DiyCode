package moe.yukinoneko.diycode.module.topic.details;

import moe.yukinoneko.diycode.bean.Like;
import moe.yukinoneko.diycode.bean.Topic;
import moe.yukinoneko.diycode.mvp.BasePresenter;
import moe.yukinoneko.diycode.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TopicDetailsContract {
    interface View extends BaseView {
        void updateTopicContent(Topic topic);

        void updateFavoriteState(boolean favorited);

        void updateLikeState(boolean liked, Like like);

        void error();
    }

    interface Presenter extends BasePresenter<View> {
        void fetchTopic(int id);

        void favoriteTopic(int id);

        void unfavoriteTopic(int id);

        void likeTopic(int id);

        void unlikeTopic(int id);
    }
}
