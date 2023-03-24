package com.chill.learn.adapter.Product;

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

import com.chill.learn.entity.Product;
import com.chill.learn.R;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Product> {
  private Context context;
  private List<Product> products;

  public ListViewAdapter(Context context, List<Product> products) {
    super(context, R.layout.item_product, products);
    this.context = context;
    this.products = products;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.item_product, parent, false);

    ImageView productImage = rowView.findViewById(R.id.product_image);
    TextView productName = rowView.findViewById(R.id.product_name);
    TextView productPrice = rowView.findViewById(R.id.product_price);

    Product product = products.get(position);
    productImage.setImageResource(product.getImage());
    productName.setText(product.getName());

    // Format price to $xx.xx
    @SuppressLint("DefaultLocale") String price = String.format("$%.2f", product.getPrice());
    productPrice.setText(price);
    return rowView;
  }
}
