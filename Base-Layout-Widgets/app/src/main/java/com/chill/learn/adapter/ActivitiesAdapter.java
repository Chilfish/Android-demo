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

  public class CardViewHolder extends BaseAdapter<ActivityCard>.ViewHolder {
    private TextView title;

    public CardViewHolder(View itemView) {
      super(itemView);
      title = itemView.findViewById(R.id.activity_name);
    }

    @Override
    public void bindData(ActivityCard data) {
      title.setText(data.getTitle());
    }
  }
}