package moe.yukinoneko.diycode.module.user;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import moe.yukinoneko.diycode.api.DiyCodeRetrofit;
import moe.yukinoneko.diycode.bean.User;
import moe.yukinoneko.diycode.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class UserPresenter extends BasePresenterImpl<UserContract.View> implements UserContract.Presenter {

    @Override
    public void fetchUser(String userLogin) {
        DiyCodeRetrofit.api().fetchUser(userLogin)
                       .compose(view.<User>bindToLife())
                       .observeOn(AndroidSchedulers.mainThread())
                       .doOnError(new Consumer<Throwable>() {
                           @Override
                           public void accept(@NonNull Throwable throwable) throws Exception {
                               view.error();
                           }
                       })
                       .subscribe(new Consumer<User>() {
                           @Override
                           public void accept(@NonNull User user) throws Exception {
                               view.updateUser(user);
                           }
                       });
    }
}
