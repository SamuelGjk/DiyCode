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

package moe.yukinoneko.diycode.module.myxx;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.base.BaseFragment;
import moe.yukinoneko.diycode.event.OpenDrawerEvent;
import moe.yukinoneko.diycode.event.SaveUserEvent;
import moe.yukinoneko.diycode.module.reply.RepliesFragment;
import moe.yukinoneko.diycode.module.topic.TopicsFragment;
import moe.yukinoneko.diycode.tool.UserHelper;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MyXXFragment extends BaseFragment {

    private static final String XX_TYPE = "xx_type";

    public static final int XX_TYPE_TOPIC = 10000;
    public static final int XX_TYPE_REPLY = 10001;

    public static MyXXFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(XX_TYPE, type);
        MyXXFragment fragment = new MyXXFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideViewLayoutId() {
        return R.layout.fragment_my_xx;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (getArguments().getInt(XX_TYPE)) {
            case XX_TYPE_TOPIC:
                setToolBarTitle(R.string.my_posts);
                break;
            case XX_TYPE_REPLY:
                setToolBarTitle(R.string.my_replies);
                break;
        }
    }

    private void setup() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        switch (getArguments().getInt(XX_TYPE)) {
            case XX_TYPE_TOPIC:
                ft.replace(R.id.content_container, TopicsFragment.newInstance(UserHelper.getUser().login));
                break;
            case XX_TYPE_REPLY:
                ft.replace(R.id.content_container, RepliesFragment.newInstance());
                break;
        }
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onSaveUserEvent(SaveUserEvent event) {
        setup();
    }

    @Override
    protected void onNavigationClick() {
        EventBus.getDefault().post(new OpenDrawerEvent());
    }
}
