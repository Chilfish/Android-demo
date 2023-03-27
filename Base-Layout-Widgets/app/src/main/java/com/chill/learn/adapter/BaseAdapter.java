package com.chill.learn.adapter;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<T> mDataList;

  public BaseAdapter(List<T> dataList) {
    mDataList = dataList;
  }

  @SuppressLint("NotifyDataSetChanged")
  public void setDataList(List<T> dataList) {
    mDataList = dataList;
    notifyDataSetChanged();
  }

  public List<T> getDataList() {
    return mDataList;
  }

  public T getItem(int position) {
    if (mDataList == null || position < 0 || position >= mDataList.size()) {
      return null;
    }
    return mDataList.get(position);
  }

  @Override
  public int getItemCount() {
    return mDataList == null ? 0 : mDataList.size();
  }

  @Override
  public int getItemViewType(int position) {
    return super.getItemViewType(position);
  }

  @NonNull
  public abstract RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

  public abstract void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position);

  protected abstract int getLayoutId();
}
