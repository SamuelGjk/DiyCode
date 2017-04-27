package moe.yukinoneko.diycode.module.reply;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import moe.yukinoneko.diycode.api.DiyCodeRetrofit;
import moe.yukinoneko.diycode.bean.Reply;
import moe.yukinoneko.diycode.list.ListBasePresenterImpl;
import moe.yukinoneko.diycode.tool.UserHelper;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RepliesPresenter extends ListBasePresenterImpl<RepliesContract.View, Reply> implements RepliesContract.Presenter {

    @Override
    public void fetchRepliesList(String feedType, int feedId, int offset) {
        DiyCodeRetrofit.api().fetchRepliesList(feedType, feedId, offset, null)
                       .compose(view.<List<Reply>>bindToLife())
                       .compose(applyCommonOperators())
                       .subscribe(new Consumer<List<Reply>>() {
                           @Override
                           public void accept(@NonNull List<Reply> replies) throws Exception {
                               view.updateRepliesList(replies);
                           }
                       });
    }

    @Override
    public void fetchMyRepliesList(int offset) {
        if (!UserHelper.isLogin()) {
            return;
        }

        DiyCodeRetrofit.api().fetchUserRepliesList(UserHelper.getUser().login, null, offset, null)
                       .compose(view.<List<Reply>>bindToLife())
                       .compose(applyCommonOperators())
                       .subscribe(new Consumer<List<Reply>>() {
                           @Override
                           public void accept(@NonNull List<Reply> replies) throws Exception {
                               view.updateRepliesList(replies);
                           }
                       });
    }
}
