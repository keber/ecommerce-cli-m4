package com.unicornt.store.model;

import java.text.NumberFormat;
import java.util.Locale;

public class AppliedDiscount {

    private final String description;
    private final int amount;

    public AppliedDiscount(String description, int amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() { return description; }
    public int getAmount()         { return amount; }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getInstance(Locale.of("es", "CL"));
        return String.format("  - %s: -$%s", description, nf.format(amount));
    }
}
