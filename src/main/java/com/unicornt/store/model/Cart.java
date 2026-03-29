package com.unicornt.store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Cart {

    private final List<CartItem> items = new ArrayList<>();

    public void add(Product product, int qty) {
        Optional<CartItem> existing = findItem(product.getId());
        if (existing.isPresent()) {
            existing.get().setQty(existing.get().getQty() + qty);
        } else {
            items.add(new CartItem(product, qty));
        }
    }

    public boolean remove(int productId) {
        return items.removeIf(item -> item.getProduct().getId() == productId);
    }

    public void clear() {
        items.clear();
    }

    public Optional<CartItem> findItem(int productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public int getTotal() {
        return items.stream().mapToInt(CartItem::getSubtotal).sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    /** Returns a mutable snapshot for building an Order. */
    public List<CartItem> snapshot() {
        return new ArrayList<>(items);
    }
}
