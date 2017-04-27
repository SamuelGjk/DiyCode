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

package moe.yukinoneko.diycode.module.reply.create;

import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import moe.yukinoneko.diycode.api.DiyCodeRetrofit;
import moe.yukinoneko.diycode.bean.Reply;
import moe.yukinoneko.diycode.mvp.BasePresenterImpl;

import static android.text.TextUtils.isEmpty;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateReplyPresenter extends BasePresenterImpl<CreateReplyContract.View> implements CreateReplyContract.Presenter {

    @Override
    public void newReply(String feedType, int feedId) {
        String content = view.getContent();

        if (isEmpty(content)) {
            view.contentError();
            return;
        }

        DiyCodeRetrofit.api().newReply(feedType, feedId, content)
                       .compose(view.<Reply>bindToLife())
                       .doOnSubscribe(new Consumer<Subscription>() {
                           @Override
                           public void accept(@NonNull Subscription subscription) throws Exception {
                               view.showProgress(true);
                           }
                       })
                       .observeOn(AndroidSchedulers.mainThread())
                       .doOnNext(new Consumer<Reply>() {
                           @Override
                           public void accept(@NonNull Reply reply) throws Exception {
                               view.showProgress(false);
                           }
                       })
                       .doOnError(new Consumer<Throwable>() {
                           @Override
                           public void accept(@NonNull Throwable throwable) throws Exception {
                               view.showProgress(false);
                           }
                       })
                       .subscribe(new Consumer<Reply>() {
                           @Override
                           public void accept(@NonNull Reply reply) throws Exception {
                               view.createSuccessful();
                           }
                       });
    }
}
