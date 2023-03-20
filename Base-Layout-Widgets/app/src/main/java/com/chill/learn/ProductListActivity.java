package com.chill.learn;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private ListView listView;
  private ProductAdapter productAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.product_view);

    var productList = new ArrayList<Product>();
    productList.add(new Product(R.drawable.avatar, "Product 1", 9.99));
    productList.add(new Product(R.drawable.avatar, "Product 2", 19.99));
    productList.add(new Product(R.drawable.avatar, "Product 3", 29.99));
    productList.add(new Product(R.drawable.avatar, "Product 4", 29.99));
    productList.add(new Product(R.drawable.avatar, "Product 5", 29.99));

    recyclerView = findViewById(R.id.product_recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    productAdapter = new ProductAdapter(productList);
    recyclerView.setAdapter(productAdapter);

    listView = findViewById(R.id.product_list);
    var arrAdapter = new ListViewAdapter(this, productList);
    listView.setAdapter(arrAdapter);
  }

}
