package com.unicornt.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.unicornt.store.model.Cart;
import com.unicornt.store.model.CartItem;
import com.unicornt.store.model.Product;

class CartTest {

    private Cart cart;
    private Product p1;
    private Product p2;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        p1 = new Product(1, "Polera Devops",   "devops",   13990);
        p2 = new Product(2, "Polera IT Crowd", "it-crowd", 12990);
    }

    @Test
    void getTotal_withMultipleItems_returnsCorrectSum() {
        cart.add(p1, 2);
        cart.add(p2, 3);
        int expected = (13990 * 2) + (12990 * 3);
        assertEquals(expected, cart.getTotal());
    }

    @Test
    void add_sameProductTwice_accumulatesQty() {
        cart.add(p1, 2);
        cart.add(p1, 3);
        assertEquals(1, cart.getItems().size());
        CartItem item = cart.getItems().get(0);
        assertEquals(5, item.getQty());
        assertEquals(13990 * 5, item.getSubtotal());
    }

    @Test
    void remove_existingProduct_removesFromCart() {
        cart.add(p1, 1);
        cart.add(p2, 2);
        boolean removed = cart.remove(p1.getId());
        assertTrue(removed);
        assertEquals(1, cart.getItems().size());
        assertEquals(p2.getId(), cart.getItems().get(0).getProduct().getId());
    }

    @Test
    void remove_nonExistingProduct_returnsFalse() {
        cart.add(p1, 1);
        boolean removed = cart.remove(999);
        assertFalse(removed);
        assertEquals(1, cart.getItems().size());
    }

    @Test
    void isEmpty_newCart_returnsTrue() {
        assertTrue(cart.isEmpty());
    }

    @Test
    void isEmpty_afterAddAndClear_returnsTrue() {
        cart.add(p1, 1);
        assertFalse(cart.isEmpty());
        cart.clear();
        assertTrue(cart.isEmpty());
    }

    @Test
    void getTotal_emptyCart_returnsZero() {
        assertEquals(0, cart.getTotal());
    }
}
