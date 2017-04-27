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

import java.util.List;

import io.reactivex.Flowable;
import moe.yukinoneko.diycode.bean.Like;
import moe.yukinoneko.diycode.bean.News;
import moe.yukinoneko.diycode.bean.NewsNode;
import moe.yukinoneko.diycode.bean.Node;
import moe.yukinoneko.diycode.bean.Notification;
import moe.yukinoneko.diycode.bean.Photo;
import moe.yukinoneko.diycode.bean.Reply;
import moe.yukinoneko.diycode.bean.Sites;
import moe.yukinoneko.diycode.bean.State;
import moe.yukinoneko.diycode.bean.Token;
import moe.yukinoneko.diycode.bean.Topic;
import moe.yukinoneko.diycode.bean.User;
import okhttp3.MultipartBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by SamuelGjk on 2017/3/23.
 */

public interface DiyCodeService {

    /**
     * 获取 Token (一般在登录时调用)
     *
     * @param clientId     客户端 id
     * @param clientSecret 客户端私钥
     * @param grantType    授权方式 - 密码
     * @param username     用户名
     * @param password     密码
     * @return Token 实体类
     */
    @POST(Url.OAUTH_URL)
    @FormUrlEncoded
    Flowable<Token> getToken(@Field("client_id") String clientId, @Field("client_secret") String clientSecret,
                             @Field("grant_type") String grantType, @Field("username") String username,
                             @Field("password") String password);

    /**
     * 刷新 token
     *
     * @param clientId     客户端 id
     * @param clientSecret 客户端私钥
     * @param grantType    授权方式 - Refresh Token
     * @param refreshToken token 信息
     * @return Token 实体类
     */
    @POST(Url.OAUTH_URL)
    @FormUrlEncoded
    Flowable<Token> refreshToken(@Field("client_id") String clientId, @Field("client_secret") String clientSecret,
                                 @Field("grant_type") String grantType, @Field("refresh_token") String refreshToken);

    //--------------------------------------------- Topic ------------------------------------------------------//

    /**
     * 获取 topic 列表
     *
     * @param type   类型，默认 last_actived，可选["last_actived", "recent", "no_reply", "popular", "excellent"]
     * @param nodeId 如果你需要只看某个节点的，请传此参数, 如果不传 则返回全部
     * @param offset 偏移数值，默认值 0
     * @param limit  数量极限，默认值 20，值范围 1..150
     * @return topic 列表
     */
    @GET(Url.TOPICS)
    Flowable<List<Topic>> fetchTopicsList(@Query("type") String type, @Query("node_id") Integer nodeId,
                                          @Query("offset") Integer offset, @Query("limit") Integer limit);

    /**
     * 获取 topic 内容
     *
     * @param id topic 的 id
     * @return 内容详情
     */
    @GET(Url.TOPIC)
    Flowable<Topic> fetchTopic(@Path("id") int id);

    /**
     * 创建一个新的 topic
     *
     * @param title  话题标题
     * @param body   话题内容, Markdown 格式
     * @param nodeId 节点编号
     * @return 新话题的内容详情
     */
    @POST(Url.TOPICS)
    @FormUrlEncoded
    Flowable<Topic> newTopic(@Field("title") String title, @Field("body") String body,
                             @Field("node_id") Integer nodeId);

    /**
     * 收藏话题
     *
     * @param id 被收藏的话题 id
     * @return 状态信息
     */
    @POST(Url.FAVORITE_TOPIC)
    Flowable<State> favoriteTopic(@Path("id") int id);

    /**
     * 取消收藏话题
     *
     * @param id 被收藏的话题 id
     * @return 状态信息
     */
    @POST(Url.UNFAVORITE_TOPIC)
    Flowable<State> unfavoriteTopic(@Path("id") int id);

    //----------------------------------------------------------------------------------------------------------//

    /**
     * 获取 news 列表
     *
     * @param nodeId 如果你需要只看某个节点的，请传此参数, 如果不传 则返回全部
     * @param offset 偏移数值，默认值 0
     * @param limit  数量极限，默认值 20，值范围 1..150
     * @return news 列表
     */
    @GET(Url.NEWS)
    Flowable<List<News>> fetchNewsList(@Query("node_id") Integer nodeId, @Query("offset") Integer offset,
                                       @Query("limit") Integer limit);

    /**
     * 创建一个 news (分享)
     *
     * @param title   标题
     * @param address 地址(网址链接)
     * @param nodeId  节点 id
     * @return 新分享内容
     */
    @POST(Url.NEWS)
    @FormUrlEncoded
    Flowable<News> newNews(@Field("title") String title, @Field("address") String address,
                           @Field("node_id") Integer nodeId);

    //-------------------------------------------- reply ------------------------------------------//

    /**
     * 创建回帖(回复，评论) (只能创建 Topic 的，news 的没有 api)
     *
     * @param type feed 的 type 可选["topics"]
     * @param id   feed 的 id
     * @param body 回帖内容, Markdown 格式
     * @return 回复详情
     */
    @POST(Url.REPLIES)
    @FormUrlEncoded
    Flowable<Reply> newReply(@Path("type") String type, @Path("id") int id, @Field("body") String body);

    /**
     * 获取回复列表
     *
     * @param type   feed 的 type
     * @param id     feed 的 id
     * @param offset 偏移数值 默认 0
     * @param limit  数量极限，默认值 20，值范围 1...150
     * @return 回复列表
     */
    @GET(Url.REPLIES)
    Flowable<List<Reply>> fetchRepliesList(@Path("type") String type, @Path("id") int id,
                                           @Query("offset") Integer offset, @Query("limit") Integer limit);

    /**
     * 用户回复列表
     *
     * @param loginName 登录用户名(非昵称)
     * @param order     类型 默认 recent，可选["recent"]
     * @param offset    偏移数值，默认值 0
     * @param limit     数量极限，默认值 20，值范围 1..150
     * @return 话题列表
     */
    @GET(Url.USER_REPLIES)
    Flowable<List<Reply>> fetchUserRepliesList(@Path("login") String loginName, @Query("order") String order,
                                               @Query("offset") Integer offset, @Query("limit") Integer limit);

    //--------------------------------------------------------------------------------------------//

    /**
     * 获取用户创建的话题列表
     *
     * @param loginName 登录用户名(非昵称)
     * @param order     类型 默认 recent，可选["recent", "likes", "replies"]
     * @param offset    偏移数值，默认值 0
     * @param limit     数量极限，默认值 20，值范围 1..150
     * @return 话题列表
     */
    @GET(Url.USER_TOPICS)
    Flowable<List<Topic>> fetchUserTopicsList(@Path("login") String loginName, @Query("order") String order,
                                              @Query("offset") Integer offset, @Query("limit") Integer limit);

    /**
     * 获取用户收藏的话题列表
     *
     * @param loginName 登录用户名(非昵称)
     * @param offset    偏移数值，默认值 0
     * @param limit     数量极限，默认值 20，值范围 1..150
     * @return 话题列表
     */
    @GET(Url.USER_FAVORITES)
    Flowable<List<Topic>> fetchUserFavoritesList(@Path("login") String loginName, @Query("offset") Integer offset,
                                                 @Query("limit") Integer limit);

    //-------------------------------------------- like ------------------------------------------//

    /**
     * 赞
     *
     * @param objType ["topic", "reply", "news"]
     * @param objId   id
     * @return 是否成功
     */
    @POST(Url.LIKES)
    @FormUrlEncoded
    Flowable<Like> like(@Field("obj_type") String objType, @Field("obj_id") Integer objId);

    /**
     * 取消赞
     *
     * @param objType ["topic", "reply", "news"]
     * @param objId   id
     * @return 是否成功
     */
    @DELETE(Url.LIKES)
    Flowable<Like> unlike(@Query("obj_type") String objType, @Query("obj_id") Integer objId);

    //--------------------------------------------------------------------------------------------//

    /**
     * 获取通知列表
     *
     * @param offset 偏移数值，默认值 0
     * @param limit  数量极限，默认值 20，值范围 1..150
     * @return 通知列表
     */
    @GET(Url.NOTIFICATIONS)
    Flowable<List<Notification>> fetchNotificationsList(@Query("offset") Integer offset,
                                                        @Query("limit") Integer limit);
    /**
     * 获取当前登录者的详细资料
     *
     * @return 用户详情
     */
    @GET(Url.USER_ME)
    Flowable<User> fetchMe();

    /**
     * 获取用户详细资料
     *
     * @param loginName 登录用户名(非昵称)
     * @return 用户详情
     */
    @GET(Url.USER)
    Flowable<User> fetchUser(@Path("login") String loginName);

    /**
     * 获取节点列表
     */
    @GET(Url.NODES)
    Flowable<List<Node>> fetchNodes();

    /**
     * 获取 News 节点列表
     */
    @GET(Url.NEWS_NODES)
    Flowable<List<NewsNode>> fetchNewsNodes();

    /**
     * 上传图片
     * @param file 图片文件
     * @return 图片链接
     */
    @Multipart
    @POST(Url.PHOTOS)
    Flowable<Photo> uploadPhoto(@Part MultipartBody.Part file);

    /**
     * 获取 酷站 列表
     * @return 列表
     */
    @GET(Url.SITES)
    Flowable<List<Sites>> fetchSites();
}
