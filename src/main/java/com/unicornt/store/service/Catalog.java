package com.unicornt.store.service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import com.unicornt.store.model.Product;

public class Catalog {

    private final Map<Integer, Product> products = new LinkedHashMap<>();

    // ─── Write operations ─────────────────────────────────────────────────────

    public void add(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacio.");
        }
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException(
                    "Ya existe un producto con el id " + product.getId() + ".");
        }
        products.put(product.getId(), product);
    }

    public void update(int id, String name, String category, int price) {
        Product p = findByIdOrThrow(id);
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacio.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        p.setName(name);
        p.setCategory(category);
        p.setPrice(price);
    }

    public void delete(int id) {
        if (products.remove(id) == null) {
            throw new NoSuchElementException("No se encontro el producto con id " + id + ".");
        }
    }

    // ─── Read operations ──────────────────────────────────────────────────────

    public Optional<Product> findById(int id) {
        return Optional.ofNullable(products.get(id));
    }

    /**
     * Case-insensitive search across product name and category.
     */
    public List<Product> search(String query) {
        String q = query.toLowerCase();
        return products.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(q)
                          || p.getCategory().toLowerCase().contains(q))
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }

    public List<Product> listAllByName() {
        return products.values().stream()
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }

    public List<Product> listAllByPrice() {
        return products.values().stream()
                .sorted(Comparator.comparingInt(Product::getPrice))
                .collect(Collectors.toList());
    }

    /** Returns the next available id (max existing id + 1). */
    public int nextId() {
        return products.keySet().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0) + 1;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    // ─── Private helpers ──────────────────────────────────────────────────────

    private Product findByIdOrThrow(int id) {
        return Optional.ofNullable(products.get(id))
                .orElseThrow(() -> new NoSuchElementException(
                        "No se encontro el producto con id " + id + "."));
    }
}
