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

/**
 * Created by SamuelGjk on 2017/3/26.
 */

public class HtmlHelper {

    public static String removeP(String html) {
        String result = html;
        if (result.contains("<p>") && result.contains("</p>")) {
            result = result.replace("<p>", "");
            result = result.replace("</p>", "<br>");
            if (result.endsWith("<br>")) {
                result = result.substring(0, result.length() - 4);
            }
        }
        return result;
    }
}
