package com.unicornt.store;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.unicornt.store.model.Cart;
import com.unicornt.store.model.CartItem;
import com.unicornt.store.model.Product;
import com.unicornt.store.model.discount.AmountDiscount;

class AmountDiscountTest {

    private List<CartItem> items(Product... products) {
        Cart cart = new Cart();
        for (Product p : products) cart.add(p, 1);
        return cart.getItems();
    }

    @Test
    void applies_whenTotalEqualsMinimum() {
        AmountDiscount discount = new AmountDiscount(30_000, 10);
        assertTrue(discount.applies(items(), 30_000));
    }

    @Test
    void applies_whenTotalExceedsMinimum() {
        AmountDiscount discount = new AmountDiscount(30_000, 10);
        List<CartItem> cart = items(new Product(1, "P", "devops", 35_000));
        assertTrue(discount.applies(cart, 35_000));
    }

    @Test
    void doesNotApply_whenTotalBelowMinimum() {
        AmountDiscount discount = new AmountDiscount(30_000, 10);
        List<CartItem> cart = items(new Product(1, "P", "devops", 20_000));
        assertFalse(discount.applies(cart, 20_000));
    }

    @Test
    void calculatesCorrectAmount() {
        AmountDiscount discount = new AmountDiscount(30_000, 10);
        List<CartItem> cart = items(new Product(1, "P", "devops", 40_000));
        assertEquals(4_000, discount.calculate(cart, 40_000));
    }

    @Test
    void calculateWith15Percent() {
        AmountDiscount discount = new AmountDiscount(10_000, 15);
        assertEquals(1_500, discount.calculate(items(), 10_000));
    }
}
