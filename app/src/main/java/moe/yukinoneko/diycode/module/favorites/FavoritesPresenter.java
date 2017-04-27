package moe.yukinoneko.diycode.module.favorites;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import moe.yukinoneko.diycode.api.DiyCodeRetrofit;
import moe.yukinoneko.diycode.bean.Topic;
import moe.yukinoneko.diycode.list.ListBasePresenterImpl;
import moe.yukinoneko.diycode.tool.UserHelper;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FavoritesPresenter extends ListBasePresenterImpl<FavoritesContract.View, Topic> implements FavoritesContract.Presenter {

    @Override
    public void fetchFavoritesList(int offset) {
        if (!UserHelper.isLogin()) {
            return;
        }

        DiyCodeRetrofit.api().fetchUserFavoritesList(UserHelper.getUser().login, offset, null)
                       .compose(view.<List<Topic>>bindToLife())
                       .compose(applyCommonOperators())
                       .subscribe(new Consumer<List<Topic>>() {
                           @Override
                           public void accept(@NonNull List<Topic> topics) throws Exception {
                               view.updateFavoritesList(topics);
                           }
                       });
    }
}
