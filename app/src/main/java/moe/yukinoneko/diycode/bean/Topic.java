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
 * Created by SamuelGjk on 2017/3/23.
 *
 * 列表和详情使用同一个实体类
 */

public class Topic {
    @SerializedName("id") public int id;                                            // 话题 id
    @SerializedName("title") public String title;                                   // 标题
    @SerializedName("created_at") public Date createdAt;                            // 创建时间
    @SerializedName("updated_at") public Date updatedAt;                            // 更新时间
    @SerializedName("replied_at") public Date repliedAt;                            // 最近一次回复时间
    @SerializedName("replies_count") public int repliesCount;                       // 回复总数量
    @SerializedName("node_name") public String nodeName;                            // 节点名称
    @SerializedName("node_id") public int nodeId;                                   // 节点 id
    @SerializedName("last_reply_user_id") public int lastReplyUserId;               // 最近一次回复的用户 id
    @SerializedName("last_reply_user_login") public String lastReplyUserLogin;      // 最近一次回复的用户登录名
    @SerializedName("user") public User user;                                       // 创建该话题的用户
    @SerializedName("deleted") public boolean deleted;                              // 是否已删除
    @SerializedName("excellent") public boolean excellent;                          // 是否加精
    @SerializedName("abilities") public Abilities abilities;                        // 当前用户对该话题拥有的权限
    @SerializedName("body") public String body;                                     // 话题详情(Markdown)
    @SerializedName("body_html") public String bodyHtml;                            // 话题详情(HTML)
    @SerializedName("hits") public int hits;                                        // 浏览次数
    @SerializedName("likes_count") public int likesCount;                           // 点赞数
    @SerializedName("suggested_at") public Date suggestedAt;                        // 置顶(推荐)时间
    @SerializedName("followed") public Boolean followed;                            // 是否关注
    @SerializedName("liked") public Boolean liked;                                  // 是否喜欢
    @SerializedName("favorited") public Boolean favorited;                          // 是否收藏
}
