package com.unicornt.store.ui;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.unicornt.store.model.Product;

class ConsoleIO {

    static final String LINE  = "-".repeat(72);
    static final String DLINE = "=".repeat(72);

    private final Scanner scanner;

    ConsoleIO(Scanner scanner) {
        this.scanner = scanner;
    }

    void println(String text) {
        System.out.println(text);
    }

    void warn(String text) {
        System.out.println("  [!] " + text);
    }

    int readInt(String prompt) {
        while (true) {
            System.out.print("  " + prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                warn("Ingresa un número entero valido.");
            }
        }
    }

    int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) return value;
            warn("El valor debe ser mayor a 0.");
        }
    }

    String readLine(String prompt) {
        System.out.print("  " + prompt);
        return scanner.nextLine();
    }

    int parseIntSafe(String s, int defaultValue) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    String formatCLP(int amount) {
        NumberFormat nf = NumberFormat.getInstance(Locale.of("es", "CL"));
        return "$" + nf.format(amount);
    }

    void printProductList(List<Product> list) {
        list.forEach(p -> println(p.toString()));
    }

    void close() {
        scanner.close();
    }
}
