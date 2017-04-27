package moe.yukinoneko.diycode.module.favorites;

import java.util.List;

import moe.yukinoneko.diycode.list.ListBaseView;
import moe.yukinoneko.diycode.bean.Topic;
import moe.yukinoneko.diycode.mvp.BasePresenter;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FavoritesContract {
    interface View extends ListBaseView {
        void updateFavoritesList(List<Topic> topics);
    }

    interface Presenter extends BasePresenter<View> {
        void fetchFavoritesList(int offset);
    }
}
