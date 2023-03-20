package com.chill.learn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
  private List<Product> productList;

  public ProductAdapter(List<Product> productList) {
    this.productList = productList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Product product = productList.get(position);
    holder.productImage.setImageResource(product.getImage());
    holder.productName.setText(product.getName());

    @SuppressLint("DefaultLocale") String price = String.format("$%.2f", product.getPrice());
    holder.productPrice.setText(price);
  }

  @Override
  public int getItemCount() {
    return productList.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView productImage;
    TextView productName;
    TextView productPrice;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      productImage = itemView.findViewById(R.id.product_image);
      productName = itemView.findViewById(R.id.product_name);
      productPrice = itemView.findViewById(R.id.product_price);
    }
  }
}

