package moe.yukinoneko.diycode.module.markdown;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mittsu.markedview.MarkedView;

import butterknife.BindView;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MarkdownActivity extends MVPBaseActivity<MarkdownContract.View, MarkdownPresenter> implements MarkdownContract.View {
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
