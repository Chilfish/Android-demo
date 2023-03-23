package com.chill.learn.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chill.learn.adapter.ListViewAdapter;
import com.chill.learn.Product;
import com.chill.learn.adapter.ProductAdapter;
import com.chill.learn.R;

import java.util.ArrayList;

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

    stringList();
  }

  void stringList() {
    var stringList = new ArrayList<String>();
    stringList.add("Product 1");
    stringList.add("Product 2");
    stringList.add("Product 3");
    stringList.add("Product 4");
    stringList.add("Product 5");

    ListView listView = findViewById(R.id.string_list);
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringList);
    listView.setAdapter(arrayAdapter);
  }

}
