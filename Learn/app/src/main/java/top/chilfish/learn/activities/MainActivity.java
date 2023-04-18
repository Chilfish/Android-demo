package top.chilfish.learn.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import top.chilfish.learn.R;
import top.chilfish.learn.databinding.ActivityMainBinding;
import top.chilfish.learn.fragments.FirstFragment;
import top.chilfish.learn.fragments.SecondFragment;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;

  public static final String BROADCAST_ACTION = "top.chilfish.learn.broadcast";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    setSupportActionBar(binding.toolbar);


    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction
        .replace(R.id.nav_host_fragment, new FirstFragment())
        .commit();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}