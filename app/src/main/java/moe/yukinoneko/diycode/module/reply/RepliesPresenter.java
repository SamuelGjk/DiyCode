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
