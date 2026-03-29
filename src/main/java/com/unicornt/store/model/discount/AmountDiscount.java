package com.unicornt.store.model.discount;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.unicornt.store.model.CartItem;

/**
 * Applies a percentage discount when the cart total reaches a minimum amount.
 */
public class AmountDiscount implements DiscountRule {

    private final int minAmount;
    private final int percentage;

    public AmountDiscount(int minAmount, int percentage) {
        this.minAmount  = minAmount;
        this.percentage = percentage;
    }

    @Override
    public boolean applies(List<CartItem> items, int totalBase) {
        return totalBase >= minAmount;
    }

    @Override
    public int calculate(List<CartItem> items, int totalBase) {
        return totalBase * percentage / 100;
    }

    @Override
    public String getDescription() {
        NumberFormat nf = NumberFormat.getInstance(Locale.of("es", "CL"));
        return String.format("Descuento por monto (minimo $%s): %d%%",
                nf.format(minAmount), percentage);
    }
}
