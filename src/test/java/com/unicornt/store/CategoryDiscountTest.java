package com.unicornt.store;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.unicornt.store.model.Cart;
import com.unicornt.store.model.CartItem;
import com.unicornt.store.model.Product;
import com.unicornt.store.model.discount.CategoryDiscount;

class CategoryDiscountTest {

    private List<CartItem> items(Product... products) {
        Cart cart = new Cart();
        for (Product p : products) cart.add(p, 1);
        return cart.getItems();
    }

    @Test
    void applies_whenCategoryPresent() {
        CategoryDiscount discount = new CategoryDiscount("it-crowd", 5);
        List<CartItem> cart = items(new Product(1, "Polera Moss", "it-crowd", 13990));
        assertTrue(discount.applies(cart, 13990));
    }

    @Test
    void doesNotApply_whenCategoryAbsent() {
        CategoryDiscount discount = new CategoryDiscount("it-crowd", 5);
        List<CartItem> cart = items(new Product(1, "Polera DevOps", "devops", 13990));
        assertFalse(discount.applies(cart, 13990));
    }

    @Test
    void isCaseInsensitive() {
        CategoryDiscount discount = new CategoryDiscount("IT-CROWD", 5);
        List<CartItem> cart = items(new Product(1, "Polera Moss", "it-crowd", 13990));
        assertTrue(discount.applies(cart, 13990));
    }

    @Test
    void calculatesCorrectAmount() {
        CategoryDiscount discount = new CategoryDiscount("it-crowd", 5);
        List<CartItem> cart = items(new Product(1, "Polera", "it-crowd", 40_000));
        assertEquals(2_000, discount.calculate(cart, 40_000));
    }

    @Test
    void applies_whenOneOfManyItemsMatchesCategory() {
        CategoryDiscount discount = new CategoryDiscount("it-crowd", 5);
        List<CartItem> cart = items(
                new Product(1, "Polera DevOps", "devops",   13990),
                new Product(2, "Polera Moss",   "it-crowd", 13990));
        assertTrue(discount.applies(cart, 27980));
    }
}
