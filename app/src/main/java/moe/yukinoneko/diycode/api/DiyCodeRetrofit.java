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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import moe.yukinoneko.diycode.bean.Token;
import moe.yukinoneko.diycode.tool.UserHelper;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by SamuelGjk on 2017/3/23.
 */

public class DiyCodeRetrofit {

    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

    private Retrofit retrofit;
    private DiyCodeService service;

    private static class HolderClass {
        private static final DiyCodeRetrofit INSTANCE = new DiyCodeRetrofit();
    }

    public static DiyCodeService api() {
        return HolderClass.INSTANCE.service;
    }

    private DiyCodeRetrofit() {

        // 为所有请求自动添加 token
        Interceptor tokenInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                // 如果当前没有缓存 token 或者请求已经附带 token 了，就不再添加
                if (!UserHelper.isLogin() || alreadyHasAuthorizationHeader(originalRequest)) {
                    return chain.proceed(originalRequest);
                }
                String token = OAuth.TOKEN_PREFIX + UserHelper.getToken().accessToken;
                // 为请求附加 token
                Request authorised = originalRequest.newBuilder()
                                                    .header(OAuth.KEY_TOKEN, token)
                                                    .build();
                return chain.proceed(authorised);
            }
        };

        // 自动刷新 token
        Authenticator authenticator = new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) {
                TokenService tokenService = retrofit.create(TokenService.class);
                String accessToken = "";
                try {
                    if (UserHelper.isLogin()) {
                        Call<Token> call = tokenService.refreshToken(OAuth.client_id,
                                OAuth.client_secret, OAuth.GRANT_TYPE_REFRESH,
                                UserHelper.getToken().refreshToken);
                        retrofit2.Response<Token> tokenResponse = call.execute();
                        Token token = tokenResponse.body();
                        if (null != token) {
                            UserHelper.saveToken(token);
                            accessToken = token.accessToken;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return response.request().newBuilder()
                               .addHeader(OAuth.KEY_TOKEN, OAuth.TOKEN_PREFIX + accessToken)
                               .build();
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)            // 是否重试
                .connectTimeout(10, TimeUnit.SECONDS)      // 连接超时事件
                .addNetworkInterceptor(tokenInterceptor)   // 自动附加 token
                .authenticator(authenticator)              // 认证失败自动刷新token
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        service = retrofit.create(DiyCodeService.class);
    }

    private boolean alreadyHasAuthorizationHeader(Request originalRequest) {
        String token = originalRequest.header(OAuth.KEY_TOKEN);
        // 如果本身是请求 token 的 URL，直接返回 true
        // 如果不是，则判断 header 中是否已经添加过 Authorization 这个字段，以及是否为空
        return !(null == token || token.isEmpty() || originalRequest.url().toString().contains(Url.OAUTH_URL));
    }

    interface TokenService {
        /**
         * 刷新 token
         */
        @POST(Url.OAUTH_URL)
        @FormUrlEncoded
        Call<Token> refreshToken(@Field("client_id") String client_id, @Field("client_secret") String client_secret,
                                 @Field("grant_type") String grant_type, @Field("refresh_token") String refresh_token);
    }
}
