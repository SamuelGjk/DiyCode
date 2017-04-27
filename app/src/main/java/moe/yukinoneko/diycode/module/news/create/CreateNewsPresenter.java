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

package moe.yukinoneko.diycode.module.news.create;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import moe.yukinoneko.diycode.api.DiyCodeRetrofit;
import moe.yukinoneko.diycode.bean.News;
import moe.yukinoneko.diycode.bean.NewsNode;
import moe.yukinoneko.diycode.mvp.BasePresenterImpl;

import static android.text.TextUtils.isEmpty;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateNewsPresenter extends BasePresenterImpl<CreateNewsContract.View> implements CreateNewsContract.Presenter {

    @Override
    public void fetchNewsNodes() {
        DiyCodeRetrofit.api().fetchNewsNodes()
                       .compose(view.<List<NewsNode>>bindToLife())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Consumer<List<NewsNode>>() {
                           @Override
                           public void accept(@NonNull List<NewsNode> nodes) throws Exception {
                               view.updateNodes(nodes);
                           }
                       });
    }

    @Override
    public void newNews(int nodeId) {
        String title = view.getNewsTitle();
        String link = view.getLink();

        if (isEmpty(title)) {
            view.titleError();
            return;
        }
        if (isEmpty(link)) {
            view.linkError();
            return;
        }

        DiyCodeRetrofit.api().newNews(title, link, nodeId)
                       .compose(view.<News>bindToLife())
                       .doOnSubscribe(new Consumer<Subscription>() {
                           @Override
                           public void accept(@NonNull Subscription subscription) throws Exception {
                               view.showProgress(true);
                           }
                       })
                       .observeOn(AndroidSchedulers.mainThread())
                       .doOnNext(new Consumer<News>() {
                           @Override
                           public void accept(@NonNull News news) throws Exception {
                               view.showProgress(false);
                           }
                       })
                       .doOnError(new Consumer<Throwable>() {
                           @Override
                           public void accept(@NonNull Throwable throwable) throws Exception {
                               view.showProgress(false);
                           }
                       })
                       .subscribe(new Consumer<News>() {
                           @Override
                           public void accept(@NonNull News news) throws Exception {
                               view.createSuccessful();
                           }
                       });
    }
}
