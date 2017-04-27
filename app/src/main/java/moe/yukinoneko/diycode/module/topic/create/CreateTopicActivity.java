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

package moe.yukinoneko.diycode.module.topic.create;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.Node;
import moe.yukinoneko.diycode.module.markdown.MarkdownActivity;
import moe.yukinoneko.diycode.mvp.MVPBaseActivity;
import moe.yukinoneko.diycode.tool.FileUtils;

import static moe.yukinoneko.diycode.tool.MarkdownHelper.insertPhoto;
import static moe.yukinoneko.diycode.tool.StringHelper.trim;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateTopicActivity extends MVPBaseActivity<CreateTopicContract.View, CreateTopicPresenter> implements CreateTopicContract.View {
    private static final int REQUEST_CODE_CHOOSE = 10000;

    @BindView(R.id.multi_state_view) MultiStateView multiStateView;
    @BindView(R.id.spinner_section) AppCompatSpinner spinnerSection;
    @BindView(R.id.spinner_node) AppCompatSpinner spinnerNode;
    @BindView(R.id.edit_title) AppCompatEditText editTitle;
    @BindView(R.id.edit_content) AppCompatEditText editContent;

    private ProgressDialog progressDialog;

    private Map<String, Integer> nodeIdMap;
    private Map<String, List<String>> nodeNameMap;

    private ArrayAdapter<String> nodesAdapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_create_topic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("请稍后...");

        nodeIdMap = new HashMap<>();
        nodeNameMap = new HashMap<>();

        nodesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        nodesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNode.setAdapter(nodesAdapter);

        presenter.fetchNodes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            presenter.newTopic();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnItemSelected(R.id.spinner_section)
    void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String key = ((TextView) view).getText().toString();
        nodesAdapter.clear();
        nodesAdapter.addAll(nodeNameMap.get(key));
    }

    @OnClick({ R.id.button_preview, R.id.button_pick_image })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_preview:
                MarkdownActivity.launch(this, editContent.getText().toString());
                break;
            case R.id.button_pick_image:
                Matisse.from(this)
                       .choose(MimeType.allOf())
                       .theme(R.style.Matisse_Dracula)
                       .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                       .imageEngine(new GlideEngine())
                       .forResult(REQUEST_CODE_CHOOSE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            presenter.uploadPhoto(FileUtils.getFile(this, Matisse.obtainResult(data).get(0)));
        }
    }

    @Override
    public void updateNodes(List<Node> nodes) {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        parseNodes(nodes);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                nodeNameMap.keySet().toArray(new String[nodeNameMap.keySet().size()])
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(adapter);
    }

    @Override
    public int getNodeId() {
        String key = spinnerNode.getSelectedItem().toString();
        return nodeIdMap.get(key);
    }

    @Override
    public String getTopicTitle() {
        return trim(editTitle.getText());
    }

    @Override
    public String getContent() {
        return trim(editContent.getText());
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
    public void titleError() {
        editTitle.setError(getString(R.string.title_non_null));
    }

    @Override
    public void contentError() {
        editContent.setError(getString(R.string.content_non_null));
    }

    @Override
    public void uploadSuccessful(String imageUrl) {
        insertPhoto(editContent, imageUrl);
    }

    @Override
    public void createSuccessful() {
        finish();
    }

    private void parseNodes(List<Node> nodes) {
        for (Node node : nodes) {
            nodeIdMap.put(node.name, node.id);
            String key = node.sectionName;
            if (nodeNameMap.containsKey(key)) {
                nodeNameMap.get(key).add(node.name);
            } else {
                List<String> temp = new ArrayList<>();
                temp.add(node.name);
                nodeNameMap.put(key, temp);
            }
        }
    }
}
