package com.chill.lab3.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chill.lab3.adapter.ListViewAdapter;
import com.chill.lab3.Product;
import com.chill.lab3.adapter.ProductAdapter;
import com.chill.lab3.R;


import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private ListView listView;
  private ProductAdapter productAdapter;
  private ArrayList<Product> products;


  void init() {
    products = new ArrayList<>();
    // TODO: fetch from database or local json
    String[] imgs = {"iphone", "fans", "orange", "paper", "bluemoon"};
    String[] names = {"Iphone 11", "Fan", "Orange", "Tissue", "Blue Moon Laundry Detergent"};
    double[] prices = {1067.5, 3.99, 1.99, 0.99, 4, 98};

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
    productAdapter = new ProductAdapter(products);
    recyclerView.setAdapter(productAdapter);

    // for list view
    listView = findViewById(R.id.product_list);
    var arrAdapter = new ListViewAdapter(this, products);
    listView.setAdapter(arrAdapter);
  }
}
