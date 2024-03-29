package com.chill.learn.adapter;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<T> mDataList;
  private OnItemClickListener<T> mOnItemClickListener;

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

  protected abstract int getLayoutId();

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder Holder, int position) {
    ViewHolder holder = (ViewHolder) Holder;
    T chatItem = getItem(position);
    holder.bindData(chatItem);

    holder.itemView.setOnClickListener(v -> {
      if (mOnItemClickListener != null) {
        mOnItemClickListener.onItemClick(chatItem);
      }
    });
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
    }

    public void bindData(T data) {
    }
  }

  public void setOnItemClickListener(OnItemClickListener<T> listener) {
    mOnItemClickListener = listener;
  }

  public interface OnItemClickListener<T> {
    void onItemClick(T item);
  }
}
