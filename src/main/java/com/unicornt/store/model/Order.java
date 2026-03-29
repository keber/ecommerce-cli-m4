package com.unicornt.store.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order {

    private static int counter = 1;

    private final int id;
    private final List<CartItem> items;
    private final int totalBase;
    private final int totalFinal;
    private final List<AppliedDiscount> discounts;
    private final LocalDateTime createdAt;

    public Order(List<CartItem> items, int totalBase, int totalFinal,
                 List<AppliedDiscount> discounts) {
        this.id        = counter++;
        this.items     = List.copyOf(items);
        this.totalBase = totalBase;
        this.totalFinal = totalFinal;
        this.discounts  = List.copyOf(discounts);
        this.createdAt  = LocalDateTime.now();
    }

    public int getId()                          { return id; }
    public List<CartItem> getItems()            { return items; }
    public int getTotalBase()                   { return totalBase; }
    public int getTotalFinal()                  { return totalFinal; }
    public List<AppliedDiscount> getDiscounts() { return discounts; }
    public LocalDateTime getCreatedAt()         { return createdAt; }

    @Override
    public String toString() {
        return String.format("Orden #%d  [%s]",
                id, createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }
}
