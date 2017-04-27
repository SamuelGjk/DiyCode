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

package moe.yukinoneko.diycode.tool;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by SamuelGjk on 2017/3/25.
 */

public class UrlHelper {

    /**
     * 获取 url 的 host
     */
    public static String getHost(String urlString) {
        String result = urlString;
        try {
            URL url = new URL(urlString);
            result = url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 判断后缀是不是图片类型的
     */
    public static boolean isImageSuffix(String url) {
        return url.endsWith(".png")
                || url.endsWith(".PNG")
                || url.endsWith(".jpg")
                || url.endsWith(".JPG")
                || url.endsWith(".jpeg")
                || url.endsWith(".JPEG");
    }

    /**
     * 判断后缀是不是 GIF
     */
    public static boolean isGifSuffix(String url) {
        return url.endsWith(".gif")
                || url.endsWith(".GIF");
    }

    /**
     * 获取 mimeType
     */
    public static String getMimeType(String url) {
        if (url.endsWith(".png") || url.endsWith(".PNG")) {
            return "data:image/png;base64,";
        } else if (url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".JPG") || url.endsWith(".JPEG")) {
            return "data:image/jpg;base64,";
        } else if (url.endsWith(".gif") || url.endsWith(".GIF")) {
            return "data:image/gif;base64,";
        } else {
            return "";
        }
    }
}
