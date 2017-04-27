package moe.yukinoneko.diycode.module.site;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import moe.yukinoneko.diycode.api.DiyCodeRetrofit;
import moe.yukinoneko.diycode.bean.Sites;
import moe.yukinoneko.diycode.list.ListBasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SitesPresenter extends ListBasePresenterImpl<SitesContract.View, Object> implements SitesContract.Presenter {
    @Override
    public void fetchSites() {
        DiyCodeRetrofit.api().fetchSites()
                       .flatMap(new Function<List<Sites>, Publisher<List<Object>>>() {
                           @Override
                           public Publisher<List<Object>> apply(@NonNull List<Sites> sitesList) throws Exception {
                               List<Object> items = new ArrayList<>();
                               for (Sites sites : sitesList) {
                                   items.add(sites);
                                   for (Sites.Site site : sites.sites) {
                                       items.add(site);
                                   }
                               }
                               return Flowable.just(items);
                           }
                       })
                       .compose(view.<List<Object>>bindToLife())
                       .compose(applyCommonOperators())
                       .subscribe(new Consumer<List<Object>>() {
                           @Override
                           public void accept(@NonNull List<Object> objects) throws Exception {
                               view.updateSitesGrid(objects);
                           }
                       });
    }
}
