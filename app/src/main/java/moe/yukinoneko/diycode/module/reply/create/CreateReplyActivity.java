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

package moe.yukinoneko.diycode.module.reply.create;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.mvp.MVPBaseActivity;

import static moe.yukinoneko.diycode.tool.StringHelper.trim;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateReplyActivity extends MVPBaseActivity<CreateReplyContract.View, CreateReplyPresenter> implements CreateReplyContract.View {

    private static final String EXTRA_PREFIX = "extra_prefix";
    private static final String EXTRA_FEED_TYPE = "extra_feed_type";
    private static final String EXTRA_FEED_ID = "extra_feed_id";

    @BindView(R.id.edit_content) AppCompatEditText editContent;

    private ProgressDialog progressDialog;

    public static void launch(Context context, String prefix, String feedType, int feedId) {
        Intent intent = new Intent(context, CreateReplyActivity.class);
        intent.putExtra(EXTRA_PREFIX, prefix);
        intent.putExtra(EXTRA_FEED_TYPE, feedType);
        intent.putExtra(EXTRA_FEED_ID, feedId);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_create_reply;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("请稍后...");

        String prefix = getIntent().getStringExtra(EXTRA_PREFIX);
        editContent.setText(prefix);
        editContent.setSelection(prefix.length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            Intent intent = getIntent();
            presenter.newReply(
                    intent.getStringExtra(EXTRA_FEED_TYPE),
                    intent.getIntExtra(EXTRA_FEED_ID, -1)
            );
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getContent() {
        return trim(editContent.getText());
    }

    @Override
    public void contentError() {
        editContent.setError(getString(R.string.content_non_null));
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void createSuccessful() {
        finish();
    }
}
