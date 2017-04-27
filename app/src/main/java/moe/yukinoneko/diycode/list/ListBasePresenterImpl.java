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

package moe.yukinoneko.diycode.list;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import moe.yukinoneko.diycode.mvp.BasePresenterImpl;

import static moe.yukinoneko.diycode.misc.Constants.LIMIT;

/**
 * Created by SamuelGjk on 2017/4/23.
 */

public class ListBasePresenterImpl<V extends ListBaseView, MODEL> extends BasePresenterImpl<V> {

    protected FlowableTransformer<List<MODEL>, List<MODEL>> applyCommonOperators() {
        return commonOperatorsTransformer;
    }

    private final FlowableTransformer<List<MODEL>, List<MODEL>> commonOperatorsTransformer = new FlowableTransformer<List<MODEL>, List<MODEL>>() {
        @Override
        public Publisher<List<MODEL>> apply(Flowable<List<MODEL>> upstream) {
            return upstream
                    .doOnSubscribe(new Consumer<Subscription>() {
                        @Override
                        public void accept(@NonNull Subscription subscription) throws Exception {
                            view.notifyLoadingStarted();
                        }
                    })
                    .doOnNext(new Consumer<List<MODEL>>() {
                        @Override
                        public void accept(@NonNull List<MODEL> models) throws Exception {
                            view.setEnd(models.size() < LIMIT);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            view.error();
                        }
                    })
                    .doFinally(new Action() {
                        @Override
                        public void run() throws Exception {
                            view.setRefresh(false);
                            view.notifyLoadingFinished();
                        }
                    });
        }
    };
}
