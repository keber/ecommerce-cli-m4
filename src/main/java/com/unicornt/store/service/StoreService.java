package com.unicornt.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.unicornt.store.exception.InvalidQuantityException;
import com.unicornt.store.model.AppliedDiscount;
import com.unicornt.store.model.Cart;
import com.unicornt.store.model.CartItem;
import com.unicornt.store.model.Order;
import com.unicornt.store.model.Product;
import com.unicornt.store.model.discount.AmountDiscount;
import com.unicornt.store.model.discount.CategoryDiscount;
import com.unicornt.store.model.discount.DiscountRule;

public class StoreService {

    private final Catalog catalog;
    private final Cart cart;
    private final List<DiscountRule> discountRules;
    private final List<Order> orders;

    public StoreService() {
        this.catalog = new Catalog();
        this.cart    = new Cart();
        this.orders  = new ArrayList<>();
        this.discountRules = List.of(
                new AmountDiscount(30_000, 10),
                new CategoryDiscount("it-crowd", 5)
        );
        DataSeeder.populate(catalog);
    }

    // ─── Catalog access ───────────────────────────────────────────────────────

    public Catalog getCatalog() { return catalog; }

    // ─── Cart operations ──────────────────────────────────────────────────────

    public Cart getCart() { return cart; }

    public void addToCart(int productId, int qty) {
        if (qty <= 0) {
            throw new InvalidQuantityException(qty);
        }
        Product p = catalog.findById(productId)
                .orElseThrow(() -> new NoSuchElementException(
                        "No se encontro el producto con id " + productId + "."));
        cart.add(p, qty);
    }

    public void removeFromCart(int productId) {
        if (!cart.remove(productId)) {
            throw new NoSuchElementException(
                    "El producto con id " + productId + " no esta en el carrito.");
        }
    }

    // ─── Discounts ────────────────────────────────────────────────────────────

    public List<DiscountRule> getActiveDiscounts() {
        return discountRules;
    }

    // ─── Purchase ─────────────────────────────────────────────────────────────

    /**
     * Evaluates all discount rules, creates an Order, clears the cart and returns it.
     *
     * @throws IllegalStateException if the cart is empty
     */
    public Order confirmPurchase() {
        if (cart.isEmpty()) {
            throw new IllegalStateException(
                    "El carrito esta vacio. Agrega productos antes de confirmar.");
        }

        List<CartItem> snapshot = cart.snapshot();
        int totalBase  = cart.getTotal();
        int totalFinal = totalBase;
        List<AppliedDiscount> applied = new ArrayList<>();

        for (DiscountRule rule : discountRules) {
            if (rule.applies(snapshot, totalBase)) {
                int amount = rule.calculate(snapshot, totalBase);
                applied.add(new AppliedDiscount(rule.getDescription(), amount));
                totalFinal -= amount;
            }
        }

        Order order = new Order(snapshot, totalBase, totalFinal, applied);
        orders.add(order);
        cart.clear();
        return order;
    }

    public List<Order> getOrders() {
        return List.copyOf(orders);
    }
}
