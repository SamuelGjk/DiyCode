package moe.yukinoneko.diycode.module.topic.details;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import moe.yukinoneko.diycode.api.DiyCodeRetrofit;
import moe.yukinoneko.diycode.bean.Like;
import moe.yukinoneko.diycode.bean.State;
import moe.yukinoneko.diycode.bean.Topic;
import moe.yukinoneko.diycode.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TopicDetailsPresenter extends BasePresenterImpl<TopicDetailsContract.View> implements TopicDetailsContract.Presenter {

    @Override
    public void fetchTopic(int id) {
        DiyCodeRetrofit.api().fetchTopic(id)
                       .compose(view.<Topic>bindToLife())
                       .observeOn(AndroidSchedulers.mainThread())
                       .doOnError(new Consumer<Throwable>() {
                           @Override
                           public void accept(@NonNull Throwable throwable) throws Exception {
                               view.error();
                           }
                       })
                       .subscribe(new Consumer<Topic>() {
                           @Override
                           public void accept(@NonNull Topic topic) throws Exception {
                               view.updateTopicContent(topic);
                           }
                       });
    }

    @Override
    public void favoriteTopic(int id) {
        DiyCodeRetrofit.api().favoriteTopic(id)
                       .compose(view.<State>bindToLife())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Consumer<State>() {
                           @Override
                           public void accept(@NonNull State state) throws Exception {
                               if (state.ok == 1) {
                                   view.updateFavoriteState(true);
                               }
                           }
                       });
    }

    @Override
    public void unfavoriteTopic(int id) {
        DiyCodeRetrofit.api().unfavoriteTopic(id)
                       .compose(view.<State>bindToLife())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Consumer<State>() {
                           @Override
                           public void accept(@NonNull State state) throws Exception {
                               if (state.ok == 1) {
                                   view.updateFavoriteState(false);
                               }
                           }
                       });
    }

    @Override
    public void likeTopic(int id) {
        DiyCodeRetrofit.api().like("topic", id)
                       .compose(view.<Like>bindToLife())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Consumer<Like>() {
                           @Override
                           public void accept(@NonNull Like like) throws Exception {
                               view.updateLikeState(true, like);
                           }
                       });
    }

    @Override
    public void unlikeTopic(int id) {
        DiyCodeRetrofit.api().unlike("topic", id)
                       .compose(view.<Like>bindToLife())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Consumer<Like>() {
                           @Override
                           public void accept(@NonNull Like like) throws Exception {
                               view.updateLikeState(false, like);
                           }
                       });
    }
}
