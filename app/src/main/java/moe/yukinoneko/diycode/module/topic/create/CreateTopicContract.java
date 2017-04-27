package moe.yukinoneko.diycode.module.topic.create;

import java.io.File;
import java.util.List;

import moe.yukinoneko.diycode.bean.Node;
import moe.yukinoneko.diycode.mvp.BasePresenter;
import moe.yukinoneko.diycode.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateTopicContract {
    interface View extends BaseView {
        void updateNodes(List<Node> nodes);

        int getNodeId();

        String getTopicTitle();

        String getContent();

        void showProgress(boolean show);

        void titleError();

        void contentError();

        void uploadSuccessful(String imageUrl);

        void createSuccessful();
    }

    interface Presenter extends BasePresenter<View> {
        void fetchNodes();

        void uploadPhoto(File photo);

        void newTopic();
    }
}
