package moe.yukinoneko.diycode.module.site;

import java.util.List;

import moe.yukinoneko.diycode.list.ListBaseView;
import moe.yukinoneko.diycode.mvp.BasePresenter;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SitesContract {
    interface View extends ListBaseView {
        void updateSitesGrid(List<Object> sites);
    }

    interface Presenter extends BasePresenter<View> {
        void fetchSites();
    }
}
