package com.chill.learn.adapter.Product;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chill.learn.entity.Product;
import com.chill.learn.R;

import java.util.List;

/*
 * Adapter for RecyclerView
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
  private List<Product> productList;

  private OnCardClickListener cardClickListener;

  public RecyclerAdapter(List<Product> productList) {
    this.productList = productList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_product, parent, false);
    return new ViewHolder(view);
  }

  // Bind data to ViewHolder
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Product product = productList.get(position);
    holder.bindData(product);

    holder.itemView.setOnClickListener(v -> {
      if (cardClickListener != null) {
        cardClickListener.onCardClick(product);
      }
    });
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

    public void bindData(Product product) {
      productImage.setImageResource(product.getImage());
      productName.setText(product.getName());

      // Format price to $xx.xx
      @SuppressLint("DefaultLocale") String price = String.format("$%.2f", product.getPrice());
      productPrice.setText(price);
    }
  }

  public void setOnCardClickListener(OnCardClickListener onCardClickListener) {
    this.cardClickListener = onCardClickListener;
  }

  public interface OnCardClickListener {
    void onCardClick(Product card);
  }
}

