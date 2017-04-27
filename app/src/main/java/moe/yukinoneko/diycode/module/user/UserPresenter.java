/*
 * Copyright (c) 2017 SamuelGjk <samuel.alva@outlook.com>
 *
 * This file is part of DiyCode
 *
 * DiyCode is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DiyCode is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DiyCode. If not, see <http://www.gnu.org/licenses/>.
 */

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
