package com.chill.learn.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chill.learn.adapter.Product.ListViewAdapter;
import com.chill.learn.entity.Product;
import com.chill.learn.adapter.Product.RecyclerAdapter;
import com.chill.learn.R;

import java.util.ArrayList;


public class ProductListActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private ListView listView;
  private RecyclerAdapter recyclerAdapter;
  private ArrayList<Product> products;

  // temp data
  // TODO: fetch from database or local json
  final String[] imgs = {"iphone", "fans", "orange", "paper", "bluemoon"};
  final String[] names = {"Iphone 11", "Fan", "Orange", "Tissue", "Blue Moon Laundry Detergent"};
  final double[] prices = {1067.5, 3.99, 1.99, 0.99, 4, 98};

  void init() {
    products = new ArrayList<>();
    for (int i = 0; i < imgs.length; ++i) {
      @SuppressLint("DiscouragedApi") int imgId = getResources().getIdentifier(imgs[i], "drawable", getPackageName());
      products.add(new Product(imgId, names[i], prices[i]));
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.product_view);

    init();
    // for recycler view
    recyclerView = findViewById(R.id.product_recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerAdapter = new RecyclerAdapter(products);
    recyclerView.setAdapter(recyclerAdapter);

    // for list view
    listView = findViewById(R.id.product_list);
    var arrAdapter = new ListViewAdapter(this, products);
    listView.setAdapter(arrAdapter);

    // for string list view
    ListView listView = findViewById(R.id.string_list);
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
    listView.setAdapter(arrayAdapter);
  }
}
