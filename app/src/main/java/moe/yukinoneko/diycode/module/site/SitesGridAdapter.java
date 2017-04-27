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

package moe.yukinoneko.diycode.module.site;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.bean.Sites;
import moe.yukinoneko.diycode.list.BaseRecyclerListAdapter;
import moe.yukinoneko.diycode.tool.ImageLoadHelper;

/**
 * Created by SamuelGjk on 2017/4/27.
 */

public class SitesGridAdapter extends BaseRecyclerListAdapter<Object, RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_CATEGORY = 10000;
    private static final int VIEW_TYPE_SITE = 10001;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (VIEW_TYPE_CATEGORY == viewType) {
            return new SitesViewHolder(inflater.inflate(R.layout.item_sites, parent, false));
        } else {
            return new SiteViewHolder(inflater.inflate(R.layout.item_site, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SitesViewHolder) {
            ((SitesViewHolder) holder).sitesCategory.setText(((Sites) data.get(position)).name);
        } else {
            SiteViewHolder siteViewHolder = (SiteViewHolder) holder;
            Sites.Site site = (Sites.Site) data.get(position);
            siteViewHolder.url = site.url;
            ImageLoadHelper.loadImage(
                    siteViewHolder.itemView.getContext(),
                    site.avatarUrl,
                    siteViewHolder.imageSiteIcon,
                    null
            );
            siteViewHolder.textSiteName.setText(site.name);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position) instanceof Sites ? VIEW_TYPE_CATEGORY : VIEW_TYPE_SITE;
    }

    class SitesViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView sitesCategory;

        public SitesViewHolder(View itemView) {
            super(itemView);

            sitesCategory = (AppCompatTextView) itemView;
        }
    }

    class SiteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image_site_icon) AppCompatImageView imageSiteIcon;
        @BindView(R.id.text_site_name) AppCompatTextView textSiteName;

        String url;

        public SiteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            v.getContext().startActivity(intent);
        }
    }
}
