package moe.yukinoneko.diycode.module.about;


import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.webkit.WebView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.mvp.MVPBaseActivity;
import moe.yukinoneko.diycode.tool.Tools;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AboutActivity extends MVPBaseActivity<AboutContract.View, AboutPresenter> implements AboutContract.View {

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
