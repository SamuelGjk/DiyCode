package moe.yukinoneko.diycode.module.news.details;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.kennyc.view.MultiStateView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Locale;

import butterknife.BindView;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.News;
import moe.yukinoneko.diycode.module.reply.RepliesFragment;
import moe.yukinoneko.diycode.mvp.MVPBaseActivity;
import moe.yukinoneko.diycode.tool.ImageLoadHelper;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NewsDetailsActivity extends MVPBaseActivity<NewsDetailsContract.View, NewsDetailsPresenter> implements NewsDetailsContract.View {

    private static final String EXTRA_NEWS = "extra_news";

    @BindView(R.id.image_user_avatar) RoundedImageView imageUserAvatar;
    @BindView(R.id.appbar_title) AppCompatTextView appbarTitle;
    @BindView(R.id.post_content) WebView postContent;
    @BindView(R.id.sliding_layout) SlidingUpPanelLayout slidingLayout;
    @BindView(R.id.post_footer) LinearLayout postFooter;
    @BindView(R.id.multi_state_view) MultiStateView multiStateView;
    @BindView(R.id.replies_title) AppCompatTextView repliesTitle;
    @BindView(R.id.fab) FloatingActionButton fab;

    public static void launch(Context context, News news) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_NEWS, news);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_post;
    }

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebSettings settings = postContent.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultFontSize(14);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(false);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        postContent.setWebViewClient(new WebClient());

        News news = getIntent().getParcelableExtra(EXTRA_NEWS);

        postFooter.setVisibility(View.GONE);
        fab.setVisibility(View.INVISIBLE);
        ImageLoadHelper.loadAvatar(this, news.user.avatarUrl, imageUserAvatar);
        appbarTitle.setText(String.format(Locale.getDefault(), "%s  ·  %s", news.user.login, news.title));
        appbarTitle.setSelected(true);
        repliesTitle.setText(String.format(Locale.getDefault(), "回复 (%d)", news.repliesCount));

        postContent.loadUrl(news.address);

        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.replies_container, RepliesFragment.newInstance("news", news.id))
                                   .commit();
    }

    @Override
    public void onBackPressed() {
        if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        WebSettings settings = postContent.getSettings();
        settings.setJavaScriptEnabled(false);
        postContent.destroy();

        super.onDestroy();
    }

    private class WebClient extends WebViewClient {

        @SuppressWarnings("deprecation")
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }
}
