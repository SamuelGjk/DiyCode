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

package moe.yukinoneko.diycode.tool;

import android.content.Context;
import android.support.annotation.NonNull;

import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.Error;
import retrofit2.HttpException;

import static moe.yukinoneko.diycode.api.DiyCodeRetrofit.GSON;

/**
 * Created by SamuelGjk on 2017/4/1.
 */

public class ErrorHandler {

    public static Consumer<Throwable> errorConsumer(final Context context) {
        return new Consumer<Throwable>() {
            @Override public void accept(@NonNull Throwable throwable) {
                String errorMessage = null;
                if (throwable instanceof HttpException) {
                    HttpException httpException = (HttpException) throwable;
                    errorMessage = GSON.fromJson(httpException.response().errorBody().charStream(), Error.class).error;
                } else if (throwable instanceof UnknownHostException) {
                    errorMessage = context.getString(R.string.tip_network_is_not_available);
                } else {
                    errorMessage = throwable.getMessage();
                }
                ToastHelper.showShort(context, errorMessage);
            }
        };
    }
}
