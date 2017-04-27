package moe.yukinoneko.diycode.module.news.create;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.kennyc.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.NewsNode;
import moe.yukinoneko.diycode.mvp.MVPBaseActivity;

import static moe.yukinoneko.diycode.tool.StringHelper.trim;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateNewsActivity extends MVPBaseActivity<CreateNewsContract.View, CreateNewsPresenter> implements CreateNewsContract.View {

    @BindView(R.id.spinner_node) AppCompatSpinner spinnerNode;
    @BindView(R.id.edit_title) AppCompatEditText editTitle;
    @BindView(R.id.edit_link) AppCompatEditText editLink;
    @BindView(R.id.multi_state_view) MultiStateView multiStateView;

    private ProgressDialog progressDialog;

    private List<NewsNode> nodes;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_create_news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("请稍后...");

        presenter.fetchNewsNodes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            presenter.newNews(nodes.get(spinnerNode.getSelectedItemPosition()).id);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateNodes(List<NewsNode> nodes) {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        this.nodes = nodes;

        List<String> nodeNames = new ArrayList<>();
        for (NewsNode node : nodes) {
            nodeNames.add(node.name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nodeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNode.setAdapter(adapter);
    }

    @Override
    public String getNewsTitle() {
        return trim(editTitle.getText());
    }

    @Override
    public String getLink() {
        return trim(editLink.getText());
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
    public void linkError() {
        editLink.setError(getString(R.string.link_non_null));
    }

    @Override
    public void createSuccessful() {
        finish();
    }
}
