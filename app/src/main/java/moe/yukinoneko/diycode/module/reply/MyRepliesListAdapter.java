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

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.Reply;
import moe.yukinoneko.diycode.list.BaseRecyclerListAdapter;
import moe.yukinoneko.diycode.module.topic.details.TopicDetailsActivity;
import moe.yukinoneko.diycode.tool.TimeHelper;

import static moe.yukinoneko.diycode.tool.ReplyHelper.convertReplyContent;

/**
 * Created by SamuelGjk on 2017/3/27.
 */

public class MyRepliesListAdapter extends BaseRecyclerListAdapter<Reply, MyRepliesListAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_reply, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.reply = data.get(position);
        holder.textContent.setText(convertReplyContent(holder.reply.bodyHtml, holder.textContent));
        holder.textPostTitle.setText(holder.reply.feedTitle);
        holder.textUpdatedAt.setText(TimeHelper.format(holder.reply.updatedAt));
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_content) AppCompatTextView textContent;
        @BindView(R.id.text_post_title) AppCompatTextView textPostTitle;
        @BindView(R.id.text_updated_at) AppCompatTextView textUpdatedAt;

        Reply reply;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            textPostTitle.setOnClickListener(this);
            textContent.setMovementMethod(LinkMovementMethod.getInstance());
        }

        @Override
        public void onClick(View v) {
            TopicDetailsActivity.launch(v.getContext(), reply.feedId);
        }
    }
}
