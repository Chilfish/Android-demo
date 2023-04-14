package com.chill.learn.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chill.learn.R;
import com.chill.learn.adapter.ActivitiesAdapter;
import com.chill.learn.entity.ActivityCard;
import com.chill.learn.helper.LoginCheck;

import java.util.List;

public class MainActivity extends AppCompatActivity {
  private RecyclerView activitiesView;
  private ActivitiesAdapter adapter;
  private ActivityCard[] activityCards;

  void init() {
    if (!LoginCheck.isLoggedIn(this)) {
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
      finish();
      return;
    }

    activityCards = new ActivityCard[]{
        new ActivityCard("MD3", MD3Activity.class),
        new ActivityCard("Product List", ProductListActivity.class),
        new ActivityCard("Login", LoginActivity.class),
        new ActivityCard("Activity Intent", IntentActivity.class),
        new ActivityCard("Props", PropsActivity.class),
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
    adapter.setOnItemClickListener(card -> {
      startActivity(new Intent(this, card.getActivity()));
    });

    activitiesView.setAdapter(adapter);
    logOut();
  }

  private void logOut() {
    Button logoutBtn = findViewById(R.id.btn_logout);
    logoutBtn.setOnClickListener(v -> {
      LoginCheck.logout(this);

      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
      finish();
    });
  }
}
