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

package moe.yukinoneko.diycode.module.about;


import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.webkit.WebView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.base.BaseActivity;
import moe.yukinoneko.diycode.tool.Tools;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.text_app_version) AppCompatTextView textAppVersion;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textAppVersion.setText(String.format(Locale.getDefault(), "Version %s", Tools.getVersionName(this)));
    }

    @OnClick(R.id.card_open_source_licenses)
    public void onViewClicked() {
        WebView v = new WebView(this);
        v.loadUrl("file:///android_asset/licenses.html");

        new AlertDialog.Builder(this)
                .setView(v)
                .setNegativeButton(R.string.close, null)
                .show();
    }
}
