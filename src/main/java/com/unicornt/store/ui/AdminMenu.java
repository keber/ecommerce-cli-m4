package com.unicornt.store.ui;

import java.util.List;
import java.util.NoSuchElementException;

import com.unicornt.store.model.Product;
import com.unicornt.store.service.StoreService;

class AdminMenu {

    private final ConsoleIO io;
    private final StoreService service;

    AdminMenu(ConsoleIO io, StoreService service) {
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
                case 3  -> createProduct();
                case 4  -> editProduct();
                case 5  -> deleteProduct();
                case 0  -> back = true;
                default -> io.warn("Opcion invalida.");
            }
        }
    }

    private void showMenu() {
        io.println("\n" + ConsoleIO.LINE);
        io.println("  ADMIN - Gestion de Productos");
        io.println(ConsoleIO.LINE);
        io.println("  1) Listar productos");
        io.println("  2) Buscar productos");
        io.println("  3) Crear producto");
        io.println("  4) Editar producto");
        io.println("  5) Eliminar producto");
        io.println("  0) Volver al menu principal");
        io.println(ConsoleIO.LINE);
    }

    private void listProducts() {
        io.println("\n  -- Catalogo de productos (orden alfabetico) --");
        List<Product> list = service.getCatalog().listAllByName();
        if (list.isEmpty()) {
            io.println("  No hay productos en el catalogo.");
            return;
        }
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

    private void createProduct() {
        io.println("\n  -- Crear nuevo producto --");
        int id          = service.getCatalog().nextId();
        io.println("  ID asignado: " + id);
        String name     = io.readLine("Nombre: ");
        String category = io.readLine("Categoria (subcategoria tematica): ");
        int price       = io.readPositiveInt("Precio en CLP (sin puntos): ");
        try {
            service.getCatalog().add(new Product(id, name, category, price));
            io.println("  OK: Producto creado - [" + id + "] " + name);
        } catch (IllegalArgumentException e) {
            io.warn(e.getMessage());
        }
    }

    private void editProduct() {
        io.println("\n  -- Editar producto --");
        int id = io.readInt("ID del producto a editar: ");
        service.getCatalog().findById(id).ifPresentOrElse(p -> {
            io.println("  Producto actual: " + p);
            String name = io.readLine("Nuevo nombre       (Enter para mantener): ");
            if (name.isBlank()) name = p.getName();
            String category = io.readLine("Nueva categoria    (Enter para mantener): ");
            if (category.isBlank()) category = p.getCategory();
            String priceStr = io.readLine("Nuevo precio CLP   (Enter para mantener): ");
            int price = priceStr.isBlank() ? p.getPrice() : io.parseIntSafe(priceStr, p.getPrice());
            try {
                service.getCatalog().update(id, name, category, price);
                io.println("  OK: Producto actualizado correctamente.");
            } catch (IllegalArgumentException e) {
                io.warn(e.getMessage());
            }
        }, () -> io.warn("No se encontro el producto con id " + id + "."));
    }

    private void deleteProduct() {
        io.println("\n  -- Eliminar producto --");
        int id = io.readInt("ID del producto a eliminar: ");
        service.getCatalog().findById(id).ifPresentOrElse(p -> {
            io.println("  Producto: " + p);
            String confirm = io.readLine("Confirmar eliminacion? (s/N): ");
            if (confirm.trim().equalsIgnoreCase("s")) {
                try {
                    service.getCatalog().delete(id);
                    io.println("  OK: Producto eliminado.");
                } catch (NoSuchElementException e) {
                    io.warn(e.getMessage());
                }
            } else {
                io.println("  Operacion cancelada.");
            }
        }, () -> io.warn("No se encontro el producto con id " + id + "."));
    }
}
