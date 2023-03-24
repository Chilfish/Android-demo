// a recyclerView adapter for the activities list

package com.chill.learn.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chill.learn.R;
import com.chill.learn.entity.ActivityCard;

import java.util.List;

public class ActivitiesAdapter extends BaseAdapter<ActivityCard> {
  private List<ActivityCard> card;
  private OnCardClickListener cardListener;

  public ActivitiesAdapter(List<ActivityCard> data) {
    super(data);
    this.card = data;
  }

  @Override
  protected int getViewType(int position) {
    return 0;
  }

  @Override
  protected int getLayoutId(int viewType) {
    return R.layout.item_activity;
  }

  @Override
  protected RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
    return new CardViewHolder(view);
  }

  @Override
  protected void bindData(RecyclerView.ViewHolder holder, ActivityCard data, int position) {
    CardViewHolder viewHolder = (CardViewHolder) holder;
    viewHolder.title.setText(data.getTitle());
  }

  public static class CardViewHolder extends RecyclerView.ViewHolder {
    private TextView title;

    public CardViewHolder(View itemView) {
      super(itemView);
      title = itemView.findViewById(R.id.activity_name);
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ActivityCard card = this.card.get(position);
    ((CardViewHolder) holder).title.setText(card.getTitle());
    holder.itemView.setOnClickListener(v -> {
      if (cardListener != null) {
        cardListener.onCardClick(card);
      }
    });
  }

  public void setOnCardClickListener(OnCardClickListener listener) {
    cardListener = listener;
  }

  public interface OnCardClickListener {
    void onCardClick(ActivityCard card);
  }
}