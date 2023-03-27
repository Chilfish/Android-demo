// a recyclerView adapter for the activities list

package com.chill.learn.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
    return new CardViewHolder(view);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.item_activity;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ActivityCard card = this.card.get(position);
    ((CardViewHolder) holder).bindData(card);

    holder.itemView.setOnClickListener(v -> {
      if (cardListener != null) {
        cardListener.onCardClick(card);
      }
    });
  }

  public static class CardViewHolder extends RecyclerView.ViewHolder {
    private TextView title;

    public CardViewHolder(View itemView) {
      super(itemView);
      title = itemView.findViewById(R.id.activity_name);
    }

    public void bindData(ActivityCard data) {
      title.setText(data.getTitle());
    }
  }

  public void setOnCardClickListener(OnCardClickListener listener) {
    cardListener = listener;
  }

  public interface OnCardClickListener {
    void onCardClick(ActivityCard card);
  }
}