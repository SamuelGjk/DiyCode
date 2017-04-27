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

import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.widget.TextView;

import moe.yukinoneko.diycode.misc.GlideImageGetter;
import moe.yukinoneko.diycode.misc.ReplySpan;

/**
 * Created by SamuelGjk on 2017/3/31.
 */

public class ReplyHelper {

    public static CharSequence convertReplyContent(String content, TextView textView) {
        Spanned spanned = Html.fromHtml(
                HtmlHelper.removeP(content),
                new GlideImageGetter(textView.getContext(), textView), null);
        SpannableStringBuilder ssb = new SpannableStringBuilder(spanned);
        URLSpan[] urls = ssb.getSpans(0, ssb.length(), URLSpan.class);
        for (URLSpan urlSpan : urls) {
            int start = ssb.getSpanStart(urlSpan);
            int end = ssb.getSpanEnd(urlSpan);
            int flags = ssb.getSpanFlags(urlSpan);
            ssb.removeSpan(urlSpan);
            ssb.setSpan(new ReplySpan(urlSpan.getURL()), start, end, flags);
        }
        return ssb;
    }
}
