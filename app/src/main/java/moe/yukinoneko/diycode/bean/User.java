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

/**
 * Created by SamuelGjk on 2017/3/23.
 */

public class User implements Parcelable {
    @SerializedName("id") public int id;                                // 用户 id
    @SerializedName("login") public String login;                       // 用户名
    @SerializedName("name") public String name;                         // 昵称
    @SerializedName("avatar_url") public String avatarUrl;              // 头像链接
    @SerializedName("location") public String location;                 // 城市
    @SerializedName("company") public String company;                   // 公司
    @SerializedName("twitter") public String twitter;                   // twitter
    @SerializedName("website") public String website;                   // 网站地址
    @SerializedName("bio") public String bio;                           // 个人介绍
    @SerializedName("tagline") public String tagline;                   // 签名
    @SerializedName("github") public String github;                     // github
    @SerializedName("created_at") public String createdAt;              // 创建时间
    @SerializedName("email") public String email;                       // email
    @SerializedName("topics_count") public int topicsCount;             // 话题数量
    @SerializedName("replies_count") public int repliesCount;           // 回复数量
    @SerializedName("following_count") public int followingCount;       // 关注人数
    @SerializedName("followers_count") public int followersCount;       // 粉丝人数
    @SerializedName("favorites_count") public int favoritesCount;       // 收藏数
    @SerializedName("level") public String level;                       // 等级
    @SerializedName("level_name") public String levelName;              // 等级名称

    protected User(Parcel in) {
        id = in.readInt();
        login = in.readString();
        name = in.readString();
        avatarUrl = in.readString();
        location = in.readString();
        company = in.readString();
        twitter = in.readString();
        website = in.readString();
        bio = in.readString();
        tagline = in.readString();
        github = in.readString();
        createdAt = in.readString();
        email = in.readString();
        topicsCount = in.readInt();
        repliesCount = in.readInt();
        followingCount = in.readInt();
        followersCount = in.readInt();
        favoritesCount = in.readInt();
        level = in.readString();
        levelName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(login);
        dest.writeString(name);
        dest.writeString(avatarUrl);
        dest.writeString(location);
        dest.writeString(company);
        dest.writeString(twitter);
        dest.writeString(website);
        dest.writeString(bio);
        dest.writeString(tagline);
        dest.writeString(github);
        dest.writeString(createdAt);
        dest.writeString(email);
        dest.writeInt(topicsCount);
        dest.writeInt(repliesCount);
        dest.writeInt(followingCount);
        dest.writeInt(followersCount);
        dest.writeInt(favoritesCount);
        dest.writeString(level);
        dest.writeString(levelName);
    }
}
