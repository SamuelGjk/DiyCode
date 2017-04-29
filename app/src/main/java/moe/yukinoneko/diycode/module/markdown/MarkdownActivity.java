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

package moe.yukinoneko.diycode.module.markdown;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mittsu.markedview.MarkedView;

import butterknife.BindView;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.base.BaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MarkdownActivity extends BaseActivity {
    private static final String EXTRA_MD_TEXT = "extra_md_text";

    @BindView(R.id.md_view) MarkedView mdView;

    public static void launch(Context context, String mdText) {
        Intent intent = new Intent(context, MarkdownActivity.class);
        intent.putExtra(EXTRA_MD_TEXT, mdText);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_markdown;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mdView.setMDText(getIntent().getStringExtra(EXTRA_MD_TEXT));
    }
}
