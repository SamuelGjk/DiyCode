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

import android.widget.EditText;

/**
 * Created by SamuelGjk on 2017/4/26.
 */

public class MarkdownHelper {

    public static void insertPhoto(EditText editText, String url) {
        String source = editText.getText().toString();
        int selectionStart = editText.getSelectionStart();
        boolean hasNewLine = hasNewLine(source, selectionStart);
        String result = String.format("%s![](%s)\n", hasNewLine ? "" : "\n", url);
        editText.getText().insert(selectionStart, result);
        editText.setSelection(result.length() + selectionStart);
    }

    private static boolean hasNewLine(String source, int selectionStart) {
        try {
            if (source.isEmpty()) return true;
            source = source.substring(0, selectionStart);
            return source.charAt(source.length() - 1) == 10;
        } catch (StringIndexOutOfBoundsException e) {
            return true;
        }
    }
}
