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
import android.content.SharedPreferences;

import com.securepreferences.SecurePreferences;

import moe.yukinoneko.diycode.bean.Token;
import moe.yukinoneko.diycode.bean.User;

import static moe.yukinoneko.diycode.api.DiyCodeRetrofit.GSON;

/**
 * Created by SamuelGjk on 2017/3/23.
 */

public class UserHelper {

    private static final String TOKEN = "token";
    private static final String USER = "user";

    private static SharedPreferences prefs;

    public static void init(Context context) {
        prefs = new SecurePreferences(context.getApplicationContext());
    }

    public static void saveToken(Token token) {
        prefs.edit().putString(TOKEN, GSON.toJson(token)).apply();
    }

    public static Token getToken() {
        String tokenJson = prefs.getString(TOKEN, null);
        if (tokenJson != null) {
            return GSON.fromJson(tokenJson, Token.class);
        }
        return null;
    }

    public static boolean isLogin() {
        return null != getToken();
    }

    public static boolean hasUser() {
        return null != getUser();
    }

    public static void saveUser(User user) {
        prefs.edit().putString(USER, GSON.toJson(user)).apply();
    }

    public static User getUser() {
        String userJson = prefs.getString(USER, null);
        if (userJson != null) {
            return GSON.fromJson(userJson, User.class);
        }
        return null;
    }

    public static void logout() {
        prefs.edit().remove(USER).remove(TOKEN).apply();
    }
}
