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

package moe.yukinoneko.diycode.module.login;

import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import moe.yukinoneko.diycode.api.DiyCodeRetrofit;
import moe.yukinoneko.diycode.api.OAuth;
import moe.yukinoneko.diycode.bean.Token;
import moe.yukinoneko.diycode.mvp.BasePresenterImpl;
import moe.yukinoneko.diycode.tool.UserHelper;

import static android.text.TextUtils.isEmpty;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login() {
        String username = view.getUsername();
        String password = view.getPassword();

        if (isEmpty(username)) {
            view.nameError();
            return;
        }

        if (isEmpty(password)) {
            view.passwordError();
            return;
        }

        DiyCodeRetrofit.api().getToken(OAuth.client_id, OAuth.client_secret,
                OAuth.GRANT_TYPE_LOGIN, username, password)
                       .compose(view.<Token>bindToLife())
                       .doOnSubscribe(new Consumer<Subscription>() {
                           @Override
                           public void accept(@NonNull Subscription subscription) throws Exception {
                               view.showProgress(true);
                           }
                       })
                       .observeOn(AndroidSchedulers.mainThread())
                       .doOnNext(new Consumer<Token>() {
                           @Override
                           public void accept(@NonNull Token token) throws Exception {
                               view.showProgress(false);
                           }
                       })
                       .doOnError(new Consumer<Throwable>() {
                           @Override
                           public void accept(@NonNull Throwable throwable) throws Exception {
                               view.showProgress(false);
                           }
                       })
                       .subscribe(new Consumer<Token>() {
                           @Override
                           public void accept(@NonNull Token token) throws Exception {
                               UserHelper.saveToken(token);
                               view.LoginSuccessful();
                           }
                       });
    }
}
