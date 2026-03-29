package com.unicornt.store.model;

import java.text.NumberFormat;
import java.util.Locale;

public class Product {

    private int id;
    private String name;
    private String category;
    private int price;

    public Product(int id, String name, String category, int price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public int getId()           { return id; }
    public String getName()      { return name; }
    public String getCategory()  { return category; }
    public int getPrice()        { return price; }

    public void setName(String name)         { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(int price)          { this.price = price; }

    public String formattedPrice() {
        NumberFormat nf = NumberFormat.getInstance(Locale.of("es", "CL"));
        return "$" + nf.format(price);
    }

    @Override
    public String toString() {
        return String.format("[%2d] %-52s %-14s %s",
                id, name, category, formattedPrice());
    }
}
