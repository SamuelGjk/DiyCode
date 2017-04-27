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

package moe.yukinoneko.diycode.module.notification;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.Notification;
import moe.yukinoneko.diycode.bean.User;
import moe.yukinoneko.diycode.list.BaseRecyclerListAdapter;
import moe.yukinoneko.diycode.module.topic.details.TopicDetailsActivity;
import moe.yukinoneko.diycode.module.user.UserActivity;
import moe.yukinoneko.diycode.tool.ImageLoadHelper;
import moe.yukinoneko.diycode.tool.TimeHelper;

import static moe.yukinoneko.diycode.tool.ReplyHelper.convertReplyContent;

/**
 * Created by SamuelGjk on 2017/3/31.
 *
 * See https://github.com/GcsSloop/diycode/blob/master/diycode-app/src/main/java/com/gcssloop/diycode/adapter/NotificationAdapter.java
 */

public class NotificationsListAdapter extends BaseRecyclerListAdapter<Notification, NotificationsListAdapter.ViewHolder> {

    private static String TYPE_NODE_CHANGED = "NodeChanged";             // 节点变更
    private static String TYPE_TOPIC_REPLY = "TopicReply";               // Topic 回复
    private static String TYPE_NEWS_REPLY = "Hacknews";                  // News  回复
    private static String TYPE_MENTION = "Mention";                      // 有人提及
    private static String MENTION_TYPE_TOPIC_REPLY = "Reply";            // - Topic 回复中提及
    private static String MENTION_TYPE_NEWS_REPLY = "HacknewsReply";     // - News  回复中提及
    private static String MENTION_TYPE_PROJECT_REPLY = "ProjectReply";   // - 项目   回复中提及

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notification notification = data.get(position);
        holder.actor = notification.actor;
        ImageLoadHelper.loadAvatar(
                holder.itemView.getContext(),
                notification.actor.avatarUrl,
                holder.imageUserAvatar
        );
        String title = "";
        String content = "";
        if (notification.type.equals(TYPE_TOPIC_REPLY)) {
            title = String.format(
                    Locale.getDefault(),
                    "%s 回复了话题：%s",
                    notification.actor.login,
                    notification.reply.feedTitle);

            content = notification.reply.bodyHtml;
            holder.topicId = notification.reply.feedId;
        } else if (notification.type.equals(TYPE_MENTION) &&
                notification.mentionType.equals(MENTION_TYPE_TOPIC_REPLY)) {
            title = String.format(Locale.getDefault(), "%s 提到了你：", notification.actor.login);
            content = notification.mention.bodyHtml;
            holder.topicId = notification.mention.feedId;
        }
        holder.textTitle.setText(title);
        holder.textFloorAndDate.setText(TimeHelper.format(notification.updatedAt));
        holder.textContent.setText(convertReplyContent(content, holder.textContent));
    }

    @Override
    public void setData(List<Notification> data) {
        super.setData(filter(data));
    }

    @Override
    public BaseRecyclerListAdapter<Notification, ViewHolder> addAll(@NonNull Collection<Notification> items) {
        return super.addAll(filter(items));
    }

    /**
     * 清洗数据，主要清洗对象
     * 1. HackNew 的回复 type = Hacknews
     * 2. HackNew 的提及 type = Mention, mention_type = HacknewsReply
     * 3. Project 的提及 type = Mention, mention_type = ProjectReply
     * <p>
     * 保留数据
     * 1. Topic 的回复 type = TopicReply
     * 2. Topic 的提及 type = Mention, mention_type = Reply
     */
    private List<Notification> filter(Collection<Notification> notifications) {
        List<Notification> data = new ArrayList<>();
        for (Notification n : notifications) {
            if (n.type.equals(TYPE_TOPIC_REPLY) ||
                    (n.type.equals(TYPE_MENTION) && n.mentionType.equals(MENTION_TYPE_TOPIC_REPLY))) {
                data.add(n);
            }
        }
        return data;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_user_avatar) RoundedImageView imageUserAvatar;
        @BindView(R.id.text_name) AppCompatTextView textTitle;
        @BindView(R.id.button_reply) AppCompatImageButton buttonReply;
        @BindView(R.id.text_floor_and_date) AppCompatTextView textFloorAndDate;
        @BindView(R.id.text_content) AppCompatTextView textContent;

        int topicId;
        User actor;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            buttonReply.setVisibility(View.GONE);

            itemView.setOnClickListener(this);
            imageUserAvatar.setOnClickListener(this);

            textContent.setMovementMethod(LinkMovementMethod.getInstance());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_user_avatar:
                    UserActivity.launch(v.getContext(), actor.login);
                    break;

                default:
                    TopicDetailsActivity.launch(v.getContext(), topicId);
                    break;
            }
        }
    }
}
