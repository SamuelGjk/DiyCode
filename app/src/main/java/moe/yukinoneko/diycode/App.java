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

package moe.yukinoneko.diycode;

import android.app.Application;

import io.reactivex.plugins.RxJavaPlugins;
import moe.yukinoneko.diycode.api.OAuth;
import moe.yukinoneko.diycode.api.Secret;
import moe.yukinoneko.diycode.tool.UserHelper;

import static moe.yukinoneko.diycode.tool.ErrorHandler.errorConsumer;

/**
 * Created by SamuelGjk on 2017/3/23.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OAuth.init(Secret.getClientId(), Secret.getSecret());
        UserHelper.init(this);
        RxJavaPlugins.setErrorHandler(errorConsumer(this));
    }
}
