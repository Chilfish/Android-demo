package com.chill.learn.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chill.learn.R;
import com.chill.learn.adapter.ActivitiesAdapter;
import com.chill.learn.entity.ActivityCard;

import java.util.List;

public class MainActivity extends AppCompatActivity {
  private RecyclerView activitiesView;
  private ActivitiesAdapter adapter;
  private ActivityCard[] activityCards;

  void init() {
    activityCards = new ActivityCard[]{
        new ActivityCard("MD3", MD3Activity.class),
        new ActivityCard("Product List", ProductListActivity.class),
        new ActivityCard("Login", LoginActivity.class),
    };
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init();

    activitiesView = findViewById(R.id.activities);
    activitiesView.setLayoutManager(new LinearLayoutManager(this));

    adapter = new ActivitiesAdapter(List.of(activityCards));
    adapter.setOnCardClickListener(card -> {
      startActivity(new Intent(this, card.getActivity()));
    });

    activitiesView.setAdapter(adapter);
  }

}
