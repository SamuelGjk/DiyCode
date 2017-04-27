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

package moe.yukinoneko.diycode.module.reply;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.Reply;
import moe.yukinoneko.diycode.list.BaseRecyclerListAdapter;
import moe.yukinoneko.diycode.module.login.LoginActivity;
import moe.yukinoneko.diycode.module.reply.create.CreateReplyActivity;
import moe.yukinoneko.diycode.module.user.UserActivity;
import moe.yukinoneko.diycode.tool.ImageLoadHelper;
import moe.yukinoneko.diycode.tool.TimeHelper;
import moe.yukinoneko.diycode.tool.UserHelper;

import static moe.yukinoneko.diycode.tool.ReplyHelper.convertReplyContent;

/**
 * Created by SamuelGjk on 2017/3/27.
 */

public class RepliesListAdapter extends BaseRecyclerListAdapter<Reply, RepliesListAdapter.ViewHolder> {

    private String feedType;

    public RepliesListAdapter(String feedType) {
        this.feedType = feedType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.reply = data.get(position);
        ImageLoadHelper.loadAvatar(
                holder.itemView.getContext(),
                holder.reply.user.avatarUrl,
                holder.imageUserAvatar
        );
        holder.textName.setText(holder.reply.user.login);
        holder.textFloorAndDate.setText(String.format(
                Locale.getDefault(),
                "%d楼  ·  %s",
                position + 1,
                TimeHelper.format(holder.reply.updatedAt)));
        holder.textContent.setText(convertReplyContent(holder.reply.bodyHtml, holder.textContent));
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_user_avatar) RoundedImageView imageUserAvatar;
        @BindView(R.id.text_name) AppCompatTextView textName;
        @BindView(R.id.button_reply) AppCompatImageButton buttonReply;
        @BindView(R.id.text_floor_and_date) AppCompatTextView textFloorAndDate;
        @BindView(R.id.text_content) AppCompatTextView textContent;

        Reply reply;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if ("news".equals(feedType)) {
                buttonReply.setVisibility(View.INVISIBLE);
            } else {
                buttonReply.setOnClickListener(this);
            }
            textContent.setMovementMethod(LinkMovementMethod.getInstance());
            imageUserAvatar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_user_avatar:
                    UserActivity.launch(v.getContext(), reply.user.login);
                    break;
                case R.id.button_reply:
                    if (UserHelper.isLogin()) {
                        CreateReplyActivity.launch(
                                v.getContext(),
                                String.format(
                                        Locale.getDefault(),
                                        "#%d楼 @%s ",
                                        getAdapterPosition() + 1,
                                        reply.user.login
                                ),
                                feedType,
                                reply.feedId
                        );
                    } else {
                        LoginActivity.launch(v.getContext());
                    }
                    break;
            }
        }
    }
}
