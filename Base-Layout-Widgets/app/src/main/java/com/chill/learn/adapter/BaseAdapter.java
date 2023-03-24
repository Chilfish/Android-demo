package com.chill.learn.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  protected List<T> mData;
  private OnItemClickListener mOnItemClickListener;

  public BaseAdapter(List<T> data) {
    mData = data;
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }

  @Override
  public int getItemViewType(int position) {
    return getViewType(position);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
    return getViewHolder(view, viewType);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    bindData(holder, mData.get(position), position);
    holder.itemView.setOnClickListener(v -> {
      if (mOnItemClickListener != null) {
        mOnItemClickListener.onItemClick(position);
      }
    });
  }

  @SuppressLint("NotifyDataSetChanged")
  public void setData(List<T> data) {
    mData = data;
    notifyDataSetChanged();
  }

  public void addData(T data) {
    mData.add(data);
    notifyItemInserted(mData.size() - 1);
  }

  @SuppressLint("NotifyDataSetChanged")
  public void clearData() {
    mData.clear();
    notifyDataSetChanged();
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    mOnItemClickListener = listener;
  }

  protected abstract int getViewType(int position);

  protected abstract int getLayoutId(int viewType);

  protected abstract RecyclerView.ViewHolder getViewHolder(View view, int viewType);

  protected abstract void bindData(RecyclerView.ViewHolder holder, T data, int position);

  public interface OnItemClickListener {
    void onItemClick(int position);
  }
}
