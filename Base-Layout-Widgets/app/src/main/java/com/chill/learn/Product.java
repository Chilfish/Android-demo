package com.chill.learn;

public class Product {
  private int image; // image resource id
  private String name; // product name
  private double price; // product price

  public Product(int image, String name, double price) {
    this.image = image;
    this.name = name;
    this.price = price;
  }

  public int getImage() {
    return image;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }
}
