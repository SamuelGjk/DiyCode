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

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

/**
 * Created by SamuelGjk on 2017/3/23.
 */

public class TimeHelper {

    private static PrettyTime prettyTime = new PrettyTime();

    public static String format(Date date) {
        return prettyTime.format(date).replace(" 前", "前");
    }
}
