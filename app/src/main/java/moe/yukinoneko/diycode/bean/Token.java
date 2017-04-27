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

package moe.yukinoneko.diycode.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SamuelGjk on 2017/3/23.
 */

public class Token {

    @SerializedName("access_token") public String accessToken;      // 用户令牌(获取相关数据使用)
    @SerializedName("token_type") public String tokenType;          // 令牌类型
    @SerializedName("expires_in") public int expiresIn;             // 过期时间
    @SerializedName("refresh_token") public String refreshToken;    // 刷新令牌(获取新的令牌)
    @SerializedName("created_at") public int createdAt;             // 创建时间
}
