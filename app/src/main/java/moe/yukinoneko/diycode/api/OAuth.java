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

package moe.yukinoneko.diycode.api;

/**
 * Created by SamuelGjk on 2017/3/23.
 */

public class OAuth {
    // 认证类型
    public static final String GRANT_TYPE_LOGIN = "password";             // 密码
    public static final String GRANT_TYPE_REFRESH = "refresh_token";      // 刷新令牌

    public static final String KEY_TOKEN = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static String client_id;          // 应用ID
    public static String client_secret;      // 私钥

    public static void init(String clientId, String clientSecret) {
        client_id = clientId;
        client_secret = clientSecret;
    }
}
