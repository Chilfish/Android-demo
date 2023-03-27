package com.chill.learn.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chill.learn.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MD3Activity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_md3);

    iconChange();

    // set a MaterialAlertDialogBuilder
    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
    dialog.setTitle("Title");
    dialog.setMessage("Message");
    dialog.setPositiveButton("OK", (dialogInterface, i) -> {
      Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
    });
    dialog.setNegativeButton("Cancel", (dialogInterface, i) -> {
      Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
    });
    dialog.show();
  }

  @SuppressLint("NonConstantResourceId")
  void iconChange() {
    BottomNavigationView BtnNav = findViewById(R.id.bottom_navigation);
    BadgeDrawable badge = BtnNav.getOrCreateBadge(R.id.nav_chat);
    badge.setVisible(true);
    badge.setNumber(10);

    // remove badge and change to filled icon while navigation item is selected
    BtnNav.setOnItemSelectedListener(item -> {
      switch (item.getItemId()) {
        case R.id.nav_chat:
          BtnNav.getMenu().findItem(R.id.nav_chat).setIcon(R.drawable.baseline_chat_24);
          break;
        case R.id.nav_home:
          BtnNav.getMenu().findItem(R.id.nav_home).setIcon(R.drawable.baseline_home_24);
          break;
        case R.id.nav_settings:
          BtnNav.getMenu().findItem(R.id.nav_settings).setIcon(R.drawable.baseline_settings_24);
          break;
      }
      badge.setVisible(false);
      return true;
    });
  }
}
