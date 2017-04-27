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
 * Created by SamuelGjk on 2017/3/30.
 */

public class Node {

    @SerializedName("id") public int id;                            // 节点 id
    @SerializedName("name") public String name;                     // 节点名
    @SerializedName("topics_count") public int topicsCount;         // 话题数量
    @SerializedName("summary") public String summary;               // 节点介绍
    @SerializedName("section_id") public int sectionId;             // 分类 id
    @SerializedName("sort") public int sort;                        // 排序
    @SerializedName("section_name") public String sectionName;      // 分类名
    @SerializedName("updated_at") public Date updatedAt;            // 更新时间
}
