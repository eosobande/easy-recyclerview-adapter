package com.eosobande.easyrecycleradapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class EasyRecyclerAdapter<Data, Holder extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<Holder> {

    private ArrayList<Data> mDataSet = new ArrayList<>(),
            mDataSetCopy = new ArrayList<>();

    protected abstract int getLayoutRes();

    public final void notifyCustomDataSetChanged() {
        mDataSetCopy = new ArrayList<>(mDataSet);
        notifyDataSetChanged();
    }

    public final void filter(String filter) {

        filter = filter.toLowerCase();
        if (filter.trim().isEmpty()) {
            mDataSet = new ArrayList<>(mDataSetCopy);
        } else {
            mDataSet = new ArrayList<>();
            for (Data data : mDataSetCopy) {
                if (onFilterCondition(data, filter)) {
                    mDataSet.add(data);
                }
            }
        }
        notifyDataSetChanged();

    }

    protected abstract boolean onFilterCondition(Data data, String filter);

    public final void add(Data item) {
        mDataSet.add(item);
        notifyCustomDataSetChanged();
    }

    public final void add(Data item, int position) {
        mDataSet.add(position, item);
        notifyCustomDataSetChanged();
    }

    public final void addAll(List<Data> itemList) {
        mDataSet.addAll(itemList);
        notifyCustomDataSetChanged();
    }

    public final void replaceAll(List<Data> itemList) {
        mDataSet.clear();
        addAll(itemList);
        notifyCustomDataSetChanged();
    }

    public final void replace(int position, Data item) {
        mDataSet.set(position, item);
        notifyCustomDataSetChanged();
    }

    public final void remove(int item) {
        mDataSet.remove(item);
        notifyCustomDataSetChanged();
    }

    public final Data getItem(int position) {
        return mDataSet.get(position);
    }

    final Data getItemCopy(int position) {
        return mDataSetCopy.get(position);
    }

    // override to implement on item click
    public abstract void onItemClick(View view, Data data, int position);

    // display the content item here
    protected abstract void displayContent(Holder holder, Data data, int position);

    public final Data getDataInstance(int position) {
        return getItem(position);
    }

    protected final View inflate(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(getLayoutRes(), parent,false);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        displayContent(holder, getItem(position), position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public int getItemCopyCount() {
        return mDataSetCopy.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
