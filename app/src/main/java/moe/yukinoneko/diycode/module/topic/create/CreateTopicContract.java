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

import java.io.File;
import java.util.List;

import moe.yukinoneko.diycode.bean.Node;
import moe.yukinoneko.diycode.mvp.BasePresenter;
import moe.yukinoneko.diycode.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateTopicContract {
    interface View extends BaseView {
        void updateNodes(List<Node> nodes);

        int getNodeId();

        String getTopicTitle();

        String getContent();

        void showProgress(boolean show);

        void titleError();

        void contentError();

        void uploadSuccessful(String imageUrl);

        void createSuccessful();
    }

    interface Presenter extends BasePresenter<View> {
        void fetchNodes();

        void uploadPhoto(File photo);

        void newTopic();
    }
}
