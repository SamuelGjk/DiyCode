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

package moe.yukinoneko.diycode.module.topic.create;

import org.reactivestreams.Subscription;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import moe.yukinoneko.diycode.api.DiyCodeRetrofit;
import moe.yukinoneko.diycode.bean.Node;
import moe.yukinoneko.diycode.bean.Photo;
import moe.yukinoneko.diycode.bean.Topic;
import moe.yukinoneko.diycode.mvp.BasePresenterImpl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.text.TextUtils.isEmpty;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateTopicPresenter extends BasePresenterImpl<CreateTopicContract.View> implements CreateTopicContract.Presenter {

    @Override
    public void fetchNodes() {
        DiyCodeRetrofit.api().fetchNodes()
                       .compose(view.<List<Node>>bindToLife())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Consumer<List<Node>>() {
                           @Override
                           public void accept(@NonNull List<Node> nodes) throws Exception {
                               view.updateNodes(nodes);
                           }
                       });
    }

    @Override
    public void uploadPhoto(File photo) {
        if (photo == null) {
            return;
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), photo);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", photo.getName(), requestFile);

        DiyCodeRetrofit.api().uploadPhoto(body)
                       .compose(view.<Photo>bindToLife())
                       .doOnSubscribe(new Consumer<Subscription>() {
                           @Override
                           public void accept(@NonNull Subscription subscription) throws Exception {
                               view.showProgress(true);
                           }
                       })
                       .observeOn(AndroidSchedulers.mainThread())
                       .doOnNext(new Consumer<Photo>() {
                           @Override
                           public void accept(@NonNull Photo photo) throws Exception {
                               view.showProgress(false);
                           }
                       })
                       .doOnError(new Consumer<Throwable>() {
                           @Override
                           public void accept(@NonNull Throwable throwable) throws Exception {
                               view.showProgress(false);
                           }
                       })
                       .subscribe(new Consumer<Photo>() {
                           @Override
                           public void accept(@NonNull Photo photo) throws Exception {
                               view.uploadSuccessful(photo.imageUrl);
                           }
                       });
    }

    @Override
    public void newTopic() {
        int nodeId = view.getNodeId();
        String title = view.getTopicTitle();
        String content = view.getContent();

        if (isEmpty(title)) {
            view.titleError();
            return;
        }
        if (isEmpty(content)) {
            view.contentError();
            return;
        }

        DiyCodeRetrofit.api().newTopic(title, content, nodeId)
                       .compose(view.<Topic>bindToLife())
                       .doOnSubscribe(new Consumer<Subscription>() {
                           @Override
                           public void accept(@NonNull Subscription subscription) throws Exception {
                               view.showProgress(true);
                           }
                       })
                       .observeOn(AndroidSchedulers.mainThread())
                       .doOnNext(new Consumer<Topic>() {
                           @Override
                           public void accept(@NonNull Topic topic) throws Exception {
                               view.showProgress(false);
                           }
                       })
                       .doOnError(new Consumer<Throwable>() {
                           @Override
                           public void accept(@NonNull Throwable throwable) throws Exception {
                               view.showProgress(false);
                           }
                       })
                       .subscribe(new Consumer<Topic>() {
                           @Override
                           public void accept(@NonNull Topic topic) throws Exception {
                               view.createSuccessful();
                           }
                       });
    }
}
