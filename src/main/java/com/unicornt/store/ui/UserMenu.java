package com.unicornt.store.ui;

import java.util.List;
import java.util.NoSuchElementException;

import com.unicornt.store.exception.InvalidQuantityException;
import com.unicornt.store.model.AppliedDiscount;
import com.unicornt.store.model.Cart;
import com.unicornt.store.model.CartItem;
import com.unicornt.store.model.Order;
import com.unicornt.store.model.Product;
import com.unicornt.store.model.discount.DiscountRule;
import com.unicornt.store.service.StoreService;

class UserMenu {

    private final ConsoleIO io;
    private final StoreService service;

    UserMenu(ConsoleIO io, StoreService service) {
        this.io      = io;
        this.service = service;
    }

    void run() {
        boolean back = false;
        while (!back) {
            showMenu();
            int option = io.readInt("Selecciona una opcion: ");
            switch (option) {
                case 1  -> listProducts();
                case 2  -> searchProducts();
                case 3  -> addToCart();
                case 4  -> removeFromCart();
                case 5  -> viewCart();
                case 6  -> viewDiscounts();
                case 7  -> confirmPurchase();
                case 0  -> back = true;
                default -> io.warn("Opcion invalida.");
            }
        }
    }

    private void showMenu() {
        io.println("\n" + ConsoleIO.LINE);
        io.println("  USUARIO - Tienda");
        io.println(ConsoleIO.LINE);
        io.println("  1) Listar productos");
        io.println("  2) Buscar productos");
        io.println("  3) Agregar al carrito");
        io.println("  4) Quitar del carrito");
        io.println("  5) Ver carrito");
        io.println("  6) Ver descuentos activos");
        io.println("  7) Confirmar compra");
        io.println("  0) Volver al menu principal");
        io.println(ConsoleIO.LINE);
    }

    private void listProducts() {
        io.println("\n  -- Catalogo de productos --");
        List<Product> list = service.getCatalog().listAllByName();
        io.printProductList(list);
        io.println("  Total: " + list.size() + " producto(s)");
    }

    private void searchProducts() {
        String query = io.readLine("Buscar (nombre/categoria): ");
        if (query.isBlank()) return;
        List<Product> results = service.getCatalog().search(query);
        if (results.isEmpty()) {
            io.println("  No se encontraron productos para: \"" + query + "\"");
        } else {
            io.println("  -- " + results.size() + " resultado(s) --");
            io.printProductList(results);
        }
    }

    private void addToCart() {
        int id  = io.readInt("ID del producto: ");
        int qty = io.readInt("Cantidad: ");
        try {
            service.addToCart(id, qty);
            service.getCatalog().findById(id).ifPresent(p ->
                    io.println("  OK: Agregado al carrito - " + p.getName() + " x" + qty));
        } catch (InvalidQuantityException | NoSuchElementException e) {
            io.warn(e.getMessage());
        }
    }

    private void removeFromCart() {
        int id = io.readInt("ID del producto a quitar: ");
        try {
            service.removeFromCart(id);
            io.println("  OK: Producto eliminado del carrito.");
        } catch (NoSuchElementException e) {
            io.warn(e.getMessage());
        }
    }

    private void viewCart() {
        io.println("\n  -- Carrito --");
        Cart cart = service.getCart();
        if (cart.isEmpty()) {
            io.println("  El carrito esta vacio.");
            return;
        }
        for (CartItem item : cart.getItems()) {
            io.println(item.toString());
        }
        io.println("  " + "-".repeat(60));
        io.println("  TOTAL BASE: " + io.formatCLP(cart.getTotal()));
    }

    private void viewDiscounts() {
        io.println("\n  -- Descuentos activos --");
        List<DiscountRule> rules = service.getActiveDiscounts();
        for (int i = 0; i < rules.size(); i++) {
            io.println("  " + (i + 1) + ". " + rules.get(i).getDescription());
        }
    }

    private void confirmPurchase() {
        io.println("\n  -- Confirmar compra --");
        if (service.getCart().isEmpty()) {
            io.warn("El carrito esta vacio. Agrega productos antes de confirmar.");
            return;
        }
        viewCart();
        String confirm = io.readLine("\n  Confirmar la compra? (s/N): ");
        if (!confirm.trim().equalsIgnoreCase("s")) {
            io.println("  Compra cancelada.");
            return;
        }
        try {
            Order order = service.confirmPurchase();
            io.println("\n  " + ConsoleIO.DLINE);
            io.println("  COMPRA CONFIRMADA");
            io.println("  " + ConsoleIO.DLINE);
            io.println("  " + order);
            io.println("  Total base  : " + io.formatCLP(order.getTotalBase()));
            if (order.getDiscounts().isEmpty()) {
                io.println("  Sin descuentos aplicados.");
            } else {
                io.println("  Descuentos aplicados:");
                for (AppliedDiscount d : order.getDiscounts()) {
                    io.println(d.toString());
                }
            }
            io.println("  " + "-".repeat(40));
            io.println("  TOTAL FINAL : " + io.formatCLP(order.getTotalFinal()));
            io.println("  " + ConsoleIO.DLINE);
        } catch (IllegalStateException e) {
            io.warn(e.getMessage());
        }
    }
}
