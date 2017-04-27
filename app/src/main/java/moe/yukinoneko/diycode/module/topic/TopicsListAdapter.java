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

package moe.yukinoneko.diycode.module.topic;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.Topic;
import moe.yukinoneko.diycode.list.BaseRecyclerListAdapter;
import moe.yukinoneko.diycode.module.topic.details.TopicDetailsActivity;
import moe.yukinoneko.diycode.module.user.UserActivity;
import moe.yukinoneko.diycode.tool.ImageLoadHelper;
import moe.yukinoneko.diycode.tool.TimeHelper;

/**
 * Created by SamuelGjk on 2017/3/23.
 */

public class TopicsListAdapter extends BaseRecyclerListAdapter<Topic, TopicsListAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.topic = data.get(position);
        ImageLoadHelper.loadAvatar(
                holder.itemView.getContext(),
                holder.topic.user.avatarUrl,
                holder.imageUserAvatar
        );
        holder.textName.setText(holder.topic.user.login);
        holder.textNodeName.setText(holder.topic.nodeName);
        holder.textUpdatedAt.setText(TimeHelper.format(holder.topic.updatedAt));
        holder.textTitle.setText(holder.topic.title);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_user_avatar) RoundedImageView imageUserAvatar;
        @BindView(R.id.text_name) AppCompatTextView textName;
        @BindView(R.id.text_node_name) AppCompatTextView textNodeName;
        @BindView(R.id.text_updated_at) AppCompatTextView textUpdatedAt;
        @BindView(R.id.text_title) AppCompatTextView textTitle;

        Topic topic;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            imageUserAvatar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_user_avatar:
                    UserActivity.launch(v.getContext(), topic.user.login);
                    break;

                default:
                    TopicDetailsActivity.launch(v.getContext(), topic.id);
                    break;
            }
        }
    }
}
