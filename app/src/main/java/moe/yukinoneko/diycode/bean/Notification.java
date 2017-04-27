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
 * Created by SamuelGjk on 2017/3/31.
 */

public class Notification {
    @SerializedName("id") public int id;                            // 通知 id
    @SerializedName("type") public String type;                     // 类型
    @SerializedName("read") public Boolean read;                    // 是否已读
    @SerializedName("actor") public User actor;                     // 相关人员
    @SerializedName("mention_type") public String mentionType;      // 提及类型
    @SerializedName("mention") public Reply mention;                // 提及详情
    @SerializedName("topic") public Topic topic;                    // 话题
    @SerializedName("reply") public Reply reply;                    // 回复
    @SerializedName("node") public Node node;                       // 节点变更
    @SerializedName("created_at") public Date createdAt;            // 创建时间
    @SerializedName("updated_at") public Date updatedAt;            // 更新时间
}
