package moe.yukinoneko.diycode.module.topic;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import moe.yukinoneko.diycode.api.DiyCodeRetrofit;
import moe.yukinoneko.diycode.bean.Topic;
import moe.yukinoneko.diycode.list.ListBasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TopicsPresenter extends ListBasePresenterImpl<TopicsContract.View, Topic> implements TopicsContract.Presenter {

    @Override
    public void fetchTopicsList(int offset) {
        DiyCodeRetrofit.api().fetchTopicsList(null, null, offset, null)
                       .compose(view.<List<Topic>>bindToLife())
                       .compose(applyCommonOperators())
                       .subscribe(new Consumer<List<Topic>>() {
                           @Override
                           public void accept(@NonNull List<Topic> topics) throws Exception {
                               view.updateTopicsList(topics);
                           }
                       });
    }

    @Override
    public void fetchUserTopicsList(String userLogin, int offset) {
        DiyCodeRetrofit.api().fetchUserTopicsList(userLogin, null, offset, null)
                       .compose(view.<List<Topic>>bindToLife())
                       .compose(applyCommonOperators())
                       .subscribe(new Consumer<List<Topic>>() {
                           @Override
                           public void accept(@NonNull List<Topic> topics) throws Exception {
                               view.updateTopicsList(topics);
                           }
                       });
    }
}
