package com.unicornt.store;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.unicornt.store.model.Product;
import com.unicornt.store.service.Catalog;

class CatalogTest {

    private Catalog catalog;

    @BeforeEach
    void setUp() {
        catalog = new Catalog();
    }

    @Test
    void add_newProduct_canBeFoundById() {
        catalog.add(new Product(1, "Polera Test", "devops", 13990));
        assertTrue(catalog.findById(1).isPresent());
        assertEquals("Polera Test", catalog.findById(1).get().getName());
    }

    @Test
    void add_duplicateId_throwsIllegalArgumentException() {
        catalog.add(new Product(1, "Polera A", "devops", 13990));
        assertThrows(IllegalArgumentException.class,
                () -> catalog.add(new Product(1, "Polera B", "linux", 12990)));
    }

    @Test
    void add_zeroPriceThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> catalog.add(new Product(1, "Polera", "devops", 0)));
    }

    @Test
    void add_negativePriceThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> catalog.add(new Product(1, "Polera", "devops", -100)));
    }

    @Test
    void add_blankNameThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> catalog.add(new Product(1, "  ", "devops", 13990)));
    }

    @Test
    void search_byName_returnsMatchingProducts() {
        catalog.add(new Product(1, "Polera CSS Is Awesome", "programador", 12990));
        catalog.add(new Product(2, "Polera DevOps",         "devops",      13990));
        List<Product> results = catalog.search("css");
        assertEquals(1, results.size());
        assertEquals(1, results.get(0).getId());
    }

    @Test
    void search_byCategory_returnsAllMatchingProducts() {
        catalog.add(new Product(1, "Polera A", "devops", 13990));
        catalog.add(new Product(2, "Polera B", "devops", 12990));
        catalog.add(new Product(3, "Polera C", "linux",  14990));
        List<Product> results = catalog.search("devops");
        assertEquals(2, results.size());
    }

    @Test
    void search_noMatch_returnsEmptyList() {
        catalog.add(new Product(1, "Polera A", "devops", 13990));
        assertTrue(catalog.search("xyz_no_match").isEmpty());
    }

    @Test
    void update_existingProduct_changesValues() {
        catalog.add(new Product(1, "Polera A", "devops", 13990));
        catalog.update(1, "Polera Editada", "linux", 15000);
        Product updated = catalog.findById(1).get();
        assertEquals("Polera Editada", updated.getName());
        assertEquals("linux",          updated.getCategory());
        assertEquals(15000,            updated.getPrice());
    }

    @Test
    void update_nonExistingProduct_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class,
                () -> catalog.update(99, "X", "y", 1000));
    }

    @Test
    void delete_existingProduct_removesItFromCatalog() {
        catalog.add(new Product(1, "Polera A", "devops", 13990));
        catalog.delete(1);
        assertFalse(catalog.findById(1).isPresent());
    }

    @Test
    void delete_nonExistingProduct_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> catalog.delete(999));
    }

    @Test
    void listAllByName_returnsSortedAlphabetically() {
        catalog.add(new Product(1, "Zebra",  "qa", 13990));
        catalog.add(new Product(2, "Alpha",  "qa", 12990));
        catalog.add(new Product(3, "Manzana","qa", 11990));
        List<Product> sorted = catalog.listAllByName();
        assertEquals("Alpha",   sorted.get(0).getName());
        assertEquals("Manzana", sorted.get(1).getName());
        assertEquals("Zebra",   sorted.get(2).getName());
    }

    @Test
    void listAllByPrice_returnsSortedByPriceAscending() {
        catalog.add(new Product(1, "Cara",   "qa", 15990));
        catalog.add(new Product(2, "Barata", "qa", 11990));
        catalog.add(new Product(3, "Media",  "qa", 13990));
        List<Product> sorted = catalog.listAllByPrice();
        assertEquals(11990, sorted.get(0).getPrice());
        assertEquals(13990, sorted.get(1).getPrice());
        assertEquals(15990, sorted.get(2).getPrice());
    }

    @Test
    void nextId_emtpyCatalog_returnsOne() {
        assertEquals(1, catalog.nextId());
    }

    @Test
    void nextId_afterAdding_returnsMaxPlusOne() {
        catalog.add(new Product(5, "P", "qa", 1000));
        assertEquals(6, catalog.nextId());
    }
}
