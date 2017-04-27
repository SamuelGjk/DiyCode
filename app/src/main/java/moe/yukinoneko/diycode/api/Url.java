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

public interface Url {
    String BASE_URL = "https://diycode.cc/api/v3/";
    String OAUTH_URL = "https://www.diycode.cc/oauth/token";
    String TOPICS = "topics.json";
    String TOPIC = "topics/{id}.json";
    String NEWS = "news.json";
    String REPLIES = "{type}/{id}/replies.json";
    String NODES = "nodes.json";
    String NEWS_NODES = "news/nodes.json";
    String USER = "users/{login}.json";
    String USER_ME = "users/me.json";
    String USER_TOPICS = "users/{login}/topics.json";
    String USER_FAVORITES = "users/{login}/favorites.json";
    String USER_REPLIES = "users/{login}/replies.json";
    String FAVORITE_TOPIC = "topics/{id}/favorite.json";
    String UNFAVORITE_TOPIC = "topics/{id}/unfavorite.json";
    String LIKES = "likes.json";
    String NOTIFICATIONS = "notifications.json";
    String PHOTOS = "photos.json";
    String SITES = "sites.json";
}
