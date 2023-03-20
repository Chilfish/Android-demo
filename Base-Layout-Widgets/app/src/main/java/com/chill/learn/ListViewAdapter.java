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

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Product> {
  private Context context;
  private List<Product> products;

  public ListViewAdapter(Context context, List<Product> products) {
    super(context, R.layout.product_item, products);
    this.context = context;
    this.products = products;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.product_item, parent, false);

    ImageView productImage = rowView.findViewById(R.id.product_image);
    TextView productName = rowView.findViewById(R.id.product_name);
    TextView productPrice = rowView.findViewById(R.id.product_price);

    Product product = products.get(position);
    productImage.setImageResource(product.getImage());
    productName.setText(product.getName());

    @SuppressLint("DefaultLocale") String price = String.format("$%.2f", product.getPrice());
    productPrice.setText(price);
    return rowView;
  }
}
