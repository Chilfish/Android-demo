package com.chill.lab3.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chill.lab3.Product;
import com.chill.lab3.R;

import java.util.List;

/*
 * Adapter for RecyclerView
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
  private List<Product> productList;

  public RecyclerAdapter(List<Product> productList) {
    this.productList = productList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.product_item, parent, false);
    return new ViewHolder(view);
  }

  // Bind data to ViewHolder
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Product product = productList.get(position);
    holder.productImage.setImageResource(product.getImage());
    holder.productName.setText(product.getName());

    // Format price to $xx.xx
    @SuppressLint("DefaultLocale") String price = String.format("$%.2f", product.getPrice());
    holder.productPrice.setText(price);
  }

  @Override
  public int getItemCount() {
    return productList.size();
  }

  /*
   * ViewHolder for RecyclerView
   */
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

