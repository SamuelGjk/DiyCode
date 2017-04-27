package moe.yukinoneko.diycode.module.news;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import moe.yukinoneko.diycode.api.DiyCodeRetrofit;
import moe.yukinoneko.diycode.bean.News;
import moe.yukinoneko.diycode.list.ListBasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NewsPresenter extends ListBasePresenterImpl<NewsContract.View, News> implements NewsContract.Presenter {

    @Override
    public void fetchNewsList(int offset) {
        DiyCodeRetrofit.api().fetchNewsList(null, offset, null)
                       .compose(view.<List<News>>bindToLife())
                       .compose(applyCommonOperators())
                       .subscribe(new Consumer<List<News>>() {
                           @Override
                           public void accept(@NonNull List<News> newses) throws Exception {
                               view.updateNewsList(newses);
                           }
                       });
    }
}
