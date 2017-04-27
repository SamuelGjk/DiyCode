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

import java.util.Date;

/**
 * Created by SamuelGjk on 2017/3/27.
 */

public class Reply {

    @SerializedName("id") public int id;                        // 回复 id
    @SerializedName("body_html") public String bodyHtml;        // 回复内容详情(HTML)
    @SerializedName("created_at") public Date createdAt;        // 创建时间
    @SerializedName("updated_at") public Date updatedAt;        // 更新时间
    @SerializedName("deleted") public boolean deleted;          // 是否已经删除
    @SerializedName("user") public User user;                   // 创建该回复的用户
    @SerializedName("likes_count") public int likesCount;       // 点赞数
    @SerializedName("abilities") public Abilities abilities;    // 当前用户所拥有的权限
    @SerializedName("body") public String body;                 // 回复内容详情


    // topic 或 news 的 id
    @SerializedName(value = "feedId", alternate = { "topic_id", "news_id" })
    public int feedId;

    // topic 或 news 的标题
    @SerializedName(value = "feedTitle", alternate = { "topic_title", "news_title" })
    public String feedTitle;
}
