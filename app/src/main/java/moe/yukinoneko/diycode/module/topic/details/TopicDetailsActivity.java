package moe.yukinoneko.diycode.module.topic.details;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.kennyc.view.MultiStateView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.Like;
import moe.yukinoneko.diycode.bean.Topic;
import moe.yukinoneko.diycode.event.LoginEvent;
import moe.yukinoneko.diycode.misc.DiyCodeWebViewClient;
import moe.yukinoneko.diycode.misc.OnWebImageClickListener;
import moe.yukinoneko.diycode.module.image.ImageActivity;
import moe.yukinoneko.diycode.module.login.LoginActivity;
import moe.yukinoneko.diycode.module.reply.RepliesFragment;
import moe.yukinoneko.diycode.module.reply.create.CreateReplyActivity;
import moe.yukinoneko.diycode.module.web.WebActivity;
import moe.yukinoneko.diycode.mvp.MVPBaseActivity;
import moe.yukinoneko.diycode.tool.ImageLoadHelper;
import moe.yukinoneko.diycode.tool.UserHelper;

import static android.text.TextUtils.isEmpty;
import static com.kennyc.view.MultiStateView.VIEW_STATE_ERROR;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TopicDetailsActivity extends MVPBaseActivity<TopicDetailsContract.View, TopicDetailsPresenter> implements TopicDetailsContract.View {

    private static final String EXTRA_TOPIC_ID = "extra_topic_id";

    @BindView(R.id.multi_state_view) MultiStateView multiStateView;
    @BindView(R.id.image_user_avatar) RoundedImageView imageUserAvatar;
    @BindView(R.id.appbar_title) AppCompatTextView appbarTitle;
    @BindView(R.id.post_content) WebView postContent;
    @BindView(R.id.sliding_layout) SlidingUpPanelLayout slidingLayout;
    @BindView(R.id.button_like) AppCompatImageButton buttonLike;
    @BindView(R.id.text_like_count) AppCompatTextView textLikeCount;
    @BindView(R.id.button_favorite) AppCompatImageButton buttonFavorite;
    @BindView(R.id.replies_title) AppCompatTextView repliesTitle;

    private int topicId;
    private Topic topic;

    public static void launch(Context context, int topicId) {
        Intent intent = new Intent(context, TopicDetailsActivity.class);
        intent.putExtra(EXTRA_TOPIC_ID, topicId);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_post;
    }

    @Override
    @SuppressLint({ "SetJavaScriptEnabled", "AddJavascriptInterface" })
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDisplayShowTitleEnabled(false);

        WebSettings settings = postContent.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultFontSize(14);
        settings.setSupportZoom(false);
        postContent.setWebViewClient(new WebClient(this));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            postContent.addJavascriptInterface(new OnWebImageClickListener() {
                @JavascriptInterface
                @Override
                public void onClick(String imageUrl) {
                    if (!isEmpty(imageUrl)) {
                        ImageActivity.launch(TopicDetailsActivity.this, imageUrl);
                    }
                }
            }, "onWebImageClickListener");
        }

        View errorView = multiStateView.getView(VIEW_STATE_ERROR);
        if (errorView != null) {
            ButterKnife.findById(errorView, R.id.text_error)
                       .setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                               presenter.fetchTopic(topicId);
                           }
                       });
        }

        topicId = getIntent().getIntExtra(EXTRA_TOPIC_ID, -1);
        presenter.fetchTopic(topicId);

        getSupportFragmentManager().beginTransaction()
                                   .replace(
                                           R.id.replies_container,
                                           RepliesFragment.newInstance("topics", topicId)
                                   )
                                   .commit();
    }

    @OnClick({ R.id.button_like, R.id.button_favorite, R.id.fab })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_like:
                if (UserHelper.isLogin()) {
                    if (topic.liked != null) {
                        if (topic.liked) {
                            presenter.unlikeTopic(topic.id);
                        } else {
                            presenter.likeTopic(topic.id);
                        }
                    }
                } else {
                    LoginActivity.launch(getContext());
                }
                break;
            case R.id.button_favorite:
                if (UserHelper.isLogin()) {
                    if (topic.favorited != null) {
                        if (topic.favorited) {
                            presenter.unfavoriteTopic(topic.id);
                        } else {
                            presenter.favoriteTopic(topic.id);
                        }
                    }
                } else {
                    LoginActivity.launch(getContext());
                }
                break;
            case R.id.fab:
                if (UserHelper.isLogin()) {
                    CreateReplyActivity.launch(this, "", "topics", topic.id);
                } else {
                    LoginActivity.launch(getContext());
                }
                break;
        }
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
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        postContent.setWebViewClient(null);
        WebSettings settings = postContent.getSettings();
        settings.setJavaScriptEnabled(false);
        postContent.removeJavascriptInterface("onWebImageClickListener");
        postContent.destroy();

        super.onDestroy();
    }

    @Subscribe
    public void onLogin(LoginEvent event) {
        presenter.fetchTopic(topicId);
    }

    @Override
    public void updateTopicContent(Topic topic) {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

        this.topic = topic;
        ImageLoadHelper.loadAvatar(
                this,
                topic.user.avatarUrl,
                imageUserAvatar
        );
        appbarTitle.setText(String.format(Locale.getDefault(), "%s  ·  %s", topic.user.login, topic.title));
        appbarTitle.setSelected(true);
        postContent.loadDataWithBaseURL("", convertTopicContent(topic.bodyHtml), "text/html", "UTF-8", "");

        if (UserHelper.isLogin()) {
            buttonFavorite.setImageResource(topic.favorited ? R.drawable.ic_favorite : R.drawable.ic_unfavorite);
            buttonLike.setImageResource(topic.liked ? R.drawable.ic_like : R.drawable.ic_unlike);
        }

        textLikeCount.setText(topic.likesCount == 0 ? null : String.valueOf(topic.likesCount));
        repliesTitle.setText(String.format(Locale.getDefault(), "回复 (%d)", topic.repliesCount));
    }

    @Override
    public void updateFavoriteState(boolean favorited) {
        topic.favorited = favorited;
        buttonFavorite.setImageResource(favorited ? R.drawable.ic_favorite : R.drawable.ic_unfavorite);
    }

    @Override
    public void updateLikeState(boolean liked, Like like) {
        topic.liked = liked;
        buttonLike.setImageResource(liked ? R.drawable.ic_like : R.drawable.ic_unlike);
        textLikeCount.setText(like.count == 0 ? null : String.valueOf(like.count));
    }

    @Override
    public void error() {
        multiStateView.setViewState(VIEW_STATE_ERROR);
    }

    private static class WebClient extends DiyCodeWebViewClient {

        public WebClient(Context context) {
            super(context);
        }

        @SuppressWarnings("deprecation")
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) {
                WebActivity.launch(view.getContext(), url);
            }
            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            if (url != null) {
                WebActivity.launch(view.getContext(), url);
            }
            return true;
        }
    }

    private String convertTopicContent(String content) {
        if (isEmpty(content) || isEmpty(content.trim())) return "";

        // 过滤掉 img标签的width,height属性
        content = content.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
        content = content.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");

        // 添加点击查看大图
        content = content.replaceAll("<img[^>]+src=\"([^\"\'\\s]+)\"[^>]*>(?!((?!</?a\\b).)*</a>)",
                "<img src=\"$1\" onClick=\"javascript:onWebImageClickListener.onClick('$1')\"/>");

        // 过滤table的内部属性
        content = content.replaceAll("(<table[^>]*?)\\s+border\\s*=\\s*\\S+", "$1");
        content = content.replaceAll("(<table[^>]*?)\\s+cellspacing\\s*=\\s*\\S+", "$1");
        content = content.replaceAll("(<table[^>]*?)\\s+cellpadding\\s*=\\s*\\S+", "$1");

        return String.format("<!DOCTYPE html>"
                + "<html><head>"
                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/html/css/markdown.css\">"
                + "<link rel=\"stylesheet\" href=\"file:///android_asset/html/css/monokai.css\"/>"
                + "<script type=\"text/javascript\" src=\"file:///android_asset/html/js/highlight.pack.js\"></script>"
                + "<script>hljs.initHighlightingOnLoad();</script>"
                + "</head>"
                + "<body>"
                + "<div class=\"markdown\">"
                + "%s"
                + "</div>"
                + "</body></html>", content);
    }
}
