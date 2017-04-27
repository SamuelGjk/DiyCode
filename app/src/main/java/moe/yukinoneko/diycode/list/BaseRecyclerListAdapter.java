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

package moe.yukinoneko.diycode.list;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by SamuelGjk on 2017/3/22.
 * <p>
 * 参考 dinuscxj/RecyclerRefreshLayout @{https://github.com/dinuscxj/RecyclerRefreshLayout/blob/master/app/src/main/java/com/dinuscxj/example/adapter/BaseRecyclerListAdapter.java}
 */

public abstract class BaseRecyclerListAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> data = new ArrayList<>();

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<T> getData() {
        return data;
    }

    @UiThread
    public void setData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);

        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return data.get(position);
    }

    @UiThread
    public BaseRecyclerListAdapter<T, VH> add(@NonNull T item) {
        data.add(item);
        notifyItemInserted(data.size() - 1);
        return this;
    }

    @UiThread
    public BaseRecyclerListAdapter<T, VH> addAll(@NonNull Collection<T> items) {
        data.addAll(items);
        notifyItemRangeInserted(data.size() - items.size(), items.size());
        return this;
    }

    @UiThread
    public BaseRecyclerListAdapter<T, VH> add(int position, @NonNull T item) {
        data.add(position, item);
        notifyItemInserted(position);
        return this;
    }

    @UiThread
    public BaseRecyclerListAdapter<T, VH> remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        return this;
    }

    @UiThread
    public BaseRecyclerListAdapter<T, VH> remove(@NonNull T item) {
        return remove(data.indexOf(item));
    }

    @UiThread
    public BaseRecyclerListAdapter<T, VH> clear() {
        data.clear();
        notifyDataSetChanged();
        return this;
    }

    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

}
