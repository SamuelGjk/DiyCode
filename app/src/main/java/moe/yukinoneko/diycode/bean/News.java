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

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by SamuelGjk on 2017/3/24.
 */

public class News implements Parcelable {

    @SerializedName("id") public int id;                                            // 新闻 id
    @SerializedName("title") public String title;                                   // 标题
    @SerializedName("created_at") public Date createdAt;                            // 创建时间
    @SerializedName("updated_at") public Date updatedAt;                            // 更新时间
    @SerializedName("user") public User user;                                       // 创建该话题的用户
    @SerializedName("node_name") public String nodeName;                            // 节点名称
    @SerializedName("node_id") public int nodeId;                                   // 节点 id
    @SerializedName("last_reply_user_id") public int lastReplyUserId;               // 最近一次回复的用户 id
    @SerializedName("last_reply_user_login") public String lastReplyUserLogin;      // 最近一次回复的用户登录名
    @SerializedName("replied_at") public Date repliedAt;                            // 最近一次回复时间
    @SerializedName("replies_count") public int repliesCount;                       // 回复总数量
    @SerializedName("address") public String address;                               // 具体地址

    protected News(Parcel in) {
        id = in.readInt();
        title = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        nodeName = in.readString();
        nodeId = in.readInt();
        lastReplyUserId = in.readInt();
        lastReplyUserLogin = in.readString();
        repliesCount = in.readInt();
        address = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeParcelable(user, flags);
        dest.writeString(nodeName);
        dest.writeInt(nodeId);
        dest.writeInt(lastReplyUserId);
        dest.writeString(lastReplyUserLogin);
        dest.writeInt(repliesCount);
        dest.writeString(address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
