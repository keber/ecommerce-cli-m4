package com.unicornt.store.model;

import java.text.NumberFormat;
import java.util.Locale;

public class CartItem {

    private final Product product;
    private int qty;

    public CartItem(Product product, int qty) {
        this.product = product;
        this.qty = qty;
    }

    public Product getProduct() { return product; }
    public int getQty()         { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public int getSubtotal() {
        return product.getPrice() * qty;
    }

    @Override
    public String toString() {
        return String.format("  %-52s x%-3d  %s c/u  =>  %s",
                product.getName(), qty,
                product.formattedPrice(),
                formatCLP(getSubtotal()));
    }

    private String formatCLP(int amount) {
        NumberFormat nf = NumberFormat.getInstance(Locale.of("es", "CL"));
        return "$" + nf.format(amount);
    }
}
