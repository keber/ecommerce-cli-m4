package com.unicornt.store.model.discount;

import java.util.List;

import com.unicornt.store.model.CartItem;

/**
 * Applies a percentage discount when the cart contains at least one product
 * belonging to the specified category.
 */
public class CategoryDiscount implements DiscountRule {

    private final String category;
    private final int percentage;

    public CategoryDiscount(String category, int percentage) {
        this.category   = category;
        this.percentage = percentage;
    }

    @Override
    public boolean applies(List<CartItem> items, int totalBase) {
        return items.stream()
                .anyMatch(item -> category.equalsIgnoreCase(item.getProduct().getCategory()));
    }

    @Override
    public int calculate(List<CartItem> items, int totalBase) {
        return totalBase * percentage / 100;
    }

    @Override
    public String getDescription() {
        return String.format("Descuento por categoria '%s': %d%%", category, percentage);
    }
}
