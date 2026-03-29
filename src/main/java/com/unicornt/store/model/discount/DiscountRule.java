package com.unicornt.store.model.discount;

import java.util.List;

import com.unicornt.store.model.CartItem;

public interface DiscountRule {

    boolean applies(List<CartItem> items, int totalBase);

    int calculate(List<CartItem> items, int totalBase);

    String getDescription();
}
